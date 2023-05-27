/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.api;

import org.jetbrains.annotations.NotNull;

public interface PlaceholderFactory<T> {
    @NotNull Placeholder literal(@NotNull final String placeholder, @NotNull final Object replacement);
    @NotNull Placeholder literal(@NotNull final String placeholder, @NotNull final String replacement);
    @NotNull Placeholder literal(@NotNull final String placeholder, @NotNull final Message<T> replacement);
    @NotNull Placeholder parsed(@NotNull final String placeholder, @NotNull final Object replacement);
    @NotNull Placeholder parsed(@NotNull final String placeholder, @NotNull final String replacement);
    @NotNull Placeholder parsed(@NotNull final String placeholder, @NotNull final Message<T> replacement);
}
