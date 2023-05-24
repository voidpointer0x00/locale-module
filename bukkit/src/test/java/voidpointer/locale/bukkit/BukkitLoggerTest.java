/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.bukkit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class BukkitLoggerTest extends BukkitLogger {
    public BukkitLoggerTest() {
        super(null, null);
    }

    @DisplayName("Should insert replacements into {} placeholders")
    @ParameterizedTest
    @MethodSource("provideForInsertPlaceholderLikeSlf4j")
    void insertPlaceholderLikeSlf4j(String expected, String input, Object... replacements) {
        Assertions.assertEquals(expected, insertPlaceholderLikeSlf4j(input, replacements));
    }

    private static Stream<Arguments> provideForInsertPlaceholderLikeSlf4j() {
        return Stream.of(
                Arguments.of("", "", null),
                Arguments.of("", "", null),
                Arguments.of("", "", new Object[] {"hi", "bye", 1, new Object()}),
                Arguments.of("{}", "{}", null),
                Arguments.of("Hello, ", "{}, ", new String[] {"Hello", "world"}),
                Arguments.of("Hello, world!", "{}, {}!", new Object[] {"Hello", "world"}),
                Arguments.of("Hello, world!", "{}, {}!", new Object[] {"Hello", "world", "extra string"}),
                Arguments.of("1", "{}", new Object[] {1, 2f, 3d}),
                Arguments.of("1, 2.0", "{}, {}", new Object[] {1, 2f, 3d}),
                Arguments.of("1, 2.0, 3.0", "{}, {}, {}", new Object[] {1, 2f, 3d}),
                Arguments.of("1, null, 3.0", "{}, {}, {}", new Object[] {1, null, 3d}),
                Arguments.of("null, 2.0, null", "{}, {}, {}", new Object[] {null, 2f, null}),
                Arguments.of("1 == 1 -> true", "{} == {} -> {}", new Object[] {1, 1L, true, false})
        );
    }
}