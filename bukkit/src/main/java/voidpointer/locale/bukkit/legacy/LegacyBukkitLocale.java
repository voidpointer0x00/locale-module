/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.bukkit.legacy;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import voidpointer.locale.api.LocaleKey;
import voidpointer.locale.api.Log;
import voidpointer.locale.api.Placeholder;
import voidpointer.locale.bukkit.AbstractBukkitLocale;
import voidpointer.locale.bukkit.BukkitLogger;
import voidpointer.locale.bukkit.BukkitPlaceholderFactory;
import voidpointer.locale.bukkit.storage.LocaleStorage;
import voidpointer.locale.bukkit.storage.yaml.TranslatableYamlLocaleFile;
import voidpointer.locale.bukkit.storage.yaml.YamlLocaleStorage;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class LegacyBukkitLocale extends AbstractBukkitLocale {
    private final LegacyBukkitPlaceholderFactory placeholderFactory = new LegacyBukkitPlaceholderFactory();

    public LegacyBukkitLocale(@NotNull final LocaleStorage localeStorage) {
        super(localeStorage);
    }

    public static LegacyBukkitLocale forPlugin(final Plugin plugin) {
        Log log = new BukkitLogger(plugin);
        return new LegacyBukkitLocale(new YamlLocaleStorage(log, TranslatableYamlLocaleFile.builder()
                .filenamePattern(TranslatableYamlLocaleFile.DEFAULT_FILENAME_PATTERN)
                .saveDefaultTask((path) -> plugin.saveResource(path, true))
                .languageProvider(() -> plugin.getConfig().getString("locale.lang", "en"))
                .log(log)
                .dataFolder(plugin.getDataFolder())
                .build()));
    }

    @Override public final @NotNull BukkitPlaceholderFactory placeholders() {
        return placeholderFactory;
    }

    @Override public void send(@NotNull LocaleKey key, @NotNull CommandSender audience) {
        audience.sendMessage(translateAlternateColorCodes('&', localeStorage.translate(key)));
    }

    @Override public void send(@NotNull LocaleKey key, @NotNull CommandSender audience, @NotNull Placeholder... placeholders) {
        audience.sendMessage(translateAlternateColorCodes('&', placeholderFactory.insert(localeStorage.translate(key), placeholders)));
    }

    @Override public @NotNull LegacyBukkitMessage get(@NotNull LocaleKey key) {
        return new LegacyBukkitMessage(localeStorage.translate(key));
    }

    @Override public @NotNull LegacyBukkitMessage get(@NotNull LocaleKey key, @NotNull Placeholder... placeholders) {
        return new LegacyBukkitMessage(placeholderFactory.insert(localeStorage.translate(key), placeholders));
    }

    @Override public @NotNull RawLegacyBukkitMessage raw(@NotNull LocaleKey key) {
        return new RawLegacyBukkitMessage(localeStorage.translate(key));
    }

    @Override public @NotNull RawLegacyBukkitMessage raw(@NotNull LocaleKey key, @NotNull Placeholder... placeholders) {
        return new RawLegacyBukkitMessage(placeholderFactory.insert(localeStorage.translate(key), placeholders));
    }
}
