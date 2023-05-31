/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.api;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Provides direct access to locale storage.
 */
public interface LocaleStorage {
    /**
     *  Tries to find a translation for a given key and gives either the
     * translation if preset or a default that the key provides.
     *
     * @return translated key, or it's default value if not present.
     * @throws IllegalStateException if the storage was not yet loaded.
     */
    @NotNull String translate(@NotNull final LocaleKey key) throws IllegalStateException;

    /** @return immutable collection of cached default keys. */
    Collection<LocaleKey> defaults();

    /**
     * Adds a default translation using the provided key.
     *
     * @param key a message identifier that will provide the path
     *            to the translation, and its default translation.
     */
    void addDefault(@NotNull final LocaleKey key);

    /**
     * Adds default translation using provided keys.
     *
     * @param keys an array of identifiers that will provide the path
     *             to translations and their default values.
     */
    void addDefaults(@NotNull final LocaleKey... keys);

    /**
     * Adds default translation using provided keys.
     *
     * @param keys a collection of identifiers that will provide the path
     *             to translations and their default values.
     */
    void addDefaults(@NotNull final Collection<LocaleKey> keys);

    /**
     * Removes any <b>default</b> translations for the provided key.
     * Does <b>not</b> affect the actual storage.
     *
     * @param key a message identifier to search for default translation
     */
    void removeDefault(@NotNull final LocaleKey key);

    /**
     * Removes any <b>default</b> translations for the provided collection
     * of keys.
     * Does <b>not</b> affect the actual storage.
     *
     * @param keys a collection of message identifiers to search for default
     *             translations
     */
    void removeDefaults(@NotNull final Collection<LocaleKey> keys);

    /**
     * Loads this storage.
     *
     * @implSpec an implementation <i>can</i> return {@code true} even
     *          if the storage did not load a translations' file (e.g. it does
     *          not exist), but the storage still initialized defaults.
     *          In that case, the storage would log any errors at their desired
     *          level.
     * @return {@code true} if the storage was initialized properly or
     *          {@code false} if it still cannot translate anything.
     */
    boolean load();

    /**
     * Saves any modifications made to the storage and updates default translations.
     *
     * @return {@code true} if the storage was successfully saved or {@code false}
     *          if any errors occurred in the process.
     */
    boolean save();
}
