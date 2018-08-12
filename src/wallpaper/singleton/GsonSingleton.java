package wallpaper.singleton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

final public class GsonSingleton {
	final private static Gson gson = new Gson();
	final private static GsonBuilder gsonBuilder = new GsonBuilder();

	private GsonSingleton() { }

	public static Gson getGson() {
		return gson;
	}

	public static GsonBuilder getGsonBuilder() {
		return gsonBuilder;
	}
}
