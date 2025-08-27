package com.yuuhikaze.ed202510.utils;

import java.util.Scanner;

public class ScannerSingleton {
    private static final Scanner scanner = new Scanner(System.in);

    private ScannerSingleton() {}

    public static int readInteger(String prompt) {
        int result;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                result = scanner.nextInt();
                scanner.nextLine();
                return result;
            } else {
                System.err.println("Invalid input! Provide an integer.");
                scanner.nextLine();
            }
        }
    }

    public static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
