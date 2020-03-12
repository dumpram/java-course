package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.util.Set;
/**
 * EchoParams simply outputs back to user parameters it obtained in a HTML
 * table.
 * 
 * @author Ivan Pavić
 * 
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		Set<String> parameters = context.getParameterNames();

		for (String i : parameters) {
			try {
				context.write(i + "\r\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
