package lip.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lip.util.DefaultEntity;

@Entity
@Table(name = "\"links\"")
@Inheritance(strategy=InheritanceType.JOINED)
public class Link extends DefaultEntity<Link>{

	@Column(nullable = false, length = 100, unique = true)
	private String url;
	@Column(nullable = false)
	private Platform platform;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Music music;
	
	public Link() {
		
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((platform == null) ? 0 : platform.hashCode());
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
		Link other = (Link) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (platform == null) {
			if (other.platform != null)
				return false;
		} else if (!platform.equals(other.platform))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return getUrl();
	}
}
