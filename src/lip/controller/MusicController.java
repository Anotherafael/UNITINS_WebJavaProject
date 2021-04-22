package lip.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.util.LangUtils;

import lip.model.Music;
import lip.model.Post;
import lip.repository.MusicRepository;
import lip.repository.RepositoryException;
import lip.util.Util;

@Named
@ViewScoped
public class MusicController extends Controller<Music> {

	private static final long serialVersionUID = 1L;

	private List<Music> musicList;
	
	public MusicController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("flashMusic");
		entity = (Music) flash.get("flashMusic");
	}
	
	public List<Music> getMusicList() {
		if (musicList == null) {
			musicList = new ArrayList<Music>();
			MusicRepository repo = new MusicRepository();
			try {
				setMusicList(repo.findAll());
			} catch (RepositoryException e) {
				e.printStackTrace();
				Util.addErrorMessage("Error on trying to find all.");
				setMusicList(null);
			}
		}
		return musicList;
	}
	
	public void setMusicList(List<Music> musicList) {
		this.musicList = musicList;
	}
	
	@Override
	public Music getEntity() {
		if (entity == null ) {
			entity = new Music();
		}
		return entity;
	}
	
	@Override
	public void save() {
		super.save();
		Util.redirect("/lip/views/music/index.xhtml");
	}
	
	@Override
	public void remove(Music entity) {
		super.remove(entity);
		Util.redirect("/lip/views/music/index.xhtml");
	}
	
	public String returnToIndex () {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.clear();
		return "index.xhtml?faces-redirect=true";
	}
	
	@Override
	public void edit(Music entity) {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("flashMusic", entity);
		Util.redirect("/lip/views/music/form.xhtml");
	}

}
