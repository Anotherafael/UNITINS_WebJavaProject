package lip.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lip.model.Music;
import lip.model.User;
import lip.repository.MusicRepository;
import lip.repository.RepositoryException;
import lip.util.Util;

@Named
@ViewScoped
public class MusicIndexController extends Controller<Music> {

	private static final long serialVersionUID = 1956727970610552478L;

	private List<Music> musicList;
	
	@Override
	public Music getEntity() {
		if (entity == null) {
			entity = new Music();
			entity.setUser(new User());
		}
		return entity;
	}

	public List<Music> getMusicList() {
		if (musicList == null) {
			musicList = new ArrayList<Music>();
			MusicRepository repo = new MusicRepository();
			try {
				setMusicList(repo.findAll());
			} catch (RepositoryException e) {
				e.printStackTrace();
				Util.addErrorMessage("Error on trying to find musics.");
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				setMusicList(null);
			}
		}
		return musicList;
	}

	public void setMusicList(List<Music> musicList) {
		this.musicList = musicList;
	}

	
}
