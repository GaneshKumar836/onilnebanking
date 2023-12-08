import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Verifiedusers
{
	void dashboard()
	{
		Connection con=null;
		String customeraccountnumber;
		int option;
		String newTransactionpassword,oldTransactionpassword;
		System.out.println("*****************************************");
		System.out.println("****** Welcome To Dashboard********");
		System.out.println("*****************************************");
		String choice="y";
		while(choice.equalsIgnoreCase("y"))
		{
			System.out.println("1.Balance EnQuiry");
			System.out.println("2.Deposit Amount");
			System.out.println("3.WithDrawl Amount");
			System.out.println("4.Transfer Amount");
			System.out.println("5.Change Transaction Password");
			System.out.println("6.account statement");
			System.out.println("7.Exit");
			Scanner din = new Scanner(System.in);
			System.out.println("Select Your Process");
			option = din.nextInt();
			int id = 0;
			switch(option)
			{
			case 1:
				System.out.println("******** Balance EnQuiry Process*********");
				System.out.println("Enter Customer Account number");
				customeraccountnumber = din.next();
				try
				{
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/netbank", "root", "root");
					String sql="select * from useraccount where accountno='"+customeraccountnumber+"'";
					//String sql="select * from customeraccount ";
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(sql);
					if(rs.next())
					{
						System.out.println("Enter Transaction Password");
						newTransactionpassword = din.next();
						int id1 =rs.getInt(1);
						String sql1="select * from useraccount where transferpassword='"+newTransactionpassword+"' and id='"+id1+"'";
						Statement st1 = con.createStatement();
						ResultSet rs1 = st1.executeQuery(sql1);
						if(rs1.next())
						{
							System.out.println("Your Balance Amount is:-  " + rs1.getDouble(4));
						}
						else
						{
							System.out.println("Transaction Password is InCorrect");
						}
					}
					else
					{
						System.out.println("Sorry !!! Customer Account number is InCorrect");
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				break;
			case 2:
				double depositamount;
				System.out.println("******** Deposit Amount Process*********");
				System.out.println("Enter Customer Account number");
				customeraccountnumber = din.next();
				try
				{
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/netbank", "root", "root");
					String sql="select * from useraccount where accountno='"+customeraccountnumber+"'";
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(sql);
					if(rs.next())
					{
						System.out.println("Enter Transaction Password");
						newTransactionpassword = din.next();
						int id1=rs.getInt(1);
						String sql1="select * from useraccount where transferpassword='"+newTransactionpassword+"' and id='"+id1+"'";
						Statement st1 = con.createStatement();
						ResultSet rs1 = st1.executeQuery(sql1);
						if(rs1.next())
						{
							double presentamount=rs1.getDouble(4);
							System.out.println("Enter Your Deposit Amount");
							depositamount = din.nextDouble();
							double totalamount = presentamount + depositamount;
							String sql2="update useraccount set balance='"+totalamount+"' where id='"+id1+"'";
							PreparedStatement ps = con.prepareStatement(sql2);
							int i=ps.executeUpdate();
							if(i>0)
							{
								System.out.println("Your Amount Deposited in Your Account Successfully");
								//System.out.println("Your Balance is : - " + rs1.getDouble(4));
								// Code Here - Display updated amount //
							}
							else
							{
								System.out.println("Amount is not Deposited");
							}
						}
						else
						{
							System.out.println("Transaction Password is InCorrect");
						}
					}
					else
					{
						System.out.println("Sorry !!! Customer Account number is InCorrect");
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				break;
			case 3:
				double withdrawlamount,gamount;
				System.out.println("******** WithDrawl Amount Process*********");
				System.out.println("Enter Customer Account number");
				customeraccountnumber = din.next();
				try
				{
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/netbank", "root", "root");
					String sql="select * from useraccount where accountno='"+customeraccountnumber+"'";
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(sql);
					if(rs.next())
					{
						System.out.println("Enter Transaction Password");
						newTransactionpassword = din.next();
						int id1=rs.getInt(1);
						String sql1="select * from useraccount where transferpassword='"+newTransactionpassword+"' and id='"+id1+"'";
						Statement st1 = con.createStatement();
						ResultSet rs1 = st1.executeQuery(sql1);
						if(rs1.next())
						{
							System.out.println("Enter WithDrawl Amount");
							withdrawlamount = din.nextDouble();
							String sql2="select * from useraccount where id='"+id1+"'";
							Statement st2 = con.createStatement();
							ResultSet rs2 = st2.executeQuery(sql2);
							if(rs2.next())
							{
								gamount = rs2.getDouble(4);
								
								if(withdrawlamount > gamount) // 5001 > 5001 // Code Here minimum Balance maintaineance
								{
									System.out.println("Insufficient Balance Amount");
								}
								else
								{
									double uamount = gamount - withdrawlamount;
									String sql3="update useraccount set balance='"+uamount+"' where id='"+id1+"'";
									PreparedStatement ps = con.prepareStatement(sql3);
									int i= ps.executeUpdate();
									if(i>0)
									{
										System.out.println("Please Wait Your Transaction is Processing ..");
										System.out.println("Please Take Your Cash");
									}
									else
									{
										System.out.println("WithDrawl Failed.. Due to Some issues");
									}
								}
							}
						}
						else
						{
							System.out.println("Transaction Password is InCorrect");
						}
					}
					else
					{
						System.out.println("Sorry !!! Customer Account number is InCorrect");
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				break;
			case 4:
				String transfernumber;
				double transferamount;
				System.out.println("******** Balance EnQuiry Process*********");
				System.out.println("Enter Customer Account number");
				customeraccountnumber = din.next();
				try
				{
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/netbank", "root", "root");
					String sql="select * from useraccount where accountno='"+customeraccountnumber+"'";
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(sql);
					if(rs.next())
					{
						System.out.println("Enter Transaction Password");
						newTransactionpassword = din.next();
						int id1=rs.getInt(1);
						String sql1="select * from useraccount where transferpassword='"+newTransactionpassword+"' and id='"+id1+"'";
						Statement st1 = con.createStatement();
						ResultSet rs1 = st1.executeQuery(sql1);
						if(rs1.next())
						{
							System.out.println("Enter Transfer Account Number");
							transfernumber = din.next(); // SBI101
							String sql2="select * from useraccount where accountno='"+transfernumber+"'";
							Statement st2 = con.createStatement();
							ResultSet rs2 = st2.executeQuery(sql2);
							
							if(rs2.next())
							{
								double vamount = rs2.getDouble(4);
								System.out.println("Transfer Account is Verified");
								System.out.println("Enter Transfer Amount");
								transferamount = din.nextDouble();
								String sql3="select * from useraccount where id='"+id1+"'";
								Statement st3 = con.createStatement();
								ResultSet rs3 = st3.executeQuery(sql3);
								if(rs3.next())
								{
									gamount = rs3.getDouble(4);
									if(transferamount > gamount) // 5001 > 5001 // Code Here minimum Balance maintaineance
									{
										System.out.println("Insufficient Balance Amount to Transfer");
									}
									else
									{
										double person1 = gamount - transferamount;
										double person2 = vamount + transferamount;
										String sql4="update useraccount set balance='"+person1+"' where id='"+id1+"'";
										PreparedStatement ps4 = con.prepareStatement(sql4);
										int i = ps4.executeUpdate();
										String sql5="update useraccount set balance='"+person2+"' where accountno='"+transfernumber+"'";
										PreparedStatement ps5 = con.prepareStatement(sql5);
										int j = ps5.executeUpdate();
										if(i>0 && j>0)
										{
											System.out.println("Transfer Account Successfully Sent");
										}
										else
										{
											System.out.println("Transaction failed");
										}
									}
								}
							}
							else
							{
								System.out.println("Transfer Account is not Verified");
							}
						}
						else
						{
							System.out.println("Transaction Password is InCorrect");
						}
					}
					else
					{
						System.out.println("Sorry !!! Customer Account number is InCorrect");
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				break;
			case 5: System.out.println("***** Update / Change Transaction Password*****");
			        System.out.println("Enter Old Transaction Password");
			        oldTransactionpassword=din.next();
			        String sql="select * from useraccount where txnpass='"+oldTransactionpassword+"' and id='"+id+"'";
				try {
					Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/netbank", "root", "root");
					Statement st = con1.createStatement();
					ResultSet rs = st.executeQuery(sql);
					if(rs.next())
					{
						System.out.println("Enter New Transaction Password");
						newTransactionpassword=din.next();
						String sql1="update useraccount set transferpassword='"+newTransactionpassword+"' where id='"+id+"'";
						PreparedStatement ps = con1.prepareStatement(sql1);
						int i=ps.executeUpdate();
						if(i>0)
						{
							System.out.println("Successfully Transaction Password is Updated");
						}
						
					}
					else
					{
						System.out.println("Transaction password is not matched");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			        
				break;
			case 6:System.out.println("*****account statement****");
			System.out.println("Enter Customer Account number");
			customeraccountnumber = din.next();
			try
			{
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/netbank", "root", "root");
				String sql1="select * from useraccount where accountno='"+customeraccountnumber+"'";
				//String sql="select * from customeraccount ";
				Statement st1 = con.createStatement();
				ResultSet rs1 = st1.executeQuery(sql1);
				if(rs1.next())
				{
					System.out.println("Enter Transaction Password");
					newTransactionpassword = din.next();
					int id1 =rs1.getInt(1);
					String sql2="select * from useraccount where transferpassword='"+newTransactionpassword+"' and id='"+id1+"'";
					Statement st2 = con.createStatement();
					ResultSet rs2 = st2.executeQuery(sql2);
					if(rs2.next())
					{
						String sql3="select * from customer_activity where  cid='"+id1+"'";
						Statement st3 = con.createStatement();
						ResultSet rs3 = st3.executeQuery(sql3);
						if(rs3.next())
						{
							int i=0;
							while(i<=rs3.getInt(1))
							{
							//System.out.println("id\t"+"account number\t"+"balance\t"+"Transaction\t"+"Date&Time");
							//System.out.println("****************************************************************");
							System.out.println("*"+rs3.getInt(1)+"\t"+rs3.getString(2)+"\t"+rs3.getDouble(3)+"\t"+rs3.getString(4)+"\t"+rs3.getTimestamp(5)+"*");
						    //System.out.println("****************************************************************");
							i++;
					       //}
						}
					}
					else
					{
						System.out.println("Transaction Password is InCorrect");
					}
				}
				else
				{
					System.out.println("Sorry !!! Customer Account number is InCorrect");
				}
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			        
				break;
			case 7:System.exit(1);
			}
			System.out.println("Do You Want To Continue Press Y Or Exit Press N");
			choice=din.next();
		}

	}
}

	
	 /*void verifieduser()
	{
		 int option;
		 String choice;
	try
	{
		choice="y";
		Scanner sc=new Scanner(System.in);
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/netbank","root","root");
		String sql="select * from userinfo where aadharcard='yes' and pancard='yes' or rationcard='yes'";
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(sql);
		if(rs.next())
		{
			System.out.println("*****Welcome to Homepage*******");
			System.out.println("1.Balance Enuiry");
			System.out.println("2.Transfer Amount");
			System.out.println("3.Statement");
			System.out.println("4.Pay bills");
			System.out.println("5.Exit");
			option=sc.nextInt();
			switch(option)
			{
			case 1:
				try
				{
					String accountno;
					int id=rs.getInt(1);
				    System.out.println("you are selected Balance Enquiry");
				   // Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/netbank","root","root");
				    String sql1="select * from useraccount where id='"+id+"'";
				    Statement st1=con.createStatement();
				    ResultSet rs1=st1.executeQuery(sql1);
				    
				    if(rs1.next())
				    {
				    	double balance=rs1.getDouble(4);
				    	String sql2="select * from useraccount where balance='"+balance+"'";
				    	 Statement st2=con.createStatement();
						    ResultSet rs2=st2.executeQuery(sql2);
						    
						    if(rs2.next())
						    {
						    	double cbalance=rs2.getDouble(4);
						    	System.out.println("your balance is"+cbalance);
						    }
				    }
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				break;
			case 2:try
			{
				String accountno;
				int id=rs.getInt(1);
			    System.out.println("you are selected Transfer Amount");
			   // Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/netbank","root","root");
			    String sql1="select * from useraccount where id='"+id+"'";
			    Statement st1=con.createStatement();
			    ResultSet rs1=st1.executeQuery(sql1);
			    
			    if(rs1.next())
			    {
			    	double balance=rs1.getDouble(4);
			    	String sql2="select * from useraccount where balance='"+balance+"'";
			    	 Statement st2=con.createStatement();
					    ResultSet rs2=st2.executeQuery(sql2);
					    
					    if(rs2.next())
					    {
					    	//String sql="insert into "
					    }
			    }
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
				  
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:System.exit(5);
			}
		}
		System.out.println("Do you want to continue again ! press y ");
				choice=sc.next();
				
		}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
}*/