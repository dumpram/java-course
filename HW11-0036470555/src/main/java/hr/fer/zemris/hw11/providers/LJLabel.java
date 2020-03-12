package hr.fer.zemris.hw11.providers;

import javax.swing.JLabel;
/**
 * Represents label which can be translated.
 * 
 * @author Ivan Pavić
 * 
 */
public class LJLabel extends JLabel {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor which accepts key and localization provider.
	 * 
	 * @param key
	 *            given key
	 * @param lp
	 *            given provider
	 */
	public LJLabel(final String key, final ILocalizationProvider lp) {

		lp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				setText(lp.getString(key));

			}
		});
	}

}
