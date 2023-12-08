import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

//import java.util.Properties;
//import javax.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//import com.mysql.cj.Session;
//import com.mysql.cj.protocol.Resultset;
//import com.mysql.cj.protocol.x.SyncFlushDeflaterOutputStream;

public class Userlogin {
	int id;
	void logtype()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter your option !");
		System.out.println("Type login for Existing user !");
		System.out.println("Type register for new user !");
		String option=sc.next();
		
		switch(option)
		{
		case "login":login();
		break;
		case "register":register();
        break;
	  }
	}
	
	void login()
	{
		Scanner sc=new Scanner(System.in);
	String status="Yes";
	int account_status=0;
	String username,password;
	int vid;
	System.out.println("Login Here");
	try
	{
		System.out.println("Enter Your UserName");
		username = sc.next();
		System.out.println("Enter Your Password");
		password = sc.next();
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/netbank", "root", "root");
		String sql="select * from user where username='"+username+"' and password='"+password+"' and status='"+status+"'";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if(rs.next())
		{
			id = rs.getInt(1);
			System.out.println("Hello Customer .. Welcome To IBM Net Banking System");
			String sql2="select c1.id,c1.name,c1.status,c2.status,c3.account from user c1 inner join userinfo c2 on c1.id=c2.id inner join useraccount c3 on c2.id=c3.id and c2.status='Approved' and c2.id='"+id+"'";
			Statement st1 = con.createStatement();
			ResultSet rs1 = st1.executeQuery(sql2);
			if(rs1.next())
			{
				Verifiedusers al=new Verifiedusers();
				al.dashboard();// Calling Dashboard Menu
			}
			else
			{
				vid = verification(); // verification method calling
				String sql1="select * from userinfo where id='"+id+"'";
				Statement st3 = con.createStatement();
				ResultSet rs3 = st3.executeQuery(sql1);
				while(rs3.next())
				{
					if(rs3.getString(6).equals("Approved"))
					{
						account_status=1;
					}
				}
				if(account_status==1)
				{
					System.out.println("Hello Customer .. Documents verified Success");
					System.out.println("Create your Account");
					accountCreation(); // Calling Account Creation Method
				}
				else
				{
					System.out.println("Hello Customer .. Sorry !! Document Verification Failed");
					System.out.println("Not Applicable to Create Account");
				}
			}
		}
		else
		{
			System.out.println("Account is Invalid Or Not Verified Or Rejected");
		}
	
	
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
	void register()
	{
		Scanner sc=new Scanner(System.in);
		String name,username,password,email,mobile; 
		System.out.println("Registration Here");
		try {
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/netbank","root","root");
		    System.out.println("Enter customer name");
		    name=sc.next();
		    System.out.println("Enter user name");
		    username=sc.next();
		    System.out.println("Enter password");
		    password=sc.next();
		    System.out.println("Enter email");
		    email=sc.next();
		 
		    System.out.println("Enter mobile number");
		    mobile=sc.next();
		    String sql="insert into user(name,username,password,email,mobile) values(?,?,?,?,?)";
		    PreparedStatement ps=con.prepareStatement(sql);
		    ps.setString(1,name);
		    ps.setString(2,username);
		    ps.setString(3,password);
		    ps.setString(4,email);
		    ps.setString(5,mobile);
		    
		    int i=ps.executeUpdate();
		    if(i==1)
		    {
		    	String host="smtp.gmail.com";   // Types of gmail sending hostname []
				  final String user="ganeshkumarmamidisetti99@gmail.com";//change accordingly  
				  final String password1="gkxd hwax gsfz fnck";//change accordingly  
				    
				  String to=email;//change accordingly  
				  
				   //Get the session object  
				   Properties props = new Properties();  
				   props.put("mail.smtp.host",host);  
				   props.put("mail.smtp.auth", "true");  
				   props.put("mail.smtp.starttls.enable", "true");
				   javax.mail.Session session = javax.mail.Session.getDefaultInstance(props,  
				    new javax.mail.Authenticator() {  
				      protected javax.mail.PasswordAuthentication getPasswordAuthentication() {  
				    return new javax.mail.PasswordAuthentication(user,password1);  
				      }  
				    });  
				  
				   //Compose the message  
				    try {  
				     MimeMessage message = new MimeMessage(session);  
				     message.setFrom(new InternetAddress(user));  
				     message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
				     message.setSubject("SBI-Online NetBanking System -");  
				     message.setText("Dear,\n "+name+" You're login Application request sent to Branch Manager\n Please Wait for the Confirmation Mail\n\n\n\n\n Thanks\n SBI-OnlineBanking System ");  
				       
				    //send the message  
				     Transport.send(message);  
				  
				     System.out.println("message sent successfully...");  
				   
				     }
				    catch (MessagingException e) {e.printStackTrace();}
		   }
		    else
		    {
		    	System.out.println("Register Application failed to sent");
		    }
		    
		    
		}
		catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	int verification()
	{
		String aadharcard,pancard,rationcard,status;
		int verified=0;
		Scanner din = new Scanner(System.in);
		System.out.println("Press Yes For Document submitted or Press Nofor Not-Submitted");
		System.out.println("Aadhar Card is Submitted-?");
		aadharcard=din.next();  // y
		System.out.println("Pan Card is Submitted-?");
		pancard=din.next(); // y
		System.out.println("Ration Card is Submitted-?");
		rationcard = din.next();  // n
		if(aadharcard.equalsIgnoreCase("yes"))
		{
			verified++;
		}
		if(pancard.equalsIgnoreCase("yes"))
		{
			verified++;
		}
		if(rationcard.equalsIgnoreCase("yes"))
		{
			verified++;
		}
		if(verified>=2)
		{
			status="Approved";
		}
		else
		{
			status="Rejected";
		}
		if(status.equalsIgnoreCase("Approved"))
		try
		{
			Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/netbank", "root", "root");
			String sql1="insert into userinfo values(?,?,?,?,?,?)";
			PreparedStatement ps = con1.prepareStatement(sql1);
			ps.setInt(1, id);
			ps.setString(2, aadharcard);
			ps.setString(3, pancard);
			ps.setString(4, rationcard);
			ps.setInt(5, verified);
			ps.setString(6, status);
			int i=ps.executeUpdate();
			if(i>0)
			{
				System.out.println("Documents Saved");
				//accountCreation();
			}
			else
			{
				System.out.println("Documents Not Saved");
			}	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 1;
			}
		//return verified;
	//}
	void accountCreation()
	{
		
	   String accounttype=null;
		double balance;
		String bank="SBI";
		int count=100;
		int value=0;
		try
		{

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/netbank", "root", "root");
			String sql="select count(*) from useraccount";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next())
			{
				value = rs.getInt(1);
			}
			count = count + value;
			String cnumber = bank + count;
			System.out.println(cnumber);
			Scanner din = new Scanner(System.in);
			System.out.println("Type savings For Savings Account\nType current For Current Account");
			String choice = din.next();
			if(choice.equalsIgnoreCase("savings"))
			{
				accounttype="Savings";
			}
			else if(choice.equalsIgnoreCase("current"))
			{
				accounttype="Current";
			}
			System.out.println("Enter your Transaction pinno");
			String transferpassword = din.next();
			System.out.println("Enter Minimum Balance To Create Account");
			balance = din.nextDouble();
			if(balance>=500)
			{
				String sql1="insert into useraccount values(?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql1);
				ps.setInt(1,id);
				ps.setString(2, cnumber);
				ps.setString(3, accounttype);
				ps.setDouble(4, balance);
				ps.setString(5,transferpassword);
				ps.setString(6,"activated");
				int i = ps.executeUpdate();
				if(i>0)
				{
					System.out.println("Dear Customer ! Your Account created Successfully.");
				       
				}
				else
				{
					System.out.println("Dear Customer!  Account Creation Failed..Try After Some Time.");
				}
			}
			else
			{
				System.out.println("Sorry !! minimum 500Rs to Create Account");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

}
}
