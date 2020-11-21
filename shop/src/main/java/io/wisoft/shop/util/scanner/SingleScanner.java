package io.wisoft.shop.util.scanner;

import java.util.Scanner;

public class SingleScanner {

    // thread not safe
    /*
        Early Loading
     */
    private static final Scanner instance = new Scanner(System.in);
    private SingleScanner() {}
    public static Scanner getInstance() {
        return instance;
    }

    /*
        Lazy Loading
     */
//    private static Scanner instance;
//    private SingleScanner() {}
//    public static Scanner getInstance() {
//        if (instance == null) {
//            instance = new Scanner(System.in);
//        }
//        return instance;
//    }
}
