/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.boot.inject.keys;

import voidpointer.mc.localemodule.LocaleKey;
import voidpointer.mc.localemodule.boot.inject.LocaleKeyContainer;
import voidpointer.mc.localemodule.boot.inject.ProvideLocaleKey;

import java.util.Collection;
import java.util.List;

@LocaleKeyContainer(onlyExplicitlyIncluded = true)
public class ProviderTestKeys {
    /*  All of these should not be included by the field, instead
     * they will be included through @ProvideLocaleKey methods invocation. */
    public static final LocaleKey LOW_RAM = LocaleKey.of("warn.low-ram", "Running low on RAM!");
    public static final LocaleKey NO_PAPI = LocaleKey.of("warn.no-papi", "PlaceholderAPI not detected");
    public static final LocaleKey SPECIAL = LocaleKey.of("warn.im-special", "Or am I..");
    /* this one is not included in providers, so it should not be visible at all. */
    public static final LocaleKey IGNORED = LocaleKey.of("warn.hehe", "They'll never see me...");

    @ProvideLocaleKey
    private static Collection<LocaleKey> provideWarnKeys() {
        return List.of(LOW_RAM, NO_PAPI);
    }

    @ProvideLocaleKey
    private static LocaleKey provideSpecialWarnKey() {
        return SPECIAL;
    }
}
