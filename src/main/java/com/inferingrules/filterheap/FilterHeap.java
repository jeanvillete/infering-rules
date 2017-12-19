package com.inferingrules.filterheap;

import com.inferingrules.exception.FilterHeapException;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.inferingrules.common.Constants.*;
import static com.inferingrules.filterheap.FilterArray.ValidateModel;

/**
 * Created by jean on 12/18/17.
 */
public class FilterHeap {

    private List< FilterField > heap = new ArrayList<>();

    public FilterHeap( String canonicalPath ){
        if ( StringUtils.isEmpty( canonicalPath ) ) {
            throw new IllegalArgumentException( "Argument 'canonicalPath' is mandatory." );
        }

        this.build( canonicalPath );
    }

    private void build( String canonicalPath ) {
        StringBuffer buffer = new StringBuffer();
        boolean onQuotesContext = false;
        boolean onArrayContext = false;

        for ( int i = 0; i < canonicalPath.length(); i++ ) {
            char currentChar = canonicalPath.charAt( i );

            if ( currentChar == QUOTES ) {
                onQuotesContext = !onQuotesContext;
            }

            if ( onQuotesContext ) {
                buffer.append( currentChar );
                continue;
            } else if ( currentChar == OPEN_BRACKETS ) {
                if ( onArrayContext ) {
                    throw new IllegalStateException( "Invalid canonical path. An open brackets was provided in the context of another already opened." );
                }
                onArrayContext = true;
                buffer.append( currentChar );
                continue;
            } else if ( currentChar == CLOSE_BRACKETS ) {
                if ( !onArrayContext ) {
                    throw new IllegalStateException( "Invalid canonical path. A close brackets was provided but no corresponding open brackets was found." );
                }
                onArrayContext = false;
                buffer.append( currentChar );
                this.addHeap( this.newArray( buffer.toString() ), buffer );
                continue;
            } else if ( currentChar == DOT ) {
                buffer.append( currentChar );
                this.addHeap( this.newFilterObject( buffer.toString() ), buffer );
                continue;
            }

            buffer.append( currentChar );
        }

        if ( onQuotesContext ) {
            throw new FilterHeapException( "String canonical path has reached the end without a closing double quotes." );
        } else if ( onArrayContext ) {
            throw new FilterHeapException( "String canonical path has reached the end without a closing brackets." );
        }

        if ( buffer.length() > 0 ) {
            this.addHeap( this.newFilterObject( buffer.toString() ), buffer );
        }
    }

    private void addHeap( FilterField filterField, StringBuffer buffer ) {
        this.heap.add( filterField );
        buffer.delete( 0, buffer.length() );
    }

    private FilterArray newArray( String fullKey ) {
        Pattern pattern = Pattern.compile( "(^.*)(\\[\\w{0,3}]$)" );
        Matcher matcher = pattern.matcher( fullKey.trim() );
        if ( !matcher.matches() ) {
            throw new FilterHeapException( "Impossible infer Array structure for the key; " + fullKey );
        }

        String arrayCharacters = matcher.group( 2 );
        arrayCharacters = arrayCharacters.trim().replaceAll( "\\[|]", "" ).toUpperCase();
        ValidateModel validateModel = arrayCharacters.equals( "ALL" ) ? ValidateModel.ALL : ValidateModel.ANY;

        String key = matcher.group( 1 );
        key = key.trim().replaceAll( "\"", "" );

        return new FilterArray( key, validateModel );
    }

    private FilterField newFilterObject( String fullKey ) {
        if ( fullKey.endsWith( "." ) ) {
            fullKey = fullKey.substring( 0, fullKey.length() - 2 );
        }
        fullKey = fullKey.trim().replaceAll( "\"", "" );

        return new FilterObject( fullKey );
    }

    public List< FilterField > getHeap() {
        return Collections.unmodifiableList( this.heap );
    }
}
