package hr.fer.zemris.web.radionice;

import java.util.Set;

/**
 * Klasa izgleda kao radionica ali ona to nije.
 * 
 * @author Ivan Pavić
 * 
 */
public class RadionicaDekorator {
	/**
	 * Identifikator radionice.
	 */
	private final Long id;
	/**
	 * Naziv radionice.
	 */
	private final String naziv;
	/**
	 * Datum održavanje radionice.
	 */
	private final String datum;
	/**
	 * Oprema u radionici.
	 */
	private final Set<Opcija> oprema;
	/**
	 * Trajanje radionice.
	 */
	private final Opcija trajanje;
	/**
	 * Radionica je namijenja dotičnoj publici.
	 */
	private final Set<Opcija> publika;
	/**
	 * Maksimalan broj polaznika.
	 */
	private final Integer maksPolaznika;
	/**
	 * Email voditelja.
	 */
	private final String email;
	/**
	 * Dopunski tekst.
	 */
	private final String dopuna;
	/**
	 * Konstruktor za razred {@link RadionicaDekorator}.
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
	public RadionicaDekorator(Long id, String naziv, String datum, Set<Opcija> oprema, Opcija trajanje,
			Set<Opcija> publika, Integer maksPolaznika, String email, String dopuna) {
		super();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datum == null) ? 0 : datum.hashCode());
		result = prime * result + ((dopuna == null) ? 0 : dopuna.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((maksPolaznika == null) ? 0 : maksPolaznika.hashCode());
		result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
		result = prime * result + ((oprema == null) ? 0 : oprema.hashCode());
		result = prime * result + ((publika == null) ? 0 : publika.hashCode());
		result = prime * result + ((trajanje == null) ? 0 : trajanje.hashCode());
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
		RadionicaDekorator other = (RadionicaDekorator) obj;
		if (datum == null) {
			if (other.datum != null) {
				return false;
			}
		} else if (!datum.equals(other.datum)) {
			return false;
		}
		if (dopuna == null) {
			if (other.dopuna != null) {
				return false;
			}
		} else if (!dopuna.equals(other.dopuna)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (maksPolaznika == null) {
			if (other.maksPolaznika != null) {
				return false;
			}
		} else if (!maksPolaznika.equals(other.maksPolaznika)) {
			return false;
		}
		if (naziv == null) {
			if (other.naziv != null) {
				return false;
			}
		} else if (!naziv.equals(other.naziv)) {
			return false;
		}
		if (oprema == null) {
			if (other.oprema != null) {
				return false;
			}
		} else if (!oprema.equals(other.oprema)) {
			return false;
		}
		if (publika == null) {
			if (other.publika != null) {
				return false;
			}
		} else if (!publika.equals(other.publika)) {
			return false;
		}
		if (trajanje == null) {
			if (other.trajanje != null) {
				return false;
			}
		} else if (!trajanje.equals(other.trajanje)) {
			return false;
		}
		return true;
	}

}
