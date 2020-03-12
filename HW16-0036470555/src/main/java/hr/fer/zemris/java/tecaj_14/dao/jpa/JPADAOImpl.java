package hr.fer.zemris.java.tecaj_14.dao.jpa;

import hr.fer.zemris.java.tecaj_14.dao.DAO;
import hr.fer.zemris.java.tecaj_14.dao.DAOException;
import hr.fer.zemris.java.tecaj_14.model.BlogComment;
import hr.fer.zemris.java.tecaj_14.model.BlogEntry;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
/**
 * Implementacija {@link DAO} sučelja za rad s hibernateom i JPA specifikacijom.
 * 
 * @author Ivan Pavić
 * 
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		Query query = em.createQuery("select b from BlogEntry as b where b.id=:be");
		query.setParameter("be", id);
		BlogEntry entry = null;
		try {
			entry = (BlogEntry) query.getSingleResult();
		} catch (Exception e) {
		}
		return entry;
	}

	@Override
	public BlogUser getBlogUser(String nick) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		Query query = em.createQuery("select b from BlogUser as b where b.nick=:be");
		query.setParameter("be", nick);
		BlogUser user = null;
		try {
			user = (BlogUser) query.getSingleResult();
		} catch (Exception e) {
		}
		return user;
	}
	@Override
	public boolean existsUserWithNick(String nick) throws DAOException {
		return getBlogUser(nick) != null;
	}

	@Override
	public void saveBlogUser(BlogUser blogUser) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(blogUser);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogUser> getUsers() {
		EntityManager em = JPAEMProvider.getEntityManager();
		Query query = em.createQuery("select b from BlogUser b");
		return query.getResultList();
	}

	@Override
	public void saveBlogEntry(BlogEntry entry) {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(entry);
	}
	@Override
	public void updateBlogEntry(BlogEntry entry) {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.merge(entry);
	}

	@Override
	public void updateBlogComment(BlogComment comment) {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(comment);
	}

}