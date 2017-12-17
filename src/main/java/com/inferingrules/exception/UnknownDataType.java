package com.inferingrules.exception;

/**
 * Created by jean on 12/17/17.
 */
public class UnknownDataType extends DataTypeException {

    public UnknownDataType(String message ) {
        super( message, null );
    }

}
