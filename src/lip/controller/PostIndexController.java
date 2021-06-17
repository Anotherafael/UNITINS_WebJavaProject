package lip.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lip.model.Post;
import lip.model.PostType;
import lip.model.User;
import lip.repository.PostRepository;
import lip.repository.RepositoryException;
import lip.repository.UserRepository;
import lip.util.Session;
import lip.util.Util;

@Named
@ViewScoped
public class PostIndexController extends Controller<Post> {

	private static final long serialVersionUID = 354705268619745054L;

	private List<Post> postList;
	private User selectedUser;
	private PostType selectedPostType;
	
	private User loggedUser = null;
	
	public User getLoggedUser() {
		if (loggedUser == null) {
			loggedUser = (User) Session.getInstance().getAttribute("loggedInUser");
		}
		return loggedUser;
	}

	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public PostType getSelectedPostType() {
		return selectedPostType;
	}

	public void setSelectedPostType(PostType selectedPostType) {
		this.selectedPostType = selectedPostType;
	}

	@Override
	public Post getEntity() {
		if (entity == null) {
			entity = new Post();
			entity.setUser(new User());
		}
		return entity;
	}

	public List<Post> getPostList() {
		if (postList == null) {
			postList = new ArrayList<Post>();
			PostRepository repo = new PostRepository();
			try {
				setPostList(repo.findAll());
			} catch (RepositoryException e) {
				e.printStackTrace();
				Util.addErrorMessage("Error on trying to find posts.");
				setPostList(null);
			}
		}
		return postList;
	}

	public void setPostList(List<Post> postList) {
		this.postList = postList;
	}

	public PostType[] getPostTypeList() {
		return PostType.values();
	}

	public List<User> users(String filter) {
		UserRepository repo = new UserRepository();
		try {
			return repo.findByName(filter, 5);
		} catch (RepositoryException e) {
			e.printStackTrace();
			return new ArrayList<User>();
		}
	}

	public void search() {

		PostRepository repo = new PostRepository();

		try {

			if (getSelectedPostType() == null) {
				if(getSelectedUser() == null) {
					setPostList(repo.findAll());
					return;
				}
				setPostList(repo.findPostsByUser(getSelectedUser()));
				return;
			}
			if(getSelectedUser() == null) {
				setPostList(repo.findPostsByType(getSelectedPostType()));
				return;
			}
			setPostList(repo.findPostsByUserAndType(getSelectedUser(), getSelectedPostType()));
			return;
			
		} catch (RepositoryException e) {
			e.printStackTrace();
			Util.addErrorMessage("Error on trying to find posts.");
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			setPostList(null);
			return;
		}
	}

}
