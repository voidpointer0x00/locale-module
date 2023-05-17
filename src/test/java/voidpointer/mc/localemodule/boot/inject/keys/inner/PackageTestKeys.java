/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.boot.inject.keys.inner;

import com.google.common.collect.ImmutableMap;
import voidpointer.mc.localemodule.LocaleKey;
import voidpointer.mc.localemodule.boot.inject.LocaleKeyContainer;

import java.util.Map;

@LocaleKeyContainer
public final class PackageTestKeys {
    public static final LocaleKey PACKAGE_KEY = LocaleKey.of("pkg.package-key", "I'm included through package scan");

    public static Map<String, String> expected() {
        return ImmutableMap.of(PACKAGE_KEY.path(), PACKAGE_KEY.defaultValue());
    }
}
