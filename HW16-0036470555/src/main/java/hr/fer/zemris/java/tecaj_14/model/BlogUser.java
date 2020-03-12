package hr.fer.zemris.java.tecaj_14.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
/**
 * Razred predstavlja korisnika na blogu.
 * 
 * @author Ivan Pavić
 * 
 */
@Entity
@Table(name = "blog_users")
public class BlogUser {
	/**
	 * Id korisnika.
	 */
	private Long id;
	/**
	 * Ime korisnika.
	 */
	private String firstName;
	/**
	 * Prezime korisnika.
	 */
	private String lastName;
	/**
	 * Nick korisnika.
	 */
	private String nick;
	/**
	 * Kriptirana lozinka korisnika.
	 */
	private String passwordHash;
	/**
	 * Email korisnika.
	 */
	private String email;
	/**
	 * Unosi korisnika.
	 */
	private List<BlogEntry> entries = new ArrayList<>();

	/**
	 * @param entries
	 *            the entries to set
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
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
	 * @return the id
	 */
	@Id
	@GeneratedValue
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
	 * @return the firstName
	 */
	@Column(length = 4096, nullable = false)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	@Column(length = 4096, nullable = false)
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the nick
	 */
	@Column(unique = true, length = 4096, nullable = false)
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick
	 *            the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the passwordHash
	 */
	@Column(length = 4096, nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * @param passwordHash
	 *            the passwordHash to set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("lastModifiedAt")
	public List<BlogEntry> getEntries() {
		return entries;
	}

	@Override
	public String toString() {
		return String.format("%s %s | %s", firstName, lastName, nick);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public BlogEntry getEntry(Long id) {
		for (BlogEntry i : entries) {
			if (i.getId() == id) {
				return i;
			}
		}
		return null;
	}

}
