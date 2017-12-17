package com.inferingrules.operator;

/**
 * Created by jean on 12/17/17.
 */
@FunctionalInterface
public interface Operator {

    boolean apply( String otherValue );

}
