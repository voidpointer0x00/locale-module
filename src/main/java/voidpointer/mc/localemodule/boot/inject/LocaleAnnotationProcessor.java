/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.boot.inject;

import com.google.common.reflect.ClassPath;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import voidpointer.mc.localemodule.Locale;
import voidpointer.mc.localemodule.LocaleKey;
import voidpointer.mc.localemodule.Log;
import voidpointer.mc.localemodule.PluginLocale;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@RequiredArgsConstructor(access=AccessLevel.PRIVATE)
public final class LocaleAnnotationProcessor {
    private final PluginLocaleFactory localeFactory;
    private final Object container;
    private final Class<?> containerClass;
    private final LinkedList<InjectionTarget> injectionTargets = new LinkedList<>();

    private record InjectionTarget(InjectLocale annotation, Field field) {}

    private Log currentLogger;

    public static void process(@NotNull final Object container, @NotNull final PluginLocaleFactory localeFactory) {
        var annotationProcessor = new LocaleAnnotationProcessor(localeFactory, container, container.getClass());
        annotationProcessor.findInjectionTargets();
        annotationProcessor.inject();
    }

    private void findInjectionTargets() {
        for (final Field declaredField : containerClass.getDeclaredFields())
            if (declaredField.isAnnotationPresent(InjectLocale.class))
                injectionTargets.add(new InjectionTarget(declaredField.getAnnotation(InjectLocale.class), declaredField));
    }

    private void inject() {
        for (final var injectionTarget : injectionTargets) {
            final PluginLocale locale = localeFactory.locale(injectionTarget.annotation.filenamePattern());
            currentLogger = locale.logger();
            findAndApplyDefaultKeys(injectionTarget.annotation(), locale);
            injectLocaleIntoField(locale, injectionTarget.field());
        }
    }

    private void injectLocaleIntoField(final PluginLocale locale, final Field field) {
        try {
            if (!Locale.class.isAssignableFrom(field.getType())) {
                currentLogger.warn("Could not inject {} into {}#{}: not assignable to {}.", locale.getClass().getName(),
                        containerClass.getSimpleName(), field.getName(), field.getType().getName());
                return;
            }
            field.setAccessible(true);
            field.set(container, locale);
        } catch (final Exception exception) {
            currentLogger.error("Could not inject {} into {}#{}: {}.", exception, locale.getClass().getName(),
                    containerClass.getSimpleName(), field.getName(), exception.getMessage());
        }
    }

    private void findAndApplyDefaultKeys(final InjectLocale injectLocale, final PluginLocale locale) {
        final Set<LocaleKey> localeKeys = new HashSet<>();
        // scan packages for LocaleKeys within LocaleKeyContainer classes within the packages
        for (final String keysPackageName : injectLocale.keyPackages())
            for (final Class<?> keysClass : findKeyContainers(keysPackageName))
                localeKeys.addAll(findLocaleKeysWithin(keysClass));
        // scan key container classes for LocaleKeys
        for (final Class<?> keyContainer : injectLocale.keyContainers())
            localeKeys.addAll(findLocaleKeysWithin(keyContainer));
        locale.addKeys(localeKeys);
    }

    private Collection<? extends LocaleKey> findLocaleKeysWithin(final Class<?> keysClass) {
        if (!keysClass.isAnnotationPresent(LocaleKeyContainer.class))
            return emptyList();
        final LocaleKeyContainer keyContainer = keysClass.getAnnotation(LocaleKeyContainer.class);
        final Set<LocaleKey> keys = new HashSet<>();

        // scan fields if they're LocaleKey instances and add to the keys Set
        final Predicate<Field> skipFieldPredicate = keyContainer.onlyExplicitlyIncluded()
                ? (field) -> !field.isAnnotationPresent(LocaleKeyContainer.Include.class)
                : field -> field.isAnnotationPresent(LocaleKeyContainer.Exclude.class);
        for (final Field declaredField : keysClass.getDeclaredFields()) {
            if (skipFieldPredicate.test(declaredField))
                continue;
            getLocaleKeys(declaredField).ifPresent(keys::add);
        }
        // scan methods if they provide LocaleKey instances add their value to the keys Set
        for (final Method declaredMethod : keysClass.getDeclaredMethods()) {
            if (!declaredMethod.isAnnotationPresent(ProvideLocaleKey.class))
                continue;
            keys.addAll(getLocaleKeys(declaredMethod));
        }

        return keys;
    }

    private Collection<LocaleKey> getLocaleKeys(final Method providerMethod) {
        if (LocaleKey.class.isAssignableFrom(providerMethod.getReturnType())) {
            providerMethod.setAccessible(true);
            return invokeKeyProviderMethod(providerMethod).map(obj -> List.of((LocaleKey) obj)).orElse(emptyList());
        }
        return invokeKeyCollectionProviderMethod(providerMethod);
    }

    private Collection<LocaleKey> invokeKeyCollectionProviderMethod(final Method method) {
        if (!Collection.class.isAssignableFrom(method.getReturnType())) {
            currentLogger.warn("Unsupported return type {} of {}#{}, expected LocaleKey or Collection<LocaleKey>.",
                    method.getReturnType().getSimpleName(), method.getDeclaringClass().getSimpleName(),
                    method.getName());
            return Collections.emptyList();
        }
        try {
            method.setAccessible(true);
            //noinspection unchecked
            return (Collection<LocaleKey>) method.invoke(null);
        } catch (final ClassCastException classCastException) {
            currentLogger.warn("Unsupported return type {} of {}#{}, expected LocaleKey or Collection<LocaleKey>.",
                    method.getReturnType().getSimpleName(), method.getDeclaringClass().getSimpleName(),
                    method.getName());
        } catch (final IllegalAccessException accessException) {
            currentLogger.warn("Cannot access key provider method {}#{}: {}", accessException,
                    method.getDeclaringClass().getSimpleName(), method.getName(), accessException.getMessage());
        } catch (final InvocationTargetException invokeException) {
            currentLogger.warn("Cannot invoke key provider method {}#{}: {}", invokeException,
                    method.getDeclaringClass().getSimpleName(), method.getName(), invokeException.getMessage());
        }
        return Collections.emptyList();
    }

    private Optional<Object> invokeKeyProviderMethod(final Method method) {
        try {
            method.setAccessible(true);
            return Optional.ofNullable(method.invoke(null));
        } catch (final IllegalAccessException accessException) {
            currentLogger.warn("Cannot access key provider method {}#{}: {}", accessException,
                    method.getDeclaringClass().getSimpleName(), method.getName(), accessException.getMessage());
        } catch (final InvocationTargetException invokeException) {
            currentLogger.warn("Cannot invoke key provider method {}#{}: {}", invokeException,
                    method.getDeclaringClass().getSimpleName(), method.getName(), invokeException.getMessage());
        }
        return Optional.empty();
    }

    private Optional<LocaleKey> getLocaleKeys(final Field field) {
        if (!LocaleKey.class.isAssignableFrom(field.getType())) {
            currentLogger.debug("Skipping non-LocaleKey field {}#{}", field.getDeclaringClass().getSimpleName(), field.getName());
            return Optional.empty();
        }
        try {
            return Optional.of((LocaleKey) field.get(null));
        } catch (final IllegalAccessException illegalAccessException) {
            currentLogger.warn("Unable to get LocaleKey from {}#{}: {}", illegalAccessException,
                    field.getDeclaringClass().getSimpleName(), field.getName(), illegalAccessException.getMessage());
            return Optional.empty();
        }
    }

    private Collection<Class<?>> findKeyContainers(final String keysPackageName) {
        try {
            return ClassPath.from(containerClass.getClassLoader()).getTopLevelClasses(keysPackageName)
                    .stream().map(ClassPath.ClassInfo::load).collect(Collectors.toList());
        } catch (final IOException ioException) {
            currentLogger.warn("Could not load package with key containers: {}", ioException, ioException.getMessage());
            return emptyList();
        }
    }
}
