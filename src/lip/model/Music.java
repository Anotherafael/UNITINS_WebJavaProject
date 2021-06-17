package lip.model;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lip.util.DefaultEntity;
import lip.util.Util;

@Entity
@Table(name = "\"musics\"")
public class Music extends DefaultEntity<Music>{

	@Column(nullable = false, length = 50)
	private String title;
	@Column(nullable = false, length = 1000)	
	private String lyrics;
	
	@OneToOne
	private User user;
	
	@OneToMany(mappedBy="music", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Link> listLinks;
	
	public Music () {
		super();
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLyrics() {
		return lyrics;
	}
	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Link> getListLinks() {
		return listLinks;
	}

	public void setListLinks(List<Link> listLinks) {
		this.listLinks = listLinks;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lyrics == null) ? 0 : lyrics.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Music other = (Music) obj;
		if (lyrics == null) {
			if (other.lyrics != null)
				return false;
		} else if (!lyrics.equals(other.lyrics))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "[Title: " + getTitle() + "]";
	}
	
	public void youtube() {		
		for (Link link : listLinks) {
			if (link.getPlatform().equals(Platform.YOUTUBE)) {
				Util.redirect(link.getUrl());
				return;
			}
		}
		Util.addWarnMessage("Não foi possível achar o link");
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		return;
	}
	
	public void instagram() {
		for (Link link : listLinks) {
			if (link.getPlatform().equals(Platform.INSTAGRAM)) {
				Util.redirect(link.getUrl());
				return;
			}
		}
		Util.addWarnMessage("Não foi possível achar o link");
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		return;
	}
	
	public void soundcloud() {
		for (Link link : listLinks) {
			if (link.getPlatform().equals(Platform.SOUNDCLOUD)) {
				Util.redirect(link.getUrl());
				return;
			}
		}
		Util.addWarnMessage("Não foi possível achar o link");
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		return;
	}
	
	public void spotify() {
		for (Link link : listLinks) {
			if (link.getPlatform().equals(Platform.SPOTIFY)) {
				Util.redirect(link.getUrl());
				return;
			}
		}
		Util.addWarnMessage("Não foi possível achar o link");
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		return;
	}
}
