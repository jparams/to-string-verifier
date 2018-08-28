package com.jparams.verifier.tostring.error;

public class ClassNameVerificationError implements VerificationError
{
    private final String className;

    public ClassNameVerificationError(final String className)
    {
        this.className = className;
    }

    @Override
    public String getMessage()
    {
        return "start with class name: " + className;
    }
}
