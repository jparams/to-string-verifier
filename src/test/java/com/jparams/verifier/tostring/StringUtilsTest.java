package com.jparams.verifier.tostring;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilsTest
{
    @Test
    public void testContains()
    {
        final int count = StringUtils.contains("aa bb a a aabbAA", "aa");
        assertThat(count).isEqualTo(2);
    }
}