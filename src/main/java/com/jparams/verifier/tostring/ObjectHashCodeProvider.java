package com.jparams.verifier.tostring;

/**
 * Provides the hash code by calling {@link Object#hashCode()}
 */
public class ObjectHashCodeProvider implements HashCodeProvider
{
    @Override
    public int provide(final Object obj)
    {
        return obj.hashCode();
    }
}
