package com.jparams.verifier.tostring.pojo;

public class Identified
{
    private final Integer id;

    public Identified(final Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    @Override
    public int hashCode()
    {
        return 1234;
    }

    @Override
    public String toString()
    {
        return this.getClass().getName();
    }
}
