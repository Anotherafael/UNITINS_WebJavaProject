package lip.repository;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;

import lip.util.DefaultEntity;
import lip.util.JPAUtil;

public class Repository<T extends DefaultEntity<? super T>>  {

	private EntityManager entityManager;
	
	public Repository() {
		this(JPAUtil.getEntityManager());
	}
	
	public Repository(EntityManager em) {
		setEntityManager(em);
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	private void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	public T save(T entity) throws RepositoryException {
		try { 
			return getEntityManager().merge(entity);
		
		} catch (OptimisticLockException e) {
			e.printStackTrace();
			throw new RepositoryException("Save doens't work. Try restart the page");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RepositoryException("Save error");
		} 
	
	}

	public void remove(T entity) throws RepositoryException {
		
		try { 
			T t = getEntityManager().merge(entity);
			getEntityManager().remove(t);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RepositoryException("Delete error ");
		} 
	
	}
	
	public void beginTransaction() {
		try {
			getEntityManager().getTransaction().begin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void commitTransaction() throws RepositoryException {
		try {
			getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RepositoryException("Commit error");
		}
	}
	
	public void rollbackTransaction() {
		try {
			getEntityManager().getTransaction().rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public T findById(Integer id) {
		final ParameterizedType type = 
				(ParameterizedType) getClass().getGenericSuperclass();
		Class<T> tClass = (Class<T>) (type).getActualTypeArguments()[0];
		
		T t = (T) getEntityManager().find(tClass, id);
		
		return t;
	}

}
