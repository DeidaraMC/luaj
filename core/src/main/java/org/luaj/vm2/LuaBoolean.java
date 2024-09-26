package org.luaj.vm2;

/**
 * Extension of {@link LuaValue} which can hold a Java boolean as its value. 
 * <p>
 * These instance are not instantiated directly by clients.  
 * Instead, there are exactly twon instances of this class, 
 * {@link LuaValue#TRUE} and {@link LuaValue#FALSE} 
 * representing the lua values {@code true} and {@code false}.
 * The function {@link LuaValue#valueOf(boolean)} will always 
 * return one of these two values. 
 * <p>
 * Any {@link LuaValue} can be converted to its equivalent 
 * boolean representation using {@link LuaValue#toBoolean()}
 * <p>
 * @see LuaValue
 * @see LuaValue#valueOf(boolean)
 * @see LuaValue#TRUE
 * @see LuaValue#FALSE
 */
public final class LuaBoolean extends LuaValue {

	/** The singleton instance representing lua {@code true} */
	static final LuaBoolean _TRUE = new LuaBoolean(true);
	
	/** The singleton instance representing lua {@code false} */
	static final LuaBoolean _FALSE = new LuaBoolean(false);
	
	/** Shared static metatable for boolean values represented in lua. */
	public static LuaValue s_metatable;

	/** The value of the boolean */
	public final boolean v;

	LuaBoolean(boolean b) {
		this.v = b;
	}

	public int getType() {
		return LuaValue.TBOOLEAN;
	}

	public String getTypeName() {
		return "boolean";
	}

	public boolean isBoolean() {
		return true;
	}

	public LuaValue not() {
		return v ? FALSE : LuaValue.TRUE;
	}

	/**
	 * Return the boolean value for this boolean
	 * @return value as a Java boolean
	 */
	public boolean booleanValue() {
		return v;
	}

	public boolean toBoolean() {
		return v;
	}

	public String toJString() {
		return v ? "true" : "false";
	}

	public boolean optionalBoolean(boolean defval) {
		return this.v;
	}
	
	public boolean checkBoolean() {
		return v;
	}
	
	public LuaValue getmetatable() { 
		return s_metatable; 
	}
}
