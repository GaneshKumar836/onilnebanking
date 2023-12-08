import java.nio.channels.AcceptPendingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Adminlogin {

	void login()
	{
		Scanner sc=new Scanner(System.in);
		String username,password;
		System.out.println("Enter username & password");
		username=sc.next();//ganesh
		password=sc.next();//1234
		try
		{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/netbank","root","root");
			String sql="select * from admin ";
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			
			while(rs.next())
			{
				if(username.equalsIgnoreCase(rs.getString(2)) && password.equalsIgnoreCase(rs.getString(3)))
				{
					System.out.println("Welcome Admin ! Hi");
					process();
				}
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	void process()
	{
		Scanner sc=new Scanner(System.in);
		String choice;
		String option="yes";
		while(option.equalsIgnoreCase("yes"))
		{
			System.out.println("View Customer Application !");
			System.out.println("View Customer History !");
			System.out.println("Enter your option");
			choice=sc.next();
			
			switch(choice)
			{
			case "viewcustomerapplication":customerApplication();
				break;
			case "viewcustomerhistory":customerHistory();
			break;
			}
			
		System.out.println("Do you want to continue press yes or Exit press no");	
		option=sc.next();
		}
	}
	
	void customerApplication()
	{
		Scanner sc=new Scanner(System.in);
		String status;
		int uid;
		try 
		{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/netbank","root","root");
			String sql="select * from user";
			Statement st1=con.createStatement();
			ResultSet rs1=st1.executeQuery(sql);

			System.out.println("id\t"+"name\t"+"username\t"+"email\t"+"mobile\t"+"status\t");
			while(rs1.next())
			{
				System.out.println(rs1.getInt(1)+"\t"+rs1.getString(2)+"\t"+rs1.getString(3)+"\t"+rs1.getString(5)+"\t"+rs1.getString(6)+"\t"+rs1.getString(7));
			}
			System.out.println("Hi Admin !. Please any user to Approve.");
			System.out.println("Enter userid ");
			uid=sc.nextInt();
			String sql2="select * from user where id='"+uid+"'";
			Statement st2=con.createStatement();
			ResultSet rs2=st2.executeQuery(sql2);
			if(rs2.next())
			{
				 String name=rs2.getString(2); 
			     String email=rs2.getString(5);
				System.out.println("user id is found for an action !");
				System.out.println("Approve press yes or Reject press no");
				status=sc.next();
				if(status.equalsIgnoreCase("yes"))
				{
					status="yes";
				}
				else
				{
					status="no";
				}
				String sql3="update user set status='"+status+"' where id='"+uid+"'";
				PreparedStatement ps3=con.prepareStatement(sql3);
				int i=ps3.executeUpdate();
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
					     message.setText("Dear,\n "+name+" Welcome to SBI Bank.\nYou're Application is verified Successfully.\n Please login with your credantials.\n and submit all required documents.\n\n\n\n\n Thanks\n SBI-OnlineBanking System ");  
					       
					    //send the message  
					     Transport.send(message);  
					  
					     System.out.println("message sent successfully...");  
					   
					     }
					    catch (MessagingException e) {e.printStackTrace();}
				}
				else
				{
					System.out.println("User application is Not Approved !");
				}
			}
			else
			{
				System.out.println("user id is not found !");
			}
		} 
		
		catch (SQLException e) {

			e.printStackTrace();
		}
		
	}
	void customerHistory()
	{
		try 
		{
	   
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/netbank","root","root");
		String sql3="select * from customer_activity";
		Statement st3 = con.createStatement();
		ResultSet rs3 = st3.executeQuery(sql3);
		System.out.println("id\t"+"account number\t"+"balance\t"+"Transaction\t"+"Date&Time");
		System.out.println("****************************************************************");
		
			while(rs3.next())
			{
				System.out.println("*"+rs3.getInt(1)+"\t"+rs3.getString(2)+"\t"+rs3.getDouble(3)+"\t"+rs3.getString(4)+"\t"+rs3.getTimestamp(5)+"*");
			}
			System.out.println("****************************************************************");
	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
}
}