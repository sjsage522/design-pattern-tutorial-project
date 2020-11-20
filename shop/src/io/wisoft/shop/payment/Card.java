package io.wisoft.shop.payment;

public class Card implements Payment {
    @Override
    public void checkPaymentMethod() {
        System.out.println("카드결제 입니다.");
    }
}
