/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.impl.adventure;

import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import voidpointer.mc.localemodule.Placeholder;
import voidpointer.mc.localemodule.TextPlaceholder;
import voidpointer.mc.localemodule.exception.UnexpectedTypeException;
import voidpointer.mc.localemodule.impl.AbstractMessageService;

import java.util.Collection;
import java.util.stream.Collectors;

import static net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.component;
import static net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacySection;

public final class NativeAdventureMessageService extends AbstractMessageService<ComponentLike> {
    public NativeAdventureMessageService() {
        super(NativeAdventureMessageService::resolvePlaceholders, new ComponentPlaceholderFactory(),
                ComponentMessageBuilder::new);
    }

    private static ComponentLike resolvePlaceholders(@NotNull String format, Collection<Placeholder> placeholders) {
        return MiniMessage.miniMessage().deserialize(format, TagResolver.resolver(placeholders.stream().map(placeholder -> {
            /* TODO json support */
            if (placeholder instanceof ComponentPlaceholder componentPlaceholder)
                return component(placeholder.key(), componentPlaceholder.component());
            if (placeholder instanceof TextPlaceholder textPlaceholder)
                return component(placeholder.key(), legacySection().deserialize(textPlaceholder.text()));
            throw new UnexpectedTypeException(placeholder);
        }).collect(Collectors.toList())));
    }
}
