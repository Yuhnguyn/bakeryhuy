package com.DAO;

import com.entity.Cart;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartDAOImpl implements CartDAO {
    private Connection conn;

    public CartDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean addCartItem(Cart cartItem) {
        boolean success = false;
        String checkSQL = "SELECT quantity FROM cart WHERE user_id = ? AND product_id = ?";
        String updateSQL = "UPDATE cart SET quantity = quantity + ?, updated_at = CURRENT_TIMESTAMP WHERE user_id = ? AND product_id = ?";
        String insertSQL = "INSERT INTO cart (user_id, product_id, quantity, created_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";

        try {
            // Kiểm tra nếu sản phẩm đã tồn tại
            try (PreparedStatement psCheck = conn.prepareStatement(checkSQL)) {
                psCheck.setInt(1, cartItem.getUserId());
                psCheck.setString(2, cartItem.getProductId());
                ResultSet rs = psCheck.executeQuery();

                if (rs.next()) {
                    // Nếu tồn tại, cập nhật số lượng
                    try (PreparedStatement psUpdate = conn.prepareStatement(updateSQL)) {
                        psUpdate.setInt(1, cartItem.getQuantity());
                        psUpdate.setInt(2, cartItem.getUserId());
                        psUpdate.setString(3, cartItem.getProductId());
                        success = psUpdate.executeUpdate() > 0;
                    }
                } else {
                    // Nếu chưa tồn tại, thêm mới
                    try (PreparedStatement psInsert = conn.prepareStatement(insertSQL)) {
                        psInsert.setInt(1, cartItem.getUserId());
                        psInsert.setString(2, cartItem.getProductId());
                        psInsert.setInt(3, cartItem.getQuantity());
                        success = psInsert.executeUpdate() > 0;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public boolean updateCartItem(int userId, String productId, int changeQuantity) {
        boolean success = false;
        String sql = "UPDATE cart SET quantity = quantity + ? WHERE user_id = ? AND product_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, changeQuantity);
            ps.setInt(2, userId);
            ps.setString(3, productId);
            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }
    @Override
    public boolean setCartItemQuantity(int userId, String productId, int quantity) {
        boolean success = false;
        String sql = "UPDATE cart SET quantity = ? WHERE user_id = ? AND product_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, userId);
            ps.setString(3, productId);
            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }
@Override
    public boolean deleteCartItem(int userId, String productId) {
        boolean success = false;
        String sql = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, productId);
            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }


    @Override
    public List<Cart> getCartByUserId(int userId) {
        List<Cart> cartList = new ArrayList<>();
        String sql = "SELECT c.id, c.user_id, c.product_id, c.quantity, " +
                     "p.name AS product_name, p.price, p.thumbnail, " +
                     "c.created_at, c.updated_at " +
                     "FROM cart c " +
                     "JOIN product p ON c.product_id = p.id " +
                     "WHERE c.user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cart cart = new Cart();
                    cart.setId(rs.getInt("id"));
                    cart.setUserId(rs.getInt("user_id"));
                    cart.setProductId(rs.getString("product_id"));
                    cart.setQuantity(rs.getInt("quantity"));
                    cart.setProductName(rs.getString("product_name")); // Tên sản phẩm
                    cart.setPrice(rs.getDouble("price")); // Giá sản phẩm
                    cart.setThumbnail(rs.getString("thumbnail")); // Hình ảnh sản phẩm
                    cart.setCreatedAt(rs.getTimestamp("created_at"));
                    cart.setUpdatedAt(rs.getTimestamp("updated_at"));
                    cartList.add(cart);
                    System.out.println("Cart Item: " + cart.getProductId() + ", " + cart.getProductName() + ", " + cart.getPrice() + ", " + cart.getThumbnail());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartList;
    }




    @Override
    public void clearCartByUserId(int userId) {
        String sql = "DELETE FROM cart WHERE user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
