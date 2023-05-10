/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.boot.inject;

import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;

@RequiredArgsConstructor
public final class LocaleIocBootstrapper {
    public static void boot(final JavaPlugin plugin) {
        PluginLocaleFactory pluginLocaleFactory = new PluginLocaleFactory(plugin);
        LocaleAnnotationProcessor.process(plugin, pluginLocaleFactory);
    }
}
