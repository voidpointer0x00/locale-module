/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Log {
    void info(@NotNull String message);
    void info(@NotNull String message, @Nullable Object... replacements);
    void info(@NotNull String message, @NotNull Throwable throwable, @Nullable Object... replacements);

    void warn(@NotNull String message);
    void warn(@NotNull String message, @Nullable Object... replacements);
    void warn(@NotNull String message, @NotNull Throwable throwable, @Nullable Object... replacements);

    void error(@NotNull String message);
    void error(@NotNull String message, @Nullable Object... replacements);
    void error(@NotNull String message, @NotNull Throwable throwable, @Nullable Object... replacements);

    void debug(@NotNull String message);
    void debug(@NotNull String message, @Nullable Object... replacements);
    void debug(@NotNull String message, @NotNull Throwable throwable, @Nullable Object... replacements);
    void debug(@NotNull Throwable throwable);
}
