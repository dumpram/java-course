package hr.fer.zemris.java.custom.collections;

public class Example1 {

	public static void main(String[] args) {
		ArrayBackedIndexedCollection col = new ArrayBackedIndexedCollection(2);
		col.add(new Integer(20));
		col.add("New York");
		col.add("San Francisco"); // here the internal array is reallocated
		System.out.println(col.contains("New York")); // writes: true
		col.remove(1); // removes "New York"; shifts "San Francisco" to position 1
		System.out.println(col.get(1)); // writes: "San Francisco"
		System.out.println(col.size()); // writes: 2


	}
}
