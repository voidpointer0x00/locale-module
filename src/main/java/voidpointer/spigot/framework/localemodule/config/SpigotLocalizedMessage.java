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

import lombok.NonNull;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import voidpointer.spigot.framework.localemodule.LocalizedMessage;

import java.util.Collection;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

class SpigotLocalizedMessage implements LocalizedMessage {
    private static final Pattern EVENTS_PATTERN = Pattern.compile(
            "\\\\\\((?<target>.+)\\)" +
            "(((\\[(hover)\\{(?<htext>.+)}])?" +
            "(\\[(?<click>click\\.(?<caction>run|suggest|url|copy))\\\\%(?<ctext>.+)\\\\%]))" +
            "|(\\[(hover)\\{(?<ohtext>.+)}]))"
    );

    @NonNull private String rawMessage;
    private TextComponent[] cachedComponents;

    public SpigotLocalizedMessage(@NonNull final String rawMessage) {
        this.rawMessage = translateAlternateColorCodes(LocalizedMessage.ALTERNATIVE_COLOR_CODE, rawMessage);
    }

    @Override public @NonNull String getRawMessage() {
        return rawMessage;
    }

    @Override public LocalizedMessage set(final String placeholder, final String replacement) {
        cachedComponents = null;
        final String target = PLACEHOLDER_START + placeholder + PLACEHOLDER_END;
        final StringBuilder buffer = new StringBuilder(rawMessage);

        int occurrenceStart, occurrenceEnd;
        /* replace all target occurrences in buffer with replacement */
        while ((occurrenceStart = buffer.indexOf(target)) != -1) {
            occurrenceEnd = occurrenceStart + target.length();
            if (null == replacement) {
                buffer.delete(occurrenceStart, occurrenceEnd);
            } else {
                buffer.replace(occurrenceStart, occurrenceEnd, replacement);
            }
        }
        rawMessage = buffer.toString();
        return this;
    }

    @Override public LocalizedMessage send(final CommandSender receiver) {
        receiver.spigot().sendMessage(parseComponents(rawMessage));
        return this;
    }

    @Override public LocalizedMessage send(final CommandSender... receivers) {
        for (CommandSender receiver : receivers)
            send(receiver);
        return this;
    }

    @Override public LocalizedMessage send(final Collection<? extends CommandSender> receivers) {
        for (CommandSender receiver : receivers)
            send(receiver);
        return this;
    }

    @Override public LocalizedMessage sendActionBarMessage(final Player receiver) {
        receiver.spigot().sendMessage(ChatMessageType.ACTION_BAR, parseComponents(rawMessage));
        return this;
    }

    @Override public LocalizedMessage sendActionBarMessage(final Player... receivers) {
        for (Player receiver : receivers)
            send(receiver);
        return this;
    }

    @Override public LocalizedMessage sendActionBarMessage(final Collection<? extends Player> receivers) {
        for (Player receiver : receivers)
            send(receiver);
        return this;
    }

    private TextComponent[] parseComponents(final String message) {
        if (cachedComponents != null)
            return cachedComponents;
        final LinkedList<TextComponent> components = new LinkedList<>();

        final StringBuilder source = new StringBuilder(message);
        final Matcher matcher = EVENTS_PATTERN.matcher(source);
        while (matcher.find()) {
            if (matcher.start() > 0)
                components.add(new TextComponent(source.substring(0, matcher.start())));

            TextComponent target = new TextComponent(matcher.group("target"));
            target.setClickEvent(getClickEvent(matcher));
            target.setHoverEvent(getHoverEvent(matcher));
            components.add(target);

            source.delete(0, matcher.end());
            matcher.reset();
        }

        if (components.isEmpty())
            components.add(new TextComponent(message));

        TextComponent[] result = new TextComponent[components.size()];
        components.toArray(result);
        cachedComponents = result;
        components.clear(); /* though it doesn't mean much, at least it will take less space before GC */
        return result;
    }

    private HoverEvent getHoverEvent(final Matcher matcher) {
        final String hoverText = matcher.group("htext") == null ? matcher.group("ohtext") : matcher.group("htext");
        if (hoverText == null)
            return null;
        return new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(TextComponent.fromLegacyText(hoverText)));
    }

    private ClickEvent getClickEvent(final Matcher matcher) {
        final String actionName = matcher.group("caction");
        if (actionName == null)
            return null;
        return new ClickEvent(getAction(actionName), matcher.group("ctext"));
    }

    private ClickEvent.Action getAction(final String action) {
        switch (action) {
            case "run":
                return ClickEvent.Action.RUN_COMMAND;
            case "suggest":
                return ClickEvent.Action.SUGGEST_COMMAND;
            case "url":
                return ClickEvent.Action.OPEN_URL;
            case "copy":
                return ClickEvent.Action.COPY_TO_CLIPBOARD;
            default:
                throw new RuntimeException("Unknown click action: " + action);
        }
    }
}
