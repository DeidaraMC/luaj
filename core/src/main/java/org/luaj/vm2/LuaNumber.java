package org.luaj.vm2;

/** 
 * Base class for representing numbers as lua values directly. 
 * <p>
 * The main subclasses are {@link LuaInteger} which holds values that fit in a java int, 
 * and {@link LuaDouble} which holds all other number values.
 * @see LuaInteger
 * @see LuaDouble
 * @see LuaValue
 * 
 */
abstract
public class LuaNumber extends LuaValue {

	/** Shared static metatable for all number values represented in lua. */
	public static LuaValue s_metatable;
	
	public int getType() {
		return TNUMBER;
	}
	
	public String getTypeName() {
		return "number";
	}
	
	public LuaNumber checkNumber() {
		return this; 
	}
	
	public LuaNumber checkNumber(String errmsg) {
		return this; 
	}
	
	public LuaNumber optionalNumber(LuaNumber defval) {
		return this; 
	}
	
	public LuaValue toNumber() {
		return this;
	}
	
	public boolean isNumber() {
		return true;
	}
	
	public boolean isString() {
		return true;
	}
	
	public LuaValue getmetatable() { 
		return s_metatable; 
	}

	public LuaValue concat(LuaValue rhs)      { return rhs.concatTo(this); }
	public Buffer   concat(Buffer rhs)        { return rhs.concatTo(this); }
	public LuaValue concatTo(LuaNumber lhs)   { return strvalue().concatTo(lhs.strvalue()); }
	public LuaValue concatTo(LuaString lhs)   { return strvalue().concatTo(lhs); }

}
