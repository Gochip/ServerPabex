package com.server;

/**
 *
 * @author Parisi Germán & Bertola Federico
 * @version 1.0
 */
public class Password {

    private final String password;

    public Password(String password) {
        this.password = password;
    }
    
    public String getString(){
        return this.password;
    }
    
    public boolean isValid(String pass){
        return this.password.equals(pass);
    }
}
