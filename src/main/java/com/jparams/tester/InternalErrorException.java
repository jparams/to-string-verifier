package com.jparams.tester;

/**
 * Thrown on internal error
 */
public class InternalErrorException extends RuntimeException
{
    public InternalErrorException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
