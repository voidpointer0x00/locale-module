/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.bukkit.storage.yaml;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import voidpointer.locale.api.Log;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Getter
@Builder
@AllArgsConstructor
public final class TranslatableYamlLocaleFile implements LocaleFile {
    public static final String DEFAULT_FILENAME_PATTERN = "locale/%s.yml";

    private final String filenamePattern;
    private final Consumer<String> saveDefaultTask;
    private final Supplier<String> languageProvider;
    private final Log log;
    private final File dataFolder;
    private YamlConfiguration yamlConfiguration;

    @Override public ConfigurationSection config() {
        if (yamlConfiguration == null)
            throw new IllegalStateException("yamlConfiguration was not initialized, config not loaded?");
        return yamlConfiguration;
    }

    @Override public boolean load() {
        File localeFile = new File(dataFolder, formattedFilename());
        if (!localeFile.exists())
            saveDefault();
        try {
            yamlConfiguration = YamlConfiguration.loadConfiguration(localeFile);
        } catch (final Exception exception) {
            log.warn("Cannot load locale: {}", exception, exception.getMessage());
            return false;
        }
        return true;
    }

    @Override public boolean loadAndUpdateDefaults() {
        return load() && save();
    }

    @Override public boolean save() {
        final File localeFile = new File(dataFolder, formattedFilename());
        if (!localeFile.exists())
            saveDefault();
        if (!localeFile.exists() && !createFile(localeFile))
            return false;
        try {
            yamlConfiguration.options().copyDefaults(true);
            yamlConfiguration.save(localeFile);
            return true;
        } catch (final IOException ioException) {
            log.warn("Could not save {}: {}", localeFile, ioException.getMessage());
            log.debug(ioException);
            return false;
        }
    }

    private void saveDefault() {
        final String defaultPath = formattedFilename();
        try {
            saveDefaultTask.accept(defaultPath);
        } catch (final Exception exception) {
            log.debug("Could not save default resource for {}: {}", defaultPath, exception.getMessage());
            log.debug(exception);
        }
    }

    private String formattedFilename() {
        return String.format(filenamePattern, languageProvider.get());
    }

    private boolean createFile(final File file) {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs())
            return false;
        try {
            return file.createNewFile() || file.exists();
        } catch (final IOException ioException) {
            log.warn("Could create {} file: {}", file, ioException.getMessage());
            log.debug(ioException);
            return false;
        }
    }
}
