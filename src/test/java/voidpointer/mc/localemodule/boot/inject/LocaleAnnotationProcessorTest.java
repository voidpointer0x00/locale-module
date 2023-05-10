/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.boot.inject;

import org.mockito.Mockito;
import org.testng.annotations.Test;
import voidpointer.mc.localemodule.Locale;
import voidpointer.mc.localemodule.Log;
import voidpointer.mc.localemodule.PluginLocale;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertSame;

@Test
public class LocaleAnnotationProcessorTest {

    @Test(description = "Tests whether annotation processor injects the right locales")
    public void testInjects() {
        class NamedTestTarget {
            static final String JUST_LOCALE_PATH = "just-locale";
            static final String PLUGIN_LOCALE_PATH = "plugin-locale";
            @InjectLocale(filenamePattern = JUST_LOCALE_PATH) Locale justLocale;
            @InjectLocale(filenamePattern = PLUGIN_LOCALE_PATH) PluginLocale pluginLocale;
        }
        class SequentialTestTarget {
            @InjectLocale Locale firstLocale; @InjectLocale PluginLocale secondLocale;
        }
        var named = new NamedTestTarget();
        var sequentialTarget = new SequentialTestTarget();

        var justLocale = Mockito.mock(PluginLocale.class);
        var pluginLocale = Mockito.mock(PluginLocale.class);

        var log = Mockito.mock(Log.class);
        when(justLocale.logger()).thenReturn(log);
        when(pluginLocale.logger()).thenReturn(log);

        var pluginLocaleFactory = Mockito.mock(PluginLocaleFactory.class);
        /* test if injected according to filename pattern order */
        when(pluginLocaleFactory.locale(eq(NamedTestTarget.PLUGIN_LOCALE_PATH))).thenReturn(pluginLocale);
        when(pluginLocaleFactory.locale(eq(NamedTestTarget.JUST_LOCALE_PATH))).thenReturn(justLocale);
        LocaleAnnotationProcessor.process(named, pluginLocaleFactory);
        assertSame(named.justLocale, justLocale);
        assertSame(named.pluginLocale, pluginLocale);
        /* just locale first, plugin next */
        when(pluginLocaleFactory.locale(InjectLocale.FILENAME_PATTERN)).thenReturn(justLocale, pluginLocale);
        LocaleAnnotationProcessor.process(sequentialTarget, pluginLocaleFactory);
        assertSame(sequentialTarget.firstLocale, justLocale);
        assertSame(sequentialTarget.secondLocale, pluginLocale);
        /* reverse order */
        when(pluginLocaleFactory.locale(InjectLocale.FILENAME_PATTERN)).thenReturn(pluginLocale, justLocale);
        LocaleAnnotationProcessor.process(sequentialTarget, pluginLocaleFactory);
        assertSame(sequentialTarget.firstLocale, pluginLocale);
        assertSame(sequentialTarget.secondLocale, justLocale);
    }
}