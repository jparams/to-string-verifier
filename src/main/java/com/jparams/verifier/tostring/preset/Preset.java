package com.jparams.verifier.tostring.preset;

import com.jparams.verifier.tostring.ToStringVerifier;

/**
 * Preset
 */
@FunctionalInterface
public interface Preset
{
    /**
     * Apply preset to the verifier
     *
     * @param verifier verifier
     */
    void apply(ToStringVerifier verifier);
}
