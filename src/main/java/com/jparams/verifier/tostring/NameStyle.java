package com.jparams.verifier.tostring;

/**
 * Style of the class name
 */
public enum NameStyle {
    /**
     * Tests the name of the class is present as returned by {@link Class#getName()}.
     */
    NAME {
        @Override
        public String getName(Class<?> clazz) {
            return clazz.getName();
        }
    },

    /**
     * Tests the name of the class is present as returned by {@link Class#getSimpleName()}.
     */
    SIMPLE_NAME {
        @Override
        public String getName(Class<?> clazz) {
            return clazz.getSimpleName();
        }
    },

    /**
     * Tests the name of the class is present as returned by {@link Class#getCanonicalName()}.
     */
    CANONICAL_NAME {
        @Override
        public String getName(Class<?> clazz) {
            return clazz.getCanonicalName();
        }
    };

    public abstract String getName(Class<?> clazz);
}
