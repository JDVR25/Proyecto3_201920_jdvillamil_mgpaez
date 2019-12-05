package test;

import java.util.List;

public class Message {

	public long id;
	public String text;
	public User user;
	public List<Double> geo;

	public Message(long id, String text, User user, List<Double> geo) {
		this.id = id;
		this.text = text;
		this.user = user;
		this.geo = geo;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", \r\n\t" + "text=" + text + ", \r\n\t" + "user=" + user + ", \r\n\t" + "geo="
				+ geo + "]\r\n";
	}

}