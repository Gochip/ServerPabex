package com.server;

import java.util.HashMap;

/**
 *
 * @author Parisi Germán y Bertola Federico
 * @version 1.0
 */
public class Attributes {

    private HashMap<String, String> attributes;

    public Attributes() {
        attributes = new HashMap<>();
    }

    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public String getAttribute(String key) {
        return attributes.get(key);
    }

    public int getCountAttributes() {
        return attributes.size();
    }

    public boolean removeAttribute(String key) {
        return attributes.remove(key) != null;
    }
}
