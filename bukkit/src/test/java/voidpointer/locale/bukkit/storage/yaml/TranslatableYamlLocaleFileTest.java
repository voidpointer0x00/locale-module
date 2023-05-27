/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.bukkit.storage.yaml;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import voidpointer.locale.api.LocaleKey;
import voidpointer.locale.bukkit.BukkitLogger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.of;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TranslatableYamlLocaleFileTest {

    static void saveResource(final String path) {
        try (var in = Test.class.getClassLoader().getResourceAsStream(path)) {
            if (in == null)
                throw new RuntimeException("Resource not found: " + path);
            var res = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            for (int length; (length = in.read(buffer)) != -1; )
                res.write(buffer, 0, length);
            File file = new File(tempDataFolder, path);
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
            Files.writeString(file.toPath(), res.toString(UTF_8), WRITE, TRUNCATE_EXISTING);
        } catch (final IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    private static final LocaleKey localeKey = LocaleKey.of("msg", "hi");
    private static final LocaleKey newKey = LocaleKey.of("new", "key");
    private static File tempDataFolder;
    private static TranslatableYamlLocaleFile nonexistentLocaleFile;
    private static TranslatableYamlLocaleFile emptyLocaleFile;
    private static TranslatableYamlLocaleFile nonemptyLocaleFile;

    @BeforeAll
    static void setup(@TempDir File tempDataFolder) {
        TranslatableYamlLocaleFileTest.tempDataFolder = tempDataFolder;
        nonexistentLocaleFile = TranslatableYamlLocaleFile.builder()
                .log(new BukkitLogger(Logger.getAnonymousLogger(), () -> false))
                .dataFolder(tempDataFolder)
                .languageProvider(() -> "")
                .filenamePattern("ignore the exception")
                .saveDefaultTask(TranslatableYamlLocaleFileTest::saveResource)
                .build();
        emptyLocaleFile = TranslatableYamlLocaleFile.builder()
                .log(new BukkitLogger(Logger.getGlobal(), () -> true))
                .dataFolder(tempDataFolder)
                .languageProvider(() -> "")
                .filenamePattern("empty.yml")
                .saveDefaultTask(TranslatableYamlLocaleFileTest::saveResource)
                .build();
        nonemptyLocaleFile = TranslatableYamlLocaleFile.builder()
                .log(new BukkitLogger(Logger.getGlobal(), () -> true))
                .dataFolder(tempDataFolder)
                .languageProvider(() -> "")
                .filenamePattern("nonempty.yml")
                .saveDefaultTask(TranslatableYamlLocaleFileTest::saveResource)
                .build();
    }

    static Stream<Arguments> configFails() {
        return Stream.of(of(nonexistentLocaleFile), of(emptyLocaleFile));
    }

    @Order(0)
    @ParameterizedTest
    @MethodSource
    void configFails(LocaleFile localeFile) {
        assertThrowsExactly(IllegalStateException.class, localeFile::config);
    }

    @Order(1)
    @Test
    void loadEmpty() {
        assertTrue(emptyLocaleFile.load());
        assertDoesNotThrow(emptyLocaleFile::config);
        assertFalse(emptyLocaleFile.config().contains(localeKey.path()));
    }

    @Order(1)
    @Test
    void loadNonempty() {
        assertTrue(nonemptyLocaleFile.load());
        assertDoesNotThrow(nonemptyLocaleFile::config);
        assertTrue(nonemptyLocaleFile.config().contains(localeKey.path()));
    }

    @Test
    void loadAndUpdateDefaults() {
    }

    @Order(3)
    @Test
    void saveAddToEmpty() {
        assertFalse(emptyLocaleFile.config().contains(localeKey.path()));
        emptyLocaleFile.config().set(localeKey.path(), localeKey.defaultValue());
        assertTrue(emptyLocaleFile.config().contains(localeKey.path()));
        assertTrue(emptyLocaleFile.save());
        assertTrue(emptyLocaleFile.config().contains(localeKey.path()));
        assertEquals(localeKey.defaultValue(), emptyLocaleFile.config().getString(localeKey.path()));
    }
}
