/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.locale.api;

import org.jetbrains.annotations.NotNull;

/**
 *  Provides a quick and easy way to get and/or send a translated message
 * to an audience.
 *
 * @implNote Certain implementations <i>might</i> add some caching mechanism for immutable
 *          messages.
 *
 * @param <T> audience type. For example, on Bukkit platform it would be CommandSender;
 *           for any Adventure platform — Audience.
 */
public interface Locale<T> {
    /**
     * @return a factory of {@link Placeholder} specific to the implementation of this
     *          {@link Locale}.
     *          For example, one implementation might support {@code {}} placeholders
     *          and the other would use {@code <>}; as well as different implementations
     *          might additionally provide different text decorations.
     */
    @NotNull PlaceholderFactory<T> placeholders();

    /**
     * Find a translation for the given key and immediately send it to the given audience.
     *
     * @param key       the message identifier to search for the translation.
     * @param audience  the audience that will receive the translated message.
     *                  
     * @see #send(LocaleKey, Object, Placeholder...)
     */
    void send(@NotNull LocaleKey key, @NotNull T audience);

    /**
     *  Finds a translation for a key, replaces placeholders with their replacements and
     * sends a resulting message to a given audience.
     *
     * @param key           the message identifier to search for the translation.
     * @param audience      the audience that will receive the resulting message.
     * @param placeholders  a set of placeholders to be processed within the message.
     *
     * @see #send(LocaleKey, Object)
     */
    void send(@NotNull LocaleKey key, @NotNull T audience, @NotNull Placeholder... placeholders);

    /**
     *  Finds a translation for a key and returns an appropriate {@link Message}
     * instance for it.
     *
     * @param key the message identifier to search for the translation.
     * @return the translated {@link Message} instance associated with the given key.
     */
    @NotNull Message<T> get(@NotNull LocaleKey key);

    /**
     *  Finds a translation for a key, processes given placeholders and return an
     * appropriate {@link Message} instance for that translation.
     *
     * @param key           the message identifier to search for the translation.
     * @param placeholders  a set of placeholders to be processed within the message.
     * @return the translated {@link Message} instance associated with the provided
     *      translation key and processed placeholders.
     */
    @NotNull Message<T> get(@NotNull LocaleKey key, @NotNull Placeholder... placeholders);

    /**
     *  Finds a translation for a key and gives an instance of {@link Message} that
     * will contain the translation as it is without any text decorations.
     *
     * @param key the message identifier to search for the translation.
     * @return the translated but not processed {@link Message} instance.
     */
    @NotNull Message<T> raw(@NotNull LocaleKey key);

    /**
     *  Finds a translation for a key and gives an instance of {@link Message} that
     * will contain the translation as it is without any text decorations, but with
     * placeholders replaced with their appropriate replacements and decorations
     * preserved.
     *
     * @param key           the message identifier to search for the translation.
     * @param placeholders  a set of placeholders to be processed within the message,
     *                      all text decorations will still be preserved.
     * @return the translated but not processed {@link Message} instance, but with
     *          processed placeholders.
     */
    @NotNull Message<T> raw(@NotNull LocaleKey key, @NotNull Placeholder... placeholders);

    /**
     * Gets the <i>back-end</i> storage that this locale is using.
     *
     * @return {@link LocaleStorage} instance of a storage that this
     *          locale instance is using to translate messages.
     */
    @NotNull LocaleStorage storage();
}
