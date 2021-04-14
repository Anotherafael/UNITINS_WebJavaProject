package lip.controller.auth;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import lip.model.Person;
import lip.model.User;
import lip.repository.RepositoryException;
import lip.util.JPAUtil;
import lip.util.Util;

@Named
@ViewScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = -7554946551552979100L;
	
	private String email;
	private String password;
	
	public User findUser(String email, String password) throws RepositoryException{ 
		
		try {
			EntityManager em = JPAUtil.getEntityManager();
			
			StringBuffer jpql = new StringBuffer();
			jpql.append("SELECT ");
			jpql.append("  u ");
			jpql.append("FROM ");
			jpql.append("  User u ");
			jpql.append("WHERE ");
			jpql.append("  u.email = :email ");
			jpql.append("  AND u.password = :password ");
			
			Query query = em.createQuery(jpql.toString());
			query.setParameter("email", email);
			query.setParameter("password", password);
			
			return (User) query.getSingleResult();
			
		} catch (Exception e) {
			Util.addErrorMessage("User not found");
			clean();
			return null;
		}
		
	}
	
	public void singIn() {
		try {
			User usuarioLogado = findUser(getEmail(), getPassword());
			
			if (usuarioLogado != null) {
				//Session.getInstance().setAttribute("usuarioLogado", usuarioLogado);
				Util.redirect("/lip/views/person/person.xhtml");
			} else {
				Util.redirect("/lip/views/person/login.xhtml");
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			Util.addErrorMessage("Error on login.");
		}
	}
	
	public void clean () {
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
}
