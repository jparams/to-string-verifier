package com.jparams.verifier.tostring;

import com.jparams.verifier.tostring.pojo.Person;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectHashCodeProviderTest
{
    @Test
    public void testProvider()
    {
        final Person person = new Person(1, "1", "1");
        final int hashCode = new ObjectHashCodeProvider().provide(person);
        assertThat(hashCode).isEqualTo(person.hashCode());
    }
}