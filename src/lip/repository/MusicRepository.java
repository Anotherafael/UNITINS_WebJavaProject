package lip.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import lip.model.Music;
import lip.model.User;
import lip.util.JPAUtil;
import lip.util.Session;

public class MusicRepository extends Repository<Music> {

	public MusicRepository() {
		super();
	}

	public MusicRepository(EntityManager em) {
		super(em);
	}
	
	@SuppressWarnings("unchecked")
	public List<Music> findAll() throws RepositoryException {

		try {
			EntityManager em = JPAUtil.getEntityManager();
			Query query = em.createQuery("SELECT m FROM Music m ORDER BY m.id ");

			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RepositoryException("Database consult error");
		}

	}
	
	@SuppressWarnings("unchecked")
	public List<Music> findMusicsByLoggedUser() throws RepositoryException {
		
		User user = (User) Session.getInstance().getAttribute("loggedInUser");
		
		try {
			EntityManager em = JPAUtil.getEntityManager();
			Query query = em.createQuery("SELECT m FROM Music m WHERE m.user = :user ORDER BY m.id ");
			query.setParameter("user",  user);
			
			return (List<Music>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RepositoryException("Database consult error");
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Music> findMusicsByUser(User user) throws RepositoryException {
		
		try {
			EntityManager em = JPAUtil.getEntityManager();
			Query query = em.createQuery("SELECT m FROM Music m WHERE m.user = :user ORDER BY m.id");
			query.setParameter("user",  user);
			
			return (List<Music>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RepositoryException("Database consult error");
		}
		
	}
}
