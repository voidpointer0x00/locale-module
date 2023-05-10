/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import voidpointer.mc.localemodule.Message;
import voidpointer.mc.localemodule.Placeholder;
import voidpointer.mc.localemodule.impl.adventure.PlaceholderFactory;

@AllArgsConstructor
@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public class AbstractMessageService<T> implements MessageService {
    @NotNull private PlaceholderResolver<T> resolver;
    @NotNull private PlaceholderFactory placeholderFactory;
    @NotNull private MessageBuilderFactory<T> messageFactory;

    @Override public @NotNull MessageBuilder builder(@NotNull final String format) {
        return messageFactory.builder(format, resolver);
    }

    @Override public @NotNull Placeholder parsed(@NotNull String key, @NotNull Object value) {
        return placeholderFactory.parsed(key, value);
    }

    @Override public @NotNull Placeholder unparsed(@NotNull String key, @NotNull Object value) {
        return placeholderFactory.unparsed(key, value);
    }

    @Override public @NotNull Placeholder unparsed(@NotNull String key, @NotNull Message message) {
        return placeholderFactory.unparsed(key, message);
    }
}
