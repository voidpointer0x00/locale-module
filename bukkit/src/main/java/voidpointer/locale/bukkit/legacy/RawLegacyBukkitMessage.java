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

import java.util.Collection;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class RawLegacyBukkitMessage implements Message<CommandSender> {
    protected final String message;

    @Override
    public @NotNull Message<CommandSender> send(@Nullable CommandSender audience) {
        if (audience != null)
            audience.sendMessage(message);
        return this;
    }

    @Override
    public @NotNull Message<CommandSender> send(@NotNull Collection<? extends CommandSender> audiences) {
        for (final CommandSender audience : audiences)
            audience.sendMessage(message);
        return this;
    }

    @Override public final @NotNull String literal() { return message; }
}
