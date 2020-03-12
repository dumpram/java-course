package hr.fer.zemris.hw11.providers;
/**
 * LocalizationProviderBridge is class which is adapter for
 * ILocalizationProvider.
 * 
 * @author Ivan Pavić
 * 
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	/**
	 * Flag of connection.
	 */
	private boolean connected;
	/**
	 * Instance of provider.
	 */
	private final ILocalizationProvider provider;
	/**
	 * Constructor accepts some provider to adapt.
	 * 
	 * @param someProvider
	 *            given provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider someProvider) {
		provider = someProvider;
	}

	@Override
	public String getString(String key) {
		return provider.getString(key);
	}
	/**
	 * Connects provider.
	 */
	public void connect() {
		if (!connected) {
			provider.addLocalizationListener(new ILocalizationListener() {

				@Override
				public void localizationChanged() {
					fire();
				}
			});
		}
	}
	/**
	 * Disconnects listeners.
	 */
	public void disconnect() {
		listeners.clear();
	}
}
