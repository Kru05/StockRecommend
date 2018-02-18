package com.cummins.stock;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.*;
 
public class ZeroOneKnapsackForStock {
	
		Connection conn=null;
		  Statement stmt = null;
		 
    public ZeroOneKnapsackForStock() throws SQLException, ClassNotFoundException {
        ZeroOneKnapsack zok = new ZeroOneKnapsack(50000); // 400 dkg = 400 dag = 4 kg
        ApplicationContext context = new ClassPathXmlApplicationContext("app-config.xml");
        Config config = context.getBean(Config.class);
        String DB_URL=config.getDBURL();
   	   String driver=config.getDriver();
   	  String USER=config.getUsername();
   	 String PASS=config.getPassword();
        System.out.println(driver);
       Class.forName(driver);
		  conn=DriverManager.getConnection(DB_URL,USER,PASS);
		  stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("select * from company where type='L' order by risk1 desc limit 30");
       ResultSet rs1;
       Statement stmt2 = conn.createStatement();
       while(rs.next())
       {
    	   rs1=stmt2.executeQuery("select * from stocks where code='"+rs.getString("code")+"' and Date=(select max(Date)from stocks)");
				rs1.next();
    	   zok.add(rs.getString("code"),(int)rs1.getDouble("close"),(int)rs.getDouble("risk1"));
       }
       // making the list of items that you want to bring
     /*   zok.add("map", 9, 150);
        zok.add("compass", 13, 35);
        zok.add("water", 153, 200);
        zok.add("sandwich", 50, 160);
        zok.add("glucose", 15, 60);
        zok.add("tin", 68, 45);
        zok.add("banana", 27, 60);
        zok.add("apple", 39, 40);
        zok.add("cheese", 23, 30);
        zok.add("beer", 52, 10);
        zok.add("suntan cream", 11, 70);
        zok.add("camera", 32, 30);
        zok.add("t-shirt", 24, 15);
        zok.add("trousers", 48, 10);
        zok.add("umbrella", 73, 40);
        zok.add("waterproof trousers", 42, 70);
        zok.add("waterproof overclothes", 43, 75);
        zok.add("note-case", 22, 80);
        zok.add("sunglasses", 7, 20);
        zok.add("towel", 18, 12);
        zok.add("socks", 4, 50);
        zok.add("book", 30, 10);*/
 
        // calculate the solution:
        List<Item> itemList = zok.calcSolution();
 
        // write out the solution in the standard output
        if (zok.isCalculated()) {
            NumberFormat nf  = NumberFormat.getInstance();
 
            System.out.println(
                "Maximal weight           = " +
                nf.format(zok.getMaxWeight()));
            System.out.println(
                "Total weight of solution = " +
                nf.format(zok.getSolutionWeight()));
            System.out.println(
                "Total value              = " +
                zok.getProfit()
            );
            System.out.println();
            System.out.println(
                "You can carry the following materials " +
                "in the knapsack:"
            );
            for (Item item : itemList) {
                if (item.getInKnapsack() == 1) {
                    System.out.format(
                        "%1$-23s %2$-3s %3$-5s %4$-15s \n",
                        item.getName(),
                        item.getWeight(), "dag  ",
                        "(value = " + item.getValue() + ")"
                    );
                }
            }
        } else {
            System.out.println(
                "The problem is not solved. " +
                "Maybe you gave wrong data."
            );
        }
 
    }
 
    public static void main(String[] args) throws Exception {
        new ZeroOneKnapsackForStock();
    }
 
} // class