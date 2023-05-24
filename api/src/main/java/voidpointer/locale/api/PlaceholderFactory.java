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
    @NotNull Placeholder literal(final String placeholder, final Object replacement);
    @NotNull Placeholder literal(final String placeholder, final String replacement);
    @NotNull Placeholder literal(final String placeholder, final Message<T> replacement);
    @NotNull Placeholder parsed(final String placeholder, final Object replacement);
    @NotNull Placeholder parsed(final String placeholder, final String replacement);
    @NotNull Placeholder parsed(final String placeholder, final Message<T> replacement);
}
