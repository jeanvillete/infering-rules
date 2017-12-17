package com.inferingrules.exception;

/**
 * Created by jean on 12/17/17.
 */
public class UnknownOperator extends DataTypeException {

    public UnknownOperator( String message ) {
        super( message, null );
    }

}
