package com.inferingrules.operator;

import com.inferingrules.datatype.DataType;
import org.springframework.util.StringUtils;

/**
 * Created by jean on 12/17/17.
 */
public interface Operator {



    default boolean apply( String operatorKey, DataType dataType, String value ) {
        if ( StringUtils.isEmpty( operatorKey ) ) {
            throw new IllegalArgumentException("Argument 'operatorKey' is mandatory.");
        } else if ( dataType == null ) {
            throw new IllegalArgumentException( "Argument 'dataType' is mandatory." );
        } else if ( StringUtils.isEmpty( value ) ) {
            throw new IllegalArgumentException( "Argument 'value' is mandatory." );
        }

        if ( operatorKey.equals( "EQUALS" ) ) {
            return ( ( IsEqual ) dataType ).isEqual( value );
        } else {
            throw new IllegalArgumentException( "Unknown 'operatorKey'. Provided value=[" + operatorKey + "]" );
        }
    }

}
