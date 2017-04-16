/*
 * @(#)DataOptionsEnum.java
 *
 * Copyright:	Copyright (c) 2012
 * Company:		Oathouse.com Ltd
 */
package io.aistac.common.data.obcache.objectstore;

import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.List;

/**
 * The {@code DataOptionsEnum} Enumeration is used to identify the storage type
 * identifying how the ObjectBean data should be stored.
 *
 * @author Darryl Oatridge
 * @version 1.00 07-Nov-2012
 */
public enum DataOptionsEnum {
    /** Persist to {@code ObjectBean} */
    QUEUE,
    /** Cipher the {@code ObjectBean} */
    CIPHER;

    /**
     * compares a DataOptionsEnum against a list, returning true if the list contains the enumeration.
     *
     * @param array the array or arguments
     * @return true if the list contains the enumeration
     */
    public boolean isIn(DataOptionsEnum[] array) {
        return (asList(array).contains(this));
    }

    /**
     * compares a DataOptionsEnum against a list, returning true if the list is empty.
     *
     * @param array the array or arguments
     * @return true if the list is empty
     */
    public boolean isEmpty(DataOptionsEnum[] array) {
        return (asList(array).isEmpty());
    }

    /**
     * A static helper method to convert a List to a DataOptionsEnum array.
     *
     * @param list a list of DataOptionsEnum
     * @return the list converted to an array of DataOptionsEnum
     */
    public static DataOptionsEnum[] toArray(List<DataOptionsEnum> list) {
        return list.toArray(new DataOptionsEnum[list.size()]);
    }

    /**
     * A static helper to add DataOptionsEnum enumeration from an already existing DataOptionsEnum array
     *
     * @param array the original array of DataOptionsEnum enumeration
     * @param options the DataOptionsEnum enumerations to be added
     * @return the new array of DataOptionsEnum enumerations
     */
    public static DataOptionsEnum[] addToArray(DataOptionsEnum[] array, DataOptionsEnum... options) {
        ArrayList<DataOptionsEnum> arrayList = new ArrayList<>(asList(array));
        for(DataOptionsEnum option : options) {
            if(!arrayList.contains(option)) {
                arrayList.add(option);
            }
        }
        return arrayList.toArray(new DataOptionsEnum[arrayList.size()]);
    }

    /**
     * A static helper to remove DataOptionsEnum enumeration from an already existing DataOptionsEnum array
     *
     * @param array the original array of DataOptionsEnum enumeration
     * @param options the DataOptionsEnum enumerations to be removed
     * @return the new array of DataOptionsEnum enumerations
     */
    public static DataOptionsEnum[] removeFromArray(DataOptionsEnum[] array, DataOptionsEnum... options) {
        ArrayList<DataOptionsEnum> arrayList = new ArrayList<>(asList(array));
        for(DataOptionsEnum option : options) {
            if(arrayList.contains(option)) {
                arrayList.remove(option);
            }
        }
        return arrayList.toArray(new DataOptionsEnum[arrayList.size()]);
    }

}
