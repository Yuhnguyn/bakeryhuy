package com.entity;

import java.sql.Timestamp;

public class Cart {
	    private int id;
	    private int userId;
	    private String productId;
	    private String productName;
	    private String thumbnail;
	    private double price;
	    public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public String getThumbnail() {
			return thumbnail;
		}
		public void setThumbnail(String thumbnail) {
			this.thumbnail = thumbnail;
		}
		public String getProductName() {
			return productName;
		}
		private int quantity;
	    private Timestamp createdAt;
	    private Timestamp updatedAt;
	    
	    
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public String getProductId() {
			return productId;
		}
		public void setProductId(String productId) {
			this.productId = productId;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
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
		public void setProductName(String productName) {
			// TODO Auto-generated method stub
			
		}

	    

}
