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
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import voidpointer.locale.api.LocaleKey;

import java.io.File;
import java.io.IOException;

import static java.lang.String.format;
import static org.testng.Assert.*;
import static voidpointer.locale.bukkit.storage.yaml.TranslatableYamlLocaleFile.DEFAULT_FILENAME_PATTERN;

public class LegacyBukkitLocaleIntegrationTest {
    private static final LocaleKey key = LocaleKey.of("key", "&7Valid text");
    private static final LocaleKey keyDefault = LocaleKey.of(key.path(), "&8Added as default");
    private static final String keyTranslated = "§7Valid text";
    public static final String keyDefaultTranslated = "§8Added as default";
    private static final String keyCustomValue = "&x&5&9&2&6&9&3Woohoo";
    private static final String keyCustomTranslated = ChatColor.translateAlternateColorCodes('&', keyCustomValue);
    static MockPlugin plugin;
    static LegacyBukkitLocale locale;
    static PlayerMock playerMock;
    static File localeFile;

    @BeforeClass
    static void setup() {
        if (!MockBukkit.isMocked())
            MockBukkit.mock();
        playerMock = MockBukkit.getMock().addPlayer();
        plugin = MockBukkit.createMockPlugin();
        plugin.getConfig().set("locale.lang", "en");
        plugin.getConfig().set("locale.debug", false);
        plugin.saveConfig();
        localeFile = new File(plugin.getDataFolder(), format(DEFAULT_FILENAME_PATTERN, "en"));
        locale = LegacyBukkitLocale.forPlugin(plugin);
    }

    @Test
    void testUnknownKey() {
        Assert.assertTrue(locale.load());
        assertEquals(locale.get(key).literal(), key.defaultValue());
        locale.send(key, playerMock);
        assertEquals(playerMock.nextMessage(), keyTranslated);
        locale.get(key).send(playerMock);
        assertEquals(playerMock.nextMessage(), keyTranslated);
    }

    @Test(dependsOnMethods = "testUnknownKey")
    void testSavesAndLoadsDefaultKey() {
        assertFalse(localeFile.exists()); /* there is no default file for this plugin */
        locale.addDefault(keyDefault);
        assertTrue(locale.save());
        assertTrue(localeFile.exists());
        assertTrue(locale.load());
        var localeConfig = YamlConfiguration.loadConfiguration(localeFile);
        assertEquals(localeConfig.get(key.path()), keyDefault.defaultValue());
        assertEquals(locale.get(key).literal(), keyDefault.defaultValue());
        locale.send(key, playerMock);
        assertEquals(playerMock.nextMessage(), keyDefaultTranslated);
        locale.get(key).send(playerMock);
        assertEquals(playerMock.nextMessage(), keyDefaultTranslated);
    }

    @Test(dependsOnMethods = "testSavesAndLoadsDefaultKey")
    void testCustomKey() throws IOException {
        var localeConfig = new YamlConfiguration();
        localeConfig.set(key.path(), keyCustomValue);
        localeConfig.save(localeFile);
        assertNotEquals(locale.get(key).literal(), keyCustomValue);
        locale.send(key, playerMock);
        assertNotEquals(playerMock.nextMessage(), keyCustomTranslated);
        assertTrue(locale.load());
        assertEquals(locale.get(key).literal(), keyCustomValue);
        locale.send(key, playerMock);
        assertEquals(playerMock.nextMessage(), keyCustomTranslated);
    }
}
