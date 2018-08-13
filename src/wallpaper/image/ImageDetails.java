package wallpaper.image;

import java.util.Date;

/** @author Aditya Rao */
class ImageDetails {
	private String id;
	private String name;
	private String user;
	private Date date;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public String toJSON() {
		return "{\"id\":" + id + ",\"name\":" + name + ",\"user\":" + user + ",\"date\":" + date.toString() + "}";
	}
}
