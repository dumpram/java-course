package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Class represents SmartHttpServer implementation using multi thread client
 * server method.
 * 
 * @author Ivan Pavić
 * 
 */
public class SmartHttpServer {
	/**
	 * Cleans cookies from session map every WAIT seconds.
	 * 
	 * @author Ivan Pavić
	 * 
	 */
	private class CookieCleaner extends Thread {
		/**
		 * Time to wait before cleaning all inactive sessions.
		 */
		private static final int WAIT = 10;

		@Override
		public void run() {
			while (serverThread.getFlag()) {
				for (String i : new ArrayList<>(sessions.keySet())) {
					if (isTooOld(sessions.get(i))) {
						sessions.remove(i);
					}
				}
				try {
					sleep(WAIT * 1000);
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}
	/**
	 * Session map entry class represent single session event.
	 * 
	 * @author Ivan Pavić
	 * 
	 */
	private static class SessionMapEntry {
		/**
		 * Serial id.
		 */
		@SuppressWarnings("unused")
		String sid;
		/**
		 * Valid until long variable.
		 */
		long validUntil;
		/**
		 * Map of strings.
		 */
		Map<String, String> map;
		/**
		 * Constructor for sessionMapEntry using serial id for client and
		 * sessionTimeout time.
		 * 
		 * @param sid
		 *            client's serial id
		 */
		public SessionMapEntry(String sid, int sessionTimeout) {
			this.sid = sid;
			validUntil = System.currentTimeMillis() + sessionTimeout * 1000;
			map = new ConcurrentHashMap<>();
		}
	}
	/**
	 * Private address.
	 */
	private final String address;
	/**
	 * Port of server.
	 */
	private final int port;
	/**
	 * Number of worker threads.
	 */
	private final int workerThreads;
	/**
	 * Session timeout property for socket timeout.
	 */
	private final int sessionTimeout;
	/**
	 * Mime types.
	 */
	private final Map<String, String> mimeTypes = new HashMap<String, String>();
	/**
	 * Instance of serverThread.
	 */
	private ServerThread serverThread;
	/**
	 * Private thread pool.s
	 */
	private ExecutorService threadPool;
	/**
	 * Private document root path.
	 */
	private final Path documentRoot;
	/**
	 * Private map of workers.
	 */
	private final Map<String, IWebWorker> workersMap = new HashMap<>();
	/**
	 * Map of session map entries.
	 */
	private final Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/**
	 * Session random generator.
	 */
	private final Random sessionRandom = new Random();
	/**
	 * Constructor accepts server configuration file name.
	 * 
	 * @param configFileName
	 *            given configuration file name
	 */
	public SmartHttpServer(String configFileName) {

		Properties serverProperties = new Properties();

		try {
			serverProperties.load(new FileInputStream(configFileName));
		} catch (FileNotFoundException ignorable) {
		} catch (IOException ignorable) {
		}

		address = serverProperties.getProperty("server.address");
		port = Integer.parseInt(serverProperties.getProperty("server.port"));
		workerThreads = Integer.parseInt(serverProperties.getProperty("server.workerThreads"));
		documentRoot = Paths.get(serverProperties.getProperty("server.documentRoot"));
		sessionTimeout = Integer.parseInt(serverProperties.getProperty("session.timeout"));
		Properties workersList = new Properties();
		Properties mimeProperties = new Properties();

		try {
			workersList.load(new FileInputStream(serverProperties.getProperty("server.workers")));
		} catch (IOException e) {
		}

		Set<Object> workerKey = workersList.keySet();

		for (Object i : workerKey) {
			String fqcn = workersList.getProperty((String) i);
			Class<?> referenceToClass = null;
			try {
				referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Object newObject = null;
			try {
				newObject = referenceToClass.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {

				e.printStackTrace();
			}
			IWebWorker iww = (IWebWorker) newObject;
			workersMap.put((String) i, iww);
		}
		try {
			mimeProperties.load(new FileInputStream(serverProperties.getProperty("server.mimeConfig")));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

		Set<Object> mimeKey = mimeProperties.keySet();

		for (Object i : mimeKey) {
			mimeTypes.put(i.toString(), mimeProperties.get(i).toString());
		}

	}
	private boolean isTooOld(SessionMapEntry found) {
		long current = System.currentTimeMillis();
		return (current - found.validUntil) > sessionTimeout * 1000;
	}
	/**
	 * Starts serverThread if not already running and initializes thread pool.
	 */
	protected synchronized void start() {

		if (serverThread == null) {
			serverThread = new ServerThread();
			serverThread.start();
			threadPool = Executors.newFixedThreadPool(workerThreads);
		}

	}
	/**
	 * Stops server thread.
	 */
	protected synchronized void stop() {
		try {
			serverThread.setFlag(false);
			serverThread.interrupt();
			serverThread.join();
		} catch (InterruptedException ignorable) {
			// wait for thread to die
		}
		threadPool.shutdown();
	}
	/**
	 * ServerThread class is simple implementation of main server thread which
	 * delegates job to client workers.
	 * 
	 * @author Ivan Pavić
	 * 
	 */
	protected class ServerThread extends Thread {
		/**
		 * Regulates life of thread.
		 */
		private boolean flag = true;
		/**
		 * Cookie cleaner. Cookie cleaning thread.
		 */
		private CookieCleaner cookieCleaner;

		@Override
		public void run() {
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(port, 50, InetAddress.getByName(address));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				serverSocket.setSoTimeout(1000);
			} catch (SocketException ignorable) {
			}
			cookieCleaner = new CookieCleaner();
			threadPool.submit(cookieCleaner);
			while (flag) {
				Socket client = null;
				try {
					client = serverSocket.accept();
				} catch (IOException ignorable) {
				}
				ClientWorker cw = new ClientWorker(client);
				threadPool.submit(cw);
			}
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/**
		 * Sets private flag to given value.
		 * 
		 * @param flag
		 *            given boolean value
		 */
		public void setFlag(boolean flag) {
			this.flag = flag;
		}
		/**
		 * Getter for flag.
		 * 
		 * @return value of flag
		 */
		public boolean getFlag() {
			return flag;
		}
	}
	/**
	 * Client worker serves one client.
	 * 
	 * @author Ivan Pavić
	 * 
	 */
	private class ClientWorker implements Runnable {
		/**
		 * Private socket instance.
		 */
		private final Socket csocket;
		/**
		 * Private input stream.
		 */
		private PushbackInputStream istream;
		/**
		 * Private output stream.
		 */
		private OutputStream ostream;
		/**
		 * Version string.
		 */
		@SuppressWarnings("unused")
		private String version;
		/**
		 * Method string.
		 */
		@SuppressWarnings("unused")
		private String method;
		/**
		 * Parameter map.
		 */
		private final Map<String, String> params = new HashMap<String, String>();
		/**
		 * Permanent parameter map.
		 */
		private Map<String, String> permPrams = null;
		/**
		 * List of RCCookies.
		 */
		private final List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/**
		 * Serial identification.
		 */
		private String SID;
		/**
		 * Constructor takes socket as parameter.
		 * 
		 * @param csocket
		 *            given socket.
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}
		/**
		 * Creates serial id for session.
		 */
		private void createSid() {
			StringBuffer sb = new StringBuffer();
			String alphabet = "abcdedfghijklmnopqrstuvwxyz";
			for (int i = 0; i < 20; i++) {
				sb.append(alphabet.charAt(sessionRandom.nextInt(alphabet.length())));
			}
			SID = new String(sb);
			SID = SID.toUpperCase();
		}
		@Override
		public void run() {

			InputStream obtainFromWorker = null;
			try {
				obtainFromWorker = csocket.getInputStream();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			istream = new PushbackInputStream(obtainFromWorker);
			try {
				ostream = csocket.getOutputStream();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			List<String> request = null;
			try {
				request = readRequest();
			} catch (IOException e) {
			}
			if (request.isEmpty()) {
				sendError(400, "text/plain", "Los zahtjev");
				return;
			}
			String firstLine = request.get(0);

			String[] parts = firstLine.split(" ");

			if (!parts[0].equalsIgnoreCase("GET")) {
				sendError(400, "text/plain", "Los zahtjev");
				return;
			}

			method = "GET";

			if (!parts[2].equalsIgnoreCase("HTTP/1.0") && !parts[2].equalsIgnoreCase("HTTP/1.1")) {
				sendError(400, "text/plain", "Los zahtjev");
				return;
			}
			version = parts[2];

			checkSession(request);

			String[] requestedPath = null;

			if (parts[1].contains("?")) {
				requestedPath = parts[1].split("\\?");
			}
			if (parts[1].equals("/")) {
				parts[1] = "/index.html";
			}
			if (requestedPath == null) {
				requestedPath = new String[]{parts[1], null};
			}

			String path = documentRoot.toString() + requestedPath[0];

			String paramString = null;

			if (requestedPath[1] != null) {
				paramString = requestedPath[1];
				parseParameters(paramString);
			}
			Path stvarna = Paths.get(path);

			String root = null;
			try {
				root = documentRoot.toFile().getCanonicalPath();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			String dobiveni = null;
			try {
				dobiveni = stvarna.toFile().getCanonicalPath();
			} catch (IOException e) {

				e.printStackTrace();
			}

			if (!dobiveni.startsWith(root)) {
				sendError(503, "text/plain", "Nelegalan zahtjev");
				return;
			}
			if (requestedPath[0].startsWith("/ext/")) {
				String fqcn = stvarna.getFileName().toString();
				fqcn = findMatchingWorker(fqcn);
				if (fqcn != null) {
					fqcn = "hr.fer.zemris.java.webserver.workers." + fqcn;
					Class<?> referenceToClass = null;
					try {
						referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
					} catch (ClassNotFoundException e) {

						e.printStackTrace();
					}
					Object newObject = null;
					try {
						newObject = referenceToClass.newInstance();
					} catch (InstantiationException e) {

						e.printStackTrace();
					} catch (IllegalAccessException e) {

						e.printStackTrace();
					}
					IWebWorker iww = (IWebWorker) newObject;
					RequestContext context = new RequestContext(ostream, params, permPrams, outputCookies);
					context.setMimeType("text/html");
					iww.processRequest(context);
					try {
						csocket.close();
					} catch (IOException e) {

						e.printStackTrace();
					}
					return;
				}
			}

			if (workersMap.containsKey(requestedPath[0])) {
				RequestContext context = new RequestContext(ostream, params, permPrams, outputCookies);
				context.setMimeType("text/html");
				workersMap.get(requestedPath[0]).processRequest(context);
				try {
					csocket.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
				return;
			}

			if (!Files.exists(stvarna)) {
				sendError(404, "text/plain", "Datoteka ne postoji!");
				return;
			}

			String fileName = stvarna.getFileName().toString();
			int index = fileName.lastIndexOf(".");
			if (index == -1) {
				index = 0;
			}
			String mime = mimeTypes.get(fileName.substring(index + 1));
			if (mime == null) {
				mime = "application/octet-stream";
			}
			RequestContext rc = new RequestContext(ostream, params, permPrams, outputCookies);
			rc.setMimeType(mime);
			byte[] data = null;

			try {
				data = readFromFile(stvarna.toString());
			} catch (IOException e) {

				e.printStackTrace();
			}

			if (fileName.substring(index + 1).equalsIgnoreCase("smscr")) {
				new SmartScriptEngine(
						new SmartScriptParser(new String(data, StandardCharsets.UTF_8)).getDocumentNode(), rc)
						.execute();
			} else {
				rc.setContentLength(data.length);
				try {
					rc.write(data);
				} catch (IOException e) {

					e.printStackTrace();
				}
			}

			try {
				csocket.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		/**
		 * Checks new session using request. Given list is list of strings used
		 * 
		 * @param request
		 *            given list of strings
		 */
		private void checkSession(List<String> request) {
			String sidCandidate = null;
			for (String i : request) {
				if (i.isEmpty()) {
					break;
				}
				if (i.startsWith("Cookie:")) {
					String temp = i.substring(8);
					String[] parts = temp.split(";");
					for (String part : parts) {
						if (part.startsWith("sid")) {
							String[] name = part.split("=");
							sidCandidate = name[1].replaceAll("\"", "");
						}
					}
				}
			}
			if (sidCandidate == null) {
				createNewSid();
				return;
			}
			SessionMapEntry found = sessions.get(sidCandidate);
			if (found == null) {
				createNewSid();
				return;
			}
			if (isTooOld(found)) {
				createNewSid();
				return;
			}
			found.validUntil = System.currentTimeMillis() + 1000 * sessionTimeout;
			permPrams = found.map;
		}
		/**
		 * Creates new SID for current client entry.
		 */
		private void createNewSid() {
			createSid();
			SessionMapEntry newOne = new SessionMapEntry(SID, sessionTimeout);
			sessions.put(SID, newOne);
			RCCookie cookie = new RCCookie("sid", SID, null, address, "/");
			cookie.setHttpOnly(true);
			outputCookies.add(cookie);

		}
		/**
		 * Finds matching worker from given string.
		 * 
		 * @param fqcn
		 *            given string
		 * @return matcing worker or null if no match is found
		 */
		private String findMatchingWorker(String fqcn) {
			if (fqcn.equalsIgnoreCase("EchoParams")) {
				return "EchoParams";
			}
			if (fqcn.equalsIgnoreCase("Hello")) {
				return "HelloWorker";
			}
			if (fqcn.equalsIgnoreCase("cw")) {
				return "CircleWorker";
			}

			return null;
		}
		/**
		 * Reads from file with given file name.
		 * 
		 * @param fileName
		 *            given file name
		 * @throws IOException
		 *             if error during reading occurs
		 */
		private byte[] readFromFile(String fileName) throws IOException {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();
			return data;
		}
		/**
		 * Parses parameters form string.
		 * 
		 * @param paramString
		 */
		private void parseParameters(String paramString) {
			String[] parameters = paramString.split("&");
			for (String i : parameters) {
				String parts[] = i.split("=");
				params.put(parts[0], parts[1]);
			}
		}
		/**
		 * Sends error with given status, mime and message.
		 * 
		 * @param status
		 *            integer indicating status
		 * @param mime
		 *            mime type
		 * @param message
		 *            message to send
		 */
		private void sendError(int status, String mime, String message) {
			byte[] data = message.getBytes(StandardCharsets.UTF_8);
			String odgovor = "HTTP/1.1 " + status + " OK \r\n" + "Content-Type: " + mime + ";charsets=utf-8\n"
					+ "Content-Length: " + data.length + "\n" + "\n";

			try {
				ostream.write(odgovor.getBytes(StandardCharsets.ISO_8859_1));
			} catch (IOException e) {

				e.printStackTrace();
			}
			try {
				ostream.write(data);
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			try {
				ostream.flush();
			} catch (IOException e) {

				e.printStackTrace();
			}
			try {
				ostream.close();
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
		/**
		 * Reads whole request from client from input stream.
		 * 
		 * @return list of strings representing request.
		 * @throws IOException
		 *             if error occurs during reading
		 */
		private List<String> readRequest() throws IOException {
			List<String> forExport = new ArrayList<>();
			BufferedReader r = new BufferedReader(new InputStreamReader(istream, StandardCharsets.ISO_8859_1));
			while (true) {
				String line = r.readLine();
				if (line.isEmpty()) {
					break;
				} else {
					forExport.add(line);
				}
			}
			return forExport;
		}
	}
	/**
	 * Main method. Accepts server configuration properties path as argument.
	 * 
	 * @param args
	 *            args[0] path of properties
	 */
	public static void main(String[] args) {
		SmartHttpServer server = new SmartHttpServer(args[0]);
		server.start();
		Scanner waitForInput = new Scanner(System.in, "UTF-8");
		System.out.println("Press any key to shutdown server...");
		waitForInput.nextLine();
		System.out.println("Server is stopping...");
		server.stop();
		System.out.println("Server will be stopped in few seconds!");
		waitForInput.close();
	}
}