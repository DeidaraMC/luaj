package org.luaj.vm2.lib;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.exception.LuaArgumentException;
import org.luaj.vm2.exception.LuaException;

/**
 * Subclass of {@link LibFunction} which implements the lua standard {@code table}
 * library.
 * 
 * <p>
 * Typically, this library is included as part of a call to either
 * {@link org.luaj.vm2.lib.jse.JsePlatform#standardGlobals()} or {@link org.luaj.vm2.lib.jme.JmePlatform#standardGlobals()}
 * <pre> {@code
 * Globals globals = JsePlatform.standardGlobals();
 * System.out.println( globals.get("table").get("length").call( LuaValue.tableOf() ) );
 * } </pre>
 * <p>
 * To instantiate and use it directly,
 * link it into your globals table via {@link LuaValue#load(LuaValue)} using code such as:
 * <pre> {@code
 * Globals globals = new Globals();
 * globals.load(new JseBaseLib());
 * globals.load(new PackageLib());
 * globals.load(new TableLib());
 * System.out.println( globals.get("table").get("length").call( LuaValue.tableOf() ) );
 * } </pre>
 * <p>
 * This has been implemented to match as closely as possible the behavior in the corresponding library in C.
 * @see LibFunction
 * @see org.luaj.vm2.lib.jse.JsePlatform
 * @see org.luaj.vm2.lib.jme.JmePlatform
 * @see <a href="http://www.lua.org/manual/5.2/manual.html#6.5">Lua 5.2 Table Lib Reference</a>
 */
public class TableLib extends TwoArgFunction {

	/** Perform one-time initialization on the library by creating a table
	 * containing the library functions, adding that table to the supplied environment,
	 * adding the table to package.loaded, and returning table as the return value.
	 * @param modname the module name supplied if this is loaded via 'require'.
	 * @param env the environment to load into, typically a Globals instance.
	 */
	public LuaValue call(LuaValue modname, LuaValue env) {
		LuaTable table = new LuaTable();
		table.set("concat", new concat());
		table.set("insert", new insert());
		table.set("pack", new pack());
		table.set("remove", new remove());
		table.set("sort", new sort());
		table.set("unpack", new unpack());
		env.set("table", table);
		if (!env.get("package").isNil()) env.get("package").get("loaded").set("table", table);
		return NIL;
	}
	
	// "concat" (table [, sep [, i [, j]]]) -> string
	static class concat extends TableLibFunction {
		public LuaValue call(LuaValue list) {
			return list.checkTable().concat(EMPTYSTRING,1,list.length());
		}
		public LuaValue call(LuaValue list, LuaValue sep) {
			return list.checkTable().concat(sep.checkLuaString(),1,list.length());
		}
		public LuaValue call(LuaValue list, LuaValue sep, LuaValue i) {
			return list.checkTable().concat(sep.checkLuaString(),i.checkInt(),list.length());
		}
		public LuaValue call(LuaValue list, LuaValue sep, LuaValue i, LuaValue j) {
			return list.checkTable().concat(sep.checkLuaString(),i.checkInt(),j.checkInt());
		}
	}

	// "insert" (table, [pos,] value)
	static class insert extends VarArgFunction {
		public Varargs invoke(Varargs args) {
			switch (args.narg()) {
			case 2: {
				LuaTable table = args.checkTable(1);
				table.insert(table.length()+1,args.arg(2));
				return NONE;
			}
			case 3: {
				LuaTable table = args.checkTable(1);
				int pos = args.checkInt(2);
				int max = table.length() + 1;
				if (pos < 1 || pos > max)  throw new LuaArgumentException(2, "position out of bounds: " + pos + " not between 1 and " + max);
				table.insert(pos, args.arg(3));
				return NONE;
			}
			default: {
				throw new LuaException("wrong number of arguments to 'table.insert': " + args.narg() + " (must be 2 or 3)");
			}
			}
		}
	}
	
	// "pack" (...) -> table
	static class pack extends VarArgFunction {
		public Varargs invoke(Varargs args) {
			LuaValue t = tableOf(args, 1);
			t.set("n", args.narg());
			return t;
		}
	}

	// "remove" (table [, pos]) -> removed-ele
	static class remove extends VarArgFunction {
		public Varargs invoke(Varargs args) {
			LuaTable table = args.checkTable(1);
			int size = table.length();
			int pos = args.optionalInt(2, size);
			if (pos != size && (pos < 1 || pos > size + 1)) {
				throw new LuaArgumentException(2, "position out of bounds: " + pos + " not between 1 and " + (size + 1));
			}
			return table.remove(pos);
		}
	}

	// "sort" (table [, comp])
	static class sort extends VarArgFunction {
		public Varargs invoke(Varargs args) {
			args.checkTable(1).sort(
					args.isNil(2)? NIL: args.checkFunction(2));
			return NONE;
		}
	}

	
	// "unpack", // (list [,i [,j]]) -> result1, ...
	static class unpack extends VarArgFunction {
		public Varargs invoke(Varargs args) {
			LuaTable t = args.checkTable(1);
			// do not waste resource for calc rawlen if arg3 is not nil
			int len = args.arg(3).isNil() ? t.length() : 0;
			return t.unpack(args.optionalInt(2, 1), args.optionalInt(3, len));
		}
	}
}
