package lip.controller.auth;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lip.controller.Controller;
import lip.model.User;
import lip.util.Util;

@Named
@ViewScoped
public class RegisterController extends Controller<User> {

	private static final long serialVersionUID = 859561339898552447L;

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
		Util.addInfoMessage("User added with success");
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		Util.redirect("/lip/views/auth/login.xhtml");
	}
}
