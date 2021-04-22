package lip.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import lip.model.User;

public class UpdateAPI {

	public static void main(String[] args) {
		
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("lip");
			EntityManager em = emf.createEntityManager();

			User user = new User();
			user = em.find(User.class, 1);
			user.setEmail("rafaelafm@gmail.com");
			user.setName("Rafael Freitas");
			
			em.getTransaction().begin();
			// Merge é utilizado tanto para Create quanto Update.
			user = em.merge(user);
			em.getTransaction().commit();
			
			System.out.println("Ready!");			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Not ready!");
		}
	}

}
