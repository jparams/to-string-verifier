package com.jparams.verifier.tostring;

import java.util.List;

import com.jparams.verifier.tostring.pojo.Identified;
import com.jparams.verifier.tostring.pojo.Person;
import com.jparams.verifier.tostring.pojo.internal.AbstractClass;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PackageScannerTest
{
    @Test
    public void testFindClasses()
    {
        final List<Class<?>> classes = PackageScanner.findClasses("com.jparams.verifier.tostring.pojo", false);
        assertThat(classes).containsExactlyInAnyOrder(Person.class, Identified.class);
    }

    @Test
    public void testFindClassesRecursively()
    {
        final List<Class<?>> classes = PackageScanner.findClasses("com.jparams.verifier.tostring.pojo", true);
        assertThat(classes).containsExactlyInAnyOrder(Person.class, Identified.class, AbstractClass.class);
    }
}