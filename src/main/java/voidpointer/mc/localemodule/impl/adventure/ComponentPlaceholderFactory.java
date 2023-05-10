/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.impl.adventure;

import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;
import voidpointer.mc.localemodule.Message;
import voidpointer.mc.localemodule.exception.UnexpectedTypeException;
import voidpointer.mc.localemodule.impl.legacy.TextMessage;

import static net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacyAmpersand;

final class ComponentPlaceholderFactory implements PlaceholderFactory {
    @Override public @NotNull ComponentPlaceholder parsed(@NotNull String key, @NotNull Object value) {
        return null;
    }

    @Override public @NotNull ComponentPlaceholder unparsed(@NotNull String key, @NotNull Object value) {
        return null;
    }

    @Override public @NotNull ComponentPlaceholder unparsed(@NotNull String key, @NotNull Message message) {
        // TODO gson serializer
        if (message instanceof TextMessage textMessage)
            return new ComponentPlaceholder(key, legacyAmpersand().deserialize(textMessage.text()));
        if (message instanceof ComponentMessage componentMessage)
            return new ComponentPlaceholder(key, componentMessage.component());
        throw new UnexpectedTypeException(message);
    }

    private ComponentLike deserialize(@NotNull final String str) {
        return null;
    }
}
