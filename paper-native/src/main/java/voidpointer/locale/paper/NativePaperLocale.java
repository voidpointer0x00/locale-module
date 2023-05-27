/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.paper;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import voidpointer.locale.api.LocaleKey;
import voidpointer.locale.api.Placeholder;
import voidpointer.locale.bukkit.AbstractBukkitLocale;
import voidpointer.locale.bukkit.storage.LocaleStorage;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;
import static net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer.plainText;
import static voidpointer.locale.paper.ComponentMessage.parsed;
import static voidpointer.locale.paper.ComponentMessage.placeholdersToResolvers;

public class NativePaperLocale extends AbstractBukkitLocale {
    private final ComponentPlaceholderFactory placeholderFactory = new ComponentPlaceholderFactory();

    public NativePaperLocale(@NotNull LocaleStorage localeStorage) {
        super(localeStorage);
    }

    @Override
    public @NotNull ComponentPlaceholderFactory placeholders() {
        return placeholderFactory;
    }

    @Override
    public @NotNull ComponentMessage get(@NotNull LocaleKey key) {
        return parsed(localeStorage.translate(key));
    }

    @Override
    public @NotNull ComponentMessage get(@NotNull LocaleKey key, @NotNull Placeholder... placeholders) {
        return parsed(localeStorage.translate(key), placeholders);
    }

    public @NotNull ComponentMessage get(@NotNull LocaleKey key, @NotNull TagResolver... resolvers) {
        return new ComponentMessage(miniMessage().deserialize(localeStorage.translate(key), resolvers));
    }

    @Override
    public @NotNull ComponentMessage raw(@NotNull LocaleKey key) {
        return ComponentMessage.raw(localeStorage.translate(key));
    }

    @Override
    public @NotNull ComponentMessage raw(@NotNull LocaleKey key, @NotNull Placeholder... placeholders) {
        return ComponentMessage.raw(localeStorage.translate(key), placeholders);
    }

    public @NotNull ComponentMessage raw(@NotNull LocaleKey key, @NotNull TagResolver... resolvers) {
        return new ComponentMessage(plainText().deserialize(miniMessage().escapeTags(localeStorage.translate(key), resolvers)));
    }

    public void send(@NotNull LocaleKey key, @NotNull Audience audience, @NotNull TagResolver... resolvers) {
        audience.sendMessage(miniMessage().deserialize(localeStorage.translate(key), resolvers));
    }

    @Override
    public void send(@NotNull LocaleKey key, @NotNull CommandSender audience, @NotNull Placeholder... placeholders) {
        audience.sendMessage(miniMessage().deserialize(localeStorage.translate(key), placeholdersToResolvers(placeholders)));
    }

    @Override
    public void send(@NotNull LocaleKey key, @NotNull CommandSender audience) {
        audience.sendMessage(miniMessage().deserialize(localeStorage.translate(key)));
    }
}
