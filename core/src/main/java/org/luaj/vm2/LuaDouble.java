package org.luaj.vm2;

import org.luaj.vm2.exception.LuaTypeException;
import org.luaj.vm2.lib.MathLib;

/**
 * Extension of {@link LuaNumber} which can hold a Java double as its value.
 * <p>
 * These instance are not instantiated directly by clients, but indirectly
 * via the static functions {@link LuaValue#valueOf(int)} or {@link LuaValue#valueOf(double)}
 * functions.  This ensures that values which can be represented as int
 * are wrapped in {@link LuaInteger} instead of {@link LuaDouble}.
 * <p>
 * Almost all API's implemented in LuaDouble are defined and documented in {@link LuaValue}.
 * <p>
 * However the constants {@link #NAN}, {@link #POSINF}, {@link #NEGINF},
 * {@link #JSTR_NAN}, {@link #JSTR_POSINF}, and {@link #JSTR_NEGINF} may be useful
 * when dealing with Nan or Infinite values.
 * <p>
 * LuaDouble also defines functions for handling the unique math rules of lua devision and modulo in
 * <ul>
 * <li>{@link #ddiv(double, double)}</li>
 * <li>{@link #ddiv_d(double, double)}</li>
 * <li>{@link #dmod(double, double)}</li>
 * <li>{@link #dmod_d(double, double)}</li>
 * </ul>
 * <p>
 * @see LuaValue
 * @see LuaNumber
 * @see LuaInteger
 * @see LuaValue#valueOf(int)
 * @see LuaValue#valueOf(double)
 */
public class LuaDouble extends LuaNumber {

	/** Constant LuaDouble representing NaN (not a number) */
	public static final LuaDouble NAN    = new LuaDouble( Double.NaN );
	
	/** Constant LuaDouble representing positive infinity */
	public static final LuaDouble POSINF = new LuaDouble( Double.POSITIVE_INFINITY );
	
	/** Constant LuaDouble representing negative infinity */
	public static final LuaDouble NEGINF = new LuaDouble( Double.NEGATIVE_INFINITY );
	
	/** Constant String representation for NaN (not a number), "nan" */
	public static final String JSTR_NAN    = "nan";
	
	/** Constant String representation for positive infinity, "inf" */
	public static final String JSTR_POSINF = "inf";

	/** Constant String representation for negative infinity, "-inf" */
	public static final String JSTR_NEGINF = "-inf";
	
	/** The value being held by this instance. */
	final double v;

	public static LuaNumber valueOf(double d) {
		return d == (int) d ? LuaInteger.valueOf((int) d) : new LuaDouble(d);
	}
	
	/** Don't allow ints to be boxed by DoubleValues  */
	public LuaDouble(double d) {
		this.v = d;
	}

	public int hashCode() {
		long l = Double.doubleToLongBits(v + 1);
		return ((int)(l>>32)) + (int) l;
	}
	
	public boolean isLong() {
		return v == (long) v;
	}
	
	public byte toByte()        { return (byte) (long) v; }
	public char    tochar()        { return (char) (long) v; }
	public double toDouble()      { return v; }
	public float toFloat()       { return (float) v; }
	public int toInt()         { return (int) (long) v; }
	public long toLong()        { return (long) v; }
	public short toShort()       { return (short) (long) v; }

	public double optionalDouble(double defval)        { return v; }
	public int optionalInt(int defval)              { return (int) (long) v;  }
	public LuaInteger optionalInteger(LuaInteger defval)   { return LuaInteger.valueOf((int) (long)v); }
	public long optionalLong(long defval)            { return (long) v; }
	
	public LuaInteger checkInteger()                  { return LuaInteger.valueOf( (int) (long) v ); }
	
	// unary operators
	public LuaValue neg() { return valueOf(-v); }
	
	// object equality, used for key comparison
	public boolean equals(Object o) { return o instanceof LuaDouble? ((LuaDouble)o).v == v: false; }
	
	// equality w/ metatable processing
	public LuaValue eq( LuaValue val )        { return val.raweq(v)? TRUE: FALSE; }
	public boolean eq_b( LuaValue val )       { return val.raweq(v); }

	// equality w/o metatable processing
	public boolean raweq( LuaValue val )      { return val.raweq(v); }
	public boolean raweq( double val )        { return v == val; }
	public boolean raweq( int val )           { return v == val; }
	
	// basic binary arithmetic
	public LuaValue   add( LuaValue rhs )        { return rhs.add(v); }
	public LuaValue   add( double lhs )     { return LuaDouble.valueOf(lhs + v); }
	public LuaValue   sub( LuaValue rhs )        { return rhs.subFrom(v); }
	public LuaValue   sub( double rhs )        { return LuaDouble.valueOf(v - rhs); }
	public LuaValue   sub( int rhs )        { return LuaDouble.valueOf(v - rhs); }
	public LuaValue   subFrom( double lhs )   { return LuaDouble.valueOf(lhs - v); }
	public LuaValue   mul( LuaValue rhs )        { return rhs.mul(v); }
	public LuaValue   mul( double lhs )   { return LuaDouble.valueOf(lhs * v); }
	public LuaValue   mul( int lhs )      { return LuaDouble.valueOf(lhs * v); }
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
	
	
	/** Divide two double numbers according to lua math, and return a {@link LuaValue} result.
	 * @param lhs Left-hand-side of the division.
	 * @param rhs Right-hand-side of the division.
	 * @return {@link LuaValue} for the result of the division,
	 * taking into account positive and negiative infinity, and Nan
	 * @see #ddiv_d(double, double)
	 */
	public static LuaValue ddiv(double lhs, double rhs) {
		return rhs!=0? valueOf( lhs / rhs ): lhs>0? POSINF: lhs==0? NAN: NEGINF;
	}
	
	/** Divide two double numbers according to lua math, and return a double result.
	 * @param lhs Left-hand-side of the division.
	 * @param rhs Right-hand-side of the division.
	 * @return Value of the division, taking into account positive and negative infinity, and Nan
	 * @see #ddiv(double, double)
	 */
	public static double ddiv_d(double lhs, double rhs) {
		return rhs!=0? lhs / rhs: lhs>0? Double.POSITIVE_INFINITY: lhs==0? Double.NaN: Double.NEGATIVE_INFINITY;
	}
	
	/** Take modulo double numbers according to lua math, and return a {@link LuaValue} result.
	 * @param lhs Left-hand-side of the modulo.
	 * @param rhs Right-hand-side of the modulo.
	 * @return {@link LuaValue} for the result of the modulo,
	 * using lua's rules for modulo
	 * @see #dmod_d(double, double)
	 */
	public static LuaValue dmod(double lhs, double rhs) {
		if (rhs == 0 || lhs == Double.POSITIVE_INFINITY || lhs == Double.NEGATIVE_INFINITY) return NAN;
		if (rhs == Double.POSITIVE_INFINITY) {
			return lhs < 0 ? POSINF : valueOf(lhs);
		}
		if (rhs == Double.NEGATIVE_INFINITY) {
			return lhs > 0 ? NEGINF : valueOf(lhs);
		}
		return valueOf( lhs-rhs*Math.floor(lhs/rhs) );
	}

	/** Take modulo for double numbers according to lua math, and return a double result.
	 * @param lhs Left-hand-side of the modulo.
	 * @param rhs Right-hand-side of the modulo.
	 * @return double value for the result of the modulo,
	 * using lua's rules for modulo
	 * @see #dmod(double, double)
	 */
	public static double dmod_d(double lhs, double rhs) {
		if (rhs == 0 || lhs == Double.POSITIVE_INFINITY || lhs == Double.NEGATIVE_INFINITY) return Double.NaN;
		if (rhs == Double.POSITIVE_INFINITY) {
			return lhs < 0 ? Double.POSITIVE_INFINITY : lhs;
		}
		if (rhs == Double.NEGATIVE_INFINITY) {
			return lhs > 0 ? Double.NEGATIVE_INFINITY : lhs;
		}
		return lhs-rhs*Math.floor(lhs/rhs);
	}

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
			
	public String toJString() {
		/*
		if ( v == 0.0 ) { // never occurs in J2me
			long bits = Double.doubleToLongBits( v );
			return ( bits >> 63 == 0 ) ? "0" : "-0";
		}
		*/
		long l = (long) v;
		if ( l == v )
			return Long.toString(l);
		if ( Double.isNaN(v) )
			return JSTR_NAN;
		if ( Double.isInfinite(v) )
			return (v<0? JSTR_NEGINF: JSTR_POSINF);
		return Float.toString((float)v);
	}
	
	public LuaString strvalue() {
		return LuaString.valueOf(toJString());
	}
	
	public LuaString optionalLuaString(LuaString defval) {
		return LuaString.valueOf(toJString());
	}
		
	public LuaValue toLuaString() {
		return LuaString.valueOf(toJString());
	}
	
	public String optionalJString(String defval) {
		return toJString();
	}
	
	public LuaNumber optionalNumber(LuaNumber defval) {
		return this;
	}
	
	public boolean isNumber() {
		return true;
	}
	
	public boolean isString() {
		return true;
	}
	
	public LuaValue toNumber() {
		return this;
	}
	public int checkInt()                { return (int) (long) v; }
	public long checkLong()              { return (long) v; }
	public LuaNumber checkNumber()       { return this; }
	public double checkDouble()          { return v; }
	
	public String checkJString() {
		return toJString();
	}
	public LuaString checkLuaString() {
		return LuaString.valueOf(toJString());
	}
	
	public boolean isValidKey() {
		return !Double.isNaN(v);
	}
}
