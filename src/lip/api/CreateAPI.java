package lip.api;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import lip.model.Person;

public class CreateAPI {

	public static void main(String[] args) {
		
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("lip");
			EntityManager em = emf.createEntityManager();

			Person person = new Person();
			person.setCpf("001");
			person.setName("Gabriela");
			
			em.getTransaction().begin();
			// Persist é utilizado para realizar um Create
			em.persist(person);
			em.getTransaction().commit();
			
			System.out.println("Ready!");			
		} catch (Exception e) {
			System.out.println("Not ready!");
		}
	}
}
