package hr.fer.zemris.web.radionice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Osnovni razred koji predstavlja bazu podataka ove aplikacije.
 * 
 * @author Ivan Pavić
 * 
 */
public final class RadioniceBaza {
	/**
	 * Ime datoteke opreme.
	 */
	private static final String OPREMA = "oprema.txt";
	/**
	 * Ime datoteke publike.
	 */
	private static final String PUBLIKA = "publika.txt";
	/**
	 * Ime datoteke opreme.
	 */
	private static final String TRAJANJE = "trajanje.txt";
	/**
	 * Ime datoteke s radionicama.
	 */
	private static final String RADIONICE = "radionice.txt";
	/**
	 * Ime datoteke s popisom ključeva opreme radionica.
	 */
	private static final String RADIONICE_OPREMA = "radionice_oprema.txt";
	/**
	 * Ime datoteke s popisom ključeva publike radionica.
	 */
	private static final String RADIONICE_PUBLIKA = "radionice_publika.txt";
	/**
	 * Putanja do direktorija s radionicama.
	 */
	private final String dirPutanja;
	/**
	 * Set radionica.
	 */
	private final Set<Radionica> radionice;
	/**
	 * Mapa opreme.
	 */
	private final Map<String, String> oprema;
	/**
	 * Mapa publike.
	 */
	private final Map<String, String> publika;
	/**
	 * Mapa trajanja.
	 */
	private final Map<String, String> trajanje;
	/**
	 * Privatni konstruktor za {@link RadioniceBaza} prima putanju do
	 * direktorija kao argument. Sprema putanju u privatnu varijablu.
	 * 
	 * @param direktorij
	 *            putanja do direktorija baze
	 */
	private RadioniceBaza(String direktorij) {
		this.dirPutanja = direktorij;
		oprema = Opcija.ucitaj(direktorij, OPREMA);
		publika = Opcija.ucitaj(direktorij, PUBLIKA);
		trajanje = Opcija.ucitaj(direktorij, TRAJANJE);
		radionice = ucitajRadionice(direktorij);
	}
	/**
	 * Statička metoda za učitavanje baze.
	 * 
	 * @param direktorij
	 *            putanja do direktorija baze
	 * @return instanca {@link RadioniceBaza}
	 */
	public static RadioniceBaza ucitaj(String direktorij) {
		RadioniceBaza baza = new RadioniceBaza(direktorij);
		return baza;
	}
	/**
	 * Ucitava radionice iz danog direktorija u set radionica.
	 * 
	 * @param direktorij
	 *            putanja do direktorija
	 * @return set radionica
	 */
	private Set<Radionica> ucitajRadionice(String direktorij) {
		List<String> lines = ucitajDatoteku(RADIONICE);
		Set<Radionica> radionice = new LinkedHashSet<>();
		for (String line : lines) {
			radionice.add(ucitajRadionicu(line));
		}
		return radionice;
	}
	/**
	 * Ucitava radionicu iz dane linije radionica.
	 * 
	 * @param line
	 *            dana linija
	 * @return {@link Radionica}
	 * 
	 */
	private Radionica ucitajRadionicu(String line) {
		String[] parts = line.split("\t");
		provjeriDijeloveRadionice(parts);
		Long id = Long.parseLong(parts[0]);
		String dopuna = (parts.length == 6) ? "" : parts[6];
		Integer maksPolaznika = Integer.parseInt(parts[3]);
		Opcija trajanje = new Opcija(parts[4], this.trajanje.get(parts[4]));
		Set<Opcija> oprema = ucitajSetoveOpcija(this.oprema, RADIONICE_OPREMA, parts[0]);
		Set<Opcija> publika = ucitajSetoveOpcija(this.publika, RADIONICE_PUBLIKA, parts[0]);
		return new Radionica(id, parts[1], parts[2], oprema, trajanje, publika, maksPolaznika, parts[5], dopuna);
	}
	/**
	 * Ucitava setove opcija iz danih parametara odnosno izvora.
	 * 
	 * @param opcija
	 *            opcija za učitati
	 * @param radioniceOpcija
	 *            putanja do radionice
	 * @param id
	 *            identifikator radionice
	 * @return {@link Set} opcija
	 */
	private Set<Opcija> ucitajSetoveOpcija(Map<String, String> opcija, String radioniceOpcija, String id) {
		List<String> lines = ucitajDatoteku(radioniceOpcija);
		Set<Opcija> setOpcija = new LinkedHashSet<>();
		Map<String, List<String>> mapaIDKljuc = new LinkedHashMap<>();
		for (String line : lines) {
			String[] parts = line.split("\t");
			if (parts.length != 2) {
				throw new InconsistentDatabaseException("Greška prilikom čitanja!");
			}
			if (!mapaIDKljuc.containsKey(parts[0])) {
				List<String> kljucevi = new ArrayList<>();
				kljucevi.add(parts[1]);
				mapaIDKljuc.put(parts[0], kljucevi);
			} else {
				mapaIDKljuc.get(parts[0]).add(parts[1]);
			}
		}
		List<String> relevantni = mapaIDKljuc.get(id);
		for (String o : relevantni) {
			setOpcija.add(new Opcija(o, opcija.get(o)));
		}
		return setOpcija;
	}
	/**
	 * Učitava datoteke u listu stringova.
	 * 
	 * @param radioniceOpcija
	 *            putanja do radionice.
	 * @return lista linija iz daoteke
	 */
	private List<String> ucitajDatoteku(String radioniceOpcija) {
		List<String> lista = new ArrayList<>();
		try {
			lista = Files.readAllLines(Datoteke.napraviPath(dirPutanja, radioniceOpcija), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new InconsistentDatabaseException("Greška prilikom učitavanja!");
		}
		return lista;
	}
	/**
	 * Metoda ispituje da li dano polje zadovoljava format radionice iz
	 * datoteke.
	 * 
	 * @param parts
	 *            dani dijelovi
	 */
	private void provjeriDijeloveRadionice(String[] parts) {
		if (parts.length < 6) {
			throw new InconsistentDatabaseException("Neispravan format!");
		}
		Opcija.provjeriId(parts[0]);
		try {
			Integer.parseInt(parts[3]);
		} catch (NumberFormatException e) {
			throw new InconsistentDatabaseException("Neispravan format!");
		}
		if (!trajanje.containsKey(parts[4])) {
			throw new InconsistentDatabaseException("Nepostojeće trajanje!");
		}
		if (!checkEmail(parts[5])) {
			throw new InconsistentDatabaseException("Pogrešan mail!");
		}
	}
	/**
	 * Metoda ispituje ipravnost formata email adrese.
	 * 
	 * @param email
	 *            dana email adresa
	 * @return <code>false</code> ako je neispravna, inače <code>true</code>
	 */
	private boolean checkEmail(String email) {
		int l = email.length();
		int p = email.indexOf('@');
		if (l < 3 || p == -1 || p == 0 || p == l - 1) {
			return false;
		}
		return true;
	}
	/**
	 * Snima predanu baza na postavljeni direktorij dirPutanja.
	 */
	public void snimi() {
		snimi(dirPutanja);
	}
	/**
	 * Snima bazu na putanju na koju pokazuje predani argument direktorij.
	 * 
	 * @param direktorij
	 *            putanja do direktorija gdje će se spremiti baza
	 */
	public void snimi(String direktorij) {
		provjeriIspravnostOpcija();
		Opcija.snimi(oprema, direktorij, OPREMA);
		Opcija.snimi(publika, direktorij, PUBLIKA);
		Opcija.snimi(trajanje, direktorij, TRAJANJE);
		snimiRadionice(direktorij);
		snimiOpremu(direktorij);
		snimiPubliku(direktorij);
	}
	/**
	 * Implementacija je konceptualno slična kao u adresaru s predavanja – ako
	 * id nije postavljen, dodjeljuje mu se novi jedinstveni, inače se ažurira
	 * objekt pod postojećim id-jem. Ako r ima postavljen id ali takav trenutno
	 * u bazi ne postoji, tretirati to kao dodavanje nove radionice s tim
	 * id-jem.
	 * 
	 * @param r
	 *            predana radionica
	 */
	public void snimi(Radionica r) {
		if (r.getId() == null) {
			postaviId(r);
		}
		if (radionice.contains(r)) {
			radionice.remove(r);
		}
		radionice.add(r);
		snimiRadionice(dirPutanja);
		snimiOpremu(dirPutanja);
		snimiPubliku(dirPutanja);
	}
	/**
	 * Postavlja id radionici. Tako da radionica dolazi na kraj liste.
	 * 
	 * @param r
	 *            predana radionica
	 */
	private void postaviId(Radionica r) {
		List<Radionica> list = new ArrayList<>(radionice);
		long id = list.get(list.size() - 1).getId();
		id++;
		r.setId(id);
	}
	/**
	 * Snima opremu u predani direktorij u odgovarajuću datoteku.
	 * 
	 * @param direktorij
	 *            predani direktorij
	 */
	private void snimiOpremu(String direktorij) {
		List<String> lines = new ArrayList<>();
		for (Radionica r : radionice) {
			for (Opcija o : r.getOprema()) {
				lines.add(String.format("%s\t%s", r.getId(), o.getId()));
			}
		}
		Path path = Datoteke.napraviPath(direktorij, RADIONICE_OPREMA);
		try {
			Files.write(path, lines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new InconsistentDatabaseException("Greška prilikom zapisivanja opreme!");
		}
	}
	/**
	 * Snima publiku u predani direktorij u odgovarajuću datoteku.
	 * 
	 * @param direktorij
	 *            predani direktorij
	 */
	private void snimiPubliku(String direktorij) {
		List<String> lines = new ArrayList<>();
		for (Radionica r : radionice) {
			for (Opcija o : r.getPublika()) {
				lines.add(String.format("%s\t%s", r.getId(), o.getId()));
			}
		}
		Path path = Datoteke.napraviPath(direktorij, RADIONICE_PUBLIKA);
		try {
			Files.write(path, lines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new InconsistentDatabaseException("Greška prilikom zapisivanja publike!");
		}
	}
	/**
	 * Snima radionice iz privatne mape radionica u predani direktorij.
	 * 
	 * @param direktorij
	 *            predani direktorij
	 */
	private void snimiRadionice(String direktorij) {
		List<String> podaci = new ArrayList<>();
		for (Radionica r : radionice) {
			String data = Radionica.format(r);
			podaci.add(data);
		}
		Path path = Datoteke.napraviPath(direktorij, RADIONICE);
		try {
			Files.write(path, podaci, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new InconsistentDatabaseException("Neuspješno pisanje radionica");
		}
	}
	/**
	 * Provjerava ispravnost opcija u bazi. U slucaju nekonzistentnosti baca
	 * iznimku.
	 * 
	 * @throws InconsistentDatabaseException
	 *             u slucaju navedenom gore.
	 */
	public void provjeriIspravnostOpcija() {
		Set<Radionica> tempList = ucitajRadionice(dirPutanja);
		Set<RadionicaDekorator> list = new LinkedHashSet<>();
		Set<RadionicaDekorator> list2 = new LinkedHashSet<>();

		for (Radionica r : tempList) {
			list.add(new RadionicaDekorator(r.getId(), r.getNaziv(), r.getDatum(), r.getOprema(), r.getTrajanje(), r
					.getPublika(), r.getMaksPolaznika(), r.getEmail(), r.getDopuna()));
		}
		for (Radionica r : radionice) {
			list2.add(new RadionicaDekorator(r.getId(), r.getNaziv(), r.getDatum(), r.getOprema(), r.getTrajanje(), r
					.getPublika(), r.getMaksPolaznika(), r.getEmail(), r.getDopuna()));
		}
		if (!list.equals(list2)) {
			throw new InconsistentDatabaseException("Nekonzistentnost u bazi podataka!");
		}
	}
	/**
	 * @return the radionice
	 */
	public List<Radionica> getRadionice() {
		return new ArrayList<Radionica>(radionice);
	}
	/**
	 * @return the oprema
	 */
	public Map<String, String> getOprema() {
		return oprema;
	}
	/**
	 * @return the publika
	 */
	public Map<String, String> getPublika() {
		return publika;
	}
	/**
	 * @return the trajanje
	 */
	public Map<String, String> getTrajanje() {
		return trajanje;
	}
	/**
	 * Dohvaća radionicu s traženim ključem.
	 * 
	 * @param id
	 *            ključ
	 * @return {@link Radionica} instanca
	 */
	public Radionica dohvatiRadionicu(long id) {
		for (Radionica i : radionice) {
			if (i.getId() == id) {
				return i;
			}
		}
		return null;
	}
}
