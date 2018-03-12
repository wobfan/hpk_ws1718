package uebung4;

import java.lang.reflect.Array;
import java.util.*;

public class Aufgabe1 {
	
	public static <T> List<T> createList(Class<T> type, int n) throws Exception {
		List<T> list = new ArrayList<T>(n);
		for (int j = 0; j < n; j++) {
			list.add(type.newInstance());
		}
		return list;
	}
	
	public static <T> T[] createArray(Class<T> type, int n) throws Exception {
		T[] array = (T[]) Array.newInstance(type, n);
		return array;
	}

}
