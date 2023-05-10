/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */

package voidpointer.mc.localemodule.exception;

import voidpointer.mc.localemodule.Message;
import voidpointer.mc.localemodule.Placeholder;

public class UnexpectedTypeException extends LocaleException {
    public UnexpectedTypeException(final Message message) {
        super("Unexpected message type: " + message.getClass().getSimpleName() + " -> " + message);
    }

    public UnexpectedTypeException(final Placeholder placeholder) {
        super("Unexpected placeholder type: " + placeholder.getClass().getSimpleName() + " -> " + placeholder);
    }
}
