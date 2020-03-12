package hr.fer.zemris.hw11.providers;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Singleton class which represents LocaliztionProvider for some application. It
 * is never used directly.
 * 
 * @author Ivan Pavić
 * 
 */
public final class LocalizationProvider extends AbstractLocalizationProvider {
	/**
	 * Current language.
	 */
	private String language;
	/**
	 * Instance of resource bundle.
	 */
	private ResourceBundle bundle;
	/**
	 * Instance of LocaliztionProvider.
	 */
	private static LocalizationProvider instance = new LocalizationProvider();

	private LocalizationProvider() {
	}
	/**
	 * Returns single instance of LocaliztionProvider.
	 * 
	 * @return instance of LocalizationProvider
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
	/**
	 * Sets language and informs listeners.
	 * 
	 * @param newLanguage
	 *            given new language to set
	 */
	public void setLanguage(String newLanguage) {
		language = newLanguage;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.hw11.prijevodi.prijevodi", locale);
		fire();
	}

	@Override
	public String getString(String key) {
		try {
			return new String(bundle.getString(key).getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException ignorable) {

		}
		return null;
	}

}
