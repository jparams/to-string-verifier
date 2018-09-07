package com.jparams.verifier.tostring.error;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jparams.verifier.tostring.error.FieldValue.ErrorType;

public class FieldValueVerificationError implements VerificationError
{
    private final List<FieldValue> fieldValues;

    public FieldValueVerificationError(final List<FieldValue> fieldValues)
    {
        this.fieldValues = fieldValues;
    }

    @Override
    public List<String> getMessages()
    {
        return Stream.of(getExpectedFieldsErrorMessage(), getUnexpectedFieldsErrorMessage())
                     .filter(Objects::nonNull)
                     .collect(Collectors.toList());
    }

    private String getExpectedFieldsErrorMessage()
    {
        final List<String> errorList = fieldValues.stream()
                                                  .filter(fieldValue -> fieldValue.getErrorType() == ErrorType.EXPECTED)
                                                  .map(fieldValue -> String.format("  - %s: %s", fieldValue.getFieldName(), fieldValue.getValue()))
                                                  .collect(Collectors.toList());

        if (errorList.isEmpty())
        {
            return null;
        }

        return "contain fields with values:\n" + String.join("\n", errorList);
    }

    private String getUnexpectedFieldsErrorMessage()
    {
        final List<String> fields = fieldValues.stream()
                                               .filter(fieldValue -> fieldValue.getErrorType() == ErrorType.UNEXPECTED)
                                               .map(FieldValue::getFieldName)
                                               .collect(Collectors.toList());

        if (fields.isEmpty())
        {
            return null;
        }

        return "not contain field" + (fields.size() == 1 ? "" : "s") + ": " + String.join(", ", fields);
    }
}
