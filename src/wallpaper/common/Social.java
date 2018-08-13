package wallpaper.common;

public enum Social {
	FACEBOOK("facebook"), GOOGLE("google");
	private String value;

	Social(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
