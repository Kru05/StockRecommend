package com.memorynotfound;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class LinearRegression {

    public static void main(String[] args) {

        // creating regression object, passing true to have intercept term
        SimpleRegression simpleRegression = new SimpleRegression(true);
        ApplicationContext context = new ClassPathXmlApplicationContext("app-config.xml");
        Config config = context.getBean(Config.class);
        String DB_URL=config.getDBURL();
   	   String driver=config.getDriver();
   	  String USER=config.getUsername();
   	 String PASS=config.getPassword();
   	Connection conn=null;
	  Statement stmt = null;
	  int ctr=1;
	  try
	  {
		  Class.forName(driver);
		  System.out.println("Connectiong to database");
		  conn=DriverManager.getConnection(DB_URL,USER,PASS);
		  stmt=conn.createStatement();
		  ResultSet rs=stmt.executeQuery("select * from company");
		 
		  while(rs.next())
		  {
			  System.out.println("Company"+rs.getString("name"));
			  System.out.println("select * from stocks where Code="+rs.getString("code"));
			  PreparedStatement prep_stmt;
		      prep_stmt=conn.prepareStatement("select * from stocks where Code=?");
		      prep_stmt.setString(1,rs.getString("code"));
		      ResultSet rs1=prep_stmt.executeQuery();
			//  ResultSet rs1=stmt.executeQuery("select * from stocks where Code="+rs.getString("code"));
		      String name=null;
		      while(rs1.next())
			  {
		      simpleRegression.addData(ctr++,Double.parseDouble(rs1.getString("Close")));
		      name=rs1.getString("Close");
			  }
		      simpleRegression.removeData(ctr, Double.parseDouble(name));
		      System.out.println(simpleRegression);
			  
		      PreparedStatement prep_stmt1;
		      prep_stmt1=conn.prepareStatement("select Close from stocks where Code=? and Date=(select max(Date) from stocks where Code=?");
		      prep_stmt1.setString(1,rs.getString("code"));
		      prep_stmt1.setString(2,rs.getString("code"));
		      ResultSet rs2=prep_stmt.executeQuery();
		      rs2.next();
		      float price=rs2.getFloat("Close");
		      float p_price=(float) simpleRegression.predict(ctr++);
		      float profit=p_price-price;
		      System.out.println(price);
		      System.out.println(p_price);
		      System.out.println(profit);
		      prep_stmt1=conn.prepareStatement("update company set risk1=? where code=?");
		      prep_stmt1.setString(2,rs.getString("code"));
		      prep_stmt1.setFloat(1,profit);
		      prep_stmt1.execute();
		      // querying for model parameters
		       // System.out.println("slope = " + simpleRegression.getSlope());
		        //System.out.println("intercept = " + simpleRegression.getIntercept());

		        // trying to run model for unknown data
		       // System.out.println("prediction  = " + simpleRegression.predict(ctr++));
		        simpleRegression.clear();
		        //
		  }
	  }
	  
	  catch(Exception e) {System.out.println(e);}

        // passing data to the model
        // model will be fitted automatically by the class 
       /* simpleRegression.addData(new double[][] {
                {1, 2},
                {2, 3},
                {3, 4},
                {4, 5},
                {5, 6}
                
        });*/
       

    }
}