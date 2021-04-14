package lip.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.util.LangUtils;

import lip.model.Post;
import lip.repository.PostRepository;
import lip.repository.RepositoryException;
import lip.util.Util;

@Named
@ViewScoped
public class PostController extends Controller<Post> {

	private static final long serialVersionUID = 1L;

	private List<Post> postList;
	private List<Post> postListFiltered;
	
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
	
	public List<Post> getPostListFiltered() {
		return postListFiltered;
	}
	
	public void setPostListFiltered(List<Post> postListFiltered) {
		this.postListFiltered = postListFiltered;
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isValueBlank(filterText)) {
            return true;
        }

        Post post = (Post) value;
        return post.getTitle().toLowerCase().contains(filterText);
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
	}
}
