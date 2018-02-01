package com.memorynotfound;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jimmoores.quandl.DataSetRequest;
import com.jimmoores.quandl.tablesaw.TableSawQuandlSession;

import tech.tablesaw.api.Table;

public class Main {

    public static void main(String... args){
        ApplicationContext context = new ClassPathXmlApplicationContext("app-config.xml");
        Config config = context.getBean(Config.class);

        System.out.println(config);
         String DB_URL=config.getDBURL();
  	   String driver=config.getDriver();
  	  String USER=config.getUsername();
  	 String PASS=config.getPassword();
        TableSawQuandlSession session = TableSawQuandlSession.create();
		Connection conn=null;
		  Statement stmt = null;
		  int n;
		  try
		  {
			  Class.forName(driver);
			  System.out.println("Connectiong to database");
			  conn=DriverManager.getConnection(DB_URL,USER,PASS);
			  stmt=conn.createStatement();
			  ResultSet rs=stmt.executeQuery("select * from company");
			  String name,code;
			  int compno;
			  while(rs.next())
			  {
				  System.out.println(rs.getString("name"));
				  String s="NSE/"+rs.getString("code");
				  System.out.println(s);
					Table table = session.getDataSet(
					DataSetRequest.Builder.of(s).withMaxRows(10).build());
					System.out.println(table);
					for(int i=0;i<10;i++)
					{
						String query = "replace into stocks (Code,Date,High,Low,Last,Close,Total_Trade_Quantity,Turnover )"+" values (?,?,?,?,?,?,?,?)";
						  PreparedStatement prep_stmt;
					      prep_stmt=conn.prepareStatement(query);
					      prep_stmt.setString(1,rs.getString("code"));
					      prep_stmt.setString(2,table.get(i, 0));
					      prep_stmt.setFloat(3,Float.parseFloat(table.get(i, 1)));
					      prep_stmt.setFloat(4,Float.parseFloat(table.get(i, 2)));
					      prep_stmt.setFloat(5,Float.parseFloat(table.get(i, 3)));
					      prep_stmt.setFloat(6,Float.parseFloat(table.get(i, 4)));
					      prep_stmt.setDouble(7,Double.parseDouble(table.get(i, 5)));
					      prep_stmt.setDouble(8,Double.parseDouble(table.get(i, 6)));
					      prep_stmt.execute();
					}
			  }
		  }
		  
		  catch(Exception e) {System.out.println(e);}
    }
}
