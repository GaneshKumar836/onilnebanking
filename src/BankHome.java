import java.util.Scanner;

public class BankHome {
	public static void main(String args[]) throws Exception
	{
		
		boolean connect=DatabaseConnection.connect();
		if(connect==true)
		{
			System.out.println("Database Connected !");
		}
		Scanner sc=new Scanner(System.in);
		System.out.println("*********Welcome to SBI Online*********");
		System.out.println("Enter your login:");
		System.out.println("Type admin for Admin login");
		System.out.println("Type user for customer login");
		String choice=sc.next();
		switch(choice)
		{
		case "admin":System.out.println("Here is the Admin Login:");
		              Adminlogin al=new Adminlogin();
		              al.login();
		break;
		case "user":System.out.println("Here is the user login or register:");
		              Userlogin ul=new Userlogin();
		              ul.logtype();
		break;
		}
	}

}
