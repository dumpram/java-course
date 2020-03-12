package hr.fer.zemris.web.radionice.servlets;

import hr.fer.zemris.web.radionice.Radionica;

import java.util.Date;

/**
 * Razred je dekorator za ispis radionica u servletu listaj.
 * 
 * @author Ivan Pavić
 * 
 */
public class IspisRadionica implements Comparable<IspisRadionica> {
	/**
	 * Naziv radionice.
	 */
	private String naziv;
	/**
	 * Datum radionice.
	 */
	private Date datum;
	/**
	 * ID radionice;
	 */
	private Long id;
	/**
	 * Konstruktor prima radionicu koju treba dekorirati.
	 * 
	 * @param r
	 *            predana radionica
	 */
	public IspisRadionica(Radionica r) {
		naziv = r.getNaziv();
		datum = java.sql.Date.valueOf(r.getDatum());
		id = r.getId();
	}
	@Override
	public int compareTo(IspisRadionica o) {
		if (datum.compareTo(o.datum) == 0) {
			return naziv.compareTo(o.naziv);
		}
		return datum.compareTo(o.datum);
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
	public Date getDatum() {
		return datum;
	}
	/**
	 * @param datum
	 *            the datum to set
	 */
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	/**
	 * Dohvaća id.
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * Postavlja id.
	 * 
	 * @param id
	 *            dani id
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
