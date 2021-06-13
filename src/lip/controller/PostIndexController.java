package lip.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lip.model.Post;
import lip.model.User;
import lip.repository.PostRepository;
import lip.repository.RepositoryException;
import lip.util.Util;

@Named
@ViewScoped
public class PostIndexController extends Controller<Post>{

	private static final long serialVersionUID = 354705268619745054L;
	
	private List<Post> postList;

	@Override
	public Post getEntity() {
		if (entity == null ) {
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

}
