/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.boot.inject.keys;

import com.google.common.collect.ImmutableMap;
import voidpointer.mc.localemodule.LocaleKey;
import voidpointer.mc.localemodule.boot.inject.LocaleKeyContainer;

import java.util.Map;

@LocaleKeyContainer(onlyExplicitlyIncluded=true)
public class IncludeTestKeys {
    @LocaleKeyContainer.Include
    public static final LocaleKey INCLUDED = LocaleKey.of("include-test.included", "I'm included");
    @SuppressWarnings("unused")
    public static final LocaleKey EXCLUDED = LocaleKey.of("include-test.excluded", "I'm excluded");

    public static Map<String, String> expected() {
        return ImmutableMap.of(INCLUDED.path(), INCLUDED.defaultValue());
    }
}
