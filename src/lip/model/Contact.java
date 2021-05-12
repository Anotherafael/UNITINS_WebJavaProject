package lip.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lip.util.DefaultEntity;

@Entity
@Table(name = "\"contacts\"")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Contact extends DefaultEntity<Contact> {

	private Integer id;
	
	@ManyToOne
	private User user;
	
	public Contact() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
