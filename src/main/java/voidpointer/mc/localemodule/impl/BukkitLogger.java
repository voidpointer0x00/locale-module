/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.impl;

import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import voidpointer.mc.localemodule.Log;

import java.util.function.Supplier;
import java.util.logging.Logger;

@RequiredArgsConstructor
public final class BukkitLogger implements Log {
    public static final String DEBUG_PATH = "locale.debug.on";
    public static final String VERBOSE_PATH = "locale.debug.verbose";

    @NotNull private final Supplier<Boolean> isDebuggingSuppler;
    @NotNull private final Supplier<Boolean> isVerboseSupplier;
    @NotNull private final Logger log;

    public static @NotNull BukkitLogger of(final Plugin plugin) {
        return new BukkitLogger(() -> plugin.getConfig().getBoolean(DEBUG_PATH, false),
                () -> plugin.getConfig().getBoolean(VERBOSE_PATH), plugin.getLogger());
    }

    @Override public void info(@NotNull String message) {
        log.info(message);
    }

    @Override public void info(@NotNull String message, Object... placeholders) {

    }

    @Override public void info(@NotNull String message, @NotNull Throwable throwable, Object... placeholders) {

    }

    @Override public void warn(@NotNull String message) {

    }

    @Override public void warn(@NotNull String message, Object... placeholders) {

    }

    @Override public void warn(@NotNull String message, @NotNull Throwable throwable, Object... placeholders) {

    }

    @Override public void error(@NotNull String message) {

    }

    @Override public void error(@NotNull String message, Object... placeholders) {

    }

    @Override public void error(@NotNull String message, @NotNull Throwable throwable, Object... placeholders) {

    }

    @Override public void debug(@NotNull String message) {

    }

    @Override public void debug(@NotNull String message, Object... placeholders) {

    }

    @Override public void debug(@NotNull String message, @NotNull Throwable throwable, Object... placeholders) {

    }

    private String resolve(final Object obj) {
        return obj == null ? "null" : obj.toString();
    }
}
