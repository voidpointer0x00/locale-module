/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.paper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import voidpointer.locale.api.Log;

import java.util.function.Supplier;

import static voidpointer.locale.bukkit.BukkitLogger.insertPlaceholderLikeSlf4j;

@RequiredArgsConstructor
@AllArgsConstructor
public class SLF4JLog implements Log {
    private final Logger logger;
    @Getter
    @Setter
    private Supplier<Boolean> isDebug;

    @Override public void info(@NotNull String message) { logger.info(message); }
    @Override public void info(@NotNull String message, @Nullable Object... replacements) {
        logger.info(message, replacements);
    }
    @Override public void info(@NotNull String message, @NotNull Throwable throwable, @Nullable Object... replacements) {
        info(insertPlaceholderLikeSlf4j(message, replacements), replacements);
    }

    @Override public void warn(@NotNull String message) { logger.warn(message); }
    @Override
    public void warn(@NotNull String message, @Nullable Object... replacements) {
        logger.warn(message, replacements);
    }
    @Override
    public void warn(@NotNull String message, @NotNull Throwable throwable, @Nullable Object... replacements) {
        logger.warn(insertPlaceholderLikeSlf4j(message, replacements), throwable);
    }

    @Override
    public void error(@NotNull String message) { logger.error(message); }
    @Override
    public void error(@NotNull String message, @Nullable Object... replacements) {
        logger.error(message, replacements);
    }
    @Override
    public void error(@NotNull String message, @NotNull Throwable throwable, @Nullable Object... replacements) {
        logger.error(insertPlaceholderLikeSlf4j(message, replacements), throwable);
    }

    @Override
    public void debug(@NotNull String message) {
        if (isDebug == null)
            logger.debug(message);
        else if (isDebug.get())
            logger.info(message);
    }

    @Override
    public void debug(@NotNull String message, @Nullable Object... replacements) {
        if (isDebug == null)
            logger.debug(message, replacements);
        else if (isDebug.get())
            logger.info(message, replacements);
    }

    @Override
    public void debug(@NotNull String message, @NotNull Throwable throwable, @Nullable Object... replacements) {
        if (isDebug == null)
            logger.debug(insertPlaceholderLikeSlf4j(message, replacements), throwable);
        else if (isDebug.get())
            logger.info(insertPlaceholderLikeSlf4j(message, replacements), throwable);
    }

    @Override
    public void debug(@NotNull Throwable throwable) {
        if (isDebug == null)
            logger.debug("", throwable);
        else if (isDebug.get())
            logger.info("", throwable);
    }
}
