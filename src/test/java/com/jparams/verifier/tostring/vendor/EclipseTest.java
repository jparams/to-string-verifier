package com.jparams.verifier.tostring.vendor;

import java.util.Arrays;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;

import org.junit.Test;

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
        ToStringVerifier.forClass(this.getClass()).withClassName(NameStyle.SIMPLE_NAME).verify();
    }

}
