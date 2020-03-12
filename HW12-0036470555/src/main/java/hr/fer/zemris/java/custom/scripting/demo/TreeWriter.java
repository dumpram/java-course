package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
/**
 * Demo for WriterVisitor of SmartScriptParser.
 * 
 * @author Ivan Pavić
 * 
 */
public class TreeWriter {
	/**
	 * WriterVistor implementation. Class implement {@link INodeVisitor}.
	 * 
	 * @author Ivan Pavić
	 * 
	 */
	static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			System.out.println(node.toString());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.println(node.toString());
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.println(node.toString());
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			System.out.println(node.toString());
		}

	}
	/**
	 * Main method accepts path to file which contains document for parsing.
	 * 
	 * @param args
	 *            args[0] path to file for parsing.
	 * @throws IOException
	 *             if error while reading file occurs
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Provide path of document for parsing!");
			System.exit(0);
		}
		String docBody = readFromDisk("osnovni.smscr");
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
	}
	/**
	 * Reads all lines of file.
	 * 
	 * @param fileName
	 *            given file path
	 * @return string representing text file content
	 * @throws IOException
	 *             if error while reading occurs
	 */
	public static String readFromDisk(String fileName) throws IOException {
		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();
		String s = new String(data, "UTF-8");
		return s;
	}
}
