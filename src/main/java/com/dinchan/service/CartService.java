package com.dinchan.service;

import com.dinchan.model.Cart;
import com.dinchan.model.CartItem;
import com.dinchan.model.Product;
import com.dinchan.model.User;

public interface CartService {
    public CartItem addCartItem(User user, Product product, String size, int quantity);
    public Cart findUserCart(User user);
}
