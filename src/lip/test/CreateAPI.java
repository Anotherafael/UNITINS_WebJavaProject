package lip.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import lip.model.User;

public class CreateAPI {

	public static void main(String[] args) {
		
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("lip");
			EntityManager em = emf.createEntityManager();

			User user = new User();
			user.setName("Rafael");
			user.setEmail("rafael@gmail.com");
			user.setPassword("123456");
			user.setNickname("Anotherafael");
			user.setCpf("000.000.000-00");
			
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
			
			System.out.println("Ready!");			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Not ready!");
		}
	}
}
