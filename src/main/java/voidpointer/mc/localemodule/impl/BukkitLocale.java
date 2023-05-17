/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.impl;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import voidpointer.mc.localemodule.*;
import voidpointer.mc.localemodule.impl.config.YamlLocale;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public final class BukkitLocale implements PluginLocale {
    @NotNull private final Log log;
    @NotNull private final YamlLocale yamlLocale;
    @NotNull private final MessageService messageService;

    @Override public @NotNull Placeholder parsed(@NotNull String placeholder, @Nullable Object value) {
        return messageService.parsed(placeholder, value == null ? "" : value);
    }

    @Override public @NotNull Placeholder unparsed(@NotNull String placeholder, @Nullable Object value) {
        return messageService.unparsed(placeholder, value == null ? "" : value);
    }

    @Override public @NotNull Placeholder unparsed(@NotNull String placeholder, @NotNull Message message) {
        return messageService.unparsed(placeholder, message);
    }

    @Override public void send(@NotNull LocaleKey key, @NotNull CommandSender audience) {
        get(key).send(audience);
    }

    @Override public void send(@NotNull LocaleKey key, @NotNull CommandSender audience, @NotNull Placeholder... placeholders) {
        get(key, placeholders).send(audience);
    }

    @Override public @NotNull Message get(@NotNull LocaleKey key) {
        return messageService.builder(yamlLocale.resolve(key)).build();
    }

    @Override public @NotNull Message get(@NotNull LocaleKey key, @NotNull Placeholder... placeholders) {
        return messageService.builder(yamlLocale.resolve(key)).withAll(placeholders).build();
    }

    @Override public void addKeys(@NotNull LocaleKey... keys) {
        yamlLocale.addKeys(keys);
    }

    @Override public void addKeys(@NotNull final Collection<LocaleKey> localeKeys) {
        yamlLocale.addKeys(localeKeys);
    }

    @Override public @NotNull Collection<LocaleKey> defaultKeys() {
        return Collections.unmodifiableCollection(yamlLocale.defaultKeys());
    }

    @Override public @NotNull Log logger() {
        return log;
    }

    @Override public void load() {
        yamlLocale.load();
    }

    @Override public void save() {
        yamlLocale.save();
    }
}
