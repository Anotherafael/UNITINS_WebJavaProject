package lip.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lip.util.DefaultEntity;

@Entity
@Table(name = "\"musics\"")
public class Music extends DefaultEntity<Music>{

	@Column(nullable = false, length = 50)
	private String title;
	@Column(nullable = false, length = 500)	
	private String lyrics;
	@Column(nullable = true, length = 100)	
	private String image;
	
	public Music () {
		super();
	}
	
	public Music(String title, String lyrics, String image) {
		super();
		this.title = title;
		this.lyrics = lyrics;
		this.image = image;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((image == null) ? 0 : image.hashCode());
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
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
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
}
