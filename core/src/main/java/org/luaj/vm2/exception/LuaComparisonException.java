package org.luaj.vm2.exception;

import org.jetbrains.annotations.NotNull;

public class LuaComparisonException extends LuaException {
    public LuaComparisonException(@NotNull String lhs, @NotNull String rhs) {
        super("attempt to compare " + lhs + " with " + rhs);
    }
}
