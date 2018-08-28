package com.jparams.verifier.tostring.error;

import java.util.List;
import java.util.stream.Collectors;

public class FieldValueVerificationError implements VerificationError
{
    private final List<FieldValue> fieldValues;

    public FieldValueVerificationError(final List<FieldValue> fieldValues)
    {
        this.fieldValues = fieldValues;
    }

    @Override
    public String getMessage()
    {
        final StringBuilder builder = new StringBuilder("contain fields with values:\n");

        final List<String> errorList = fieldValues.stream()
                                                  .map(fieldValue -> String.format("  - %s: %s", fieldValue.getFieldName(), fieldValue.getValue()))
                                                  .collect(Collectors.toList());

        return builder.append(String.join("\n", errorList)).toString();
    }
}
