/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Locale {
    @NotNull Placeholder parsed(@NotNull String placeholder, @Nullable Object value);

    @NotNull Placeholder unparsed(@NotNull String placeholder, @Nullable Object value);

    @NotNull Placeholder unparsed(@NotNull String placeholder, @NotNull Message message);

    void send(@NotNull LocaleKey key, @NotNull CommandSender audience);

    void send(@NotNull LocaleKey key, @NotNull CommandSender audience, @NotNull Placeholder... placeholders);

    @NotNull Message get(@NotNull LocaleKey key);

    @NotNull Message get(@NotNull LocaleKey key, @NotNull Placeholder... placeholders);
}
