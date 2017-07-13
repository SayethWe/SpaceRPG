package sineSection.spaceRPG.script;

@FunctionalInterface
public interface TriFunction<R, A, B, C> {
	public R apply(A a, B b, C c);
}
