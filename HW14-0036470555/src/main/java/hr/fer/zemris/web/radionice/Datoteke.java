package hr.fer.zemris.web.radionice;

import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * Klasa služi za rad s datotekama.
 * 
 * @author Ivan Pavić
 * 
 */
public class Datoteke {
	/**
	 * Radi path od direktorija i predane datoteke.
	 * 
	 * @param direktorij
	 *            predani direktorij
	 * @param datoteka
	 *            predana datoteka
	 * @return {@link Path}
	 */
	public static Path napraviPath(String direktorij, String datoteka) {
		Path path = Paths.get(direktorij);
		Path forExport = path.resolve(Paths.get(datoteka));
		return forExport;
	}

}
