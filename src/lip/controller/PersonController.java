package lip.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;

import lip.model.Person;
import lip.util.DefaultEntityManager;
import lip.util.Util;

@Named
@ViewScoped
public class PersonController implements Serializable{

	private static final long serialVersionUID = 1598383566831207263L;
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
