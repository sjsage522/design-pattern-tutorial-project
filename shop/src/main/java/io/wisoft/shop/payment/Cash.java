package io.wisoft.shop.payment;

public class Cash implements Payment {
    @Override
    public void checkPaymentMethod() {
        System.out.println("현금결제 입니다.");
    }
}
