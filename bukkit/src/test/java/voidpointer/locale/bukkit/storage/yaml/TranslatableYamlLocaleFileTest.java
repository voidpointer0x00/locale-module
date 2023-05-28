/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.bukkit.storage.yaml;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import voidpointer.locale.api.LocaleKey;
import voidpointer.locale.bukkit.BukkitLogger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;
import static org.testng.Assert.*;

public class TranslatableYamlLocaleFileTest {

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

    @BeforeClass
    static void setup() throws IOException {
        TranslatableYamlLocaleFileTest.tempDataFolder = Files.createTempDirectory("native-paper-locale-test").toFile();
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

    @AfterClass
    static void deleteTmp() {
        //noinspection ResultOfMethodCallIgnored
        tempDataFolder.delete();
    }

    @DataProvider
    public static Object[][] configFails() {
        return new Object[][] { {nonexistentLocaleFile}, {nonemptyLocaleFile} };
    }

    @Test(dataProvider = "configFails")
    public void configFails(LocaleFile localeFile) {
        assertThrows(IllegalStateException.class, localeFile::config);
    }

    @Test(dependsOnMethods="configFails")
    public void loadEmpty() {
        assertTrue(emptyLocaleFile.load());
        //noinspection ResultOfMethodCallIgnored
        emptyLocaleFile.config(); /* doesn't throw */
        assertFalse(emptyLocaleFile.config().contains(localeKey.path()));
    }

    @Test(dependsOnMethods = "loadEmpty")
    public void loadNonempty() {
        assertTrue(nonemptyLocaleFile.load());
        //noinspection ResultOfMethodCallIgnored
        nonemptyLocaleFile.config(); /* doesn't throw */
        assertTrue(nonemptyLocaleFile.config().contains(localeKey.path()));
    }

    @Test(dependsOnMethods = "loadNonempty")
    public void saveAddToEmpty() {
        assertFalse(emptyLocaleFile.config().contains(localeKey.path()));
        emptyLocaleFile.config().set(localeKey.path(), localeKey.defaultValue());
        assertTrue(emptyLocaleFile.config().contains(localeKey.path()));
        assertTrue(emptyLocaleFile.save());
        assertTrue(emptyLocaleFile.config().contains(localeKey.path()));
        assertEquals(localeKey.defaultValue(), emptyLocaleFile.config().getString(localeKey.path()));
    }
}
