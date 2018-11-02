package com.jpmorgan.salesmessage.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpmorgan.salesmessage.bean.SalesBean;

public class DataBaseUtil {

	private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "jpmorgan";
    private static final String DB_PASSWORD = "1234";
    private static final String INSERTSTATEMENT = "INSERT INTO SALES(id,quantity,prod_type,price)"
    											+ " values (?,?,?,?)";
    private static final String SELECT = "SELECT * FROM SALES ORDER BY ID";
    private static final String SELECT_MAX = "SELECT MAX(ID) FROM SALES";
    private int id;
    private static DataBaseUtil instance = null;
    
    private  DataBaseUtil() {
    	System.out.println(":: Start DataBaseUtil() Contructor ::");
    	Connection connection = getConnection();
		
    	String createTable = "CREATE TABLE SALES (id int primary key,quantity int,prod_type varchar(20),price float)";
    	try {
    		PreparedStatement ps = connection.prepareStatement(createTable);
    		ps.executeUpdate();
    		ps.close();
    		connection.commit();
    		id=0;
    	}catch(SQLException se) {
    		System.out.println(se.getMessage());
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    	}finally{
    		try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	
    	System.out.println(":: End DataBaseUtil() Contructor ::");
    }
    
    
    public static DataBaseUtil getInstance() {
    	if(instance==null) {
    		instance =  new DataBaseUtil();
    	}
    	return instance;
    }
    
    private Connection getConnection() {
    	//System.out.println(":: Start getConnection() ::");
    	Connection connection=null;;
    	
    	try {
    		Class.forName(DB_DRIVER);
    		connection = DriverManager.getConnection(DB_CONNECTION,DB_USER,DB_PASSWORD);
    	}catch(SQLException se){
    		System.out.println(se.getMessage());
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
    	
    	//System.out.println(":: End getConnection() ::");
    	return connection;
    	
    }
    
    /**
     * 
     * @param sales
     * @return int , 0-> failure, 1-> success 2->success n runreport
     * ps args below
     * 1 - id
     * 2 - quantity
     * 3 - ProdType
     * 4 - prices
     */
    public  String insertSales(SalesBean sales) {
    	Connection conn = getConnection();
    	String status="";
    	try {
    		conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(INSERTSTATEMENT);
			id++;
			ps.setInt(1,id);
			ps.setInt(2,sales.getQuantity());
			ps.setString(3,sales.getProdType());
			ps.setDouble(4, sales.getPrice());
			ps.executeUpdate();
			ps.close();
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    	
    	return status;
    }
    
    
    public void selectFromSales() {
    	Connection conn = getConnection();
    	try {
    		PreparedStatement ps = conn.prepareStatement(SELECT);
    		ResultSet rs = ps.executeQuery();
    		System.out.println("\n:: DIsplaying current sales Entries ::");
    		System.out.println("ID\t" +"QUANTITY\t"+"PROD_TYPE\t"+"PRICE");
    		System.out.println("==\t" +"========\t"+"=========\t" +"=====");
    		while(rs.next()) {
    			System.out.println(rs.getInt(1)+"\t"+rs.getInt(2)+"\t\t"+rs.getString(3)+"\t\t"+rs.getDouble(4));
    		}
    	}catch(SQLException se) {
    		se.printStackTrace();
    	}
    }
    
    public int selectMaxID() {
    	Connection conn = getConnection();
    	int maxid=0;
    	try {
    		PreparedStatement ps = conn.prepareStatement(SELECT_MAX);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			maxid = rs.getInt(1);
    		}
    		//System.out.println("MAX ID RETRIEVED -> \t"+ maxid);
    		conn.close();
    	}catch(SQLException se) {
    		se.printStackTrace();
    	}
    	return maxid;
    
    	
    }
    
    /**
     *
     */
    public void generateReportfor10thMessage() {
    	Connection conn = getConnection();
    	
    	try {
    		String reportQuery = "SELECT sum(quantity) TOTAL_SALES,sum(price*quantity) TOTAL_VALUE,PROD_TYPE from SALES GROUP BY PROD_TYPE";
    		PreparedStatement ps = conn.prepareStatement(reportQuery);
    		ResultSet rs = ps.executeQuery();
    		System.out.println("PROD_TYPE\t" + "TOTAL_SALES\t" +"TOTAL_VALUE\t");
    		System.out.println("=========\t" +"============\t" +"===========\t");
    		
    		while(rs.next()) {
    			System.out.println(rs.getString(3)+"\t\t" + rs.getInt(1)+"\t\t"+rs.getDouble(2)+"\t");
        		
    		}
    		conn.close();
    	}catch(SQLException se) {
    		se.printStackTrace();
    	}
    }
    
    public void generateFinalReport() {
    	Connection conn = getConnection();
  
    	try {
    		String finalReportQuery = "SELECT sum(quantity) TOTAL_SALES,sum(price*quantity) TOTAL_VALUE from SALES GROUP BY PROD_TYPE";
    		PreparedStatement ps = conn.prepareStatement(finalReportQuery);
    		ResultSet rs = ps.executeQuery();
    		System.out.println("TOTAL_SALES\t" +"TOTAL_VALUE\t");
    		System.out.println("===========\t" +"=============\t");
    		
    		while(rs.next()) {
    			System.out.println(rs.getInt(1)+"\t"+rs.getDouble(2)+"\t\t");
        		
    		}
    		
    		conn.close();
    	}catch(SQLException se) {
    		se.printStackTrace();
    	}
    }
    
    public  String updateSales(SalesBean sales) {
    	Connection conn = getConnection();
    	String status="";
    	try {
    		conn.setAutoCommit(false);
    		String operand = null;
    		if(sales.getOperation()!=null ) {
    			if(sales.getOperation().equalsIgnoreCase("ADD")) {
    				operand = "+";
    			}else if(sales.getOperation().equalsIgnoreCase("SUBTRACT")) {
    				operand = "-";
    			}else if(sales.getOperation().equalsIgnoreCase("MULTIPLY")) {
    				operand = "*";
    			}
    		}
    			
    		String updateQuery = "update SALES set price = price " + operand + " ? where PROD_TYPE=?";
			PreparedStatement ps = conn.prepareStatement(updateQuery);
			ps.setDouble(1, sales.getPriceAdjust());
			ps.setString(2,sales.getProdType());
			ps.executeUpdate();
			ps.close();
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    	
    	return status;
    }
    
    
    
}
