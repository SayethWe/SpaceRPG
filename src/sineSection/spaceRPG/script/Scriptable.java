package sineSection.spaceRPG.script;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 
 * @author Richard Abbott
 * @see https://docs.oracle.com/javase/8/docs/api/java/util/function
 *
 */
public interface Scriptable {

	// TODO add all java.util.function interfaces

	/**
	 * @return a hashmap of variables to put into the script at when it runs
	 */
	HashMap<String, Object> getScriptVars();

	/**
	 * @return a hashmap of {@link java.util.function.Supplier} to put into the
	 *         script at when it runs
	 */
	HashMap<String, Supplier<?>> getScriptSuppliers();

	/**
	 * @return a hashmap of {@link java.util.function.Consumer} to put into the
	 *         script at when it runs
	 */
	HashMap<String, Consumer<?>> getScriptConsumers();

	/**
	 * @return a hashmap of {@link java.util.function.BiConsumer} to put into
	 *         the script at when it runs
	 */
	HashMap<String, BiConsumer<?, ?>> getScriptBiConsumers();

	/**
	 * @return a hashmap of {@link java.util.function.Function} to put into the
	 *         script at when it runs
	 */
	HashMap<String, Function<?, ?>> getScriptFunctions();

	/**
	 * @return a hashmap of {@link java.util.function.BiFunction} to put into
	 *         the script at when it runs
	 */
	HashMap<String, BiFunction<?, ?, ?>> getScriptBiFunctions();
}
