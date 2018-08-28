package com.jparams.verifier.tostring.error;

import java.util.Arrays;
import java.util.Collections;

import com.jparams.verifier.tostring.pojo.Person;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorMessageGeneratorTest
{
    @Test
    public void testErrorMessage()
    {
        final String errorMessage = ErrorMessageGenerator.generateErrorMessage(Person.class,
                                                                               "some string",
                                                                               Collections.singletonList(new ClassNameVerificationError(Person.class.getName())));

        assertThat(errorMessage).isEqualTo("Failed verification:\n"
                                               + "com.jparams.verifier.tostring.pojo.Person\n"
                                               + "\n"
                                               + "Expected toString:\n"
                                               + "some string\n"
                                               + "\n"
                                               + "To start with class name: com.jparams.verifier.tostring.pojo.Person");
    }


    @Test
    public void testMultipleErrorMessages()
    {
        final String errorMessage = ErrorMessageGenerator.generateErrorMessage(Person.class,
                                                                               "some string",
                                                                               Arrays.asList(new ClassNameVerificationError(Person.class.getName()),
                                                                                             new HashCodeVerificationError(1234),
                                                                                             new FieldValueVerificationError(Arrays.asList(new FieldValue("firstName", "Bob"), new FieldValue("lastName", "Smith")))));

        assertThat(errorMessage).isEqualTo("Failed verification:\n"
                                               + "com.jparams.verifier.tostring.pojo.Person\n"
                                               + "\n"
                                               + "Expected toString:\n"
                                               + "some string\n"
                                               + "\n"
                                               + "To:\n"
                                               + "   - start with class name: com.jparams.verifier.tostring.pojo.Person\n"
                                               + "   - contain hash code: 4d2\n"
                                               + "   - contain fields with values:\n"
                                               + "     - firstName: Bob\n"
                                               + "     - lastName: Smith");
    }
}