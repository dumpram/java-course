package hr.fer.zemris.hw11.providers;
/**
 * Interface defines Localization provider.
 * 
 * @author Ivan Pavić
 * 
 */
public interface ILocalizationProvider {
	/**
	 * Adds localization listener to list of listeners.
	 * 
	 * @param listener
	 *            given listener.
	 */
	void addLocalizationListener(ILocalizationListener listener);
	/**
	 * Removes localization givenlistener.
	 * 
	 * @param listener
	 *            given listener
	 */
	void removeLocalizationListener(ILocalizationListener listener);
	/**
	 * Returns key representing translation of some kind.
	 * 
	 * @param string
	 *            given string key
	 * @return translation
	 */
	String getString(String string);
}
