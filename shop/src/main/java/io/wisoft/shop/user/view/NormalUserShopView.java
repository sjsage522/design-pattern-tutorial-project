package io.wisoft.shop.user.view;

import io.wisoft.shop.cart.controller.CartController;
import io.wisoft.shop.cart.view.CartListView;
import io.wisoft.shop.product.view.ProductListView;
import io.wisoft.shop.util.scanner.InputInfo;
import io.wisoft.shop.util.scanner.SingleScanner;

import java.util.Scanner;

public class NormalUserShopView extends ShopView{
    final String userId;
    ProductListView productListView = new ProductListView();
    CartController cartController = new CartController();
    Scanner scanner = SingleScanner.getInstance();
    InputInfo inputInfo = new InputInfo();
    CartListView cartListView = new CartListView();

    public NormalUserShopView(final String userId) {
        this.userId = userId;
    }

    @Override
    void selectMenu() {
        while (true) {
            System.out.println("----------------");
            System.out.println("1. 모든 제품 출력");
            System.out.println("2. 제품 장바구니에 담기");
            System.out.println("3. 장바구니 확인");
            System.out.println("4. 장바구니에서 제품 제거");
            System.out.println("5. 장바구니 초기화");
            System.out.println("6. 구매");
            System.out.println("7. 로그아웃");
            System.out.println("----------------");

            System.out.print(">> ");
            int number = scanner.nextInt();

            switch (number) {
                case 1:
                    productListView.printAllProduct();
                    break;
                case 2:
                    System.out.println(
                            cartController.addProductToCart(
                            inputInfo.inputProductId(),
                            inputInfo.inputProductCount(),
                            userId
                    ));
                    break;
                case 3:
                    cartListView.printCartList(userId);
                    break;
                case 4:
                    System.out.println(cartController.removeProductFromCartById(
                            inputInfo.inputCartId(),
                            userId
                    ));
                    break;
                case 5:
                    System.out.println(cartController.clearCartByUserId(
                            userId
                    ));
                    break;
                case 6:
                    System.out.println(
                            cartController.buyProductFromCartByUserId(userId)
                    );
                    break;
                case 7:
                    return;
            }
        }
    }
}
