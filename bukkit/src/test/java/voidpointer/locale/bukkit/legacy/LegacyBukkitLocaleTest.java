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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import voidpointer.locale.api.Placeholder;

import static org.testng.Assert.assertEquals;

public class LegacyBukkitLocaleTest {

    @DataProvider
    public static Object[][] placeholdersLiteral() {
        return new Object[][] {
                {"", "", ""}, {"&", "key", "&"},
                {"&7", "key", "&7"}, {"&7Hi!", "key", "&7Hi!"},
                {"§7Hi!", "key", "§7Hi!"}, {"1", "key", 1},
                {"1.0", "key", 1.0F}, {"1.0", "key", 1.0D}
        };
    }

    @DataProvider
    public static Object[][] placeholdersParsed() {
        return new Object[][] {
                {"", "", ""}, {"&", "key", "&"},
                {"§7", "key", "&7"}, {"§7Hi!", "key", "&7Hi!"},
                {"§7Hi!", "key", "§7Hi!"}, {"1", "key", 1},
                {"1.0", "key", 1.0F}, {"1.0", "key", 1.0D}
        };
    }

    static MockPlugin mockPlugin;
    static LegacyBukkitLocale legacyBukkitLocale;

    @BeforeClass
    static void setup() {
        if (!MockBukkit.isMocked())
            MockBukkit.mock();
        mockPlugin = MockBukkit.createMockPlugin();
        legacyBukkitLocale = LegacyBukkitLocale.forPlugin(mockPlugin);
    }

    @Test(dataProvider = "placeholdersLiteral")
    public void placeholdersLiteral(String expectedReplacement, String key, Object replacement) {
        Placeholder literal = legacyBukkitLocale.placeholders().literal(key, replacement);
        assertEquals(expectedReplacement, literal.replacement());
        assertEquals(key, literal.key());
    }

    @Test(dataProvider = "placeholdersParsed")
    public void placeholdersParsed(String expectedReplacement, String key, Object replacement) {
        Placeholder literal = legacyBukkitLocale.placeholders().parsed(key, replacement);
        assertEquals(expectedReplacement, literal.replacement());
        assertEquals(key, literal.key());
    }
}