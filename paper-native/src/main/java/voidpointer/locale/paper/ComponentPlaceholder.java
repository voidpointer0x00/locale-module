/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.paper;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import voidpointer.locale.api.Placeholder;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;
import static net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer.plainText;

@AllArgsConstructor(access=AccessLevel.PROTECTED)
public class ComponentPlaceholder implements Placeholder {
    private final String key;
    private final String replacement;
    protected Component componentReplacement;

    public static ComponentPlaceholder parsed(final String key, final String replacement) {
        return new ComponentPlaceholder(key, replacement, miniMessage().deserialize(replacement));
    }

    public static ComponentPlaceholder component(final String key, final Component component) {
        return new ComponentPlaceholder(key, plainText().serialize(component), component);
    }

    public static ComponentPlaceholder unparsed(final String key, final String replacement) {
        return new ComponentPlaceholder(key, replacement, plainText().deserialize(replacement));
    }

    @Override public final @NotNull String key() {
        return key;
    }

    @Override public final @NotNull String replacement() {
        return replacement;
    }

    public @NotNull Component component() {
        return componentReplacement;
    }
}
