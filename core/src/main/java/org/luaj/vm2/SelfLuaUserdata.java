package org.luaj.vm2;

import org.luaj.vm2.exception.LuaException;
import org.luaj.vm2.exception.LuaTypeException;

/**
 * No metatables/instance objects, this userdata is for integrating a class directly into userdata itself
 * rather than wrapping it.
 * Will require you to define get/set/operation/comparison methods yourself by overriding these respective functions
 * __eq: {@link Object#equals(Object)} or {@link LuaValue#eq(LuaValue)}
 * __index: {@link LuaValue#get(LuaValue)}
 * __newindex: {@link LuaValue#set(LuaValue, LuaValue)}
 * __add: {@link LuaValue#add(LuaValue)}
 * __sub: {@link LuaValue#sub(LuaValue)}
 * __mul: {@link LuaValue#mul(LuaValue)}
 * __div: {@link LuaValue#div(LuaValue)}
 * __mod: {@link LuaValue#mod(LuaValue)}
 * __concat: {@link LuaValue#concat(LuaValue)}
 */
public class SelfLuaUserdata extends LuaValue {
	public SelfLuaUserdata() { }

	public String toJString() {
		return this.toString();
	}
	
	public int getType() {
		return LuaValue.TUSERDATA;
	}
	
	public String getTypeName() {
		return "userdata";
	}

	public boolean isUserdata() {
		return true;
	}

	public boolean isUserdata(Class<?> type) {
		return type.isAssignableFrom(this.getClass());
	}

	public Object toUserdata() {
		return this;
	}

	public <T> T toUserdata(Class<T> type) {
		return type.isAssignableFrom(this.getClass()) ? (T) this : null;
	}

	public Object optionalUserdata(Object defval) {
		return this;
	}

	public <T> T optionalUserdata(Class<T> type, Object defval) {
		if (type.isAssignableFrom(this.getClass())) return (T) this;
		throw new LuaTypeException(getTypeName(), type.getName());
	}

	public Object checkUserdata() {
		return this;
	}
	
	public <T> T checkUserdata(Class<T> type) {
		if (type.isAssignableFrom(this.getClass())) return (T) this;
		throw new LuaTypeException(getTypeName(), type.getName());
	}
	
	public LuaValue get(LuaValue key) {
		throw new LuaException("cannot get " + key + " for userdata");
	}
	
	public void set(LuaValue key, LuaValue value) {
		throw new LuaException("cannot set " + key + " for userdata");
	}

	public LuaValue eq(LuaValue val) {
		return this.equals(val) ? TRUE : FALSE;
	}
}
