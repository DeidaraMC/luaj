package org.luaj.vm2.exception;

import org.jetbrains.annotations.NotNull;

public class LuaTypeException extends LuaException {
    public LuaTypeException(@NotNull String type, @NotNull String expected) {
        super(expected + " expected, got "+ type);
    }
}
