package lip.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lip.util.DefaultEntity;

@Entity
@Table(name = "\"user\"")
public class User extends DefaultEntity<User>{

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(unique = true, nullable = false)
	private Person person;
	@Column(nullable = false, length = 20)
	private String nickname;
	@Column(unique = true, nullable = false, length = 60)
	private String email;
	@Column(nullable = false, length = 60)
	private String password;
	
	public User () {
		
	}
	
	public User (String nickname, String email, String password, Person person) {
		super();
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.person = person;
	}
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return "User [nickname=" + nickname + ", email=" + email + ", password=" + password + "]";
	}
	
}
