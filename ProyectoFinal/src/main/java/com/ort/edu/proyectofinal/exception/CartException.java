package com.ort.edu.proyectofinal.exception;

public class CartException extends Exception {

    public CartException() {
        super();
    }

    public CartException(String m) {
        super(m);
    }

    public CartException(String m, Throwable t) {
        super(m, t);
    }
}

