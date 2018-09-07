package com.jparams.verifier.tostring.error;

public class FieldValue
{
    private final String fieldName;
    private final String value;
    private final ErrorType errorType;

    public FieldValue(final String fieldName, final String value, final ErrorType errorType)
    {
        this.fieldName = fieldName;
        this.value = value;
        this.errorType = errorType;
    }

    public String getFieldName()
    {
        return fieldName;
    }

    public String getValue()
    {
        return value;
    }

    public ErrorType getErrorType()
    {
        return errorType;
    }

    public enum ErrorType
    {
        /**
         * Expected but not found
         */
        EXPECTED,

        /**
         * Unexpected but not found
         */
        UNEXPECTED
    }
}
