package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.entity.Cart;

public class CartDAOImpl implements CartDAO {
	private Connection conn;

	public CartDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean addToCart(int userId, String productId, int quantity) {
		boolean result = false;
		String sql = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?) "
				+ "ON DUPLICATE KEY UPDATE quantity = quantity + ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ps.setString(2, productId);
			ps.setInt(3, quantity);
			ps.setInt(4, quantity);
			result = ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean updateCart(int cartId, int quantity) {
		boolean result = false;
		String sql = "UPDATE cart SET quantity = ? WHERE id = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, quantity);
			ps.setInt(2, cartId);
			result = ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean deleteFromCart(int cartId) {
		boolean result = false;
		String sql = "DELETE FROM cart WHERE id = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, cartId);
			result = ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Cart> getCartByUserId(int userId) {
		List<Cart> cartList = new ArrayList<>();
		String sql = "SELECT * FROM cart WHERE user_id = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Cart cart = new Cart();
				cart.setId(rs.getInt("id"));
				cart.setUserId(rs.getInt("user_id"));
				cart.setProductId(rs.getString("product_id"));
				cart.setQuantity(rs.getInt("quantity"));
				cart.setCreatedAt(rs.getTimestamp("created_at"));
				cart.setUpdatedAt(rs.getTimestamp("updated_at"));
				cartList.add(cart);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cartList;
	}

}
