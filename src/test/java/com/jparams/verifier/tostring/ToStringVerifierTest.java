package com.jparams.verifier.tostring;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.jparams.verifier.tostring.error.ClassNameVerificationError;
import com.jparams.verifier.tostring.error.ErrorMessageGenerator;
import com.jparams.verifier.tostring.error.FieldValue;
import com.jparams.verifier.tostring.error.FieldValue.ErrorType;
import com.jparams.verifier.tostring.error.FieldValueVerificationError;
import com.jparams.verifier.tostring.error.HashCodeVerificationError;
import com.jparams.verifier.tostring.error.VerificationError;
import com.jparams.verifier.tostring.pojo.Identified;
import com.jparams.verifier.tostring.pojo.Person;
import com.jparams.verifier.tostring.pojo.internal.AbstractClass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ToStringVerifierTest
{
    private static final Lock lock = new ReentrantLock();

    private ToStringVerifier subject;

    @Before
    public void setUp()
    {
        lock.lock(); // to force only one test to run at a time

        Person.setStringValue(null);
        subject = ToStringVerifier.forClass(Person.class).withHashCodeProvider(new ObjectHashCodeProvider());
    }

    @After
    public void tearDown()
    {
        lock.unlock();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithNullClass()
    {
        ToStringVerifier.forClass(null).verify();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithAbstractClass()
    {
        ToStringVerifier.forClass(AbstractClass.class).verify();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithInterfaceClass()
    {
        ToStringVerifier.forClass(FieldFilter.class).verify();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithEnum()
    {
        ToStringVerifier.forClass(Enum.class).verify();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPackageScanWithAbstractClass()
    {
        ToStringVerifier.forPackage("com.jparams.verifier.tostring.pojo.internal", true).verify();
    }

    @Test
    public void testFailureWithClassNameStyleName()
    {
        Person.setStringValue("Person");
        final ToStringVerifier verifier = subject.withOnlyTheseFields(Collections.emptyList()).withClassName(NameStyle.NAME);
        assertError(verifier, new ClassNameVerificationError("com.jparams.verifier.tostring.pojo.Person"));
    }

    @Test
    public void testSuccessWithClassNameStyleName()
    {
        Person.setStringValue("com.jparams.verifier.tostring.pojo.Person");

        subject.withOnlyTheseFields(Collections.emptyList()).withClassName(NameStyle.NAME).verify();
    }

    @Test
    public void testFailureWithClassNameStyleSimpleName()
    {
        Person.setStringValue("com.jparams.verifier.tostring.pojo.Person");
        final ToStringVerifier verifier = subject.withOnlyTheseFields(Collections.emptyList()).withClassName(NameStyle.SIMPLE_NAME);
        assertError(verifier, new ClassNameVerificationError("Person"));
    }

    @Test
    public void testSuccessWithClassNameStyleSimpleName()
    {
        Person.setStringValue("Person");

        subject.withOnlyTheseFields(Collections.emptyList()).withClassName(NameStyle.SIMPLE_NAME).verify();
    }


    @Test
    public void testFailureWithHashCode()
    {
        Person.setStringValue("Person@111");
        final ToStringVerifier verifier = subject.withOnlyTheseFields(Collections.emptyList()).withHashCode(true);
        assertError(verifier, new HashCodeVerificationError(123));
    }

    @Test
    public void testSuccessWithHashCode()
    {
        Person.setStringValue("Person@123");

        subject.withOnlyTheseFields(Collections.emptyList()).withHashCode(true).verify();
    }

    @Test
    public void testContainsAllFields()
    {
        subject.verify();
    }

    @Test
    public void testContainsAllFieldsFailure()
    {
        Person.setStringValue("Person{id=1, firstName='A', lastName='A'}");
        final ToStringVerifier verifier = subject.withPrefabValue(Integer.class, 1).withPrefabValue(String.class, "B");
        assertError(verifier, new FieldValueVerificationError(Arrays.asList(new FieldValue("firstName", "B", ErrorType.EXPECTED), new FieldValue("lastName", "B", ErrorType.EXPECTED))));
    }

    @Test
    public void testWithNullValue()
    {
        Person.setStringValue("Person{id=<NULL>, firstName='A', lastName='A'}");

        subject.withPrefabValue(Integer.class, null)
               .withPrefabValue(String.class, "A")
               .withNullValue("<NULL>")
               .verify();
    }

    @Test
    public void testWithNullValueFailure()
    {
        Person.setStringValue("Person{id=null, firstName='A', lastName='A'}");
        final ToStringVerifier verifier = subject.withPrefabValue(Integer.class, null).withPrefabValue(String.class, "A").withNullValue("<NULL>");
        assertError(verifier, new FieldValueVerificationError(Collections.singletonList(new FieldValue("id", "<NULL>", ErrorType.EXPECTED))));
    }

    @Test
    public void testWithFormatter()
    {
        Person.setStringValue("Person{id=int1, firstName='A', lastName='A'}");

        subject.withPrefabValue(Integer.class, 1)
               .withPrefabValue(String.class, "A")
               .withFormatter(Integer.class, item -> "int" + item)
               .verify();
    }

    @Test
    public void testWithFormatterFailure()
    {
        Person.setStringValue("Person{id=1, firstName='A', lastName='A'}");
        final ToStringVerifier verifier = subject.withPrefabValue(Integer.class, 999).withPrefabValue(String.class, "A").withFormatter(Integer.class, item -> "int" + item);
        assertError(verifier, new FieldValueVerificationError(Collections.singletonList(new FieldValue("id", "int999", ErrorType.EXPECTED))));
    }

    @Test
    public void testWithPrefabValues()
    {
        Person.setStringValue("Person{id=1, firstName='A', lastName='A'}");

        subject.withPrefabValue(Integer.class, 1)
               .withPrefabValue(String.class, "A")
               .verify();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithOnlyTheseFieldsNullList()
    {
        subject.withOnlyTheseFields((Collection<String>) null).verify();
    }

    @Test
    public void testWithOnlyTheseFields()
    {
        Person.setStringValue("Person{id=1, firstName='XXX', lastName='A'}");

        subject.withOnlyTheseFields("id")
               .withPrefabValue(Integer.class, 1)
               .withPrefabValue(String.class, "XXX")
               .verify();
    }

    @Test
    public void testWithOnlyTheseFieldsAndFailOnExcludedFields()
    {
        Person.setStringValue("Person{id=1}");

        subject.withOnlyTheseFields("id")
               .withPrefabValue(Integer.class, 1)
               .withPrefabValue(String.class, "XXX")
               .withFailOnExcludedFields(true)
               .verify();
    }

    @Test
    public void testWithIgnoredFields()
    {
        Person.setStringValue("Person{id=1, firstName='XXX', lastName='A'}");

        subject.withIgnoredFields("firstName", "lastName")
               .withPrefabValue(Integer.class, 1)
               .withPrefabValue(String.class, "XXX")
               .verify();
    }

    @Test
    public void testWithIgnoredFieldsAndFailOnExcludedFields()
    {
        Person.setStringValue("Person{id=1}");

        subject.withIgnoredFields("firstName", "lastName")
               .withPrefabValue(Integer.class, 1)
               .withPrefabValue(String.class, "XXX")
               .withFailOnExcludedFields(true)
               .verify();
    }

    @Test
    public void testWithIgnoredFieldsAndFailOnExcludedFieldsFails()
    {
        Person.setStringValue("Person{id=1, firstName='A', lastName='A'}");

        final ToStringVerifier verifier = subject.withIgnoredFields("firstName", "lastName")
                                                 .withPrefabValue(Integer.class, 1)
                                                 .withPrefabValue(String.class, "A")
                                                 .withFailOnExcludedFields(true);

        assertError(verifier, new FieldValueVerificationError(Arrays.asList(new FieldValue("firstName", null, ErrorType.UNEXPECTED),
                                                                            new FieldValue("lastName", null, ErrorType.UNEXPECTED))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithIgnoredFieldsNullList()
    {
        subject.withIgnoredFields((Collection<String>) null).verify();
    }

    @Test
    public void testWithMatchingFields()
    {
        Person.setStringValue("Person{id=1, firstName='XXX', lastName='A'}");

        subject.withMatchingFields((subject, field) -> !field.getName().matches("firstName|lastName"))
               .withPrefabValue(Integer.class, 1)
               .withPrefabValue(String.class, "XXX")
               .verify();
    }

    @Test
    public void testWithMatchingFieldsAndFailOnExcludedFields()
    {
        Person.setStringValue("Person{id=1}");

        subject.withMatchingFields((subject, field) -> !field.getName().matches("firstName|lastName"))
               .withPrefabValue(Integer.class, 1)
               .withPrefabValue(String.class, "XXX")
               .withFailOnExcludedFields(true)
               .verify();
    }

    @Test
    public void testWithMatchingFieldsAndFailOnExcludedFieldsFails()
    {
        Person.setStringValue("Person{id=1}, firstName='A', lastName='A'");

        final ToStringVerifier verifier = subject.withMatchingFields((subject, field) -> !field.getName().matches("firstName|lastName"))
                                                 .withPrefabValue(Integer.class, 1)
                                                 .withPrefabValue(String.class, "A")
                                                 .withFailOnExcludedFields(true);

        assertError(verifier, new FieldValueVerificationError(Arrays.asList(new FieldValue("firstName", null, ErrorType.UNEXPECTED),
                                                                            new FieldValue("lastName", null, ErrorType.UNEXPECTED))));
    }

    @Test
    public void testWithMatchingFieldRegex()
    {
        Person.setStringValue("Person{id=1, firstName='A', lastName='A'}");

        subject.withMatchingFields("(.*)Name")
               .withPrefabValue(Integer.class, 2)
               .withPrefabValue(String.class, "A")
               .verify();
    }

    @Test
    public void testWithMatchingFieldRegexAndFailOnExcludedFields()
    {
        Person.setStringValue("Person{firstName='A', lastName='A'}");

        subject.withMatchingFields("(.*)Name")
               .withPrefabValue(Integer.class, 1)
               .withPrefabValue(String.class, "A")
               .withFailOnExcludedFields(true)
               .verify();
    }

    @Test
    public void testWithMatchingFieldRegexAndFailOnExcludedFieldsFails()
    {
        Person.setStringValue("Person{id=1, firstName='A', lastName='A'}");

        final ToStringVerifier verifier = subject.withMatchingFields("firstName")
                                                 .withPrefabValue(Integer.class, 1)
                                                 .withPrefabValue(String.class, "A")
                                                 .withFailOnExcludedFields(true);

        assertError(verifier, new FieldValueVerificationError(Arrays.asList(new FieldValue("lastName", null, ErrorType.UNEXPECTED),
                                                                            new FieldValue("id", null, ErrorType.UNEXPECTED))));
    }

    @Test
    public void testWithoutInheritedFields()
    {
        Person.setStringValue("Person{id=1, firstName='A', lastName='A'}");

        subject.withInheritedFields(false)
               .withPrefabValue(Integer.class, 999)
               .withPrefabValue(String.class, "A")
               .verify();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWthInvalidOptions()
    {
        subject.withIgnoredFields("a").withOnlyTheseFields("a");
    }

    @Test
    public void testToString()
    {
        ToStringVerifier.forClass(Person.class)
                        .withClassName(NameStyle.SIMPLE_NAME)
                        .withIgnoredFields("password")
                        .verify();
    }

    @Test
    public void testToStringWithMultipleClasses()
    {
        Person.setStringValue("Person");

        try
        {
            ToStringVerifier.forClasses(Person.class, Identified.class)
                            .withMatchingFields((subject, field) -> false)
                            .withHashCode(true)
                            .withHashCodeProvider(new ObjectHashCodeProvider())
                            .verify();

            TestCase.fail("Exception expected");
        }
        catch (final AssertionError e)
        {
            final String message = e.getMessage();
            assertThat(message).contains(ErrorMessageGenerator.generateErrorMessage(Person.class, Person.getStringValue(), Collections.singletonList(new HashCodeVerificationError(123))));
            assertThat(message).contains(ErrorMessageGenerator.generateErrorMessage(Identified.class, "com.jparams.verifier.tostring.pojo.Identified", Collections.singletonList(new HashCodeVerificationError(1234))));
        }
    }

    @Test
    public void testToStringWithPackageScan()
    {
        Person.setStringValue("Person");

        try
        {
            ToStringVerifier.forPackage("com.jparams.verifier.tostring.pojo", false)
                            .withMatchingFields((subject, field) -> false)
                            .withHashCode(true)
                            .withHashCodeProvider(new ObjectHashCodeProvider())
                            .verify();

            TestCase.fail("Exception expected");
        }
        catch (final AssertionError e)
        {
            final String message = e.getMessage();
            assertThat(message).contains(ErrorMessageGenerator.generateErrorMessage(Person.class, Person.getStringValue(), Collections.singletonList(new HashCodeVerificationError(123))));
            assertThat(message).contains(ErrorMessageGenerator.generateErrorMessage(Identified.class, "com.jparams.verifier.tostring.pojo.Identified", Collections.singletonList(new HashCodeVerificationError(1234))));
        }
    }

    @Test
    public void testToStringWithPackageScanWithPredicate()
    {
        Person.setStringValue("Person{id=1, firstName='A', lastName='A'}");

        ToStringVerifier.forPackage("com.jparams.verifier.tostring.pojo", false, clazz -> clazz.equals(Person.class))
                        .withPrefabValue(Integer.class, 1)
                        .withPrefabValue(String.class, "A")
                        .verify();
    }

    private static void assertError(final ToStringVerifier verifier, final VerificationError... verificationErrors)
    {
        final String errorMessage = "\n\n" + ErrorMessageGenerator.generateErrorMessage(Person.class, Person.getStringValue(), Arrays.asList(verificationErrors));
        assertThatThrownBy(verifier::verify).isInstanceOf(AssertionError.class).hasMessage(errorMessage);
    }
}