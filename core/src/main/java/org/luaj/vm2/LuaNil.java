package org.luaj.vm2;

/**
 * Class to encapsulate behavior of the singleton instance {@code nil} 
 * <p>
 * There will be one instance of this class, {@link LuaValue#NIL}, 
 * per Java virtual machine.  
 * However, the {@link Varargs} instance {@link LuaValue#NONE}
 * which is the empty list, 
 * is also considered treated as a nil value by default.  
 * <p>
 * Although it is possible to test for nil using Java == operator, 
 * the recommended approach is to use the method {@link LuaValue#isNil()}
 * instead.  By using that any ambiguities between 
 * {@link LuaValue#NIL} and {@link LuaValue#NONE} are avoided.
 * @see LuaValue
 * @see LuaValue#NIL
 */
public class LuaNil extends LuaValue {
	
	static final LuaNil _NIL = new LuaNil();
	
	public static LuaValue s_metatable;
	
	LuaNil() {}

	public int getType() {
		return LuaValue.TNIL;
	}

	public String toString() {
		return "nil";		
	}
	
	public String getTypeName() {
		return "nil";
	}
	
	public String toJString() {
		return "nil";
	}

	public LuaValue not()  { 
		return LuaValue.TRUE;  
	}
	
	public boolean toBoolean() {
		return false; 
	}
	
	public boolean isNil() {
		return true;
	}
		
	public LuaValue getmetatable() { 
		return s_metatable; 
	}
	
	public boolean equals(Object o) {
		return o instanceof LuaNil;
	}

	public LuaValue checkNotNil() {
		return argerror("value");
	}
	
	public boolean isValidKey() {
		return false;
	}

	// optional argument conversions - nil alwas falls badk to default value
	public boolean optionalBoolean(boolean defval)          { return defval; }
	public LuaClosure optionalClosure(LuaClosure defval)       { return defval; }
	public double optionalDouble(double defval)               { return defval; }
	public LuaFunction optionalFunction(LuaFunction defval)     { return defval; }
	public int optionalInt(int defval)                  { return defval; }
	public LuaInteger optionalInteger(LuaInteger defval)       { return defval; }
	public long optionalLong(long defval)                { return defval; }
	public LuaNumber optionalNumber(LuaNumber defval)         { return defval; }
	public LuaTable optionalTable(LuaTable defval)           { return defval; }
	public LuaThread optionalThread(LuaThread defval)         { return defval; }
	public String optionalJString(String defval)            { return defval; }
	public LuaString optionalLuaString(LuaString defval)         { return defval; }
	public Object optionalUserdata(Object defval)          { return defval; }
	public Object optionalUserdata(Class type, Object defval) { return defval; }
	public LuaValue optionalValue(LuaValue defval)           { return defval; }
}
