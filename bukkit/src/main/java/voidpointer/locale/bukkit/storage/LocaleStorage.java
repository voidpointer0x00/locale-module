/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.bukkit.storage;

import org.jetbrains.annotations.NotNull;
import voidpointer.locale.api.LocaleKey;

import java.util.Collection;

public interface LocaleStorage {
    /** @return translated key or it's default value if not present */
    @NotNull String translate(@NotNull final LocaleKey key);

    /** @return immutable collection of cached default keys. */
    Collection<LocaleKey> defaults();

    void addDefault(@NotNull final LocaleKey key);

    void addDefaults(@NotNull final LocaleKey... keys);

    void addDefaults(@NotNull final Collection<LocaleKey> keys);

    void removeDefault(@NotNull final LocaleKey key);

    void removeDefaults(@NotNull final Collection<LocaleKey> keys);

    boolean load();

    boolean save();
}
