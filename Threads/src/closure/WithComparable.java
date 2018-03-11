package closure;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;

public class WithComparable implements Comparable<WithComparable> {
	Integer value;
	
	public WithComparable(Integer value) {
		this.value = value;
	}
	
	public static void main(String[] args) {
		List<WithComparable> list = Arrays.asList(new WithComparable(1), new WithComparable(3), new WithComparable(2), new WithComparable(-13));
		System.out.println("unsorted:");
		list.forEach((i) -> System.out.println(i.value));
		
		Collections.sort(list);
		System.out.println("sorted:");
		list.forEach((i) -> System.out.println(i.value));
	}

	@Override
	public int compareTo(WithComparable arg0) {
		return (this.value - arg0.value);
	}

}
