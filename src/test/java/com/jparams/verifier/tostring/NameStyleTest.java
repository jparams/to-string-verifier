package com.jparams.verifier.tostring;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NameStyleTest
{
    @Test
    public void testGetName()
    {
        assertThat(NameStyle.NAME.getName(String.class)).isEqualTo(String.class.getName());
        assertThat(NameStyle.SIMPLE_NAME.getName(String.class)).isEqualTo(String.class.getSimpleName());
        assertThat(NameStyle.CANONICAL_NAME.getName(String.class)).isEqualTo(String.class.getCanonicalName());
    }
}