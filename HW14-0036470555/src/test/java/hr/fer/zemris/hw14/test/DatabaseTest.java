package hr.fer.zemris.hw14.test;

import static org.junit.Assert.assertTrue;
import hr.fer.zemris.web.radionice.InconsistentDatabaseException;
import hr.fer.zemris.web.radionice.Opcija;
import hr.fer.zemris.web.radionice.Radionica;
import hr.fer.zemris.web.radionice.RadioniceBaza;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import org.junit.Test;

public class DatabaseTest {

	@Test
	public void testFirst() {
		RadioniceBaza baza = RadioniceBaza.ucitaj("baza");
		baza.getOprema();
	}

	@Test
	public void firstOfficalTest() throws IOException {
		RadioniceBaza baza = RadioniceBaza.ucitaj("baza");
		Path d = Files.createTempDirectory("baza1");
		boolean flag = usporediDatoteke(new File("baza"), new File(d.toString()));
		baza.snimi(d.toString());
		Files.walkFileTree(d, deleteVisitor);
		assertTrue(flag);
	}

	@Test
	public void secondOfficalTest() throws IOException {
		RadioniceBaza baza = RadioniceBaza.ucitaj("baza");
		Radionica radionica = baza.getRadionice().get(1);
		radionica.getOprema().add(new Opcija(101, "USB stick"));
		Path d = Files.createTempDirectory("baza1");
		boolean flag = false;
		try {
			baza.snimi(d.toString());
		} catch (InconsistentDatabaseException e) {
			flag = true;
		}
		Files.walkFileTree(d, deleteVisitor);
		assertTrue(flag);
	}

	private boolean usporediDatoteke(File file, File file2) throws IOException {
		File[] prvi = file.listFiles();
		File[] drugi = file.listFiles();
		if (prvi.length != drugi.length) {
			return false;
		}
		for (int i = 0; i < prvi.length; i++) {
			List<String> prviLinije = Files.readAllLines(prvi[i].toPath(), StandardCharsets.UTF_8);
			List<String> drugiLinije = Files.readAllLines(drugi[i].toPath(), StandardCharsets.UTF_8);
			if (!prviLinije.equals(drugiLinije)) {
				return false;
			}
		}
		return true;
	}

	private final FileVisitor<Path> deleteVisitor = new FileVisitor<Path>() {

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			file.toFile().delete();
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			file.toFile().delete();
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			dir.toFile().delete();
			return FileVisitResult.CONTINUE;
		}

	};
}
