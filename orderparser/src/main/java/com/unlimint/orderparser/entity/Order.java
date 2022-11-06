package com.unlimint.orderparser.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.unlimint.orderparser.exception.OrderConversionException;

public class Order {
	
	private long orderId;
	private BigDecimal amount;
	private String currency;
	private String comment;
	private long id;
	private String fileName;
	private BigInteger line;
	private String result;
	
	@Override
	public String toString() {
		return "Order [ orderId=" + orderId + ", amount=" + amount + ", currency=" + currency + ", comment=" + comment
				+ ", fileName=" + fileName + ", line=" + line + ", result=" + result + "]";
	}

	public Order(long orderId,
	 BigDecimal amount,
	String currency,
	String comment,
	String fileName,
	BigInteger line,
	String result) {
		
		this.orderId = orderId;
		this.amount = amount;
		this.currency = currency;
		this.comment = comment;
		this.fileName = fileName;
		this.line = line;
		this.result = result;	
	}
	public Order() {  }
	
	 public Order(String information) throws OrderConversionException{
		 
		 List<String> list = new ArrayList<>();
		 StringTokenizer st = new StringTokenizer(information, ",");
		 while(st.hasMoreTokens()){
			 list.add(st.nextToken());
		 }
		 this.setOrderId(Long.valueOf(list.get(0)));
		 this.setAmount(BigDecimal.valueOf(Double.valueOf(list.get(1))));
		 this.setCurrency(list.get(2));
		 this.setComment(list.get(3));
	 }

	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	
	
	public long getOrderId() {
		return orderId;
	}


	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public BigInteger getLine() {
		return line;
	}


	public void setLine(BigInteger line) {
		this.line = line;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}
	
	
} 