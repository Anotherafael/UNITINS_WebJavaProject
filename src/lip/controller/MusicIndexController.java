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
import lip.repository.UserRepository;
import lip.util.Util;

@Named
@ViewScoped
public class MusicIndexController extends Controller<Music> {

	private static final long serialVersionUID = 1956727970610552478L;

	private List<Music> musicList;
	private User selectedUser;

	public User getSelectedUser() {
		if (selectedUser == null) {
			selectedUser = new User();
		}
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

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

		if (getSelectedUser() == null) {
			Util.addErrorMessage("You didn't selected an artists.");
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			return;
		}

		MusicRepository repo = new MusicRepository();
		try {
			setMusicList(repo.findMusicsByUser(getSelectedUser()));
		} catch (RepositoryException e) {
			e.printStackTrace();
			Util.addErrorMessage("Error on trying to find posts.");
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			setMusicList(null);
			return;
		}
	}
}
