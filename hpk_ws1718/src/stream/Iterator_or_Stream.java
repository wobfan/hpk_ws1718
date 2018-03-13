package stream;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.*;

interface Condition<T> {
	public boolean match(T obj);
}

public class Iterator_or_Stream<T> {
	
	private static void print(String msg) { System.out.println(msg); }
	
	public static void main(String[] args) {
		List<String> list = new ArrayList(Arrays.asList("das", "hier", "sind", "unterschiedlich", "lange", "Strings"));
		Condition<String> condition = (s -> s.length() >= 5 ? true : false);
		Consumer<String> consumer = s -> System.out.print(s + " ");
		Iterator_or_Stream<String> instance = new Iterator_or_Stream<String>();
		
		print("full list: ");
		list.forEach(consumer);
		
		print("\n\niterator: ");
		instance.iterator(list, condition, consumer);
		print("\n\niterator with foreach: ");
		instance.iterator_foreach(list, condition, consumer);
		print("\n\nstream: ");
		instance.stream(list, condition, consumer);
		print("\n\nstream with filter: ");
		instance.stream_filter(list, condition, consumer);
		print("\n\nstream with filter (parallel): ");
		instance.stream_filter_parallel(list, condition, consumer);
		print("\n\nstream with map: ");
		instance.stream_map(list, condition, consumer);
		
	}
	
	public void iterator(List<T> list, Condition<T> condition, Consumer<T> consumer) {
		Iterator<T> lItr = list.iterator();
		while(lItr.hasNext()) {
			T next = lItr.next();
			if(condition.match(next)) consumer.accept(next);
		}
	}
	
	public void iterator_foreach(List<T> list, Condition<T> condition, Consumer<T> consumer) {
		list.forEach(s -> { if(condition.match(s)) consumer.accept(s); });
	}
	
	public void stream(List<T> list, Condition<T> condition, Consumer<T> consumer) {
		Stream<T> stream = list.stream();
		stream.forEach(s -> {
			if(condition.match(s)) consumer.accept(s);
		});
	}
	
	public void stream_filter(List<T> list, Condition<T> condition, Consumer<T> consumer) {
		Stream<T> stream = list.stream();
		stream.filter(s -> condition.match(s))
			.forEach(consumer);		// forEach() needs a Consumer as Parameter, which would equal: s -> doSomething(s);, because Consumer.accept() needs only one object as paramenter
	}
	
	public void stream_filter_parallel(List<T> list, Condition<T> condition, Consumer<T> consumer) {
		Stream<T> stream = list.parallelStream();
		stream.filter(s -> condition.match(s))
			.forEach(consumer);		// forEach() needs a Consumer as Parameter, which would equal: s -> doSomething(s);, because Consumer.accept() needs only one object as paramenter
	}
	
	public void stream_map(List<T> list, Condition<T> condition, Consumer<T> consumer) {
		Stream<T> stream = list.stream();
		stream.filter(s -> condition.match(s))
			.map(s -> ((String)s).toUpperCase())
			.forEach((Consumer<String>)consumer);		
	}

}
