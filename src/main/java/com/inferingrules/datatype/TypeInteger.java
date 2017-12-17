package com.inferingrules.datatype;

import com.inferingrules.exception.DataTypeException;
import com.inferingrules.operator.IsEqual;
import org.springframework.util.StringUtils;

/**
 * Created by jean on 12/17/17.
 */
public class TypeInteger implements DataType, IsEqual {

    private Long value;

    @Override
    public DataType parse( String checkValue ) {
        if ( StringUtils.isEmpty( checkValue ) )
            throw new IllegalArgumentException( "Argument 'dataTypeKey' is mandatory." );

        try {
            this.value = Long.valueOf( checkValue );
        } catch ( NumberFormatException e ) {
            throw new DataTypeException( "Impossible parse the value [" + checkValue + "] for the TypeInteger data type.", e );
        }

        return this;
    }

    @Override
    public boolean isEqual( String otherValue ) {
        Long outerValue;
        try {
            outerValue = Long.valueOf( otherValue );
        } catch ( NumberFormatException e ) {
            throw new DataTypeException( "The provided argument 'otherValue' cannot be parsed to a java Long format. Provided value=[" + otherValue + "]", e );
        }

        return this.value.equals( outerValue );
    }
}
