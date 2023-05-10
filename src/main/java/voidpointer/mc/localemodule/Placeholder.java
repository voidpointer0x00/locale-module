/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Placeholder {
    @NotNull String key();

    static Placeholder of(@NotNull final String key, @Nullable Object value) {
        return new ImmutablePlaceholder(key, value != null ? value.toString() : "");
    }
}
