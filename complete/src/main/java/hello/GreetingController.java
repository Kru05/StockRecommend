package hello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import hello.vo.Company;
import  hello.vo.History;
import hello.vo.Recommend;
import  hello.vo.UserInfo;

@RestController
public class GreetingController {

	@Value("${application.DBURL}")
    private String DB_URL;
    @Value("${application.driver}")
    private String driver;
    
    @Value("${application.username}")
    private String USER;
    
    @Value("${application.password}")
    private String PASS;
	
		Connection conn=null;
		  Statement stmt = null;
		  Statement stmt1 = null;
		
		  @RequestMapping(value = "/knapsack", produces = MediaType.APPLICATION_JSON_VALUE)
			public List<Recommend> knapsack(@RequestParam(value = "userid") String userid, @RequestParam(value = "level") String level, @RequestParam(value = "total") String total) {
				
					try
				  {
					
					  Class.forName(driver);
					  conn=DriverManager.getConnection(DB_URL,USER,PASS);
					  stmt=conn.createStatement();
					  ResultSet rs=stmt.executeQuery("select * from users where userid='"+userid+"'");
					  String risk;
					  rs.next();
					  risk=rs.getString("Risk");
					  
					  rs=stmt.executeQuery("select count(*) as Large from company where type='L'");
					  rs.next();
					  int l=rs.getInt("Large");
					  rs=stmt.executeQuery("select count(*) as Med from company where type='M'");
					  rs.next();
					  int m=rs.getInt("Med");
					  
					  rs=stmt.executeQuery("select count(*) as Small from company where type='S'");
					  rs.next();
					  int s=rs.getInt("Small");
					  double ltotal = 0,mtotal=0,stotal=0;
					  if(risk.equalsIgnoreCase(("High")))
						{
							l=((l*10)/100);
							l=(int) Math.ceil(l);
							m=((m*30)/100);
							m=(int) Math.ceil(m);
							s=((s*60)/100);
							s=(int) Math.ceil(s);
							ltotal=0.10*Double.parseDouble(total);
							mtotal=0.30*Double.parseDouble(total);
							stotal=0.60*Double.parseDouble(total);
						}
						else if(risk.equalsIgnoreCase("Medium"))
						{
							l=((l*30)/100);
							l=(int) Math.ceil(l);
							m=((m*30)/100);
							m=(int) Math.ceil(m);
							s=((s*40)/100);
							s=(int) Math.ceil(s);
							ltotal=0.30*Double.parseDouble(total);
							mtotal=0.30*Double.parseDouble(total);
							stotal=0.40*Double.parseDouble(total);
						}
						else if(risk.equalsIgnoreCase("low"))
						{
							l=((l*60)/100);
							l=(int) Math.ceil(l);
							m=((m*30)/100);
							m=(int) Math.ceil(m);
							s=((s*10)/100);
							s=(int) Math.ceil(s);
							ltotal=0.60*Double.parseDouble(total);
							mtotal=0.30*Double.parseDouble(total);
							stotal=0.10*Double.parseDouble(total);
						} 
						  if(level.equalsIgnoreCase("basic"))
						  {
							  return knapsack(ltotal,mtotal,stotal,"risk1");  	  
						  }
						  else if(level.equalsIgnoreCase("intermediate"))
						  {
							  return knapsack(ltotal,mtotal,stotal,"risk4");
						  }
						  else if(level.equalsIgnoreCase("advance"))
						  {
							  return knapsack(ltotal,mtotal,stotal,"risk2");
						  }
						  else 
						  {
							  return knapsack(ltotal,mtotal,stotal,"risk3");
						  }
						  
						  
						  
					  }
						 
						    
					catch(Exception e)
						    {
						    	
						    }
		return null;		
	 }
						  
	public List<Recommend> knapsack(double ltotal,double mtotal,double stotal,String risk) throws ClassNotFoundException, SQLException
	{
		int id=0;
		List<Recommend> finalList =new ArrayList<Recommend>();
		ZeroOneKnapsack zok = new ZeroOneKnapsack((int) Math.ceil(ltotal));
		  Class.forName(driver);
		  conn=DriverManager.getConnection(DB_URL,USER,PASS);
		  stmt=conn.createStatement();
		  ResultSet rs11=stmt.executeQuery("select * from company where type='L' order by risk1 desc limit 30");
		  ResultSet rs1;
		  Statement stmt2 = conn.createStatement();
		  
		  while(rs11.next())
		  {
	    	   rs1=stmt2.executeQuery("select * from stocks where Code='"+rs11.getString("code")+"' and Date=(select max(Date)from stocks)");
	    
	    	   rs1.next();
	    	   zok.add(rs11.getString("code"),(int)rs1.getDouble("Close"),(int)rs11.getDouble(risk));
	    	
	       }
		  

	        // calculate the solution:
	        List<Item> itemList = zok.calcSolution();
	 
	        // write out the solution in the standard output
	        if (zok.isCalculated()) {
	            NumberFormat nf  = NumberFormat.getInstance();
	 
	         
	            for (Item item : itemList) {
	                if (item.getInKnapsack() == 1) {
	                    System.out.format(
	                        "%1$-23s %2$-3s %3$-5s %4$-15s \n",
	                        item.getName(),
	                        item.getWeight(), "dag  ",
	                        "(value = " + item.getValue() + ")"
	                    );
	                   Statement stmt3=conn.createStatement();
	                   Statement stmt4=conn.createStatement();
	                   ResultSet rs2=stmt3.executeQuery("select * from company where code='"+item.getName()+"'");
	                   ResultSet rs3=stmt4.executeQuery("select Close from stocks where Code='"+item.getName()+"' and date=(select max(date) from stocks)");
	                   rs2.next();
	                    rs3.next();
	                    Recommend r=new Recommend();
	                    id++;
	                    r.setId(id);
	                    r.setClose((float)rs3.getDouble("Close"));
	                    r.setCode(item.getName());
	                    r.setCompanyType(rs2.getString("type"));
	                    r.setName(rs2.getString("name"));
	                    r.setProfit(rs2.getFloat(risk));
	                    finalList.add(r);
	                }
	            }
	            
	        } else {
	            System.out.println(
	                "The problem is not solved. " +
	                "Maybe you gave wrong data."
	            );
	        }
	        System.out.println("first");
	        zok = new ZeroOneKnapsack((int) Math.ceil(mtotal));
	         rs11=stmt.executeQuery("select * from company where type='M' order by risk1 desc limit 30");
		     stmt2 = conn.createStatement();
		       while(rs11.next())
		       {
		    	   rs1=stmt2.executeQuery("select * from stocks where code='"+rs11.getString("code")+"' and Date=(select max(Date)from stocks)");
						rs1.next();
		    	   zok.add(rs11.getString("code"),(int)rs1.getDouble("close"),(int)rs11.getDouble(risk));
		       }
		      
		 
		        // calculate the solution:
		        itemList = zok.calcSolution();
		 
		        // write out the solution in the standard output
		        if (zok.isCalculated()) {
		            NumberFormat nf  = NumberFormat.getInstance();
		 
		            
		            for (Item item : itemList) {
		                if (item.getInKnapsack() == 1) {
		                    System.out.format(
		                        "%1$-23s %2$-3s %3$-5s %4$-15s \n",
		                        item.getName(),
		                        item.getWeight(), "dag  ",
		                        "(value = " + item.getValue() + ")"
		                    );
		                    Statement stmt3=conn.createStatement();
			                   Statement stmt4=conn.createStatement();
		                    ResultSet rs2=stmt3.executeQuery("select * from company where code='"+item.getName()+"'");
			                   ResultSet rs3=stmt4.executeQuery("select Close from stocks where Code='"+item.getName()+"' and date=(select max(date) from stocks)");
			                    rs2.next();
			                    rs3.next();
			                    Recommend r=new Recommend();
			                    id++;
			                    r.setId(id);
			                    r.setClose((float)rs3.getDouble("Close"));
			                    r.setCode(item.getName());
			                    r.setCompanyType(rs2.getString("type"));
			                    r.setName(rs2.getString("name"));
			                    r.setProfit(rs2.getFloat(risk));
			                   
			                    finalList.add(r);
		                }
		            }
		           
		        } else {
		            System.out.println(
		                "The problem is not solved. " +
		                "Maybe you gave wrong data."
		            );
		        }
		        zok = new ZeroOneKnapsack((int) Math.ceil(stotal));
		         rs11=stmt.executeQuery("select * from company where type='S' order by risk1 desc limit 30");
			     stmt2 = conn.createStatement();
			       while(rs11.next())
			       {
			    	   rs1=stmt2.executeQuery("select * from stocks where code='"+rs11.getString("code")+"' and Date=(select max(Date)from stocks)");
							rs1.next();
			    	   zok.add(rs11.getString("code"),(int)rs1.getDouble("close"),(int)rs11.getDouble(risk));
			       }
			      
			 
			        // calculate the solution:
			        itemList = zok.calcSolution();
			 
			        // write out the solution in the standard output
			        if (zok.isCalculated()) {
			            NumberFormat nf  = NumberFormat.getInstance();
			 
			            for (Item item : itemList) {
			                if (item.getInKnapsack() == 1) {
			                    System.out.format(
			                        "%1$-23s %2$-3s %3$-5s %4$-15s \n",
			                        item.getName(),
			                        item.getWeight(), "dag  ",
			                        "(value = " + item.getValue() + ")"
			                    );
			                    Statement stmt3=conn.createStatement();
				                   Statement stmt4=conn.createStatement();
			                    ResultSet rs2=stmt3.executeQuery("select * from company where code='"+item.getName()+"'");
				                   ResultSet rs3=stmt4.executeQuery("select Close from stocks where Code='"+item.getName()+"' and date=(select max(date) from stocks)");
				                    rs2.next();
				                    rs3.next();
				                    Recommend r=new Recommend();
				                    id++;
				                    r.setId(id);
				                    r.setClose((float)rs3.getDouble("Close"));
				                    r.setCode(item.getName());
				                    r.setCompanyType(rs2.getString("type"));
				                    r.setName(rs2.getString("name"));
				                    r.setProfit(rs2.getFloat(risk));
				                    finalList.add(r);
			                }
			            }
			            
			        } 
			        else {
			            System.out.println(
			                "The problem is not solved. " +
			                "Maybe you gave wrong data."
			            );
			        }
			    	return finalList;     
	
	}
						  

	@RequestMapping(value = "/login", produces = "application/json")
	public String  login(@RequestParam String username ,@RequestParam String password) {
		
		  try
		  {
			 
			  System.out.print("Entered in login");
			  Class.forName(driver);
			  conn=DriverManager.getConnection(DB_URL,USER,PASS);
			  stmt=conn.createStatement();
			  ResultSet rs=stmt.executeQuery("select * from users");
			  UserInfo userinfo=new UserInfo();
			  ObjectMapper mapper = new ObjectMapper();
			  int flag=0;
			  while(rs.next())
			  {
			  if (password.equals(rs.getString("Password")) && username.equals(rs.getString("Username")))
			  {
				  flag=1;
				  userinfo.setUserName(username);
				  userinfo.setId(rs.getInt("Userid"));
				  break;
			  }
			  }
			  conn.close();
			 if(flag==1)
			 {
				 String msg=mapper.writeValueAsString(userinfo);
				 return msg;
			 }
			 else
			 {
				 String msg=mapper.writeValueAsString(userinfo);
				 return msg;
			 }
			 
			 
		  }
		  catch(Exception e)
		  {
			  System.out.println(e);
		  }
		  return null;
	}
	@RequestMapping(value = "/recommend", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<Recommend> recommend(@RequestParam(value = "userid") String userid, @RequestParam(value = "level") String level) {
		
			ArrayList <Recommend> rlist=new ArrayList<Recommend>();
		  try
		  {
			  Class.forName(driver);
			  conn=DriverManager.getConnection(DB_URL,USER,PASS);
			  stmt=conn.createStatement();
			  ResultSet rs=stmt.executeQuery("select * from users where userid='"+userid+"'");
			  String risk;
			  rs.next();
			  risk=rs.getString("Risk");
			  
			  rs=stmt.executeQuery("select count(*) as Large from company where type='L'");
			  rs.next();
			  int l=rs.getInt("Large");
			  rs=stmt.executeQuery("select count(*) as Med from company where type='M'");
			  rs.next();
			  int m=rs.getInt("Med");
			  
			  rs=stmt.executeQuery("select count(*) as Small from company where type='S'");
			  rs.next();
			  int s=rs.getInt("Small");
			  
			  if(risk.equalsIgnoreCase(("High")))
				{
					l=((l*10)/100);
					l=(int) Math.ceil(l);
					m=((m*30)/100);
					m=(int) Math.ceil(m);
					s=((s*60)/100);
					s=(int) Math.ceil(s);
				}
				else if(risk.equalsIgnoreCase("Medium"))
				{
					l=((l*30)/100);
					l=(int) Math.ceil(l);
					m=((m*30)/100);
					m=(int) Math.ceil(m);
					s=((s*40)/100);
					s=(int) Math.ceil(s);
				}
				else if(risk.equalsIgnoreCase("low"))
				{
					l=((l*60)/100);
					l=(int) Math.ceil(l);
					m=((m*30)/100);
					m=(int) Math.ceil(m);
					s=((s*10)/100);
					s=(int) Math.ceil(s);
				} 
				  if(level.equalsIgnoreCase("basic"))
				  {// select * from company where type='L' order by risk1 desc;	
					  			System.out.print(l);
					  			System.out.print("\t"+m);
					  			System.out.print("\t"+s);
					  			ResultSet rs12;
				  				rs=stmt.executeQuery("select * from company where type='L' order by risk1 desc limit "+l);
				  				while(rs.next())
				  				{
				  					Recommend r=new Recommend();
				  					r.setId(rs.getInt("compno"));
				  					r.setCode(rs.getString("code"));
				  					r.setCompanyType(rs.getString("type"));
				  					r.setName((rs.getString("name")));
				  					r.setProfit(rs.getFloat("risk1"));
				  					PreparedStatement prep_stmt;
				  					prep_stmt=conn.prepareStatement("select Close from stocks where Code=? and date=(select max(date) from stocks)");
				  					prep_stmt.setString(1,rs.getString("code"));
				  					rs12=prep_stmt.executeQuery();
				  					rs12.next();
				  					r.setClose(rs12.getFloat("Close"));
				  					rlist.add(r);
				  				}
				  				rs=stmt.executeQuery("select * from company where type='M' order by risk1 desc limit "+m);
				  				while(rs.next())
				  				{
				  					Recommend r=new Recommend();
				  					r.setId(rs.getInt("compno"));
				  					r.setCode(rs.getString("code"));
				  					r.setCompanyType(rs.getString("type"));
				  					r.setName((rs.getString("name")));
				  					r.setProfit(rs.getFloat("risk1"));
				  					PreparedStatement prep_stmt;
				  					prep_stmt=conn.prepareStatement("select Close from stocks where Code=? and date=(select max(date) from stocks)");
				  					prep_stmt.setString(1,rs.getString("code"));
				  					rs12=prep_stmt.executeQuery();
				  					rs12.next();
				  					r.setClose(rs12.getFloat("Close"));
				  					rlist.add(r);
				  				}
				  				rs=stmt.executeQuery("select * from company where type='S' order by risk1 desc limit "+s);
				  				while(rs.next())
				  				{
				  					Recommend r=new Recommend();
				  					r.setId(rs.getInt("compno"));
				  					r.setCode(rs.getString("code"));
				  					r.setCompanyType(rs.getString("type"));
				  					r.setName((rs.getString("name")));
				  					r.setProfit(rs.getFloat("risk1"));
				  					PreparedStatement prep_stmt;
				  					prep_stmt=conn.prepareStatement("select Close from stocks where Code=? and date=(select max(date) from stocks)");
				  					prep_stmt.setString(1,rs.getString("code"));
				  					rs12=prep_stmt.executeQuery();
				  					rs12.next();
				  					r.setClose(rs12.getFloat("Close"));
				  					rlist.add(r);
				  				}
			  					return rlist;
				  }
			 
				 
		  }
		  catch(Exception e)
		  {
			  System.out.println(e);
		  }
		  return null;
	}
	@RequestMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public int save(@RequestParam(value = "userid") String userid, @RequestParam(value = "code") String code,@RequestParam(value = "date") String date,@RequestParam(value = "close") String close) {
		
		PreparedStatement stmt1;
		  try
		  {
			  System.out.println("Entered in save");
			  Class.forName(driver);
			  conn=DriverManager.getConnection(DB_URL,USER,PASS);
			  stmt1=conn.prepareStatement("insert into history values(?,?,?,?)");
			  stmt1.setInt(1,Integer.parseInt(userid));
			  stmt1.setString(2,code);
			  stmt1.setString(3,date);
			  stmt1.setDouble(4,Double.parseDouble(close));
			  int i=stmt1.executeUpdate();
			  return i;
		  }
		  catch(Exception e)
		  {
			  System.out.print(e);;
		  }
		return 0;
	}
	@RequestMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public int delete(@RequestParam(value = "userid") String userid, @RequestParam(value = "code") String code,@RequestParam(value = "date") String date,@RequestParam(value = "close") String close) {
		System.out.println("Delete");
		PreparedStatement stmt1;
		  try
		  {
			  Class.forName(driver);
			  conn=DriverManager.getConnection(DB_URL,USER,PASS);
			  stmt1=conn.prepareStatement("delete from history where Userid= ? and Code = ? and Date= ? and Close= ?");
			  stmt1.setInt(1,Integer.parseInt(userid));
			  stmt1.setString(2,code);
			  stmt1.setString(3,date);
			  stmt1.setDouble(4,Double.parseDouble(close));
			  int i=stmt1.executeUpdate();
			  return i;
		  }
		  catch(Exception e)
		  {
			  System.out.print(e);;
		  }
		return 0;
	}
	@RequestMapping(value = "/getCompanyList", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<Company> login1() {
		ArrayList<Company> companyList = new ArrayList<Company>();
		
		 try
		  {
			  Class.forName(driver);
			  conn=DriverManager.getConnection(DB_URL,USER,PASS);
			  stmt=conn.createStatement();
			  ResultSet rs=stmt.executeQuery("select * from company");
			  
			  while(rs.next())
			  {
				  Company c=new Company();
				  c.setId(rs.getInt("compno"));
				  c.setName(rs.getString("name"));
				  c.setCode(rs.getString("code"));
				  c.setCompanyType(rs.getString("type"));
				  c.setLastUpdated((rs.getString("updateDate")));
				  c.setActiveStatus((rs.getString("active")));
				  companyList.add(c);
			  }
			  conn.close();
			  return companyList;
		  }
		  catch(Exception e)
		  {
			  System.out.println(e);
		  }
		return companyList;
	}
	@RequestMapping(value = "/stockGraph", produces = MediaType.APPLICATION_JSON_VALUE)
	public float[] graph(@RequestParam(value = "code") String code) {
		float a[]=new float[10];
		
		 try
		  {
			  Class.forName(driver);
			  conn=DriverManager.getConnection(DB_URL,USER,PASS);
			  stmt=conn.createStatement();
			  ResultSet rs=stmt.executeQuery(" select  * from  stocks where Code='"+code+"'"
			  		);
			  int i=0;
			  while(rs.next())
			  {
				  a[i]=(float)rs.getDouble("Close");
				 i++;
			  }
			  conn.close();
			  return a;
		  }
		  catch(Exception e)
		  {
			  System.out.println(e);
		  }
		return a;
	}
	@RequestMapping(value = "/getSavedCompanyForUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<History> login2(@RequestParam(value = "id") String id) {
		ArrayList<History> companyList = new ArrayList<History>();
		System.out.println("getSavedCompanyForUser");
		 try
		  {
			  Class.forName(driver);
			  conn=DriverManager.getConnection(DB_URL,USER,PASS);
			  stmt=conn.createStatement();
			  stmt1=conn.createStatement();
			  ResultSet rs=stmt.executeQuery("select * from history where Userid="+id);
			  
			  while(rs.next())
			  {
				  
				  History h=new History();
				  h.setClose(rs.getBigDecimal("Close"));
				  h.setCode(rs.getString("Code"));
				  h.setDate(rs.getString("Date"));
				  ResultSet rs1=stmt1.executeQuery("select * from company where code = '"+rs.getString("Code")+"'");
				 rs1.next();
				  h.setCompanyType(rs1.getString("type"));
				  h.setName(rs1.getString("name"));
				  companyList.add(h);
			  }
			  conn.close();
			  return companyList;
		  }
		  catch(Exception e)
		  {
			  System.out.println(e);
		  }
		return companyList;
	}

	@RequestMapping(value = "/getUserProfile", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserInfo getusers(@RequestParam(value = "id") int id,@RequestParam(value = "username") String username) {
		UserInfo h=new UserInfo();
		 try
		  {
			 Class.forName(driver);
			  conn=DriverManager.getConnection(DB_URL,USER,PASS);
			  stmt=conn.createStatement();
			  stmt1=conn.createStatement();
			  ResultSet rs=stmt.executeQuery("select * from users where Userid="+id+" and Username='"+username+"'");
			  
			  while(rs.next())
			  {
				  
				  
				 h.setDOB(rs.getString("DOB"));
				 h.setEmail(rs.getString("Email"));
				 h.setGender(rs.getString("Gender"));
				 h.setPassword(rs.getString("Password"));
				 h.setRisk(rs.getString("Risk"));
				  
			  }
			  conn.close();
			  System.out.println("Data Returned");
			  return h;
			  
		  }
		  catch(Exception e)
		  {
			  System.out.println(e);
		  }
		return h;
	}

	
	@RequestMapping(value = "/setProfile", produces = MediaType.APPLICATION_JSON_VALUE)
	public int login2(@RequestParam(value = "username") String username,@RequestParam(value = "password") String password,@RequestParam(value = "gender") String gender,@RequestParam(value = "DOB") String DOB,@RequestParam(value = "email") String email,@RequestParam(value = "risk") String risk) {
		PreparedStatement stmt1;
		  try
		  {
			  System.out.println("set profile");
			  Class.forName(driver);
			  conn=DriverManager.getConnection(DB_URL,USER,PASS);
			  stmt1=conn.prepareStatement("insert into users values(null,?,?,?,?,?,?)");
			  //stmt1.setInt(1,3);
			  stmt1.setString(1,username);
			  stmt1.setString(2,password);
			  stmt1.setString(3,gender);
			  stmt1.setString(4,DOB);
			  stmt1.setString(5,email);
			  stmt1.setString(6,risk);
			  int i=stmt1.executeUpdate();
			  return i;
		  }
		  catch(Exception e)
		  {
			  System.out.print(e);
		  }
		return 0;
			
	}
	@RequestMapping(value = "/updateProfile", produces = MediaType.APPLICATION_JSON_VALUE)
	public int update(@RequestParam(value = "userid") int userid,@RequestParam(value = "username") String username,@RequestParam(value = "password") String password,@RequestParam(value = "gender") String gender,@RequestParam(value = "DOB") String DOB,@RequestParam(value = "email") String email,@RequestParam(value = "risk") String risk) {
		PreparedStatement stmt1;
		  try
		  {
			  System.out.println("update profile");
			  Class.forName(driver);
			  conn=DriverManager.getConnection(DB_URL,USER,PASS);
			  stmt1=conn.prepareStatement("update users set Password=?, Gender=? ,DOB=? ,Email=? ,Risk=? where Userid=? and Username=?");
			  
			  stmt1.setString(1,password);
			  stmt1.setString(2,gender);
			  stmt1.setString(3,DOB);
			  stmt1.setString(4,email);
			  stmt1.setString(5,risk);
			  stmt1.setInt(6,userid);
			  stmt1.setString(7,username);
			  int i=stmt1.executeUpdate();
			  return i;
		  }
		  catch(Exception e)
		  {
			  System.out.print(e);
		  }
		return 0;
			
	}
	
}
