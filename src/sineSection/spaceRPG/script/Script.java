package sineSection.spaceRPG.script;

import java.util.ArrayList;

import javax.script.ScriptEngine;

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

	public void run(ScriptEngine sEng) throws Exception {
		if (sEng != null) {
			for (Scriptable s : scriptables) {
				if (s.getScriptVars() != null)
					s.getScriptVars().forEach((name, value) -> sEng.put(name, value));
				
				if (s.getScriptSuppliers() != null)
					s.getScriptSuppliers().forEach((name, supplier) -> sEng.put(name, supplier));

				if (s.getScriptConsumers() != null)
					s.getScriptConsumers().forEach((name, consumer) -> sEng.put(name, consumer));

				if (s.getScriptBiConsumers() != null)
					s.getScriptBiConsumers().forEach((name, biConsumer) -> sEng.put(name, biConsumer));
				
				if (s.getScriptTriConsumers() != null)
					s.getScriptTriConsumers().forEach((name, triConsumer) -> sEng.put(name, triConsumer));

				if (s.getScriptFunctions() != null)
					s.getScriptFunctions().forEach((name, function) -> sEng.put(name, function));

				if (s.getScriptBiFunctions() != null)
					s.getScriptBiFunctions().forEach((name, biFunction) -> sEng.put(name, biFunction));
				
				if (s.getScriptTriFunctions() != null)
					s.getScriptTriFunctions().forEach((name, triFunction) -> sEng.put(name, triFunction));
				
				if (s.getScriptRunnables() != null)
					s.getScriptRunnables().forEach((name, runnable) -> sEng.put(name, runnable));
			}
			sEng.eval(script);
		}
	}

}
