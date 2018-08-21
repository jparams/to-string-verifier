package com.jparams.tester;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String utility methods
 */
final class StringUtils
{
    private StringUtils()
    {
    }

    /**
     * Returns the count of times the string contained the required value
     *
     * @param str      string
     * @param contains item to lookup
     * @return count
     */
    static int contains(final String str, final String contains)
    {
        final Pattern pattern = Pattern.compile(Pattern.quote(contains));
        final Matcher matcher = pattern.matcher(str);

        int count = 0;

        while (matcher.find())
        {
            count++;
        }

        return count;
    }
}
