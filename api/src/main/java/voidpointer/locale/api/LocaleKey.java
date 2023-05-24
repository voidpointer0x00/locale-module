/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.api;

import org.jetbrains.annotations.NotNull;

public interface LocaleKey {
    @NotNull String path();

    @NotNull String defaultValue();

    static @NotNull String fromScreamingSnakeToKebab(@NotNull final String str) {
        return str.toLowerCase().replace('_', '-');
    }

    static @NotNull LocaleKey of(@NotNull final String path, @NotNull final String defaultValue) {
        return new ImmutableLocaleKey(path, defaultValue);
    }
}
