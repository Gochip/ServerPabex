package com.server;

import java.util.TreeMap;

/**
 *
 * @author Parisi Germán & Bertola Federico
 * @version 1.0
 */
public class Helper {

    private static TreeMap<Byte, Character> abecedario;

    public static void nextId(StringBuilder currentId) {
        if (abecedario == null) {
            abecedario = new TreeMap<>();
            init();
        }
        int i = currentId.length() - 1;
        while (i >= 0) {
            char c = currentId.charAt(i);
            byte cod = (byte) c;
            char nextChar = abecedario.get(cod);
            currentId.replace(i, i + 1, String.valueOf(nextChar));
            if (c != 'z') {
                break;
            }
            i--;
        }
    }

    private static void init() {
        for (byte i = 48; i < 57; i++) {
            abecedario.put(i, (char) (i + 1));
        }
        abecedario.put((byte) 57, 'A');
        for (byte i = 65; i < 90; i++) {
            abecedario.put(i, (char) (i + 1));
        }
        abecedario.put((byte) 90, 'a');
        for (byte i = 97; i < 122; i++) {
            abecedario.put(i, (char) (i + 1));
        }
        abecedario.put((byte) 122, '0');
    }
    
}
