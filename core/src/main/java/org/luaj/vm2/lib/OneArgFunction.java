package org.luaj.vm2.lib;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

/** Abstract base class for Java function implementations that take one argument and 
 * return one value. 
 * <p>
 * Subclasses need only implement {@link LuaValue#call(LuaValue)} to complete this class, 
 * simplifying development.  
 * All other uses of {@link #call()}, {@link #invoke(Varargs)},etc, 
 * are routed through this method by this class, 
 * dropping or extending arguments with {@code nil} values as required.
 * <p>
 * If more than one argument are required, or no arguments are required, 
 * or variable argument or variable return values, 
 * then use one of the related function
 * {@link ZeroArgFunction}, {@link TwoArgFunction}, {@link ThreeArgFunction}, or {@link VarArgFunction}.
 * <p>
 * See {@link LibFunction} for more information on implementation libraries and library functions.
 * @see #call(LuaValue)
 * @see LibFunction
 * @see ZeroArgFunction
 * @see TwoArgFunction
 * @see ThreeArgFunction
 * @see VarArgFunction
 */
abstract public class OneArgFunction extends LibFunction {

	abstract public LuaValue call(LuaValue arg);
	
	/** Default constructor */
	public OneArgFunction() {
	}
		
	public final LuaValue call() {
		return call(NIL);
	}

	public final LuaValue call(LuaValue arg1, LuaValue arg2) {
		return call(arg1);
	}

	public LuaValue call(LuaValue arg1, LuaValue arg2, LuaValue arg3) {
		return call(arg1);
	}

	public Varargs invoke(Varargs varargs) {
		return call(varargs.arg1());
	}
} 
