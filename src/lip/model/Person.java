package lip.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lip.util.DefaultEntity;

@Entity
public class Person extends DefaultEntity{

	private String name;
	
	@Column(unique=true)
	private String cpf;
	
	public Person () {
		super();
	}
	
	public Person(String name, String cpf) {
		super();
		this.name = name;
		this.cpf = cpf;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@Override
	public String toString() {
		return "Person [name=" + name + ", cpf=" + cpf + "]";
	}	
	
}
