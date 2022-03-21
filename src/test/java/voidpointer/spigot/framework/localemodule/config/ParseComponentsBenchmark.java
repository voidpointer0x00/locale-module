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

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations=1, time=10)
@Measurement(iterations=3, time=5)
@State(Scope.Benchmark)
@Fork(1)
public class ParseComponentsBenchmark {
    private String message = "&eДобро пожаловать \\(&cна хуй)[hover{&6&lFuck yourself}][click" +
            ".suggest\\%command\\%]";
    private final Pattern EVENTS_PATTERN = Pattern.compile(
            "\\\\\\((?<target>.+)\\)" +
                    "(((\\[(hover)\\{(?<htext>.+)}])?" +
                    "(\\[(?<click>click\\.(?<caction>run|suggest|url|copy))\\\\%(?<ctext>.+)\\\\%]))" +
                    "|(\\[(hover)\\{(?<ohtext>.+)}]))"
    );

    private static LinkedList<BaseComponent> components = new LinkedList<>();

    @Setup(Level.Invocation)
    public void setupComponents() {
        components.clear();
    }

    /*
     * On my PC:
     * Benchmark                                  Mode  Cnt       Score       Error  Units
     * ParseComponentsBenchmark.parseComponents  thrpt    6  437499.218 ± 13482.077  ops/s
     *      0.004571638% of MC tick time per operation (228 581 nanos)
     *
     * I was afraid regular expressions are going to considerably slow down the whole
     *  process , but it turned out that the method executes faster that I anticipated.
     */

    @Benchmark
    public void parseComponents() {
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
    }

    private HoverEvent getHoverEvent(final Matcher matcher) {
        final String hoverText = matcher.group("htext") == null ? matcher.group("ohtext") : matcher.group("htext");
        if (hoverText == null)
            return null;
        return new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hoverText));
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
