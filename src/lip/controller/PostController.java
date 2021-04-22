package lip.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.util.LangUtils;

import lip.model.Post;
import lip.model.PostType;
import lip.repository.PostRepository;
import lip.repository.RepositoryException;
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
				setPostList(repo.findAll());
			} catch (RepositoryException e) {
				e.printStackTrace();
				Util.addErrorMessage("Error on trying to find all.");
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
		}
		return entity;
	}
	
	@Override
	public void save() {
		super.save();
		Util.redirect("/lip/views/post/index.xhtml");
	}
	
	@Override
	public void remove(Post entity) {
		super.remove(entity);
		Util.redirect("/lip/views/post/index.xhtml");
	}
	
	public String returnToIndex () {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.clear();
		return "index.xhtml?faces-redirect=true";
	}
	
	@Override
	public void edit(Post entity) {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("flashPost", entity);
		Util.redirect("/lip/views/post/form.xhtml");
	}
}
