package com.jparams.verifier.tostring;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Scans package and identifies classes
 */
final class PackageScanner
{
    PackageScanner()
    {
    }

    /**
     * Scan for all classes in the given package
     *
     * @param packageName package to scan
     * @param recursively true to recursively scan all sub packages
     * @return classes
     */
    public static List<Class<?>> findClasses(final String packageName, final boolean recursively)
    {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final String path = packageName.replace('.', '/');
        final Enumeration<URL> resources;

        try
        {
            resources = classLoader.getResources(path);
        }
        catch (final IOException e)
        {
            throw new PackageScanException(e);
        }

        final List<File> rootDirectories = new ArrayList<>();

        while (resources.hasMoreElements())
        {
            final File directory = new File(resources.nextElement().getFile());
            rootDirectories.add(directory);
        }

        return rootDirectories.stream()
                              .map(rootDirectory -> findClasses(rootDirectory, packageName, recursively))
                              .flatMap(List::stream)
                              .collect(Collectors.toList());
    }

    private static List<Class<?>> findClasses(final File rootDirectory, final String packageName, final boolean recursively)
    {
        final File[] files;

        if (!rootDirectory.exists() || (files = rootDirectory.listFiles()) == null)
        {
            return Collections.emptyList();
        }

        final List<Class<?>> classes = new ArrayList<>();

        for (final File file : files)
        {
            if (file.isDirectory() && recursively)
            {
                final String subPackageName = packageName + "." + file.getName();
                classes.addAll(findClasses(file, subPackageName, true));
            }
            else if (file.getName().endsWith(".class"))
            {
                final String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);

                try
                {
                    final Class<?> clazz = Class.forName(className);
                    classes.add(clazz);
                }
                catch (final ClassNotFoundException e)
                {
                    throw new PackageScanException(e);
                }
            }
        }

        return classes;
    }

    private static class PackageScanException extends RuntimeException
    {
        PackageScanException(final Throwable cause)
        {
            super(cause);
        }
    }
}
