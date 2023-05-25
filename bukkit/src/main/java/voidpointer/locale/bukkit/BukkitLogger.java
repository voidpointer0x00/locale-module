/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.bukkit;

import lombok.AllArgsConstructor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import voidpointer.locale.api.Log;

import java.util.function.Supplier;
import java.util.logging.Logger;

@AllArgsConstructor
public class BukkitLogger implements Log {
    public static final String IS_DEBUG_PATH = "locale.debug";
    private final Logger log;
    private final Supplier<Boolean> isDebug;

    public BukkitLogger(final Plugin plugin) {
        this.log = plugin.getLogger();
        this.isDebug = () -> plugin.getConfig().getBoolean(IS_DEBUG_PATH, false);
    }

    @Override public void info(@NotNull final String message) {
        log.info(message);
    }
    @Override public void info(@NotNull final String message, final @Nullable Object... replacements) {
        log.info(insertPlaceholderLikeSlf4j(message, replacements));
    }
    @Override public void info(@NotNull final String message, @NotNull final Throwable throwable, final @Nullable Object... replacements) {
        info(message, replacements);
        throwable.printStackTrace();
    }

    @Override public void warn(@NotNull final String message) {
        log.warning(message);
    }
    @Override public void warn(@NotNull final String message, final @Nullable Object... replacements) {
        log.warning(insertPlaceholderLikeSlf4j(message, replacements));
    }
    @Override public void warn(@NotNull final String message, @NotNull final Throwable throwable, final @Nullable Object... replacements) {
        warn(message, replacements);
        throwable.printStackTrace();
    }

    @Override public void error(@NotNull final String message) {
        log.severe(message);
    }
    @Override public void error(@NotNull final String message, final @Nullable Object... replacements) {
        log.severe(insertPlaceholderLikeSlf4j(message, replacements));
    }
    @Override public void error(@NotNull final String message, @NotNull final Throwable throwable, final @Nullable Object... replacements) {
        error(message, replacements);
        throwable.printStackTrace();
    }

    @Override public void debug(@NotNull final String message) {
        if (isDebug.get())
            info(message);
    }
    @Override public void debug(@NotNull final String message, final @Nullable Object... replacements) {
        if (isDebug.get())
            info(message, replacements);
    }
    @Override public void debug(@NotNull final String message, @NotNull final Throwable throwable, final @Nullable Object... replacements) {
        if (isDebug.get()) {
            info(message, replacements);
            throwable.printStackTrace();
        }
    }
    @Override public void debug(@NotNull Throwable throwable) {
        if (isDebug.get())
            throwable.printStackTrace();
    }

    /**
     *  Inserts replacement objects into {@code {}} parentheses without position indication
     * <p>Example: {@code insertPlaceholderLikeSlf4j("Invalid entry {} at {}",
     * ".;sadge", "49L")} returns {@code "Invalid entry .;sadge at 49L"}</p>
     */
    @Contract(pure=true)
    @NotNull String insertPlaceholderLikeSlf4j(@NotNull final String message, @Nullable final Object... replacements) {
        if (replacements == null || replacements.length == 0)
            return message;
        StringBuilder result = new StringBuilder(message);
        int curReplacement = 0;
        int occurrence = 0;
        while (((occurrence = result.indexOf("{}", occurrence)) != -1) && (curReplacement < replacements.length)) {
            result.replace(occurrence, occurrence + 2, String.valueOf(replacements[curReplacement++]));
        }
        return result.toString();
    }
}
