/*
 *            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *
 * Copyright (C) 2022 Vasiliy Petukhov <void.pointer@ya.ru>
 *
 * Everyone is permitted to copy and distribute verbatim or modified
 * copies of this license document, and changing it is allowed as long
 * as the name is changed.
 *
 *            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *  0. You just DO WHAT THE FUCK YOU WANT TO.
 */

package voidpointer.spigot.framework.localemodule.annotation;

import be.seeseemelk.mockbukkit.MockBukkit;
import lombok.val;
import org.bukkit.configuration.ConfigurationSection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import voidpointer.spigot.framework.localemodule.annotation.target.InjectionTarget;
import voidpointer.spigot.framework.localemodule.config.LocaleFile;

import java.io.File;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static org.bukkit.configuration.file.YamlConfiguration.loadConfiguration;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static voidpointer.spigot.framework.localemodule.annotation.TestMessage.COOL;
import static voidpointer.spigot.framework.localemodule.annotation.TranslatedLocalePlugin.LOCALE_YML;
import static voidpointer.spigot.framework.localemodule.config.LocaleFile.LOCALE_FILENAME;

class LocaleAnnotationResolverTest {
    @BeforeAll static void setUp() {
        MockBukkit.mock();
    }

    @AfterAll static void tearUp() {
        MockBukkit.unmock();
    }

    @AfterEach void clearInjectionTarget() {
        InjectionTarget.locale = null;
    }

    @ParameterizedTest
    @MethodSource("mockLocaleFilePlugin")
    void testFileLocaleResolve(final LocaleFilePlugin plugin) {
        File pluginDescriptionFile = new File(plugin.getDataFolder(), LOCALE_FILENAME);
        assertFalse(pluginDescriptionFile.exists());
        assertTrue(LocaleAnnotationResolver.resolve(plugin));
        assertNotNull(LocaleFilePlugin.locale);
        assertNotNull(InjectionTarget.locale);
        assertTrue(pluginDescriptionFile.exists());
        val localeReader = new InputStreamReader(requireNonNull(plugin.getResource(LOCALE_FILENAME)));
        val expect = loadConfiguration(localeReader);
        assertTrue(isValidLocale(expect, loadConfiguration(pluginDescriptionFile)));
    }

    private boolean isValidLocale(ConfigurationSection expect, ConfigurationSection actual) {
        expect = expect.getConfigurationSection("messages");
        actual = actual.getConfigurationSection("messages");
        if ((expect == null) || (actual == null))
            return false;
        for (String key : expect.getKeys(false)) {
            if (!requireNonNull(expect.get(key)).equals(actual.get(key)))
                return false;
        }
        return requireNonNull(actual.getString(COOL.getPath())).equals(COOL.getDefaultMessage());
    }

    static Stream<Arguments> mockLocaleFilePlugin() {
        return Stream.of(arguments(MockBukkit.loadWith(LocaleFilePlugin.class, LocaleFilePlugin.PLUGIN_YML)));
    }

    @ParameterizedTest
    @MethodSource("mockTranslatedLocalePlugin")
    void testTranslatedLocaleResolve(final TranslatedLocalePlugin plugin) {
        File pluginDescriptionFile = new File(plugin.getDataFolder(), LOCALE_YML);
        assertFalse(pluginDescriptionFile.exists());
        assertTrue(LocaleAnnotationResolver.resolve(plugin));
        assertNotNull(TranslatedLocalePlugin.locale);
        assertNotNull(InjectionTarget.locale);
        assertTrue(pluginDescriptionFile.exists());
        val localeReader = new InputStreamReader(requireNonNull(plugin.getResource(LOCALE_YML)));
        val expect = loadConfiguration(localeReader);
        assertTrue(isValidLocale(expect, loadConfiguration(pluginDescriptionFile)));
    }

    static Stream<Arguments> mockTranslatedLocalePlugin() {
        return Stream.of(arguments(MockBukkit.loadWith(TranslatedLocalePlugin.class,
                TranslatedLocalePlugin.PLUGIN_YML)));
    }

    @ParameterizedTest
    @MethodSource("mockConfigurationSectionPlugin")
    void testConfigurationSectionLocaleResolve(final ConfigurationSectionPlugin plugin) {
        assertTrue(LocaleAnnotationResolver.resolve(plugin));
        assertNotNull(ConfigurationSectionPlugin.locale);
        assertNotNull(InjectionTarget.locale);
        ConfigurationSection messagesSection =
                plugin.getConfig().getConfigurationSection(LocaleFile.MESSAGES_PATH);
        assertNotNull(messagesSection);
        assertNotNull(messagesSection.getString(COOL.getPath()));
        assertEquals(COOL.getDefaultMessage(), messagesSection.getString(COOL.getPath()));
    }

    static Stream<Arguments> mockConfigurationSectionPlugin() {
        return Stream.of(arguments(MockBukkit.loadWith(ConfigurationSectionPlugin.class,
                ConfigurationSectionPlugin.PLUGIN_YML)));
    }
}
