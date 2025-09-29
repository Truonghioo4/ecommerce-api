package com.dinchan.service.impl;

import com.dinchan.model.CartItem;
import com.dinchan.model.User;
import com.dinchan.repository.CartItemRepository;
import com.dinchan.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;


    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws Exception {
        CartItem item = findCartItemById(id);
        User cartItemUser = item.getCart().getUser();
        if(cartItemUser.getId().equals(userId)) {
            item.setQuantity(cartItem.getQuantity());
            item.setMrpPrice(item.getQuantity()*item.getProduct().getMrpPrice());
            item.setSellingPrice(item.getQuantity()*item.getProduct().getSellPrice());
            return cartItemRepository.save(item);
        }
        throw new Exception("You can't update this cartItem");
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws Exception {
        CartItem item = findCartItemById(cartItemId);
        User cartItemUser = item.getCart().getUser();
        if(cartItemUser.getId().equals(userId)) {
            cartItemRepository.delete(item);
        } else throw new Exception("You can't delete this item");
    }

    @Override
    public CartItem findCartItemById(Long id) throws Exception {
        return cartItemRepository.findById(id).orElseThrow(() ->
                new Exception("cart item not found with id"+ id));
    }
}
