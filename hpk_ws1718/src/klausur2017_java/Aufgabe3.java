package klausur2017_java;

import java.lang.reflect.Array;
import java.util.*;

interface IPrototype<T extends IPrototype> { };

public class Aufgabe3<T extends IPrototype<T>> {
	
	public List<T> create(T prototype, int n) {
		List<T> list;
		T[] array = (T[]) Array.newInstance(prototype.getClass(), n);
		list = new ArrayList<T>(Arrays.asList(array));
		return list;
	}
	
	public T create(T prototype) throws Exception {
		Class<T> clazz = (Class<T>) prototype.getClass();
		return clazz.newInstance();
	}
	
	public T create(Class<T> type) throws Exception {
		return type.newInstance();
	}
	
}
