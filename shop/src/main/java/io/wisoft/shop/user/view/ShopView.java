package io.wisoft.shop.user.view;

public abstract class ShopView {
    public void printShopMenu(final String id) {
        hook(id);
        selectMenu();
    }

    abstract void selectMenu();
    private void hook(final String id) {
        System.out.println("WELCOME, " + id + "!!!");
    }
}
