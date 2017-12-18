package com.inferingrules.datatype;

/**
 * Created by jean on 12/17/17.
 */
@FunctionalInterface
interface DataTypeMapInstance {

    enum Name {
        INTEGER
    }

    DataType instance( String checkValue );

}
