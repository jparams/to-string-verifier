package com.jparams.verifier.tostring.error;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FieldValueVerificationErrorTest
{
    private FieldValueVerificationError subject;

    @Before
    public void setUp()
    {
        subject = new FieldValueVerificationError(Arrays.asList(new FieldValue("field1", "1"), new FieldValue("field2", "2")));
    }

    @Test
    public void testGetMessage()
    {
        assertThat(subject.getMessage()).isEqualTo("contain fields with values:\n"
                                                       + "  - field1: 1\n"
                                                       + "  - field2: 2");
    }
}