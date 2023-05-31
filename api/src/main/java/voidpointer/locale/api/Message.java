/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * An immutable translated message.
 * Provides the API to send this message to audience, generally intended use
 * is when you would want or need to cache a certain message in order to avoid
 * a repeated translation process.
 *
 * <p>For example, there is the same, but complex message with server description
 * that would be sent to every pinging client. On a scale, using this immutable
 * and pre-processed instance would improve over-all performance for pings.</p>
 *
 * @param <T> a type of audience
 */
public interface Message<T> {
    /**
     * Sends this message to a given audience.
     *
     * @param audience the audience that will receive this message
     * @return the same instance of this message.
     */
    @NotNull Message<T> send(@Nullable T audience);

    /**
     * Sends this message to a given audience.
     *
     * @param audience the audience that will receive this message
     * @return the same instance of this message.
     */
    @NotNull Message<T> send(@NotNull Collection<? extends T> audience);

    /**
     * @implNote depending on the implementation and platform, this might involve
     *          serialization of this message if it had a more complex structure.
     * @return a string value of this message.
     */
    @NotNull String literal();
}
