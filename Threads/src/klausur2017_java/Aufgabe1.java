package klausur2017_java;

import java.util.*;

interface F<X, Y> {
	public Y eval(X x);
}

public class Aufgabe1 {
	public static <X, Y> void map(X x[], Y y[], F<X,Y> function) {
		for(int i = 0; i < x.length; i++) {
			y[i] = function.eval(x[i]);
		}
	}
	
	public static void main(String[] args) {
		Integer ints[] = {1,2,3,4,5,6,7,8,9,0};
		Integer ints_[] = new Integer[10];
		F<Integer, Integer> funct = (s) -> (s+2);
		map(ints, ints_, funct);
		
		List<Integer> list = Arrays.asList(ints_);
		list.forEach(s -> System.out.println(s));
	}
}