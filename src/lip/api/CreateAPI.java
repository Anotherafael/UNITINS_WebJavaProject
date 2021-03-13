package lip.api;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import lip.model.Person;
import lip.model.User;
import lip.util.Util;

public class CreateAPI {

	public static void main(String[] args) {
		
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("lip");
			EntityManager em = emf.createEntityManager();

			User user = new User();
			Person person = new Person();
			person.setName("Rafael");
			person.setCpf("123");
			user.setPerson(person);
			user.setEmail("rafael@gmail.com");
			user.setNickname("anotherafael");
			user.setPassword(Util.hash("123"));
			
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
