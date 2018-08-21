package com.jparams.tester;

/**
 * Thrown on internal error
 */
public class InternalErrorException extends RuntimeException
{
    InternalErrorException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
