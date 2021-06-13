package lip.controller;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lip.model.Music;
import lip.util.Util;

@Named
@ViewScoped
public class MusicInfoController extends Controller<Music> {

	private static final long serialVersionUID = 6729017227222778483L;

	Music music;
	
	public MusicInfoController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("flashMusicInfo");
		entity = (Music) flash.get("flashMusicInfo");
	}

	@Override
	public Music getEntity() {
		if(entity == null) {
			entity = new Music();
		}
		return entity;
	}
	
	public void musicInfo(Music entity) {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("flashMusicInfo", entity);
		Util.redirect("/lip/views/home/music_info.xhtml");
	}

	public Music getMusic() {
		if(music == null) {
			music = new Music();
		}
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}
}
