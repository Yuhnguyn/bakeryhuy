// CartDAO.java
package com.DAO;

import com.entity.Cart;
import java.util.List;

public interface CartDAO {
    boolean addToCart(int userId, String productId, int quantity);
    boolean updateCart(int cartId, int quantity);
    boolean deleteFromCart(int cartId);
    List<Cart> getCartByUserId(int userId);
}
