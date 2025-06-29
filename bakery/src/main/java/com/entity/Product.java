package com.entity;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


public class Product{
	private String id;
	private String categoryId;
	private String category;
	private String name;
	private double price;
	private String discount;
	private String description;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private String thumbnail;
	
	
	
	public Product(String id, String categoryId, String category, String name, double price, String discount,
			String description, Timestamp createdAt, Timestamp updatedAt, String thumbnail) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.category = category;
		this.name = name;
		this.price = price;
		this.discount = discount;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.thumbnail = thumbnail;
	}
	
	public String getFormattedBalance() {
        DecimalFormat df = new DecimalFormat("#,###"); // Định dạng số với dấu phẩy
        return df.format(price); // Trả về số dư đã định dạng
    }
	// định dạng ngày tháng năm
	public String getFormattedCreatedAt() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        return createdAt != null ? formatter.format(createdAt) : "";
    }

    public String getFormattedUpdatedAt() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        return updatedAt != null ? formatter.format(updatedAt) : "";
    }
    
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}


