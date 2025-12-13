package com.ort.edu.proyectofinal.exception;

public class OrderException extends Exception {

    public OrderException() {
        super();
    }

    public OrderException(String m) {
        super(m);
    }

    public OrderException(String m, Throwable t) {
        super(m, t);
    }
}
