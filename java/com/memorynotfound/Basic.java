package com.memorynotfound;
import java.io.IOException;
import java.io.*;
import java.sql.*;
import java.util.*;
public class Basic {
	static final String DB_URL="jdbc:mysql://localhost:3306/citi";
	static final String driver="com.mysql.jdbc.Driver";
	static final String USER="root";
	static final String PASS="admin";
	public static void main(String[] args) throws ClassNotFoundException {
		Scanner sc=new Scanner(System.in);
		String risk,q;
		Connection conn=null;
		Statement stmt = null;


		System.out.println("Enter the risk appetite");
		risk=sc.next();
		try {
			Class.forName(driver);
			conn=DriverManager.getConnection(DB_URL,USER,PASS);
			stmt=conn.createStatement();
			int l=0,m=0,s=0;
			q="select count(*) as Large from company where type='L'";
			ResultSet rsL=stmt.executeQuery(q);
			while(rsL.next())
			{
				l=rsL.getInt("Large");
			}
			q="select count(*) as Mid from company where type='M'";
			ResultSet rsM=stmt.executeQuery(q);
			while(rsM.next())
			{
				m=rsM.getInt("Mid");
			}
			q="select count(*) as Small from company where type='S'";
			ResultSet rsS=stmt.executeQuery(q);
			while(rsS.next())
			{
				s=rsS.getInt("Small");
			}
			if(risk.equals("High"))
			{
				l=((l*10)/100);
				l=(int) Math.ceil(l);
				m=((m*30)/100);
				m=(int) Math.ceil(m);
				s=((s*60)/100);
				s=(int) Math.ceil(s);
				cal(l,m,s);
			}

			else if(risk.equals("Mid"))
			{
				l=((l*30)/100);
				l=(int) Math.ceil(l);
				m=((m*30)/100);
				m=(int) Math.ceil(m);
				s=((s*40)/100);
				s=(int) Math.ceil(s);
				cal(l,m,s);
			}
			else if(risk.equals("Small"))
			{
				l=((l*60)/100);
				l=(int) Math.ceil(l);
				m=((m*30)/100);
				m=(int) Math.ceil(m);
				s=((s*10)/100);
				s=(int) Math.ceil(s);
				cal(l,m,s);						  }

		} 
		catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public static void cal(int c,int c1,int c2)
	{ 
		Connection conn=null;
		Statement stmt = null;
		try {
			conn=DriverManager.getConnection(DB_URL,USER,PASS);
			stmt=conn.createStatement();
			String query,query1,query2,q;
			String code,company_name;
			int count=0,count1=0,count2=0;
			System.out.println("From Large Cap");

			query="select * from company where type='L';";
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next() && count!=c)
			{
				code=rs.getString("code");
				company_name=rs.getString("name");
				System.out.println(code+"   "+company_name);
				count++; 
			} 
			System.out.println("From Mid Cap");

			query1="select * from company where type='M'";
			ResultSet rs1=stmt.executeQuery(query1);
			while(rs1.next() && count1!=c1 )
			{
				code=rs1.getString("code");
				company_name=rs1.getString("name");
				System.out.println(code+"   "+company_name);
				count1++;
			}
			System.out.println("From Small Cap");

			query2="select * from company where type='S'";
			ResultSet rs2=stmt.executeQuery(query2);
			while(rs2.next() && count2!=c2)
			{
				code=rs2.getString("code");
				company_name=rs2.getString("name");
				System.out.println(code+"   "+company_name);
				count2++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}





	}

}
