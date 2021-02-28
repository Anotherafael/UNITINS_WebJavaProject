package lip.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import lip.model.Person;

@Named
@RequestScoped
public class PersonController {

	private Person person = null;

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("lip");
	EntityManager em = emf.createEntityManager();
	
	public Person getPerson() {
		if (person == null)
			person = new Person();
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	public void save() {
		em.getTransaction().begin();
		em.persist(getPerson());
		em.getTransaction().commit();
		System.out.println("Saved on Database");
		clean();
	}
	
	public void clean() {
		System.out.println("Cleaning");
		person = null;
	}
}
