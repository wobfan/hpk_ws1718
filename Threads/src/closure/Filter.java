package closure;

import java.util.*;

interface Condition<T> {
	public boolean match(T obj);
}

public class Filter<T> {
	Condition<T> condition;
	
	public Filter(Condition<T> condition) {
		this.condition = condition;
	}
	
	public void doFilter(List<T> list) {
		ListIterator<T> lItr = list.listIterator();
		while(lItr.hasNext()) {
			if (!condition.match(lItr.next())) lItr.remove();
		}
	}
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>(Arrays.asList("hi", "das", "hier", "sind", "Strings", "lol"));
		Filter<String> filter = new Filter<String>((s) -> s.length() >= 4 ? true : false );
		filter.doFilter(list);
		list.forEach((s) -> System.out.println(s));
	}
}
