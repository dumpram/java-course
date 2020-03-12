package hr.fer.zemris.java.custom.collections;
/**
 * ObjectStack
 * <p>
 * Implementation of classic stack using ABICollection.
 * </p>
 * @author Ivan Pavić
 * @version 1.0
 */
public class ObjectStack {
	private ArrayBackedIndexedCollection stack = new ArrayBackedIndexedCollection();
	
	/**
	 * isEmpty
	 * @return Returns true if stack is empty. False otherwise.
	 */
	public boolean isEmpty() {
		return stack.isEmpty();				
	}
	
	/**
	 * size
	 * @return Returns size
	 */
	public int size() {
		return stack.size();
	}
	
	/**
	 * push
	 * <p>
	 * Pushes value on stack.
	 * </p>
	 * @param value
	 */
	public void push(Object value) {
		stack.add(value);
	}
	
	/**
	 * pop 
	 * @return Returns Object from the top of the stack. (removes it from stack)
	 */
	public Object pop() {
		Object vracam;
		if (stack.isEmpty() == true) {
			throw new EmptyStackException("The stack is empty!");
		}
		vracam = stack.get(stack.size()-1);
		
		stack.remove(stack.size()-1);
		
		return vracam;
	}
	
	/**
	 * peek
	 * @return Returns Object from the top of the stack. (stack is unchanged)
	 */
	public Object peek() {
		if (stack.isEmpty() == true) {
			throw new EmptyStackException("The stack is empty!");
		}
		return stack.get(stack.size()-1);			
	}
	/**
	 * clear
	 * Removes elements from stack.
	 */
	public void clear() {
		stack.clear();
	}
}
