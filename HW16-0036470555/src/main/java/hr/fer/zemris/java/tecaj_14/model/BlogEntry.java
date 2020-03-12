package hr.fer.zemris.java.tecaj_14.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Razred predstavlja unos na blogu.
 * 
 * @author Ivan Pavić
 * 
 */
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
@NamedQueries({@NamedQuery(name = "BlogEntry.upit1", query = "select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when")})
public class BlogEntry {
	/**
	 * Long id.
	 */
	private Long id;
	/**
	 * Lista komentara.
	 */
	private List<BlogComment> comments = new ArrayList<>();
	/**
	 * Datum stvaranja.
	 */
	private Date createdAt;
	/**
	 * Datum modifikacije.
	 */
	private Date lastModifiedAt;
	/**
	 * Naslov unsoa.
	 */
	private String title;
	/**
	 * Tekst unosa.
	 */
	private String text;
	/**
	 * Autor unosa.
	 */
	private BlogUser author;
	/**
	 * Dohvaća id.
	 * 
	 * @return id
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	/**
	 * Postavlja id na predani.
	 * 
	 * @param id
	 *            predani id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * Vraća listu komentara.
	 * 
	 * @return lista {@link BlogComment}
	 */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}
	/**
	 * Postavlja listu komentara.
	 * 
	 * @param comments
	 *            lista {@link BlogComment}
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}
	/**
	 * Dohvaća datum stvaranja.
	 * 
	 * @return {@link Date} stvaranja
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreatedAt() {
		return createdAt;
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
	 * Postavljanje datuma postavljanja.
	 * 
	 * @param createdAt
	 *            {@link Date} postavljanja
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	/**
	 * Dohvaća datum zadnje promjene.
	 * 
	 * @return {@link Date} zadnje promjene
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}
	/**
	 * Postavlja datum zadnje promjene.
	 * 
	 * @param lastModifiedAt
	 *            {@link Date} zadnje promjene
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}
	/**
	 * Dohvaća naslov unosa.
	 * 
	 * @return naslov unosa
	 */
	@Column(length = 200, nullable = false)
	public String getTitle() {
		return title;
	}
	/**
	 * Postavlja naslov unosa na zadanu vrijednost.
	 * 
	 * @param title
	 *            zadana vrijednost.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Dohvaća tekst unosa.
	 * 
	 * @return tekst unosa.
	 */
	@Column(length = 4096, nullable = false)
	public String getText() {
		return text;
	}
	/**
	 * Postavlja tekst unosa.
	 * 
	 * @param text
	 *            tekst unosa.
	 */
	public void setText(String text) {
		this.text = text;
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
		BlogEntry other = (BlogEntry) obj;
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