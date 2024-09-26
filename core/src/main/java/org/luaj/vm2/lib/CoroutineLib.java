package org.luaj.vm2.lib;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.exception.LuaException;

/**
 * Subclass of {@link LibFunction} which implements the lua standard {@code coroutine}
 * library.
 * <p>
 * The coroutine library in luaj has the same behavior as the
 * coroutine library in C, but is implemented using Java Threads to maintain
 * the call state between invocations.  Therefore it can be yielded from anywhere,
 * similar to the "Coco" yield-from-anywhere patch available for C-based lua.
 * However, coroutines that are yielded but never resumed to complete their execution
 * may not be collected by the garbage collector.
 * <p>
 * Typically, this library is included as part of a call to either
 * {@link org.luaj.vm2.lib.jse.JsePlatform#standardGlobals()} or {@link org.luaj.vm2.lib.jme.JmePlatform#standardGlobals()}
 * <pre> {@code
 * Globals globals = JsePlatform.standardGlobals();
 * System.out.println( globals.get("coroutine").get("running").call() );
 * } </pre>
 * <p>
 * To instantiate and use it directly,
 * link it into your globals table via {@link LuaValue#load(LuaValue)} using code such as:
 * <pre> {@code
 * Globals globals = new Globals();
 * globals.load(new JseBaseLib());
 * globals.load(new PackageLib());
 * globals.load(new CoroutineLib());
 * System.out.println( globals.get("coroutine").get("running").call() );
 * } </pre>
 * <p>
 * @see LibFunction
 * @see org.luaj.vm2.lib.jse.JsePlatform
 * @see org.luaj.vm2.lib.jme.JmePlatform
 * @see <a href="http://www.lua.org/manual/5.2/manual.html#6.2">Lua 5.2 Coroutine Lib Reference</a>
 */
public class CoroutineLib extends TwoArgFunction {

	static int coroutine_count = 0;

	Globals globals;
	
	/** Perform one-time initialization on the library by creating a table
	 * containing the library functions, adding that table to the supplied environment,
	 * adding the table to package.loaded, and returning table as the return value.
	 * @param modname the module name supplied if this is loaded via 'require'.
	 * @param env the environment to load into, which must be a Globals instance.
	 */
	public LuaValue call(LuaValue modname, LuaValue env) {
		globals = env.checkGlobals();
		LuaTable coroutine = new LuaTable();
		coroutine.set("create", new Create());
		coroutine.set("resume", new Resume());
		coroutine.set("running", new Running());
		coroutine.set("status", new Status());
		coroutine.set("yield", new Yield());
		coroutine.set("wrap", new Wrap());
		env.set("coroutine", coroutine);
		if (!env.get("package").isNil()) env.get("package").get("loaded").set("coroutine", coroutine);
		return coroutine;
	}

	final class Create extends LibFunction {
		public LuaValue call(LuaValue f) {
			return new LuaThread(globals, f.checkFunction());
		}
	}

	static final class Resume extends VarArgFunction {
		public Varargs invoke(Varargs args) {
			final LuaThread t = args.checkThread(1);
			return t.resume( args.subArgs(2) );
		}
	}

	final class Running extends VarArgFunction {
		public Varargs invoke(Varargs args) {
			final LuaThread r = globals.running;
			return varargsOf(r, valueOf(r.isMainThread()));
		}
	}

	static final class Status extends LibFunction {
		public LuaValue call(LuaValue t) {
			LuaThread lt = t.checkThread();
			return valueOf( lt.getStatus() );
		}
	}
	
	final class Yield extends VarArgFunction {
		public Varargs invoke(Varargs args) {
			return globals.yield( args );
		}
	}

	final class Wrap extends LibFunction {
		public LuaValue call(LuaValue f) {
			final LuaValue func = f.checkFunction();
			final LuaThread thread = new LuaThread(globals, func);
			return new wrapper(thread);
		}
	}

	static final class wrapper extends VarArgFunction {
		final LuaThread luathread;
		wrapper(LuaThread luathread) {
			this.luathread = luathread;
		}
		public Varargs invoke(Varargs args) {
			final Varargs result = luathread.resume(args);
			if ( result.arg1().toBoolean() ) {
				return result.subArgs(2);
			}
			throw new LuaException( result.arg(2).toJString() );
		}
	}
}
