package com.inferingrules.filterheap;

/**
 * Created by jean on 12/18/17.
 */
public class FilterArray extends FilterField {

    public enum ValidateModel {
        ANY, ALL
    }

    private ValidateModel validateModel;

    public FilterArray( String key, ValidateModel validateModel ) {
        super( key );
        this.validateModel = validateModel;
    }

    public ValidateModel getValidateModel() {
        return validateModel;
    }
}
