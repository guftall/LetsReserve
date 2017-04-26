package tools;

public class User {

	private String name;
	private String txtUsername;
	private String txtPassword;
	
	public User(String username, String password) {
		this.txtUsername = username;
		this.txtPassword = password;
		
	}
	
	public String getName() {
		return name;
	}
	public String getTxtUsername() {
		return txtUsername;
	}
	public String getTxtPassword() {
		return txtPassword;
	}
	
	@Override
	public String toString() {
		String reString = "Name: "+ name + "\n";
		reString += "Username : "+ txtUsername + "\n";
		reString += "Password : "+ txtPassword;
		
		return reString;
	}
	
}
