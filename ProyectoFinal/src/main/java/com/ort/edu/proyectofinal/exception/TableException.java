package com.ort.edu.proyectofinal.exception;

public class TableException extends Exception {

    public TableException() {
        super();
    }

    public TableException(String m) {
        super(m);
    }

    public TableException(String m, Throwable t) {
        super(m, t);
    }
}
