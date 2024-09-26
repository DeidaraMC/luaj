/*******************************************************************************
 * Copyright (c) 2009 Luaj.org. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ******************************************************************************/
package org.luaj.vm2;

import java.lang.reflect.InvocationTargetException;

import junit.framework.TestCase;

import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.JsePlatform;

public class TypeTest extends TestCase {
	static {
		JsePlatform.debugGlobals();
	}
	
	private final int sampleint = 77;
	private final long samplelong = 123400000000L;
	private final double sampledouble = 55.25;
	private final String samplestringstring = "abcdef";
	private final String samplestringint = String.valueOf(sampleint);
	private final String samplestringlong = String.valueOf(samplelong);
	private final String samplestringdouble = String.valueOf(sampledouble);
	private final Object sampleobject = new Object();
	private final MyData sampledata = new MyData();
	
	private final LuaValue somenil       = LuaValue.NIL;
	private final LuaValue sometrue      = LuaValue.TRUE;
	private final LuaValue somefalse     = LuaValue.FALSE;
	private final LuaValue zero          = LuaValue.ZERO;
	private final LuaValue intint        = LuaValue.valueOf(sampleint);
	private final LuaValue longdouble    = LuaValue.valueOf(samplelong);
	private final LuaValue doubledouble  = LuaValue.valueOf(sampledouble);
	private final LuaValue stringstring  = LuaValue.valueOf(samplestringstring);
	private final LuaValue stringint     = LuaValue.valueOf(samplestringint);
	private final LuaValue stringlong    = LuaValue.valueOf(samplestringlong);
	private final LuaValue stringdouble  = LuaValue.valueOf(samplestringdouble);
	private final LuaTable    table         = LuaValue.tableOf();
	private final LuaFunction somefunc      = new ZeroArgFunction() { public LuaValue call() { return NONE;}};
	private final LuaThread   thread        = new LuaThread(new Globals(), somefunc);
	private final LuaClosure  someclosure   = new LuaClosure(new Prototype(), new LuaTable());
	private final LuaUserdata userdataobj   = LuaValue.userdataOf(sampleobject);
	private final LuaUserdata userdatacls   = LuaValue.userdataOf(sampledata);
	
	public static final class MyData {
		public MyData() {			
		}
	}
	
	// ===================== type checks =======================
	
	public void testIsBoolean() {
		assertEquals( false, somenil.isBoolean() );
		assertEquals( true, sometrue.isBoolean() );
		assertEquals( true, somefalse.isBoolean() );
		assertEquals( false, zero.isBoolean() );
		assertEquals( false, intint.isBoolean() );
		assertEquals( false, longdouble.isBoolean() );
		assertEquals( false, doubledouble.isBoolean() );
		assertEquals( false, stringstring.isBoolean() );
		assertEquals( false, stringint.isBoolean() );
		assertEquals( false, stringlong.isBoolean() );
		assertEquals( false, stringdouble.isBoolean() );
		assertEquals( false, thread.isBoolean() );
		assertEquals( false, table.isBoolean() );
		assertEquals( false, userdataobj.isBoolean() );
		assertEquals( false, userdatacls.isBoolean() );
		assertEquals( false, somefunc.isBoolean() );
		assertEquals( false, someclosure.isBoolean() );
	}
	
	public void testIsClosure() {
		assertEquals( false, somenil.isClosure() );
		assertEquals( false, sometrue.isClosure() );
		assertEquals( false, somefalse.isClosure() );
		assertEquals( false, zero.isClosure() );
		assertEquals( false, intint.isClosure() );
		assertEquals( false, longdouble.isClosure() );
		assertEquals( false, doubledouble.isClosure() );
		assertEquals( false, stringstring.isClosure() );
		assertEquals( false, stringint.isClosure() );
		assertEquals( false, stringlong.isClosure() );
		assertEquals( false, stringdouble.isClosure() );
		assertEquals( false, thread.isClosure() );
		assertEquals( false, table.isClosure() );
		assertEquals( false, userdataobj.isClosure() );
		assertEquals( false, userdatacls.isClosure() );
		assertEquals( false, somefunc.isClosure() );
		assertEquals( true, someclosure.isClosure() );
	}

	
	public void testIsFunction() {
		assertEquals( false, somenil.isFunction() );
		assertEquals( false, sometrue.isFunction() );
		assertEquals( false, somefalse.isFunction() );
		assertEquals( false, zero.isFunction() );
		assertEquals( false, intint.isFunction() );
		assertEquals( false, longdouble.isFunction() );
		assertEquals( false, doubledouble.isFunction() );
		assertEquals( false, stringstring.isFunction() );
		assertEquals( false, stringint.isFunction() );
		assertEquals( false, stringlong.isFunction() );
		assertEquals( false, stringdouble.isFunction() );
		assertEquals( false, thread.isFunction() );
		assertEquals( false, table.isFunction() );
		assertEquals( false, userdataobj.isFunction() );
		assertEquals( false, userdatacls.isFunction() );
		assertEquals( true, somefunc.isFunction() );
		assertEquals( true, someclosure.isFunction() );
	}

	
	public void testIsInt() {
		assertEquals( false, somenil.isInt() );
		assertEquals( false, sometrue.isInt() );
		assertEquals( false, somefalse.isInt() );
		assertEquals( true, zero.isInt() );
		assertEquals( true, intint.isInt() );
		assertEquals( false, longdouble.isInt() );
		assertEquals( false, doubledouble.isInt() );
		assertEquals( false, stringstring.isInt() );
		assertEquals( true, stringint.isInt() );
		assertEquals( false, stringdouble.isInt() );
		assertEquals( false, thread.isInt() );
		assertEquals( false, table.isInt() );
		assertEquals( false, userdataobj.isInt() );
		assertEquals( false, userdatacls.isInt() );
		assertEquals( false, somefunc.isInt() );
		assertEquals( false, someclosure.isInt() );
	}

	public void testIsIntType() {
		assertEquals( false, somenil.isInteger() );
		assertEquals( false, sometrue.isInteger() );
		assertEquals( false, somefalse.isInteger() );
		assertEquals( true, zero.isInteger() );
		assertEquals( true, intint.isInteger() );
		assertEquals( false, longdouble.isInteger() );
		assertEquals( false, doubledouble.isInteger() );
		assertEquals( false, stringstring.isInteger() );
		assertEquals( false, stringint.isInteger() );
		assertEquals( false, stringlong.isInteger() );
		assertEquals( false, stringdouble.isInteger() );
		assertEquals( false, thread.isInteger() );
		assertEquals( false, table.isInteger() );
		assertEquals( false, userdataobj.isInteger() );
		assertEquals( false, userdatacls.isInteger() );
		assertEquals( false, somefunc.isInteger() );
		assertEquals( false, someclosure.isInteger() );
	}

	public void testIsLong() {
		assertEquals( false, somenil.isLong() );
		assertEquals( false, sometrue.isLong() );
		assertEquals( false, somefalse.isLong() );
		assertEquals( true, intint.isInt() );
		assertEquals( true, longdouble.isLong() );
		assertEquals( false, doubledouble.isLong() );
		assertEquals( false, stringstring.isLong() );
		assertEquals( true, stringint.isLong() );
		assertEquals( true, stringlong.isLong() );
		assertEquals( false, stringdouble.isLong() );
		assertEquals( false, thread.isLong() );
		assertEquals( false, table.isLong() );
		assertEquals( false, userdataobj.isLong() );
		assertEquals( false, userdatacls.isLong() );
		assertEquals( false, somefunc.isLong() );
		assertEquals( false, someclosure.isLong() );
	}

	public void testIsNil() {
		assertEquals( true, somenil.isNil() );
		assertEquals( false, sometrue.isNil() );
		assertEquals( false, somefalse.isNil() );
		assertEquals( false, zero.isNil() );
		assertEquals( false, intint.isNil() );
		assertEquals( false, longdouble.isNil() );
		assertEquals( false, doubledouble.isNil() );
		assertEquals( false, stringstring.isNil() );
		assertEquals( false, stringint.isNil() );
		assertEquals( false, stringlong.isNil() );
		assertEquals( false, stringdouble.isNil() );
		assertEquals( false, thread.isNil() );
		assertEquals( false, table.isNil() );
		assertEquals( false, userdataobj.isNil() );
		assertEquals( false, userdatacls.isNil() );
		assertEquals( false, somefunc.isNil() );
		assertEquals( false, someclosure.isNil() );
	}

	public void testIsNumber() {
		assertEquals( false, somenil.isNumber() );
		assertEquals( false, sometrue.isNumber() );
		assertEquals( false, somefalse.isNumber() );
		assertEquals( true, zero.isNumber() );
		assertEquals( true, intint.isNumber() );
		assertEquals( true, longdouble.isNumber() );
		assertEquals( true, doubledouble.isNumber() );
		assertEquals( false, stringstring.isNumber() );
		assertEquals( true, stringint.isNumber() );
		assertEquals( true, stringlong.isNumber() );
		assertEquals( true, stringdouble.isNumber() );
		assertEquals( false, thread.isNumber() );
		assertEquals( false, table.isNumber() );
		assertEquals( false, userdataobj.isNumber() );
		assertEquals( false, userdatacls.isNumber() );
		assertEquals( false, somefunc.isNumber() );
		assertEquals( false, someclosure.isNumber() );
	}

	public void testIsString() {
		assertEquals( false, somenil.isString() );
		assertEquals( false, sometrue.isString() );
		assertEquals( false, somefalse.isString() );
		assertEquals( true, zero.isString() );
		assertEquals( true, longdouble.isString() );
		assertEquals( true, doubledouble.isString() );
		assertEquals( true, stringstring.isString() );
		assertEquals( true, stringint.isString() );
		assertEquals( true, stringlong.isString() );
		assertEquals( true, stringdouble.isString() );
		assertEquals( false, thread.isString() );
		assertEquals( false, table.isString() );
		assertEquals( false, userdataobj.isString() );
		assertEquals( false, userdatacls.isString() );
		assertEquals( false, somefunc.isString() );
		assertEquals( false, someclosure.isString() );
	}

	public void testIsThread() {
		assertEquals( false, somenil.isThread() );
		assertEquals( false, sometrue.isThread() );
		assertEquals( false, somefalse.isThread() );
		assertEquals( false, intint.isThread() );
		assertEquals( false, longdouble.isThread() );
		assertEquals( false, doubledouble.isThread() );
		assertEquals( false, stringstring.isThread() );
		assertEquals( false, stringint.isThread() );
		assertEquals( false, stringdouble.isThread() );
		assertEquals( true, thread.isThread() );
		assertEquals( false, table.isThread() );
		assertEquals( false, userdataobj.isThread() );
		assertEquals( false, userdatacls.isThread() );
		assertEquals( false, somefunc.isThread() );
		assertEquals( false, someclosure.isThread() );
	}

	public void testIsTable() {
		assertEquals( false, somenil.isTable() );
		assertEquals( false, sometrue.isTable() );
		assertEquals( false, somefalse.isTable() );
		assertEquals( false, intint.isTable() );
		assertEquals( false, longdouble.isTable() );
		assertEquals( false, doubledouble.isTable() );
		assertEquals( false, stringstring.isTable() );
		assertEquals( false, stringint.isTable() );
		assertEquals( false, stringdouble.isTable() );
		assertEquals( false, thread.isTable() );
		assertEquals( true, table.isTable() );
		assertEquals( false, userdataobj.isTable() );
		assertEquals( false, userdatacls.isTable() );
		assertEquals( false, somefunc.isTable() );
		assertEquals( false, someclosure.isTable() );
	}

	public void testIsUserdata() {
		assertEquals( false, somenil.isUserdata() );
		assertEquals( false, sometrue.isUserdata() );
		assertEquals( false, somefalse.isUserdata() );
		assertEquals( false, intint.isUserdata() );
		assertEquals( false, longdouble.isUserdata() );
		assertEquals( false, doubledouble.isUserdata() );
		assertEquals( false, stringstring.isUserdata() );
		assertEquals( false, stringint.isUserdata() );
		assertEquals( false, stringdouble.isUserdata() );
		assertEquals( false, thread.isUserdata() );
		assertEquals( false, table.isUserdata() );
		assertEquals( true, userdataobj.isUserdata() );
		assertEquals( true, userdatacls.isUserdata() );
		assertEquals( false, somefunc.isUserdata() );
		assertEquals( false, someclosure.isUserdata() );
	}
	
	public void testIsUserdataObject() {
		assertEquals( false, somenil.isUserdata(Object.class) );
		assertEquals( false, sometrue.isUserdata(Object.class) );
		assertEquals( false, somefalse.isUserdata(Object.class) );
		assertEquals( false, longdouble.isUserdata(Object.class) );
		assertEquals( false, doubledouble.isUserdata(Object.class) );
		assertEquals( false, stringstring.isUserdata(Object.class) );
		assertEquals( false, stringint.isUserdata(Object.class) );
		assertEquals( false, stringdouble.isUserdata(Object.class) );
		assertEquals( false, thread.isUserdata(Object.class) );
		assertEquals( false, table.isUserdata(Object.class) );
		assertEquals( true, userdataobj.isUserdata(Object.class) );
		assertEquals( true, userdatacls.isUserdata(Object.class) );
		assertEquals( false, somefunc.isUserdata(Object.class) );
		assertEquals( false, someclosure.isUserdata(Object.class) );
	}
	
	public void testIsUserdataMyData() {
		assertEquals( false, somenil.isUserdata(MyData.class) );
		assertEquals( false, sometrue.isUserdata(MyData.class) );
		assertEquals( false, somefalse.isUserdata(MyData.class) );
		assertEquals( false, longdouble.isUserdata(MyData.class) );
		assertEquals( false, doubledouble.isUserdata(MyData.class) );
		assertEquals( false, stringstring.isUserdata(MyData.class) );
		assertEquals( false, stringint.isUserdata(MyData.class) );
		assertEquals( false, stringdouble.isUserdata(MyData.class) );
		assertEquals( false, thread.isUserdata(MyData.class) );
		assertEquals( false, table.isUserdata(MyData.class) );
		assertEquals( false, userdataobj.isUserdata(MyData.class) );
		assertEquals( true, userdatacls.isUserdata(MyData.class) );
		assertEquals( false, somefunc.isUserdata(MyData.class) );
		assertEquals( false, someclosure.isUserdata(MyData.class) );
	}
	
	
	// ===================== Coerce to Java =======================
	
	public void testToBoolean() {
		assertEquals( false, somenil.toBoolean() );
		assertEquals( true, sometrue.toBoolean() );
		assertEquals( false, somefalse.toBoolean() );
		assertEquals( true, zero.toBoolean() );
		assertEquals( true, intint.toBoolean() );
		assertEquals( true, longdouble.toBoolean() );
		assertEquals( true, doubledouble.toBoolean() );
		assertEquals( true, stringstring.toBoolean() );
		assertEquals( true, stringint.toBoolean() );
		assertEquals( true, stringlong.toBoolean() );
		assertEquals( true, stringdouble.toBoolean() );
		assertEquals( true, thread.toBoolean() );
		assertEquals( true, table.toBoolean() );
		assertEquals( true, userdataobj.toBoolean() );
		assertEquals( true, userdatacls.toBoolean() );
		assertEquals( true, somefunc.toBoolean() );
		assertEquals( true, someclosure.toBoolean() );
	}
	
	public void testToByte() {
		assertEquals( (byte) 0, somenil.toByte() );
		assertEquals( (byte) 0, somefalse.toByte() );
		assertEquals( (byte) 0, sometrue.toByte() );
		assertEquals( (byte) 0, zero.toByte() );
		assertEquals( (byte) sampleint, intint.toByte() );
		assertEquals( (byte) samplelong, longdouble.toByte() );
		assertEquals( (byte) sampledouble, doubledouble.toByte() );
		assertEquals( (byte) 0, stringstring.toByte() );
		assertEquals( (byte) sampleint, stringint.toByte() );
		assertEquals( (byte) samplelong, stringlong.toByte() );
		assertEquals( (byte) sampledouble, stringdouble.toByte() );
		assertEquals( (byte) 0, thread.toByte() );
		assertEquals( (byte) 0, table.toByte() );
		assertEquals( (byte) 0, userdataobj.toByte() );
		assertEquals( (byte) 0, userdatacls.toByte() );
		assertEquals( (byte) 0, somefunc.toByte() );
		assertEquals( (byte) 0, someclosure.toByte() );
	}
	
	public void testToChar() {
		assertEquals( (char) 0, somenil.tochar() );
		assertEquals( (char) 0, somefalse.tochar() );
		assertEquals( (char) 0, sometrue.tochar() );
		assertEquals( (char) 0, zero.tochar() );
		assertEquals( (int) (char) sampleint, (int) intint.tochar() );
		assertEquals( (int) (char) samplelong, (int) longdouble.tochar() );
		assertEquals( (int) (char) sampledouble, (int) doubledouble.tochar() );
		assertEquals( (char) 0, stringstring.tochar() );
		assertEquals( (int) (char) sampleint, (int) stringint.tochar() );
		assertEquals( (int) (char) samplelong, (int) stringlong.tochar() );
		assertEquals( (int) (char) sampledouble, (int) stringdouble.tochar() );
		assertEquals( (char) 0, thread.tochar() );
		assertEquals( (char) 0, table.tochar() );
		assertEquals( (char) 0, userdataobj.tochar() );
		assertEquals( (char) 0, userdatacls.tochar() );
		assertEquals( (char) 0, somefunc.tochar() );
		assertEquals( (char) 0, someclosure.tochar() );
	}
	
	public void testToDouble() {
		assertEquals( 0., somenil.toDouble() );
		assertEquals( 0., somefalse.toDouble() );
		assertEquals( 0., sometrue.toDouble() );
		assertEquals( 0., zero.toDouble() );
		assertEquals( (double) sampleint, intint.toDouble() );
		assertEquals( (double) samplelong, longdouble.toDouble() );
		assertEquals( (double) sampledouble, doubledouble.toDouble() );
		assertEquals( (double) 0, stringstring.toDouble() );
		assertEquals( (double) sampleint, stringint.toDouble() );
		assertEquals( (double) samplelong, stringlong.toDouble() );
		assertEquals( (double) sampledouble, stringdouble.toDouble() );
		assertEquals( 0., thread.toDouble() );
		assertEquals( 0., table.toDouble() );
		assertEquals( 0., userdataobj.toDouble() );
		assertEquals( 0., userdatacls.toDouble() );
		assertEquals( 0., somefunc.toDouble() );
		assertEquals( 0., someclosure.toDouble() );
	}
	
	public void testToFloat() {
		assertEquals( 0.f, somenil.toFloat() );
		assertEquals( 0.f, somefalse.toFloat() );
		assertEquals( 0.f, sometrue.toFloat() );
		assertEquals( 0.f, zero.toFloat() );
		assertEquals( (float) sampleint, intint.toFloat() );
		assertEquals( (float) samplelong, longdouble.toFloat() );
		assertEquals( (float) sampledouble, doubledouble.toFloat() );
		assertEquals( (float) 0, stringstring.toFloat() );
		assertEquals( (float) sampleint, stringint.toFloat() );
		assertEquals( (float) samplelong, stringlong.toFloat() );
		assertEquals( (float) sampledouble, stringdouble.toFloat() );
		assertEquals( 0.f, thread.toFloat() );
		assertEquals( 0.f, table.toFloat() );
		assertEquals( 0.f, userdataobj.toFloat() );
		assertEquals( 0.f, userdatacls.toFloat() );
		assertEquals( 0.f, somefunc.toFloat() );
		assertEquals( 0.f, someclosure.toFloat() );
	}
	
	public void testToInt() {
		assertEquals( 0, somenil.toInt() );
		assertEquals( 0, somefalse.toInt() );
		assertEquals( 0, sometrue.toInt() );
		assertEquals( 0, zero.toInt() );
		assertEquals( (int) sampleint, intint.toInt() );
		assertEquals( (int) samplelong, longdouble.toInt() );
		assertEquals( (int) sampledouble, doubledouble.toInt() );
		assertEquals( (int) 0, stringstring.toInt() );
		assertEquals( (int) sampleint, stringint.toInt() );
		assertEquals( (int) samplelong, stringlong.toInt() );
		assertEquals( (int) sampledouble, stringdouble.toInt() );
		assertEquals( 0, thread.toInt() );
		assertEquals( 0, table.toInt() );
		assertEquals( 0, userdataobj.toInt() );
		assertEquals( 0, userdatacls.toInt() );
		assertEquals( 0, somefunc.toInt() );
		assertEquals( 0, someclosure.toInt() );
	}
	
	public void testToLong() {
		assertEquals( 0L, somenil.toLong() );
		assertEquals( 0L, somefalse.toLong() );
		assertEquals( 0L, sometrue.toLong() );
		assertEquals( 0L, zero.toLong() );
		assertEquals( (long) sampleint, intint.toLong() );
		assertEquals( (long) samplelong, longdouble.toLong() );
		assertEquals( (long) sampledouble, doubledouble.toLong() );
		assertEquals( (long) 0, stringstring.toLong() );
		assertEquals( (long) sampleint, stringint.toLong() );
		assertEquals( (long) samplelong, stringlong.toLong() );
		assertEquals( (long) sampledouble, stringdouble.toLong() );
		assertEquals( 0L, thread.toLong() );
		assertEquals( 0L, table.toLong() );
		assertEquals( 0L, userdataobj.toLong() );
		assertEquals( 0L, userdatacls.toLong() );
		assertEquals( 0L, somefunc.toLong() );
		assertEquals( 0L, someclosure.toLong() );
	}
	
	public void testToShort() {
		assertEquals( (short) 0, somenil.toShort() );
		assertEquals( (short) 0, somefalse.toShort() );
		assertEquals( (short) 0, sometrue.toShort() );
		assertEquals( (short) 0, zero.toShort() );
		assertEquals( (short) sampleint, intint.toShort() );
		assertEquals( (short) samplelong, longdouble.toShort() );
		assertEquals( (short) sampledouble, doubledouble.toShort() );
		assertEquals( (short) 0, stringstring.toShort() );
		assertEquals( (short) sampleint, stringint.toShort() );
		assertEquals( (short) samplelong, stringlong.toShort() );
		assertEquals( (short) sampledouble, stringdouble.toShort() );
		assertEquals( (short) 0, thread.toShort() );
		assertEquals( (short) 0, table.toShort() );
		assertEquals( (short) 0, userdataobj.toShort() );
		assertEquals( (short) 0, userdatacls.toShort() );
		assertEquals( (short) 0, somefunc.toShort() );
		assertEquals( (short) 0, someclosure.toShort() );
	}
	
	public void testToString() {
		assertEquals( "nil", somenil.toJString() );
		assertEquals( "false", somefalse.toJString() );
		assertEquals( "true", sometrue.toJString() );
		assertEquals( "0", zero.toJString() );
		assertEquals( String.valueOf(sampleint), intint.toJString() );
		assertEquals( String.valueOf(samplelong), longdouble.toJString() );
		assertEquals( String.valueOf(sampledouble), doubledouble.toJString() );
		assertEquals( samplestringstring, stringstring.toJString() );
		assertEquals( String.valueOf(sampleint), stringint.toJString() );
		assertEquals( String.valueOf(samplelong), stringlong.toJString() );
		assertEquals( String.valueOf(sampledouble), stringdouble.toJString() );
		assertEquals( "thread: ", thread.toJString().substring(0,8) );
		assertEquals( "table: ", table.toJString().substring(0,7) );
		assertEquals( sampleobject.toString(), userdataobj.toJString() );
		assertEquals( sampledata.toString(), userdatacls.toJString() );
		assertEquals( "function: ", somefunc.toJString().substring(0,10) );
		assertEquals( "function: ", someclosure.toJString().substring(0,10) );
	}
	
	public void testToUserdata() {
		assertEquals( null, somenil.toUserdata() );
		assertEquals( null, somefalse.toUserdata() );
		assertEquals( null, sometrue.toUserdata() );
		assertEquals( null, zero.toUserdata() );
		assertEquals( null, intint.toUserdata() );
		assertEquals( null, longdouble.toUserdata() );
		assertEquals( null, doubledouble.toUserdata() );
		assertEquals( null, stringstring.toUserdata() );
		assertEquals( null, stringint.toUserdata() );
		assertEquals( null, stringlong.toUserdata() );
		assertEquals( null, stringdouble.toUserdata() );
		assertEquals( null, thread.toUserdata() );
		assertEquals( null, table.toUserdata() );
		assertEquals( sampleobject, userdataobj.toUserdata() );
		assertEquals( sampledata, userdatacls.toUserdata() );
		assertEquals( null, somefunc.toUserdata() );
		assertEquals( null, someclosure.toUserdata() );
	}

	
	
	// ===================== Optional argument conversion =======================


	private void throwsError(LuaValue obj, String method, Class argtype, Object argument ) {
		try {
			obj.getClass().getMethod(method,argtype).invoke(obj, argument );
		} catch (InvocationTargetException e) {
			if ( ! (e.getTargetException() instanceof LuaError) )
				fail("not a LuaError: "+e.getTargetException());
			return; // pass
		} catch ( Exception e ) {
			fail( "bad exception: "+e );
		}
		fail("failed to throw LuaError as required");
	}

	public void testOptBoolean() {
		assertEquals( true, somenil.optionalBoolean(true) );
		assertEquals( false, somenil.optionalBoolean(false) );
		assertEquals( true, sometrue.optionalBoolean(false) );
		assertEquals( false, somefalse.optionalBoolean(true) );
		throwsError( zero, "optboolean", boolean.class, Boolean.FALSE );
		throwsError( intint, "optboolean", boolean.class, Boolean.FALSE );
		throwsError( longdouble, "optboolean", boolean.class, Boolean.FALSE );
		throwsError( doubledouble, "optboolean", boolean.class, Boolean.FALSE );
		throwsError( somefunc, "optboolean", boolean.class, Boolean.FALSE );
		throwsError( someclosure, "optboolean", boolean.class, Boolean.FALSE );
		throwsError( stringstring, "optboolean", boolean.class, Boolean.FALSE );
		throwsError( stringint, "optboolean", boolean.class, Boolean.FALSE );
		throwsError( stringlong, "optboolean", boolean.class, Boolean.FALSE );
		throwsError( stringdouble, "optboolean", boolean.class, Boolean.FALSE );
		throwsError( thread, "optboolean", boolean.class, Boolean.FALSE );
		throwsError( table, "optboolean", boolean.class, Boolean.FALSE );
		throwsError( userdataobj, "optboolean", boolean.class, Boolean.FALSE );
		throwsError( userdatacls, "optboolean", boolean.class, Boolean.FALSE );
	}

	public void testOptClosure() {
		assertEquals( someclosure, somenil.optionalClosure(someclosure) );
		assertEquals( null, somenil.optionalClosure(null) );
		throwsError( sometrue, "optclosure", LuaClosure.class, someclosure );
		throwsError( somefalse, "optclosure", LuaClosure.class, someclosure );
		throwsError( zero, "optclosure", LuaClosure.class, someclosure );
		throwsError( intint, "optclosure", LuaClosure.class, someclosure );
		throwsError( longdouble, "optclosure", LuaClosure.class, someclosure );
		throwsError( doubledouble, "optclosure", LuaClosure.class, someclosure );
		throwsError( somefunc, "optclosure", LuaClosure.class, someclosure );
		assertEquals( someclosure, someclosure.optionalClosure(someclosure) );
		assertEquals( someclosure, someclosure.optionalClosure(null) );
		throwsError( stringstring, "optclosure", LuaClosure.class, someclosure );
		throwsError( stringint, "optclosure", LuaClosure.class, someclosure );
		throwsError( stringlong, "optclosure", LuaClosure.class, someclosure );
		throwsError( stringdouble, "optclosure", LuaClosure.class, someclosure );
		throwsError( thread, "optclosure", LuaClosure.class, someclosure );
		throwsError( table, "optclosure", LuaClosure.class, someclosure );
		throwsError( userdataobj, "optclosure", LuaClosure.class, someclosure );
		throwsError( userdatacls, "optclosure", LuaClosure.class, someclosure );
	}

	public void testOptDouble() {
		assertEquals( 33., somenil.optionalDouble(33.) );
		throwsError( sometrue, "optdouble", double.class, 33. );
		throwsError( somefalse, "optdouble", double.class, 33. );
		assertEquals( 0., zero.optionalDouble(33.) );
		assertEquals( (double) sampleint, intint.optionalDouble(33.) );
		assertEquals( (double) samplelong, longdouble.optionalDouble(33.) );
		assertEquals( sampledouble, doubledouble.optionalDouble(33.) );
		throwsError( somefunc, "optdouble", double.class, 33. );
		throwsError( someclosure, "optdouble", double.class, 33. );
		throwsError( stringstring, "optdouble", double.class, 33. );
		assertEquals( (double) sampleint, stringint.optionalDouble(33.) );
		assertEquals( (double) samplelong, stringlong.optionalDouble(33.) );
		assertEquals( sampledouble, stringdouble.optionalDouble(33.) );
		throwsError( thread, "optdouble", double.class, 33. );
		throwsError( table, "optdouble", double.class, 33. );
		throwsError( userdataobj, "optdouble", double.class, 33. );
		throwsError( userdatacls, "optdouble", double.class, 33. );
	}

	public void testOptFunction() {
		assertEquals( somefunc, somenil.optionalFunction(somefunc) );
		assertEquals( null, somenil.optionalFunction(null) );
		throwsError( sometrue, "optfunction", LuaFunction.class, somefunc );
		throwsError( somefalse, "optfunction", LuaFunction.class, somefunc );
		throwsError( zero, "optfunction", LuaFunction.class, somefunc );
		throwsError( intint, "optfunction", LuaFunction.class, somefunc );
		throwsError( longdouble, "optfunction", LuaFunction.class, somefunc );
		throwsError( doubledouble, "optfunction", LuaFunction.class, somefunc );
		assertEquals( somefunc, somefunc.optionalFunction(null) );
		assertEquals( someclosure, someclosure.optionalFunction(null) );
		assertEquals( somefunc, somefunc.optionalFunction(somefunc) );
		assertEquals( someclosure, someclosure.optionalFunction(somefunc) );
		throwsError( stringstring, "optfunction", LuaFunction.class, somefunc );
		throwsError( stringint, "optfunction", LuaFunction.class, somefunc );
		throwsError( stringlong, "optfunction", LuaFunction.class, somefunc );
		throwsError( stringdouble, "optfunction", LuaFunction.class, somefunc );
		throwsError( thread, "optfunction", LuaFunction.class, somefunc );
		throwsError( table, "optfunction", LuaFunction.class, somefunc );
		throwsError( userdataobj, "optfunction", LuaFunction.class, somefunc );
		throwsError( userdatacls, "optfunction", LuaFunction.class, somefunc );
	}

	public void testOptInt() {
		assertEquals( 33, somenil.optionalInt(33) );
		throwsError( sometrue, "optint", int.class, new Integer(33) );
		throwsError( somefalse, "optint", int.class, new Integer(33) );
		assertEquals( 0, zero.optionalInt(33) );
		assertEquals( sampleint, intint.optionalInt(33) );
		assertEquals( (int) samplelong, longdouble.optionalInt(33) );
		assertEquals( (int) sampledouble, doubledouble.optionalInt(33) );
		throwsError( somefunc, "optint", int.class, new Integer(33) );
		throwsError( someclosure, "optint", int.class, new Integer(33) );
		throwsError( stringstring, "optint", int.class, new Integer(33) );
		assertEquals( sampleint, stringint.optionalInt(33) );
		assertEquals( (int) samplelong, stringlong.optionalInt(33) );
		assertEquals( (int) sampledouble, stringdouble.optionalInt(33) );
		throwsError( thread, "optint", int.class, new Integer(33) );
		throwsError( table, "optint", int.class, new Integer(33) );
		throwsError( userdataobj, "optint", int.class, new Integer(33) );
		throwsError( userdatacls, "optint", int.class, new Integer(33) );
	}
	
	public void testOptInteger() {
		assertEquals( LuaValue.valueOf(33), somenil.optionalInteger(LuaValue.valueOf(33)) );
		throwsError( sometrue, "optinteger", LuaInteger.class, LuaValue.valueOf(33) );
		throwsError( somefalse, "optinteger", LuaInteger.class, LuaValue.valueOf(33) );
		assertEquals( zero, zero.optionalInteger(LuaValue.valueOf(33)) );
		assertEquals( LuaValue.valueOf( sampleint ), intint.optionalInteger(LuaValue.valueOf(33)) );
		assertEquals( LuaValue.valueOf( (int) samplelong ), longdouble.optionalInteger(LuaValue.valueOf(33)) );
		assertEquals( LuaValue.valueOf( (int) sampledouble ), doubledouble.optionalInteger(LuaValue.valueOf(33)) );
		throwsError( somefunc, "optinteger", LuaInteger.class, LuaValue.valueOf(33) );
		throwsError( someclosure, "optinteger", LuaInteger.class, LuaValue.valueOf(33) );
		throwsError( stringstring, "optinteger", LuaInteger.class, LuaValue.valueOf(33) );
		assertEquals( LuaValue.valueOf( sampleint), stringint.optionalInteger(LuaValue.valueOf(33)) );
		assertEquals( LuaValue.valueOf( (int) samplelong), stringlong.optionalInteger(LuaValue.valueOf(33)) );
		assertEquals( LuaValue.valueOf( (int) sampledouble), stringdouble.optionalInteger(LuaValue.valueOf(33)) );
		throwsError( thread, "optinteger", LuaInteger.class, LuaValue.valueOf(33) );
		throwsError( table, "optinteger", LuaInteger.class, LuaValue.valueOf(33) );
		throwsError( userdataobj, "optinteger", LuaInteger.class, LuaValue.valueOf(33) );
		throwsError( userdatacls, "optinteger", LuaInteger.class, LuaValue.valueOf(33) );
	}

	public void testOptLong() {
		assertEquals( 33L, somenil.optionalLong(33) );
		throwsError( sometrue, "optlong", long.class, new Long(33) );
		throwsError( somefalse, "optlong", long.class, new Long(33) );
		assertEquals( 0L, zero.optionalLong(33) );
		assertEquals( sampleint, intint.optionalLong(33) );
		assertEquals( (long) samplelong, longdouble.optionalLong(33) );
		assertEquals( (long) sampledouble, doubledouble.optionalLong(33) );
		throwsError( somefunc, "optlong", long.class, new Long(33) );
		throwsError( someclosure, "optlong", long.class, new Long(33) );
		throwsError( stringstring, "optlong", long.class, new Long(33) );
		assertEquals( sampleint, stringint.optionalLong(33) );
		assertEquals( (long) samplelong, stringlong.optionalLong(33) );
		assertEquals( (long) sampledouble, stringdouble.optionalLong(33) );
		throwsError( thread, "optlong", long.class, new Long(33) );
		throwsError( table, "optlong", long.class, new Long(33) );
		throwsError( userdataobj, "optlong", long.class, new Long(33) );
		throwsError( userdatacls, "optlong", long.class, new Long(33) );
	}
	
	public void testOptNumber() {
		assertEquals( LuaValue.valueOf(33), somenil.optionalNumber(LuaValue.valueOf(33)) );
		throwsError( sometrue, "optnumber", LuaNumber.class, LuaValue.valueOf(33) );
		throwsError( somefalse, "optnumber", LuaNumber.class, LuaValue.valueOf(33) );
		assertEquals( zero, zero.optionalNumber(LuaValue.valueOf(33)) );
		assertEquals( LuaValue.valueOf( sampleint ), intint.optionalNumber(LuaValue.valueOf(33)) );
		assertEquals( LuaValue.valueOf( samplelong ), longdouble.optionalNumber(LuaValue.valueOf(33)) );
		assertEquals( LuaValue.valueOf( sampledouble ), doubledouble.optionalNumber(LuaValue.valueOf(33)) );
		throwsError( somefunc, "optnumber", LuaNumber.class, LuaValue.valueOf(33) );
		throwsError( someclosure, "optnumber", LuaNumber.class, LuaValue.valueOf(33) );
		throwsError( stringstring, "optnumber", LuaNumber.class, LuaValue.valueOf(33) );
		assertEquals( LuaValue.valueOf( sampleint), stringint.optionalNumber(LuaValue.valueOf(33)) );
		assertEquals( LuaValue.valueOf( samplelong), stringlong.optionalNumber(LuaValue.valueOf(33)) );
		assertEquals( LuaValue.valueOf( sampledouble), stringdouble.optionalNumber(LuaValue.valueOf(33)) );
		throwsError( thread, "optnumber", LuaNumber.class, LuaValue.valueOf(33) );
		throwsError( table, "optnumber", LuaNumber.class, LuaValue.valueOf(33) );
		throwsError( userdataobj, "optnumber", LuaNumber.class, LuaValue.valueOf(33) );
		throwsError( userdatacls, "optnumber", LuaNumber.class, LuaValue.valueOf(33) );
	}
	
	public void testOptTable() {
		assertEquals( table, somenil.optionalTable(table) );
		assertEquals( null, somenil.optionalTable(null) );
		throwsError( sometrue, "opttable", LuaTable.class, table );
		throwsError( somefalse, "opttable", LuaTable.class, table );
		throwsError( zero, "opttable", LuaTable.class, table );
		throwsError( intint, "opttable", LuaTable.class, table );
		throwsError( longdouble, "opttable", LuaTable.class, table );
		throwsError( doubledouble, "opttable", LuaTable.class, table );
		throwsError( somefunc, "opttable", LuaTable.class, table );
		throwsError( someclosure, "opttable", LuaTable.class, table );
		throwsError( stringstring, "opttable", LuaTable.class, table );
		throwsError( stringint, "opttable", LuaTable.class, table );
		throwsError( stringlong, "opttable", LuaTable.class, table );
		throwsError( stringdouble, "opttable", LuaTable.class, table );
		throwsError( thread, "opttable", LuaTable.class, table );
		assertEquals( table, table.optionalTable(table) );
		assertEquals( table, table.optionalTable(null) );
		throwsError( userdataobj, "opttable", LuaTable.class, table );
		throwsError( userdatacls, "opttable", LuaTable.class, table );
	}
	
	public void testOptThread() {
		assertEquals( thread, somenil.optionalThread(thread) );
		assertEquals( null, somenil.optionalThread(null) );
		throwsError( sometrue, "optthread", LuaThread.class, thread );
		throwsError( somefalse, "optthread", LuaThread.class, thread );
		throwsError( zero, "optthread", LuaThread.class, thread );
		throwsError( intint, "optthread", LuaThread.class, thread );
		throwsError( longdouble, "optthread", LuaThread.class, thread );
		throwsError( doubledouble, "optthread", LuaThread.class, thread );
		throwsError( somefunc, "optthread", LuaThread.class, thread );
		throwsError( someclosure, "optthread", LuaThread.class, thread );
		throwsError( stringstring, "optthread", LuaThread.class, thread );
		throwsError( stringint, "optthread", LuaThread.class, thread );
		throwsError( stringlong, "optthread", LuaThread.class, thread );
		throwsError( stringdouble, "optthread", LuaThread.class, thread );
		throwsError( table, "optthread", LuaThread.class, thread );
		assertEquals( thread, thread.optionalThread(thread) );
		assertEquals( thread, thread.optionalThread(null) );
		throwsError( userdataobj, "optthread", LuaThread.class, thread );
		throwsError( userdatacls, "optthread", LuaThread.class, thread );
	}
	
	public void testOptJavaString() {
		assertEquals( "xyz", somenil.optionalJString("xyz") );
		assertEquals( null, somenil.optionalJString(null) );
		throwsError( sometrue, "optjstring", String.class, "xyz" );
		throwsError( somefalse, "optjstring", String.class, "xyz" );
		assertEquals( String.valueOf(zero), zero.optionalJString("xyz") );
		assertEquals( String.valueOf(intint), intint.optionalJString("xyz") );
		assertEquals( String.valueOf(longdouble), longdouble.optionalJString("xyz") );
		assertEquals( String.valueOf(doubledouble), doubledouble.optionalJString("xyz") );
		throwsError( somefunc, "optjstring", String.class, "xyz" );
		throwsError( someclosure, "optjstring", String.class, "xyz" );
		assertEquals( samplestringstring, stringstring.optionalJString("xyz") );
		assertEquals( samplestringint, stringint.optionalJString("xyz") );
		assertEquals( samplestringlong, stringlong.optionalJString("xyz") );
		assertEquals( samplestringdouble, stringdouble.optionalJString("xyz") );
		throwsError( thread, "optjstring", String.class, "xyz" );
		throwsError( table, "optjstring", String.class, "xyz" );
		throwsError( userdataobj, "optjstring", String.class, "xyz" );
		throwsError( userdatacls, "optjstring", String.class, "xyz" );
	}
	
	public void testOptLuaString() {
		assertEquals( LuaValue.valueOf("xyz"), somenil.optionalLuaString(LuaValue.valueOf("xyz")) );
		assertEquals( null, somenil.optionalLuaString(null) );
		throwsError( sometrue, "optstring", LuaString.class, LuaValue.valueOf("xyz") );
		throwsError( somefalse, "optstring", LuaString.class, LuaValue.valueOf("xyz") );
		assertEquals( LuaValue.valueOf("0"), zero.optionalLuaString(LuaValue.valueOf("xyz")) );
		assertEquals( stringint, intint.optionalLuaString(LuaValue.valueOf("xyz")) );
		assertEquals( stringlong, longdouble.optionalLuaString(LuaValue.valueOf("xyz")) );
		assertEquals( stringdouble, doubledouble.optionalLuaString(LuaValue.valueOf("xyz")) );
		throwsError( somefunc, "optstring", LuaString.class, LuaValue.valueOf("xyz") );
		throwsError( someclosure, "optstring", LuaString.class, LuaValue.valueOf("xyz") );
		assertEquals( stringstring, stringstring.optionalLuaString(LuaValue.valueOf("xyz")) );
		assertEquals( stringint, stringint.optionalLuaString(LuaValue.valueOf("xyz")) );
		assertEquals( stringlong, stringlong.optionalLuaString(LuaValue.valueOf("xyz")) );
		assertEquals( stringdouble, stringdouble.optionalLuaString(LuaValue.valueOf("xyz")) );
		throwsError( thread, "optstring", LuaString.class, LuaValue.valueOf("xyz") );
		throwsError( table, "optstring", LuaString.class, LuaValue.valueOf("xyz") );
		throwsError( userdataobj, "optstring", LuaString.class, LuaValue.valueOf("xyz") );
		throwsError( userdatacls, "optstring", LuaString.class, LuaValue.valueOf("xyz") );
	}
	
	public void testOptUserdata() {
		assertEquals( sampleobject, somenil.optionalUserdata(sampleobject) );
		assertEquals( sampledata, somenil.optionalUserdata(sampledata) );
		assertEquals( null, somenil.optionalUserdata(null) );
		throwsError( sometrue, "optuserdata", Object.class, sampledata );
		throwsError( somefalse, "optuserdata", Object.class, sampledata );
		throwsError( zero, "optuserdata", Object.class, sampledata );
		throwsError( intint, "optuserdata", Object.class, sampledata );
		throwsError( longdouble, "optuserdata", Object.class, sampledata );
		throwsError( doubledouble, "optuserdata", Object.class, sampledata );
		throwsError( somefunc, "optuserdata", Object.class, sampledata );
		throwsError( someclosure, "optuserdata", Object.class, sampledata );
		throwsError( stringstring, "optuserdata", Object.class, sampledata );
		throwsError( stringint, "optuserdata", Object.class, sampledata );
		throwsError( stringlong, "optuserdata", Object.class, sampledata );
		throwsError( stringdouble, "optuserdata", Object.class, sampledata );
		throwsError( table, "optuserdata", Object.class, sampledata );
		assertEquals( sampleobject, userdataobj.optionalUserdata(sampledata) );
		assertEquals( sampleobject, userdataobj.optionalUserdata(null) );
		assertEquals( sampledata, userdatacls.optionalUserdata(sampleobject) );
		assertEquals( sampledata, userdatacls.optionalUserdata(null) );
	}

	private void throwsErrorOptUserdataClass(LuaValue obj, Class arg1, Object arg2 ) {
		try {
			obj.getClass().getMethod("optionalUserdata", Class.class, Object.class ).invoke(obj, arg1, arg2);
		} catch (InvocationTargetException e) {
			if ( ! (e.getTargetException() instanceof LuaError) )
				fail("not a LuaError: "+e.getTargetException());
			return; // pass
		} catch ( Exception e ) {
			fail( "bad exception: "+e );
		}
		fail("failed to throw LuaError as required");
	}
	
	public void testOptUserdataClass() {
		assertEquals( sampledata, somenil.optionalUserdata(MyData.class, sampledata) );
		assertEquals( sampleobject, somenil.optionalUserdata(Object.class, sampleobject) );
		assertEquals( null, somenil.optionalUserdata(null) );
		throwsErrorOptUserdataClass( sometrue,  Object.class, sampledata );
		throwsErrorOptUserdataClass( zero,  MyData.class, sampledata);
		throwsErrorOptUserdataClass( intint,  MyData.class, sampledata);
		throwsErrorOptUserdataClass( longdouble,  MyData.class, sampledata);
		throwsErrorOptUserdataClass( somefunc,  MyData.class, sampledata);
		throwsErrorOptUserdataClass( someclosure,  MyData.class, sampledata);
		throwsErrorOptUserdataClass( stringstring,  MyData.class, sampledata);
		throwsErrorOptUserdataClass( stringint,  MyData.class, sampledata);
		throwsErrorOptUserdataClass( stringlong,  MyData.class, sampledata);
		throwsErrorOptUserdataClass( stringlong,  MyData.class, sampledata);
		throwsErrorOptUserdataClass( stringdouble,  MyData.class, sampledata);
		throwsErrorOptUserdataClass( table,  MyData.class, sampledata);
		throwsErrorOptUserdataClass( thread,  MyData.class, sampledata);
		assertEquals( sampleobject, userdataobj.optionalUserdata(Object.class, sampleobject) );
		assertEquals( sampleobject, userdataobj.optionalUserdata(null) );
		assertEquals( sampledata, userdatacls.optionalUserdata(MyData.class, sampledata) );
		assertEquals( sampledata, userdatacls.optionalUserdata(Object.class, sampleobject) );
		assertEquals( sampledata, userdatacls.optionalUserdata(null) );
		// should fail due to wrong class
		try {
			Object o = userdataobj.optionalUserdata(MyData.class, sampledata);
			fail( "did not throw bad type error" );
			assertTrue( o instanceof MyData );
		} catch ( LuaError le ) {
			assertEquals( "org.luaj.vm2.TypeTest$MyData expected, got userdata", le.getMessage() );
		}
	}
	
	public void testOptValue() {
		assertEquals( zero, somenil.optionalValue(zero) );
		assertEquals( stringstring, somenil.optionalValue(stringstring) );
		assertEquals( sometrue, sometrue.optionalValue(LuaValue.TRUE) );
		assertEquals( somefalse, somefalse.optionalValue(LuaValue.TRUE) );
		assertEquals( zero, zero.optionalValue(LuaValue.TRUE) );
		assertEquals( intint, intint.optionalValue(LuaValue.TRUE) );
		assertEquals( longdouble, longdouble.optionalValue(LuaValue.TRUE) );
		assertEquals( somefunc, somefunc.optionalValue(LuaValue.TRUE) );
		assertEquals( someclosure, someclosure.optionalValue(LuaValue.TRUE) );
		assertEquals( stringstring, stringstring.optionalValue(LuaValue.TRUE) );
		assertEquals( stringint, stringint.optionalValue(LuaValue.TRUE) );
		assertEquals( stringlong, stringlong.optionalValue(LuaValue.TRUE) );
		assertEquals( stringdouble, stringdouble.optionalValue(LuaValue.TRUE) );
		assertEquals( thread, thread.optionalValue(LuaValue.TRUE) );
		assertEquals( table, table.optionalValue(LuaValue.TRUE) );
		assertEquals( userdataobj, userdataobj.optionalValue(LuaValue.TRUE) );
		assertEquals( userdatacls, userdatacls.optionalValue(LuaValue.TRUE) );
	}
	
	
	
	// ===================== Required argument conversion =======================


	private void throwsErrorReq(LuaValue obj, String method ) {
		try {
			obj.getClass().getMethod(method).invoke(obj);
		} catch (InvocationTargetException e) {
			if ( ! (e.getTargetException() instanceof LuaError) )
				fail("not a LuaError: "+e.getTargetException());
			return; // pass
		} catch ( Exception e ) {
			fail( "bad exception: "+e );
		}
		fail("failed to throw LuaError as required");
	}

	public void testCheckBoolean() {
		throwsErrorReq( somenil, "checkboolean" );
		assertEquals( true, sometrue.checkBoolean() );
		assertEquals( false, somefalse.checkBoolean() );
		throwsErrorReq( zero, "checkboolean" );
		throwsErrorReq( intint, "checkboolean" );
		throwsErrorReq( longdouble, "checkboolean" );
		throwsErrorReq( doubledouble, "checkboolean" );
		throwsErrorReq( somefunc, "checkboolean" );
		throwsErrorReq( someclosure, "checkboolean" );
		throwsErrorReq( stringstring, "checkboolean" );
		throwsErrorReq( stringint, "checkboolean" );
		throwsErrorReq( stringlong, "checkboolean" );
		throwsErrorReq( stringdouble, "checkboolean" );
		throwsErrorReq( thread, "checkboolean" );
		throwsErrorReq( table, "checkboolean" );
		throwsErrorReq( userdataobj, "checkboolean" );
		throwsErrorReq( userdatacls, "checkboolean" );
	}

	public void testCheckClosure() {
		throwsErrorReq( somenil, "checkclosure" );
		throwsErrorReq( sometrue, "checkclosure" );
		throwsErrorReq( somefalse, "checkclosure" );
		throwsErrorReq( zero, "checkclosure" );
		throwsErrorReq( intint, "checkclosure" );
		throwsErrorReq( longdouble, "checkclosure" );
		throwsErrorReq( doubledouble, "checkclosure" );
		throwsErrorReq( somefunc, "checkclosure" );
		assertEquals( someclosure, someclosure.checkClosure() );
		assertEquals( someclosure, someclosure.checkClosure() );
		throwsErrorReq( stringstring, "checkclosure" );
		throwsErrorReq( stringint, "checkclosure" );
		throwsErrorReq( stringlong, "checkclosure" );
		throwsErrorReq( stringdouble, "checkclosure" );
		throwsErrorReq( thread, "checkclosure" );
		throwsErrorReq( table, "checkclosure" );
		throwsErrorReq( userdataobj, "checkclosure" );
		throwsErrorReq( userdatacls, "checkclosure" );
	}

	public void testCheckDouble() {
		throwsErrorReq( somenil, "checkdouble" );
		throwsErrorReq( sometrue, "checkdouble" );
		throwsErrorReq( somefalse, "checkdouble" );
		assertEquals( 0., zero.checkDouble() );
		assertEquals( (double) sampleint, intint.checkDouble() );
		assertEquals( (double) samplelong, longdouble.checkDouble() );
		assertEquals( sampledouble, doubledouble.checkDouble() );
		throwsErrorReq( somefunc, "checkdouble" );
		throwsErrorReq( someclosure, "checkdouble" );
		throwsErrorReq( stringstring, "checkdouble" );
		assertEquals( (double) sampleint, stringint.checkDouble() );
		assertEquals( (double) samplelong, stringlong.checkDouble() );
		assertEquals( sampledouble, stringdouble.checkDouble() );
		throwsErrorReq( thread, "checkdouble" );
		throwsErrorReq( table, "checkdouble" );
		throwsErrorReq( userdataobj, "checkdouble" );
		throwsErrorReq( userdatacls, "checkdouble" );
	}

	public void testCheckFunction() {
		throwsErrorReq( somenil, "checkfunction" );
		throwsErrorReq( sometrue, "checkfunction" );
		throwsErrorReq( somefalse, "checkfunction" );
		throwsErrorReq( zero, "checkfunction" );
		throwsErrorReq( intint, "checkfunction" );
		throwsErrorReq( longdouble, "checkfunction" );
		throwsErrorReq( doubledouble, "checkfunction" );
		assertEquals( somefunc, somefunc.checkFunction() );
		assertEquals( someclosure, someclosure.checkFunction() );
		assertEquals( somefunc, somefunc.checkFunction() );
		assertEquals( someclosure, someclosure.checkFunction() );
		throwsErrorReq( stringstring, "checkfunction" );
		throwsErrorReq( stringint, "checkfunction" );
		throwsErrorReq( stringlong, "checkfunction" );
		throwsErrorReq( stringdouble, "checkfunction" );
		throwsErrorReq( thread, "checkfunction" );
		throwsErrorReq( table, "checkfunction" );
		throwsErrorReq( userdataobj, "checkfunction" );
		throwsErrorReq( userdatacls, "checkfunction" );
	}

	public void testCheckInt() {
		throwsErrorReq( somenil, "checkint" );
		throwsErrorReq( sometrue, "checkint" );
		throwsErrorReq( somefalse, "checkint" );
		assertEquals( 0, zero.checkInt() );
		assertEquals( sampleint, intint.checkInt() );
		assertEquals( (int) samplelong, longdouble.checkInt() );
		assertEquals( (int) sampledouble, doubledouble.checkInt() );
		throwsErrorReq( somefunc, "checkint" );
		throwsErrorReq( someclosure, "checkint" );
		throwsErrorReq( stringstring, "checkint" );
		assertEquals( sampleint, stringint.checkInt() );
		assertEquals( (int) samplelong, stringlong.checkInt() );
		assertEquals( (int) sampledouble, stringdouble.checkInt() );
		throwsErrorReq( thread, "checkint" );
		throwsErrorReq( table, "checkint" );
		throwsErrorReq( userdataobj, "checkint" );
		throwsErrorReq( userdatacls, "checkint" );
	}
	
	public void testCheckInteger() {
		throwsErrorReq( somenil, "checkinteger" );
		throwsErrorReq( sometrue, "checkinteger" );
		throwsErrorReq( somefalse, "checkinteger" );
		assertEquals( zero, zero.checkInteger() );
		assertEquals( LuaValue.valueOf( sampleint ), intint.checkInteger() );
		assertEquals( LuaValue.valueOf( (int) samplelong ), longdouble.checkInteger() );
		assertEquals( LuaValue.valueOf( (int) sampledouble ), doubledouble.checkInteger() );
		throwsErrorReq( somefunc, "checkinteger" );
		throwsErrorReq( someclosure, "checkinteger" );
		throwsErrorReq( stringstring, "checkinteger" );
		assertEquals( LuaValue.valueOf( sampleint), stringint.checkInteger() );
		assertEquals( LuaValue.valueOf( (int) samplelong), stringlong.checkInteger() );
		assertEquals( LuaValue.valueOf( (int) sampledouble), stringdouble.checkInteger() );
		throwsErrorReq( thread, "checkinteger" );
		throwsErrorReq( table, "checkinteger" );
		throwsErrorReq( userdataobj, "checkinteger" );
		throwsErrorReq( userdatacls, "checkinteger" );
	}

	public void testCheckLong() {
		throwsErrorReq( somenil, "checklong" );
		throwsErrorReq( sometrue, "checklong" );
		throwsErrorReq( somefalse, "checklong" );
		assertEquals( 0L, zero.checkLong() );
		assertEquals( sampleint, intint.checkLong() );
		assertEquals( (long) samplelong, longdouble.checkLong() );
		assertEquals( (long) sampledouble, doubledouble.checkLong() );
		throwsErrorReq( somefunc, "checklong" );
		throwsErrorReq( someclosure, "checklong" );
		throwsErrorReq( stringstring, "checklong" );
		assertEquals( sampleint, stringint.checkLong() );
		assertEquals( (long) samplelong, stringlong.checkLong() );
		assertEquals( (long) sampledouble, stringdouble.checkLong() );
		throwsErrorReq( thread, "checklong" );
		throwsErrorReq( table, "checklong" );
		throwsErrorReq( userdataobj, "checklong" );
		throwsErrorReq( userdatacls, "checklong" );
	}
	
	public void testCheckNumber() {
		throwsErrorReq( somenil, "checknumber" );
		throwsErrorReq( sometrue, "checknumber" );
		throwsErrorReq( somefalse, "checknumber" );
		assertEquals( zero, zero.checkNumber() );
		assertEquals( LuaValue.valueOf( sampleint ), intint.checkNumber() );
		assertEquals( LuaValue.valueOf( samplelong ), longdouble.checkNumber() );
		assertEquals( LuaValue.valueOf( sampledouble ), doubledouble.checkNumber() );
		throwsErrorReq( somefunc, "checknumber" );
		throwsErrorReq( someclosure, "checknumber" );
		throwsErrorReq( stringstring, "checknumber" );
		assertEquals( LuaValue.valueOf( sampleint), stringint.checkNumber() );
		assertEquals( LuaValue.valueOf( samplelong), stringlong.checkNumber() );
		assertEquals( LuaValue.valueOf( sampledouble), stringdouble.checkNumber() );
		throwsErrorReq( thread, "checknumber" );
		throwsErrorReq( table, "checknumber" );
		throwsErrorReq( userdataobj, "checknumber" );
		throwsErrorReq( userdatacls, "checknumber" );
	}
	
	public void testCheckTable() {
		throwsErrorReq( somenil, "checktable" );
		throwsErrorReq( sometrue, "checktable" );
		throwsErrorReq( somefalse, "checktable" );
		throwsErrorReq( zero, "checktable" );
		throwsErrorReq( intint, "checktable" );
		throwsErrorReq( longdouble, "checktable" );
		throwsErrorReq( doubledouble, "checktable" );
		throwsErrorReq( somefunc, "checktable" );
		throwsErrorReq( someclosure, "checktable" );
		throwsErrorReq( stringstring, "checktable" );
		throwsErrorReq( stringint, "checktable" );
		throwsErrorReq( stringlong, "checktable" );
		throwsErrorReq( stringdouble, "checktable" );
		throwsErrorReq( thread, "checktable" );
		assertEquals( table, table.checkTable() );
		assertEquals( table, table.checkTable() );
		throwsErrorReq( userdataobj, "checktable" );
		throwsErrorReq( userdatacls, "checktable" );
	}
	
	public void testCheckThread() {
		throwsErrorReq( somenil, "checkthread" );
		throwsErrorReq( sometrue, "checkthread" );
		throwsErrorReq( somefalse, "checkthread" );
		throwsErrorReq( zero, "checkthread" );
		throwsErrorReq( intint, "checkthread" );
		throwsErrorReq( longdouble, "checkthread" );
		throwsErrorReq( doubledouble, "checkthread" );
		throwsErrorReq( somefunc, "checkthread" );
		throwsErrorReq( someclosure, "checkthread" );
		throwsErrorReq( stringstring, "checkthread" );
		throwsErrorReq( stringint, "checkthread" );
		throwsErrorReq( stringlong, "checkthread" );
		throwsErrorReq( stringdouble, "checkthread" );
		throwsErrorReq( table, "checkthread" );
		assertEquals( thread, thread.checkThread() );
		assertEquals( thread, thread.checkThread() );
		throwsErrorReq( userdataobj, "checkthread" );
		throwsErrorReq( userdatacls, "checkthread" );
	}
	
	public void testCheckJavaString() {
		throwsErrorReq( somenil, "checkjstring" );
		throwsErrorReq( sometrue, "checkjstring" );
		throwsErrorReq( somefalse, "checkjstring" );
		assertEquals( String.valueOf(zero), zero.checkJString() );
		assertEquals( String.valueOf(intint), intint.checkJString() );
		assertEquals( String.valueOf(longdouble), longdouble.checkJString() );
		assertEquals( String.valueOf(doubledouble), doubledouble.checkJString() );
		throwsErrorReq( somefunc, "checkjstring" );
		throwsErrorReq( someclosure, "checkjstring" );
		assertEquals( samplestringstring, stringstring.checkJString() );
		assertEquals( samplestringint, stringint.checkJString() );
		assertEquals( samplestringlong, stringlong.checkJString() );
		assertEquals( samplestringdouble, stringdouble.checkJString() );
		throwsErrorReq( thread, "checkjstring" );
		throwsErrorReq( table, "checkjstring" );
		throwsErrorReq( userdataobj, "checkjstring" );
		throwsErrorReq( userdatacls, "checkjstring" );
	}
	
	public void testCheckLuaString() {
		throwsErrorReq( somenil, "checkstring" );
		throwsErrorReq( sometrue, "checkstring" );
		throwsErrorReq( somefalse, "checkstring" );
		assertEquals( LuaValue.valueOf("0"), zero.checkLuaString() );
		assertEquals( stringint, intint.checkLuaString() );
		assertEquals( stringlong, longdouble.checkLuaString() );
		assertEquals( stringdouble, doubledouble.checkLuaString() );
		throwsErrorReq( somefunc, "checkstring" );
		throwsErrorReq( someclosure, "checkstring" );
		assertEquals( stringstring, stringstring.checkLuaString() );
		assertEquals( stringint, stringint.checkLuaString() );
		assertEquals( stringlong, stringlong.checkLuaString() );
		assertEquals( stringdouble, stringdouble.checkLuaString() );
		throwsErrorReq( thread, "checkstring" );
		throwsErrorReq( table, "checkstring" );
		throwsErrorReq( userdataobj, "checkstring" );
		throwsErrorReq( userdatacls, "checkstring" );
	}
	
	public void testCheckUserdata() {
		throwsErrorReq( somenil, "checkuserdata" );
		throwsErrorReq( sometrue, "checkuserdata" );
		throwsErrorReq( somefalse, "checkuserdata" );
		throwsErrorReq( zero, "checkuserdata" );
		throwsErrorReq( intint, "checkuserdata" );
		throwsErrorReq( longdouble, "checkuserdata" );
		throwsErrorReq( doubledouble, "checkuserdata" );
		throwsErrorReq( somefunc, "checkuserdata" );
		throwsErrorReq( someclosure, "checkuserdata" );
		throwsErrorReq( stringstring, "checkuserdata" );
		throwsErrorReq( stringint, "checkuserdata" );
		throwsErrorReq( stringlong, "checkuserdata" );
		throwsErrorReq( stringdouble, "checkuserdata" );
		throwsErrorReq( table, "checkuserdata" );
		assertEquals( sampleobject, userdataobj.checkUserdata() );
		assertEquals( sampleobject, userdataobj.checkUserdata() );
		assertEquals( sampledata, userdatacls.checkUserdata() );
		assertEquals( sampledata, userdatacls.checkUserdata() );
	}

	private void throwsErrorReqCheckUserdataClass(LuaValue obj, Class arg ) {
		try {
			obj.getClass().getMethod("checkUserdata", Class.class ).invoke(obj, arg);
		} catch (InvocationTargetException e) {
			if ( ! (e.getTargetException() instanceof LuaError) )
				fail("not a LuaError: "+e.getTargetException());
			return; // pass
		} catch ( Exception e ) {
			fail( "bad exception: "+e );
		}
		fail("failed to throw LuaError as required");
	}
	
	public void testCheckUserdataClass() {
		throwsErrorReqCheckUserdataClass( somenil,  Object.class );
		throwsErrorReqCheckUserdataClass( somenil,  MyData.class);
		throwsErrorReqCheckUserdataClass( sometrue,  Object.class );
		throwsErrorReqCheckUserdataClass( zero,  MyData.class);
		throwsErrorReqCheckUserdataClass( intint,  MyData.class);
		throwsErrorReqCheckUserdataClass( longdouble,  MyData.class);
		throwsErrorReqCheckUserdataClass( somefunc,  MyData.class);
		throwsErrorReqCheckUserdataClass( someclosure,  MyData.class);
		throwsErrorReqCheckUserdataClass( stringstring,  MyData.class);
		throwsErrorReqCheckUserdataClass( stringint,  MyData.class);
		throwsErrorReqCheckUserdataClass( stringlong,  MyData.class);
		throwsErrorReqCheckUserdataClass( stringlong,  MyData.class);
		throwsErrorReqCheckUserdataClass( stringdouble,  MyData.class);
		throwsErrorReqCheckUserdataClass( table,  MyData.class);
		throwsErrorReqCheckUserdataClass( thread,  MyData.class);
		assertEquals( sampleobject, userdataobj.checkUserdata(Object.class) );
		assertEquals( sampleobject, userdataobj.checkUserdata() );
		assertEquals( sampledata, userdatacls.checkUserdata(MyData.class) );
		assertEquals( sampledata, userdatacls.checkUserdata(Object.class) );
		assertEquals( sampledata, userdatacls.checkUserdata() );
		// should fail due to wrong class
		try {
			Object o = userdataobj.checkUserdata(MyData.class);
			fail( "did not throw bad type error" );
			assertTrue( o instanceof MyData );
		} catch ( LuaError le ) {
			assertEquals( "org.luaj.vm2.TypeTest$MyData expected, got userdata", le.getMessage() );
		}
	}
	
	public void testCheckValue() {
		throwsErrorReq( somenil, "checknotnil" );
		assertEquals( sometrue, sometrue.checkNotNil() );
		assertEquals( somefalse, somefalse.checkNotNil() );
		assertEquals( zero, zero.checkNotNil() );
		assertEquals( intint, intint.checkNotNil() );
		assertEquals( longdouble, longdouble.checkNotNil() );
		assertEquals( somefunc, somefunc.checkNotNil() );
		assertEquals( someclosure, someclosure.checkNotNil() );
		assertEquals( stringstring, stringstring.checkNotNil() );
		assertEquals( stringint, stringint.checkNotNil() );
		assertEquals( stringlong, stringlong.checkNotNil() );
		assertEquals( stringdouble, stringdouble.checkNotNil() );
		assertEquals( thread, thread.checkNotNil() );
		assertEquals( table, table.checkNotNil() );
		assertEquals( userdataobj, userdataobj.checkNotNil() );
		assertEquals( userdatacls, userdatacls.checkNotNil() );
	}
	
}
