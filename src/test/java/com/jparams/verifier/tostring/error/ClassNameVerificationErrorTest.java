package com.jparams.verifier.tostring.error;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClassNameVerificationErrorTest
{
    private ClassNameVerificationError subject;

    @Before
    public void setUp()
    {
        subject = new ClassNameVerificationError("ClassName1");
    }

    @Test
    public void testGetMessage()
    {
        assertThat(subject.getMessage()).isEqualTo("start with class name: ClassName1");
    }
}