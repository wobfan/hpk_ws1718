package uebung3_2;

import java.util.*;
import java.util.concurrent.*;

interface ResourcePool<T> {
	void release(T resource) throws Exception;
	T require() throws Exception;
	int size();
}

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

class BufferSemaphore<T> implements ResourcePool<T> {
	List<T> pool = new ArrayList<T>();
	Semaphore sem;
	
	public BufferSemaphore(List<T> objects) {
		pool = objects;
		sem = new Semaphore(size(), true);
	}
	
	public BufferSemaphore(T... objects) {
		for(T obj: objects) pool.add(obj);
		sem = new Semaphore(size(), true);
	}
	
	@Override
	public synchronized void release(T resource) throws Exception {
		sem.release();
		pool.add(resource);
	}
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
		
		Runner<Integer> manRun = new Runner(manBuffer);
		Runner<Integer> semRun = new Runner(semBuffer);
		
		Thread t1 = new Thread(manRun);
		Thread t2 = new Thread(semRun);
		
		t1.start();
		t2.start();
	}
}

class Runner<T> implements Runnable {
	ResourcePool<T> pool;
	
	public Runner(ResourcePool<T> pool) {
		this.pool = pool;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 2; i++) {
			long start = System.nanoTime();
			System.out.println(Thread.currentThread() + ": REQUIRING RESOUCE...");
			T resource = null;
			try { resource = pool.require(); } catch (Exception e1) { e1.printStackTrace(); }
			System.out.println(Thread.currentThread() + ": RESOURCE RECEIVED\tTIME ELAPSED: " + (System.nanoTime() - start) + " ns");
			try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }

			try { pool.release(resource); } catch (Exception e1) { e1.printStackTrace(); }
			System.out.println(Thread.currentThread() + ": RESOURCE RELEASED");

			try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
		}
	}	
}
