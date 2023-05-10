/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface PluginLocale extends Locale {
    void addKeys(@NotNull LocaleKey... keys);

    void addKeys(@NotNull Collection<LocaleKey> localeKeys);

    @NotNull Log logger();

    void load();

    void save();
}
