/*
 *            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *
 * Copyright (C) 2020 Vasiliy Petukhov <void.pointer@ya.ru>
 *
 * Everyone is permitted to copy and distribute verbatim or modified
 * copies of this license document, and changing it is allowed as long
 * as the name is changed.
 *
 *            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *  0. You just DO WHAT THE FUCK YOU WANT TO.
 */

package voidpointer.spigot.framework.localemodule;

public interface Locale {
    String MISSING_LOCALIZATION = "Missing localization for «%s» message, using default instead";

    void addDefaults(Message[] messages);

    LocalizedMessage localize(Message message);

    LocalizedMessage localize(String path, String defaultMessage);

    LocalizedMessage localizeColorized(Message message);

    LocalizedMessage localizeColorized(String path, String defaultMessage);
}
