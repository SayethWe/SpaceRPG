package sineSection.util;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;

/**
 * 
 * @author Richard Abbott
 *
 */
public class ScriptHelper {

	public static final String VAR_STRING_INSERTION_REGEX = "(%\\w+%)";
	private static final Pattern VAR_STRING_INSERTION_PATTERN = Pattern.compile(VAR_STRING_INSERTION_REGEX);
	public static final String STRING_NEW_LINE_REGEX = "(%n(?!\\w*%))";

	/**
	 * Takes the input <code>string</code> and replaces all
	 * <code>%&lt;varName&gt;%</code> with the respective variable with the name
	 * <code>"VarName"</code> in the <code>scriptEngine</code> Example:
	 * <p>
	 * <code>
	 * ScriptEngine vars:<br>
	 * <p>a = 20</p>
	 * Input:
	 * <p>
	 * "Hey, this is a string containing a var! %a%"
	 * </p>
	 * Returns:<br>
	 * <p>
	 * "Hey, this is a string containing a var! 20"
	 * </p>
	 * <br>
	 * Incorrect:
	 * <p>
	 * "Hey, this is a string containing two vars! %a% <b>%b%</b>"
	 * </p>
	 * </code> The incorrect example's script engine does not have the variable
	 * <code>"b"</code>, so <code>%b%</code> will be removed.
	 * 
	 * @return the replaced string
	 * @author Richard Abbott
	 */
	public static String putVarsInString(ScriptEngine sEng, String s) {
		String ret = s.toString();
		Matcher m = VAR_STRING_INSERTION_PATTERN.matcher(s);
		while (m.find()) {
			String varInsert = s.substring(m.start(), m.end());
			String varName = varInsert.substring(1, varInsert.length() - 1);
			if (sEng.get(varName) != null) {
				ret.substring(m.start(), m.end());
				String[] parts = Utils.splitString(ret, m.start());
				ret = parts[0];
				ret.concat(sEng.get(varName).toString());
				ret.concat(parts[1]);
			} else {
				ret.substring(m.start(), m.end());
			}
		}
		return ret;
	}

	/**
	 * Takes the input <code>string</code> and checks if it contains
	 * <code>%&lt;varName&gt;%</code> and also matches with the variables in the
	 * <code>scriptEngine</code><br>
	 * Example:
	 * <p>
	 * <code>
	 * ScriptEngine vars:<br>
	 * <p>a = 20</p>
	 * Input:
	 * <p>
	 * "Hey, this is a string containing a var! %a%"
	 * </p>
	 * Returns:<br>
	 * <p>
	 * true
	 * </p>
	 * <br>
	 * Incorrect:
	 * <p>
	 * "Hey, this is a string containing two vars! %c% <b>%d%</b>"
	 * </p>
	 * Returns:<br>
	 * <p>
	 * false
	 * </p>
	 * </code> The incorrect example's script engine does not have the variables
	 * <code>"c"</code> and <code>"d"</code>, so there are no valid variables.
	 * 
	 * @return if the string has valid variables
	 * @author Richard Abbott
	 */
	public static boolean hasVarsInString(ScriptEngine sEng, String s) {
		int validVars = 0;
		Matcher m = VAR_STRING_INSERTION_PATTERN.matcher(s);
		if (sEng != null)
			while (m.find()) {
				String varInsert = s.substring(m.start(), m.end());
				String varName = varInsert.substring(1, varInsert.length() - 1);
				if (sEng.get(varName) != null) {
					validVars++;
				}
			}
		return validVars > 0;
	}

	@Deprecated
	public static void addMethodToScriptEngine(ScriptEngine sEng, Method method) {
		try {
			// TODO adding methods to script engine
//			Bindings tempBindings = sEng.createBindings();
//			tempBindings.put("method", method);
//			tempBindings.put("className", method.getDeclaringClass());
//			tempBindings.put("arguments", method.getParameterTypes());
//			sEng.eval("method.apply(className, arguments)", tempBindings);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Takes the input <code>string</code> and removes all
	 * <code>%&lt;varName&gt;%</code><br>
	 * Example:
	 * <p>
	 * <code>
	 * Input:
	 * <p>
	 * "Hey, this is not a string containing a var! %a%"
	 * </p>
	 * Returns:<br>
	 * <p>
	 * "Hey, this is not a string containing a var! "
	 * </p>
	 * </code> Does not remove spaces that are left. Use {@link String#trim()}.
	 * 
	 * @return the replaced string
	 * @author Richard Abbott
	 */
	public static String removeAllVarsFromString(String s) {
		return s.replaceAll(VAR_STRING_INSERTION_REGEX, "");
	}
	
	/**
	 * Takes the input <code>string</code> and replaces all <code>%n</code> it with a newline char.
	 * Example:
	 * <p>
	 * <code>
	 * Input:
	 * <p>
	 * "Hey, this is a string%ncontaining a new line!"
	 * </p>
	 * Returns:<br>
	 * <p>
	 * "Hey, this is a string\ncontaining a new line! "
	 * </p>
	 * 
	 * @return the replaced string
	 * @author Richard Abbott
	 */
	public static String replaceAllNewLinesInString(String s) {
		return s.replaceAll(STRING_NEW_LINE_REGEX, "\n");
	}

}
