package lip.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lip.model.Contact;
import lip.model.Email;
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
	private Email email;

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

	public Email getEmail() {
		if (email == null)
			email = new Email();
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public void addContact() {
		if (getEntity().getListContacts() == null)
			getEntity().setListContacts(new ArrayList<Contact>());

		if (getPhone().getNumber() == null) {
			if (getEmail().getAddress() == null) {
				phone = null;
				email = null;
				return;
			} else {
				getEmail().setUser(getEntity());
				getEntity().getListContacts().add(getEmail());
				phone = null;
				email = null;
				return;
			}
		} else {
			getPhone().setUser(getEntity());
			getEntity().getListContacts().add(getPhone());
			if (getEmail().getAddress() == null) {
				phone = null;
				email = null;
				return;
			} else {
				getEmail().setUser(getEntity());
				getEntity().getListContacts().add(getEmail());
			}
			phone = null;
			email = null;
			return;
		}

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
				Util.addErrorMessage("Error on trying to find users.");
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
		
		if (entity == null) {
			entity = new User();
		}
		return entity;
	}

	@Override
	public void save() {
		super.save();
		Util.addInfoMessage("User saved");
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		Util.redirect("/lip/views/user/index.xhtml");
	}
	
	@Override
	public void remove(User user) {
		super.remove(user);
		Util.addInfoMessage("User removed");
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		Util.redirect("/lip/views/user/index.xhtml");
	}

	public void returnToIndex() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.clear();
		Util.redirect("/lip/views/user/index.xhtml");
	}

	@Override
	public void edit(User entity) {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("flashUser", entity);
		Util.redirect("/lip/views/user/edit.xhtml");
	}

}
