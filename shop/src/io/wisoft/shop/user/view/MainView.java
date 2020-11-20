package io.wisoft.shop.user.view;

import lib.java.scanner.SingleScanner;
import java.util.Scanner;

public class MainView {
    final Scanner scanner = SingleScanner.getInstance();
    final LoginView loginView = new LoginView();
    final RegisterView registerView = new RegisterView();

    public void printMainView() {
        while (true) {
            System.out.println("### DESIGN PATTERN PROJECT SHOP ###");
            System.out.println("1. SIGN IN");
            System.out.println("2. SIGN UP");
            System.out.println("3. EXIT");

            System.out.print(">> ");
            int select = scanner.nextInt();

            switch (select) {
                case 1:
                    loginView.login();
                    break;
                case 2:
                    registerView.register();
                    break;
                case 3:
                    System.out.println("BYE...");
                    return;
            }
        }
    }
}
