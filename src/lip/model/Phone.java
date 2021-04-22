package lip.model;

import javax.persistence.Entity;

@Entity
public class Phone extends Contact {

	private String number;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@Override
	public String toString() {
		return getNumber();
	}
}
