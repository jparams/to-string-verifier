package com.jparams.verifier.tostring.error;

import java.util.List;

public final class ErrorMessageGenerator
{
    private static final String MESSAGE_SPLITTER = "\n\n";
    private static final String ERROR_PREFIX = "   ";

    private ErrorMessageGenerator()
    {
    }

    /**
     * Generate error message
     *
     * @param clazz       class under test
     * @param stringValue string value of the class
     * @param errors      errors
     * @return error message
     */
    public static String generateErrorMessage(final Class<?> clazz, final String stringValue, final List<VerificationError> errors)
    {
        final StringBuilder builder = new StringBuilder("Failed verification:\n").append(clazz.getName())
                                                                                 .append("\n\n")
                                                                                 .append("Expected auto generated toString:\n")
                                                                                 .append(stringValue)
                                                                                 .append(MESSAGE_SPLITTER)
                                                                                 .append("To");

        if (errors.size() == 1)
        {
            return builder.append(" ").append(errors.get(0).getMessage()).toString();
        }

        builder.append(":\n");

        for (int i = 0; i < errors.size(); i++)
        {
            builder.append(ERROR_PREFIX)
                   .append("- ")
                   .append(errors.get(i).getMessage().replaceAll("\n", "\n" + ERROR_PREFIX));

            if (i < errors.size() - 1)
            {
                builder.append("\n");
            }
        }

        return builder.toString();
    }
}
