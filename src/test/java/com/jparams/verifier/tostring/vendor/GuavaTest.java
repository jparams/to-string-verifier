package com.jparams.verifier.tostring.vendor;

import com.google.common.base.MoreObjects;
import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;

import org.junit.Test;

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
        ToStringVerifier.forClass(this.getClass()).withClassName(NameStyle.SIMPLE_NAME).verify();
    }
}
