/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.bukkit.storage.yaml;

import org.bukkit.configuration.ConfigurationSection;

public interface YamlLocaleFile {
    /** @throws IllegalStateException if the configuration was not loaded first. */
    ConfigurationSection config() throws IllegalStateException;

    boolean load();

    boolean loadAndUpdateDefaults();

    boolean save();
}
