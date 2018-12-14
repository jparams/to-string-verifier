package com.jparams.verifier.tostring;

/**
 * Hash code provider
 */
@FunctionalInterface
public interface HashCodeProvider
{
    /**
     * Provide object hash code
     *
     * @param obj object
     * @return hash code
     */
    int provide(Object obj);
}
