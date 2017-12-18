package com.inferingrules.datatype;

import com.inferingrules.exception.DataTypeException;
import com.inferingrules.operator.Operator;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.inferingrules.operator.Operator.Name.*;

/**
 * Created by jean on 12/17/17.
 */
public class TypeInteger implements DataType {

    private String checkValue;

    private final Map< String, Operator > OPERATOR_MAP = Collections.unmodifiableMap(
        new HashMap< String, Operator >() {{
            put( EQUALS.name(), otherValue -> parse( otherValue ).equals( parse( checkValue ) ) );
            put( NOT_EQUALS.name(), otherValue -> ! parse( otherValue ).equals( parse( checkValue ) ) );
            put( GREATER_THAN.name(), otherValue -> parse( otherValue ) > parse( checkValue ) );
            put( GREATER_THAN_OR_EQUALS.name(), otherValue -> parse( otherValue ) >= parse( checkValue ) );
            put( IS_IN.name(),
                otherValue -> Arrays.stream( checkValue.split(",") )
                    .map( String::trim )
                    .map( Long::valueOf )
                    .filter( v -> v.equals( parse( otherValue ) ) )
                    .findAny()
                    .isPresent()
            );
        }}
    );

    public TypeInteger( String checkValue ) {
        if ( StringUtils.isEmpty( checkValue ) ) {
            throw new IllegalArgumentException( "Argument 'dataTypeKey' is mandatory." );
        }
        this.checkValue = checkValue;
    }

    protected Long parse( String value ) {
        try {
            return Long.valueOf( value );
        } catch ( NumberFormatException e ) {
            throw new DataTypeException( "Impossible parse the value [" + value + "] for the TypeInteger data type.", e );
        }
    }

    @Override
    public Map< String, Operator > operators() {
        return OPERATOR_MAP;
    }

}
