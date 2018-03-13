package resource_pool.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.TimeoutException;

public class ResourcePool<T extends Object> {
	T[] objectPool;
	private int currentSize;
	private long startTime;
	
	/**
	 * proxy is being generated and returned from require, it will throw an exception if it is used too long (longer than 6 seconds)
	 * TODO proxy must throw an exception if it was released by a thread, but is still being accessed by it.
	 */
	
	public ResourcePool(T[] objects) {
		startTime = System.nanoTime();
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
		
		return createProxy(objectPool[currentSize]);
	}
	
	public synchronized void release(T object) {
		currentSize++;
		notify();
	}
	
	private T createProxy(T t) {
		Class<?> clazz = t.getClass();
		T proxy = (T) java.lang.reflect.Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new ResourceHandler(t));
		return proxy;
	}
	
	private void checkAccess() throws TimeoutException {
		long thisTime = System.nanoTime();
		if (thisTime - startTime >= 6000000) throw new TimeoutException();
	}
	
	class ResourceHandler implements InvocationHandler {
		private T t;
		
		public ResourceHandler(T t) {
			this.t = t;
		}
		
		@Override
		public Object invoke(Object o, Method m, Object[] args) throws Throwable {
			checkAccess();
			return m.invoke(t, args);
		}
		
	}
}
 