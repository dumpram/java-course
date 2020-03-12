package hr.fer.zemris.java.hw11.jvdraw;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
/**
 * Represents central component of JVDraw. It is derived from {@link JComponent}
 * and implements interface {@link DrawingModelListener}.
 * 
 * @author Ivan Pavić
 * 
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Private reference to DrawingModel.
	 */
	private final DrawingModel model;
	/**
	 * Private reference to foreground.
	 */
	private final JColorArea foreground;
	/**
	 * Private reference to background.
	 */
	private final JColorArea background;
	/**
	 * Private reference to shape shifting buttons.
	 */
	private final ButtonGroup shapeShifter;
	/**
	 * Temporary geometric object.
	 */
	private GeometricalObject tempObject = null;

	/**
	 * Constructor for canvas accepts DrawingModel as argument.
	 * 
	 * @param model
	 *            given DrawingModel
	 */
	public JDrawingCanvas(final DrawingModel model, final JColorArea foreground1, final JColorArea background1,
			final ButtonGroup shapeShifter1) {
		this.model = model;
		this.foreground = foreground1;
		this.background = background1;
		this.shapeShifter = shapeShifter1;
		final AtomicBoolean start = new AtomicBoolean(false);
		final AtomicInteger startX = new AtomicInteger();
		final AtomicInteger startY = new AtomicInteger();
		final Double radius = new Double(0);
		final AtomicInteger endX = new AtomicInteger();
		final AtomicInteger endY = new AtomicInteger();
		final Line temp = new Line(startX.get(), startY.get(), endX.get(), endY.get());
		final Circle temp1 = new Circle(startX.get(), startY.get(), radius.doubleValue());
		final FilledCircle temp2 = new FilledCircle(startX.get(), startY.get(), radius.doubleValue());

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (shapeShifter.getSelection().getActionCommand().compareTo("line") == 0) {
					if (e.getClickCount() == 1) {
						if (!start.get()) {
							start.set(true);
							startX.set(e.getX());
							startY.set(e.getY());
							temp.setStartX(startX.get());
							temp.setStartY(startY.get());
							temp.setEndX(startX.get());
							temp.setEndY(startY.get());
							temp.setForeground(foreground.getCurrentColor());
							tempObject = temp;
						} else {
							Line line = new Line(temp.getStartX(), temp.getStartY(), temp.getEndX(), temp.getEndY());
							line.setForeground(temp.getForeground());
							model.add(line);
							start.set(false);
							tempObject = null;
						}
					}
				}
			}
		});

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				if (shapeShifter.getSelection().getActionCommand().compareTo("line") == 0) {
					if (start.get()) {
						temp.setEndX(e.getX());
						temp.setEndY(e.getY());
						repaint();
					}
				}
			}
			@Override
			public void mouseDragged(MouseEvent e) {

			}

		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (shapeShifter.getSelection().getActionCommand().compareTo("circle") == 0) {
					if (e.getClickCount() == 1) {
						if (!start.get()) {
							start.set(true);
							startX.set(e.getX());
							startY.set(e.getY());
							temp1.setCenterX(startX.get());
							temp1.setCenterY(startY.get());
							temp1.setRadius(0);
							temp1.setForeground(foreground.getCurrentColor());
							tempObject = temp1;
						} else {
							Circle circle = new Circle(temp1.getCenterX(), temp1.getCenterY(), temp1.getRadius());
							circle.setForeground(temp1.getForeground());
							model.add(circle);
							start.set(false);
							tempObject = null;
						}
					}
				}
			}
		});

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				if (shapeShifter.getSelection().getActionCommand().compareTo("circle") == 0) {
					if (start.get()) {
						temp1.setRadius((int) Math.sqrt(Math.pow(e.getX() - startX.get(), 2)
								+ Math.pow(e.getY() - startY.get(), 2)));
						repaint();
					}
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {

			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (shapeShifter.getSelection().getActionCommand().compareTo("fcircle") == 0) {
					if (e.getClickCount() == 1) {
						if (!start.get()) {
							start.set(true);
							startX.set(e.getX());
							startY.set(e.getY());
							temp2.setCenterX(startX.get());
							temp2.setCenterY(startY.get());
							temp2.setRadius(0);
							temp2.setForeground(foreground.getCurrentColor());
							temp2.setBackground(background.getCurrentColor());
							tempObject = temp2;
						} else {
							FilledCircle circle = new FilledCircle(temp2.getCenterX(), temp2.getCenterY(),
									temp2.getRadius());
							circle.setForeground(temp2.getForeground());
							circle.setBackground(temp2.getBackground());
							model.add(circle);
							start.set(false);
							tempObject = null;
						}
					}
				}
			}
		});

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				if (shapeShifter.getSelection().getActionCommand().compareTo("fcircle") == 0) {
					if (start.get()) {
						temp2.setRadius((int) Math.sqrt(Math.pow(e.getX() - startX.get(), 2)
								+ Math.pow(e.getY() - startY.get(), 2)));
						repaint();
					}
				}
			}
			@Override
			public void mouseDragged(MouseEvent e) {

			}
		});

	}
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		for (int i = 0; i < model.getSize(); i++) {
			GeometricalObject current = model.getObject(i);
			current.paintComponent(g);
		}
		if (tempObject != null) {
			tempObject.paintComponent(g);
		}
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}
}
