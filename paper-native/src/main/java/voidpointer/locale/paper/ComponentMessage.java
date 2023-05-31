/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.paper;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import voidpointer.locale.api.Message;

import java.util.Collection;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;
import static net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer.plainText;

public record ComponentMessage(Component component) implements Message<CommandSender> {
    public static final MiniMessage EMPTY_MM = MiniMessage.builder().tags(TagResolver.empty()).build();

    public static ComponentMessage parsed(@NotNull final String content) {
        return new ComponentMessage(miniMessage().deserialize(content));
    }

    public static ComponentMessage parsed(String content, voidpointer.locale.api.Placeholder[] placeholders) {
        return new ComponentMessage(miniMessage().deserialize(content, placeholdersToResolvers(placeholders)));
    }

    public static ComponentMessage raw(final String content) {
        return new ComponentMessage(plainText().deserialize(content));
    }

    public static ComponentMessage raw(String content, voidpointer.locale.api.Placeholder[] placeholders) {
        return raw(content, placeholdersToResolvers(placeholders));
    }

    public static ComponentMessage raw(String content, TagResolver... resolvers) {
        return new ComponentMessage(EMPTY_MM.deserialize(content, resolvers));
    }

    public static ComponentMessage component(Component component) {
        return new ComponentMessage(component);
    }

    public static TagResolver[] placeholdersToResolvers(final voidpointer.locale.api.Placeholder[] placeholders) {
        TagResolver[] resolvers = new TagResolver[placeholders.length];
        for (int index = 0; index < resolvers.length; index++) {
            if (placeholders[index] instanceof ComponentPlaceholder placeholder)
                resolvers[index] = Placeholder.component(placeholder.key(), placeholder.component());
            else
                resolvers[index] = Placeholder.parsed(placeholders[index].key(), placeholders[index].replacement());
        }
        return resolvers;
    }

    public @NotNull ComponentMessage send(@NotNull Audience audience) {
        audience.sendMessage(component);
        return this;
    }

    @Override
    public @NotNull ComponentMessage send(@Nullable CommandSender audience) {
        if (audience != null)
            audience.sendMessage(component);
        return this;
    }

    @Override
    public @NotNull ComponentMessage send(@NotNull Collection<? extends CommandSender> audiences) {
        for (var audience : audiences)
            audience.sendMessage(component);
        return this;
    }

    @Override public @NotNull String literal() { return plainText().serialize(component); }

    @Override public @NotNull Component component() { return component; }
}
