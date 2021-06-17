package lip.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lip.model.Post;
import lip.model.PostType;
import lip.model.User;
import lip.repository.PostRepository;
import lip.repository.RepositoryException;
import lip.util.Session;
import lip.util.Util;

@Named
@ViewScoped
public class PostController extends Controller<Post> {

	private static final long serialVersionUID = 1L;

	private List<Post> postList;
	
	public PostController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("flashPost");
		entity = (Post) flash.get("flashPost");
	}

	public List<Post> getPostList() {
		if (postList == null) {
			postList = new ArrayList<Post>();
			PostRepository repo = new PostRepository();
			try {
				setPostList(repo.findPostsByLoggedUser());
			} catch (RepositoryException e) {
				e.printStackTrace();
				Util.addErrorMessage("Error on trying to find posts.");
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				setPostList(null);
			}
		}
		return postList;
	}
	
	public PostType[] getPostTypeList() {
		return PostType.values();
	}
	
	public void setPostList(List<Post> postList) {
		this.postList = postList;
	}
	
	@Override
	public Post getEntity() {
		if (entity == null ) {
			entity = new Post();
			entity.setUser(new User());
		}
		return entity;
	}
	
	@Override
	public void save() {
		User user = (User) Session.getInstance().getAttribute("loggedInUser");
		System.out.println(user);
		getEntity().setUser(user);
		super.save();
		Util.addInfoMessage("Post saved");
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		Util.redirect("/lip/views/post/index.xhtml");
	}
	
	@Override
	public void remove(Post entity) {
		super.remove(entity);
		Util.addWarnMessage("Post removed");
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		Util.redirect("/lip/views/post/index.xhtml");
	}
	
	public void returnToIndex () {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.clear();
		Util.redirect("/lip/views/post/index.xhtml");
	}
	
	@Override
	public void edit(Post entity) {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("flashPost", entity);
		Util.redirect("/lip/views/post/form.xhtml");
	}
}
