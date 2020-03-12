package hr.fer.zemris.web.radionice;

import java.util.Set;

/**
 * Razred modelira zapis o pojedinoj radionici.
 * 
 * @author Ivan Pavić
 * 
 */
public class Radionica {
	/**
	 * Identifikator radionice.
	 */
	private Long id;
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
	private Integer maksPolaznika;
	/**
	 * Email voditelja.
	 */
	private String email;
	/**
	 * Dopunski tekst.
	 */
	private String dopuna;
	/**
	 * Konstruktor za razred {@link Radionica}.
	 * 
	 * @param id
	 *            identifikator
	 * @param naziv
	 *            ime radionice
	 * @param datum
	 *            datum održavanja radionice
	 * @param oprema
	 *            oprema dostupna u radionici
	 * @param trajanje
	 *            trajanje radionice
	 * @param publika
	 *            publika radionice
	 * @param maksPolaznika
	 *            najveći broj polaznika
	 * @param email
	 *            email voditelja
	 * @param dopuna
	 *            teks dopune
	 */
	public Radionica(Long id, String naziv, String datum, Set<Opcija> oprema, Opcija trajanje, Set<Opcija> publika,
			Integer maksPolaznika, String email, String dopuna) {
		this.id = id;
		this.naziv = naziv;
		this.datum = datum;
		this.oprema = oprema;
		this.trajanje = trajanje;
		this.publika = publika;
		this.maksPolaznika = maksPolaznika;
		this.email = email;
		this.dopuna = dopuna;
	}
	/**
	 * Konstruktor.
	 */
	public Radionica() {
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	public Set<Opcija> getOprema() {
		return oprema;
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
	public Integer getMaksPolaznika() {
		return maksPolaznika;
	}
	/**
	 * @param maksPolaznika
	 *            the maksPolaznika to set
	 */
	public void setMaksPolaznika(Integer maksPolaznika) {
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
	 * Radi prikaz podataka radionice u string koji se sprema u tekstualnu
	 * datoteku u bazi podataka.
	 * 
	 * @param r
	 *            radionica
	 * @return {@link String} koji predstavalja radionicu
	 */
	public static String format(Radionica r) {
		return String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s", r.id, r.naziv, r.datum, r.maksPolaznika, r.trajanje.getId(),
				r.email, r.dopuna);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Radionica other = (Radionica) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
