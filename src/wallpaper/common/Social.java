package wallpaper.common;

/**
 * @author Aditya
 */
public enum Social {
	FACEBOOK("facebook"), GOOGLE("google");
	private String value;

	Social(String value) {
		this.value = value;
	}

	/**
	 * this method returns the string representation the the constants for
	 * comparison operations etc.
	 * @return String
	 */
	public String value() {
		return value;
	}
}
