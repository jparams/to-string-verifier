package com.jparams.verifier.tostring.error;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HashCodeVerificationErrorTest
{
    private HashCodeVerificationError subject;

    @Before
    public void setUp()
    {
        subject = new HashCodeVerificationError(1122);
    }

    @Test
    public void testGetMessage()
    {
        assertThat(subject.getMessage()).isEqualTo("contain hash code: 462");
    }
}