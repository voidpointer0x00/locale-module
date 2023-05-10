/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.impl.config;

import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import voidpointer.mc.localemodule.LocaleKey;
import voidpointer.mc.localemodule.Log;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

@RequiredArgsConstructor
public final class YamlLocale {
    private final Collection<LocaleKey> defaultKeys = new LinkedHashSet<>();
    private final YamlConfiguration config = new YamlConfiguration();

    @NotNull private final Log log;
    @NotNull private final LocaleFile localeFile;

    public @NotNull String resolve(@NotNull final LocaleKey key) {
        if (!config.contains(key.path())) {
            log.warn("Unable to resolve translation for key «{}».");
            return key.defaultValue();
        }
        return config.getString(key.path(), key.defaultValue());
    }

    public void load() {
        if (!localeFile.file().exists())
            trySaveDefault();
        tryLoad();
    }

    public void addKeys(@NotNull final LocaleKey... keys) {
        Collections.addAll(defaultKeys, keys);
    }

    public void addKeys(final Collection<LocaleKey> localeKeys) {
        defaultKeys.addAll(localeKeys);
    }

    public void save() {
        defaultKeys.forEach(key -> config.addDefault(key.path(), key.defaultValue()));
        config.options().copyDefaults(true);
        try {
            config.save(localeFile.file());
        } catch (final IOException ioException) {
            log.warn("Unable to save {}.", localeFile);
        }
    }

    private void trySaveDefault() {
        try {
            localeFile.saveFromResources();
        } catch (final Exception exception) {
            log.debug("{} is missing within plugin .jar: {}", exception.getMessage(), exception);
        }
    }

    private void tryLoad() {
        try {
            config.load(localeFile.file());
        } catch (final IOException ioException) {
            log.warn("Could not load {}: ", ioException.getMessage(), ioException);
        } catch (final InvalidConfigurationException invalidConfigurationException) {
            invalidConfigurationException.printStackTrace();
        }
    }
}
