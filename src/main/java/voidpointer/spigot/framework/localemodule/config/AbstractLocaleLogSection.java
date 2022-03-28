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

package voidpointer.spigot.framework.localemodule.config;

import lombok.NoArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import voidpointer.spigot.framework.localemodule.LocaleLog;
import voidpointer.spigot.framework.localemodule.Message;

import java.util.logging.Level;

@NoArgsConstructor
public class AbstractLocaleLogSection extends AbstractLocaleSection implements LocaleLog {
    public AbstractLocaleLogSection(final Plugin plugin, final ConfigurationSection config) {
        super(plugin, config);
    }

    @Override public void log(final Level level, final String message) {
        getPlugin().getLogger().log(level, message);
    }

    @Override public void log(final Level level, final String message, final Object obj) {
        getPlugin().getLogger().log(level, message, obj);
    }

    @Override public void log(final Level level, final String message, final Object... objects) {
        getPlugin().getLogger().log(level, message, objects);
    }

    @Override public void log(final Level level, final String message, final Throwable thrown) {
        getPlugin().getLogger().log(level, message, thrown);
    }

    @Override public void info(final String message) {
        log(Level.INFO, message);
    }

    @Override public void info(final String message, final Object obj) {
        log(Level.INFO, message, obj);
    }

    @Override public void info(final String message, final Object... objects) {
        log(Level.INFO, message, objects);
    }

    @Override public void info(final String message, final Throwable thrown) {
        log(Level.INFO, message, thrown);
    }

    @Override public void warn(final String message) {
        log(Level.WARNING, message);
    }

    @Override public void warn(final String message, final Object obj) {
        log(Level.WARNING, message, obj);
    }

    @Override public void warn(final String message, final Object... objects) {
        log(Level.WARNING, message, objects);
    }

    @Override public void warn(final String message, final Throwable thrown) {
        log(Level.WARNING, message, thrown);
    }

    @Override public void severe(final String message) {
        log(Level.SEVERE, message);
    }

    @Override public void severe(final String message, final Object obj) {
        log(Level.SEVERE, message, obj);
    }

    @Override public void severe(final String message, final Object... objects) {
        log(Level.SEVERE, message, objects);
    }

    @Override public void severe(final String message, final Throwable thrown) {
        log(Level.SEVERE, message, thrown);
    }

    @Override public void log(final Level level, final Message message) {
        log(level, localize(message).getRawMessage());
    }

    @Override public void log(final Level level, final Message message, final Object obj) {
        log(level, localize(message).getRawMessage(), obj);
    }

    @Override public void log(final Level level, final Message message, final Object... objects) {
        log(level, localize(message).getRawMessage(), objects);
    }

    @Override public void log(final Level level, final Message message, final Throwable thrown) {
        log(level, localize(message).getRawMessage(), thrown);
    }

    @Override public void info(final Message message) {
        log(Level.INFO, localize(message).getRawMessage());
    }

    @Override public void info(final Message message, final Object obj) {
        log(Level.INFO, localize(message).getRawMessage(), obj);
    }

    @Override public void info(final Message message, final Object... objects) {
        log(Level.INFO, localize(message).getRawMessage(), objects);
    }

    @Override public void info(final Message message, final Throwable thrown) {
        log(Level.INFO, localize(message).getRawMessage(), thrown);
    }

    @Override public void warn(final Message message) {
        log(Level.WARNING, localize(message).getRawMessage());
    }

    @Override public void warn(final Message message, final Object obj) {
        log(Level.WARNING, localize(message).getRawMessage(), obj);
    }

    @Override public void warn(final Message message, final Object... objects) {
        log(Level.WARNING, localize(message).getRawMessage(), objects);
    }

    @Override public void warn(final Message message, final Throwable thrown) {
        log(Level.WARNING, localize(message).getRawMessage(), thrown);
    }

    @Override public void severe(final Message message) {
        log(Level.SEVERE, localize(message).getRawMessage());
    }

    @Override public void severe(final Message message, final Object obj) {
        log(Level.SEVERE, localize(message).getRawMessage(), obj);
    }

    @Override public void severe(final Message message, final Object... objects) {
        log(Level.SEVERE, localize(message).getRawMessage(), objects);
    }

    @Override public void severe(final Message message, final Throwable thrown) {
        log(Level.SEVERE, localize(message).getRawMessage(), thrown);
    }
}
