package lip.model;

public enum PostType {
	
	NEWS (1, "News"), 
	EVENT (2, "Event"),
	MUSIC (3, "Music");

	private int id;
	private String label;

	private PostType(int id, String label) {
	        this.id = id;
	        this.label = label;
	    }

	public int getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public static PostType valueOf(int value) {
		for (PostType postType : PostType.values()) {
			if (value == postType.getId())
				return postType;
		}
		return null;
	}
}
