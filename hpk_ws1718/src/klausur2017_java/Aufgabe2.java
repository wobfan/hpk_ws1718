package klausur2017_java;

import java.util.*;

interface Buffer<V> {
	void put(V item) throws InterruptedException;
	V take() throws InterruptedException;
}

class RingBuffer<V> implements Buffer<V> {
	List<V> buffer;
	Object lockTake;
	Object lockPut;

	public RingBuffer(V... items) {
		buffer = new ArrayList<V>(Arrays.asList(items));
		lockTake = new Object();
		lockPut = new Object();
	}

	@Override
	public void put(V item) throws InterruptedException {
		synchronized(lockPut) {
			buffer.add(item);
			lockPut.notify();
		}
	}

	@Override
	public V take() throws InterruptedException {
		V took;
		synchronized(lockTake) {
			while (buffer.size() == 0) lockTake.wait(200);
			took = buffer.remove(buffer.size() - 1);
			lockTake.notify();
		}
		return took;
	}

}

public class Aufgabe2<T> {

	public static void main(String[] args) {
		String strings[] = {"there", "are", "some", "very", "interesting", "strings", "in", "this", "array"};
		RingBuffer<String> buffer = new RingBuffer<String>(strings);

		Runnable producer = new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while(true) {
					String toEnter = "String" + i;
					i++;
					try { buffer.put(toEnter); } catch (InterruptedException e) { e.printStackTrace(); }
					System.out.println(Thread.currentThread() + " | PUT: '" + toEnter + "'");
					try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
				}
			}
		};

		Runnable consumer = new Runnable() {

			@Override
			public void run() {
				while(true) {
					String toReceive = "ERROR";
					try { toReceive = buffer.take(); } catch (InterruptedException e) { e.printStackTrace(); }
					System.out.println(Thread.currentThread() + " | GOT: '" + toReceive + "'");
					try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }					
				}
			}
		};

		(new Thread(producer)).start();
		(new Thread(consumer)).start();
	}
}
