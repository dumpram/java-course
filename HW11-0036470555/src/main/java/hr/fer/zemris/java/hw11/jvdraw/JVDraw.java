package hr.fer.zemris.java.hw11.jvdraw;

import hr.fer.zemris.hw11.providers.FormLocalizationProvider;
import hr.fer.zemris.hw11.providers.ILocalizationListener;
import hr.fer.zemris.hw11.providers.LocalizableAction;
import hr.fer.zemris.hw11.providers.LocalizationProvider;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 * JVDraw is main class for JVDraw application. Extends JFrame. It is developed
 * with Java swing library. Application is used for drawing shapes. Additionally
 * it can export drawings in JPG, GIF and PNG format. Supports export in text
 * file containing commands for generating drawings.
 * 
 * @author Ivan Pavić
 * 
 */
public class JVDraw extends JFrame {
	/**
	 * Opened file path.
	 */
	private Path openedFilePath;
	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Localization provider for i18n.
	 */
	private final FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	/**
	 * Constructor for JVDraw frame.
	 */
	/**
	 * Background color area.
	 */
	JColorArea background = new JColorArea(Color.red);
	/**
	 * Foreground color area.
	 */
	JColorArea foreground = new JColorArea(Color.yellow);
	/**
	 * Mutually exclusive buttons group.
	 */
	private final ButtonGroup shapes = new ButtonGroup();
	/**
	 * Drawing model instance.
	 */
	private final DrawingModelImpl drawingModel = new DrawingModelImpl();
	/**
	 * List of drawn objects.
	 */
	private final DrawingObjectListModel listModel = new DrawingObjectListModel(drawingModel);
	/**
	 * Canvas for JVDraw.
	 */
	private final JDrawingCanvas canvas = new JDrawingCanvas(drawingModel, foreground, background, shapes);
	/**
	 * Indicates state of current document.
	 */
	private final ImageChangedListener stateChanged = new ImageChangedListener(drawingModel);
	/**
	 * Default constructor. Accepts no arguments.
	 */
	public JVDraw() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		setSize(600, 600);
		setTitle("JVDraw 1.0");
		initGUI();
		pack();
	}
	/**
	 * Initializes Graphic User Interface for JVDraw application.
	 */
	private void initGUI() {
		setLayout(new BorderLayout());
		createMenus();
		createToolbar();
		createColorPreview();
		createCanvas();
		createList();

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				String[] options = new String[]{flp.getString("yes"), flp.getString("no"), flp.getString("cancel")};
				JPanel panel = new JPanel();
				JLabel label = new JLabel(flp.getString("exitconfirm"));
				panel.add(label);
				if (stateChanged.isChanged()) {
					int rez = JOptionPane.showOptionDialog(JVDraw.this, panel, "JNotepad++",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

					if (rez == JOptionPane.YES_OPTION) {
						JButton button = new JButton(saveAction);
						button.doClick();
						if (!stateChanged.isChanged()) {
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
	}
	/**
	 * Creates list of drawn objects.
	 */
	private void createList() {
		final JList<GeometricalObject> list = new JList<>(listModel);
		JScrollPane s1 = new JScrollPane(list);
		getContentPane().add(s1, BorderLayout.EAST);
		drawingModel.addDrawingModelListener(listModel);
		list.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					GeometricalObject selected = list.getSelectedValue();
					selected.showChangeOptions(JVDraw.this);
					drawingModel.update();;
				}
			}
		});
	}
	/**
	 * Creates central canvas JVDraw.
	 */
	private void createCanvas() {
		drawingModel.addDrawingModelListener(canvas);
		getContentPane().add(canvas, BorderLayout.CENTER);
	}
	/**
	 * Creates color preview label on bottom of frame.
	 */
	private void createColorPreview() {
		JColorPreview colorPreview = new JColorPreview(foreground, background);
		background.addColorChangeListener(colorPreview);
		foreground.addColorChangeListener(colorPreview);
		getContentPane().add(colorPreview, BorderLayout.SOUTH);
	}
	/**
	 * Creates toolbar for JVDraw application.
	 */
	private void createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.add(foreground);
		toolbar.addSeparator();
		toolbar.add(background);
		toolbar.addSeparator();
		createButtonGroup(toolbar);
		getContentPane().add(toolbar, BorderLayout.PAGE_START);
	}
	/**
	 * Creates group of mutually exclusive buttons and adds them to given
	 * toolbar.
	 * 
	 * @param toolbar
	 *            given toolbar
	 */
	private void createButtonGroup(JToolBar toolbar) {
		final JToggleButton line = new JToggleButton(flp.getString("line"));
		final JToggleButton circle = new JToggleButton(flp.getString("circle"));
		final JToggleButton filledCircle = new JToggleButton(flp.getString("filledCircle"));
		line.setActionCommand("line");
		circle.setActionCommand("circle");
		filledCircle.setActionCommand("fcircle");
		line.setSelected(true);
		shapes.add(line);
		shapes.add(circle);
		shapes.add(filledCircle);
		toolbar.add(line);
		toolbar.add(circle);
		toolbar.add(filledCircle);
		flp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				line.setText(flp.getString("line"));
				circle.setText(flp.getString("circle"));
				filledCircle.setText(flp.getString("filledCircle"));
			}
		});
	}
	/**
	 * Creates main menu bar and adds menus.
	 */
	private void createMenus() {
		JMenuBar mainMenu = new JMenuBar();
		final JMenu fileMenu = new JMenu(flp.getString("file"));
		final JMenu language = new JMenu(flp.getString("language"));
		fileMenu.add(new JMenuItem(openAction));
		fileMenu.add(new JMenuItem(saveAction));
		fileMenu.add(new JMenuItem(saveAsAction));
		fileMenu.add(new JMenuItem(exportAction));
		fileMenu.add(new JMenuItem(exitAction));
		language.add(new JMenuItem(changeLanguageHR));
		language.add(new JMenuItem(changeLanguageEN));
		mainMenu.add(fileMenu);
		mainMenu.add(language);
		this.setJMenuBar(mainMenu);

		flp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				fileMenu.setText(flp.getString("file"));
				language.setText(flp.getString("language"));

			}
		});
	}
	/**
	 * Action which opens file.
	 */
	private final LocalizableAction openAction = new LocalizableAction("open", flp) {
		/**
		 * Default serial version.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JVDraw file", "jvd");
			fc.setFileFilter(filter);
			if (fc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if (!isJVDFile(fileName.getName())) {
				JOptionPane
						.showMessageDialog(JVDraw.this, "Pogrešna ekstenzija", "Pogreška", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(JVDraw.this, "Datoteka " + fileName.getAbsolutePath() + " ne postoji!",
						"Pogreška", JOptionPane.ERROR_MESSAGE);
				return;
			}
			byte[] okteti;
			try {
				okteti = Files.readAllBytes(filePath);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(JVDraw.this,
						"Pogreška prilikom čitanja datoteke " + fileName.getAbsolutePath() + ".", "PogreĹˇka",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			String tekst = new String(okteti, StandardCharsets.UTF_8);
			String[] objects = tekst.split("\n");
			drawingModel.clear();
			for (int i = 0; i < objects.length; i++) {
				try {
					drawingModel.add(getObjectFromString(objects[i]));
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(JVDraw.this,
							"Pogreška prilikom čitanja datoteke " + fileName.getAbsolutePath() + ".", "Pogreška",
							JOptionPane.ERROR_MESSAGE);
					break;
				}
			}

		}

		private GeometricalObject getObjectFromString(String string) {
			string = string.trim();
			String[] components = string.split(" ");
			if (components[0].compareToIgnoreCase("line") == 0) {
				Line line = new Line(Integer.parseInt(components[1]), Integer.parseInt(components[2]),
						Integer.parseInt(components[3]), Integer.parseInt(components[4]));
				Color color = new Color(Integer.parseInt(components[5]), Integer.parseInt(components[6]),
						Integer.parseInt(components[7]));
				line.setForeground(color);
				return line;
			}
			if (components[0].compareToIgnoreCase("circle") == 0) {
				Circle circle = new Circle(Integer.parseInt(components[1]), Integer.parseInt(components[2]),
						Double.parseDouble(components[3]));
				Color color = new Color(Integer.parseInt(components[4]), Integer.parseInt(components[5]),
						Integer.parseInt(components[6]));
				circle.setForeground(color);
				return circle;
			}
			if (components[0].compareToIgnoreCase("fcircle") == 0) {
				FilledCircle circle = new FilledCircle(Integer.parseInt(components[1]),
						Integer.parseInt(components[2]), Double.parseDouble(components[3]));
				Color foreground = new Color(Integer.parseInt(components[4]), Integer.parseInt(components[5]),
						Integer.parseInt(components[6]));
				Color background = new Color(Integer.parseInt(components[7]), Integer.parseInt(components[8]),
						Integer.parseInt(components[9]));
				circle.setBackground(background);
				circle.setForeground(foreground);
				return circle;
			}
			throw new IllegalArgumentException();
		}

		private boolean isJVDFile(String name) {
			int index = name.lastIndexOf(".");
			if (index == -1) {
				return false;
			}
			if (name.substring(index + 1).compareToIgnoreCase("jvd") == 0) {
				return true;
			}
			return false;
		}
	};
	/**
	 * Action saves document if it is changed.
	 */
	private final LocalizableAction saveAction = new LocalizableAction("save", flp) {

		/**
		 * Default serial version.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			if (openedFilePath == null) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Save document");
				if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JVDraw.this, "Ništa nije snimljeno.", "Upozorenje",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (jfc.getSelectedFile() == null) {
					return;
				}
				openedFilePath = jfc.getSelectedFile().toPath();
			}
			byte[] podatci = exportJVDFile();
			try {
				Files.write(openedFilePath, podatci);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(JVDraw.this, "Pogreška prilikom zapisivanja datoteke "
						+ openedFilePath.toFile().getAbsolutePath()
						+ ".\nPaĹľnja: nije jasno u kojem je stanju datoteka na disku!", "Pogreška",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(JVDraw.this, "Datoteka je snimljena.", "Informacija",
					JOptionPane.INFORMATION_MESSAGE);
			stateChanged.setChanged(false);
		}
	};
	/**
	 * Action saves document as.
	 */
	private final LocalizableAction saveAsAction = new LocalizableAction("saveas", flp) {
		/**
		 * Default serial version.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save document");
			if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JVDraw.this, "Ništa nije snimljeno.", "Upozorenje",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			openedFilePath = jfc.getSelectedFile().toPath();
			byte[] podatci = exportJVDFile();
			try {
				Files.write(openedFilePath, podatci);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(JVDraw.this, "Pogreška prilikom zapisivanja datoteke "
						+ openedFilePath.toFile().getAbsolutePath()
						+ ".\nPaĹľnja: nije jasno u kojem je stanju datoteka na disku!", "Pogreška",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(JVDraw.this, "Datoteka je snimljena.", "Informacija",
					JOptionPane.INFORMATION_MESSAGE);
			stateChanged.setChanged(false);

		}
	};
	/**
	 * Action exports canvas as png, gif, jpg format picture.
	 */
	private final LocalizableAction exportAction = new LocalizableAction("export", flp) {

		/**
		 * Default serial version.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter("JPEG image", "jpg");
			FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG image", "png");
			FileNameExtensionFilter gifFilter = new FileNameExtensionFilter("GIF image", "png");
			jfc.setFileFilter(jpgFilter);
			jfc.setFileFilter(pngFilter);
			jfc.setFileFilter(gifFilter);
			jfc.showSaveDialog(JVDraw.this);
			if (jfc.getSelectedFile() == null) {
				return;
			}
			File fileSelected = jfc.getSelectedFile();
			String extension = checkExtension(fileSelected.getName());
			if (extension == null) {
				JOptionPane
						.showMessageDialog(JVDraw.this, "Pogrešna ekstenzija", "Pogreška", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Rectangle canvasRect = drawingModel.getBoundingBox(canvas.getBounds());
			BufferedImage image = new BufferedImage(canvasRect.width, canvasRect.height, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g = image.createGraphics();
			for (int i = 0; i < drawingModel.getSize(); i++) {
				drawingModel.getObject(i).paintComponent(g);
			}
			g.dispose();
			File file = jfc.getSelectedFile();
			try {
				ImageIO.write(image, extension, file);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(JVDraw.this, "Greška prilikom izvoza!", "Pogreška",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(JVDraw.this, "Datoteka uspješno izvezena!", "Uspjeh!",
					JOptionPane.INFORMATION_MESSAGE);
		}
		/**
		 * Checks extension of image.
		 * 
		 * @param name
		 *            image name.
		 * @return extension of image or null if extension isn't supported.
		 */
		private String checkExtension(String name) {
			String[] allowed = new String[]{"jpg", "gif", "png"};
			int index = name.lastIndexOf(".");
			if (index == -1) {
				return null;
			}
			String temp = name.substring(index + 1);
			for (int i = 0; i < allowed.length; i++) {
				if (temp.compareTo(allowed[i]) == 0) {
					return allowed[i];
				}
			}
			return null;
		}
	};
	/**
	 * Exits JVDraw.
	 */
	private final Action exitAction = new LocalizableAction("exit", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			dispatchEvent(new WindowEvent(JVDraw.this, WindowEvent.WINDOW_CLOSING));
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
	 * Action which is performed after
	 */

	/**
	 * Main method sets JVDrawFrame visible.
	 * 
	 * @param args
	 *            non required
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				LocalizationProvider.getInstance().setLanguage("hr");;
				new JVDraw().setVisible(true);
			}
		});
	}
	/**
	 * Method creates byte array for export while saving as JVD file.
	 * 
	 * @return byte array representing content of JVD file
	 */
	protected byte[] exportJVDFile() {
		StringBuffer allLines = new StringBuffer();
		for (int i = 0, size = drawingModel.getSize(); i < size; i++) {
			allLines.append(drawingModel.getObject(i) + "\n");
		}
		return allLines.toString().getBytes(StandardCharsets.UTF_8);
	}
}
