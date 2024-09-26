package org.luaj.vm2.exception;

import org.jetbrains.annotations.NotNull;

public class LuaArithmeticException extends LuaException {
    public LuaArithmeticException(@NotNull String type) {
        super("attempt to perform arithmetic on " + type);
    }

    public LuaArithmeticException(@NotNull String type, @NotNull String op) {
        super("attempt to perform arithmetic '" + op + "' on " + type);
    }
}
