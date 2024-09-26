package org.luaj.vm2.lib;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.exception.LuaArgumentException;

class TableLibFunction extends LibFunction {
	public LuaValue call() {
		throw new LuaArgumentException(1, "table expected, got no value");
	}
}
