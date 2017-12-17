package com.inferingrules.datatype;

import com.inferingrules.exception.UnknownDataType;
import com.inferingrules.exception.UnknownOperator;
import org.junit.Test;
import org.springframework.util.Assert;

/**
 * Created by jean on 12/17/17.
 */
public class TypeIntegerTest {

    @Test( expected = UnknownDataType.class )
    public void test_unknown_dataType() {
        String aMisspelledDataType = "int", checkValue = "1";
        DataType.get( aMisspelledDataType, checkValue );
    }

    @Test
    public void test_valid_dataType() {
        String dataTypeKey = "INTEGER", checkValue = "1";
        DataType.get( dataTypeKey, checkValue );
    }

    @Test( expected = UnknownOperator.class )
    public void test_unknown_operator() {
        String dataTypeKey = "INTEGER", checkValue = "1, 2, 3, 4, 5, 6", aMisspelledOperator = "is_in", payloadValue = "7";
        Assert.isTrue( DataType.get( dataTypeKey, checkValue ).operator( aMisspelledOperator ).apply( payloadValue ) );
    }

    @Test
    public void test_valid_operatorEquals() {
        String dataTypeKey = "INTEGER", checkValue = "123456", operatorKey = "EQUALS";

        Assert.isTrue( DataType.get( dataTypeKey, checkValue ).operator( operatorKey ).apply( "123456" ) );
        Assert.isTrue( ! DataType.get( dataTypeKey, checkValue ).operator( operatorKey ).apply( "123457" ) );
    }

    @Test
    public void test_valid_operatorNotEquals() {
        String dataTypeKey = "INTEGER", checkValue = "123456", operatorKey = "NOT_EQUALS";

        Assert.isTrue( ! DataType.get( dataTypeKey, checkValue ).operator( operatorKey ).apply( "123456" ) );
        Assert.isTrue( DataType.get( dataTypeKey, checkValue ).operator( operatorKey ).apply( "123457" ) );
    }

    @Test
    public void test_valid_operatorGreaterThan() {
        String dataTypeKey = "INTEGER", checkValue = "123456", operatorKey = "GREATER_THAN";

        Assert.isTrue( ! DataType.get( dataTypeKey, checkValue ).operator( operatorKey ).apply( "123455" ) );
        Assert.isTrue( ! DataType.get( dataTypeKey, checkValue ).operator( operatorKey ).apply( "123456" ) );
        Assert.isTrue( DataType.get( dataTypeKey, checkValue ).operator( operatorKey ).apply( "123457" ) );
    }

    @Test
    public void test_valid_operatorGreaterThanOrEquals() {
        String dataTypeKey = "INTEGER", checkValue = "123456", operatorKey = "GREATER_THAN_OR_EQUALS";

        Assert.isTrue( ! DataType.get( dataTypeKey, checkValue ).operator( operatorKey ).apply( "123455" ) );
        Assert.isTrue( DataType.get( dataTypeKey, checkValue ).operator( operatorKey ).apply( "123456" ) );
        Assert.isTrue( DataType.get( dataTypeKey, checkValue ).operator( operatorKey ).apply( "123457" ) );
    }

    @Test
    public void test_simple_IsIn_operation() {
        String dataTypeKey = "INTEGER", checkValue = "187", operatorKey = "IS_IN";

        Assert.isTrue( DataType.get( dataTypeKey, checkValue ).operator( operatorKey ).apply( "187" ) );
        Assert.isTrue( ! DataType.get( dataTypeKey, checkValue ).operator( operatorKey ).apply( "7" ) );
        Assert.isTrue( ! DataType.get( dataTypeKey, checkValue ).operator( operatorKey ).apply( "8" ) );
    }

    @Test
    public void test_complex_IsIn_operation() {
        String dataTypeKey = "INTEGER", checkValue = "198, 264, 312, 455, 591, 611", operatorKey = "IS_IN";

        Assert.isTrue( DataType.get( dataTypeKey, checkValue ).operator( operatorKey ).apply( "198" ) );
        Assert.isTrue( DataType.get( dataTypeKey, checkValue ).operator( operatorKey ).apply( "611" ) );
        Assert.isTrue( ! DataType.get( dataTypeKey, checkValue ).operator( operatorKey ).apply( "761" ) );
        Assert.isTrue( ! DataType.get( dataTypeKey, checkValue ).operator( operatorKey ).apply( "802" ) );
    }

}
