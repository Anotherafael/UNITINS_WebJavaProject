package lip.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import lip.model.Link;
import lip.model.Music;
import lip.model.Platform;
import lip.model.User;
import lip.repository.MusicRepository;
import lip.repository.RepositoryException;
import lip.util.Session;
import lip.util.Util;

@Named
@ViewScoped
public class MusicController extends Controller<Music> {

	private static final long serialVersionUID = 1L;

	private List<Music> musicList;

	private Link instagram;
	private Link soundcloud;
	private Link youtube;
	private Link spotify;

	private InputStream fotoInputStream = null;
	private String nomeFoto = null;

	public MusicController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("flashMusic");
		entity = (Music) flash.get("flashMusic");
	}
	
	public InputStream getFotoInputStream() {
		return fotoInputStream;
	}

	public void setFotoInputStream(InputStream fotoInputStream) {
		this.fotoInputStream = fotoInputStream;
	}

	public String getNomeFoto() {
		if (nomeFoto == null) 
			return "Selecione uma foto ...";
		return "Arquivo: "+ nomeFoto + " (Clique para alterar a foto...)";
	}

	public void setNomeFoto(String nomeFoto) {
		this.nomeFoto = nomeFoto;
	}

	public Link getInstagram() {
		if (instagram == null) {
			instagram = new Link();
		}
		return instagram;
	}

	public void setInstagram(Link instagram) {
		this.instagram = instagram;
	}

	public Link getSoundcloud() {
		if (soundcloud == null) {
			soundcloud = new Link();
		}
		return soundcloud;
	}

	public void setSoundcloud(Link soundcloud) {
		this.soundcloud = soundcloud;
	}

	public Link getYoutube() {
		if (youtube == null) {
			youtube = new Link();
		}
		return youtube;
	}

	public void setYoutube(Link youtube) {
		this.youtube = youtube;
	}

	public Link getSpotify() {
		if (spotify == null) {
			spotify = new Link();
		}
		return spotify;
	}

	public void setSpotify(Link spotify) {
		this.spotify = spotify;
	}

	public List<Music> getMusicList() {
		if (musicList == null) {
			musicList = new ArrayList<Music>();
			MusicRepository repo = new MusicRepository();
			try {
				setMusicList(repo.findMusicByUser());
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

	@Override
	public void save() {
		User user = (User) Session.getInstance().getAttribute("loggedInUser");
		entity.setUser(user);
		if (fotoInputStream == null) {
			super.save();
			clean();
			Util.addInfoMessage("Music saved without thumb.");
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			Util.redirect("/lip/views/music/index.xhtml");
			return;
		};
		MusicRepository repo = new MusicRepository();
		try {
			repo.beginTransaction();
			setEntity(repo.save(getEntity()));
			if (! Util.saveImageUsuario(fotoInputStream, "png", getEntity().getId())) {
				throw new RepositoryException("Error on saving image");				
			}
			repo.commitTransaction();
			clean();
			Util.addInfoMessage("Music saved with thumb.");
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			Util.redirect("/lip/views/music/index.xhtml");
		} catch (RepositoryException e) {
			repo.rollbackTransaction();
			e.printStackTrace();
			Util.addErrorMessage("Error on saving");
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		}
	}
	
	public void upload(FileUploadEvent event) {

		UploadedFile uploadFile = event.getFile();
		System.out.println("nome arquivo: " + uploadFile.getFileName());
		System.out.println("tipo: " + uploadFile.getContentType());
		System.out.println("tamanho: " + uploadFile.getSize());

		if (uploadFile.getContentType().equals("image/png")) {
			try {
				fotoInputStream = uploadFile.getInputStream();
				nomeFoto = uploadFile.getFileName();
				System.out.println("inputStream: " + uploadFile.getInputStream().toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Util.addInfoMessage("Image's upload was a sucess.");
		} else {
			Util.addErrorMessage("Image must be in .PNG");
		}

	}

	public void addLink() {
		if (getEntity().getListLinks() == null)
			getEntity().setListLinks(new ArrayList<Link>());

		if (getInstagram().getUrl() == null) {
			if (getSoundcloud().getUrl() == null) {
				if (getSpotify().getUrl() == null) {
					if (getYoutube().getUrl() == null) {
						return;
					} else {
						getYoutube().setMusic(getEntity());
						getYoutube().setPlatform(Platform.YOUTUBE);
						getEntity().getListLinks().add(getYoutube());
						cleanLink();
					}
				} else {
					getSpotify().setMusic(getEntity());
					getSpotify().setPlatform(Platform.SPOTIFY);
					;
					getEntity().getListLinks().add(getSpotify());
					cleanLink();
				}
			} else {
				getSoundcloud().setMusic(getEntity());
				getSoundcloud().setPlatform(Platform.SOUNDCLOUD);
				getEntity().getListLinks().add(getSoundcloud());
				cleanLink();
			}
		} else {
			getInstagram().setMusic(getEntity());
			getInstagram().setPlatform(Platform.INSTAGRAM);
			getEntity().getListLinks().add(getInstagram());
			cleanLink();
		}

	}

	public void cleanLink() {
		instagram = null;
		soundcloud = null;
		spotify = null;
		youtube = null;
	}

	public void removeLink(Link link) {
		getEntity().getListLinks().remove(link);
	}

	@Override
	public Music getEntity() {
		if (entity == null) {
			entity = new Music();
			entity.setUser(new User());
		}
		return entity;
	}

	@Override
	public void remove(Music entity) {
		super.remove(entity);
		Util.addWarnMessage("Music removed");;
		Util.redirect("/lip/views/music/index.xhtml");
	}

	public void returnToIndex() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.clear();
		Util.redirect("/lip/views/music/index.xhtml");
	}

	@Override
	public void edit(Music entity) {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("flashMusic", entity);
		Util.redirect("/lip/views/music/form.xhtml");
	}

}
