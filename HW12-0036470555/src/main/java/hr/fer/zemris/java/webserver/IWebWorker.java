package hr.fer.zemris.java.webserver;
/**
 * Interface toward any object that can process current request: it gets
 * RequestContext as parameter and it is expected to create a content for
 * client.
 * 
 * @author Ivan Pavić
 * 
 */
public interface IWebWorker {
	/**
	 * Method processes request in given context.
	 * 
	 * @param context
	 *            given context
	 */
	void processRequest(RequestContext context);

}
