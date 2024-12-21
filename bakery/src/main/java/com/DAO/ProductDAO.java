package com.DAO;


import java.util.List;
import java.util.Map;

import com.entity.Product;

public interface ProductDAO {
	boolean addProduct(Product product);
	boolean updateProduct(Product product);
	boolean deleteProduct(String id);
	Product getProductById(String id);
	List<Product> getAllProducts();
	List<Product> getProductsByCategoryId(String categoryId);
	public Map<String, Object> getTop5Products();
	
}
