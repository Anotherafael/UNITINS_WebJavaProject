package lip.api;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import lip.model.Person;

public class UpdateAPI {

	public static void main(String[] args) {
		
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("lip");
			EntityManager em = emf.createEntityManager();

			Person person = new Person();
			
			person = em.find(Person.class, 1);
			person.setName("Rafael");
			
			em.getTransaction().begin();
			// Merge é utilizado tanto para Create quanto Update.
			person = em.merge(person);
			em.getTransaction().commit();
			
			System.out.println("Ready!");			
		} catch (Exception e) {
			System.out.println("Not ready!");
		}
	}

}
