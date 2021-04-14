package lip.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import lip.model.Post;
import lip.util.JPAUtil;

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
}
