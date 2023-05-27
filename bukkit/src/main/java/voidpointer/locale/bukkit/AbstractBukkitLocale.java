/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.bukkit;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import voidpointer.locale.api.LocaleKey;
import voidpointer.locale.bukkit.storage.LocaleStorage;

import java.util.Collection;

@RequiredArgsConstructor
public abstract class AbstractBukkitLocale implements BukkitLocale {
    @NotNull protected LocaleStorage localeStorage;

    @Override public void addDefault(@NotNull LocaleKey key) {
        localeStorage.addDefault(key);
    }

    @Override public void addDefaults(@NotNull LocaleKey... keys) {
        localeStorage.addDefaults(keys);
    }

    @Override public void addDefaults(@NotNull Collection<LocaleKey> keys) {
        localeStorage.addDefaults(keys);
    }

    @Override public @NotNull Collection<LocaleKey> defaults() {
        return localeStorage.defaults();
    }

    @Override public void removeDefault(@NotNull LocaleKey key) {
        localeStorage.removeDefault(key);
    }

    @Override public void removeDefaults(@NotNull Collection<LocaleKey> keys) {
        localeStorage.removeDefaults(keys);
    }

    @Override public boolean load() {
        return localeStorage.load();
    }

    @Override public boolean save() {
        return localeStorage.save();
    }
}
