package com.inferingrules.exception;

/**
 * Created by jean on 12/17/17.
 */
public class DataTypeException extends RuntimeException {

    public DataTypeException( String message, Throwable throwable ) {
        super( message, throwable );
    }
}
