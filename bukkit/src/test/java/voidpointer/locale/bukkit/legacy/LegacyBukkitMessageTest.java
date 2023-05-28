/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.bukkit.legacy;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collection;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;


public class LegacyBukkitMessageTest {
    static PlayerMock mockedSingleReceiver;
    static Collection<? extends PlayerMock> mockedMultipleReceivers;
    static PlayerMock[] receiversArray;

    @BeforeClass
    static void setUp() {
        ServerMock mock = MockBukkit.getOrCreateMock();
        mockedSingleReceiver = mock.addPlayer();
        mock.addPlayer();
        mock.addPlayer();
        mock.addPlayer();
        mockedMultipleReceivers = mock.getOnlinePlayers();
        receiversArray = new PlayerMock[mockedMultipleReceivers.size()];
        mockedMultipleReceivers.toArray(receiversArray);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @BeforeMethod
    void clearMessages() {
        while (mockedSingleReceiver.nextMessage() != null)
            ;
        for (var mockedMultipleReceiver : mockedMultipleReceivers)
            while (mockedMultipleReceiver.nextMessage() != null)
                ;
        for (var receiver : receiversArray)
            while (receiver.nextMessage() != null)
                ;
    }

    @DataProvider
    public static Object[][] supplyMessages() {
        return new Object[][] {
                {"", ""}, {" .", " ."}, {". ", ". "},
                {"&", "&"}, {" &", " &"}, {"& ", "& "},
                {"&&&", "&&&"}, {"§a", "&a"}, {"§6 H§5e§4l§3l§2o§1! ", "&6 H&5e&4l&3l&2o&1! "}
        };
    }

    @Test(dataProvider = "supplyMessages")
    public void testSingleSend(String expected, String message) {
        new LegacyBukkitMessage(message).send(mockedSingleReceiver);
        assertEquals(expected, mockedSingleReceiver.nextMessage());
        assertNull(mockedSingleReceiver.nextMessage(), "The message was sent multiple times!");
    }

    @Test(dataProvider = "supplyMessages")
    public void testSingleSendForAll(String expected, String message) {
        var legacyBukkitMessage = new LegacyBukkitMessage(message);
        for (var mockedMultipleReceiver : mockedMultipleReceivers) {
            legacyBukkitMessage.send(mockedMultipleReceiver);
            assertEquals(expected, mockedMultipleReceiver.nextMessage());
            assertNull(mockedMultipleReceiver.nextMessage(), "The message was sent multiple times!");
        }
    }


    @Test(dataProvider = "supplyMessages")
    public void testSend(String expected, String message) {
        var legacyBukkitMessage = new LegacyBukkitMessage(message);
        legacyBukkitMessage.send(mockedMultipleReceivers);
        for (var mockedMultipleReceiver : mockedMultipleReceivers) {
            assertEquals(expected, mockedMultipleReceiver.nextMessage());
            assertNull(mockedMultipleReceiver.nextMessage(), "The message was sent multiple times!");
        }
    }

    @Test(dataProvider = "supplyMessages")
    public void testSend1(String expected, String message) {
        new LegacyBukkitMessage(message).send(receiversArray);
        for (var receiver : receiversArray) {
            assertEquals(expected, receiver.nextMessage());
            assertNull(receiver.nextMessage(), "The message was sent multiple times!");
        }
    }

    @Test(dataProvider = "supplyMessages")
    public void literal(String ignore, String literal) {
        assertEquals(literal, new LegacyBukkitMessage(literal).literal());
    }
}