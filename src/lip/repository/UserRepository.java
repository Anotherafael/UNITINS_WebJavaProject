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
	
	@SuppressWarnings("unchecked")
	public List<User> findByName(String search) throws RepositoryException {
		
		try {
			EntityManager em = JPAUtil.getEntityManager();
			Query query = em.createQuery("SELECT u FROM User u ORDER BY u.id LIKE search");
			
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RepositoryException("Database consult error");
		}
		
	}
	
	public User findByEmail(String email) throws RepositoryException {
		EntityManager em = getEntityManager();
		StringBuffer jpql = new StringBuffer();
		jpql.append("SELECT ");
		jpql.append(" u ");
		jpql.append("FROM ");
		jpql.append(" User u ");
		jpql.append("WHERE ");
		jpql.append(" u.email = :email ");
		
		Query query = em.createQuery(jpql.toString());
		query.setParameter("email", email);
		
		User user = null;
		try {
			user = (User) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	public User findUserByCode(String code) throws RepositoryException {
		
		EntityManager em = getEntityManager();
		StringBuffer jpql = new StringBuffer();
		jpql.append("SELECT ");
		jpql.append(" u ");
		jpql.append("FROM ");
		jpql.append(" User u, RecoverPassword r ");
		jpql.append("WHERE ");
		jpql.append(" r.code = :code ");
		
		Query query = em.createQuery(jpql.toString());
		query.setParameter("code", code);
		
		User user = null;
		try {
			user = (User) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}
}
