/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.api;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Placeholder {
    @Contract(pure=true)
    @NotNull String key();

    @Contract(pure=true)
    @Nullable String replacement();

    static Placeholder of(@NotNull final String key, @Nullable Object replacement) {
        return new ImmutablePlaceholder(key, replacement != null ? replacement.toString() : "");
    }
}
