package com.jparams.tester;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides all fields for a given class
 */
public final class FieldsProvider
{
    private FieldsProvider()
    {
    }

    /**
     * Provide all fields for the given class.
     *
     * @param clazz           class to scan
     * @param inheritedFields include/ exclude inherited fields
     * @return fields
     */
    public static List<Field> provide(final Class<?> clazz, final boolean inheritedFields)
    {
        final List<Field> fields = new ArrayList<>();

        Class<?> currentClass = clazz;

        while (currentClass != null)
        {
            for (final Field field : currentClass.getDeclaredFields())
            {
                if (!Modifier.isStatic(field.getModifiers()))
                {
                    field.setAccessible(true);
                    fields.add(field);
                }
            }

            currentClass = inheritedFields ? currentClass.getSuperclass() : null;
        }

        return fields;
    }
}
