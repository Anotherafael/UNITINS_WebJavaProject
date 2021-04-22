package lip.model;

import javax.persistence.Entity;

@Entity
public class Email extends Contact {

	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
