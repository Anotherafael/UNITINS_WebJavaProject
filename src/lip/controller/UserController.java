package lip.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lip.model.Contact;
import lip.model.Phone;
import lip.model.User;
import lip.repository.RepositoryException;
import lip.repository.UserRepository;
import lip.util.Util;

@Named
@ViewScoped
public class UserController extends Controller<User> {
	
	static final long serialVersionUID = -7139140939538307737L;

	private List<User> userList;
	private Phone phone;

	public UserController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("flashUser");
		entity = (User) flash.get("flashUser");
	}

	public Phone getPhone() {
		if (phone == null) {
			phone = new Phone();
		}
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}
	
	public void addContact() {
		if (getEntity().getListContacts() == null)
			getEntity().setListContacts(new ArrayList<Contact>());

		getPhone().setUser(getEntity());
		getEntity().getListContacts().add(getPhone());

		phone = null;
	}
	
	public void removeContact(Contact contact) {
		getEntity().getListContacts().remove(contact);
	}

	public List<User> getUserList() {
		if (userList == null) {
			userList = new ArrayList<User>();
			UserRepository repo = new UserRepository();
			try {
				setUserList(repo.findAll());
			} catch (RepositoryException e) {
				e.printStackTrace();
				Util.addErrorMessage("Error on trying to find all.");
				setUserList(null);
			}
		}
		return userList;
	}
	
	public void setUserList(List<User> listaUsuario) {
		this.userList = listaUsuario;
	}
	
	@Override
	public User getEntity() {
		if (entity == null ) {
			entity = new User();
		}
		return entity;
	}
	
	@Override
	public void save() {
		super.save();
	}
	
	@Override
	public void remove(User user) {
		super.remove(user);
	}
	
	@Override
	public void edit(User entity) {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("flashUser", entity);
		Util.redirect("/lip/views/user/edit.xhtml");
	}

}
