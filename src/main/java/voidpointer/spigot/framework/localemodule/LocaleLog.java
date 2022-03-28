/*
 *            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *
 * Copyright (C) 2022 Vasiliy Petukhov <void.pointer@ya.ru>
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

import java.util.logging.Level;

public interface LocaleLog extends Locale {
    /** {@String} message log methods */

    /** @see java.util.logging.Logger#log(Level, String) */
    void log(Level level, String message);
    /** @see java.util.logging.Logger#log(Level, String, Object) */
    void log(Level level, String message, Object obj);
    /** @see java.util.logging.Logger#log(Level, String, Object[]) */
    void log(Level level, String message, Object... objects);
    /** @see java.util.logging.Logger#log(Level, String, Throwable) */
    void log(Level level, String message, Throwable thrown);

    /** Shortcut for {@code #log(Level#INFO, String)} */
    void info(String message);
    /** Shortcut for {@code #log(Level#INFO, String, Object)} */
    void info(String message, Object obj);
    /** Shortcut for {@code #log(Level#INFO, String, Object...)} */
    void info(String message, Object... objects);
    /** Shortcut for {@code #log(Level#INFO, String, Throwable)} */
    void info(String message, Throwable thrown);

    /** Shortcut for {@code #log(Level#WARNING, String, String)} */
    void warn(String message);
    /** Shortcut for {@code #log(Level#WARNING, String, Object)} */
    void warn(String message, Object obj);
    /** Shortcut for {@code #log(Level#WARNING, String, Object...)} */
    void warn(String message, Object... objects);
    /** Shortcut for {@code #log(Level#WARNING, String, Throwable)} */
    void warn(String message, Throwable thrown);

    /** Shortcut for {@code #log(Level#SEVERE, String)} */
    void severe(String message);
    /** Shortcut for {@code #log(Level#SEVERE, String, Object)} */
    void severe(String message, Object obj);
    /** Shortcut for {@code #log(Level#SEVERE, String, Object...)} */
    void severe(String message, Object... objects);
    /** Shortcut for {@code #log(Level#SEVERE, String, Throwable)} */
    void severe(String message, Throwable thrown);

    /** {@link Message} message log methods */

    /** @see java.util.logging.Logger#log(Level, String) */
    void log(Level level, Message message);
    /** @see java.util.logging.Logger#log(Level, String, Object) */
    void log(Level level, Message message, Object obj);
    /** @see java.util.logging.Logger#log(Level, String, Object[]) */
    void log(Level level, Message message, Object... objects);
    /** @see java.util.logging.Logger#log(Level, String, Throwable) */
    void log(Level level, Message message, Throwable thrown);

    /** Shortcut for {@code #log(Level#INFO, Message)} */
    void info(Message message);
    /** Shortcut for {@code #log(Level#INFO, Message, Object)} */
    void info(Message message, Object obj);
    /** Shortcut for {@code #log(Level#INFO, Message, Object...)} */
    void info(Message message, Object... objects);
    /** Shortcut for {@code #log(Level#INFO, Message, Throwable)} */
    void info(Message message, Throwable thrown);

    /** Shortcut for {@code #log(Level#WARNING, Message)} */
    void warn(Message message);
    /** Shortcut for {@code #log(Level#WARNING, Message, Object)} */
    void warn(Message message, Object obj);
    /** Shortcut for {@code #log(Level#WARNING, Message, Object...)} */
    void warn(Message message, Object... objects);
    /** Shortcut for {@code #log(Level#WARNING, Message, Throwable)} */
    void warn(Message message, Throwable thrown);

    /** Shortcut for {@code #log(Level#SEVERE, Message)} */
    void severe(Message message);
    /** Shortcut for {@code #log(Level#SEVERE, Message, Object)} */
    void severe(Message message, Object obj);
    /** Shortcut for {@code #log(Level#SEVERE, Message, Object...)} */
    void severe(Message message, Object... objects);
    /** Shortcut for {@code #log(Level#SEVERE, Message, Throwable)} */
    void severe(Message message, Throwable thrown);
}
