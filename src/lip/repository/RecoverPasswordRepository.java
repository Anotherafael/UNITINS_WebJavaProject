package lip.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import lip.model.RecoverPassword;

public class RecoverPasswordRepository extends Repository<RecoverPassword> {

	public RecoverPasswordRepository() {
		
	}
	
	public RecoverPasswordRepository(EntityManager em) {
		super(em);
	}
	
	public RecoverPassword findByCode(String code) throws RepositoryException {
		
		try {
			EntityManager em = getEntityManager();

			String jpql = "SELECT p FROM RecoverPassword p WHERE p.code = :code ORDER BY p.created_at ASC";
			Query query = em.createQuery(jpql);
			query.setParameter("code",  code);

			return (RecoverPassword)(query.getSingleResult());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RepositoryException("Database consult error");
		}
		
	}
}
