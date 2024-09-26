package org.luaj.vm2.require;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

/**
 * This should fail while trying to load via 
 * "require()" because it throws a LuaException
 * 
 */
public class RequireSampleLoadLuaError extends ZeroArgFunction {
	
	public RequireSampleLoadLuaError() {		
	}
	
	public LuaValue call() {
		LuaValue.valueOf"sample-load-lua-error");
		return LuaValue.valueOf("require-sample-load-lua-error");
	}	
}
