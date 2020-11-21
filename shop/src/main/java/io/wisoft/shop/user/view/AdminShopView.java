package io.wisoft.shop.user.view;

import io.wisoft.shop.util.scanner.InputInfo;
import io.wisoft.shop.util.scanner.SingleScanner;
import io.wisoft.shop.product.controller.ProductController;
import io.wisoft.shop.product.view.ProductListView;
import io.wisoft.shop.user.controller.UserController;
import java.util.Scanner;

public class AdminShopView extends ShopView {
    Scanner scanner = SingleScanner.getInstance();
    UserController userController = new UserController();
    ProductController productController = new ProductController();
    ProductListView productListView = new ProductListView();
    InputInfo inputInfo = new InputInfo();

    @Override
    void selectMenu() {
        while (true) {
            System.out.println("----------------");
            System.out.println("1. 모든 사용자 출력");
            System.out.println("2. 사용자 정보 제거");
            System.out.println("3. 모든 제품 출력");
            System.out.println("4. 제품 단가 조정");
            System.out.println("5. 새로운 제품 추가");
            System.out.println("6. 제품 제거");
            System.out.println("7. 제품 재고 조정");
            System.out.println("8. 로그아웃");
            System.out.println("----------------");

            System.out.print(">> ");
            int number = scanner.nextInt();

            switch (number) {
                case 1:
                    userController.printUserList();
                    break;
                case 2:
                    userController.deleteUserById(inputInfo.inputUserId());
                    break;
                case 3:
                    productListView.printAllProduct();
                    break;
                case 4:
                    String updateMessage = productController.setProductUnitPriceById(
                            inputInfo.inputProductId(), inputInfo.inputUnitPrice());
                    System.out.println(updateMessage);
                    break;
                case 5:
                    String[] productInfo = inputInfo.inputProductInfo();
                    String addMessage = productController.addProduct(
                            productInfo[0],
                            productInfo[1],
                            Integer.parseInt(productInfo[2]),
                            Integer.parseInt(productInfo[3])
                    );
                    System.out.println(addMessage);
                    break;
                case 6:
                    String delMessage = productController.deleteProductById(inputInfo.inputProductId());
                    System.out.println(delMessage);
                    break;
                case 7:
                    String supplyMessage = productController.supplyProductById(
                            inputInfo.inputProductId(),
                            inputInfo.supplyProductCount()
                    );
                    System.out.println(supplyMessage);
                    break;
                case 8:
                    return;
            }
        }
    }
}
