package com.jparams.verifier.tostring.preset;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;

public class EclipsePreset implements Preset
{
    @Override
    public void apply(final ToStringVerifier verifier)
    {
        verifier.withClassName(NameStyle.SIMPLE_NAME).withHashCode(false);
    }
}
