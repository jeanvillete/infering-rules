package com.inferingrules.filterheap;

import org.springframework.util.StringUtils;

/**
 * Created by jean on 12/18/17.
 */
public abstract class FilterField {

    private String key;

    public FilterField( String key ) {
        if ( StringUtils.isEmpty( key ) ) {
            throw new IllegalArgumentException( "Argument 'key' is mandatory." );
        }
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
