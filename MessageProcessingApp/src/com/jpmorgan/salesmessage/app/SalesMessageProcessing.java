package com.jpmorgan.salesmessage.app;

import com.jpmorgan.salesmessage.bean.SalesBean;
import com.jpmorgan.salesmessage.util.DataBaseUtil;

public class SalesMessageProcessing {

	private int messageCount;
	private DataBaseUtil dataBaseUtil = DataBaseUtil.getInstance();
	private boolean generate10thReport = false;
	
	public static void main(String[] args) {
		SalesMessageProcessing processing = new SalesMessageProcessing();
		processing.recieveMessage();
		
	}
	
	public SalesMessageProcessing(){
		messageCount=0;
	}
	
	public void recieveMessage() {
		
		SalesBean sales = new SalesBean();
		sales.setProdType("apple");
		sales.setQuantity(1);
		sales.setPrice(10.00);
		processMessage(sales);
		
		SalesBean sales1 = new SalesBean();
		sales1.setProdType("apple");
		sales1.setQuantity(1);
		sales1.setPrice(10.00);
		processMessage(sales1);
		
		SalesBean sales2 = new SalesBean();
		sales2.setProdType("apple");
		sales2.setQuantity(1);
		sales2.setPrice(10.00);
		processMessage(sales2);
		
		SalesBean sales3 = new SalesBean();
		sales3.setProdType("orange");
		sales3.setQuantity(1);
		sales3.setPrice(10.00);
		processMessage(sales3);
		
		SalesBean sales4 = new SalesBean();
		sales4.setProdType("orange");
		sales4.setQuantity(1);
		sales4.setPrice(10.00);
		processMessage(sales4);
		
		SalesBean sales5 = new SalesBean();
		sales5.setProdType("orange");
		sales5.setQuantity(1);
		sales5.setPrice(10.00);
		processMessage(sales5);
		
		SalesBean sales6 = new SalesBean();
		sales6.setProdType("banana");
		sales6.setQuantity(1);
		sales6.setPrice(10.00);
		processMessage(sales6);
		
		SalesBean sales7 = new SalesBean();
		sales7.setProdType("banana");
		sales7.setQuantity(1);
		sales7.setPrice(10.00);
		processMessage(sales7);
		
		SalesBean sales8 = new SalesBean();
		sales8.setProdType("banana");
		sales8.setQuantity(1);
		sales8.setPrice(10.00);
		sales8.setOperation("ADD");
		sales8.setPriceAdjust(10.00);
		processMessage(sales8);
		
		SalesBean sales9 = new SalesBean();
		sales9.setProdType("berry");
		sales9.setQuantity(1);
		sales9.setPrice(10.00);
		processMessage(sales9);
	}
	
	private void processMessage(SalesBean msg) {
		
		messageCount = dataBaseUtil.selectMaxID();
		System.out.println("Current ID retrieved " + messageCount);
		
		if(validateMessageCount()) {
			dataBaseUtil.insertSales(msg);		
			if(msg.getOperation()!=null) {
				dataBaseUtil.updateSales(msg);
			}
			dataBaseUtil.selectFromSales();
		}
		if(generate10thReport) {
			generateReportFor10thMessage();
		}
		
		
	}
	
	 private boolean validateMessageCount() {
	    boolean flag = true; 	
	    if(messageCount!=0 && messageCount%10==9) {
	    	generate10thReport=true;
	    	flag=true;
	    }else if (messageCount==50) {
	    	generateFinalReport();
	    	flag=false;
	    }
	    	
	    return flag;
	 }
	 
	 private void generateReportFor10thMessage() {
		 System.out.println("\n Current Meesage Count is " + (messageCount+1) +" Generating Report ...");
		 
		 dataBaseUtil.generateReportfor10thMessage();
	 }
	 
	 private void generateFinalReport() {
		 System.out.println("\n:: Recieved Message Count for the day has reached the Limit 50. Processing is paused::");
		 System.out.println(":: Generating the Final Report..");
	 }
	   

}
