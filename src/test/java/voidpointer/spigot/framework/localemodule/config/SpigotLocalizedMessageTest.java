/*
 *             DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *
 *  Copyright (C) 2022 Vasiliy Petukhov <void.pointer@ya.ru>
 *
 *  Everyone is permitted to copy and distribute verbatim or modified
 *  copies of this license document, and changing it is allowed as long
 *  as the name is changed.
 *
 *             DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *    TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *   0. You just DO WHAT THE FUCK YOU WANT TO.
 */

package voidpointer.spigot.framework.localemodule.config;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.ChatColor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class SpigotLocalizedMessageTest {
    static final String COLORFUL = "&cColorful message";
    static final String HOVER_CLICK_SUGGEST = "&eHere's \\(&csome)[hover{&6&lComplex message!}][click.suggest{kick " +
            "{player}}] &8<- hover :>";
    // there's no way to check if TextComponent events are being sent correctly yet
    static final String HOVER_CLICK_SUGGEST_VISIBLE = "§f§eHere's §f§csome§f §8<- hover :>";
    static final String PLAYER_NAME = "_voidpointer";

    static ServerMock serverMock;
    static PlayerMock playerMock;

    @BeforeAll
    static void mock() {
        serverMock = MockBukkit.mock();
        playerMock = new PlayerMock(serverMock, PLAYER_NAME);
    }

    @AfterAll
    static void unmock() {
        MockBukkit.unmock();
    }

    @ParameterizedTest
    @ValueSource(strings = {COLORFUL, HOVER_CLICK_SUGGEST})
    void testGetRawMessage(final String message) {
        SpigotLocalizedMessage spigotLocalizedMessage = new SpigotLocalizedMessage(message);
        assertEquals(color(message), spigotLocalizedMessage.getRawMessage());
    }

    static String color(final String raw) {
        return ChatColor.translateAlternateColorCodes('&', raw);
    }

    @ParameterizedTest
    @MethodSource("testSetProvider")
    void testSet(final String message, final String expected, final String placeholder, final String replacement) {
        SpigotLocalizedMessage spigotLocalizedMessage = new SpigotLocalizedMessage(message);
        assertEquals(expected, spigotLocalizedMessage.set(placeholder, replacement).getRawMessage());
    }

    static Stream<Arguments> testSetProvider() {
        return Stream.of(
                arguments(COLORFUL, color(COLORFUL), "player", PLAYER_NAME),
                arguments(HOVER_CLICK_SUGGEST, color(HOVER_CLICK_SUGGEST).replace("{player}", PLAYER_NAME),
                        "player", PLAYER_NAME)
        );
    }

    @ParameterizedTest
    @MethodSource("supplyTestRemoveComponents")
    void testRemoveComponents(final String message, final String expect) {
        SpigotLocalizedMessage msg = new SpigotLocalizedMessage(message);
        assertEquals(expect, msg.removeComponents(msg.getRawMessage()));
    }

    public static Stream<Arguments> supplyTestRemoveComponents() {
        return Stream.of(
                arguments("just text", "just text"),
                arguments("&1colorful &2text", "§1colorful §2text"),
                arguments("\\(&0colorful &4inner) [hover{hover}] [click.run{/nothing}]", "§0colorful §4inner"),
                arguments("\\(inner) [hover{hover}] [click.run{/nothing}]", "inner"),
                arguments("start \\(inner) [hover{hover}] [click.run{/nothing}]", "start inner"),
                arguments("\\(inner) [hover{hover}] [click.run{/nothing}] end", "inner end"),
                arguments("start \\(inner) [hover{hover}] [click.run{/nothing}] end", "start inner end"),
                arguments("\\(inner) [hover{hover}]", "inner"),
                arguments("start \\(inner) [hover{hover}]", "start inner"),
                arguments("\\(inner) [hover{hover}] end", "inner end"),
                arguments("start \\(inner) [hover{hover}] end", "start inner end"),
                arguments("\\(inner) [click.run{/nothing}]", "inner"),
                arguments("start \\(inner) [click.run{/nothing}]", "start inner"),
                arguments("\\(inner) [click.run{/nothing}] end", "inner end"),
                arguments("start \\(inner) [click.run{/nothing}] end", "start inner end")
        );
    }

    @ParameterizedTest
    @MethodSource("testSendProvider")
    void testSend(final String message, final String expected) {
        SpigotLocalizedMessage spigotLocalizedMessage = new SpigotLocalizedMessage(message);
        spigotLocalizedMessage.send(playerMock);

        String nextMessage = playerMock.nextMessage();
        assertNotNull(nextMessage);
        StringBuilder said = new StringBuilder(nextMessage);
        while ((nextMessage = playerMock.nextMessage()) != null)
            said.append(nextMessage);
        assertEquals(expected, said.toString());
    }

    static Stream<Arguments> testSendProvider() {
        return Stream.of(
                arguments(COLORFUL, "§f"+color(COLORFUL)),
                arguments(HOVER_CLICK_SUGGEST, HOVER_CLICK_SUGGEST_VISIBLE)
        );
    }
}