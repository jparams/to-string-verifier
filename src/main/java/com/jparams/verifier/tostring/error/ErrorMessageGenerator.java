package com.jparams.verifier.tostring.error;

import java.util.List;
import java.util.stream.Collectors;

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

        final List<String> messages = errors.stream()
                                            .map(VerificationError::getMessages)
                                            .flatMap(List::stream)
                                            .collect(Collectors.toList());

        if (messages.size() == 1)
        {
            return builder.append(" ").append(messages.get(0)).toString();
        }

        builder.append(":\n");

        for (int i = 0; i < messages.size(); i++)
        {
            builder.append(ERROR_PREFIX)
                   .append("- ")
                   .append(messages.get(i).replaceAll("\n", "\n" + ERROR_PREFIX));

            if (i < messages.size() - 1)
            {
                builder.append("\n");
            }
        }

        return builder.toString();
    }
}
