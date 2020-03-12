package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The class RequestContext has following private properties OutputStream
 * outputStream and Charset charset; following public write-only properties
 * String encoding (defaults to "UTF-8"), int statusCode (defaults to 200),
 * String statusText (defaults to "OK"), String mimeType (defaults to
 * "text/html"); following private collections Map<String,String> parameters,
 * Map<String,String> temporaryParameters, Map<String,String>
 * persistentParameters, List<RCCookie> outputCookies and private property
 * boolean headerGenerated (defaults to false).
 * 
 * @author Ivan Pavić
 * 
 */
public class RequestContext {
	/**
	 * RCCookie has read-only String properties name, value, domain and path and
	 * read-only Integer property maxAge.
	 * 
	 * @author Ivan Pavić
	 * 
	 */
	public static class RCCookie {
		/**
		 * Name of cookie.
		 */
		private final String name;
		/**
		 * Value of cookie.
		 */
		private final String value;
		/**
		 * Domain of cookie.
		 */
		private final String domain;
		/**
		 * Path of cookie.
		 */
		private final String path;
		/**
		 * Max age of cookie.
		 */
		private final Integer maxAge;
		/**
		 * Flag for httpOnly cookie.
		 */
		private boolean httpOnly;
		/**
		 * Constructor generated from all fields.
		 * 
		 * @param name
		 *            name of cookie
		 * @param value
		 *            value of cookie
		 * @param maxAge
		 *            max age of cookie
		 * @param path
		 *            path of cookie
		 * @param domain
		 *            domain of cookie
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}
		/**
		 * Getter for name of cookie.
		 * 
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * Getter for value of cookie.
		 * 
		 * @return the value
		 */
		public String getValue() {
			return value;
		}
		/**
		 * Getter for domain of cookie.
		 * 
		 * @return the domain
		 */
		public String getDomain() {
			return domain;
		}
		/**
		 * Getter for path of cookie.
		 * 
		 * @return the path
		 */
		public String getPath() {
			return path;
		}
		/**
		 * Getter for max age of cookie.
		 * 
		 * @return the maxAge
		 */
		public int getMaxAge() {
			return maxAge;
		}
		@Override
		public String toString() {
			StringBuffer output = new StringBuffer();
			output.append(name + "=" + "\"" + value + "\"");
			if (domain != null) {
				output.append("; ");
				output.append("Domain=" + domain);
			}
			if (path != null) {
				output.append("; ");
				output.append("Path=" + path);
			}
			if (maxAge != null) {
				output.append("; ");
				output.append("Max-Age=" + maxAge);
			}
			if (httpOnly) {
				output.append("; HttpOnly");
			}
			return output.toString();
		}
		public void setHttpOnly(boolean state) {
			httpOnly = state;
		}
	}
	/**
	 * Private output stream.
	 */
	private final OutputStream outputStream;
	/**
	 * Private charset property.
	 */
	private Charset charset;
	/**
	 * Encoding for Request context
	 */
	private String encoding = "UTF-8";
	/**
	 * Status code of request. Default value 200.
	 */
	private int statusCode = 200;
	/**
	 * Status text.
	 */
	private String statusText = "OK";
	/**
	 * Mime type.
	 */
	private String mimeType = "text/html";
	/**
	 * Private map of parameters.
	 */
	private final Map<String, String> parameters;
	/**
	 * Private map of temporary parameters.
	 */
	private final Map<String, String> temporaryParameters = new HashMap<>();
	/**
	 * Private map of persistent parameters.
	 */
	private final Map<String, String> persistentParameters;
	/**
	 * List of RCCookies.
	 */
	private final List<RCCookie> outputCookies;
	/**
	 * Content length.
	 */
	private int contentLength;
	/**
	 * Flag for generation header. Default is false.
	 */
	private boolean headerGenerated = false;
	/**
	 * Is Content length set.
	 */
	private boolean contentSet;
	/**
	 * Constructor accepts 4 parameters.
	 * 
	 * @param outputStream
	 *            must not be null
	 * @param parameters
	 *            if null is treated as empty
	 * @param persistentParameters
	 *            if null is treated as empty
	 * @param outputCookies
	 *            if null is treated as empty
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		if (outputStream == null) {
			throw new IllegalArgumentException("Output stream must not be null!");
		}
		if (parameters == null) {
			parameters = new HashMap<>();
		}
		if (persistentParameters == null) {
			persistentParameters = new HashMap<>();
		}
		if (outputCookies == null) {
			outputCookies = new ArrayList<>();
		}
		this.outputStream = outputStream;
		this.parameters = parameters;
		this.persistentParameters = persistentParameters;
		this.outputCookies = outputCookies;
	}
	/**
	 * Method that retrieves value from parameters map (or null if no
	 * association exists).
	 * 
	 * @param name
	 *            given key
	 * @return parameter
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	/**
	 * Method that retrieves names of all parameters in parameters map (note,
	 * this set must be read-only).
	 * 
	 * @return set of parameter names
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}
	/**
	 * Method that retrieves value from persistentParameters map (or null if no
	 * association exists).
	 * 
	 * @param name
	 *            given key
	 * @return persistent parameter
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	/**
	 * Method that retrieves names of all parameters in persistentParameters map
	 * (note, this set must be read-only).
	 * 
	 * @return set of persistent parameter names
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}
	/**
	 * Method that stores a value to persistentParameters map.
	 * 
	 * @param name
	 *            given key
	 * @param value
	 *            given value
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	/**
	 * Method that removes a value from persistentParameters map.
	 * 
	 * @param name
	 *            given key
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	/**
	 * Method that retrieves value from temporaryParameters map (or null if no
	 * association exists).
	 * 
	 * @param name
	 *            given key
	 * @return temporary parameter
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	/**
	 * Method that retrieves names of all parameters in temporaryParameters map
	 * (note, this set must be read-only).
	 * 
	 * @return set of temporary parameter names
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}
	/**
	 * Method that stores a value to temporaryParameters map.
	 * 
	 * @param name
	 *            given key
	 * @param value
	 *            given value
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}
	/**
	 * Method that removes a value from temporaryParameters map.
	 * 
	 * @param name
	 *            given key
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * @param encoding
	 *            the encoding to set
	 */
	public void setEncoding(String encoding) {
		checkIsHeaderCreated();
		this.encoding = encoding;
	}
	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		checkIsHeaderCreated();
		this.statusCode = statusCode;
	}
	/**
	 * @param statusText
	 *            the statusText to set
	 */
	public void setStatusText(String statusText) {
		checkIsHeaderCreated();
		this.statusText = statusText;
	}
	/**
	 * @param mimeType
	 *            the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		checkIsHeaderCreated();
		this.mimeType = mimeType;
	}
	/**
	 * Checks if header is already generated.
	 * 
	 * @throws RuntimeException
	 *             if header is already generated
	 */
	private void checkIsHeaderCreated() {
		if (headerGenerated) {
			throw new RuntimeException("Header already created!");
		}
	}
	/**
	 * Obtains header.
	 * 
	 * @return byte array representing header.
	 */
	private byte[] obtainHeader() {
		charset = Charset.forName(encoding);
		StringBuffer sb = new StringBuffer();
		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		sb.append("Content-Type: " + mimeType);
		if (mimeType.startsWith("text/")) {
			sb.append("; charset=" + encoding);
		}
		sb.append("\r\n");
		if (contentSet) {
			sb.append("Content-Length: " + contentLength + "\r\n");
		}
		for (RCCookie i : outputCookies) {
			sb.append("Set-Cookie: ");
			sb.append(i.toString());
			sb.append("\r\n");
		}
		sb.append("\n");
		headerGenerated = true;
		return sb.toString().getBytes(StandardCharsets.ISO_8859_1);
	}
	/**
	 * Writes data to {@link OutputStream}.
	 * 
	 * @param data
	 *            given byte array to write.
	 * @return this
	 * @throws IOException
	 *             if writing can't be done
	 */
	public RequestContext write(byte[] data) throws IOException {
		writeHeader();
		outputStream.write(data);
		return this;
	}
	/**
	 * Writes data to {@link OutputStream}.
	 * 
	 * @param data
	 *            string
	 * @return this
	 * @throws IOException
	 *             if writing can't be done
	 */
	public RequestContext write(String data) throws IOException {
		return write(data.getBytes(encoding));
	}
	/**
	 * Writes header.
	 * 
	 * @throws IOException
	 *             because of writing
	 */
	private void writeHeader() throws IOException {
		if (!headerGenerated) {
			outputStream.write(obtainHeader());
		}
	}
	/**
	 * @return the charset
	 */
	public Charset getCharset() {
		return charset;
	}
	/**
	 * Adds given cookie to cookie list.
	 * 
	 * @param rcCookie
	 *            given cookie
	 */
	public void addRCCookie(RCCookie rcCookie) {
		outputCookies.add(rcCookie);
	}
	/**
	 * @param contentLength
	 *            the contentLength to set
	 */
	public void setContentLength(int contentLength) {
		checkIsHeaderCreated();
		contentSet = true;
		this.contentLength = contentLength;
	}
}
