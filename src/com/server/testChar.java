package com.server;

/**
 *
 *
 * @author Parisi Germán & Bertola Federico
 * @version 1.0
 */
public class testChar {

    public static void main(String args[]) {
        int count = 0;
        for (int i = 48; i < 58; i++) {
            System.out.println(i + ") " + (char) i);
            count++;
        }
        for (int i = 65; i < 91; i++) {
            System.out.println(i + ") " + (char) i);
            count++;
        }
        for (int i = 97; i < 123; i++) {
            System.out.println(i + ") " + (char) i);
            count++;
        }
        System.out.println(count);
    }
}
