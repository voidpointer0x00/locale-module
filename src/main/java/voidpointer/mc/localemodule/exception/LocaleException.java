/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.exception;

public abstract class LocaleException extends RuntimeException {
    public LocaleException() {
        super();
    }

    public LocaleException(String message) {
        super(message);
    }

    public LocaleException(String message, Throwable cause) {
        super(message, cause);
    }

    public LocaleException(Throwable cause) {
        super(cause);
    }

    protected LocaleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
