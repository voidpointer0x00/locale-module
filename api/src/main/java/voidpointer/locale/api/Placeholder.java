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

/**
 * An immutable placeholder with its replacement.
 * Provides the API to allow <i>placeholder processors</i> to apply a
 * placeholder to a certain <i>target</i>, usually it's string.
 */
public interface Placeholder {
    /**
     *  Gets a key of this placeholder to find the place for a replacement
     * within a certain string that this placeholder gets applied to.
     *
     * @return an identifying key of this placeholder.
     */
    @Contract(pure=true)
    @NotNull String key();

    /**
     *  Gets a replacement that should replace the {@link #key()} within
     * a certain string that this placeholder gets applied to.
     *
     * @return a replacement for the key.
     */
    @Contract(pure=true)
    @NotNull String replacement();

    /**
     * Creates an immutable {@link Placeholder} instance for the given parameters.
     *
     * @param key           an identifying key of a placeholder
     * @param replacement   a replacement for the key
     * @return an immutable instance of {@link Placeholder} with the given parameters.
     */
    static Placeholder of(@NotNull final String key, @NotNull Object replacement) {
        return new ImmutablePlaceholder(key, replacement.toString());
    }
}
