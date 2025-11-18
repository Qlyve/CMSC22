package application;

import uiandlogic.DataAccess;
import uiandlogic.Login;
import uiandlogic.User;

public class Main {
	public static void main(String[] args) {
		// we can change it later to "extends application" kapag okay na ung backend like logging in and stuff.
		
		DataAccess dataAccess = new DataAccess();
		 
		// TESTING //
		
		// print all users for now to check if nababasa ng tama
		System.out.printf("\t\t\tCurrent Users in System\t\t\t\n");
		for (User user : dataAccess.getUsers()) {
			user.viewUser();
		}
		
		Login login = new Login(dataAccess);
		login.authenticate("test1username", "test1pw");
		login.authenticate("test2username", "test2pw");
		
		
		
		// TESTING //
		
		
		
	}
}
