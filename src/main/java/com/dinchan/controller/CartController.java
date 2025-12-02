package com.dinchan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinchan.model.Cart;
import com.dinchan.model.CartItem;
import com.dinchan.model.Product;
import com.dinchan.model.User;
import com.dinchan.request.AddItemRequest;
import com.dinchan.response.ApiResponse;
import com.dinchan.service.CartItemService;
import com.dinchan.service.CartService;
import com.dinchan.service.ProductService;
import com.dinchan.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
  private final CartService cartService;
  private final CartItemService cartItemService;
  private final UserService userService;
  private final ProductService productService;

  @GetMapping()
  public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws Exception{
    User user = userService.findUserByJwtToken(jwt);
    Cart cart = cartService.findUserCart(user);
    
    return new ResponseEntity<Cart>(cart, HttpStatus.OK);
  }

  @PutMapping("/add")
  public ResponseEntity<CartItem> addItemToCard(@RequestBody AddItemRequest req, 
    @RequestHeader("Authorization") String jwt) throws Exception{
    
    User user = userService.findUserByJwtToken(jwt);
    Product product = productService.findProductById(req.getProductId());
    CartItem cartItem = cartService.addCartItem(user, product, req.getSize(), req.getQuantity());
    return new ResponseEntity<>(cartItem, HttpStatus.ACCEPTED);
  }

  @DeleteMapping("/item/{cartItemId}")
  public ResponseEntity<ApiResponse> delelteCartItem(@PathVariable Long cartItemId,
    @RequestHeader("Authorization") String jwt) throws Exception{
    
    User user = userService.findUserByJwtToken(jwt);
    cartItemService.removeCartItem(user.getId(), cartItemId);
    ApiResponse res = new ApiResponse();
    res.setMessage("Item Remove From Cart");
    return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
  }

  @PutMapping("/item/{cartItemId}")
  public ResponseEntity<CartItem> updateCartItem(
    @PathVariable Long cartItemId, 
    @RequestBody CartItem cartItem,
    @RequestHeader("Authorization") String jwt
  ) throws Exception{
    User user = userService.findUserByJwtToken(jwt);
    CartItem updatedCartItem = null;
    if(cartItem.getQuantity()>0){
      updatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
    }
    return new ResponseEntity<CartItem>(updatedCartItem, HttpStatus.ACCEPTED);
  }

  
}
