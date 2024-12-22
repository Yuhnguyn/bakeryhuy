package com.entity;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.UUID;

public class Orders {
	private String id;               
    private int userId;           
    private String name;      
    private String phone;   
    private String address;       
    private Timestamp createdAt;
    private Timestamp approvedAt; 
    private String status;           
    private double totalMoney;
    
    
    public Orders() {
    	this.id=generateId();
    	this.status = "pending";
	}
	public String getFormattedTotalMoney() {
        DecimalFormat df = new DecimalFormat("#,###"); 
        return df.format(totalMoney); 
    }
    private String generateId() {
        return "ORDER-" + UUID.randomUUID().toString().substring(0, 8);
    }
	
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getApprovedAt() {
		return approvedAt;
	}
	public void setApprovedAt(Timestamp approvedAt) {
		this.approvedAt = approvedAt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}
    
    
}