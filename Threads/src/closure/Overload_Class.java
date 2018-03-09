package closure;

public class Overload_Class {
	
	interface NewObject {
		public void work();
	}

	public static void main(String[] args) {
		Overload_Class obj = new Overload_Class();
		obj.test();
	}
	
	public void printf(String fmt, Object ...args) {
		System.out.println(String.format(fmt, args));
	}
	
	public void toString(String fmt, Object ...args) {
		System.out.println(String.format(fmt, args));
	}
	
	public void test() {
		String s = "local variable";
		NewObject test1 = new NewObject() {
			public void work() {
				toString("hi");		// anonymous class can call a method of the original class
			}
		};
		
		test1.work();
	}

}
