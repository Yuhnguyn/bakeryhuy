package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.entity.Category;

public class CategoryDAOImpl implements CategoryDAO {
	private Connection conn;

	public CategoryDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean addCategory(Category category) {
		boolean isAdded = false;
		String sql = "INSERT INTO category (id, name, description, thumbnail, created_at, updated_at) VALUES (?, ?, ?, ?, NOW(), NOW())";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, category.getId());
			ps.setString(2, category.getName());
			ps.setString(3, category.getDescription());
			ps.setString(4, category.getThumbnail());

			int rows = ps.executeUpdate();
			isAdded = rows > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return isAdded;
	}

	@Override
	public boolean isCategoryExists(String id) {
		boolean exists = false;
		String sql = "SELECT id FROM category WHERE id = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				exists = rs.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exists;
	}

	@Override
	public boolean updateCategory(Category category) {
		boolean f=false;
		
		try{
			String sql = "UPDATE category SET name = ?, description = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, category.getName());
			stmt.setString(2, category.getDescription());
			stmt.setString(3, category.getId());

			int i = stmt.executeUpdate();
			if(i==1) {
				f=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return f;
	}

	@Override
	public boolean deleteCategory(String id) {
		boolean isDeleted = false;
		
		try {
			String sql = "DELETE FROM category WHERE id = ?";
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
    public Category getCategoryById(String id) {
    	Category category = null;
        
        try {
        	String sql = "SELECT * FROM category WHERE id = ?";
        	PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	category=new Category();
            	category.setId(rs.getString(1));
            	category.setName(rs.getString(2));
            	category.setThumbnail(rs.getString(3));
            	category.setDescription(rs.getString(4));
            	category.setCreatedAt(rs.getTimestamp(5));
            	category.setUpdatedAt(rs.getTimestamp(6));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }





	@Override
	public List<Category> getAllCategories() {
	    List<Category> categories = new ArrayList<>();
	    String sql = "SELECT c.id, c.name, c.thumbnail, c.description, c.created_at, c.updated_at, " +
	                 "COUNT(p.id) AS product_count " +
	                 "FROM category c " +
	                 "LEFT JOIN product p ON c.id = p.category_id " +
	                 "GROUP BY c.id " +
	                 "ORDER BY c.created_at DESC";
	    
	    try (PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            Category category = new Category();
	            category.setId(rs.getString("id"));
	            category.setName(rs.getString("name"));
	            category.setThumbnail(rs.getString("thumbnail"));
	            category.setDescription(rs.getString("description"));
	            category.setCreatedAt(rs.getTimestamp("created_at"));
	            category.setUpdatedAt(rs.getTimestamp("updated_at"));
	            category.setProductCount(rs.getInt("product_count"));
	            
	            categories.add(category);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return categories;
	}



	@Override
	public List<Category> searchCategories(String keyword, String sort) {
	    List<Category> categories = new ArrayList<>();
	    
	    // Câu truy vấn để tìm kiếm danh mục và đếm số lượng sản phẩm trong mỗi danh mục
	    String sql = "SELECT c.id, c.name, c.thumbnail, c.description, c.created_at, c.updated_at, " +
	                 "COUNT(p.id) AS product_count " +
	                 "FROM category c " +
	                 "LEFT JOIN product p ON c.id = p.category_id " +
	                 "WHERE c.name LIKE ? OR c.id LIKE ? " +
	                 "GROUP BY c.id";
	    
	    // Thêm điều kiện sắp xếp
	    if ("name_asc".equals(sort)) {
	        sql += " ORDER BY c.name ASC";
	    } else if ("name_desc".equals(sort)) {
	        sql += " ORDER BY c.name DESC";
	    } else if ("date_newest".equals(sort)) {
	        sql += " ORDER BY c.created_at DESC";
	    } else if ("date_oldest".equals(sort)) {
	        sql += " ORDER BY c.created_at ASC";
	    }

	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        // Gán giá trị cho tham số tìm kiếm
	        ps.setString(1, "%" + keyword + "%"); // Tìm kiếm theo tên
	        ps.setString(2, "%" + keyword + "%"); // Tìm kiếm theo ID

	        ResultSet rs = ps.executeQuery();

	        // Xử lý kết quả trả về
	        while (rs.next()) {
	            Category category = new Category();
	            category.setId(rs.getString("id"));
	            category.setName(rs.getString("name"));
	            category.setThumbnail(rs.getString("thumbnail"));
	            category.setDescription(rs.getString("description"));
	            category.setCreatedAt(rs.getTimestamp("created_at"));
	            category.setUpdatedAt(rs.getTimestamp("updated_at"));
	            category.setProductCount(rs.getInt("product_count")); // Gán productCount

	            categories.add(category);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return categories;
	}

    
    

}