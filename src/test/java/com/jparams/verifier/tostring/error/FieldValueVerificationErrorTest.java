package com.jparams.verifier.tostring.error;

import java.util.Arrays;

import com.jparams.verifier.tostring.error.FieldValue.ErrorType;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FieldValueVerificationErrorTest
{
    @Test
    public void testGetMessageWithExpectedFields()
    {
        final FieldValueVerificationError subject = new FieldValueVerificationError(Arrays.asList(new FieldValue("field1", "1", ErrorType.EXPECTED),
                                                                                                  new FieldValue("field2", "2", ErrorType.EXPECTED)));

        assertThat(subject.getMessages()).containsExactly("contain fields with values:\n"
                                                              + "  - field1: 1\n"
                                                              + "  - field2: 2");
    }

    @Test
    public void testGetMessageWithUnexpectedFields()
    {
        final FieldValueVerificationError subject = new FieldValueVerificationError(Arrays.asList(new FieldValue("field1", "1", ErrorType.UNEXPECTED),
                                                                                                  new FieldValue("field2", "2", ErrorType.UNEXPECTED)));

        assertThat(subject.getMessages()).containsExactly("not contain fields: field1, field2");
    }

    @Test
    public void testGetMessageWithExpectedAndUnexpectedFields()
    {
        final FieldValueVerificationError subject = new FieldValueVerificationError(Arrays.asList(new FieldValue("field1", "1", ErrorType.EXPECTED),
                                                                                                  new FieldValue("field2", "2", ErrorType.UNEXPECTED)));

        assertThat(subject.getMessages()).containsExactly("contain fields with values:\n  - field1: 1",
                                                          "not contain field: field2");
    }
}