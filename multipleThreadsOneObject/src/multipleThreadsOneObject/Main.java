package multipleThreadsOneObject;

public class Main {

	public static void main(String[] args) {
		
		class AClass implements Runnable {

			int a = 5;
			int b = 4;
			int calls = 0;
			
			@Override
			public void run() {
				System.out.printf("[%s] starting. a=%d, b=%d, calls=%d\n", Thread.currentThread(), a, b, calls);
				calls++;
				System.out.printf("[%s] adding %d to %d, saving in variable b.\n", Thread.currentThread(), a, b);
				b = a + b;
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.printf("[%s] b is now %d\n", Thread.currentThread(), b);
			}
			
		}
		
		AClass anObject = new AClass();
		Thread t1 = new Thread(anObject);
		Thread t2 = new Thread(anObject);
		
		t1.start();
		t2.start();
		
	}

}
