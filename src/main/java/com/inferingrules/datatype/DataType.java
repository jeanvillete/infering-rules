package com.inferingrules.datatype;

import com.inferingrules.exception.UnknownDataType;
import com.inferingrules.exception.UnknownOperator;
import com.inferingrules.operator.Operator;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jean on 12/17/17.
 */
public interface DataType {

    @FunctionalInterface
    interface DataTypeMapInstance {
        DataType instance( String checkValue );
    }

    Map< String, DataTypeMapInstance> DATA_TYPE_MAP = Collections.unmodifiableMap(
        new HashMap<String, DataTypeMapInstance>() {{
            put( "INTEGER", TypeInteger::new );
        }}
    );

    static DataType get( String dataTypeKey, String checkValue ) {
        if ( StringUtils.isEmpty( dataTypeKey ) ) {
            throw new IllegalArgumentException( "Argument 'dataTypeKey' is mandatory." );
        } else if ( StringUtils.isEmpty( checkValue ) ) {
            throw new IllegalArgumentException( "Argument 'checkValue' is mandatory." );
        }

        DataTypeMapInstance dataTypeMap = DATA_TYPE_MAP.get( dataTypeKey );
        if ( dataTypeMap != null ) {
            return DATA_TYPE_MAP.get( dataTypeKey ).instance( checkValue );
        } else {
            throw new UnknownDataType( "Unknown data type implementation for the 'dataTypeKey' provided. Provided value=[" + dataTypeKey + "]" );
        }
    }

    default Operator operator( String operatorKey ) {
        if ( CollectionUtils.isEmpty( operators() ) ) {
            throw new IllegalStateException( "The method 'operators' has not been properly implemented." );
        }

        Operator operator = operators().get( operatorKey );
        if ( operator != null ) {
            return operator;
        } else {
            throw new UnknownOperator( "Unknown operator implementation for the 'operatorKey' provided. Provided value=[" + operatorKey + "]" );
        }
    }

    Map< String, Operator > operators();

}
