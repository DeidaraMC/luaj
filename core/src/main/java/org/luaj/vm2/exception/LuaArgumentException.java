package org.luaj.vm2.exception;

import org.jetbrains.annotations.NotNull;

public class LuaArgumentException extends LuaException {
    public LuaArgumentException(@NotNull String expected, @NotNull String type) {
        super("bad argument: " + expected + " expected, got " + type);
    }
    public LuaArgumentException(int iarg, @NotNull String message) {
        super("bad argument #" + iarg + ": " + message);
    }
}
