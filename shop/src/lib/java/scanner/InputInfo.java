package lib.java.scanner;

import io.wisoft.shop.payment.Card;
import io.wisoft.shop.payment.Cash;
import io.wisoft.shop.payment.Payment;
import io.wisoft.shop.user.view.SettingView;

import java.util.Scanner;
import java.util.UUID;

public class InputInfo {
    Scanner scanner = SingleScanner.getInstance();

    public String inputUserId() {
        SettingView.displaySetting();
        System.out.println("고객 아이디를 입력하세요.");
        System.out.print(">> ");
        String id = scanner.nextLine();
        return id;
    }

    public String inputProductId() {
        SettingView.displaySetting();
        System.out.println("제품 아이디를 입력하세요.");
        System.out.print(">> ");
        String id = scanner.nextLine();
        return id;
    }

    public int supplyProductCount() {
        System.out.println("조정할 재고량을 입력하세요");
        System.out.print(">> ");
        String count = scanner.nextLine();
        return Integer.parseInt(count);
    }

    public String inputCartId() {
        SettingView.displaySetting();
        System.out.println("장바구니 아이디를 입력하세요.");
        System.out.print(">> ");
        String id = scanner.nextLine();
        return id;
    }

    public int inputProductCount() {
        System.out.println("구매 갯수를 입력하세요.");
        System.out.print(">> ");
        String count = scanner.nextLine();
        return Integer.parseInt(count);
    }

    public int inputUnitPrice() {
        System.out.println("조정할 단가를 입력하세요.");
        System.out.print(">> ");
        String unitPrice = scanner.nextLine();
        return Integer.parseInt(unitPrice);
    }

    public String[] inputProductInfo() {
        SettingView.displaySetting();
        String[] temp = new String[4];

        System.out.println("제품의 정보를 입력하세요.");
        System.out.print("제품 이름 >> ");
        temp[1] = scanner.nextLine();
        System.out.print("단가 >> ");
        temp[2] = scanner.nextLine();
        System.out.print("갯수 >> ");
        temp[3] = scanner.nextLine();

        /*
            https://offbyone.tistory.com/303
         */
        temp[0] = UUID.randomUUID().toString().toUpperCase().substring(0, 8);

        return temp;
    }

    public Payment selectPaymentMethod() {
        SettingView.displaySetting();
        System.out.println("구매 방법을 선택해 주세요.");
        System.out.println("1. 카드");
        System.out.println("2. 현금");
        System.out.print(">> ");
        String number = scanner.nextLine();

        int num = Integer.parseInt(number);
        Payment payment;

        switch (num) {
            case 1:
                payment = new Card();
                break;
            case 2:
                payment = new Cash();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + num);
        }

        return payment;
    }
}
