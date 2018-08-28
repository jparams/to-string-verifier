package com.jparams.verifier.tostring.error;

public class FieldValue
{
    private final String fieldName;
    private final String value;

    public FieldValue(final String fieldName, final String value)
    {
        this.fieldName = fieldName;
        this.value = value;
    }

    public String getFieldName()
    {
        return fieldName;
    }

    public String getValue()
    {
        return value;
    }
}
