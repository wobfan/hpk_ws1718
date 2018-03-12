package uebung3;

import java.util.*;
import java.util.concurrent.Semaphore;

interface ResourcePool<T> {
	void release(T resource);					// release a resource into pool, may block if pool full
	T require();								// acquire a resource, may block if pool empty
	int size();									// actual pool size
}

class SemaphoreJava<T> implements ResourcePool<T> {
	Semaphore sem;
	int size;
	List<T> resourcePool;

	@SafeVarargs
	public SemaphoreJava(T... ts) {
		resourcePool = new ArrayList<T>(Arrays.asList(ts));
		sem = new Semaphore(resourcePool.size());
	}

	@Override
	public synchronized void release(T resource) {
		sem.release();
		resourcePool.add(resource);
	}

	@Override
	public synchronized T require() {
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return resourcePool.remove(resourcePool.size() - 1);
	}

	@Override
	public int size() {
		return sem.availablePermits();
	}

}

class SemaphoreManual<T> implements ResourcePool<T> {
	int size;
	List<T> resourcePool;

	/*
	 * TODO implements maximum waiting time, if exceeded return an empty object or null
	 */

	@SafeVarargs
	public SemaphoreManual(T... ts) {
		resourcePool = new ArrayList<>();
		size = ts.length;
		for(T t: ts) resourcePool.add(t);
	}

	@Override
	public synchronized void release(T resource) {
		size++;
		resourcePool.add(resource);
	}

	@Override
	public synchronized T require() {
		if (size == 0)
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		size--;
		T output = resourcePool.get(size);
		resourcePool.remove(size);
		return output;
	}

	@Override
	public int size() {
		return size;
	}

}

public class Aufgabe1<T> implements Runnable {
	ResourcePool<T> sem;

	public Aufgabe1(ResourcePool<T> sem) {
		this.sem = sem;
	}

	public static void main(String[] args) {
		String[] resources = {"das", "hier", "sind", "einfallsreiche"};
		Aufgabe1<String> manual = new Aufgabe1<String>(new SemaphoreManual<String>(resources));
		Aufgabe1<String> java = new Aufgabe1<String>(new SemaphoreJava<String>(resources));
		
		Thread[] tsJava = new Thread[8];
		Thread[] tsManual = new Thread[8];
		for (int i = 0; i < 8; i++) tsJava[i] = new Thread(java);
		for (int i = 0; i < 8; i++) tsManual[i] = new Thread(manual);
		
		long javaTime = System.nanoTime();
		System.out.println("now using the java semaphore implementation. every thread will hold a resource 2 times for 5 seconds. between both it will sleep for 1 second. using 8 threads.");
		for (Thread t: tsJava) t.start();
		for (Thread t: tsJava) try { t.join(); } catch (InterruptedException e) { e.printStackTrace(); }
		javaTime = (System.nanoTime() - javaTime) / 1000000;
		
		long manualTime = System.nanoTime();
		System.out.println("now using the self-programmed semaphore implementation. every thread will hold a resource 2 times for 5 seconds. between both it will sleep for 1 second. using 8 threads.");
		for (Thread t: tsManual) t.start();
		for (Thread t: tsManual) try { t.join(); } catch (InterruptedException e) { e.printStackTrace(); }
		manualTime = (System.nanoTime() - manualTime) / 1000000;
		
		System.out.println("java needed " + javaTime + " whereas the self-programmed method needed " + manualTime + ". difference is " + Math.abs(javaTime - manualTime));
	}

	@Override
	public void run() {
		for (int i = 0; i < 2; i++) {
			long start = System.nanoTime();
			System.out.println(Thread.currentThread() + ": REQUIRING RESOUCE...");
			T resource = sem.require();
			System.out.println(Thread.currentThread() + ": RESOURCE RECEIVED\t\tTIME ELAPSED: " + (System.nanoTime() - start)/1000000 + " ns");
			try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }

			sem.release(resource);
			System.out.println(Thread.currentThread() + ": RESOURCE RELEASED");

			try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
		}
	}



}
