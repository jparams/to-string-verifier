package com.jparams.verifier.tostring;

/**
 * Thrown on internal error
 */
class InternalErrorException extends RuntimeException
{
    InternalErrorException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
