package com.jparams.verifier.tostring.preset;

import java.util.Arrays;

import com.jparams.verifier.tostring.ToStringVerifier;

import org.junit.Test;

/**
 * Eclipse toString format
 */
public class EclipseTest extends AbstractDataTest
{
    @Override
    public String toString()
    {
        return "EclipseTest [str=" + str + ", list=" + list + ", map=" + map + ", ary=" + Arrays.toString(ary) + "]";
    }

    @Test
    public void testToString()
    {
        ToStringVerifier.forClass(EclipseTest.class)
                        .withPreset(Presets.ECLIPSE)
                        .verify();
    }
}
