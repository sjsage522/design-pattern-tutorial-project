package io.wisoft.shop.user.view;

import io.wisoft.shop.util.scanner.SingleScanner;
import io.wisoft.shop.user.controller.UserController;

import java.util.Scanner;

public class LoginView {
    final UserController controller = new UserController();
    final Scanner scanner = SingleScanner.getInstance();

    public void login() {
        SettingView.displaySetting();
        System.out.println("### LOGIN DISPLAY ###");

        System.out.print("ID : ");   String id = scanner.nextLine();
        System.out.print("PW : "); String pw = scanner.nextLine();

        String userId = controller.getLoginUser(id, pw);

        if (userId != null) {
            ShopView shopView;

            switch (userId) {
                case "admin":
                    shopView = new AdminShopView();
                    break;
                default:
                    shopView = new NormalUserShopView(userId);
                    break;
            }

            shopView.printShopMenu(userId);
        } else {
            System.out.println("Incorrect Info");
        }
    }
}
