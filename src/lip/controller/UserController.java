package lip.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.hibernate.criterion.MatchMode;
import org.primefaces.model.FilterMeta;
import org.primefaces.util.LangUtils;

import lip.model.Person;
import lip.model.User;
import lip.repository.RepositoryException;
import lip.repository.UserRepository;
import lip.util.Util;

@Named
@ViewScoped
public class UserController extends Controller<User> {
	
	static final long serialVersionUID = -7139140939538307737L;

	private List<User> userList;
	private List<User> userListFiltered;
	
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
	
	public List<User> getUserListFiltered() {
		return userListFiltered;
	}
	
	public void setUserListFiltered(List<User> userListFiltered) {
		this.userListFiltered = userListFiltered;
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isValueBlank(filterText)) {
            return true;
        }

        User user = (User) value;
        return user.getPerson().getName().toLowerCase().contains(filterText);
    }
	
	public void setUserList(List<User> listaUsuario) {
		this.userList = listaUsuario;
	}
	
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
