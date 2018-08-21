package com.jparams.tester;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.jparams.tester.pojo.Person;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ToStringTesterTest
{
    private static final Lock lock = new ReentrantLock();

    private ToStringTester<Person> subject;

    @Before
    public void setUp()
    {
        lock.lock(); // to force only one test to run at a time

        Person.setStringValue(null);
        subject = ToStringTester.forClass(Person.class);
    }

    @After
    public void tearDown()
    {
        lock.unlock();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithNullClass()
    {
        ToStringTester.forClass(null).verify();
    }

    @Test
    public void testFailureWithClassNameStyleName()
    {
        Person.setStringValue("Person");

        assertThatThrownBy(() -> subject.withOnlyTheseFields(Collections.emptyList()).withClassName(NameStyle.NAME).verify())
            .isInstanceOf(AssertionError.class)
            .hasMessage("\n\nExpected toString:\nPerson\n\nto start with class name:\ncom.jparams.tester.pojo.Person");
    }

    @Test
    public void testSuccessWithClassNameStyleName()
    {
        Person.setStringValue("com.jparams.tester.pojo.Person");

        subject.withOnlyTheseFields(Collections.emptyList()).withClassName(NameStyle.NAME).verify();
    }

    @Test
    public void testFailureWithClassNameStyleSimpleName()
    {
        Person.setStringValue("com.jparams.tester.pojo.Person");

        assertThatThrownBy(() -> subject.withOnlyTheseFields(Collections.emptyList()).withClassName(NameStyle.SIMPLE_NAME).verify())
            .isInstanceOf(AssertionError.class)
            .hasMessage("\n\nExpected toString:\ncom.jparams.tester.pojo.Person\n\nto start with class name:\nPerson");
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

        assertThatThrownBy(() -> subject.withOnlyTheseFields(Collections.emptyList()).withHashCode(true).verify())
            .isInstanceOf(AssertionError.class)
            .hasMessage("\n\nExpected toString:\nPerson@111\n\nto contain hash code:\n123");
    }

    @Test
    public void testSuccessWithHashCode()
    {
        Person.setStringValue("Person@123");

        subject.withOnlyTheseFields(Collections.emptyList()).withHashCode(true).verify();
    }

    @Test
    public void testWithFieldValuePatter()
    {
        subject.withFieldValuePattern("%s=(.{0,1}?)%s").verify();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithInvalidFieldValuePattern()
    {
        subject.withFieldValuePattern("%s=").verify();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithNullFieldValuePattern()
    {
        subject.withFieldValuePattern(null).verify();
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

        assertThatThrownBy(() -> subject.withPrefabValue(Integer.class, 1).withPrefabValue(String.class, "B").verify())
            .isInstanceOf(AssertionError.class)
            .hasMessage("\n\nExpected toString:\nPerson{id=1, firstName='A', lastName='A'}\n\nto contain field:\nfirstName\n\nwith value:\nB");
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
        Person.setStringValue("Person{id=1, firstName='A', lastName='A'}");

        subject.withOnlyTheseFields("id")
               .withPrefabValue(Integer.class, 1)
               .withPrefabValue(String.class, "XXX")
               .verify();
    }

    @Test
    public void testWithIgnoredFields()
    {
        Person.setStringValue("Person{id=1, firstName='A', lastName='A'}");

        subject.withIgnoredFields("firstName", "lastName")
               .withPrefabValue(Integer.class, 1)
               .withPrefabValue(String.class, "XXX")
               .verify();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithIgnoredFieldsNullList()
    {
        subject.withIgnoredFields((Collection<String>) null).verify();
    }

    @Test
    public void testWithMatchingFields()
    {
        Person.setStringValue("Person{id=1, firstName='A', lastName='A'}");

        subject.withMatchingFields("(.*)Name")
               .withPrefabValue(Integer.class, 2)
               .withPrefabValue(String.class, "A")
               .verify();
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

    /**
     * testToString
     */
    @Test
    public void testToString()
    {
        ToStringTester.forClass(Person.class)
                      .withClassName(NameStyle.SIMPLE_NAME)
                      .withIgnoredFields("password")
                      .verify();
    }
}