/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.bukkit;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class BukkitLoggerTest extends BukkitLogger {
    public BukkitLoggerTest() {
        super(null, null);
    }

    @Test(dataProvider = "provideForInsertPlaceholderLikeSlf4j")
    public void insertPlaceholderLikeSlf4j(String expected, String input, Object... replacements) {
        assertEquals(expected, insertPlaceholderLikeSlf4j(input, replacements));
    }

    @DataProvider
    private static Object[][] provideForInsertPlaceholderLikeSlf4j() {
        return new Object[][] {
                {"", "", null},
                {"", "", new Object[] {"hi", "bye", 1, new Object()}},
                {"{}", "{}", null},
                {"Hello, ", "{}, ", new String[] {"Hello", "world"}},
                {"Hello, world!", "{}, {}!", new Object[] {"Hello", "world"}},
                {"Hello, world!", "{}, {}!", new Object[] {"Hello", "world", "extra string"}},
                {"1", "{}", new Object[] {1, 2f, 3d}},
                {"1, 2.0", "{}, {}", new Object[] {1, 2f, 3d}},
                {"1, 2.0, 3.0", "{}, {}, {}", new Object[] {1, 2f, 3d}},
                {"1, null, 3.0", "{}, {}, {}", new Object[] {1, null, 3d}},
                {"null, 2.0, null", "{}, {}, {}", new Object[] {null, 2f, null}},
                {"1 == 1 -> true", "{} == {} -> {}", new Object[] {1, 1L, true, false}}
        };
    }
}