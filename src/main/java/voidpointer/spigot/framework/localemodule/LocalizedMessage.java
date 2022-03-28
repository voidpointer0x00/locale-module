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

import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.logging.Level;

public interface LocalizedMessage {
    char ALTERNATIVE_COLOR_CODE = '&';
    char PLACEHOLDER_START = '{';
    char PLACEHOLDER_END = '}';

    String getRawMessage();

    LocalizedMessage set(String placeholder, String replacement);

    LocalizedMessage send(CommandSender receiver);

    LocalizedMessage send(CommandSender... receivers);

    LocalizedMessage send(Collection<? extends CommandSender> receivers);

    LocalizedMessage sendActionBar(CommandSender receivers);

    LocalizedMessage sendActionBar(CommandSender... receivers);

    LocalizedMessage sendActionBar(Collection<CommandSender> receivers);

    LocalizedMessage log(Level level);

    LocalizedMessage log(Level level, Object object);

    LocalizedMessage log(Level level, Object[] objects);

    LocalizedMessage log(Level level, Throwable thrown);
}
