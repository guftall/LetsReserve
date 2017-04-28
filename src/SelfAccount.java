
import tools.User;

public class SelfAccount {
	
	private static User myUser;

	public void test() throws Exception {
		requestConnection.showNextWeekGhazas();
		
	}
	
	
	
	
	
	
	
	
	private SelfAccount(User user) {
		requestConnection = new ReqClass(user);
		myUser = user;
	}
	
	public static SelfAccount getSelfAccount(User user) throws Exception {
		if(selfAccount == null)
			return new SelfAccount(user);
		else
			return selfAccount;
	}
	
	public void printUser() {
		System.out.println(myUser);
	}
	private static SelfAccount selfAccount;
	private ReqClass requestConnection;
}
