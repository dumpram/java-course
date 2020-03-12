package hr.fer.zemris.java.hw13.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * Listener for application start. Sets attribute "app-info" in servlet context.
 * 
 * @author Ivan Pavić
 * 
 */
public class AppInfo implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("app-info", System.currentTimeMillis());
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
