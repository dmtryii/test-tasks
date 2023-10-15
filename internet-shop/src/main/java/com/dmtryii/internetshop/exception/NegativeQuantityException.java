package com.dmtryii.internetshop.exception;

public class NegativeQuantityException extends RuntimeException {
    public NegativeQuantityException(String msg) {
        super(msg);
    }
}
