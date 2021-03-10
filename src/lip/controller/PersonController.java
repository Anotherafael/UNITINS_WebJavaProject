package lip.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import lip.model.Person;
import lip.util.DefaultEntityManager;
import lip.util.Util;

@Named
@RequestScoped
public class PersonController {

	private Person person = null;

	public Person getPerson() {
		if (person == null)
			person = new Person();
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	public void save() {
		EntityManager em = DefaultEntityManager.getEntityManager();
		em.getTransaction().begin();
		em.persist(getPerson());
		em.getTransaction().commit();
		Util.addInfoMessage("User added");
		System.out.println("Saved on Database");
		clean();
	}
	
	public void clean() {
		System.out.println("Cleaning");
		person = null;
	}
}
