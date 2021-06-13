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
import lip.util.Session;
import lip.util.Util;

@Named
@ViewScoped
public class ProfileController extends Controller<User> {

	private static final long serialVersionUID = 5150422400208707191L;
	
	private List<String> list;

	private Phone phone;
	private Email email;

	public List<String> getList() {
		if (list == null) {
			list = new ArrayList<String>();
			list.add(getEntity().getName());
			list.add(getEntity().getEmail());
			list.add(getEntity().getNickname());
		}
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	@Override
	public User getEntity() {
		if (entity == null) {
			User user = (User) Session.getInstance().getAttribute("loggedInUser");
			entity = user;
		}
		return entity;
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
		Util.addWarnMessage("Contact removed");
	}

	@Override
	public void save() {
		super.save();
		Util.addInfoMessage("Updated with success");
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		Util.redirect("/lip/views/profile/index.xhtml");
	}

	public void returnToIndex() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.clear();
		Util.redirect("/lip/views/profile/index.xhtml");
	}

	public void edit() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("flashUser", getEntity());
		Util.redirect("/lip/views/profile/edit.xhtml");
	}
}
