package org.luaj.vm2.exception;

import org.jetbrains.annotations.NotNull;

public class LuaIllegalOperationException extends LuaException {
    public LuaIllegalOperationException(@NotNull String type, @NotNull String op) {
        super("illegal operation '" + op + "' for " + type);
    }
}
