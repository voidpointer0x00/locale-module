/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.api;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record ImmutableLocaleKey(@NotNull String path, @NotNull String defaultValue) implements LocaleKey {
    @Override public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (!(o instanceof LocaleKey that))
            return false;
        return Objects.equals(path, that.path());
    }

    @Override public int hashCode() {
        return Objects.hash(path);
    }
}
