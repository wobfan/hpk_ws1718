package klausur2015_java;

interface Unary<A, B> {
	public B f(A a);
}


public class Aufgabe1 {

	public static <X, Y> void map(X[] x, Y[] y, Unary<X, Y> function) {
		for(int i = 0; i < x.length; i++) {
			y[i] = function.f(x[i]);
		}
	}
	
}
