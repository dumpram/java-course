package hr.fer.zemris.web.radionice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Opcije radionice modelirane su razredom {@link Opcija}. Opciju čine dvije
 * vrijednosti: ID i vrijednost.
 * 
 * @author Ivan Pavić
 * 
 */
public class Opcija {
	/**
	 * Identifikator opcije.
	 */
	private final String id;
	/**
	 * Vrijednost opcije.
	 */
	private String vrijednost;
	/**
	 * Konstruktor za razred {@link Opcija}.
	 * 
	 * @param id
	 *            identifikator
	 * @param vrijednost
	 *            vrijednost opcije
	 */
	public Opcija(String id, String vrijednost) {
		provjeriId(id);
		this.id = id;
		this.vrijednost = vrijednost;
	}
	public Opcija(long i, String vrijednost) {
		id = Long.toString(i);
		this.vrijednost = vrijednost;
	}
	/**
	 * Provjerava da li se id može parsirati u long tip.
	 * 
	 * @param id2
	 *            string koji predstavlja id
	 */
	protected static void provjeriId(String id2) {
		try {
			Long.parseLong(id2);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("ID mora biti long!");
		}
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @return the vrijednost
	 */
	public String getVrijednost() {
		return vrijednost;
	}
	/**
	 * @param vrijednost
	 *            the vrijednost to set
	 */
	public void setVrijednost(String vrijednost) {
		this.vrijednost = vrijednost;
	}
	/**
	 * Statička metoda koja vraća mapu opcija učitanu iz danih parametara baze
	 * podataka.
	 * 
	 * @param direktorij
	 *            korijen baze
	 * @param opcija
	 *            ime datoteke s traženom opcijom
	 * @return mapa opcija
	 */
	public static Map<String, String> ucitaj(String direktorij, String opcija) {
		List<String> lines;
		try {
			lines = Files.readAllLines(Datoteke.napraviPath(direktorij, opcija), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new InconsistentDatabaseException("Nema tražene datoteke!");
		}
		Map<String, String> map = new LinkedHashMap<>();
		for (String line : lines) {
			String[] parts = createParts(line);
			Opcija nova = new Opcija(parts[0], parts[1]);
			map.put(nova.getId(), nova.getVrijednost());
		}
		return map;
	}
	/**
	 * Rastavlja jednu liniju iz tekstualne datoteke na dijelove.
	 * 
	 * @param line
	 *            dana linija teksta
	 * @return 2 dijela teksta, id i vrijednost opcije
	 * @throws IllegalArgumentException
	 *             ako id nije parsabilan u long
	 */
	private static String[] createParts(String line) {
		String[] parts = line.split("\t");
		if (parts.length != 2) {
			throw new IllegalArgumentException("Pogrešna opcija!");
		}
		return parts;
	}
	/**
	 * Metoda snima mapu opcija u predanu tekstualnu datoteku baze podataka.
	 * 
	 * @param mapaOpcija
	 *            mapa opcija
	 * @param direktorij
	 *            direktorij baze
	 * @param datoteka
	 *            datoteka opcije u bazi
	 */
	public static void snimi(Map<String, String> mapaOpcija, String direktorij, String datoteka) {
		List<String> lines = new ArrayList<>();
		for (String entry : mapaOpcija.keySet()) {
			provjeriId(entry);
			lines.add(entry + "\t" + mapaOpcija.get(entry));
		}
		Path path = Datoteke.napraviPath(direktorij, datoteka);
		try {
			Files.write(path, lines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new InconsistentDatabaseException("Greška prilikom spremanja opcija!");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((vrijednost == null) ? 0 : vrijednost.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Opcija other = (Opcija) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (vrijednost == null) {
			if (other.vrijednost != null) {
				return false;
			}
		} else if (!vrijednost.equals(other.vrijednost)) {
			return false;
		}
		return true;
	}

}
