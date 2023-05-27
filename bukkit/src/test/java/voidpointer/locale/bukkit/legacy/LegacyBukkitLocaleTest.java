/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.bukkit.legacy;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import voidpointer.locale.api.Placeholder;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;

class LegacyBukkitLocaleTest {

    public static Stream<Arguments> placeholdersLiteral() {
        return Stream.of(
                of("", "", ""),
                of("&", "key", "&"),
                of("&7", "key", "&7"),
                of("&7Hi!", "key", "&7Hi!"),
                of("§7Hi!", "key", "§7Hi!"),
                of("1", "key", 1),
                of("1.0", "key", 1.0F),
                of("1.0", "key", 1.0D)
        );
    }

    public static Stream<Arguments> placeholdersParsed() {
        return Stream.of(
                of("", "", ""),
                of("&", "key", "&"),
                of("§7", "key", "&7"),
                of("§7Hi!", "key", "&7Hi!"),
                of("§7Hi!", "key", "§7Hi!"),
                of("1", "key", 1),
                of("1.0", "key", 1.0F),
                of("1.0", "key", 1.0D)
        );
    }

    static MockPlugin mockPlugin;
    static LegacyBukkitLocale legacyBukkitLocale;

    @BeforeAll
    static void setup() {
        MockBukkit.mock();
        mockPlugin = MockBukkit.createMockPlugin();
        legacyBukkitLocale = LegacyBukkitLocale.forPlugin(mockPlugin);
    }

    @ParameterizedTest
    @MethodSource
    void placeholdersLiteral(String expectedReplacement, String key, Object replacement) {
        Placeholder literal = legacyBukkitLocale.placeholders().literal(key, replacement);
        assertEquals(expectedReplacement, literal.replacement());
        assertEquals(key, literal.key());
    }

    @ParameterizedTest
    @MethodSource
    void placeholdersParsed(String expectedReplacement, String key, Object replacement) {
        Placeholder literal = legacyBukkitLocale.placeholders().parsed(key, replacement);
        assertEquals(expectedReplacement, literal.replacement());
        assertEquals(key, literal.key());
    }
}