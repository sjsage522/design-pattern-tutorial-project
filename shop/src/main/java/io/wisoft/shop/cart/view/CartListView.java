package io.wisoft.shop.cart.view;

import io.wisoft.shop.cart.controller.CartController;

public class CartListView {
    CartController cartController = new CartController();

    public void printCartList(final String userId) {
        String message = cartController.getCartInfoById(userId);
        System.out.println(message);
    }
}
