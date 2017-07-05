package sineSection.spaceRPG.script;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public interface Scriptable {
	HashMap<String, Object> getScriptVars();
	ArrayList<Method> getScriptFunctions();
}
