package org.luaj.vm2.exception;

import org.luaj.vm2.LuaValue;

/**
 * RuntimeException that is thrown and caught in response to a lua error. 
 * <p>
 * {@link LuaException} is used wherever a lua call to {@code error()}
 * would be used within a script.
 * <p>
 * Since it is an unchecked exception inheriting from {@link RuntimeException},
 * Java method signatures do notdeclare this exception, althoug it can 
 * be thrown on almost any luaj Java operation.
 * This is analagous to the fact that any lua script can throw a lua error at any time.
 * <p> 
 * The LuaException may be constructed with a message object, in which case the message
 * is the string representation of that object.  getMessageObject will get the object
 * supplied at construct time, or a LuaString containing the message of an object 
 * was not supplied.
 */
public class LuaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public int level;
	
	public String fileline;

	public String traceback;

	public Throwable cause;

	private LuaValue object;
	
	/** Get the string message if it was supplied, or a string 
	 * representation of the message object if that was supplied.
	 */
	public String getMessage() {
		if (traceback != null)
			return traceback;
		String m = super.getMessage();
		if (m == null)
			return null;
		if (fileline != null)
			return fileline + " " + m;
		return m;
	}

	/** Get the LuaValue that was provided in the constructor, or 
	 * a LuaString containing the message if it was a string error argument.
	 * @return LuaValue which was used in the constructor, or a LuaString
	 * containing the message.
	 */
	public LuaValue getMessageObject() {
		if (object != null) return object;
		String m = getMessage();
		return m != null ? LuaValue.valueOf(m): null;
	}
	
	/** Construct LuaException when a program exception occurs. 
	 * <p> 
	 * All errors generated from lua code should throw LuaException(String) instead.
	 * @param cause the Throwable that caused the error, if known.  
	 */
	public LuaException(Throwable cause) {
		super( "vm error: "+cause );
		this.cause = cause;
		this.level = 1;
	}

	/**
	 * Construct a LuaException with a specific message.  
	 *  
	 * @param message message to supply
	 */
	public LuaException(String message) {
		super( message );
		this.level = 1;
	}		

	/**
	 * Construct a LuaException with a message, and level to draw line number information from.
	 * @param message message to supply
	 * @param level where to supply line info from in call stack
	 */
	public LuaException(String message, int level) {
		super( message );
		this.level = level;
	}	

	/**
	 * Construct a LuaException with a LuaValue as the message object,
	 * and level to draw line number information from.
	 * @param message_object message string or object to supply
	 */
	public LuaException(LuaValue message_object) {
		super( message_object.toJString() );
		this.object = message_object;
		this.level = 1;
	}	


	/** 
	 * Get the cause, if any.
	 */
	public Throwable getCause() {
		return cause;
	}


}
