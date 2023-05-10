/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.impl.legacy;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import voidpointer.mc.localemodule.Message;

import java.util.Collection;

@ToString(onlyExplicitlyIncluded=true)
@RequiredArgsConstructor
public class TextMessage implements Message {
    @ToString.Include
    private final String text;
    // TODO

    @Override public @NotNull Message send(@Nullable CommandSender audience) {
        throw new UnsupportedOperationException();
    }

    @Override public @NotNull Message send(@Nullable CommandSender... audience) {
        throw new UnsupportedOperationException();
    }

    @Override public @NotNull Message send(@Nullable Collection<? extends CommandSender> audience) {
        throw new UnsupportedOperationException();
    }

    public @NotNull String text() {
        return text;
    }
}
