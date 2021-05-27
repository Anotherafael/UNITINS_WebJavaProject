package lip.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lip.util.DefaultEntity;

@Entity
public class RecoverPassword extends DefaultEntity<RecoverPassword> {

	private String code;
	private LocalDateTime limitDate;
	private boolean isUsed;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDateTime getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(LocalDateTime limitDate) {
		this.limitDate = limitDate;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
