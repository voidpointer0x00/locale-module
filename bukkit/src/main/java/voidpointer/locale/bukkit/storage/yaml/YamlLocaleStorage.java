/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.bukkit.storage.yaml;

import org.jetbrains.annotations.NotNull;
import voidpointer.locale.api.LocaleKey;
import voidpointer.locale.api.Log;

public class YamlLocaleStorage extends ConfigurationSectionYamlStorage {
    private final Log log;
    private final LocaleFile localeFile;

    public YamlLocaleStorage(final Log log, final LocaleFile localeFile) {
        this.log = log;
        this.localeFile = localeFile;
    }

    @Override public @NotNull String translate(@NotNull LocaleKey key) {
        if (!localeSection.contains(key.path()))
            log.warn("Missing {} translation", key.path());
        return localeSection.getString(key.path(), key.defaultValue());
    }

    @Override public final boolean load() {
        if (!localeFile.load())
            return false;
        this.localeSection = localeFile.config();
        return true;
    }

    @Override public final boolean save() {
        applyDefaultKeysCache();
        return localeFile.save();
    }
}