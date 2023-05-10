/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.impl.adventure;

import lombok.AllArgsConstructor;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;
import voidpointer.mc.localemodule.Placeholder;

@AllArgsConstructor
public class ComponentPlaceholder implements Placeholder {
    private final String key;
    private ComponentLike component;

    @Override public @NotNull String key() {
        return key;
    }

    public ComponentLike component() {
        return component;
    }
}
