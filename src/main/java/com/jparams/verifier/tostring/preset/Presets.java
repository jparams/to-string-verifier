package com.jparams.verifier.tostring.preset;

import com.jparams.verifier.tostring.preset.ApacheToStringBuilderPreset.Style;

/**
 * Presets
 */
public final class Presets
{
    private Presets()
    {
    }

    /**
     * Pre-set for testing toString generated using Apache's ToStringBuilder using the ToStringStyle.JSON_STYLE.
     * This pre-set expects a toString in the format:
     *
     * <pre>
     * {"firstName": "John", "lastName": "Smith"}
     * </pre>
     */
    public static Preset APACHE_TO_STRING_BUILDER_JSON_STYLE = new ApacheToStringBuilderPreset(Style.JSON_STYLE);

    /**
     * Pre-set for testing toString generated using Apache's ToStringBuilder using the ToStringStyle.NO_CLASS_NAME_STYLE.
     * This pre-set expects a toString in the format:
     *
     * <pre>
     * [firstName=John,lastName=Smith]
     * </pre>
     */
    public static Preset APACHE_TO_STRING_BUILDER_NO_CLASS_NAME_STYLE = new ApacheToStringBuilderPreset(Style.NO_CLASS_NAME_STYLE);

    /**
     * Pre-set for testing toString generated using Apache's ToStringBuilder using the ToStringStyle.DEFAULT_STYLE.
     * This pre-set expects a toString in the format:
     *
     * <pre>
     * com.package.Person@7e0babb1[firstName=John,lastName=Smith]
     * </pre>
     */
    public static Preset APACHE_TO_STRING_BUILDER_DEFAULT_STYLE = new ApacheToStringBuilderPreset(Style.DEFAULT_STYLE);

    /**
     * Pre-set for testing toString generated using Apache's ToStringBuilder using the ToStringStyle.MULTI_LINE_STYLE.
     * This pre-set expects a toString in the format:
     *
     * <pre>
     * com.package.Person@7e0babb1[
     *    firstName=John
     *    lastName=Smith
     * ]
     * </pre>
     */
    public static Preset APACHE_TO_STRING_BUILDER_MULTI_LINE_STYLE = new ApacheToStringBuilderPreset(Style.MULTI_LINE_STYLE);

    /**
     * Pre-set for testing toString generated using Apache's ToStringBuilder using the ToStringStyle.SHORT_PREFIX_STYLE.
     * This pre-set expects a toString in the format:
     *
     * <pre>
     * Person[firstName=John,lastName=Smith]
     * </pre>
     */
    public static Preset APACHE_TO_STRING_BUILDER_SHORT_PREFIX_STYLE = new ApacheToStringBuilderPreset(Style.SHORT_PREFIX_STYLE);

    /**
     * Pre-set for testing toString generated using the Eclipse IDE. This pre-set expects a toString in the format:
     *
     * <pre>
     * Person [firstName=John, lastName=Smith]
     * </pre>
     */
    public static Preset ECLIPSE = new EclipsePreset();

    /**
     * Pre-set for testing toString generated using the IntelliJ IDE. This pre-set expects a toString in the format:
     *
     * <pre>
     * Person{firstName='John', lastName='Smith'}
     * </pre>
     */
    public static Preset INTELLI_J = new IntelliJPreset();

    /**
     * Pre-set for testing toString generated using Guava's MoreObjects.toStringHelper(...). This pre-set expects a toString in the format:
     *
     * <pre>
     * Person{firstName=John, lastName=Smith}
     * </pre>
     */
    public static Preset GUAVA_TO_STRING_HELPER = new GuavaPreset();
}
