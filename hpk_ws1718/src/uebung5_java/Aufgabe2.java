package uebung5_java;

import java.util.*;
import java.util.function.*;

public class Aufgabe2 {

	public static <A> void for_each(List<A> list, Predicate<A> filter, Consumer<A> job) {
		List<A> newList = new ArrayList<A>();
		
		for (A item: list) {
			if (filter.test(item)) newList.add(item);
		}
		for (A item: newList) {
			job.accept(item);
		}
	}
	
	public static void main(String args[]) {
		List<Integer> intList = Arrays.asList(1,3,4,5,6);
		
		for_each(intList, (s -> (s <= 4)), (s -> System.out.print(s + " ")));
	}
	
}
