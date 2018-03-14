package klausur2015_java;

import java.util.*;

interface IPrototype<A extends IPrototype<A>> { }

class Factory<A extends IPrototype<A>> implements IPrototype<A> {
	public List<A> create(A prototype, int n) throws Exception {
		ArrayList<A> list = new ArrayList<A>();
		for (int i = 0; i < n; i++) {
			list.add(create(prototype));
		}
		return list;
	}
	public A create(A prototype) throws Exception {
		Class type = prototype.getClass();
		return (A) type.newInstance();
	}
	public A create(Class<? extends A> type) throws Exception {
		return (A) type.newInstance();
	}
}

class Bar implements IPrototype<Bar> {
	public Bar() {};
}
class Foo implements IPrototype<Foo> {
	public Foo() {};
}

public class Aufgabe3 {
	public static void main(String[] args) throws Exception {
		Factory<Bar> factBar = new Factory<Bar>();
		Factory<Foo> factFoo = new Factory<Foo>();
		
		Foo protFoo = new Foo();
		Bar protBar = new Bar();
		
		Foo fooInst = factFoo.create(protFoo);
		List<Bar> barList = factBar.create(protBar, 10);
		
		// do something with Foo and Bar
	}
}