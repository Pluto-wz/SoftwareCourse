package com.wangzheng.utils;

public class StringException extends Exception{

    public StringException() {
        super();
    }

    public StringException(String message) {
        super(message);
    }

    public StringException(String message, Throwable cause) {
        super(message, cause);
    }

    public StringException(Throwable cause) {
        super(cause);
    }
}
