package hr.fer.zemris.hw11.providers;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
/**
 * FormLocaliztionProvider is adapter for LocalizationProvider.
 * 
 * @author Ivan Pavić
 * 
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
	/**
	 * Constructor accepts 2 arguments. ILocalization provider and frame.
	 * 
	 * @param someProvider
	 *            given provider
	 * @param frame
	 *            given frame
	 */
	public FormLocalizationProvider(ILocalizationProvider someProvider, JFrame frame) {
		super(someProvider);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}

}
