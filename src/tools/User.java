package tools;

public class User {

	private String Name;
	private String txtUsername;
	private String txtPassword;
	
	public User(String username, String password) {
		this.txtUsername = username;
		this.txtPassword = password;
		
	}
	
	public String getName() {
		return Name;
	}
	public String getTxtUsername() {
		return txtUsername;
	}
	public String getTxtPassword() {
		return txtPassword;
	}
	
}
