package com.jparams.tester;

import com.jparams.object.builder.Build;
import com.jparams.object.builder.Configuration;
import com.jparams.object.builder.Context;
import com.jparams.object.builder.ObjectBuilder;
import com.jparams.object.builder.provider.ObjectProvider.InjectionStrategy;
import com.jparams.object.builder.provider.Provider;

/**
 * Builds the subject of the test
 *
 * @param <T> subject type
 */
class SubjectBuilder<T>
{
    private final Class<T> clazz;
    private final Configuration configuration;

    SubjectBuilder(final Class<T> clazz)
    {
        this.clazz = clazz;
        this.configuration = new Configuration().withDefaultProviders()
                                                .withMaxDepth(2)
                                                .withFailOnWarning(false)
                                                .withFailOnError(false)
                                                .withCaching(false);
    }

    /**
     * Set a prefabricated value
     *
     * @param type        The class of the prefabricated values
     * @param prefabValue An instance of {@code S}.
     * @param <S>         The class of the prefabricated values.
     */
    <S> void setPrefabValue(final Class<S> type, final S prefabValue)
    {
        configuration.withProvider(new Provider()
        {
            @Override
            public boolean supports(final Class<?> clazz)
            {
                return clazz.equals(type);
            }

            @Override
            public Object provide(final Context context)
            {
                return prefabValue;
            }
        });
    }

    /**
     * Build test subject
     *
     * @return test subject
     */
    T build()
    {
        // attempt to build with field injection to bypass any validation that may exist in a constructor
        final Build<T> builtWithFieldInjection = buildWithFieldInjection();

        if (builtWithFieldInjection.get() != null)
        {
            return builtWithFieldInjection.get();
        }

        // if unable to build, attempt to build with constructor injection
        final Build<T> builtWithConstructorInjection = buildWithConstructorInjection();

        if (builtWithConstructorInjection.get() != null)
        {
            return builtWithConstructorInjection.get();
        }

        // if unable to build with either injection method, throw assertion error
        throw new AssertionError("Failed to create instance of " + clazz + ". Failed with error: " + builtWithConstructorInjection.getErrors());
    }

    private Build<T> buildWithFieldInjection()
    {
        return ObjectBuilder.withConfiguration(configuration.withDefaultProviders(InjectionStrategy.FIELD_INJECTION))
                            .buildInstanceOf(clazz);
    }

    private Build<T> buildWithConstructorInjection()
    {
        return ObjectBuilder.withConfiguration(configuration.withDefaultProviders(InjectionStrategy.CONSTRUCTOR_INJECTION))
                            .buildInstanceOf(clazz);
    }
}
