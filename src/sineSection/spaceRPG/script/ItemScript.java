package sineSection.spaceRPG.script;

import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ItemScript {

	private String script;

	public ItemScript(String script) {
		this.script = script;
		System.out.println(script);
	}

	public boolean parse() {

		return false;
	}

	public void run(Map<String, Object> vars) {
		try {
			ScriptEngineManager enMgr = new ScriptEngineManager();
			ScriptEngine ssEng = enMgr.getEngineByName("JavaScript");
			vars.forEach((name, var) -> ssEng.put(name, var));
			ssEng.eval(script);
		} catch (ScriptException e) {
			System.err.println("Error in script!");
			e.printStackTrace();
		}
	}

}
