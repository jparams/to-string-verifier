package com.jparams.tester;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * {@link ToStringTester} can be used in unit tests to verify that the {@link Object#toString()} returns
 * the desired results.
 *
 * <p>
 * To get started, use {@code ToStringTester} as follows:
 * <p>
 * {@code ToStringTester.forClass(MyClass.class).verify();}
 * <p>
 *
 * @param <T> class under test
 */
public final class ToStringTester<T>
{
    private static final Predicate<Field> MATCH_ALL_PREDICATE = (item) -> true;

    private final SubjectBuilder<T> subjectBuilder;
    private final Class<T> clazz;
    private NameStyle nameStyle = null;
    private Predicate<Field> fieldPredicate = null;
    private boolean inheritedFields = true;
    private boolean hashCode = false;
    private String fieldValuePattern = "%s(.{0,4}?)%s";

    private ToStringTester(final Class<T> clazz)
    {
        this.subjectBuilder = new SubjectBuilder<>(clazz);
        this.clazz = clazz;
    }

    /**
     * Create a tester for the given class
     *
     * @param clazz class to test
     * @param <T>   class type
     * @return tester
     */
    public static <T> ToStringTester<T> forClass(final Class<T> clazz)
    {
        return new ToStringTester<>(clazz);
    }

    /**
     * Specify a alternate field value pattern where the first <code>%s</code> will be replaced by the the field name and the second <code>%s</code>
     * will be replaced by the expected value.<br><br>
     *
     * If an alternate pattern is not provided, the following default pattern will be used:<br><br>
     *
     * Default pattern: %s(.{0,4}?)%s<br><br>
     *
     * Here is an example what this pattern will look like once it is compiled:<br>
     * - Field Name: firstName<br>
     * - Expected Value: John<br>
     * - Compiled Pattern: firstName(.{0,4}?)John<br><br>
     *
     * This pattern will successfully match the following field value pair variations:<br>
     * - firstName=John<br>
     * - firstName = John<br>
     * - firstName='John'<br>
     * - firstName = 'John'<br>
     *
     * @param fieldValuePattern field value pattern
     * @return this
     */
    public ToStringTester<T> withFieldValuePattern(final String fieldValuePattern)
    {
        if (fieldValuePattern == null)
        {
            throw new IllegalArgumentException("Null field value pattern supplied");
        }

        if (StringUtils.contains(fieldValuePattern, "%s") != 2)
        {
            throw new IllegalArgumentException("Invalid field value pattern. Expected pattern to contain two instances of: %s");
        }

        this.fieldValuePattern = fieldValuePattern;
        return this;
    }

    /**
     * If specified, this tester will assert that the {@link Object#toString()} output contains the name of the subject class in
     * the {@link NameStyle} specified.
     *
     * @param nameStyle style of the class name
     * @return this
     */
    public ToStringTester<T> withClassName(final NameStyle nameStyle)
    {
        this.nameStyle = nameStyle;
        return this;
    }

    /**
     * If set to true, this tester will assert that the {@link Object#toString()} output contains the hash code as returned
     * by {@link Object#hashCode()}.
     *
     * @param hashCode check for inclusion of the hash code
     * @return this
     */
    public ToStringTester<T> withHashCode(final boolean hashCode)
    {
        this.hashCode = hashCode;
        return this;
    }

    /**
     * This is enabled by default. Set this to false to ignore fields inherited from the parent class.
     *
     * @param inheritedFields true to test for fields inherited from the parent class
     * @return this
     */
    public ToStringTester<T> withInheritedFields(final boolean inheritedFields)
    {
        this.inheritedFields = inheritedFields;
        return this;
    }

    /**
     * If specified, this tester will only assert that the given fields are present in the {@link Object#toString()} output. To
     * assert all fields are present, do not call this method. To test for all fields excluding some, call {@link #withIgnoredFields(Collection)}.
     *
     * @param fields field names to include in test
     * @return this
     */
    public ToStringTester<T> withOnlyTheseFields(final String... fields)
    {
        final Set<String> fieldNamesSet = new HashSet<>(Arrays.asList(fields));
        return withOnlyTheseFields(fieldNamesSet);
    }

    /**
     * If specified, this tester will only assert that the given fields are present in the (@link {@link Object#toString()} output. To
     * assert all fields are present, do not call this method. To test for all fields excluding some, call {@link #withIgnoredFields(Collection)}.
     *
     * @param fields field names to include in test
     * @return this
     */
    public ToStringTester<T> withOnlyTheseFields(final Collection<String> fields)
    {
        if (fields == null)
        {
            throw new IllegalArgumentException("Null list of fields supplied");
        }

        this.checkFieldPredicate();
        this.fieldPredicate = (field) -> fields.contains(field.getName());
        return this;
    }

    /**
     * If specified, this tester will assert that all but the excluded fields are present in the (@link {@link Object#toString()} output. To
     * assert only certain fields are present, do not call this method, instead use {@link #withOnlyTheseFields(Collection)}.
     *
     * @param fields field names to exclude in test
     * @return this
     */
    public ToStringTester<T> withIgnoredFields(final String... fields)
    {
        return withIgnoredFields(new HashSet<>(Arrays.asList(fields)));
    }

    /**
     * If specified, this tester will assert that all but the excluded fields are present in the (@link {@link Object#toString()} output. To
     * assert only certain fields are present, do not call this method, instead use {@link #withOnlyTheseFields(Collection)}.
     *
     * @param fields field names to exclude in test
     * @return this
     */
    public ToStringTester<T> withIgnoredFields(final Collection<String> fields)
    {
        if (fields == null)
        {
            throw new IllegalArgumentException("Null list of fields supplied");
        }

        this.checkFieldPredicate();
        this.fieldPredicate = (field) -> !fields.contains(field.getName());
        return this;
    }

    /**
     * If specified, this tester will only assert that field names matching this regex pattern are present in the (@link {@link Object#toString()} output.
     *
     * @param regex field name match regex
     * @return this
     */
    public ToStringTester<T> withMatchingFields(final String regex)
    {
        this.checkFieldPredicate();
        this.fieldPredicate = (field) -> field.getName().matches(regex);
        return this;
    }

    /**
     * Adds prefabricated values for instance fields of classes that ToStringTester cannot instantiate by itself.
     *
     * @param type        The class of the prefabricated values
     * @param prefabValue An instance of {@code S}.
     * @param <S>         The class of the prefabricated values.
     * @return this
     */
    public <S> ToStringTester<T> withPrefabValue(final Class<S> type, final S prefabValue)
    {
        this.subjectBuilder.setPrefabValue(type, prefabValue);
        return this;
    }

    /**
     * Perform verification
     *
     * @throws AssertionError if assertion conditions are not met
     */
    public void verify()
    {
        if (clazz == null)
        {
            throw new IllegalArgumentException("Null class to test");
        }

        final T subject = subjectBuilder.build();
        final String toString = subject.toString();

        if (toString == null)
        {
            throw new AssertionError("toString method returned a null string");
        }

        if (nameStyle != null)
        {
            verifyClassName(toString, nameStyle.getName(clazz));
        }

        if (hashCode)
        {
            verifyHashCode(toString, subject.hashCode());
        }

        FieldsProvider.provide(clazz, inheritedFields)
                      .stream()
                      .filter(fieldPredicate == null ? MATCH_ALL_PREDICATE : fieldPredicate)
                      .forEach(this::verifyField);
    }

    private void verifyClassName(final String string, final String className)
    {
        if (!string.startsWith(className))
        {
            assertFailed(string, "start with class name", className);
        }
    }

    private void verifyHashCode(final String string, final int hashCode)
    {
        final String stringHashCode = String.valueOf(hashCode);

        if (!string.contains(stringHashCode))
        {
            assertFailed(string, "contain hash code", hashCode);
        }
    }

    private void verifyField(final Field field)
    {
        try
        {
            final T subject = subjectBuilder.build();
            final String fieldValue = String.valueOf(field.get(subject));
            final String stringValue = subject.toString();

            final String regex = String.format("(.*)" + fieldValuePattern + "(.*)", Pattern.quote(field.getName()), Pattern.quote(fieldValue));
            final Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);

            if (!pattern.matcher(stringValue).matches())
            {
                assertFailed(stringValue, "contain field", field.getName() + "\n\nwith value:\n" + fieldValue);
            }
        }
        catch (final IllegalAccessException e)
        {
            throw new InternalErrorException("Failed to access internals of test subject", e);
        }
    }

    private void assertFailed(final String string, final String field, final Object expectedValue)
    {
        final String message = String.format("\n\nExpected toString:\n%s\n\nto %s:\n%s", string, field, expectedValue);
        throw new AssertionError(message);
    }

    private void checkFieldPredicate()
    {
        if (fieldPredicate != null)
        {
            throw new IllegalArgumentException("You can call either withOnlyTheseFields, withIgnoredFields or withMatchingFields, but not a combination of the three.");
        }
    }
}
