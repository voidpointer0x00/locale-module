/*
 *             DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *
 *  Copyright (C) 2022 Vasiliy Petukhov <void.pointer@ya.ru>
 *
 *  Everyone is permitted to copy and distribute verbatim or modified
 *  copies of this license document, and changing it is allowed as long
 *  as the name is changed.
 *
 *             DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *    TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *   0. You just DO WHAT THE FUCK YOU WANT TO.
 */

package voidpointer.spigot.framework.localemodule.annotation;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import lombok.val;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import voidpointer.spigot.framework.localemodule.Locale;
import voidpointer.spigot.framework.localemodule.Message;
import voidpointer.spigot.framework.localemodule.config.LocaleFile;
import voidpointer.spigot.framework.localemodule.config.LocaleSection;
import voidpointer.spigot.framework.localemodule.config.TranslatedLocaleFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LocaleAnnotationResolver {
    public static boolean resolve(final JavaPlugin plugin) {
        Locale locale = null;
        for (Field field : plugin.getClass().getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers()))
                continue;
            if (!field.isAnnotationPresent(PluginLocale.class))
                continue;

            if (isLocaleSectionType(field.getType()))
                locale = injectLocaleSection(field, plugin);
            else if (isTranslatableLocaleType(field.getType()))
                locale = injectTranslatableLocale(field, plugin);
            else if (isLocaleFileType(field.getType()))
                locale = injectLocaleFile(field, plugin);
            else
                continue;
            if ((locale != null) & field.getAnnotation(PluginLocale.class).searchForInjection())
                injectAutowiredLocale(locale, plugin);
            break;
        }
        if (locale == null) {
            plugin.getLogger().warning("@PluginLocale annotated static field not found or failed to initialize.");
            return false;
        }
        return true;
    }

    /* This Guava API is marked as @Beta for more than 8 years */
    private static void injectAutowiredLocale(final Locale locale, final JavaPlugin plugin) {
        Package pluginPackage = plugin.getClass().getPackage();
        try {
            ImmutableSet<ClassInfo> subClasses = ClassPath.from(plugin.getClass().getClassLoader())
                    .getTopLevelClassesRecursive(pluginPackage.getName());
            for (ClassInfo classInfo : subClasses)
                injectAutowired(locale, classInfo);
        } catch (IllegalAccessException illegalAccessException) {
            plugin.getLogger().warning("Can't inject Locale, perhaps, it's a final field: " + illegalAccessException.getMessage());
        } catch (IOException e) {
            plugin.getLogger().warning("Unable to search through the packages to inject locale.");
        }
    }

    private static void injectAutowired(final Locale locale, final ClassInfo info) throws IllegalAccessException {
        try {
            injectAutowired0(locale, info);
        } catch (NoClassDefFoundError ignoreNms) {}
    }

    private static void injectAutowired0(final Locale locale, final ClassInfo info) throws IllegalAccessException {
        Class<?> target = info.load();
        if (target == null)
            return;
        for (Field field : target.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers()))
                continue;
            if (!field.isAnnotationPresent(AutowiredLocale.class))
                continue;
            field.setAccessible(true);
            field.set(null, locale);
        }
    }

    private static LocaleFile injectLocaleFile(final Field field, final JavaPlugin plugin) {
        try {
            val locale = new LocaleFile(plugin);
            addDefaults(locale, field.getAnnotation(PluginLocale.class));
            locale.save();
            field.setAccessible(true);
            field.set(plugin, locale);
            return locale;
        } catch (IllegalAccessException illegalAccessException) {
            logFailedSetting(plugin.getLogger(), illegalAccessException);
        }
        return null;
    }

    private static TranslatedLocaleFile injectTranslatableLocale(final Field field, final JavaPlugin plugin) {
        try {
            PluginLocale pluginLocale = field.getAnnotation(PluginLocale.class);
            String language = getLanguage(pluginLocale, plugin);
            val locale = new TranslatedLocaleFile(plugin, language);
            addDefaults(locale, pluginLocale);
            locale.save();
            field.setAccessible(true);
            field.set(plugin, locale);
            return locale;
        } catch (IllegalAccessException illegalAccessException) {
            logFailedSetting(plugin.getLogger(), illegalAccessException);
        }
        return null;
    }

    private static LocaleSection injectLocaleSection(final Field field, final JavaPlugin plugin) {
        try {
            PluginLocale pluginLocale = field.getAnnotation(PluginLocale.class);
            ConfigurationSection messagesSection = getMessagesSection(pluginLocale, plugin);
            val locale = new LocaleSection(plugin, messagesSection);
            addDefaults(locale, pluginLocale);
            field.setAccessible(true);
            field.set(plugin, locale);
            return locale;
        } catch (IllegalAccessException illegalAccessException) {
            logFailedSetting(plugin.getLogger(), illegalAccessException);
        }
        return null;
    }

    private static void addDefaults(final Locale locale, final PluginLocale pluginLocale) {
        if (pluginLocale.defaultMessages().equals(void.class))
            return;
        if (!pluginLocale.defaultMessages().isEnum())
            return;
        Object[] defaultMessages = pluginLocale.defaultMessages().getEnumConstants();
        if (defaultMessages.length == 0)
            return;
        if (!(defaultMessages[0] instanceof Message))
            return;
        locale.addDefaults((Message[]) defaultMessages);
    }

    private static String getLanguage(final PluginLocale pluginLocale, final JavaPlugin plugin) {
        String language = invokeGetLanguageIfPossible(pluginLocale.languageMethodName(), plugin);
        if (language != null)
            return language;
        return plugin.getConfig().getString(pluginLocale.languagePath(), pluginLocale.defaultLanguage());
    }

    private static String invokeGetLanguageIfPossible(final String methodName, final JavaPlugin plugin) {
        try {
            Method method = plugin.getClass().getMethod(methodName);
            method.setAccessible(true);
            return (String) method.invoke(plugin);
        } catch (NoSuchMethodException ignore) {
            // ignore
        } catch (IllegalAccessException | InvocationTargetException | ClassCastException invocationException) {
            plugin.getLogger().log(Level.WARNING, "Method invocation failed", invocationException);
        }
        return null;
    }

    private static ConfigurationSection getMessagesSection(final PluginLocale pluginLocale, final JavaPlugin plugin) {
        try {
            Method getMessagesMethod = plugin.getClass().getMethod(pluginLocale.messagesSectionMethodName());
            getMessagesMethod.setAccessible(true);
            return (ConfigurationSection) getMessagesMethod.invoke(plugin);
        } catch (NoSuchMethodException ignore) {
            // ignore
        } catch (InvocationTargetException | IllegalAccessException | ClassCastException invocationException) {
            plugin.getLogger().log(Level.WARNING, "Method invocation failed", invocationException);
        }
        FileConfiguration pluginConfig = plugin.getConfig();
        if (pluginConfig.isSet(LocaleFile.MESSAGES_PATH))
            return pluginConfig.getConfigurationSection(LocaleFile.MESSAGES_PATH);
        return pluginConfig.createSection(LocaleFile.MESSAGES_PATH);
    }

    private static void logFailedSetting(final Logger logger, final Throwable throwable) {
        logger.log(Level.SEVERE, "Failed setting locale", throwable);
    }

    private static boolean isLocaleSectionType(Class<?> type) {
        return LocaleSection.class.isAssignableFrom(type);
    }

    private static boolean isLocaleFileType(final Class<?> type) {
        return LocaleFile.class.isAssignableFrom(type);
    }

    private static boolean isTranslatableLocaleType(final Class<?> type) {
        return TranslatedLocaleFile.class.isAssignableFrom(type);
    }
}
