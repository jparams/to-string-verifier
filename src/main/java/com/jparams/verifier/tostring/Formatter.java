package com.jparams.verifier.tostring;

/**
 * Formatter for a data type
 *
 * @param <T> type
 */
public interface Formatter<T>
{
    /**
     * Format the item to string
     *
     * @param item item
     * @return string value
     */
    String format(T item);
}
