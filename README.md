# To String Verifier

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.jparams/to-string-verifier/badge.svg)](https://search.maven.org/search?q=g:com.jparams%20AND%20a:to-string-verifier) [![Build Status](https://travis-ci.org/jparams/to-string-verifier.svg?branch=master)](https://travis-ci.org/jparams/to-string-verifier) [![Javadocs](http://www.javadoc.io/badge/com.jparams/to-string-verifier.svg)](http://www.javadoc.io/doc/com.jparams/to-string-verifier)

## Getting Started

### Get To String Verifier

Maven:
```
<dependency>
    <groupId>com.jparams</groupId>
    <artifactId>to-string-verifier</artifactId>
    <version>1.x.x</version>
</dependency>
```

Gradle:
```
compile 'com.jparams:to-string-verifier:1.x.x'
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
                  .verify();
}
```

To String Verifier is incredibly configurable, so take a look at the Java Docs for more options.

## Compatibility
This library is compatible with Java 8 and above.

## License
[MIT License](http://www.opensource.org/licenses/mit-license.php)

Copyright 2018 JParams

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
