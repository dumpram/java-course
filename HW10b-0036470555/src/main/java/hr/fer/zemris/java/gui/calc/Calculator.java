package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.expressions.ArithmeticStatement;
import hr.fer.zemris.java.gui.calc.expressions.EqualStatement;
import hr.fer.zemris.java.gui.calc.expressions.ExpressionStatement;
import hr.fer.zemris.java.gui.calc.input.DecimalInput;
import hr.fer.zemris.java.gui.calc.input.InputOperation;
import hr.fer.zemris.java.gui.calc.input.NumberInput;
import hr.fer.zemris.java.gui.calc.oneop.CosStatement;
import hr.fer.zemris.java.gui.calc.oneop.CtgStatement;
import hr.fer.zemris.java.gui.calc.oneop.FlipStatement;
import hr.fer.zemris.java.gui.calc.oneop.LnStatement;
import hr.fer.zemris.java.gui.calc.oneop.LogStatement;
import hr.fer.zemris.java.gui.calc.oneop.OneOperatorStatement;
import hr.fer.zemris.java.gui.calc.oneop.SinStatement;
import hr.fer.zemris.java.gui.calc.oneop.TanStatement;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * Calculator class extends JFrame. It is simple calculator with 31 fields in
 * {@link CalcLayout}. Functions are simple as in basic windows calculator.
 * 
 * @author Ivan Pavić
 * 
 */
public class Calculator extends JFrame {

    /**
     * Serial version of JFrame.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Map of buttons.
     */
    Map<RCPosition, JButton> buttonMap = new HashMap<>();
    /**
     * Output label for calculator.
     */
    final JLabel output = new JLabel("0.0");
    /**
     * String expression is container from which all commands are parsed and
     * executed.
     */
    String expression = output.getText();
    /**
     * Invert check box if checked turns button to
     */
    final JCheckBox invert = new JCheckBox("Inv", false);
    /**
     * Flag to check if output is new result.
     */
    boolean isNew = true;
    /**
     * Flag to check if current expression was proccesed by equal command.
     */
    boolean isDone = false;
    /**
     * Flag to check if some command is waiting for second operand.
     */
    boolean isWaitingForOperand = false;
    /**
     * Internal calculator stack for purposes of push and pop commands.
     */
    final Stack<String> stack = new Stack<>();

    /**
     * Constructor. Most of the job is delegated to initGUI method.
     */
    public Calculator() {
	initGUI();
    }

    /**
     * This is what some people call monster method. Author deeply regrets that
     * he didn't find a better way to do this due to large number of
     * declarations. Any suggestions would be helpful.
     */
    private void addButtonsToButtonMap() {
	/**
	 * JButton declarations.
	 */
	JButton flip = new JButton("1/x");
	JButton decimal = new JButton(".");
	JButton signChange = new JButton("+/-");
	final JButton sin = new JButton("sin");
	final JButton log = new JButton("log");
	final JButton cos = new JButton("cos");
	final JButton tan = new JButton("tan");
	final JButton ln = new JButton("ln");
	final JButton ctg = new JButton("ctg");
	final JButton pow = new JButton("x^n");
	JButton add = new JButton("+");
	JButton sub = new JButton("-");
	JButton mul = new JButton("*");
	JButton div = new JButton("/");
	JButton equal = new JButton("=");
	JButton reset = new JButton("res");
	JButton clear = new JButton("clr");
	JButton push = new JButton("push");
	JButton pop = new JButton("pop");
	/**
	 * Special button reset action listener.
	 */
	reset.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		reset();
	    }
	});

	/**
	 * Special button clear action listener.
	 */
	clear.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		EqualStatement equalStatement = new EqualStatement();
		String tempExpression = expression;
		expression = equalStatement.changeSecondArgument(expression);
		if (expression.compareTo(tempExpression) != 0) {
		    output.setText("0.0");
		}
		isNew = true;
	    }
	});

	/**
	 * Stack button push action listener.
	 */
	push.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		stack.push(output.getText());
	    }
	});

	/**
	 * Stack button pop action listener.
	 */
	pop.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (stack.isEmpty()) {
		    reset();
		    output.setText("Dear user, stack is empty. Continue with your work!");
		    return;
		}
		String text = stack.pop();
		if (!isDone && isWaitingForOperand) {
		    expression += text;
		} else {
		    EqualStatement statement = new EqualStatement();
		    expression = statement.changeArgument(expression, text,
			    output.getText());
		}
		output.setText(text);
	    }
	});

	/**
	 * This is the part where generics listeners are added.
	 */
	decimal.addActionListener(createInputListener(new DecimalInput(), "."));
	signChange.addActionListener(createSignChangeListener());
	sin.addActionListener(createOneOperatorListener(new SinStatement()));
	log.addActionListener(createOneOperatorListener(new LogStatement()));
	cos.addActionListener(createOneOperatorListener(new CosStatement()));
	tan.addActionListener(createOneOperatorListener(new TanStatement()));
	ln.addActionListener(createOneOperatorListener(new LnStatement()));
	ctg.addActionListener(createOneOperatorListener(new CtgStatement()));
	flip.addActionListener(createOneOperatorListener(new FlipStatement()));
	pow.addActionListener(createExpressionListener(
		new ArithmeticStatement(), "^"));
	add.addActionListener(createExpressionListener(
		new ArithmeticStatement(), "+"));
	sub.addActionListener(createExpressionListener(
		new ArithmeticStatement(), "-"));
	mul.addActionListener(createExpressionListener(
		new ArithmeticStatement(), "*"));
	div.addActionListener(createExpressionListener(
		new ArithmeticStatement(), "/"));
	equal.addActionListener(createEqualListener(new EqualStatement()));

	/**
	 * Here buttons are put in button map.
	 */
	buttonMap.put(new RCPosition(5, 4), signChange);
	buttonMap.put(new RCPosition(2, 1), flip);
	buttonMap.put(new RCPosition(2, 2), sin);
	buttonMap.put(new RCPosition(3, 1), log);
	buttonMap.put(new RCPosition(3, 2), cos);
	buttonMap.put(new RCPosition(4, 1), ln);
	buttonMap.put(new RCPosition(4, 2), tan);
	buttonMap.put(new RCPosition(5, 1), pow);
	buttonMap.put(new RCPosition(5, 2), ctg);
	buttonMap.put(new RCPosition(5, 5), decimal);
	buttonMap.put(new RCPosition(5, 6), add);
	buttonMap.put(new RCPosition(4, 6), sub);
	buttonMap.put(new RCPosition(3, 6), mul);
	buttonMap.put(new RCPosition(2, 6), div);
	buttonMap.put(new RCPosition(1, 6), equal);
	buttonMap.put(new RCPosition(2, 7), reset);
	buttonMap.put(new RCPosition(1, 7), clear);
	buttonMap.put(new RCPosition(3, 7), push);
	buttonMap.put(new RCPosition(4, 7), pop);
	/**
	 * Special category of buttons are numbers, so here they are put in the
	 * map.
	 */
	List<JButton> numbers = addListenersToNumbers();
	buttonMap.put(new RCPosition(5, 3), numbers.get(0));
	buttonMap.put(new RCPosition(4, 3), numbers.get(1));
	buttonMap.put(new RCPosition(4, 4), numbers.get(2));
	buttonMap.put(new RCPosition(4, 5), numbers.get(3));
	buttonMap.put(new RCPosition(3, 3), numbers.get(4));
	buttonMap.put(new RCPosition(3, 4), numbers.get(5));
	buttonMap.put(new RCPosition(3, 5), numbers.get(6));
	buttonMap.put(new RCPosition(2, 3), numbers.get(7));
	buttonMap.put(new RCPosition(2, 4), numbers.get(8));
	buttonMap.put(new RCPosition(2, 5), numbers.get(9));
    }

    /**
     * Method is simple. Returns List of JButtons representing numbers of
     * calculator with added generic input listeners.
     * 
     * @return list of JButton objects
     */
    private List<JButton> addListenersToNumbers() {
	List<JButton> numbers = new ArrayList<>();
	for (int i = 0; i < 10; i++) {
	    numbers.add(i, new JButton(String.valueOf(i)));
	    numbers.get(i).addActionListener(
		    createInputListener(new NumberInput(), String.valueOf(i)));
	}
	return numbers;
    }

    /**
     * Creates EqualsListener. It is designed for extension, for more generic
     * equal statements. Its Job is to create implementation of ActionListener
     * which will be executed when equals button is pressed. Therefore equals
     * statement is given as parameter.
     * 
     * @param equalStatement
     *            given equal statement
     * @return new ActionListener forExport
     */
    private ActionListener createEqualListener(
	    final EqualStatement equalStatement) {
	ActionListener forExport = new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		String tempExpression = equalStatement.execute(expression,
			null, invert.isSelected());
		output.setText(equalStatement.getOutput(expression,
			invert.isSelected()));
		expression = tempExpression;
		isNew = true;
		isWaitingForOperand = false;
		isDone = true;
	    }

	};
	return forExport;
    }

    /**
     * Creates ExpressionListener. Its job is to execute some Expression
     * statement. In this category {@link ArithmeticStatement} are included.
     * Supports operations with two operands such as +,-,/,^.
     * 
     * @param statement
     *            given {@link ExpressionStatement}
     * @param suffix
     *            given operation key (for example + or -)
     * @return new ActionListener
     */
    private ActionListener createExpressionListener(
	    final ExpressionStatement statement, final String suffix) {
	ActionListener forExport = new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		EqualStatement equalStatement = new EqualStatement();
		if (!isDone) {
		    expression = equalStatement.getOutput(expression,
			    invert.isSelected());
		} else {
		    expression = equalStatement.getFirstOperand(expression);
		}
		output.setText(expression);
		expression = statement.execute(expression, suffix,
			invert.isSelected());
		isWaitingForOperand = true;
		isNew = true;
		isDone = false;
	    }
	};
	return forExport;
    }

    /**
     * Creates inputListeners which is basically very simple. It enables
     * changing the output and expression statement. InputOperation
     * implementations are {@link DecimalInput}, {@link NumberInput} etc.
     * 
     * @param input
     *            given InputOperation
     * @param suffix
     *            is part of string which will be added to output after
     *            executing
     * @return new ActionListener
     */
    private ActionListener createInputListener(final InputOperation input,
	    final String suffix) {
	ActionListener forExport = new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		String text = output.getText();
		text = input.execute(text, suffix, isNew);
		if (isNew && !isWaitingForOperand) {
		    expression = text;
		} else if (!isNew) {
		    expression += text.substring(text.length() - 1);
		} else if (isNew && isWaitingForOperand) {
		    expression += text.substring(text.length() - 1);
		}
		output.setText(text);
		isNew = false;
	    }
	};
	return forExport;
    }

    /**
     * Creates signChangeListener. It is used for signChange of current output,
     * idea is to expand this so that you can change certain numbers.
     * 
     * @return new ActionListener
     */
    private ActionListener createSignChangeListener() {
	ActionListener forExport = new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		EqualStatement equalStatement = new EqualStatement();
		expression = equalStatement.changeSign(output.getText(),
			expression);
		output.setText(String.valueOf(-Double.valueOf(output.getText())));
	    }
	};
	return forExport;
    }

    private ActionListener createOneOperatorListener(
	    final OneOperatorStatement statement) {
	ActionListener forExport = new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		EqualStatement equalStatement = new EqualStatement();
		if (!isDone) {
		    expression = equalStatement.getOutput(expression,
			    invert.isSelected());
		} else {
		    expression = equalStatement.getFirstOperand(expression);
		}
		expression = equalStatement.getFirstOperand(expression);
		expression = statement.execute(expression, invert.isSelected());
		output.setText(expression);
		isNew = true;
		isDone = false;
	    }
	};
	return forExport;
    }

    /**
     * Reset calculator. Sets output and expression to zero and other flags to
     * their initial state.
     */
    protected void reset() {
	output.setText("0.0");
	expression = output.getText();
	invert.setSelected(false);
	stack.clear();
	isNew = true;
	isDone = false;
	isWaitingForOperand = false;
    }

    /**
     * Initializes GUI.
     */
    private void initGUI() {
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	addButtonsToButtonMap();
	Container p = getContentPane();
	p.setLayout(new CalcLayout(5));
	configOutput();
	/**
	 * Adding buttons two their position;
	 */
	p.add(output, new RCPosition(1, 1));
	p.add(invert, new RCPosition(5, 7));
	for (int i = 1; i < 6; i++) {
	    for (int j = 1; j < 8; j++) {
		if (!((i == 1 && j == 1) || (i == 1 && j == 2)
			|| (i == 1 && j == 3) || (i == 1 && j == 4) || (i == 1 && j == 5))) {
		    JButton b = buttonMap.get(new RCPosition(i, j));
		    if (b != null) {
			p.add(b, new RCPosition(i, j));
		    }
		}
	    }
	}
	pack();
    }

    /**
     * Configuration of output;
     */
    private void configOutput() {
	output.setBackground(Color.blue);
	output.setForeground(Color.white);
	output.setBorder(BorderFactory.createLineBorder(Color.black));
	output.setOpaque(true);
	output.setHorizontalAlignment(JLabel.RIGHT);
    }

    /**
     * Main method.
     * 
     * @param args
     *            none taken
     */
    public static void main(String[] args) {

	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		new Calculator().setVisible(true);
	    }
	});

    }

}
