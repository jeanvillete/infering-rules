package com.inferingrules.datatype;

import org.springframework.util.StringUtils;

/**
 * Created by jean on 12/17/17.
 */
@FunctionalInterface
public interface DataType {

    default DataType get( String dataTypeKey, String checkValue ) {
        if ( StringUtils.isEmpty( dataTypeKey ) ) {
            throw new IllegalArgumentException( "Argument 'dataTypeKey' is mandatory." );
        } else if ( StringUtils.isEmpty( checkValue ) ) {
            throw new IllegalArgumentException( "Argument 'checkValue' is mandatory." );
        }

        if ( dataTypeKey.equals( "INTEGER" ) ) {
            return new TypeInteger().parse( checkValue );
        } else {
            throw new IllegalArgumentException( "Unknown 'dataTypeKey'. Provided value=[" + dataTypeKey + "]" );
        }
    }

    DataType parse( String checkValue ) ;

}
