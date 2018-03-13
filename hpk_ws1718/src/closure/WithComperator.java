package closure;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WithComperator {
	Integer value;
	
	public WithComperator(Integer value) { this.value = value; }
	
	public static void main(String[] args) {
		List<WithComperator> list = Arrays.asList(new WithComperator(1), new WithComperator(4), new WithComperator(-13), new WithComperator(1));
		System.out.println("unsorted:");
		list.forEach((s) -> System.out.println(s.value));
		
		Collections.sort(list, (a,b) -> a.value - b.value);
		System.out.println("sorted:");
		list.forEach((s) -> System.out.println(s.value));
		
	}
	
}
