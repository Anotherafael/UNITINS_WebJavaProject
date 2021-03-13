package lip.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import lip.model.User;
import lip.util.JPAUtil;

public class UserRepository extends Repository<User> {

	public UserRepository() {
		super();
	}

	public UserRepository(EntityManager em) {
		super(em);
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() throws RepositoryException {

		try {
			EntityManager em = JPAUtil.getEntityManager();
			Query query = em.createQuery("SELECT u FROM User u ORDER BY u.id ");

			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RepositoryException("Database consult error");
		}

	}
}
