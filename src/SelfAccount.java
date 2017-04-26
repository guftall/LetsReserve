import javax.jws.soap.SOAPBinding.Use;

import tools.User;

public class SelfAccount {
	
	private static User myUser;

	public void test() throws Exception {
		//requestConnection.TaideReserveGhaza();
		requestConnection.nextWeekBtn();
	}
	
	
	
	
	
	
	
	
	
	
	
	private SelfAccount() {
		requestConnection = new ReqClass(myUser, false);
	}
	
	public static SelfAccount getSelfAccount(User user) throws Exception {
		myUser = user;
		return selfAccount;
	}
	
	public void printUser() {
		System.out.println(myUser);
	}
	private static SelfAccount selfAccount = new SelfAccount();
	private ReqClass requestConnection;
}
