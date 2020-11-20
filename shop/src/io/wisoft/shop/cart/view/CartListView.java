package io.wisoft.shop.cart.view;

import io.wisoft.shop.cart.controller.CartController;

public class CartListView {
    CartController cartController = new CartController();

    public void printCartList(String userId) {
        String message = cartController.getCartInfo(userId);
        System.out.println(message);
    }
}
