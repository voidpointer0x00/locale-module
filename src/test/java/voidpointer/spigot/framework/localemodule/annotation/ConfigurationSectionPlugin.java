/*
 *            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *
 * Copyright (C) 2022 Vasiliy Petukhov <void.pointer@ya.ru>
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

package voidpointer.spigot.framework.localemodule.annotation;

import lombok.NonNull;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import voidpointer.spigot.framework.localemodule.config.LocaleConfigurationSection;

import java.io.File;

class ConfigurationSectionPlugin extends JavaPlugin {
    static final String PLUGIN_YML = "configuration_section_plugin.yml";
    @PluginLocale(defaultMessages=TestMessage.class, searchForInjection=true)
    static LocaleConfigurationSection locale;

    protected ConfigurationSectionPlugin(@NonNull final JavaPluginLoader loader, @NonNull final PluginDescriptionFile description, @NonNull final File dataFolder, @NonNull final File file) {
        super(loader, description, dataFolder, file);
    }
}
