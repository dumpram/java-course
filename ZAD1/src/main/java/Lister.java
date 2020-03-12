import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Lister {

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Ocekivao jedan argument!");
			System.exit(0);
		}
		Walker walker = new Walker();
		Files.walkFileTree(Paths.get(args[0]), walker);

		if (walker.getN() == 0) {
			System.out.println("Nema nijedne tekstualne datoteke");
			return;
		}
		System.out.println("Postoji " + walker.getN() + " datoteka!");
		System.out.println("Ukupna veličina datoteka je: " + walker.getSize());
	}

}
