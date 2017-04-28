import tools.User;

public class MainClass {

	public static void main(String[] args) throws Exception {
		
		// Some Test
		SelfAccount myAccount = SelfAccount.getSelfAccount(new User("15481", "17633"));
		
		myAccount.test();

	}

}
