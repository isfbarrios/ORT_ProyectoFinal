package com.ort.edu.proyectofinal.exception;

public class AuthException extends Exception {

    public AuthException() {
        super();
    }

    public AuthException(String m) {
        super(m);
    }

    public AuthException(String m, Throwable t) {
        super(m, t);
    }
}
