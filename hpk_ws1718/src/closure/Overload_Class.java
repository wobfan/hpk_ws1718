package closure;

public class Overload_Class {
	
	interface NewObject {
		public void work();
	}

	public static void main(String[] args) {
		Overload_Class obj = new Overload_Class();
		obj.test(obj);
	}
	
	public void printf(String fmt, Object ...args) {
		System.out.println(String.format(fmt, args));
	}
	
	public void toString(Object arg) {
		System.out.println(String.format("%s[%s]", arg.getClass().getName(), arg.toString()));
	}
	
	public NewObject outerLambda = () -> System.out.println("this works in a lambda expression, too");
	
	public void test(Overload_Class self) {
		String s = "local variable";	// s wird "effectively final" = final gesetzt, da es einmal deklariert wird, aber nie verändert

		NewObject test1 = new NewObject() {
			public void work() {
				/*
				 * - s ist in der inneren klasse verfügbar, da es final ist (s.o.)
				 * - toString muss mit self. genau gekennzeichnet werden, da test1 ja nicht von Overload_Class erbt, somit also toStirng von Object 
				 *   benutzt werden würde
				 */
				self.toString(s);
				/*
				 * printf hingegen ist im scope, da Object kein printf besitzt. daher sucht der compiler in der äußeren klasse
				 */
				printf("printf works because it is not inherited by a superclass.");
			}
		};
		
		outerLambda.work();
		
		test1.work();
	}

}
