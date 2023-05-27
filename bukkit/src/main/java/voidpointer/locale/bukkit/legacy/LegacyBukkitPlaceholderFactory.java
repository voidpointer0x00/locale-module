/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.bukkit.legacy;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import voidpointer.locale.api.Message;
import voidpointer.locale.api.Placeholder;
import voidpointer.locale.api.PlaceholderFactory;

public class LegacyBukkitPlaceholderFactory implements PlaceholderFactory<CommandSender> {
    public static final char LEGACY_PLACEHOLDER_START = '{';
    public static final char LEGACY_PLACEHOLDER_END = '{';

    public String insert(final String format, final Placeholder... placeholders) {
        var buffer = new StringBuilder(format);
        for (Placeholder placeholder : placeholders) {
            var searchingTarget = LEGACY_PLACEHOLDER_START + placeholder.key() + LEGACY_PLACEHOLDER_END;
            insertReplacement(buffer, searchingTarget, placeholder.replacement());
        }
        return buffer.toString();
    }

    private void insertReplacement(final StringBuilder buffer, final String placeholder, final String replacement) {
        /* search for placeholder in buffer and insert replacement at that position */
        int occurrenceStart, occurrenceEnd;
        while ((occurrenceStart = buffer.indexOf(placeholder)) != -1) {
            occurrenceEnd = occurrenceStart + placeholder.length();
            if (null == replacement || replacement.isEmpty())
                buffer.delete(occurrenceStart, occurrenceEnd);
            else
                buffer.replace(occurrenceStart, occurrenceEnd, replacement);
        }
    }

    @Override public @NotNull Placeholder literal(@NotNull String placeholder, @NotNull Object replacement) {
        return Placeholder.of(placeholder, replacement);
    }

    @Override public @NotNull Placeholder literal(@NotNull String placeholder, @NotNull String replacement) {
        return Placeholder.of(placeholder, replacement);
    }

    @Override public @NotNull Placeholder literal(@NotNull String placeholder, @NotNull Message<CommandSender> replacement) {
        return literal(placeholder, replacement.literal());
    }

    @Override public @NotNull Placeholder parsed(@NotNull String placeholder, @NotNull Object replacement) {
        return parsed(placeholder, replacement.toString());
    }

    @Override public @NotNull Placeholder parsed(@NotNull String placeholder, @NotNull String replacement) {
        return Placeholder.of(placeholder, ChatColor.translateAlternateColorCodes('&', replacement));
    }

    @Override public @NotNull Placeholder parsed(@NotNull String placeholder, @NotNull Message<CommandSender> replacement) {
        return parsed(placeholder, replacement.literal());
    }
}
