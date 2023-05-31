/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.api;

import org.jetbrains.annotations.NotNull;

/**
 *  Essentially a message identifier. Provides a {@link #path()} as a key
 * to identify a certain requested message within a locale.
 */
public interface LocaleKey {
    /**
     * @return a universal path to translation of a message associated with this key.
     */
    @NotNull String path();

    /**
     * @return the default translation of a message associated with this key if a {@link Locale}
     *          does not provide any.
     */
    @NotNull String defaultTranslation();

    static @NotNull String fromScreamingSnakeToKebab(@NotNull final String str) {
        return str.toLowerCase().replace('_', '-');
    }

    static @NotNull LocaleKey of(@NotNull final String path, @NotNull final String defaultValue) {
        return new ImmutableLocaleKey(path, defaultValue);
    }
}
