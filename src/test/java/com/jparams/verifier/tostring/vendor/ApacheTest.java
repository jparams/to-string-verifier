package com.jparams.verifier.tostring.vendor;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

public class ApacheTest extends AbstractDataTest
{
    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
            .append("str", str)
            .append("list", list)
            .append("map", map)
            .append("ary", ary)
            .toString();
    }

    @Test
    public void testToStringWithDefaultStyle()
    {
        ToStringBuilder.setDefaultStyle(ToStringStyle.DEFAULT_STYLE);
        ToStringVerifier.forClass(this.getClass()).withClassName(NameStyle.NAME).withHashCode(true).verify();
    }

    @Test
    public void testToStringWithJsonStyle()
    {
        ToStringBuilder.setDefaultStyle(ToStringStyle.JSON_STYLE);
        ToStringVerifier.forClass(this.getClass()).verify();
    }

    @Test
    public void testToStringWithMultiLineStyle()
    {
        ToStringBuilder.setDefaultStyle(ToStringStyle.MULTI_LINE_STYLE);
        ToStringVerifier.forClass(this.getClass()).withClassName(NameStyle.NAME).withHashCode(true).verify();
    }

    @Test
    public void testToStringWithShortPrefixStyle()
    {
        ToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE);
        ToStringVerifier.forClass(this.getClass()).withClassName(NameStyle.SIMPLE_NAME).verify();
    }

    @Test
    public void testToStringWithNoClassNameStyle()
    {
        ToStringBuilder.setDefaultStyle(ToStringStyle.NO_CLASS_NAME_STYLE);
        ToStringVerifier.forClass(this.getClass()).verify();
    }
}
