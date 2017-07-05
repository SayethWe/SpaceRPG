package sineSection.spaceRPG.script;

import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import sineSection.util.ScriptHelper;

public class Script {

	private String script;
	private ArrayList<Scriptable> scriptables;

	public Script(String script) {
		this.scriptables = new ArrayList<>();
		this.script = script;
	}

	public void addScriptable(Scriptable scriptable) {
		if (scriptables.contains(scriptable))
			scriptables.remove(scriptable);
		else
			scriptables.add(scriptable);
	}

	public void run(ScriptEngine sEng) throws ScriptException {
		if (sEng != null) {
			for (Scriptable s : scriptables) {
				if (s.getScriptFunctions() != null)
					s.getScriptFunctions().forEach((method) -> ScriptHelper.addMethodToScriptEngine(sEng, method)); // TODO addMethodToScriptEngine(ScriptEngine, Method);
				if (s.getScriptVars() != null)
					s.getScriptVars().forEach((name, value) -> sEng.put(name, value));
			}
			sEng.eval(script);
		}
	}

}
