package com.jparams.verifier.tostring.preset;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;

/**
 * Preset for org.apache.commons.lang3.builder.ToStringBuilder
 */
public class ApacheToStringBuilderPreset implements Preset
{
    private final Style style;

    public ApacheToStringBuilderPreset(final Style style)
    {
        this.style = style;
    }

    @Override
    public void apply(final ToStringVerifier verifier)
    {
        switch (style)
        {
            case JSON_STYLE:
            case NO_CLASS_NAME_STYLE:
                verifier.withClassName(null).withHashCode(false);
                break;
            case DEFAULT_STYLE:
            case MULTI_LINE_STYLE:
                verifier.withClassName(NameStyle.NAME).withHashCode(true);
                break;
            case SHORT_PREFIX_STYLE:
                verifier.withClassName(NameStyle.SIMPLE_NAME).withHashCode(false);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported style " + style);
        }
    }

    /**
     * See org.apache.commons.lang3.builder.ToStringStyle
     */
    public enum Style
    {
        DEFAULT_STYLE,
        JSON_STYLE,
        MULTI_LINE_STYLE,
        SHORT_PREFIX_STYLE,
        NO_CLASS_NAME_STYLE
    }
}
