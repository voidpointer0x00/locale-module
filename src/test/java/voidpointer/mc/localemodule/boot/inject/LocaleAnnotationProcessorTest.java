/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.boot.inject;

import lombok.Getter;
import org.mockito.Mockito;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import voidpointer.mc.localemodule.Locale;
import voidpointer.mc.localemodule.LocaleKey;
import voidpointer.mc.localemodule.Log;
import voidpointer.mc.localemodule.PluginLocale;

import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

@Test(groups="inject")
public class LocaleAnnotationProcessorTest {

    interface DoubleLocaleHolder { Locale getFirst(); Locale getSecond(); }

    @DataProvider
    public static Object[][] provideInjects() {
        @Getter class NamedTestTarget implements DoubleLocaleHolder {
            static final String JUST_LOCALE_PATH = "just-locale";
            static final String PLUGIN_LOCALE_PATH = "plugin-locale";
            @InjectLocale(filenamePattern = PLUGIN_LOCALE_PATH) PluginLocale first;
            @InjectLocale(filenamePattern = JUST_LOCALE_PATH) Locale second;
        }
        @Getter class SequentialTestTarget implements DoubleLocaleHolder {
            @InjectLocale Locale first; @InjectLocale PluginLocale second;
        }
        var named = new NamedTestTarget();
        var sequentialTarget = new SequentialTestTarget();

        var justLocale = Mockito.mock(PluginLocale.class);
        var pluginLocale = Mockito.mock(PluginLocale.class);

        var log = Mockito.mock(Log.class);
        when(justLocale.logger()).thenReturn(log);
        when(pluginLocale.logger()).thenReturn(log);

        var localeFactory = Mockito.mock(PluginLocaleFactory.class);
        /* test if injected according to filename pattern order */
        when(localeFactory.locale(eq(NamedTestTarget.PLUGIN_LOCALE_PATH))).thenReturn(pluginLocale);
        when(localeFactory.locale(eq(NamedTestTarget.JUST_LOCALE_PATH))).thenReturn(justLocale);
        when(localeFactory.locale(InjectLocale.FILENAME_PATTERN)).thenReturn(pluginLocale, justLocale,
                justLocale, pluginLocale);
        return new Object[][] {
                {named, localeFactory, pluginLocale, justLocale},
                {sequentialTarget, localeFactory, pluginLocale, justLocale},
                {sequentialTarget, localeFactory, justLocale, pluginLocale}
        };
    }

    @Test(dataProvider="provideInjects", description="Tests whether annotation processor injects the right locales")
    public void testInjects(DoubleLocaleHolder doubleLocaleHolder, PluginLocaleFactory pluginLocaleFactory,
                            Locale expectedFirst, Locale expectedSecond) {
        LocaleAnnotationProcessor.process(doubleLocaleHolder, pluginLocaleFactory);
        assertSame(doubleLocaleHolder.getFirst(), expectedFirst);
        assertSame(doubleLocaleHolder.getSecond(), expectedSecond);
    }

    @Test(dataProvider="provideKeys", dataProviderClass=InjectsKeysPluginLocaleMocker.class, dependsOnMethods="testInjects",
            description="Tests whether annotation processor injected the right keys")
    public void testInjectsKeys(PluginLocale locale, Map<String, String> expected) {
        var localeKeys = locale.defaultKeys();
        assertEquals(localeKeys.size(), expected.size());
        for (final LocaleKey key : localeKeys)
            assertEquals(key.defaultValue(), expected.get(key.path()));
    }
}