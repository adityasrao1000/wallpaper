package wallpaper.image;

public class Token {
	private String provider;
	private String token;

	protected void setProvider(String provider) {
		this.provider = provider;
	}

	protected String getProvider() {
		return provider;
	}

	protected void setToken(String token) {
		this.token = token;
	}

	protected String getToken() {
		return token;
	}
}
