package com.jparams.verifier.tostring.error;

import java.util.Collections;
import java.util.List;

public class ClassNameVerificationError implements VerificationError
{
    private final String className;

    public ClassNameVerificationError(final String className)
    {
        this.className = className;
    }

    @Override
    public List<String> getMessages()
    {
        final String message = "start with class name: " + className;
        return Collections.singletonList(message);
    }
}
