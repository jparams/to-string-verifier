package com.jparams.verifier.tostring.error;

import java.util.Collections;
import java.util.List;

public class HashCodeVerificationError implements VerificationError
{
    private final int hashCode;

    public HashCodeVerificationError(final int hashCode)
    {
        this.hashCode = hashCode;
    }

    @Override
    public List<String> getMessages()
    {
        final String message = "contain hash code: " + Integer.toHexString(hashCode);
        return Collections.singletonList(message);
    }
}
