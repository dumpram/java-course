package hr.fer.zemris.java.tecaj_14.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * Komentar na blogu.
 * 
 * @author Ivan Pavić
 * 
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {
	/**
	 * Id komentara.
	 */
	private Long id;
	/**
	 * BlogEntry u kojem je komentar.
	 */
	private BlogEntry blogEntry;
	/**
	 * Autor komentara.
	 */
	private BlogUser author;
	/**
	 * Poruka komentara.
	 */
	private String message;
	/**
	 * Datum postavljanja komentara.
	 */
	private Date postedOn;
	/**
	 * @return id komentara
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	/**
	 * Postavlja komentara na zadanu vrijednost.
	 * 
	 * @param id
	 *            zadana vrijednost.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return {@link BlogEntry} u kojem je komentar
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	/**
	 * Postavlja blog entry na zadanu instancu.
	 * 
	 * @param blogEntry
	 *            {@link BlogEntry} za postavljanje
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * @return the author
	 */
	@ManyToOne
	public BlogUser getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(BlogUser author) {
		this.author = author;
	}
	/**
	 * @return tekst komentara.
	 */
	@Column(length = 4096, nullable = false)
	public String getMessage() {
		return message;
	}
	/**
	 * Postavlja teskt komentara.
	 * 
	 * @param message
	 *            zadani tekst
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * Datum postavljanja komentara.
	 * 
	 * @return vraća datum
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getPostedOn() {
		return postedOn;
	}
	/**
	 * Postavlja vrijeme komentara.
	 * 
	 * @param postedOn
	 *            {@link Date} za postaviti
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
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
		BlogComment other = (BlogComment) obj;
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