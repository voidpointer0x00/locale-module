/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.paper;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import voidpointer.locale.api.Message;
import voidpointer.locale.api.PlaceholderFactory;

public class ComponentPlaceholderFactory implements PlaceholderFactory<CommandSender> {
    @Override
    public @NotNull ComponentPlaceholder literal(@NotNull String placeholder, @NotNull Object replacement) {
        return literal(placeholder, replacement.toString());
    }

    @Override
    public @NotNull ComponentPlaceholder literal(@NotNull String placeholder, @NotNull String replacement) {
        return ComponentPlaceholder.unparsed(placeholder, replacement);
    }

    @Override
    public @NotNull ComponentPlaceholder literal(@NotNull String placeholder, @NotNull Message<CommandSender> replacement) {
        if (replacement instanceof ComponentMessage componentMessage)
            return ComponentPlaceholder.component(placeholder, componentMessage.component());
        return ComponentPlaceholder.unparsed(placeholder, replacement.literal());
    }

    @Override
    public @NotNull ComponentPlaceholder parsed(@NotNull String placeholder, @NotNull Object replacement) {
        return parsed(placeholder, replacement.toString());
    }

    @Override
    public @NotNull ComponentPlaceholder parsed(@NotNull String placeholder, @NotNull String replacement) {
        return ComponentPlaceholder.parsed(placeholder, replacement);
    }

    @Override
    public @NotNull ComponentPlaceholder parsed(@NotNull String placeholder, @NotNull Message<CommandSender> replacement) {
        if (replacement instanceof ComponentMessage componentMessage)
            return ComponentPlaceholder.component(placeholder, componentMessage.component());
        return ComponentPlaceholder.parsed(placeholder, replacement.literal());
    }
}
