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

package voidpointer.spigot.framework.localemodule.config;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import voidpointer.spigot.framework.localemodule.LocalizedMessage;

import java.util.Collection;

@Builder
@RequiredArgsConstructor
class LocalizedMessageBuilder implements LocalizedMessage {
    @Getter
    @NonNull
    protected String rawMessage;

    @Override public LocalizedMessage colorize() {
        rawMessage = ChatColor.translateAlternateColorCodes(ALTERNATIVE_COLOR_CODE, rawMessage);
        return this;
    }

    @Override public LocalizedMessage set(final String placeholder, final String replacement) {
        final String target = PLACEHOLDER_START + placeholder + PLACEHOLDER_END;
        final StringBuilder buffer = new StringBuilder(rawMessage);

        int occurrenceStart, occurrenceEnd;
        /* replace all target occurrences in buffer with replacement */
        while ((occurrenceStart = buffer.indexOf(target)) != -1) {
            occurrenceEnd = occurrenceStart + target.length();
            buffer.replace(occurrenceStart, occurrenceEnd, replacement);
        }
        rawMessage = buffer.toString();
        return this;
    }

    @Override public LocalizedMessage send(final CommandSender receiver) {
        receiver.sendMessage(rawMessage);
        return this;
    }

    @Override public LocalizedMessage send(final CommandSender... receivers) {
        for (CommandSender receiver : receivers)
            receiver.sendMessage(rawMessage);
        return this;
    }

    @Override public LocalizedMessage send(final Collection<? extends CommandSender> receivers) {
        for (CommandSender receiver : receivers)
            receiver.sendMessage(rawMessage);
        return this;
    }

    @Override public SpigotLocalizedMessage spigot() {
        return new SpigotLocalizedMessageBuilder(rawMessage);
    }

    class SpigotLocalizedMessageBuilder extends LocalizedMessageBuilder implements SpigotLocalizedMessage {
        SpigotLocalizedMessageBuilder(@NonNull final String rawMessage) {
            super(rawMessage);
        }

        @Override public LocalizedMessage sendActionBarMessage(final Player receiver) {
            receiver.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(rawMessage));
            return this;
        }

        @Override public LocalizedMessage sendActionBarMessage(final Player... receivers) {
            BaseComponent[] components = TextComponent.fromLegacyText(rawMessage);
            for (Player receiver : receivers) {
                receiver.spigot().sendMessage(ChatMessageType.ACTION_BAR, components);
            }
            return this;
        }

        @Override public LocalizedMessage sendActionBarMessage(final Collection<? extends Player> receivers) {
            BaseComponent[] components = TextComponent.fromLegacyText(rawMessage);
            for (Player receiver : receivers) {
                receiver.spigot().sendMessage(ChatMessageType.ACTION_BAR, components);
            }
            return this;
        }
    }
}
