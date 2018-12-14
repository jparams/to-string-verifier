package com.jparams.verifier.tostring.preset;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

abstract class AbstractDataTest
{
    String str;
    List<Integer> list;
    Map<String, BigDecimal> map;
    String[] ary;

    @Override
    public boolean equals(final Object other)
    {
        if (this == other)
        {
            return true;
        }

        if (other == null || getClass() != other.getClass())
        {
            return false;
        }

        final AbstractDataTest that = (AbstractDataTest) other;
        return Objects.equals(str, that.str) && Objects.equals(list, that.list) && Objects.equals(map, that.map) && Arrays.equals(ary, that.ary);
    }

    @Override
    public int hashCode()
    {
        int result = Objects.hash(str, list, map);
        result = 31 * result + Arrays.hashCode(ary);
        return result;
    }
}
