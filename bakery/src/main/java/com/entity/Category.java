package com.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Category {
	private String id;
	private String name;
	private String description;
	private String thumbnail;
	private int productCount;
	

	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	
	// định dạng ngày tháng năm
		public String getFormattedCreatedAt() {
	        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
	        return createdAt != null ? formatter.format(createdAt) : "";
	    }

	    public String getFormattedUpdatedAt() {
	        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
	        return updatedAt != null ? formatter.format(updatedAt) : "";
	    }
	public int getProductCount() {
		return productCount;
	}
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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
	

	public Category(String id, String name, String description, String thumbnail, int productCount, Timestamp createdAt,
			Timestamp updatedAt) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.thumbnail = thumbnail;
		this.productCount = productCount;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	
	public Category(String id, String name, String description, String thumbnail, Timestamp createdAt,
			Timestamp updatedAt) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.thumbnail = thumbnail;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	public Category() {
	}

	
}
