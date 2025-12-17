package com.dinchan.service;


import com.dinchan.model.Product;
import com.dinchan.model.User;
import com.dinchan.model.Wishlist;

public interface WishlistService {
    Wishlist createWishlist(User user);
    Wishlist getWishlistByUserId(User user);
    Wishlist updateProductToWishlist(User user, Product product);
    void removeProductFromWishlist(User user, Product product);
}
