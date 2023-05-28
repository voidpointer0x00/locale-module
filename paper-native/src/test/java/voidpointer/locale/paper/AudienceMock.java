/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.paper;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;

import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedDeque;

public class AudienceMock implements Audience {
    private final Deque<ComponentLike> messages = new ConcurrentLinkedDeque<>();

    @Override public void sendMessage(@NotNull Component message) { messages.add(message); }

    @Override public void sendMessage(@NotNull ComponentLike message) { messages.add(message); }

    /**
     * @throws NoSuchElementException if there are no more messages sent to this audience
     */
    public ComponentLike nextMessage() throws NoSuchElementException {
        return messages.pop();
    }
}
