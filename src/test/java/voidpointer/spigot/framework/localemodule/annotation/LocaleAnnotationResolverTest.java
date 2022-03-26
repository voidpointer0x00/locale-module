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
import org.bukkit.configuration.ConfigurationSection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import voidpointer.spigot.framework.localemodule.annotation.target.InjectionTarget;
import voidpointer.spigot.framework.localemodule.config.LocaleFileConfiguration;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

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
        InjectionTarget injectTarget = new InjectionTarget();
        assertTrue(LocaleAnnotationResolver.resolve(plugin));
        assertNotNull(plugin.locale);
        assertNotNull(injectTarget.locale);
        // TODO: ensure that locale file was created and it's valid
    }

    static Stream<Arguments> mockLocaleFilePlugin() {
        return Stream.of(arguments(MockBukkit.loadWith(LocaleFilePlugin.class, LocaleFilePlugin.PLUGIN_YML)));
    }

    @ParameterizedTest
    @MethodSource("mockTranslatedLocalePlugin")
    void testTranslatedLocaleResolve(final TranslatedLocalePlugin plugin) {
        InjectionTarget injectTarget = new InjectionTarget();
        assertTrue(LocaleAnnotationResolver.resolve(plugin));
        assertNotNull(plugin.locale);
        assertNotNull(injectTarget.locale);
        // TODO: ensure that locale file was created and it's valid
    }

    static Stream<Arguments> mockTranslatedLocalePlugin() {
        return Stream.of(arguments(MockBukkit.loadWith(TranslatedLocalePlugin.class,
                TranslatedLocalePlugin.PLUGIN_YML)));
    }

    @ParameterizedTest
    @MethodSource("mockConfigurationSectionPlugin")
    void testConfigurationSectionLocaleResolve(final ConfigurationSectionPlugin plugin) {
        InjectionTarget injectTarget = new InjectionTarget();
        assertTrue(LocaleAnnotationResolver.resolve(plugin));
        assertNotNull(plugin.locale);
        assertNotNull(injectTarget.locale);
        ConfigurationSection messagesSection =
                plugin.getConfig().getConfigurationSection(LocaleFileConfiguration.MESSAGES_PATH);
        assertNotNull(messagesSection);
        assertNotNull(messagesSection.getString(TestMessage.COOL.getPath()));
        assertEquals(TestMessage.COOL.getDefaultMessage(), messagesSection.getString(TestMessage.COOL.getPath()));
    }

    static Stream<Arguments> mockConfigurationSectionPlugin() {
        return Stream.of(arguments(MockBukkit.loadWith(ConfigurationSectionPlugin.class,
                ConfigurationSectionPlugin.PLUGIN_YML)));
    }
}
