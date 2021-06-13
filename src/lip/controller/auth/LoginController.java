package lip.controller.auth;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import lip.model.User;
import lip.util.JPAUtil;
import lip.util.Session;
import lip.util.Util;

@Named
@ViewScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = -7554946551552979100L;

	private User user;
	private String email;
	private String password;

	public void singIn() {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<User> query = em.createQuery("from User u where u.email = :email and u.password = :password",
				User.class);

		String email = getEmail();
		String password = getPassword();
		query.setParameter("email", email);
		query.setParameter("password", Util.hash(password));

		try {
			setUser(query.getSingleResult());
			Session.getInstance().setAttribute("loggedInUser", user);
			Util.addInfoMessage("Welcome, " + user.getNickname() + "!");
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			Util.redirect("/lip/views/music/index.xhtml");
		} catch (javax.persistence.NoResultException e) {
			Util.addErrorMessage("User not found");
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			Util.redirect("/lip/views/auth/login.xhtml");
		}
	}

	public void logout() {
		User user = (User) Session.getInstance().getAttribute("loggedInUser");
		Util.addWarnMessage("Bye, " + user.getNickname() + "!");
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		Session.getInstance().setAttribute("loggedInUser", null);
		
		Util.redirect("/lip/views/auth/login.xhtml");
	}

	public void clean() {
		setEmail(null);
		setPassword(null);
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
