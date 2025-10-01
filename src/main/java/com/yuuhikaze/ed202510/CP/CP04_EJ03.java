package com.yuuhikaze.ed202510.CP;

public class CP04_EJ03 {

    static boolean isPalindrome(String str) {
        StringBuilder str_builder = new StringBuilder();
        byte[] str_bytes = str.toLowerCase().getBytes();
        for (int i = str_bytes.length - 1; i >= 0; i--) {
            str_builder.append((char) str_bytes[i]);
        }
        return str.toLowerCase().equals(str_builder.toString());
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome("Palabra"));
        System.out.println(isPalindrome("Aba"));
        System.out.println(isPalindrome("Reconocer"));
        System.out.println(isPalindrome("Sobreverbos"));
        System.out.println(isPalindrome("Rajar"));
    }
}
