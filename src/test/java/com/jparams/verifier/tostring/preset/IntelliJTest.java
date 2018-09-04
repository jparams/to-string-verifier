package com.jparams.verifier.tostring.preset;

import java.util.Arrays;

import com.jparams.verifier.tostring.ToStringVerifier;

import org.junit.Test;

/**
 * IntelliJ to string format
 */
public class IntelliJTest extends AbstractDataTest
{
    @Override
    public String toString()
    {
        return "IntelliJTest{" +
            "str='" + str + '\'' +
            ", list=" + list +
            ", map=" + map +
            ", ary=" + Arrays.toString(ary) +
            '}';
    }

    @Test
    public void testToString()
    {
        ToStringVerifier.forClass(IntelliJTest.class)
                        .withPreset(Presets.INTELLI_J)
                        .verify();
    }
}
