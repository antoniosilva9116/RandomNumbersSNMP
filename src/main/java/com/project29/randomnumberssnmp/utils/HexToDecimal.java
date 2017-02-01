/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project29.randomnumberssnmp.utils;

/**
 *
 * @author anton
 */
public class HexToDecimal {

    private static String HexRep = "0123456789ABCDEF";

    public static int hexToDecimal(String s) {

        String digits = HexRep;
        s = s.toUpperCase();

        int val = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16 * val + d;
        }
        return val;
    }

    public static long hexToDecimalNumber(String hex) {

        hex = hex.toUpperCase();

        int l = hex.length();
        char curDig;
        long dec = 0L;
        int d = 0, power = 0;
        for (int i = l - 1; i >= 0; i--) { //Running loop backward to extract digits from the end

            curDig = hex.charAt(i);
            if (curDig >= '0' && curDig <= '9') {
                d = curDig - 48;
            }
            if (curDig >= 'A' && curDig <= 'F') {
                d = curDig - 55;
            }

            dec = dec + d * (long) Math.pow(16, power);
            power++;
        }
        System.out.println("The number in Decimal System = " + dec);

        return dec;
    }

// precondition:  d is a nonnegative integer
    public static String decimalToHex(int d) {
        String digits = HexRep;
        if (d == 0) {
            return "0";
        }
        String hex = "";
        while (d > 0) {
            int digit = d % 16;                // rightmost digit
            hex = digits.charAt(digit) + hex;  // string concatenation
            d = d / 16;
        }
        return hex;
    }

    public static void main(String[] args) {
        int decimal = hexToDecimal(args[0]);
        System.out.println(decimal);

        String hex = decimalToHex(decimal);
        System.out.println(hex);
    }
}
