/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.boot.inject;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import voidpointer.mc.localemodule.LanguageProvider;
import voidpointer.mc.localemodule.Log;
import voidpointer.mc.localemodule.PluginLocale;
import voidpointer.mc.localemodule.impl.BukkitLocale;
import voidpointer.mc.localemodule.impl.BukkitLogger;
import voidpointer.mc.localemodule.impl.MessageService;
import voidpointer.mc.localemodule.impl.adventure.NativeAdventureMessageService;
import voidpointer.mc.localemodule.impl.config.LocaleFile;
import voidpointer.mc.localemodule.impl.config.YamlLocale;

import static voidpointer.mc.localemodule.LanguageProvider.ENGLISH_PROVIDER;

class PluginLocaleFactory {
    @NotNull private final Plugin plugin;
    @NotNull private final Log log;
    @NotNull private final LanguageProvider languageProvider;

    PluginLocaleFactory(Plugin plugin) {
        this(plugin, BukkitLogger.of(plugin));
    }

    PluginLocaleFactory(Plugin plugin, Log log) {
        this(plugin, log, (plugin instanceof LanguageProvider provider) ? provider : ENGLISH_PROVIDER);
    }

    PluginLocaleFactory(@NotNull Plugin plugin, @NotNull Log log, @NotNull LanguageProvider languageProvider) {
        this.plugin = plugin;
        this.log = log;
        this.languageProvider = languageProvider;
    }

    public @NotNull PluginLocale locale(final String pathPattern) {
        return new BukkitLocale(
                log,
                new YamlLocale(log, LocaleFile.ofPlugin(plugin, languageProvider, pathPattern)),
                messageService()
        );
    }

    private MessageService messageService() {
        // TODO implement runtime based service selection
        return new NativeAdventureMessageService();
    }
}
