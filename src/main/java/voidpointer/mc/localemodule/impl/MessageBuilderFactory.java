/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.impl;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface MessageBuilderFactory<T> {
    @NotNull MessageBuilder builder(@NotNull String format, @NotNull PlaceholderResolver<T> resolver);
}