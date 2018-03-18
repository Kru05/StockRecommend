package hello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.threeten.bp.LocalDate;

import com.jimmoores.quandl.DataSetRequest;
import com.jimmoores.quandl.QuandlSession;
import com.jimmoores.quandl.Row;
import com.jimmoores.quandl.TabularResult;
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
     QuandlSession session = QuandlSession.create("5eXgyoUcjVASEZx5kXf6");
		Connection conn=null;
		  Statement stmt = null;
		  try
		  {
			  Class.forName(driver);
			  System.out.println("Connectiong to database");
			  conn=DriverManager.getConnection(DB_URL,USER,PASS);
			  stmt=conn.createStatement();
			  stmt.execute("delete from stocks");
			  ResultSet rs=stmt.executeQuery("select * from company");
			 
			  while(rs.next())
			{
			  	
				  System.out.println(rs.getString("name"));
				  String s="NSE/"+rs.getString("code");
				  System.out.println(s);
				  TabularResult tabularResult = session.getDataSet(DataSetRequest.Builder
				    .of(s).withMaxRows(10)
				    .build());
				  System.out.println(tabularResult.toPrettyPrintedString());
				  for (final Row row : tabularResult) {
					
					  LocalDate date = row.getLocalDate("Date");
					  Double open = row.getDouble("Open");
					  Double high = row.getDouble("High");
					  Double low = row.getDouble("Low");
					  Double last = row.getDouble("Last");
					  Double close = row.getDouble("Close");
					  Double  Total_Trade_Quantity = row.getDouble("Total Trade Quantity");
					  Double Turnover = row.getDouble("Turnover (Lacs)");
			
						String query = "insert into stocks (Code,Date,Open,High,Low,Last,Close,Total_Trade_Quantity,Turnover )"+" values (?,?,?,?,?,?,?,?,?)";
						  PreparedStatement prep_stmt;
					      prep_stmt=conn.prepareStatement(query);
					      prep_stmt.setString(1,rs.getString("code"));
					      prep_stmt.setString(2,date.toString());
					      prep_stmt.setDouble(3,open);
					      prep_stmt.setDouble(4,high);
					      prep_stmt.setDouble(5,low);
					      prep_stmt.setDouble(6,last);
					      prep_stmt.setDouble(7,close);
					      prep_stmt.setDouble(8,Total_Trade_Quantity);
					      prep_stmt.setDouble(9,Turnover);
					      prep_stmt.execute();
					}
			}
		  }
		  
		  catch(Exception e) {System.out.println(e);}
    }
    
  
}
