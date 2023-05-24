/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.bukkit.legacy;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import voidpointer.locale.api.Message;
import voidpointer.locale.bukkit.BukkitMessage;

import java.util.Collection;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded=true)
@RequiredArgsConstructor
public final class LegacyBukkitMessage implements BukkitMessage {
    @EqualsAndHashCode.Include
    private final String message;
    private String parsed;

    @Override
    public @NotNull Message<CommandSender> send(@Nullable CommandSender audience) {
        if (audience != null)
            audience.sendMessage(parsed == null ? parsed = translateAlternateColorCodes('&', message) : parsed);
        return this;
    }

    @Override
    public @NotNull Message<CommandSender> send(@NotNull CommandSender... audience) {
        for (final CommandSender receiver : audience)
            receiver.sendMessage(parsed == null ? parsed = translateAlternateColorCodes('&', message) : parsed);
        return this;
    }

    @Override
    public @NotNull Message<CommandSender> send(@NotNull Collection<? extends CommandSender> audience) {
        for (final CommandSender receiver : audience)
            receiver.sendMessage(parsed == null ? parsed = translateAlternateColorCodes('&', message) : parsed);
        return this;
    }

    @Override public @NotNull String literal() {
        return message;
    }
}
