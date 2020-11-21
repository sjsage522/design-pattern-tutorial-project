package io.wisoft.shop.user.view;

import io.wisoft.shop.util.scanner.SingleScanner;
import io.wisoft.shop.user.controller.UserController;
import java.util.Scanner;

public class RegisterView {
    final UserController controller = new UserController();
    final Scanner scanner = SingleScanner.getInstance();


    public void register() {
        SettingView.displaySetting();
        System.out.println("### REGISTER DISPLAY ###");

        System.out.print("ID : "); String id = scanner.nextLine();
        System.out.print("PW : "); String pw = scanner.nextLine();
        System.out.print("username : "); String userName = scanner.nextLine();
        System.out.print("gender : "); String gender = scanner.nextLine();
        System.out.print("email : "); String email = scanner.nextLine();
        System.out.print("address : "); String address = scanner.nextLine();
        String rankId = "None";

        if (controller.registerUser(id, pw, userName, gender, email, address, rankId))
            System.out.println("SUCCESS REGISTER");
        else
            System.out.println("이미 존재하는 아이디 입니다.");
    }
}
