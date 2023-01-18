public class DaemonThread extends Thread {
// private:
	
	
	
// public:
	public DaemonThread() {
		
		// Makes this a daemon thread, meaning that it will terminate
		// when the main thread terminates, whether this thread has finished
		// or not
		
		setDaemon(true);
	}
	
	
	@Override
	public void run() {
		System.out.println("Hello from daemon thread");
		
	}

}
