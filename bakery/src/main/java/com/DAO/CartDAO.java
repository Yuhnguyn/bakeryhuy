package com.DAO;

import com.entity.Cart;
import java.util.List;

public interface CartDAO {
    boolean addCartItem(Cart cartItem); // Thêm sản phẩm mới vào giỏ hàng
    boolean updateCartItem(int userId, String productId, int quantity); // Cập nhật số lượng sản phẩm trong giỏ

    List<Cart> getCartByUserId(int userId); // Lấy danh sách giỏ hàng của người dùng
    void clearCartByUserId(int userId); // Xóa toàn bộ giỏ hàng của người dùng
	boolean deleteCartItem(int userId, String productId);
	boolean setCartItemQuantity(int userId, String productId, int quantity);
}
