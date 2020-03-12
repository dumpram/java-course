import hr.fer.zemris.hw11.providers.FormLocalizationProvider;
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
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
public class DummyEditor extends JFrame {
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
	public DummyEditor() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(0, 0);
		setSize(600, 600);
		setTitle("Dummy editor!");
		initGUI();
	}
	/**
	 * Initializes graphic user interface.
	 */
	private void initGUI() {

		editor = new JTextArea();
		editor.setEnabled(false);
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

	}
	/**
	 * Set mnemonics and accelerators for actions.
	 */
	private void createActions() {
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
	}
	/**
	 * Creates menus.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		final JMenu fileMenu = new JMenu(flp.getString("file"));
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(openDocumentAction));

		fileMenu.add(new JMenuItem(sortAction));

		fileMenu.add(new JMenuItem(deleteOddAction));

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
			if (fc.showOpenDialog(DummyEditor.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(DummyEditor.this, flp.getString("filenotexist"), flp.getString("error"),
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			byte[] okteti;
			try {
				okteti = Files.readAllBytes(filePath);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(DummyEditor.this,
						flp.getString("errorreading") + fileName.getAbsolutePath() + ".", flp.getString("error"),
						JOptionPane.ERROR_MESSAGE);
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
				if (jfc.showSaveDialog(DummyEditor.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(DummyEditor.this, flp.getString("errorsaving"),
							flp.getString("warning"), JOptionPane.WARNING_MESSAGE);
					return;
				}
				openedFilePath = jfc.getSelectedFile().toPath();
			}
			byte[] podatci = editor.getText().getBytes(StandardCharsets.UTF_8);
			try {
				Files.write(openedFilePath, podatci);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(DummyEditor.this, flp.getString("errorsaving"), flp.getString("error"),
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(DummyEditor.this, flp.getString("save-success"), flp.getString("info"),
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
			dispatchEvent(new WindowEvent(DummyEditor.this, WindowEvent.WINDOW_CLOSING));
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

	private final LocalizableAction sortAction = new LocalizableAction("sort", flp) {

		@Override
		public void actionPerformed(ActionEvent e) {
			String[] lines = editor.getText().split("\n");
			List<String> linesList = new ArrayList<>();
			for (String line : lines) {
				linesList.add(line);
			}
			Collections.sort(linesList);
			StringBuilder sb = new StringBuilder();
			for (String line : linesList) {
				sb.append(line + "\n");
			}
			editor.setText(sb.toString());

		}
	};

	private final LocalizableAction deleteOddAction = new LocalizableAction("odd", flp) {

		@Override
		public void actionPerformed(ActionEvent e) {
			String[] lines = editor.getText().split("\n");
			StringBuffer sb = new StringBuffer();
			for (int i = 1; i < lines.length; i += 2) {
				sb.append(lines[i] + "\n");
			}
			editor.setText(sb.toString());
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
			if (jfc.showSaveDialog(DummyEditor.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(DummyEditor.this, flp.getString("errorsaving"), flp.getString("warning"),
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			openedFilePath = jfc.getSelectedFile().toPath();
			byte[] podatci = editor.getText().getBytes(StandardCharsets.UTF_8);
			try {
				Files.write(openedFilePath, podatci);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(DummyEditor.this, flp.getString("errorsaving"), flp.getString("error"),
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(DummyEditor.this, flp.getString("save-success"), flp.getString("info"),
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
				new DummyEditor().setVisible(true);
			}
		});
	}

}