package closure;

interface Comperator<T> {
	boolean cmp(final T a, final T b);
}

public class Closure_or_Interface<T> {

	public static void main(String[] args) {
		
		Double[] dfield = {1.,2.,-4.,3.};
		
		double dmax = Closure_or_Interface.max(dfield, new Comperator<Double>() {
			@Override
			public boolean cmp(Double a, Double b) {
				return a.doubleValue() > b.doubleValue();
			}
		});
		
		double dmax_closure = Closure_or_Interface.max(dfield, (a,b) -> a > b);
		
		System.out.println("max via interface: " + dmax);
		System.out.println("max via closure: " + dmax_closure);
		
		
	}
	
	public static <T> T max(final T[] v, final Comperator<T> fp) {
		int k = 0;
		int len = v.length;
		
		for (int j = 1; j < len; j++) {
			if (fp.cmp(v[j], v[k])) k = j;
		}
		
		return v[k];
	}

}
