package lip.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.util.LangUtils;

import lip.model.Music;
import lip.repository.MusicRepository;
import lip.repository.RepositoryException;
import lip.util.Util;

@Named
@ViewScoped
public class MusicController extends Controller<Music> {

	private static final long serialVersionUID = 1L;

	private List<Music> musicList;
	private List<Music> musicListFiltered;
	
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
	
	public List<Music> getMusicListFiltered() {
		return musicListFiltered;
	}
	
	public void setMusicListFiltered(List<Music> musicListFiltered) {
		this.musicListFiltered = musicListFiltered;
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isValueBlank(filterText)) {
            return true;
        }

        Music music = (Music) value;
        return music.getTitle().toLowerCase().contains(filterText);
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
	}

}
