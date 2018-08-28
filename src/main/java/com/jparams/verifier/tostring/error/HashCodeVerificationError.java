package com.jparams.verifier.tostring.error;

public class HashCodeVerificationError implements VerificationError
{
    private final int hashCode;

    public HashCodeVerificationError(final int hashCode)
    {
        this.hashCode = hashCode;
    }

    @Override
    public String getMessage()
    {
        return "contain hash code: " + Integer.toHexString(hashCode);
    }
}
