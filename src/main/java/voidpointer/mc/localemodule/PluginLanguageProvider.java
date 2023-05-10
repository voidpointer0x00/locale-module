/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public abstract class PluginLanguageProvider implements Plugin, LanguageProvider {
    public static final String LANG_PATH = "locale.lang";
    @Override public @NotNull String provideLanguage() {
        return getConfig().getString(LANG_PATH, LanguageProvider.ENGLISH_LANGUAGE);
    }
}
