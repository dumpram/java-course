package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * Demo for testing CalcLayout.
 * 
 * @author Ivan Pavić
 * 
 */
public class CalcLayoutDemo {
    public static void addComponentsToPane(Container p) {
	p.setLayout(new CalcLayout(10));
	JLabel label = new JLabel("label");
	label.setOpaque(true);
	label.setBackground(Color.GREEN);
	p.add(new JButton("x"), "1,1");
	p.add(new JButton("y"), "2,3");
	p.add(new JButton("z"), "2,7");
	p.add(new JButton("w"), "4,2");
	p.add(new JButton("a"), "4,5");
	p.add(new JButton("b"), "4,7");
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
	// Create and set up the window.
	JFrame frame = new JFrame("CustomLayoutDemo");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// Set up the content pane.
	addComponentsToPane(frame.getContentPane());

	// Display the window.
	frame.pack();
	frame.setVisible(true);
    }

    public static void main(String[] args) {
	// Schedule a job for the event-dispatching thread:
	// creating and showing this application's GUI.
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		createAndShowGUI();
	    }
	});
    }
}
