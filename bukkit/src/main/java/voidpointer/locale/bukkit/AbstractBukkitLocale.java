/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.bukkit;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import voidpointer.locale.api.LocaleStorage;

@RequiredArgsConstructor
public abstract class AbstractBukkitLocale implements BukkitLocale {
    @NotNull protected LocaleStorage localeStorage;

    @Override
    public @NotNull LocaleStorage storage() {
        return localeStorage;
    }
}
