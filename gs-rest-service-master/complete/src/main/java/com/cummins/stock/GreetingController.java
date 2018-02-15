package com.cummins.stock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cummins.stock.vo.Company;
import com.cummins.stock.vo.History;
import com.cummins.stock.vo.Stock;
import com.cummins.stock.vo.UserInfo;

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
	@RequestMapping("/greeting")
	public String greeting(@RequestParam(value = "name") String name) {
		return "hello"+name;
	}
	@RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserInfo login(@RequestParam(value = "user") String user, @RequestParam(value = "pass") String password) {
		
 
		  try
		  {
			  Class.forName(driver);
			  conn=DriverManager.getConnection(DB_URL,USER,PASS);
			  stmt=conn.createStatement();
			  ResultSet rs=stmt.executeQuery("select * from users");
			  UserInfo userinfo=new UserInfo();
			  int flag=0;
			  while(rs.next())
			  {
			  if (password.equals(rs.getString("Password")) && user.equals(rs.getString("Username")))
			  {
				  flag=1;
				  userinfo.setUserName(rs.getString("Username"));
				  userinfo.setPassword(rs.getString("Password"));
				  userinfo.setId(Integer.parseInt(rs.getString("Userid")));
				  userinfo.setGender(rs.getString("Gender"));
				  userinfo.setDOB(rs.getString("DOB"));
				  userinfo.setEmail(rs.getString("Email"));
				  userinfo.setRisk(rs.getString("Risk"));
				  break;
			  }
			  }
			  conn.close();
			 if(flag==1)
				 return userinfo;
		  }
		  catch(Exception e)
		  {
			  System.out.println(e);
		  }
		  return null;
	}
	@RequestMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public int save(@RequestParam(value = "userid") int userid, @RequestParam(value = "code") String code,@RequestParam(value = "date") String date,@RequestParam(value = "close") double close) {
		
		PreparedStatement stmt1;
		  try
		  {
			  Class.forName(driver);
			  conn=DriverManager.getConnection(DB_URL,USER,PASS);
			  stmt1=conn.prepareStatement("insert into history values(?,?,?,?)");
			  stmt1.setInt(1,userid);
			  stmt1.setString(2,code);
			  stmt1.setString(3,date);
			  stmt1.setDouble(4,close);
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
			  ResultSet rs=stmt.executeQuery("select * from Company");
			  
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
	public ArrayList<Stock> graph(@RequestParam(value = "start") String start,@RequestParam(value = "end") String end,@RequestParam(value = "code") String code) {
		ArrayList<Stock> companyList = new ArrayList<Stock>();
		
		 try
		  {
			  Class.forName(driver);
			  conn=DriverManager.getConnection(DB_URL,USER,PASS);
			  stmt=conn.createStatement();
			  ResultSet rs=stmt.executeQuery(" select  * from  stocks" + 
			  		" where  Date >='"+start+"' and Date  <= '"+end+"' and Code='"+code+"'"
			  		);
			  
			  while(rs.next())
			  {
				  Stock h=new Stock();
				  h.setClose(rs.getDouble("Close"));
				  h.setCode(rs.getString("Code"));
				  h.setDate(rs.getString("Date"));
				  h.setHigh(rs.getDouble("High"));
				  h.setLast(rs.getDouble("Last"));
				  h.setLow(rs.getDouble("Low"));
				  h.setOpen(rs.getDouble("Open"));
				  h.setTotal_Trade_Quantity(rs.getBigDecimal("Total_Trade_Quantity"));
				  h.setTurnover(rs.getBigDecimal("Turnover"));
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
	@RequestMapping(value = "/getSavedCompanyForUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<History> login2(@RequestParam(value = "id") int id) {
		ArrayList<History> companyList = new ArrayList<History>();
		
		 try
		  {
			  Class.forName(driver);
			  conn=DriverManager.getConnection(DB_URL,USER,PASS);
			  stmt=conn.createStatement();
			  stmt1=conn.createStatement();
			  ResultSet rs=stmt.executeQuery("select * from History where Userid="+id);
			  
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

	@RequestMapping(value = "/setProfile", produces = MediaType.APPLICATION_JSON_VALUE)
	public int login2(@RequestParam(value = "username") String username,@RequestParam(value = "password") String password,@RequestParam(value = "gender") String gender,@RequestParam(value = "DOB") String DOB,@RequestParam(value = "email") String email,@RequestParam(value = "risk") String risk) {
		PreparedStatement stmt1;
		  try
		  {
			  Class.forName(driver);
			  conn=DriverManager.getConnection(DB_URL,USER,PASS);
			  stmt1=conn.prepareStatement("insert into users values(?,?,?,?,?,?,?)");
			  stmt1.setInt(1,3);
			  stmt1.setString(2,username);
			  stmt1.setString(3,password);
			  stmt1.setString(4,gender);
			  stmt1.setString(5,DOB);
			  stmt1.setString(6,email);
			  stmt1.setString(7,risk);
			  int i=stmt1.executeUpdate();
			  return i;
		  }
		  catch(Exception e)
		  {
			  System.out.print(e);;
		  }
		return 0;
			
	}
	/*@RequestMapping(value = "/getSavedCompanyForUser", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public List<Company> login2(@RequestBody UserInfo user) {
		List<Company> companyList = new ArrayList<Company>();
		Company c1 = new Company();
		c1.setName("APPLLE");
		c1.setCode("AAPL");
		c1.setActiveStatus("Y");
		//c1.setLastUpdated(new Date());
		c1.setCompanyType("L");
		companyList.add(c1);
		
		c1.setName("GOOGLE");
		c1.setCode("GOOG");
		c1.setActiveStatus("Y");
		//c1.setLastUpdated(new Date());
		c1.setCompanyType("M");
		companyList.add(c1);
		return companyList;
	}*/
	
}
