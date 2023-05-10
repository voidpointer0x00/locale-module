/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.impl;

import org.jetbrains.annotations.NotNull;
import voidpointer.mc.localemodule.Placeholder;

import java.util.Collection;

public interface PlaceholderResolver<T> {
    T resolve(@NotNull String format, Collection<Placeholder> placeholders);
}
