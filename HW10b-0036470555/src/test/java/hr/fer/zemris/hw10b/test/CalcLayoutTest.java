package hr.fer.zemris.hw10b.test;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.junit.Test;

public class CalcLayoutTest {

    @Test(expected = IllegalArgumentException.class)
    public void IlegallPositionsTest() {
	JPanel panel = new JPanel(new CalcLayout());
	JButton test = new JButton("test");
	panel.add(test, new RCPosition("1,2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void IlegallPositions1Test() {
	JPanel panel = new JPanel(new CalcLayout());
	JButton test = new JButton("test");
	panel.add(test, new RCPosition("1,3"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void IlegallPositions2Test() {
	JPanel panel = new JPanel(new CalcLayout());
	JButton test = new JButton("test");
	panel.add(test, new RCPosition("1,4"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void IlegallPositions3Test() {
	JPanel panel = new JPanel(new CalcLayout());
	JButton test = new JButton("test");
	panel.add(test, new RCPosition("1,5"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void IllegalRCPositionConstraintString() {
	@SuppressWarnings("unused")
	RCPosition positon = new RCPosition("12");
    }
}
