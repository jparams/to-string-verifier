package com.jparams.tester;

/**
 * Style of the class name
 */
public enum NameStyle
{
    /**
     * Tests the name of the class is present as returned by {@link Class#getName()}.
     */
    NAME,

    /**
     * Tests the name of the class is present as returned by {@link Class#getSimpleName()}.
     */
    SIMPLE_NAME,

    /**
     * Tests the name of the class is present as returned by {@link Class#getCanonicalName()}.
     */
    CANONICAL_NAME;


    public String getName(final Class<?> clazz)
    {
        switch (this)
        {
            case NAME:
                return clazz.getName();
            case SIMPLE_NAME:
                return clazz.getSimpleName();
            case CANONICAL_NAME:
                return clazz.getCanonicalName();
            default:
                throw new UnsupportedOperationException("Unknown name type " + this);
        }
    }
}
