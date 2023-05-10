/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.boot.inject;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectLocale {
    String FILENAME_PATTERN = "locale/%s.yml";

    /** A pattern for corresponding YAML file with language argument for {@link String#format(String, Object...)}. */
    @NotNull String filenamePattern() default FILENAME_PATTERN;

    /** An array of packages to scan for classes annotated with {@link LocaleKeyContainer}. */
    @NotNull String[] keyPackages() default {};

    /** An array of {@link LocaleKeyContainer} classes. */
    Class<?>[] keyContainers() default {};

    /** Automatically loads and save defaults. */
    boolean autoload() default true;
}
