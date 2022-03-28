/*
 *            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *
 * Copyright (C) 2020 Vasiliy Petukhov <void.pointer@ya.ru>
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
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public class LocaleFile extends AbstractLocaleLogSection {
    public static final String LOCALE_FILENAME = "locale.yml";
    public static final String MESSAGES_PATH = "messages";
    private File messagesFile;
    private FileConfiguration fileConfiguration;

    protected LocaleFile() {}

    public LocaleFile(@NonNull final Plugin plugin) {
        super.setPlugin(plugin);
        load();
    }

    public final void reload() {
        loadFileConfiguration();
    }

    public final void save() {
        try {
            fileConfiguration.save(messagesFile);
        } catch (IOException ioException) {
            warn(String.format("Unable to save %s file", messagesFile.getAbsolutePath()), ioException);
        }
    }

    protected void load() {
        messagesFile = new File(super.getPlugin().getDataFolder(), LOCALE_FILENAME);
        saveDefaultMessagesFileIfNotExists();
        loadFileConfiguration();
    }

    protected void saveDefaultMessagesFileIfNotExists() {
        if (messagesFile.exists())
            return;
        if (!messagesFile.getParentFile().exists())
            messagesFile.getParentFile().mkdirs();
        saveDefaultMessagesFile();
    }

    protected void saveDefaultMessagesFile() {
        try {
            super.getPlugin().saveResource(messagesFile.getName(), false);
        } catch (IllegalArgumentException illegalArgumentException) {
            warn(String.format("Unable to save default %s file", messagesFile.getAbsolutePath()), illegalArgumentException);
        }
    }

    protected void loadFileConfiguration() {
        fileConfiguration = YamlConfiguration.loadConfiguration(messagesFile);
        fileConfiguration.options().copyDefaults(true).copyHeader(true);
        ConfigurationSection config = fileConfiguration.getConfigurationSection(MESSAGES_PATH);
        if (config != null) {
            super.setConfig(fileConfiguration.getConfigurationSection(MESSAGES_PATH));
        } else {
            super.setConfig(fileConfiguration.createSection(MESSAGES_PATH));
        }
    }
}
