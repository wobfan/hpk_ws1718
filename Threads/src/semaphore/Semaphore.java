package semaphore;

public class Semaphore<T extends Object> {
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
 