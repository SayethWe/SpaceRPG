package sineSection.spaceRPG.script;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

public class SineScriptEngineFactory implements ScriptEngineFactory {

	public String getEngineName() {
		return "SineScript";
	}

	public String getEngineVersion() {
		return "0.1a";
	}

	public List<String> getExtensions() {
		ArrayList<String> extList = new ArrayList<String>();
		extList.add("sinscr");
		extList.add("sinescript");
		extList.add("sscr");
		return extList;
	}

	public String getLanguageName() {
		return null;
	}

	public String getLanguageVersion() {
		return null;
	}

	public String getMethodCallSyntax(String arg0, String arg1, String... arg2) {
		return null;
	}

	public List<String> getMimeTypes() {
		return null;
	}

	public List<String> getNames() {
		return null;
	}

	public String getOutputStatement(String arg0) {
		return null;
	}

	public Object getParameter(String arg0) {
		return null;
	}

	public String getProgram(String... arg0) {
		return null;
	}

	public ScriptEngine getScriptEngine() {
		return null;
	}

}
