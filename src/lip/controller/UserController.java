package lip.controller;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lip.model.Person;
import lip.model.User;

@Named
@ViewScoped
public class UserController extends Controller<User> {
	
	static final long serialVersionUID = -7139140939538307737L;

	@Override
	public User getEntity() {
		if (entity == null ) {
			entity = new User();
			entity.setPerson(new Person());
		}
		return entity;
	}
	
	@Override
	public void save() {
		super.save();
	}

}
