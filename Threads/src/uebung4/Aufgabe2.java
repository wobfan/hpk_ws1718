package uebung4;

import java.util.*;
import java.util.stream.Stream;

import uebung4.Aufgabe1;

class Buh {
	private static int idGenerator = 0;
	protected Integer id = new Integer(++idGenerator);

	@Override
	public String toString() {
		return String.format("%s-%d", getClass().getSimpleName(), id);
	}

	public int hashCode() {
		return this.id.hashCode();
	}

	public boolean equals(Object obj) {
		if (obj.getClass() == Buh.class) {
			if (this.id.equals(((Buh)obj).id)) return true;
		}
		return false;
	}
}

class Foo extends Buh implements Comparable<Foo> {
	public int compareTo(Foo that) {
		return this.id.compareTo(that.id);
	}
}

class Bar extends Buh {

}

public class Aufgabe2 extends Aufgabe1 {

	public static <T> T[] sort(T[] a) {
		Arrays.sort(a);
		return a;
	}

	public static void main(String[] args) throws Exception {
		final int n = 4;
		List<Foo> fooList = createList(Foo.class, n);
		List<Bar> barList = createList(Bar.class, n);
		Foo[] fooArray = createArray(Foo.class, n);
		Bar[] barArray = createArray(Bar.class, n);

		List<Buh> mixList = mixit(fooList, barList);
		Buh[] mixField = mixFields(fooArray, barArray);

		System.out.println(fooList);
		System.out.println(barList);
		System.out.println(mixList);
		// TODO fields arent generated correctly, as it's an array of nulls currently	
//		for (Buh b: mixField) System.out.print(b + " ");
		
		Stream mixListStream = mixList.stream();
		mixList.stream().filter(s -> (s.getClass() == Foo.class))
			.forEach(s -> System.out.print(s + ","));
		System.out.println();
		mixList.stream().filter(s -> (s.getClass() == Bar.class))
			.forEach(s -> System.out.print(s + ","));
		System.out.println();
	}
	
	public static <A extends Buh, B extends Buh> Buh[] mixFields(A[] f1, B[] f2) {
		Buh[] ret = new Buh[f1.length + f2.length];
		for (int i = 0; i < f1.length*2; i+=2) {
			ret[i] = f1[i/2];
			ret[i+1] = f2[i/2];
		}
		return ret;
	}

	public static <A extends Buh, B extends Buh> List<Buh> mixit(List<A> l1, List<B> l2) {

		List<Buh> resList = new ArrayList<>();
		for (int i = 0; i < l1.size() ; i++) {
			resList.add(l1.get(i));
			resList.add(l2.get(i));
		}

		return resList;
	}
	
	public static <A extends Buh, B extends Buh> void mixit_param(List<? super Buh> param, List<A> l1, List<B> l2) {

		int size = l1.size() + l2.size();
		for (int i = 0; i < size; i++) {
			param.add(l1.get(i));
			param.add(l2.get(i));
		}
	}


}
