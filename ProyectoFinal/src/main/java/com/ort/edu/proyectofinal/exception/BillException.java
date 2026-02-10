package com.ort.edu.proyectofinal.exception;

public class BillException extends Exception {

    public BillException() {
        super();
    }

    public BillException(String m) {
        super(m);
    }

    public BillException(String m, Throwable t) {
        super(m, t);
    }
}