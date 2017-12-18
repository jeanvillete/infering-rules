package com.inferingrules.operator;

/**
 * Created by jean on 12/17/17.
 */
@FunctionalInterface
public interface Operator {

    enum Name {
        EQUALS,
        NOT_EQUALS,
        GREATER_THAN,
        GREATER_THAN_OR_EQUALS,
        IS_IN
    }

    boolean apply( String otherValue );

}
