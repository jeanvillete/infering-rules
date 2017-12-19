package com.inferingrules.filterheap;

import org.junit.Test;
import org.springframework.util.Assert;

/**
 * Created by jean on 12/18/17.
 */
public class FilterHeapTest {

    @Test
    public void simpleFields() {
        String[] fields = new String[]{
            "field",
            "\"field\"",
            "\"field.anotherField\"",
            "\"field.anotherField[]\"",
            "\"field.anotherField[any]\"",
            "\"field.anotherField[all]\""
        };

        for ( String field : fields ) {
            FilterHeap filter = new FilterHeap( field );

            Assert.notNull( filter.getHeap() );
            Assert.notEmpty( filter.getHeap() );
            Assert.state( filter.getHeap().size() == 1 );
            Assert.notNull( filter.getHeap().get( 0 ) );
            Assert.isInstanceOf( FilterObject.class, filter.getHeap().get( 0 ) );
            Assert.state( filter.getHeap().get( 0 ).getKey().equals( field.replaceAll( "\"", "" ) ) );
        }
    }

    @Test
    public void nestedFields() {
        String[] fields = new String[]{
            "field.foo",
            "\"field\".\"foo\"",
            "\"field\".\"anotherField\"",
            "\"field\".\"anotherField[]\"",
            "\"field\".\"anotherField[any]\"",
            "\"field\".\"anotherField[all]\""
        };

        for ( String field : fields ) {
            FilterHeap filter = new FilterHeap( field );

            Assert.notNull( filter.getHeap() );
            Assert.notEmpty( filter.getHeap() );
            Assert.state( filter.getHeap().size() == 2 );
            Assert.notNull( filter.getHeap().get( 0 ) );
            Assert.isInstanceOf( FilterObject.class, filter.getHeap().get( 0 ) );
            Assert.notNull( filter.getHeap().get( 1 ) );
            Assert.isInstanceOf( FilterObject.class, filter.getHeap().get( 1 ) );
        }
    }

    @Test
    public void simpleArrayFields_implicit_validateModel_any() {
        String[] fields = new String[]{
            "field[]",
            "\"field\"[]",
            "\"field.anotherField\"[]",
            "\"field.anotherField[]\"[]",
            "\"field.anotherField[any]\"[]",
            "\"field.anotherField[all]\"[]"
        };

        for ( String field : fields ) {
            FilterHeap filter = new FilterHeap( field );

            Assert.notNull( filter.getHeap() );
            Assert.notEmpty( filter.getHeap() );
            Assert.state( filter.getHeap().size() == 1 );

            Assert.notNull( filter.getHeap().get( 0 ) );
            Assert.isInstanceOf( FilterArray.class, filter.getHeap().get( 0 ) );

            FilterArray filterArray = ( FilterArray ) filter.getHeap().get( 0 );
            Assert.state( filterArray.getKey().equals( field.substring( 0, field.length() - 2 ).replaceAll( "\"", "" ) ) );
            Assert.state( filterArray.getValidateModel().equals( FilterArray.ValidateModel.ANY ) );
        }
    }

    @Test
    public void nestedArrayFields_implicit_validateModel_any() {
        String[] fields = new String[]{
            "field[]anotherField",
            "\"field\"[]anotherField",
            "\"field.anotherField\"[]anotherField",
            "\"field.anotherField[]\"[]anotherField",
            "\"field.anotherField[any]\"[]anotherField",
            "\"field.anotherField[all]\"[]anotherField"
        };

        for ( String field : fields ) {
            FilterHeap filter = new FilterHeap( field );

            Assert.notNull( filter.getHeap() );
            Assert.notEmpty( filter.getHeap() );
            Assert.state( filter.getHeap().size() == 2 );

            Assert.notNull( filter.getHeap().get( 0 ) );
            Assert.isInstanceOf( FilterArray.class, filter.getHeap().get( 0 ) );

            FilterArray filterArray = ( FilterArray ) filter.getHeap().get( 0 );
            Assert.state( filterArray.getValidateModel().equals( FilterArray.ValidateModel.ANY ) );

            Assert.notNull( filter.getHeap().get( 1 ) );
            Assert.isInstanceOf( FilterObject.class, filter.getHeap().get( 1 ) );
        }
    }

    @Test
    public void simpleArrayFields_explicit_validateModel_any() {
        String[] fields = new String[]{
            "field[any]",
            "\"field\"[any]",
            "\"field.anotherField\"[any]",
            "\"field.anotherField[]\"[any]",
            "\"field.anotherField[any]\"[any]",
            "\"field.anotherField[all]\"[any]"
        };

        for ( String field : fields ) {
            FilterHeap filter = new FilterHeap( field );

            Assert.notNull( filter.getHeap() );
            Assert.notEmpty( filter.getHeap() );
            Assert.state( filter.getHeap().size() == 1 );

            Assert.notNull( filter.getHeap().get( 0 ) );
            Assert.isInstanceOf( FilterArray.class, filter.getHeap().get( 0 ) );

            FilterArray filterArray = ( FilterArray ) filter.getHeap().get( 0 );
            Assert.state( filterArray.getKey().equals( field.substring( 0, field.length() - 5 ).replaceAll( "\"", "" ) ) );
            Assert.state( filterArray.getValidateModel().equals( FilterArray.ValidateModel.ANY ) );
        }
    }

    @Test
    public void simpleArrayFields_explicit_validateModel_all() {
        String[] fields = new String[]{
            "field[all]",
            "\"field\"[all]",
            "\"field.anotherField\"[all]",
            "\"field.anotherField[]\"[all]",
            "\"field.anotherField[any]\"[all]",
            "\"field.anotherField[all]\"[all]"
        };

        for ( String field : fields ) {
            FilterHeap filter = new FilterHeap( field );

            Assert.notNull( filter.getHeap() );
            Assert.notEmpty( filter.getHeap() );
            Assert.state( filter.getHeap().size() == 1 );

            Assert.notNull( filter.getHeap().get( 0 ) );
            Assert.isInstanceOf( FilterArray.class, filter.getHeap().get( 0 ) );

            FilterArray filterArray = ( FilterArray ) filter.getHeap().get( 0 );
            Assert.state( filterArray.getKey().equals( field.substring( 0, field.length() - 5 ).replaceAll( "\"", "" ) ) );
            Assert.state( filterArray.getValidateModel().equals( FilterArray.ValidateModel.ALL ) );
        }
    }

}
