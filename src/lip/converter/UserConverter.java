package lip.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import lip.model.User;
import lip.repository.UserRepository;

@FacesConverter(forClass = User.class)
public class UserConverter implements Converter<User> {

	@Override
	public User getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty())
			return null;
		
		UserRepository repo = new UserRepository();
		User user;
		try {
			user = repo.findById(Integer.valueOf(value.trim()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		} 
		return user;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, User value) {
		if (value == null || value.getId() == null)
			return null;
		return value.getId().toString();
	}
}
