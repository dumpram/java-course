package hr.fer.zemris.hw11.notepad;

import hr.fer.zemris.hw11.providers.FormLocalizationProvider;
import hr.fer.zemris.hw11.providers.ILocalizationListener;
import hr.fer.zemris.hw11.providers.LocalizableAction;
import hr.fer.zemris.hw11.providers.LocalizationProvider;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
/**
 * Main class for JNotepad++ application.
 * 
 * @author Ivan Pavić
 * 
 */
public class JNotepad extends JFrame {
	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Private JTextArea representing editor.
	 */
	private JTextArea editor;
	/**
	 * Path of file which is currently open.
	 */
	private Path openedFilePath;
	/**
	 * FormLocalizationProvider instance.
	 */
	private final FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	/**
	 * Indicates state of document.
	 */
	private boolean stateChanged = true;
	/**
	 * Default constructor.
	 */
	public JNotepad() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		setSize(600, 600);
		setTitle("JNotepad++ 1.0");

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				if (stateChanged) {
					String[] options = new String[]{flp.getString("yes"), flp.getString("no"), flp.getString("cancel")};
					JPanel panel = new JPanel();
					JLabel label = new JLabel(flp.getString("exitconfirm"));
					panel.add(label);
					int rez = JOptionPane.showOptionDialog(JNotepad.this, panel, "JNotepad++",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

					if (rez == JOptionPane.YES_OPTION) {
						JButton button = new JButton(saveDocumentAction);
						button.doClick();
						if (!stateChanged) {
							dispose();
							return;
						}
						return;
					}
					if (rez == JOptionPane.NO_OPTION) {
						dispose();
						System.exit(0);
					}
					if (rez == JOptionPane.CANCEL_OPTION) {
						return;
					}
				}
				dispose();
				return;
			}
		});
		initGUI();
	}
	/**
	 * Initializes graphic user interface.
	 */
	private void initGUI() {

		editor = new JTextArea();
		Document doc = editor.getDocument();
		doc.addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				stateChanged = true;

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				stateChanged = true;

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				stateChanged = true;

			}
		});

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(new JScrollPane(editor), BorderLayout.CENTER);
		createActions();
		createMenus();
		createToolbars();

	}
	/**
	 * Set mnemonics and accelerators for actions.
	 */
	private void createActions() {
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		deleteSelectedPartAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F2"));
		deleteSelectedPartAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);

		toggleCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F3"));
		toggleCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);

		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);

		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);

		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);

		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
	}
	/**
	 * Creates menus.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		final JMenu fileMenu = new JMenu(flp.getString("file"));
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));

		final JMenu editMenu = new JMenu(flp.getString("edit"));

		menuBar.add(editMenu);

		editMenu.add(new JMenuItem(deleteSelectedPartAction));
		editMenu.add(new JMenuItem(toggleCaseAction));
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(pasteAction));

		final JMenu languageMenu = new JMenu(flp.getString("language"));
		languageMenu.add(new JMenuItem(changeLanguageHR));
		languageMenu.add(new JMenuItem(changeLanguageEN));
		menuBar.add(languageMenu);

		flp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				fileMenu.setText(flp.getString("file"));
				editMenu.setText(flp.getString("edit"));
				languageMenu.setText(flp.getString("language"));

			}
		});
		this.setJMenuBar(menuBar);
	}
	/**
	 * Creates toolBar.
	 */
	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);

		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(deleteSelectedPartAction));
		toolBar.add(new JButton(toggleCaseAction));
		toolBar.add(new JButton(copyAction));
		toolBar.add(new JButton(cutAction));
		toolBar.add(new JButton(pasteAction));

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}
	/**
	 * Open document action.
	 */
	private final Action openDocumentAction = new LocalizableAction("open", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle(flp.getString("open"));
			if (fc.showOpenDialog(JNotepad.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(JNotepad.this, flp.getString("filenotexist"), flp.getString("error"),
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			byte[] okteti;
			try {
				okteti = Files.readAllBytes(filePath);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(JNotepad.this, flp.getString("errorreading") + fileName.getAbsolutePath()
						+ ".", flp.getString("error"), JOptionPane.ERROR_MESSAGE);
				return;
			}
			String tekst = new String(okteti, StandardCharsets.UTF_8);
			editor.setText(tekst);
			openedFilePath = filePath;
		}
	};
	/**
	 * Save document action.
	 */
	private final Action saveDocumentAction = new LocalizableAction("save", flp) {
		/**
		 * Default serial version.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (openedFilePath == null) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle(flp.getString("save-description"));
				if (jfc.showSaveDialog(JNotepad.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JNotepad.this, flp.getString("errorsaving"),
							flp.getString("warning"), JOptionPane.WARNING_MESSAGE);
					return;
				}
				openedFilePath = jfc.getSelectedFile().toPath();
			}
			byte[] podatci = editor.getText().getBytes(StandardCharsets.UTF_8);
			try {
				Files.write(openedFilePath, podatci);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(JNotepad.this, flp.getString("errorsaving"), flp.getString("error"),
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(JNotepad.this, flp.getString("save-success"), flp.getString("info"),
					JOptionPane.INFORMATION_MESSAGE);
			stateChanged = false;
		}
	};
	/**
	 * Delete selected part action.
	 */
	private final Action deleteSelectedPartAction = new LocalizableAction("delete", flp) {
		/**
		 * Default serial version.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			if (len == 0) {
				return;
			}
			int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			try {
				doc.remove(offset, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	};
	/**
	 * Change case action.
	 */
	private final Action toggleCaseAction = new LocalizableAction("toggle", flp) {
		/**
		 * Default serial version.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			int offset = 0;
			if (len != 0) {
				offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			} else {
				len = doc.getLength();
			}
			try {
				String text = doc.getText(offset, len);
				text = changeCase(text);
				doc.remove(offset, len);
				doc.insertString(offset, text, null);
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		}
		/**
		 * Changes case of given string.
		 * 
		 * @param text
		 *            given string text
		 * @return string with changed case
		 */
		private String changeCase(String text) {
			char[] znakovi = text.toCharArray();
			for (int i = 0; i < znakovi.length; i++) {
				char c = znakovi[i];
				if (Character.isLowerCase(c)) {
					znakovi[i] = Character.toUpperCase(c);
				} else if (Character.isUpperCase(c)) {
					znakovi[i] = Character.toLowerCase(c);
				}
			}
			return new String(znakovi);
		}
	};
	/**
	 * Action for abandon program.
	 */
	private final Action exitAction = new LocalizableAction("exit", flp) {
		/**
		 * Default serial version.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			dispatchEvent(new WindowEvent(JNotepad.this, WindowEvent.WINDOW_CLOSING));
		}
	};
	/**
	 * Changes language to HR.
	 */
	private final Action changeLanguageHR = new LocalizableAction("languageHR", flp) {

		/**
		 * Default serial version.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
		}
	};
	/**
	 * Changes language to EN.
	 */
	private final Action changeLanguageEN = new LocalizableAction("languageEN", flp) {
		/**
		 * Default serial version.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
		}
	};
	/**
	 * Action copies content of editor to clipboard.
	 */
	private final Action copyAction = new LocalizableAction("copy", flp) {
		/**
		 * Default serial version.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			if (len == 0) {
				return;
			}
			int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			try {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(new StringSelection(doc.getText(offset, len)), null);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}

		}
	};
	/**
	 * Action cuts content of editor to clipboard
	 */
	private final Action cutAction = new LocalizableAction("cut", flp) {
		/**
		 * Default serial version.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			if (len == 0) {
				return;
			}
			int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			try {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(new StringSelection(doc.getText(offset, len)), null);
				doc.remove(offset, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}

		}
	};
	/**
	 * Action pastes content of clipboard to editor.
	 */
	private final Action pasteAction = new LocalizableAction("paste", flp) {
		/**
		 * Default serial version.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();

			int offset = editor.getCaret().getDot();
			try {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				Transferable data = clipboard.getContents(DataFlavor.stringFlavor);
				try {
					doc.insertString(offset, (String) data.getTransferData(DataFlavor.stringFlavor), null);
				} catch (UnsupportedFlavorException ignorable) {
				} catch (IOException ignorable) {
				}
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}

		}
	};
	/**
	 * Save as action.
	 */
	private final LocalizableAction saveAsAction = new LocalizableAction("saveas", flp) {
		/**
		 * Default serial version.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle(flp.getString("save-description"));
			if (jfc.showSaveDialog(JNotepad.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JNotepad.this, flp.getString("errorsaving"), flp.getString("warning"),
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			openedFilePath = jfc.getSelectedFile().toPath();
			byte[] podatci = editor.getText().getBytes(StandardCharsets.UTF_8);
			try {
				Files.write(openedFilePath, podatci);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(JNotepad.this, flp.getString("errorsaving"), flp.getString("error"),
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(JNotepad.this, flp.getString("save-success"), flp.getString("info"),
					JOptionPane.INFORMATION_MESSAGE);
			stateChanged = false;
		}
	};

	/**
	 * Main method.
	 * 
	 * @param args
	 *            none required.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				LocalizationProvider.getInstance().setLanguage("hr");
				new JNotepad().setVisible(true);
			}
		});
	}

}