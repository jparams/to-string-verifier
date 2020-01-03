# To String Verifier

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.jparams/to-string-verifier/badge.svg)](https://search.maven.org/search?q=g:com.jparams%20AND%20a:to-string-verifier) [![Build Status](https://travis-ci.org/jparams/to-string-verifier.svg?branch=master)](https://travis-ci.org/jparams/to-string-verifier) [![Javadocs](http://www.javadoc.io/badge/com.jparams/to-string-verifier.svg)](http://www.javadoc.io/doc/com.jparams/to-string-verifier)

## Getting Started

### Get To String Verifier

Maven:
```xml
<dependency>
    <groupId>com.jparams</groupId>
    <artifactId>to-string-verifier</artifactId>
    <version>1.x.x</version>
    <scope>test</scope>
</dependency>
```

Gradle:
```groovy
testCompile 'com.jparams:to-string-verifier:1.x.x'
```

### What is To String Verifier?
To String Verifier is the easiest, most convenient way to test the toString method on your class. toString methods are incredibly important for logging and debugging purposes, yet when it comes to testing, it is often forgotten or ignored. It is very easy to add a new field to your class and forget to update the toString method. Without tests in place, you won’t know something is missing until you are sitting looking through logs or debugging your application and by that time its already too late.

### Write a Test
Writing a test is easy! In most cases you want to ensure that your toString method contains the class name and all the fields. Let's look at how we would write this test!

```java
@Test
public void testToString()
{
    ToStringVerifier.forClass(User.class)
                    .withClassName(NameStyle.SIMPLE_NAME)
                    .verify();
}
```

Oh! A user object. I would not want to include the password in the toString, how do I exclude it from my test? 

```java
@Test
public void testToString()
{
    ToStringVerifier.forClass(Person.class)
                    .withClassName(NameStyle.SIMPLE_NAME)
                    .withIgnoredFields("password")
                    .withFailOnExcludedFields(true) // with this set true, if a developer accidently adds the password to the toString(), the unit test will fail
                    .verify();
}
```

To String Verifier is incredibly configurable, so take a look at the Java Docs for more options.

### Package Scan
Some times you want to test a number of classes at once, or maybe all classes in a package. Well, we have you covered!

Testing multiple classes is as easy as: `ToStringVerifier.forClasses(Person.class, Animal.class, Tree.class).verify()`

To scan an entire package:

```java
@Test
public void testToString()
{
    boolean scanRecursively = true; // when set to true, this will scan the given package and all subpackages.
    Predicate<Class<?>> predicate = (clazz) -> clazz.getName().endsWith("Model"); // optional parameter
    ToStringVerifier.forPackage("my.most.awesome.package", scanRecursively, predicate).verify();
}
```

### Presets
To String Verifier also comes with a number of Vendor presets. These presets describe how different vendors style and format their toString output. Just tell us the Vendor preset to apply and begin testing!

`ToStringVerifier.forClass(Person.class).withPreset(Presets.ECLIPSE).verify()`

Here are all the Vendor presets available out of the box:
- IntelliJ default style
- Eclipse default style
- Guava's MoreObjects toStringHelper.
- Apache's ToStringBuilder. Supported ToStringStyles include:
  * JSON_STYLE
  * NO_CLASS_NAME_STYLE
  * DEFAULT_STYLE
  * MULTI_LINE_STYLE
  * SHORT_PREFIX_STYLE

## Compatibility
This library is compatible with Java 8 and above.
