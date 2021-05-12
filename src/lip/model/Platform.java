package lip.model;

public enum Platform {

	INSTAGRAM (1, "Instagram"),
	SOUNDCLOUD (1, "Soundcloud"),
	YOUTUBE (1, "Youtube"),
	SPOTIFY (1, "Spotify"),
	FACEBOOK (1, "Facebook"),
	TWITTER (1, "Twitter");
	
	private Integer id;
	private String label;
	
	private Platform(Integer id, String label) {
		this.id = id;
		this.label = label;
	}

	public Integer getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}
	
	public static Platform valueOf(int value) {
		for (Platform platform : Platform.values()) {
			if (value == platform.getId())
				return platform;
		}
		return null;
	}
}
