/*
 *            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *
 * Copyright (C) 2020-2021 Vasiliy Petukhov <void.pointer@ya.ru>
 *
 * Everyone is permitted to copy and distribute verbatim or modified
 * copies of this license document, and changing it is allowed as long
 * as the name is changed.
 *
 *            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *  0. You just DO WHAT THE FUCK YOU WANT TO.
 */

package voidpointer.spigot.framework.localemodule.config;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.plugin.Plugin;

import java.io.File;

@Setter(AccessLevel.PROTECTED)
public class TranslatedLocaleFileConfiguration extends LocaleFileConfiguration {
    public static final String LOCALE_FILENAME_FORMAT = "locale-%s.yml";
    private String language;

    public TranslatedLocaleFileConfiguration(final @NonNull Plugin plugin, final String language) {
        this.language = language;
        load(plugin);
    }

    public void changeLanguage(final String language) {
        setLanguage(language);
        load(super.getPlugin());
    }

    @Override protected void load(final Plugin plugin) {
        if (null == language) {
            super.load(plugin);
            return;
        }
        // basically this implementation differs only in the file that's loaded
        super.setPlugin(plugin);
        super.setMessagesFile(new File(plugin.getDataFolder(), getLocaleFilename()));
        warnIfLanguageIsMissing(plugin);
        super.saveDefaultMessagesFileIfNotExists();
        super.loadFileConfiguration();
    }

    protected String getLocaleFilename() {
        return (null != language) ? String.format(LOCALE_FILENAME_FORMAT, language) : LOCALE_FILENAME;
    }

    protected void warnIfLanguageIsMissing(final Plugin plugin) {
        if (null == plugin.getResource(getLocaleFilename())) {
            plugin.getLogger().warning("Trying to load a missing translation file " + getLocaleFilename());
        }
    }
}
