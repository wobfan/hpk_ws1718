package semaphore;

class Semaphore<T extends Object> {
	T[] objectPool;
	private int currentSize;

	public Semaphore(T[] objects) {
		objectPool = objects;
		currentSize = objectPool.length;
	}
	
	public void fill (T obj) {
		objectPool[objectPool.length] = obj;
		currentSize = objectPool.length;
	}
	
	public synchronized T require() throws Exception {
		while (currentSize == 0) wait();
		currentSize--;
		return objectPool[currentSize];
	}
	
	public synchronized void release(T object) {
		currentSize++;
		notify();
	}
}

public class SemaphoreWork {

	public static void main(String[] args) {
		
		Integer list[] = {1, 2, 3}; 
		Thread[] threads = new Thread[4];
		Semaphore<Integer> pool = new Semaphore<Integer>(list);
		
		Runnable task = new Runnable() {
			@Override
			public void run() {
				System.out.println("hey, i'm " + Thread.currentThread());
				while(true) {
					try {
						System.out.println(Thread.currentThread() + ": trying to get a resource now.");
						Integer hold = pool.require();
						System.out.println(Thread.currentThread() + ": i just got myself a resource, and i will hold it for 5 seconds.");
						Thread.sleep(5000);
						
						pool.release(hold);
						System.out.println(Thread.currentThread() + ": i released a resource. will try to get another one in 5 seconds.");
						Thread.sleep(5000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		};
		
		for (int i = 0; i < 4; i++) {
			threads[i] = new Thread(task);
			threads[i].start();
		}
	}

}
