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

public interface Locale<T> {
    @NotNull PlaceholderFactory<T> placeholders();

    void send(@NotNull LocaleKey key, @NotNull T audience);

    void send(@NotNull LocaleKey key, @NotNull T audience, @NotNull Placeholder... placeholders);

    @NotNull Message<T> get(@NotNull LocaleKey key);

    @NotNull Message<T> get(@NotNull LocaleKey key, @NotNull Placeholder... placeholders);

    void addDefault(@NotNull final LocaleKey key);

    void addDefaults(@NotNull final LocaleKey... keys);

    void addDefaults(@NotNull final Collection<LocaleKey> keys);

    /** @return immutable copy of default keys cache. */
    @NotNull Collection<LocaleKey> defaults();

    /**
     *  Will remove the given key from default keys cache.
     */
    void removeDefault(@NotNull final LocaleKey key);

    /**
     *  Will remove the given keys from default keys cache.
     */
    void removeDefaults(@NotNull final Collection<LocaleKey> keys);
}
