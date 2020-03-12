package hr.fer.zemris.hw11.providers;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
/**
 * Class represents action which is localized. Name and description are changed
 * due to language changes.
 * 
 * @author Ivan Pavić
 * 
 */
public abstract class LocalizableAction extends AbstractAction {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor accepts key and localization provider.
	 * 
	 * @param key
	 *            given key
	 * @param lp
	 *            given loclization provider
	 */
	public LocalizableAction(final String key, final ILocalizationProvider lp) {
		putValue(Action.NAME, lp.getString(key));
		putValue(Action.SHORT_DESCRIPTION, lp.getString(key + "-description"));
		lp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				putValue(Action.NAME, lp.getString(key));
				putValue(Action.SHORT_DESCRIPTION, lp.getString(key + "-description"));
			}
		});
	}

	@Override
	public abstract void actionPerformed(ActionEvent e);
}
