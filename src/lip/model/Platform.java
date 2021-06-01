package lip.model;

public enum Platform {

	INSTAGRAM (1, "Instagram"),
	SOUNDCLOUD (2, "Soundcloud"),
	YOUTUBE (3, "Youtube"),
	SPOTIFY (4, "Spotify");
	
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
