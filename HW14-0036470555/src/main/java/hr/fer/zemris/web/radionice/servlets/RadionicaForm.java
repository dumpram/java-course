package hr.fer.zemris.web.radionice.servlets;

import hr.fer.zemris.web.radionice.Opcija;
import hr.fer.zemris.web.radionice.Radionica;
import hr.fer.zemris.web.radionice.RadioniceBaza;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * FormularForm je razred namijenjen za mijenjanje, stvaranje i spremanje
 * radionica u bazu podataka. Na neki način on je dekorator za razred
 * {@link Radionica}
 * 
 * @author Ivan Pavić
 * 
 */
public class RadionicaForm {
	/**
	 * Identifikator radionice.
	 */
	private String id;
	/**
	 * Naziv radionice.
	 */
	private String naziv;
	/**
	 * Datum održavanje radionice.
	 */
	private String datum;
	/**
	 * Oprema u radionici.
	 */
	private Set<Opcija> oprema;
	/**
	 * Trajanje radionice.
	 */
	private Opcija trajanje;
	/**
	 * Radionica je namijenja dotičnoj publici.
	 */
	private Set<Opcija> publika;
	/**
	 * Maksimalan broj polaznika.
	 */
	private String maksPolaznika;
	/**
	 * Email voditelja.
	 */
	private String email;
	/**
	 * Dopunski tekst.
	 */
	private String dopuna;
	/**
	 * Mapa grešaka.
	 */
	Map<String, String> greske = new HashMap<>();
	/**
	 * Polje ID za opremu.
	 */
	private String[] opremaId;
	/**
	 * Polje ID za publiku.
	 */
	private String[] publikaId;
	private String trajanjeId;
	/**
	 * Konstruktor.
	 */
	public RadionicaForm() {
	}
	/**
	 * Dohvaćanje pogrešaka.
	 * 
	 * @param ime
	 *            ime pogreške
	 * @return pogreška
	 */
	public String dohvatiPogresku(String ime) {
		return greske.get(ime);
	}
	/**
	 * Vraća <code>true</code> ako ima pogrešaka, false inače.
	 * 
	 * @return kako je opisano gore
	 */
	public boolean imaPogresaka() {
		return !greske.isEmpty();
	}
	/**
	 * Vraća <code>true</code> ako ima pogreška pod zadanim imenom,
	 * <code>false</code> inače.
	 * 
	 * @param ime
	 *            ime pogreške
	 * @return kako je opisano gore
	 */
	public boolean imaPogresku(String ime) {
		return greske.containsKey(ime);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * Puni formu iz http requesta.
	 * 
	 * @param req
	 *            predani request
	 */
	public void popuniIzHttpRequesta(HttpServletRequest req) {
		this.id = pripremi(req.getParameter("id"));
		this.naziv = pripremi(req.getParameter("naziv"));
		this.datum = pripremi(req.getParameter("datum"));
		this.email = pripremi(req.getParameter("email"));
		this.dopuna = pripremi(req.getParameter("dopuna"));
		this.maksPolaznika = pripremi(req.getParameter("maksPolaznika"));
		trajanjeId = req.getParameter("trajanje");
		opremaId = req.getParameterValues("oprema");
		publikaId = req.getParameterValues("publika");
	}

	/**
	 * @return the naziv
	 */
	public String getNaziv() {
		return naziv;
	}

	/**
	 * @param naziv
	 *            the naziv to set
	 */
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	/**
	 * @return the datum
	 */
	public String getDatum() {
		return datum;
	}

	/**
	 * @param datum
	 *            the datum to set
	 */
	public void setDatum(String datum) {
		this.datum = datum;
	}

	/**
	 * @return the oprema
	 */
	public List<Opcija> getOprema() {
		return new ArrayList<>(oprema);
	}

	/**
	 * @param oprema
	 *            the oprema to set
	 */
	public void setOprema(Set<Opcija> oprema) {
		this.oprema = oprema;
	}

	/**
	 * @return the trajanje
	 */
	public Opcija getTrajanje() {
		return trajanje;
	}

	/**
	 * @param trajanje
	 *            the trajanje to set
	 */
	public void setTrajanje(Opcija trajanje) {
		this.trajanje = trajanje;
	}

	/**
	 * @return the publika
	 */
	public Set<Opcija> getPublika() {
		return publika;
	}

	/**
	 * @param publika
	 *            the publika to set
	 */
	public void setPublika(Set<Opcija> publika) {
		this.publika = publika;
	}

	/**
	 * @return the maksPolaznika
	 */
	public String getMaksPolaznika() {
		return maksPolaznika;
	}

	/**
	 * @param maksPolaznika
	 *            the maksPolaznika to set
	 */
	public void setMaksPolaznika(String maksPolaznika) {
		this.maksPolaznika = maksPolaznika;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the dopuna
	 */
	public String getDopuna() {
		return dopuna;
	}

	/**
	 * @param dopuna
	 *            the dopuna to set
	 */
	public void setDopuna(String dopuna) {
		this.dopuna = dopuna;
	}

	/**
	 * @return the greske
	 */
	public Map<String, String> getGreske() {
		return greske;
	}

	/**
	 * @param greske
	 *            the greske to set
	 */
	public void setGreske(Map<String, String> greske) {
		this.greske = greske;
	}

	public void popuniIzRadionice(Radionica r) {
		if (r.getId() == null) {
			this.id = "";
		} else {
			this.id = r.getId().toString();
		}
		naziv = r.getNaziv();
		datum = r.getDatum();
		email = r.getEmail();
		dopuna = r.getDopuna();
		if (r.getMaksPolaznika() != null) {
			maksPolaznika = r.getMaksPolaznika().toString();
		} else {
			maksPolaznika = "";
		}
		trajanje = r.getTrajanje();
		oprema = r.getOprema();
		publika = r.getPublika();
	}
	/**
	 * Priprema string, trima ga i slično.
	 * 
	 * @param parameter
	 *            predani string
	 * @return trimani string
	 */
	private String pripremi(String parameter) {
		if (parameter == null) {
			return "";
		}
		return parameter.trim();
	}
	/**
	 * Puni kontekst u radionicu. Podaci se preuzimaju iz predane baze podataka.
	 * 
	 * @param r
	 *            predana {@link Radionica}
	 * @param baza
	 */
	public void popuniURadionicu(Radionica r, RadioniceBaza baza) {
		if (this.id.isEmpty()) {
			r.setId(null);
		} else {
			r.setId(Long.valueOf(this.id));
		}
		r.setDatum(datum);
		r.setDopuna(dopunaVrati());
		r.setEmail(email);
		r.setMaksPolaznika(Integer.valueOf(maksPolaznika));
		oprema = new LinkedHashSet<>();
		for (String id : opremaId) {
			oprema.add(new Opcija(id, baza.getOprema().get(id)));
		}
		r.setOprema(oprema);
		publika = new LinkedHashSet<>();
		for (String id : publikaId) {
			publika.add(new Opcija(id, baza.getPublika().get(id)));
		}
		r.setPublika(publika);
		trajanje = new Opcija(trajanjeId, baza.getTrajanje().get(trajanjeId));
		r.setTrajanje(trajanje);
		r.setNaziv(naziv);
	}
	/**
	 * Funkcija provjera jesu li parameteri razreda ispravni.
	 */
	public void validiraj() {
		greske.clear();
		if (!this.id.isEmpty()) {
			try {
				Long.parseLong(this.id);
			} catch (NumberFormatException e) {
				greske.put("id", "Invalidna vrijednost id!");
			}
		}
		if (naziv.isEmpty()) {
			greske.put("naziv", "Naziv je obavezan!");
		}
		if (datum.isEmpty()) {
			greske.put("datum", "Datum održavanja je obavezan!");
		} else {
			try {
				Date.valueOf(datum);
			} catch (Exception e) {
				greske.put("datum", "Pogrešan format datuma!");
			}
		}
		if (maksPolaznika.isEmpty()) {
			greske.put("maksPolaznika", "Potrebno je unijeti maksimalan broj polaznika!");
		} else {
			try {
				int i = Integer.parseInt(maksPolaznika);
				if (i < 10 || i > 50) {
					greske.put("maksPolaznika", "Broj polaznika je između 10 i 50!");
				}
			} catch (NumberFormatException e) {
				greske.put("maksPolaznika", "Maksimalan broj polaznika je broj između 10 i 50!");
			}
		}
		if (email.isEmpty()) {
			greske.put("email", "Email je obavezan!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if (l < 3 || p == -1 || p == 0 || p == l - 1) {
				greske.put("email", "Pogresan format maila!");
			}
		}
		if (opremaId == null) {
			greske.put("opremaId", "Potrebno je odabrati opremu!");
		}
		if (publikaId == null) {
			greske.put("publikaId", "Potrebno je odabrati publiku!");
		}
		if (trajanjeId == null) {
			greske.put("trajanjeId", "Potrebno je odabrati trajanje!");
		}
	}
	/**
	 * Provjerava postoji li odogovarajuća oprema u setu opreme.
	 * 
	 * @param id
	 *            predani identifikator
	 * @return <code>true</code> ako postoji, inače <code>false</code>
	 */
	public boolean postoji(String id) {
		if (id == null || id.isEmpty()) {
			return false;
		}
		if (oprema == null) {
			return false;
		}
		for (Opcija o : oprema) {
			if (o.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Provjerava postoji li odogovarajuća publika u setu publika.
	 * 
	 * @param id
	 *            predani identifikator
	 * @return <code>true</code> ako postoji, inače <code>false</code>
	 */
	public boolean postojiPublika(String id) {
		if (id == null || id.isEmpty()) {
			return false;
		}
		if (publika == null) {
			return false;
		}
		for (Opcija o : publika) {
			if (o.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Dohvaća tekst dopune bez escape sekvenci.
	 * 
	 * @return dopuna bez escape sekvenci
	 */
	public String dohvatiTekstDopune() {
		if (dopuna == null) {
			return "";
		}
		String temp = new String(dopuna);
		for (int i = 0; i < temp.length(); i++) {
			if (temp.charAt(i) == '\\' && temp.charAt(i + 1) == '\\') {
				temp = temp.substring(0, i) + "\\" + temp.substring(i + 2);
			} else if (temp.charAt(i) == '\\' && temp.charAt(i + 1) == 'n') {
				temp = temp.substring(0, i) + "\n" + temp.substring(i + 2);
			} else if (temp.charAt(i) == '\\' && temp.charAt(i + 1) == 't') {
				temp = temp.substring(0, i) + "\t" + temp.substring(i + 2);
			} else if (temp.charAt(i) == '\\' && temp.charAt(i + 1) == 'r') {
				temp = temp.substring(0, i) + "\r" + temp.substring(i + 2);
			} else if (temp.charAt(i) == '\\' && temp.charAt(i + 1) == '"') {
				temp = temp.substring(0, i) + "\"" + temp.substring(i + 2);
			}
		}
		return temp;
	}

	/**
	 * Vraća dopunu u escapani oblik.
	 * 
	 * @return {@link String} dopuna
	 */
	public String dopunaVrati() {
		char[] array = dopuna.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			if (array[i] == '\n') {
				sb.append("\\n");
			} else if (array[i] == '\t') {
				sb.append("\\t");
			} else if (array[i] == '\\') {
				sb.append("\\\\");
			} else if (array[i] == '\r') {
				sb.append("\\r");
			} else {
				sb.append(array[i]);
			}
		}
		return new String(sb);
	}
}
