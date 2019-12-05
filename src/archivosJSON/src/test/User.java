package test;

public class User {

	public String username;
	public int followersCount;

	public User(String username, int followersCount) {
		this.username = username;
		this.followersCount = followersCount;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", followersCount=" + followersCount + "]";
	}

}