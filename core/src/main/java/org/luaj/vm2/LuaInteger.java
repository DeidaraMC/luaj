package org.luaj.vm2;

import org.luaj.vm2.exception.LuaTypeException;
import org.luaj.vm2.lib.MathLib;

/**
 * Extension of {@link LuaNumber} which can hold a Java int as its value.
 * <p>
 * These instance are not instantiated directly by clients, but indirectly
 * via the static functions {@link LuaValue#valueOf(int)} or {@link LuaValue#valueOf(double)}
 * functions.  This ensures that policies regarding pooling of instances are
 * encapsulated.
 * <p>
 * There are no API's specific to LuaInteger that are useful beyond what is already
 * exposed in {@link LuaValue}.
 * 
 * @see LuaValue
 * @see LuaNumber
 * @see LuaDouble
 * @see LuaValue#valueOf(int)
 * @see LuaValue#valueOf(double)
 */
public class LuaInteger extends LuaNumber {
	private static final int CACHE_SIZE = 1028;
	private static final LuaInteger[] POSITIVE_INT_VALUES = new LuaInteger[CACHE_SIZE];
	private static final LuaInteger[] NEGATIVE_INT_VALUES = new LuaInteger[CACHE_SIZE];
	static {
		for (int i = 0; i < POSITIVE_INT_VALUES.length; i++) POSITIVE_INT_VALUES[i] = new LuaInteger(i);
		for (int i = 0; i < NEGATIVE_INT_VALUES.length; i++) NEGATIVE_INT_VALUES[i] = new LuaInteger(i - CACHE_SIZE);
	}

	public static LuaInteger valueOf(int i) {
		return i < CACHE_SIZE ?
				(i >= 0 ? POSITIVE_INT_VALUES[i] : (i >= -CACHE_SIZE ? NEGATIVE_INT_VALUES[i + CACHE_SIZE] : new LuaInteger(i))) :
				new LuaInteger(i);
	};
	
	 // TODO consider moving this to LuaValue
	/** Return a LuaNumber that represents the value provided
	 * @param l long value to represent.
	 * @return LuaNumber that is eithe LuaInteger or LuaDouble representing l
	 * @see LuaValue#valueOf(int)
	 * @see LuaValue#valueOf(double)
	 */
	public static LuaNumber valueOf(long l) {
		return (l == (int) l) ? LuaInteger.valueOf((int) l) : LuaDouble.valueOf(l);
	}
	
	/** The value being held by this instance. */
	public final int v;
	
	/**
	 * Package protected constructor.
	 * @see LuaValue#valueOf(int)
	 **/
	LuaInteger(int i) {
		this.v = i;
	}
	
	public boolean isInt() {		return true;	}
	public boolean isInteger() {	return true;	}
	public boolean isLong() {		return true;	}
	
	public byte toByte()        { return (byte) v; }
	public char    tochar()        { return (char) v; }
	public double toDouble()      { return v; }
	public float toFloat()       { return v; }
	public int toInt()         { return v; }
	public long toLong()        { return v; }
	public short toShort()       { return (short) v; }

	public double optionalDouble(double defval)            { return v; }
	public int optionalInt(int defval)                  { return v;  }
	public LuaInteger optionalInteger(LuaInteger defval)       { return this; }
	public long optionalLong(long defval)                { return v; }

	public String toJString() {
		return Integer.toString(v);
	}

	public LuaString strvalue() {
		return LuaString.valueOf(Integer.toString(v));
	}
		
	public LuaString optionalLuaString(LuaString defval) {
		return LuaString.valueOf(Integer.toString(v));
	}
	
	public LuaValue toLuaString() {
		return LuaString.valueOf(Integer.toString(v));
	}
		
	public String optionalJString(String defval) {
		return Integer.toString(v);
	}
	
	public LuaInteger checkInteger() {
		return this;
	}
	
	public boolean isString() {
		return true;
	}
	
	public int hashCode() {
		return v;
	}

	public static int hashCode(int x) {
		return x;
	}

	// unary operators
	public LuaValue neg() { return valueOf(-(long)v); }
	
	// object equality, used for key comparison
	public boolean equals(Object o) { return o instanceof LuaInteger? ((LuaInteger)o).v == v: false; }
	
	// equality w/ metatable processing
	public LuaValue eq( LuaValue val )    { return val.raweq(v)? TRUE: FALSE; }
	public boolean eq_b( LuaValue val )   { return val.raweq(v); }
	
	// equality w/o metatable processing
	public boolean raweq( LuaValue val )  { return val.raweq(v); }
	public boolean raweq( double val )    { return v == val; }
	public boolean raweq( int val )       { return v == val; }
	
	// arithmetic operators
	public LuaValue   add( LuaValue rhs )        { return rhs.add(v); }
	public LuaValue   add( double lhs )     { return LuaDouble.valueOf(lhs + v); }
	public LuaValue   add( int lhs )        { return LuaInteger.valueOf(lhs + (long)v); }
	public LuaValue   sub( LuaValue rhs )        { return rhs.subFrom(v); }
	public LuaValue   sub( double rhs )        { return LuaDouble.valueOf(v - rhs); }
	public LuaValue   sub( int rhs )        { return LuaDouble.valueOf(v - rhs); }
	public LuaValue   subFrom( double lhs )   { return LuaDouble.valueOf(lhs - v); }
	public LuaValue   subFrom( int lhs )      { return LuaInteger.valueOf(lhs - (long)v); }
	public LuaValue   mul( LuaValue rhs )        { return rhs.mul(v); }
	public LuaValue   mul( double lhs )   { return LuaDouble.valueOf(lhs * v); }
	public LuaValue   mul( int lhs )      { return LuaInteger.valueOf(lhs * (long)v); }
	public LuaValue   pow( LuaValue rhs )        { return rhs.powWith(v); }
	public LuaValue   pow( double rhs )        { return MathLib.dpow(v,rhs); }
	public LuaValue   pow( int rhs )        { return MathLib.dpow(v,rhs); }
	public LuaValue   powWith( double lhs )   { return MathLib.dpow(lhs,v); }
	public LuaValue   powWith( int lhs )      { return MathLib.dpow(lhs,v); }
	public LuaValue   div( LuaValue rhs )        { return rhs.divInto(v); }
	public LuaValue   div( double rhs )        { return LuaDouble.ddiv(v,rhs); }
	public LuaValue   div( int rhs )        { return LuaDouble.ddiv(v,rhs); }
	public LuaValue   divInto( double lhs )   { return LuaDouble.ddiv(lhs,v); }
	public LuaValue   mod( LuaValue rhs )        { return rhs.modFrom(v); }
	public LuaValue   mod( double rhs )        { return LuaDouble.dmod(v,rhs); }
	public LuaValue   mod( int rhs )        { return LuaDouble.dmod(v,rhs); }
	public LuaValue   modFrom( double lhs )   { return LuaDouble.dmod(lhs,v); }
	
	// relational operators
	public LuaValue   lt( LuaValue rhs )         { return rhs instanceof LuaNumber ? (rhs.gt_b(v)? TRUE: FALSE) : super.lt(rhs); }
	public LuaValue   lt( double rhs )      { return v < rhs? TRUE: FALSE; }
	public LuaValue   lt( int rhs )         { return v < rhs? TRUE: FALSE; }
	public boolean lt_b( LuaValue rhs )       { return rhs instanceof LuaNumber ? rhs.gt_b(v) : super.lt_b(rhs); }
	public boolean lt_b( int rhs )         { return v < rhs; }
	public boolean lt_b( double rhs )      { return v < rhs; }
	public LuaValue   lteq( LuaValue rhs )       { return rhs instanceof LuaNumber ? (rhs.gteq_b(v)? TRUE: FALSE) : super.lteq(rhs); }
	public LuaValue   lteq( double rhs )    { return v <= rhs? TRUE: FALSE; }
	public LuaValue   lteq( int rhs )       { return v <= rhs? TRUE: FALSE; }
	public boolean lteq_b( LuaValue rhs )     { return rhs instanceof LuaNumber ? rhs.gteq_b(v) : super.lteq_b(rhs); }
	public boolean lteq_b( int rhs )       { return v <= rhs; }
	public boolean lteq_b( double rhs )    { return v <= rhs; }
	public LuaValue   gt( LuaValue rhs )         { return rhs instanceof LuaNumber ? (rhs.lt_b(v)? TRUE: FALSE) : super.gt(rhs); }
	public LuaValue   gt( double rhs )      { return v > rhs? TRUE: FALSE; }
	public LuaValue   gt( int rhs )         { return v > rhs? TRUE: FALSE; }
	public boolean gt_b( LuaValue rhs )       { return rhs instanceof LuaNumber ? rhs.lt_b(v) : super.gt_b(rhs); }
	public boolean gt_b( int rhs )         { return v > rhs; }
	public boolean gt_b( double rhs )      { return v > rhs; }
	public LuaValue   gteq( LuaValue rhs )       { return rhs instanceof LuaNumber ? (rhs.lteq_b(v)? TRUE: FALSE) : super.gteq(rhs); }
	public LuaValue   gteq( double rhs )    { return v >= rhs? TRUE: FALSE; }
	public LuaValue   gteq( int rhs )       { return v >= rhs? TRUE: FALSE; }
	public boolean gteq_b( LuaValue rhs )     { return rhs instanceof LuaNumber ? rhs.lteq_b(v) : super.gteq_b(rhs); }
	public boolean gteq_b( int rhs )       { return v >= rhs; }
	public boolean gteq_b( double rhs )    { return v >= rhs; }
	
	// string comparison
	public int strcmp( LuaString rhs )      { throw new LuaTypeException(getTypeName(), "attempt to compare number with string"); }
	
	public int checkInt() {
		return v;
	}
	public long checkLong() {
		return v;
	}
	public double checkDouble() {
		return v;
	}
	public String checkJString() {
		return String.valueOf(v);
	}
	public LuaString checkLuaString() {
		return valueOf( String.valueOf(v) );
	}

}
