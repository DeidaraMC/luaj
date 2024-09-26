package org.luaj.vm2;

import org.luaj.vm2.exception.LuaArgumentException;

/**
 * Class to encapsulate varargs values, either as part of a variable argument list, or multiple return values.
 * <p>
 * To construct varargs, use one of the static methods such as
 * {@code LuaValue.varargsOf(LuaValue,LuaValue)}
 * <p>
 * <p>
 * Any LuaValue can be used as a stand-in for Varargs, for both calls and return values.
 * When doing so, nargs() will return 1 and arg1() or arg(1) will return this.
 * This simplifies the case when calling or implementing varargs functions with only
 * 1 argument or 1 return value.
 * <p>
 * Varargs can also be derived from other varargs by appending to the front with a call
 * such as  {@code LuaValue.varargsOf(LuaValue,Varargs)}
 * or by taking a portion of the args using {@code Varargs.subargs(int start)}
 * <p>
 * @see LuaValue#varargsOf(LuaValue[])
 * @see LuaValue#varargsOf(LuaValue, Varargs)
 * @see LuaValue#varargsOf(LuaValue[], Varargs)
 * @see LuaValue#varargsOf(LuaValue, LuaValue, Varargs)
 * @see LuaValue#varargsOf(LuaValue[], int, int)
 * @see LuaValue#varargsOf(LuaValue[], int, int, Varargs)
 * @see LuaValue#subArgs(int)
 */
public abstract class Varargs {

	/**
	 * Get the n-th argument value (1-based).
	 * @param i the index of the argument to get, 1 is the first argument
	 * @return Value at position i, or LuaValue.NIL if there is none.
	 * @see Varargs#arg1()
	 * @see LuaValue#NIL
	 */
	abstract public LuaValue arg( int i );
	
	/**
	 * Get the number of arguments, or 0 if there are none.
	 * @return number of arguments.
	 */
	abstract public int narg();
	
	/**
	 * Get the first argument in the list.
	 * @return LuaValue which is first in the list, or LuaValue.NIL if there are no values.
	 * @see Varargs#arg(int)
	 * @see LuaValue#NIL
	 */
	abstract public LuaValue arg1();

	/**
	 * Evaluate any pending tail call and return result.
	 * @return the evaluated tail call result
	 */
	public Varargs eval() { return this; }
	
	/**
	 * Return true if this is a TailcallVarargs
	 * @return true if a tail call, false otherwise
	 */
	public boolean isTailcall() {
		return false;
	}
	
	// -----------------------------------------------------------------------
	// utilities to get specific arguments and type-check them.
	// -----------------------------------------------------------------------
	
	/** Gets the type of argument {@code i}
	 * @param i the index of the argument to convert, 1 is the first argument
	 * @return int value corresponding to one of the LuaValue integer type values
	 * @see LuaValue#TNIL
	 * @see LuaValue#TBOOLEAN
	 * @see LuaValue#TNUMBER
	 * @see LuaValue#TSTRING
	 * @see LuaValue#TTABLE
	 * @see LuaValue#TFUNCTION
	 * @see LuaValue#TUSERDATA
	 * @see LuaValue#TTHREAD
	 * */
	public int type(int i)             { return arg(i).getType(); }
	
	/** Tests if argument i is nil.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return true if the argument is nil or does not exist, false otherwise
	 * @see LuaValue#TNIL
	 * */
	public boolean isNil(int i)        { return arg(i).isNil(); }

	/** Tests if argument i is a function.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return true if the argument exists and is a function or closure, false otherwise
	 * @see LuaValue#TFUNCTION
	 * */
	public boolean isFunction(int i)   { return arg(i).isFunction(); }

	/** Tests if argument i is a number.
	 * Since anywhere a number is required, a string can be used that
	 * is a number, this will return true for both numbers and
	 * strings that can be interpreted as numbers.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return true if the argument exists and is a number or
	 * string that can be interpreted as a number, false otherwise
	 * @see LuaValue#TNUMBER
	 * @see LuaValue#TSTRING
	 * */
	public boolean isNumber(int i)     { return arg(i).isNumber(); }

	/** Tests if argument i is a string.
	 * Since all lua numbers can be used where strings are used,
	 * this will return true for both strings and numbers.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return true if the argument exists and is a string or number, false otherwise
	 * @see LuaValue#TNUMBER
	 * @see LuaValue#TSTRING
	 * */
	public boolean isString(int i)     { return arg(i).isString(); }

	/** Tests if argument i is a table.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return true if the argument exists and is a lua table, false otherwise
	 * @see LuaValue#TTABLE
	 * */
	public boolean isTable(int i)      { return arg(i).isTable(); }

	/** Tests if argument i is a thread.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return true if the argument exists and is a lua thread, false otherwise
	 * @see LuaValue#TTHREAD
	 * */
	public boolean isThread(int i)     { return arg(i).isThread(); }

	/** Tests if argument i is a userdata.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return true if the argument exists and is a userdata, false otherwise
	 * @see LuaValue#TUSERDATA
	 * */
	public boolean isUserdata(int i)   { return arg(i).isUserdata(); }

	/** Tests if a value exists at argument i.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return true if the argument exists, false otherwise
	 * */
	public boolean isValue(int i)      { return i>0 && i<=narg(); }
	
	/** Return argument i as a boolean value, {@code defval} if nil, or throw a LuaException if any other type.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return true if argument i is boolean true, false if it is false, or defval if not supplied or nil
	 * @exception LuaException if the argument is not a lua boolean
	 * */
	public boolean optionalBoolean(int i, boolean defval)          { return arg(i).optionalBoolean(defval); }

	/** Return argument i as a closure, {@code defval} if nil, or throw a LuaException if any other type.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return LuaClosure if argument i is a closure, or defval if not supplied or nil
	 * @exception LuaException if the argument is not a lua closure
	 * */
	public LuaClosure optionalClosure(int i, LuaClosure defval)       { return arg(i).optionalClosure(defval); }

	/** Return argument i as a double, {@code defval} if nil, or throw a LuaException if it cannot be converted to one.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return java double value if argument i is a number or string that converts to a number, or defval if not supplied or nil
	 * @exception LuaException if the argument is not a number
	 * */
	public double optionalDouble(int i, double defval)            { return arg(i).optionalDouble(defval); }

	/** Return argument i as a function, {@code defval} if nil, or throw a LuaException  if an incompatible type.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return LuaValue that can be called if argument i is lua function or closure, or defval if not supplied or nil
	 * @exception LuaException if the argument is not a lua function or closure
	 * */
	public LuaFunction optionalFunction(int i, LuaFunction defval)     { return arg(i).optionalFunction(defval); }

	/** Return argument i as a java int value, discarding any fractional part, {@code defval} if nil, or throw a LuaException  if not a number.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return int value with fraction discarded and truncated if necessary if argument i is number, or defval if not supplied or nil
	 * @exception LuaException if the argument is not a number
	 * */
	public int optionalInt(int i, int defval)                  { return arg(i).optionalInt(defval); }

	/** Return argument i as a java int value, {@code defval} if nil, or throw a LuaException  if not a number or is not representable by a java int.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return LuaInteger value that fits in a java int without rounding, or defval if not supplied or nil
	 * @exception LuaException if the argument cannot be represented by a java int value
	 * */
	public LuaInteger optionalInteger(int i, LuaInteger defval)       { return arg(i).optionalInteger(defval); }

	/** Return argument i as a java long value, discarding any fractional part, {@code defval} if nil, or throw a LuaException  if not a number.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return long value with fraction discarded and truncated if necessary if argument i is number, or defval if not supplied or nil
	 * @exception LuaException if the argument is not a number
	 * */
	public long optionalLong(int i, long defval)                { return arg(i).optionalLong(defval); }

	/** Return argument i as a LuaNumber, {@code defval} if nil, or throw a LuaException  if not a number or string that can be converted to a number.
	 * @param i the index of the argument to test, 1 is the first argument, or defval if not supplied or nil
	 * @return LuaNumber if argument i is number or can be converted to a number
	 * @exception LuaException if the argument is not a number
	 * */
	public LuaNumber optionalNumber(int i, LuaNumber defval)         { return arg(i).optionalNumber(defval); }

	/** Return argument i as a java String if a string or number, {@code defval} if nil, or throw a LuaException  if any other type
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return String value if argument i is a string or number, or defval if not supplied or nil
	 * @exception LuaException if the argument is not a string or number
	 * */
	public String optionalJString(int i, String defval)           { return arg(i).optionalJString(defval); }

	/** Return argument i as a LuaString if a string or number, {@code defval} if nil, or throw a LuaException  if any other type
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return LuaString value if argument i is a string or number, or defval if not supplied or nil
	 * @exception LuaException if the argument is not a string or number
	 * */
	public LuaString optionalLuaString(int i, LuaString defval)         { return arg(i).optionalLuaString(defval); }

	/** Return argument i as a LuaTable if a lua table, {@code defval} if nil, or throw a LuaException  if any other type.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return LuaTable value if a table, or defval if not supplied or nil
	 * @exception LuaException if the argument is not a lua table
	 * */
	public LuaTable optionalTable(int i, LuaTable defval)           { return arg(i).optionalTable(defval); }

	/** Return argument i as a LuaThread if a lua thread, {@code defval} if nil, or throw a LuaException  if any other type.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return LuaThread value if a thread, or defval if not supplied or nil
	 * @exception LuaException if the argument is not a lua thread
	 * */
	public LuaThread optionalThread(int i, LuaThread defval)         { return arg(i).optionalThread(defval); }

	/** Return argument i as a java Object if a userdata, {@code defval} if nil, or throw a LuaException  if any other type.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return java Object value if argument i is a userdata, or defval if not supplied or nil
	 * @exception LuaException if the argument is not a userdata
	 * */
	public Object optionalUserdata(int i, Object defval)          { return arg(i).optionalUserdata(defval); }

	/** Return argument i as a java Object if it is a userdata whose instance Class c or a subclass,
	 * {@code defval} if nil, or throw a LuaException  if any other type.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @param type the class to which the userdata instance must be assignable
	 * @return java Object value if argument i is a userdata whose instance Class c or a subclass, or defval if not supplied or nil
	 * @exception LuaException if the argument is not a userdata or from whose instance c is not assignable
	 * */
	public <T> T optionalUserdata(int i, Class<T> type, Object defval) { return arg(i).optionalUserdata(type,defval); }

	/** Return argument i as a LuaValue if it exists, or {@code defval}.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return LuaValue value if the argument exists, defval if not
	 * @exception LuaException if the argument does not exist.
	 * */
	public LuaValue optionalValue(int i, LuaValue defval)           { return i>0 && i<=narg()? arg(i): defval; }

	/** Return argument i as a boolean value, or throw an error if any other type.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return true if argument i is boolean true, false if it is false
	 * @exception LuaException if the argument is not a lua boolean
	 * */
	public boolean checkBoolean(int i)          { return arg(i).checkBoolean(); }

	/** Return argument i as a closure, or throw an error if any other type.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return LuaClosure if argument i is a closure.
	 * @exception LuaException if the argument is not a lua closure
	 * */
	public LuaClosure checkClosure(int i)          { return arg(i).checkClosure(); }

	/** Return argument i as a double, or throw an error if it cannot be converted to one.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return java double value if argument i is a number or string that converts to a number
	 * @exception LuaException if the argument is not a number
	 * */
	public double checkDouble(int i)           { return arg(i).checkDouble(); }

	/** Return argument i as a function, or throw an error if an incompatible type.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return LuaValue that can be called if argument i is lua function or closure
	 * @exception LuaException if the argument is not a lua function or closure
	 * */
	public LuaFunction checkFunction(int i)         { return arg(i).checkFunction(); }

	/** Return argument i as a java int value, or throw an error if it cannot be converted to one.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return int value if argument i is a number or string that converts to a number
	 * @exception LuaException if the argument cannot be represented by a java int value
	 * */
	public int checkInt(int i)              { return arg(i).checkInt(); }

	/** Return argument i as a java int value, or throw an error if not a number or is not representable by a java int.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return LuaInteger value that fits in a java int without rounding
	 * @exception LuaException if the argument cannot be represented by a java int value
	 * */
	public LuaInteger checkInteger(int i)          { return arg(i).checkInteger(); }

	/** Return argument i as a java long value, or throw an error if it cannot be converted to one.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return long value if argument i is a number or string that converts to a number
	 * @exception LuaException if the argument cannot be represented by a java long value
	 * */
	public long checkLong(int i)             { return arg(i).checkLong(); }

	/** Return argument i as a LuaNumber, or throw an error if not a number or string that can be converted to a number.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return LuaNumber if argument i is number or can be converted to a number
	 * @exception LuaException if the argument is not a number
	 * */
	public LuaNumber checkNumber(int i)           { return arg(i).checkNumber(); }

	/** Return argument i as a java String if a string or number, or throw an error if any other type
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return String value if argument i is a string or number
	 * @exception LuaException if the argument is not a string or number
	 * */
	public String checkJString(int i)          { return arg(i).checkJString(); }

	/** Return argument i as a LuaString if a string or number, or throw an error if any other type
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return LuaString value if argument i is a string or number
	 * @exception LuaException if the argument is not a string or number
	 * */
	public LuaString checkLuaString(int i)           { return arg(i).checkLuaString(); }

	/** Return argument i as a LuaTable if a lua table, or throw an error if any other type.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return LuaTable value if a table
	 * @exception LuaException if the argument is not a lua table
	 * */
	public LuaTable checkTable(int i)            { return arg(i).checkTable(); }

	/** Return argument i as a LuaThread if a lua thread, or throw an error if any other type.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return LuaThread value if a thread
	 * @exception LuaException if the argument is not a lua thread
	 * */
	public LuaThread checkThread(int i)           { return arg(i).checkThread(); }

	/** Return argument i as a java Object if a userdata, or throw an error if any other type.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return java Object value if argument i is a userdata
	 * @exception LuaException if the argument is not a userdata
	 * */
	public Object checkUserdata(int i)         { return arg(i).checkUserdata(); }

	/** Return argument i as a java Object if it is a userdata whose instance Class type or a subclass,
	 * or throw an error if any other type.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @param type the class to which the userdata instance must be assignable
	 * @return java Object value if argument i is a userdata whose instance Class type or a subclass
	 * @exception LuaException if the argument is not a userdata or from whose instance type is not assignable
	 * */
	public <T> T checkUserdata(int i, Class<T> type) { return arg(i).checkUserdata(type); }

	/** Return argument i as a LuaValue if it exists, or throw an error.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return LuaValue value if the argument exists
	 * @exception LuaException if the argument does not exist.
	 * */
	public LuaValue checkValue(int i) {
		if (i <= narg()) return arg(i);
		throw new LuaArgumentException(i,"value expected");
	}

	/** Return argument i as a LuaValue if it is not nil, or throw an error if it is nil.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return LuaValue value if the argument is not nil
	 * @exception LuaException if the argument doesn't exist or evaluates to nil.
	 * */
	public LuaValue checkNotNil(int i)           { return arg(i).checkNotNil(); }
	
	/** Performs test on argument i as a LuaValue when a user-supplied assertion passes, or throw an error.
	 * Returns normally if the value of {@code test} is {@code true}, otherwise throws and argument error with
	 * the supplied message, {@code msg}.
	 * @param test user supplied assertion to test against
	 * @param i the index to report in any error message
	 * @param msg the error message to use when the test fails
	 * @exception LuaException if the the value of {@code test} is {@code false}
	 * */
	public void argCheck(boolean test, int i, String msg) { if (!test) throw new LuaArgumentException(i,msg); }
	
	/** Return true if there is no argument or nil at argument i.
	 * @param i the index of the argument to test, 1 is the first argument
	 * @return true if argument i contains either no argument or nil
	 * */
	public boolean isNoneOrNil(int i) {
		return i>narg() || arg(i).isNil();
	}
	
	/** Convert argument {@code i} to java boolean based on lua rules for boolean evaluation.
	 * @param i the index of the argument to convert, 1 is the first argument
	 * @return {@code false} if argument i is nil or false, otherwise {@code true}
	 * */
	public boolean toBoolean(int i)           { return arg(i).toBoolean(); }

	/** Return argument i as a java byte value, discarding any fractional part and truncating,
	 * or 0 if not a number.
	 * @param i the index of the argument to convert, 1 is the first argument
	 * @return byte value with fraction discarded and truncated if necessary if argument i is number, otherwise 0
	 * */
	public byte toByte(int i)              { return arg(i).toByte(); }
	
	/** Return argument i as a java char value, discarding any fractional part and truncating,
	 * or 0 if not a number.
	 * @param i the index of the argument to convert, 1 is the first argument
	 * @return char value with fraction discarded and truncated if necessary if argument i is number, otherwise 0
	 * */
	public char toChar(int i)              { return arg(i).tochar(); }

	/** Return argument i as a java double value or 0 if not a number.
	 * @param i the index of the argument to convert, 1 is the first argument
	 * @return double value if argument i is number, otherwise 0
	 * */
	public double toDouble(int i)            { return arg(i).toDouble(); }

	/** Return argument i as a java float value, discarding excess fractional part and truncating,
	 * or 0 if not a number.
	 * @param i the index of the argument to convert, 1 is the first argument
	 * @return float value with excess fraction discarded and truncated if necessary if argument i is number, otherwise 0
	 * */
	public float toFloat(int i)             { return arg(i).toFloat(); }
	
	/** Return argument i as a java int value, discarding any fractional part and truncating,
	 * or 0 if not a number.
	 * @param i the index of the argument to convert, 1 is the first argument
	 * @return int value with fraction discarded and truncated if necessary if argument i is number, otherwise 0
	 * */
	public int toInt(int i)               { return arg(i).toInt(); }

	/** Return argument i as a java long value, discarding any fractional part and truncating,
	 * or 0 if not a number.
	 * @param i the index of the argument to convert, 1 is the first argument
	 * @return long value with fraction discarded and truncated if necessary if argument i is number, otherwise 0
	 * */
	public long toLong(int i)              { return arg(i).toLong(); }

	/** Return argument i as a java String based on the type of the argument.
	 * @param i the index of the argument to convert, 1 is the first argument
	 * @return String value representing the type
	 * */
	public String toJString(int i)           { return arg(i).toJString(); }
	
	/** Return argument i as a java short value, discarding any fractional part and truncating,
	 * or 0 if not a number.
	 * @param i the index of the argument to convert, 1 is the first argument
	 * @return short value with fraction discarded and truncated if necessary if argument i is number, otherwise 0
	 * */
	public short toShort(int i)             { return arg(i).toShort(); }

	/** Return argument i as a java Object if a userdata, or null.
	 * @param i the index of the argument to convert, 1 is the first argument
	 * @return java Object value if argument i is a userdata, otherwise null
	 * */
	public Object toUserdata(int i)          { return arg(i).toUserdata(); }

	/** Return argument i as a java Object if it is a userdata whose instance Class c or a subclass, or null.
	 * @param i the index of the argument to convert, 1 is the first argument
	 * @param type the class to which the userdata instance must be assignable
	 * @return java Object value if argument i is a userdata whose instance Class c or a subclass, otherwise null
	 * */
	public <T> T toUserdata(int i, Class<T> type)  { return arg(i).toUserdata(type); }
	
	/** Convert the list of varargs values to a human readable java String.
	 * @return String value in human readable form such as {1,2}.
	 */
	public String toJString() {
		Buffer sb = new Buffer();
		sb.append( "(" );
		for ( int i=1,n=narg(); i<=n; i++ ) {
			if (i>1) sb.append( "," );
			sb.append( arg(i).toJString() );
		}
		sb.append( ")" );
		return sb.tojstring();
	}
	
	/** Convert the value or values to a java String using Varargs.tojstring()
	 * @return String value in human readable form.
	 * @see Varargs#toJString()
	 */
	public String toString() { return toJString(); }

	/**
	 * Create a {@code Varargs} instance containing arguments starting at index {@code start}
	 * @param start the index from which to include arguments, where 1 is the first argument.
	 * @return Varargs containing argument { start, start+1,  ... , narg-start-1 }
	 */
	abstract public Varargs subArgs(final int start);

	/**
	 * Implementation of Varargs for use in the Varargs.subargs() function.
	 * @see Varargs#subArgs(int)
	 */
	static class SubVarargs extends Varargs {
		private final Varargs v;
		private final int start;
		private final int end;
		public SubVarargs(Varargs varargs, int start, int end) {
			this.v = varargs;
			this.start = start;
			this.end = end;
		}
		public LuaValue arg(int i) {
			i += start-1;
			return i>=start && i<=end? v.arg(i): LuaValue.NIL;
		}
		public LuaValue arg1() {
			return v.arg(start);
		}
		public int narg() {
			return end+1-start;
		}
		public Varargs subArgs(final int start) {
			if (start == 1)
				return this;
			final int newstart = this.start + start - 1;
			if (start > 0) {
				if (newstart >= this.end)
					return LuaValue.NONE;
				if (newstart == this.end)
					return v.arg(this.end);
				if (newstart == this.end-1)
					return new Varargs.PairVarargs(v.arg(this.end-1), v.arg(this.end));
				return new SubVarargs(v, newstart, this.end);
			}
			return new SubVarargs(v, newstart, this.end);
		}
	}

	/** Varargs implemenation backed by two values.
	 * <p>
	 * This is an internal class not intended to be used directly.
	 * Instead use the corresponding static method on LuaValue.
	 * 
	 * @see LuaValue#varargsOf(LuaValue, Varargs)
	 */
	static final class PairVarargs extends Varargs {
		private final LuaValue v1;
		private final Varargs v2;
		/** Construct a Varargs from an two LuaValue.
		 * <p>
		 * This is an internal class not intended to be used directly.
		 * Instead use the corresponding static method on LuaValue.
		 * 
		 * @see LuaValue#varargsOf(LuaValue, Varargs)
		 */
		PairVarargs(LuaValue v1, Varargs v2) {
			this.v1 = v1;
			this.v2 = v2;
		}
		public LuaValue arg(int i) {
			return i==1? v1: v2.arg(i-1);
		}
		public int narg() {
			return 1+v2.narg();
		}
		public LuaValue arg1() {
			return v1;
		}
		public Varargs subArgs(final int start) {
			if (start == 1)
				return this;
			if (start == 2)
				return v2;
			if (start > 2)
				return v2.subArgs(start - 1);
			throw new LuaArgumentException(1, "start must be > 0");
		}
	}

	/** Varargs implemenation backed by an array of LuaValues
	 * <p>
	 * This is an internal class not intended to be used directly.
	 * Instead use the corresponding static methods on LuaValue.
	 * 
	 * @see LuaValue#varargsOf(LuaValue[])
	 * @see LuaValue#varargsOf(LuaValue[], Varargs)
	 */
	static final class ArrayVarargs extends Varargs {
		private final LuaValue[] v;
		private final Varargs r;
		/** Construct a Varargs from an array of LuaValue.
		 * <p>
		 * This is an internal class not intended to be used directly.
		 * Instead use the corresponding static methods on LuaValue.
		 * 
		 * @see LuaValue#varargsOf(LuaValue[])
		 * @see LuaValue#varargsOf(LuaValue[], Varargs)
		 */
		ArrayVarargs(LuaValue[] v, Varargs r) {
			this.v = v;
			this.r = r ;
		}
		public LuaValue arg(int i) {
			return i < 1 ? LuaValue.NIL: i <= v.length? v[i - 1]: r.arg(i-v.length);
		}
		public int narg() {
			return v.length+r.narg();
		}
		public LuaValue arg1() { return v.length>0? v[0]: r.arg1(); }
		public Varargs subArgs(int start) {
			if (start <= 0)
				throw new LuaArgumentException(1, "start must be > 0");
			if (start == 1)
				return this;
			if (start > v.length)
				return r.subArgs(start - v.length);
			return LuaValue.varargsOf(v, start - 1, v.length - (start - 1), r);
		}
		void copyTo(LuaValue[] dest, int offset, int length) {
			int n = Math.min(v.length, length);
			System.arraycopy(v, 0, dest, offset, n);
			r.copyTo(dest, offset + n, length - n);
		}
	}

	/** Varargs implemenation backed by an array of LuaValues
	 * <p>
	 * This is an internal class not intended to be used directly.
	 * Instead use the corresponding static methods on LuaValue.
	 * 
	 * @see LuaValue#varargsOf(LuaValue[], int, int)
	 * @see LuaValue#varargsOf(LuaValue[], int, int, Varargs)
	 */
	static final class ArrayPartVarargs extends Varargs {
		private final int offset;
		private final LuaValue[] v;
		private final int length;
		private final Varargs more;
		/** Construct a Varargs from an array of LuaValue.
		 * <p>
		 * This is an internal class not intended to be used directly.
		 * Instead use the corresponding static methods on LuaValue.
		 * 
		 * @see LuaValue#varargsOf(LuaValue[], int, int)
		 */
		ArrayPartVarargs(LuaValue[] v, int offset, int length) {
			this.v = v;
			this.offset = offset;
			this.length = length;
			this.more = LuaValue.NONE;
		}
		/** Construct a Varargs from an array of LuaValue and additional arguments.
		 * <p>
		 * This is an internal class not intended to be used directly.
		 * Instead use the corresponding static method on LuaValue.
		 * 
		 * @see LuaValue#varargsOf(LuaValue[], int, int, Varargs)
		 */
		public ArrayPartVarargs(LuaValue[] v, int offset, int length, Varargs more) {
			this.v = v;
			this.offset = offset;
			this.length = length;
			this.more = more;
		}
		public LuaValue arg(final int i) {
			return i < 1? LuaValue.NIL: i <= length? v[offset+i-1]: more.arg(i-length);
		}
		public int narg() {
			return length + more.narg();
		}
		public LuaValue arg1() {
			return length>0? v[offset]: more.arg1();
		}
		public Varargs subArgs(int start) {
			if (start <= 0)
				throw new LuaArgumentException(1, "start must be > 0");
			if (start == 1)
				return this;
			if (start > length)
				return more.subArgs(start - length);
			return LuaValue.varargsOf(v, offset + start - 1, length - (start - 1), more);
		}
		void copyTo(LuaValue[] dest, int offset, int length) {
			int n = Math.min(this.length, length);
			System.arraycopy(this.v, this.offset, dest, offset, n);
			more.copyTo(dest, offset + n, length - n);
		}
	}

	/** Copy values in a varargs into a destination array.
	 * Internal utility method not intended to be called directly from user code.
	 * @return Varargs containing same values, but flattened.
	 */
	void copyTo(LuaValue[] dest, int offset, int length) {
		for (int i=0; i<length; ++i)
			dest[offset+i] = arg(i+1);
	}

	/** Return Varargs that cannot be using a shared array for the storage, and is flattened.
	 * Internal utility method not intended to be called directly from user code.
	 * @return Varargs containing same values, but flattened and with a new array if needed.
	 * @exclude
	 * @hide
	 */
	public Varargs deAlias() {
		int n = narg();
		switch (n) {
		case 0: return LuaValue.NONE;
		case 1: return arg1();
		case 2: return new PairVarargs(arg1(), arg(2));
		default:
			LuaValue[] v = new LuaValue[n];
			copyTo(v, 0, n);
			return new ArrayVarargs(v, LuaValue.NONE);
		}
	}
}
