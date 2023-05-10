/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.impl.adventure;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;
import voidpointer.mc.localemodule.Message;
import voidpointer.mc.localemodule.Placeholder;
import voidpointer.mc.localemodule.impl.MessageBuilder;
import voidpointer.mc.localemodule.impl.PlaceholderResolver;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

@RequiredArgsConstructor
public final class ComponentMessageBuilder implements MessageBuilder {
    @NotNull private final String format;
    @NotNull private final PlaceholderResolver<ComponentLike> placeholderResolver;
    private final Collection<Placeholder> placeholders = new LinkedList<>();

    @Override public @NotNull MessageBuilder withAll(@NotNull Placeholder... placeholders) {
        Collections.addAll(this.placeholders, placeholders);
        return this;
    }

    @Override public @NotNull Message build() {
        return new ComponentMessage(placeholderResolver.resolve(format, placeholders));
    }
}
