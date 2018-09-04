package com.jparams.verifier.tostring.preset;

import com.google.common.base.MoreObjects;
import com.jparams.verifier.tostring.ToStringVerifier;

import org.junit.Test;

/**
 * Guava toString using {@link MoreObjects}
 */
public class GuavaTest extends AbstractDataTest
{
    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                          .add("str", str)
                          .add("list", list)
                          .add("map", map)
                          .add("ary", ary)
                          .toString();
    }

    @Test
    public void testToString()
    {
        ToStringVerifier.forClass(GuavaTest.class)
                        .withPreset(Presets.GUAVA_TO_STRING_HELPER)
                        .verify();
    }
}
