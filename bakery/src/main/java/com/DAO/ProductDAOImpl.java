package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.entity.Product;
import com.entity.User;

public class ProductDAOImpl implements ProductDAO {
	private Connection conn;

	public ProductDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean addProduct(Product product) {
		boolean f=false;
		try {
			String sql = "INSERT INTO product (thumbnail,id, category_id, name, price, discount, description, created_at, updated_at) VALUES (?, ?,?,?, ?, ?, ?, NOW(), NOW())";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1,product.getThumbnail());
			ps.setString(2,product.getId());
			ps.setString(3,product.getCategoryId());
			ps.setString(4,product.getName());
			ps.setString(5,product.getPrice());
			ps.setString(6,product.getDiscount());
			ps.setString(7,product.getDescription());
			
			int i=ps.executeUpdate();
			if(i==1) {
				f=true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	@Override
	public boolean updateProduct(Product product) {
		boolean f=false;
		try {
			String sql="update product set category_id=?, name=?, price=?, discount=?, description=?,updated_at = CURRENT_TIMESTAMP where id=? ";
			PreparedStatement ps=conn.prepareStatement(sql);

			ps.setString(1,product.getCategoryId());
			ps.setString(2,product.getName());
			ps.setString(3,product.getPrice());
			ps.setString(4,product.getDiscount());
			ps.setString(5,product.getDescription());
			ps.setString(6,product.getId());
			
			int i=ps.executeUpdate();
			if (i==1) {
				f=true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	@Override
	public boolean deleteProduct(String id) {
		boolean isDeleted = false;
		
		try {
			String sql = "DELETE FROM product WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			int i=ps.executeUpdate();
			if (i==1) {
				isDeleted=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isDeleted;
	}

	@Override
	public Product getProductById(String id) {
		Product product=null;
		try {
			String sql="select * from product where id=?";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				product=new Product();				
				product.setId(rs.getString(1));
				product.setCategoryId(rs.getString(2));
				product.setName(rs.getString(3));
				product.setPrice(rs.getString(4));
				product.setDiscount(rs.getString(5));
				product.setDescription(rs.getString(6));
				product.setCreatedAt(rs.getTimestamp(7));
				product.setUpdatedAt(rs.getTimestamp(8));
				product.setThumbnail(rs.getString(9));

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}

	@Override
	public List<Product> getAllProducts() {
	    List<Product> products = new ArrayList<>();
	    try {
	        String sql = "SELECT p.id, p.category_id, p.name, p.price, p.discount, p.description, " +
	                     "p.created_at, p.updated_at, p.thumbnail, c.name AS category " +
	                     "FROM product p " +
	                     "JOIN category c ON p.category_id = c.id";  // JOIN để lấy tên danh mục từ bảng category
	        
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        
	        while (rs.next()) {
	            Product product = new Product();
	            product.setId(rs.getString("id"));
	            product.setCategoryId(rs.getString("category_id"));
	            product.setCategory(rs.getString("category"));
	            product.setName(rs.getString("name"));
	            product.setPrice(rs.getString("price"));
	            product.setDiscount(rs.getString("discount"));
	            product.setDescription(rs.getString("description"));
	            product.setCreatedAt(rs.getTimestamp("created_at"));
	            product.setUpdatedAt(rs.getTimestamp("updated_at"));
	            product.setThumbnail(rs.getString("thumbnail"));

	            
	            products.add(product);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return products;
	}
	
	@Override
	public List<Product> getProductsByCategoryId(String categoryId) {
	    List<Product> products = new ArrayList<>();
	    try {
	        String sql = "SELECT p.id, p.category_id, p.name, p.price, p.discount, p.description, " +
	                     "p.created_at, p.updated_at, p.thumbnail, c.name AS category " +
	                     "FROM product p " +
	                     "JOIN category c ON p.category_id = c.id " +
	                     "WHERE p.category_id = ?";  // Lọc sản phẩm theo category_id
	        
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, categoryId);  // Gán categoryId vào câu truy vấn
	        ResultSet rs = ps.executeQuery();
	        
	        while (rs.next()) {
	            Product product = new Product();
	            product.setId(rs.getString("id"));
	            product.setCategoryId(rs.getString("category_id"));
	            product.setCategory(rs.getString("category"));
	            product.setName(rs.getString("name"));
	            product.setPrice(rs.getString("price"));
	            product.setDiscount(rs.getString("discount"));
	            product.setDescription(rs.getString("description"));
	            product.setCreatedAt(rs.getTimestamp("created_at"));
	            product.setUpdatedAt(rs.getTimestamp("updated_at"));
	            product.setThumbnail(rs.getString("thumbnail"));
	            
	            products.add(product);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return products;
	}


	
	
}
