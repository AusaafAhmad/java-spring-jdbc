package metal.project;

public class User {
	private String username;
	private String password;
	private String name;
	private String bio;
	
	public User(String user, String pass, String new_name, String new_bio) {
		username = user;
		password = pass;
		name = new_name;
		bio = new_bio;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	
	public  void display() {
		System.out.println("Username: "+ username);
		System.out.println("Password: "+ password);
		System.out.println("Name: "+ name);
		System.out.println("Bio: "+ bio);
	}
}
