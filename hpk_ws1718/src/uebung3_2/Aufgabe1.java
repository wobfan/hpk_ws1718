package uebung3_2;

import java.util.*;
import java.util.concurrent.*;

interface ResourcePool<T> {
	void release(T resource) throws Exception;
	T require() throws Exception;
	int size();
}

/*
 * ResourcePool für manuelle wait-notify Semantik
 */
class Buffer<T> implements ResourcePool<T> {
	List<T> pool = new ArrayList<T>();
	
	public Buffer(List<T> objects) {
		pool = objects;
	}
	
	public Buffer(T... objects) {
		for(T obj: objects) pool.add(obj);
	}

	@Override
	public synchronized void release(T resource) throws Exception {
		pool.add(resource);
		notify();
	}
	@Override
	public synchronized T require() throws Exception {
		if (size() != 0) {
			return pool.remove(size() - 1);
		} else wait();
		return null;
	}
	@Override
	public int size() {
		return pool.size();
	}
}

/*
 * ResourcePool für Java-basierte Semaphore
 */
class BufferSemaphore<T> implements ResourcePool<T> {
	List<T> pool = new ArrayList<T>();
	Semaphore sem;
	
	/*
	 * Semaphore wird in beiden Konstruktoren initialisiert,
	 * erstes Argument: availablePermits() werden gesetzt, also wie oft das die Semaphore erlauben etwas herauszunehmen,
	 * 					bevor gewartet werden muss, dass wieder etwas eingesetzt wird. (= pool.size());
	 * zweites Argument:	true, die Semaphore Implementierung achtet darauf, dass die Resourcen fair vergeben werden
	 * 						false wäre auch möglich, sollte genau so funktionieren, da in diesem Fall eh nur zwei Threads gleichzeitig arbeiten.
	 * 						Bei größeren Projekten mit deutlich mehr Threads kann aber ein Lifelock auftreten, heißt es könnte passieren dass ein Thread 
	 * 						nie eine Resource bekommt. Hier hilft die faire Semaphore Implementierung.
	 */
	public BufferSemaphore(List<T> objects) {
		pool = objects;
		sem = new Semaphore(size(), false);
	}
	
	public BufferSemaphore(T... objects) {
		for(T obj: objects) pool.add(obj);
		sem = new Semaphore(size(), false);
	}
	
	
	/*
	 * sem.release():	Die Semaphore inkrementiert die availablePermits().
	 */
	@Override
	public synchronized void release(T resource) throws Exception {
		sem.release();
		pool.add(resource);
	}
	
	/*
	 * sem.acquire():	Semaphore schaut nach, ob es noch available Permits gibt (da die mit der pool.size() initialisiert wurde,
	 * 					könnte man stattdessen auch einfach schauen ob die list.size() != 0 ist.
	 * 					Außerdem sorgt die Semaphore allerdings auch automatisch dafür, dass, falls die pool.size() == 0 ist,
	 * 					der aktuelle Thread schlafen gelegt wird.
	 */
	@Override
	public synchronized T require() throws Exception {
		sem.acquire();
		return pool.remove(size() - 1);
	}
	@Override
	public int size() {
		return pool.size();
	}
}

public class Aufgabe1 {
	public static void main(String args[]) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
		Buffer<Integer> manBuffer = new Buffer<Integer>(list);
		BufferSemaphore<Integer> semBuffer = new BufferSemaphore<Integer>(list);
		
		/*
		 * Zwei Threads werden initialisiert, der eine soll doWork() mit dem manuellen ResourcePool ausführen,
		 * der andere mit dem Semaphore ResourcePool.
		 */
		Thread tMan = new Thread(() -> {
			while(true) doWork(manBuffer);
		});
		Thread tSem = new Thread(() -> {
			while(true) doWork(semBuffer);
		});
		
		tSem.start();
		tMan.start();
	}
	
	/*
	 * 1. Es wird versucht, direkt ein Element aus pool zu entnehmen.
	 * 2. Das Element wird für mindestens 2 volle Sekunden gehalten.
	 * 3. Das Element wird wieder via pool.release() released.
	 * 4. Es wird mindestens 1 Sekunde gewartet.
	 */
	public static <T> void doWork(ResourcePool<T> pool) {
		long start = System.nanoTime();
		System.out.println(Thread.currentThread() + ": REQUIRING RESOUCE...");
		T resource = null;
		try { resource = pool.require(); } catch (Exception e1) { e1.printStackTrace(); }
		System.out.println(Thread.currentThread() + ": RESOURCE RECEIVED\tTIME ELAPSED: " + (System.nanoTime() - start) + " ns");
		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

		try { pool.release(resource); } catch (Exception e1) { e1.printStackTrace(); }
		System.out.println(Thread.currentThread() + ": RESOURCE RELEASED");

		try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
	}
}
