/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.boot.inject.keys;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import voidpointer.mc.localemodule.LocaleKey;
import voidpointer.mc.localemodule.boot.inject.LocaleKeyContainer;

@LocaleKeyContainer
@RequiredArgsConstructor
public enum EnumTestKeys implements LocaleKey {
    LOADED("Plugin {plugin} loaded!");

    private final String defaultValue;

    @Override public @NotNull String path() {
        return "enum-test." + LocaleKey.fromScreamingSnakeToKebab(toString());
    }

    @Override public @NotNull String defaultValue() {
        return defaultValue;
    }
}