package io.wisoft.shop.product.view;

import io.wisoft.shop.product.controller.ProductController;

public class ProductListView {
    ProductController productController = new ProductController();

    public void printAllProduct() {
        String allProductMessage = productController.getAllProductInfo();
        System.out.println(allProductMessage);
    }
}
