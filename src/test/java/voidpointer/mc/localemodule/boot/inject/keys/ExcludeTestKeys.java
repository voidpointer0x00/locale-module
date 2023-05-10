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

@LocaleKeyContainer
public class ExcludeTestKeys {
    public static final LocaleKey INCLUDED = LocaleKey.of("exclude-test.i-am-included", "I'm included");
    @LocaleKeyContainer.Exclude
    public static final LocaleKey EXCLUDED = LocaleKey.of("exclude-test.excluded", "Senpai notice me з:");
}