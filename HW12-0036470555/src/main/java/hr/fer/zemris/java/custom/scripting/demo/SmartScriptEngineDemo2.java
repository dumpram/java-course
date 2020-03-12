package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Demo for smart script engine.
 * 
 * @author Ivan Pavić
 * 
 */
public class SmartScriptEngineDemo2 {
	/**
	 * Accepts arguments from command line.
	 * 
	 * @param args
	 *            args[0] path to file.
	 * @throws IOException
	 *             if error while reading occurs
	 */
	public static void main(String[] args) throws IOException {
		String documentBody = TreeWriter.readFromDisk("zbrajanje.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		parameters.put("a", "4");
		parameters.put("b", "2");
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), new RequestContext(System.out,
				parameters, persistentParameters, cookies)).execute();
	}

}
