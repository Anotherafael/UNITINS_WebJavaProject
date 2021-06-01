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
import lip.repository.MusicRepository;
import lip.repository.RepositoryException;
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
	public void save() {
		if (fotoInputStream == null) {
			super.save();
			clean();
			Util.addInfoMessage("Opera��o realizada com sucesso.");
			Util.redirect("/lip/views/music/index.xhtml");
			return;
		};
		MusicRepository repo = new MusicRepository();
		try {
			repo.beginTransaction();
			setEntity(repo.save(getEntity()));
			if (! Util.saveImageUsuario(fotoInputStream, "png", getEntity().getId())) 
				throw new RepositoryException("Erro ao salvar. N�o foi poss�vel salvar a imagem do usu�rio.");
			repo.commitTransaction();
			Util.addInfoMessage("Opera��o realizada com sucesso.");
			clean();
		} catch (RepositoryException e) {
			repo.rollbackTransaction();
			System.out.println("Erro ao salvar.");
			e.printStackTrace();
			Util.addErrorMessage(e.getMessage());
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
			Util.addInfoMessage("Upload realizado com sucesso.");
		} else {
			Util.addErrorMessage("O tipo da image deve ser png.");
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
		}
		return entity;
	}

	@Override
	public void remove(Music entity) {
		super.remove(entity);
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
