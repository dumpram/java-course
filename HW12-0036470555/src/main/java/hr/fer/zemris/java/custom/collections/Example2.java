package hr.fer.zemris.java.custom.collections;

public class Example2 {

	public static void main(String[] args) {
		ObjectStack stack = new ObjectStack();
		stack.push("Danas je lijep dan!");
		stack.push(new Integer (20));
		stack.push(new Double (3.123));
		System.out.println("Ispisujem elemente: ");
		while (stack.isEmpty() == false) {
			System.out.println(stack.pop());
		}
	}
}
