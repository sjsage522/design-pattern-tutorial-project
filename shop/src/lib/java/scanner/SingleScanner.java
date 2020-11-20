package lib.java.scanner;

import java.util.Scanner;

public class SingleScanner {
    private static Scanner instance;

    public static Scanner getInstance() {
        if (instance == null) {
            instance = new Scanner(System.in);
        }
        return instance;
    }
}
