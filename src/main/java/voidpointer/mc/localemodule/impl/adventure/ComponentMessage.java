/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.impl.adventure;

import net.kyori.adventure.text.ComponentLike;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import voidpointer.mc.localemodule.Message;

import java.util.Collection;
import java.util.Objects;

record ComponentMessage(ComponentLike component) implements Message {
    @Override public @NotNull Message send(@Nullable CommandSender audience) {
        if (audience != null)
            audience.sendMessage(component);
        return this;
    }

    @Override public @NotNull Message send(@Nullable CommandSender... audiences) {
        for (CommandSender audience : audiences)
            if (audience != null)
                audience.sendMessage(component);
        return this;
    }

    @Override public @NotNull Message send(@Nullable Collection<? extends CommandSender> audiences) {
        if (audiences != null)
            audiences.stream().filter(Objects::nonNull).forEach(audience -> audience.sendMessage(component));
        return this;
    }
}
