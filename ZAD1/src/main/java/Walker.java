import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class Walker implements FileVisitor<Path> {

	private int n;
	private long size;

	private boolean ispitajEkstenziju(File file) {
		String name = file.getName();
		if (!name.contains(".")) {
			return false;
		}
		String ekst = name.substring(name.lastIndexOf(".") + 1);
		if (ekst.equalsIgnoreCase("txt")) {
			return true;
		}
		return false;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		File file1 = file.toFile();
		if (ispitajEkstenziju(file1)) {
			System.out.println(file1.getCanonicalPath() + " " + file1.length());
			n++;
			setSize(getSize() + file1.length());
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	/**
	 * @return the n
	 */
	public int getN() {
		return n;
	}

	/**
	 * @param n
	 *            the n to set
	 */
	public void setN(int n) {
		this.n = n;
	}

	/**
	 * @return the size
	 */
	public long getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(long size) {
		this.size = size;
	}

}
