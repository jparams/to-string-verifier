package com.jparams.verifier.tostring;

/**
 * Provides the System identity hash code by calling {@link System#identityHashCode(Object)}
 */
public class SystemIdentityHashCodeProvider implements HashCodeProvider
{
    @Override
    public int provide(final Object obj)
    {
        return System.identityHashCode(obj);
    }
}
