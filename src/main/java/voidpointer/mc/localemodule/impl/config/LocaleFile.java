/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.impl.config;

import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import voidpointer.mc.localemodule.LanguageProvider;

import java.io.File;

@RequiredArgsConstructor
public final class LocaleFile {

    public static LocaleFile ofPlugin(final Plugin plugin, final LanguageProvider languageProvider, final String pattern) {
        return new LocaleFile(path -> plugin.saveResource(path, false),
                plugin.getDataFolder(), languageProvider, pattern);
    }

    private final SaveResourceTask saveResourceTask;
    private final File dataFolder;
    private final LanguageProvider languageProvider;
    private final String filenamePattern;

    private String lastLanguage;
    private File lastFile;

    public void saveFromResources() throws Exception {
        saveResourceTask.saveWithoutReplacing(getFilenameWithLanguage());
    }

    public @NotNull File file() {
        final String lastLanguage = this.lastLanguage;
        if (!(this.lastLanguage = languageProvider.provideLanguage()).equals(lastLanguage) || (lastFile == null))
            return (lastFile = new File(dataFolder, getFilenameWithLanguage()));
        return lastFile;
    }

    private String getFilenameWithLanguage() {
        return String.format(filenamePattern, lastLanguage == null ? "" : lastLanguage);
    }
}
