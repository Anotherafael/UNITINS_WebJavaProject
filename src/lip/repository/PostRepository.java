package lip.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import lip.model.Post;
import lip.model.User;
import lip.util.JPAUtil;
import lip.util.Session;

public class PostRepository extends Repository<Post> {

	public PostRepository() {
		super();
	}

	public PostRepository(EntityManager em) {
		super(em);
	}
	
	@SuppressWarnings("unchecked")
	public List<Post> findAll() throws RepositoryException {

		try {
			EntityManager em = JPAUtil.getEntityManager();
			Query query = em.createQuery("SELECT p FROM Post p ORDER BY p.id ");

			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RepositoryException("Database consult error");
		}

	}
	
	@SuppressWarnings("unchecked")
	public List<Post> findPostByUser() throws RepositoryException {
		
		User user = (User) Session.getInstance().getAttribute("loggedInUser");
		
		try {
			EntityManager em = JPAUtil.getEntityManager();
			Query query = em.createQuery("SELECT p FROM Post p WHERE p.user = :user ORDER BY p.id");
			query.setParameter("user",  user);
			
			return (List<Post>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RepositoryException("Database consult error");
		}
		
	}
}
