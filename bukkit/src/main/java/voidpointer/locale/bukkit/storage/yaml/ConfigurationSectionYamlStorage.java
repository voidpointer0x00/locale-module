/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.bukkit.storage.yaml;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import voidpointer.locale.api.LocaleKey;
import voidpointer.locale.api.LocaleStorage;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ConfigurationSectionYamlStorage implements LocaleStorage {
    protected final Set<LocaleKey> defaultKeysCache = ConcurrentHashMap.newKeySet();
    protected ConfigurationSection localeSection;

    @Override public Collection<LocaleKey> defaults() {
        return Collections.unmodifiableSet(defaultKeysCache);
    }

    @Override public void addDefault(@NotNull LocaleKey key) {
        cacheDefault(key);
    }

    @Override public void addDefaults(@NotNull LocaleKey... keys) {
        cacheDefaults(keys);
    }

    @Override public void addDefaults(@NotNull Collection<LocaleKey> keys) {
        cacheDefaults(keys);
    }

    @Override public void removeDefault(@NotNull LocaleKey key) {
        removeCachedDefault(key);
    }

    @Override public void removeDefaults(@NotNull Collection<LocaleKey> keys) {
        removeCachedDefaults(keys);
    }

    protected final void applyDefaultKeysCache() {
        defaultKeysCache.forEach(key -> localeSection.addDefault(key.path(), key.defaultValue()));
    }

    protected final void cacheDefault(@NotNull LocaleKey key) {
        defaultKeysCache.add(key);
    }

    protected final void cacheDefaults(@NotNull LocaleKey... keys) {
        defaultKeysCache.addAll(Arrays.stream(keys).toList());
    }

    protected final void cacheDefaults(@NotNull Collection<LocaleKey> keys) {
        defaultKeysCache.addAll(keys);
    }

    protected final void removeCachedDefault(@NotNull LocaleKey key) {
        defaultKeysCache.remove(key);
    }

    protected final void removeCachedDefaults(@NotNull Collection<LocaleKey> keys) {
        defaultKeysCache.removeAll(keys);
    }
}
