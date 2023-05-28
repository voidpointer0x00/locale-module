/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.paper;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import voidpointer.locale.api.LocaleKey;
import voidpointer.locale.api.Log;
import voidpointer.locale.api.Placeholder;
import voidpointer.locale.bukkit.BukkitLogger;
import voidpointer.locale.bukkit.storage.yaml.TranslatableYamlLocaleFile;
import voidpointer.locale.bukkit.storage.yaml.YamlLocaleStorage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.event.ClickEvent.Action.RUN_COMMAND;
import static net.kyori.adventure.text.event.ClickEvent.clickEvent;
import static net.kyori.adventure.text.event.ClickEvent.runCommand;
import static net.kyori.adventure.text.format.NamedTextColor.AQUA;
import static net.kyori.adventure.text.format.NamedTextColor.GOLD;
import static net.kyori.adventure.text.format.TextDecoration.UNDERLINED;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static voidpointer.locale.api.LocaleKey.of;
import static voidpointer.locale.bukkit.storage.yaml.TranslatableYamlLocaleFile.DEFAULT_FILENAME_PATTERN;

public class NativePaperLocaleTest {
    public static Placeholder EMPTY_PLACEHOLDER = Placeholder.of("", "");

    static File tmp;
    static NativePaperLocale locale;

    @BeforeClass
    static void setup() throws IOException {
        tmp = Files.createTempDirectory("native-paper-locale-test").toFile();
        Log log = new BukkitLogger(Logger.getGlobal(), () -> false);
        locale = new NativePaperLocale(new YamlLocaleStorage(log, TranslatableYamlLocaleFile.builder()
                .filenamePattern(DEFAULT_FILENAME_PATTERN)
                .log(log)
                .languageProvider(() -> "")
                .saveDefaultTask(path -> {})
                .dataFolder(tmp)
                .build()));
    }

    @Test
    public void testLoad() {
        assertTrue(locale.load());
    }

    @DataProvider
    public static Object[][] testGet() {
        return new Object[][] {
                {of("k", "No style"), text("No style")},
                // not supported
                // {of("k", "&7Legacy &x&a&a&f&f&0&0styling"), parsed("§7Legacy §x§a§a§f§f§0§0styling")},
                {of("k", "<aqua>Modern styles"), text("Modern styles").color(AQUA)},
                {of("k", "<click:run_command:/cmd>With click</click>"), text("With click").clickEvent(clickEvent(RUN_COMMAND, "/cmd"))}
        };
    }

    @Test(dependsOnMethods = "testLoad", dataProvider = "testGet")
    public void testGet(LocaleKey key, Component expected) {
        assertEquals(locale.get(key).component(), expected);
    }

    @DataProvider
    public static Object[][] testGetWithPlaceholders() {
        return new Object[][] {
                {of("k", "No placeholders"), text("No placeholders"), Placeholder.of("", "")},
                {of("k", "<one>"), text("One"), Placeholder.of("one", "One")},
                {of("k", "<one>"), text("One"), ComponentPlaceholder.component("one", text("One"))},
                {of("k", "<one>"), text("One"), ComponentPlaceholder.parsed("one", "One")},
                {of("k", "<one>"), text("One"), ComponentPlaceholder.unparsed("one", "One")},
                {of("k", "<one>Hey<two>"), text("OneHey").append(text("Two").color(AQUA)),
                        Placeholder.of("one", "One"), ComponentPlaceholder.parsed("two", "<aqua>Two")}
        };
    }

    @Test(dependsOnMethods = "testLoad", dataProvider = "testGetWithPlaceholders")
    public void testGetWithPlaceholders(LocaleKey key, Component expected, Placeholder... placeholders) {
        assertEquals(locale.get(key, placeholders).component(), expected);
    }

    @DataProvider
    public static Object[][] testGetWithResolvers() {
        return new Object[][] {
                {of("k", "No placeholders"), text("No placeholders"),
                        net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.unparsed("", "")},
                {of("k", "<one>"), text("One"),
                        net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.unparsed("one", "One")},
                {of("k", "<one>"), text("One"),
                        net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.parsed("one", "One")},
                {of("k", "<one>"), text("One"),
                        net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.component("one", text("One"))},
                {of("k", "<one>Hey<two>"), text("OneHey").append(text("Two").color(AQUA)),
                        net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.unparsed("one", "One"),
                        net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.parsed("two", "<aqua>Two")}
        };
    }

    @Test(dependsOnMethods = "testLoad", dataProvider = "testGetWithResolvers")
    public void testGetWithResolvers(LocaleKey key, Component expected, TagResolver... resolvers) {
        assertEquals(locale.get(key, resolvers).component(), expected);
    }

    @DataProvider
    public static Object[][] testRaw() {
        return new Object[][] {
                {of("k", ""), text("")},
                {of("k", "Nothing"), text("Nothing")},
                {of("k", "&7Do not throw"), text("&7Do not throw")},
                {of("k", "<aqua>Unparsed"), text("<aqua>Unparsed")},
                {of("k", "<click:run_command:/cmd>No cmd</click>"), text("<click:run_command:/cmd>No cmd</click>")}
        };
    }

    @Test(dependsOnMethods = "testLoad", dataProvider = "testRaw")
    public void testRaw(LocaleKey key, Component expected) {
        assertEquals(locale.raw(key).component(), expected);
    }

    @DataProvider
    public static Object[][] testRawWithPlaceholders() {
        return new Object[][] {
                {of("k", "&7Do not throw"), text("&7Do not throw"), EMPTY_PLACEHOLDER},
                {of("k", "<aqua>Unparsed"), text("<aqua>Unparsed"), EMPTY_PLACEHOLDER},
                {of("k", "<click:run_command:/cmd>No cmd</click>"), text("<click:run_command:/cmd>No cmd</click>"),
                        EMPTY_PLACEHOLDER,
                },
                {of("k", "<aqua><name>"), text("<aqua>Name"), Placeholder.of("name", "Name")},
                {of("k", "<aqua><name><hehe>"), text("<aqua>Name He-he"), ComponentPlaceholder.unparsed("name", "Name"),
                        ComponentPlaceholder.unparsed("hehe", " He-he")},
                {of("k", "<aqua><name>"), text("<aqua>").append(text("Name").color(GOLD)),
                        ComponentPlaceholder.parsed("name", "<gold>Name")},
                {of("k", "<aqua><name>"), text("<aqua>").append(text("Name").color(GOLD)),
                        ComponentPlaceholder.component("name", text("Name").color(GOLD))}
        };
    }

    @Test(dependsOnMethods = "testLoad", dataProvider = "testRawWithPlaceholders")
    public void testRawWithPlaceholders(LocaleKey key, Component expected, Placeholder... placeholders) {
        assertEquals(locale.raw(key, placeholders).component(), expected);
    }

    @DataProvider
    public static Object[][] testRawWithResolvers() {
        return new Object[][] {
                {of("k", "&7Do not throw"), text("&7Do not throw"),
                        net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.unparsed("", "")},
                {of("k", "<aqua>Unparsed"), text("<aqua>Unparsed"),
                        net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.unparsed("", "")},
                {of("k", "<click:run_command:/cmd>No cmd</click>"), text("<click:run_command:/cmd>No cmd</click>"),
                        net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.unparsed("", ""),
                },
                {of("k", "<aqua><name>"), text("<aqua>Name"),
                        net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.unparsed("name", "Name")},
                {of("k", "<aqua><name><hehe>"), text("<aqua>Name He-he"),
                        net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.unparsed("name", "Name"),
                        net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.unparsed("hehe", " He-he")},
                {of("k", "<aqua><name>"), text("<aqua><gold>Name"),
                        net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.parsed("name", "<gold>Name")},
                {of("k", "<aqua><name>"), text("<aqua>").append(text("Name").color(GOLD)),
                        net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.component("name", text("Name").color(GOLD))}
        };
    }

    @Test(dependsOnMethods = "testLoad", dataProvider = "testRawWithResolvers")
    public void testRawWithResolvers(LocaleKey key, Component expected, TagResolver... resolvers) {
        assertEquals(locale.raw(key, resolvers).component(), expected);
    }

    @DataProvider
    public static Object[][] testSend() {
        AudienceMock audienceMock = new AudienceMock();
        return new Object[][] {
                {audienceMock, of("k", ""), text("")},
                {audienceMock, of("k", "Just a message"), text("Just a message")},
                {audienceMock, of("k", "<aqua>"), text().color(AQUA).build()},
                {audienceMock, of("k", "<aqua><u><click:run_command:/cmd>click</click></u><aqua>"),
                        text().color(AQUA).append(text("click").decorate(UNDERLINED).clickEvent(runCommand("/cmd")))
                                .build()}
        };
    }

    @Test(dependsOnMethods = "testLoad", dataProvider = "testSend")
    public void testSend(AudienceMock audienceMock, LocaleKey key, ComponentLike expected) {
        locale.send(key, audienceMock);
        assertEquals(audienceMock.nextMessage(), expected);
    }

    @DataProvider
    public static Object[][] testSendWithResolvers() {
        AudienceMock audienceMock = new AudienceMock();
        return new Object[][] {
                {audienceMock, of("k", "<name>"), text("Name").color(AQUA),
                        net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.parsed("name", "<aqua>Name</aqua>")},
                {audienceMock, of("k", "Just <name> a <name> message"), text("Just ")
                        .append(text("Name").color(AQUA)).append(text(" a ")).append(text("Name").color(AQUA))
                        .append(text(" message")),
                        net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.parsed("name", "<aqua>Name</aqua>")},
                {audienceMock, of("k", "<name> text <aqua><u><click:run_command:/cmd><name>click</click></u><aqua>"),
                        text().append(text("Name").color(AQUA)).append(text(" text ")).append(
                                text().color(AQUA).append(
                                        text().clickEvent(runCommand("/cmd"))
                                        .decorate(UNDERLINED)
                                        .append(text("Name").color(AQUA))
                                        .append(text("click"))
                                )
                        ).build(),
                        net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.parsed("name", "<aqua>Name</aqua>")}
        };
    }

    @Test(dependsOnMethods = "testLoad", dataProvider = "testSendWithResolvers")
    public void testSendWithResolvers(
            AudienceMock audienceMock, LocaleKey key, ComponentLike expected, TagResolver... resolvers) {
        locale.send(key, audienceMock, resolvers);
        assertEquals(audienceMock.nextMessage(), expected);
    }
}