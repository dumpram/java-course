package hr.fer.zemris.hw11.providers;

import java.util.ArrayList;
import java.util.List;
/**
 * Class represent abstract localization provider.
 * 
 * @author Ivan Pavić
 * 
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	/**
	 * List of listeners.
	 */
	protected List<ILocalizationListener> listeners = new ArrayList<>();

	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}

	@Override
	public abstract String getString(String string);
	/**
	 * Called when change occurs.
	 */
	public void fire() {
		for (ILocalizationListener listener : listeners) {
			listener.localizationChanged();
		}
	}

}
