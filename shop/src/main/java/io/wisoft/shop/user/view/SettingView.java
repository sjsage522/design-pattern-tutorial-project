package io.wisoft.shop.user.view;

import io.wisoft.shop.util.scanner.SingleScanner;

import java.util.Scanner;

public class SettingView {
    final static Scanner scanner = SingleScanner.getInstance();

    public static void displaySetting() {
        scanner.nextLine();
        for(int i=0; i<40; i++) System.out.println();
    }
}
