package org.luaj.vm2.exception;

import org.jetbrains.annotations.NotNull;

public class LuaUnimplementedException extends LuaException {
    public LuaUnimplementedException(@NotNull String type, @NotNull String function) {
        super("'" + function + "' not implemented for " + type);
    }
}
