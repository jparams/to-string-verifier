package com.jparams.verifier.tostring;

import java.lang.reflect.Field;

/**
 * Field filter
 */
@FunctionalInterface
public interface FieldFilter
{
    /**
     * Return true to allow the field to be tested
     *
     * @param subject class under test
     * @param field   field to allow or disallow
     * @return true to allow
     */
    boolean matches(Class<?> subject, Field field);
}
