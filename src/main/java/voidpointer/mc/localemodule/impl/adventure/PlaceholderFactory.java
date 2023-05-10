/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.impl.adventure;

import org.jetbrains.annotations.NotNull;
import voidpointer.mc.localemodule.Message;
import voidpointer.mc.localemodule.Placeholder;

public interface PlaceholderFactory {
    @NotNull Placeholder parsed(@NotNull String key, @NotNull Object value);

    @NotNull Placeholder unparsed(@NotNull String key, @NotNull Object value);

    @NotNull Placeholder unparsed(@NotNull String key, @NotNull Message message);
}
