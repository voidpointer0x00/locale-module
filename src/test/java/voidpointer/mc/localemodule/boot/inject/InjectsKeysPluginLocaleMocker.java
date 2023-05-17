/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.boot.inject;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.DataProvider;
import voidpointer.mc.localemodule.LocaleKey;
import voidpointer.mc.localemodule.Log;
import voidpointer.mc.localemodule.PluginLocale;
import voidpointer.mc.localemodule.boot.inject.keys.EnumTestKeys;
import voidpointer.mc.localemodule.boot.inject.keys.ExcludeTestKeys;
import voidpointer.mc.localemodule.boot.inject.keys.IncludeTestKeys;
import voidpointer.mc.localemodule.boot.inject.keys.ProviderTestKeys;
import voidpointer.mc.localemodule.boot.inject.keys.inner.PackageTestKeys;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

public final class InjectsKeysPluginLocaleMocker {
    @DataProvider
    public static Object[][] provideKeys() {
        class ProvideKeysLocaleContainer { @InjectLocale(keyContainers=ProviderTestKeys.class) PluginLocale locale; }
        class IncludeKeysLocaleContainer { @InjectLocale(keyContainers=IncludeTestKeys.class) PluginLocale locale; }
        class ExcludeKeysLocaleContainer { @InjectLocale(keyContainers=ExcludeTestKeys.class) PluginLocale locale; }
        class EnumKeysLocaleContainer { @InjectLocale(keyContainers=EnumTestKeys.class) PluginLocale locale; }
        class PackageKeysLocaleContainer { @InjectLocale(keyContainers=PackageTestKeys.class) PluginLocale locale; }

        var provideKeysMocker = new InjectsKeysPluginLocaleMocker();
        var includeKeysMocker = new InjectsKeysPluginLocaleMocker();
        var excludeKeysProvider = new InjectsKeysPluginLocaleMocker();
        var enumKeysProvider = new InjectsKeysPluginLocaleMocker();
        var packageKeysProvider = new InjectsKeysPluginLocaleMocker();

        LocaleAnnotationProcessor.process(new ProvideKeysLocaleContainer(), provideKeysMocker.mockedLocaleFactory);
        LocaleAnnotationProcessor.process(new IncludeKeysLocaleContainer(), includeKeysMocker.mockedLocaleFactory);
        LocaleAnnotationProcessor.process(new ExcludeKeysLocaleContainer(), excludeKeysProvider.mockedLocaleFactory);
        LocaleAnnotationProcessor.process(new EnumKeysLocaleContainer(), enumKeysProvider.mockedLocaleFactory);
        LocaleAnnotationProcessor.process(new PackageKeysLocaleContainer(), packageKeysProvider.mockedLocaleFactory);

        return new Object[][] {
                {provideKeysMocker.mockedLocale, ProviderTestKeys.expected()},
                {includeKeysMocker.mockedLocale, IncludeTestKeys.expected()},
                {excludeKeysProvider.mockedLocale, ExcludeTestKeys.expected()},
                {enumKeysProvider.mockedLocale, EnumTestKeys.expected()},
                {packageKeysProvider.mockedLocale, PackageTestKeys.expected()}
        };
    }

    private final PluginLocaleFactory mockedLocaleFactory = Mockito.mock(PluginLocaleFactory.class);
    private final LinkedHashSet<LocaleKey> keys = new LinkedHashSet<>();
    private final PluginLocale mockedLocale;

    private InjectsKeysPluginLocaleMocker() {
        var mockedLog = Mockito.mock(Log.class);
        mockedLocale = Mockito.mock(PluginLocale.class);
        Mockito.when(mockedLocale.logger()).thenReturn(mockedLog);
        Mockito.doAnswer(this::mockAdd).when(mockedLocale).addKeys(Mockito.anyCollection());
        Mockito.doAnswer(this::mockAdd).when(mockedLocale).addKeys((LocaleKey[]) Mockito.any());
        Mockito.doAnswer(this::mockAdd).when(mockedLocale).addKeys((LocaleKey) Mockito.any());
        Mockito.when(mockedLocale.defaultKeys()).thenReturn(keys);

        Mockito.when(mockedLocaleFactory.locale(InjectLocale.FILENAME_PATTERN)).thenReturn(mockedLocale);
    }

    private <T> Answer<T> mockAdd(final InvocationOnMock invocation) {
        Object arg = invocation.getArgument(0);
        if (arg instanceof Collection<?> collection)
            //noinspection unchecked
            keys.addAll((Collection<? extends LocaleKey>) collection);
        else if (arg instanceof LocaleKey key)
            keys.add(key);
        else if (arg instanceof LocaleKey[] keysArray)
            keys.addAll(Arrays.stream(keysArray).toList());
        else
            throw new IllegalArgumentException();
        return null;
    }
}
