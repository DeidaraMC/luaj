package org.luaj.vm2;

public class LuaUserdata extends LuaValue {
	
	public Object m_instance;
	public LuaValue m_metatable;
	
	public LuaUserdata(Object obj) {
		m_instance = obj;
	}
	
	public LuaUserdata(Object obj, LuaValue metatable) {
		m_instance = obj;
		m_metatable = metatable;
	}
	
	public String toJString() {
		return String.valueOf(m_instance);
	}
	
	public int getType() {
		return LuaValue.TUSERDATA;
	}
	
	public String getTypeName() {
		return "userdata";
	}

	public int hashCode() {
		return m_instance.hashCode();
	}
	
	public Object userdata() {
		return m_instance;
	}
	
	public boolean isUserdata()                        { return true; }
	public boolean isUserdata(Class<?> type)                 { return type.isAssignableFrom(m_instance.getClass()); }
	public Object toUserdata()                        { return m_instance; }
	public <T> T toUserdata(Class<T> type)                 { return type.isAssignableFrom(m_instance.getClass())? (T) m_instance: null; }
	public Object optionalUserdata(Object defval)          { return m_instance; }
	public <T> T optionalUserdata(Class<T> type, Object defval) {
		if (!type.isAssignableFrom(m_instance.getClass()))
			typerror(type.getName());
		return (T) m_instance;
	}
	
	public LuaValue getmetatable() {
		return m_metatable;
	}

	public LuaValue setmetatable(LuaValue metatable) {
		this.m_metatable = metatable;
		return this;
	}

	public Object checkUserdata() {
		return m_instance;
	}
	
	public <T> T checkUserdata(Class<T> type) {
		if ( type.isAssignableFrom(m_instance.getClass()) )
			return (T) m_instance;
		return (T) typerror(type.getName());
	}
	
	public LuaValue get( LuaValue key ) {
		return m_metatable!=null? gettable(this,key): NIL;
	}
	
	public void set( LuaValue key, LuaValue value ) {
		if ( m_metatable==null || ! settable(this,key,value) )
			error( "cannot set "+key+" for userdata" );
	}

	public boolean equals( Object val ) {
		if ( this == val )
			return true;
		if ( ! (val instanceof LuaUserdata) )
			return false;
		LuaUserdata u = (LuaUserdata) val;
		return m_instance.equals(u.m_instance);
	}

	// equality w/ metatable processing
	public LuaValue eq( LuaValue val )     { return eq_b(val)? TRUE: FALSE; } 
	public boolean eq_b( LuaValue val ) { 
		if ( val.raweq(this) ) return true;
		if ( m_metatable == null || !val.isUserdata() ) return false;
		LuaValue valmt = val.getmetatable();
		return valmt!=null && LuaValue.eqmtcall(this, m_metatable, val, valmt); 
	}
	
	// equality w/o metatable processing
	public boolean raweq( LuaValue val )      { return val.raweq(this); }
	public boolean raweq( LuaUserdata val )   {
		return this == val || (m_metatable == val.m_metatable && m_instance.equals(val.m_instance)); 
	}
	
	// __eq metatag processing
	public boolean eqmt( LuaValue val ) {
		return m_metatable!=null && val.isUserdata()? LuaValue.eqmtcall(this, m_metatable, val, val.getmetatable()): false;
	}
}
