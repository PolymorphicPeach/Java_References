import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		// ==================== Thread Interruption =========================================================================
		
		// The calculation could take too long.
		Thread thread1 = new Thread(new ThreadInterruption(new BigInteger("200000"), new BigInteger("10000000000")));
		
		thread1.start();
		
		// Simply calling .interrupt() here would not be enough. 
		// The ThreadInterruption class must also have code for responding to the interruption.
		thread1.interrupt();
		
		// ------------------------------------------------------------------------------------------------------------------
		
		
		// ==================== Daemon Threads =========================================================================
		// Since "setDaemon(true)" was used, this thread will terminate when the main thread does.
		Thread thread2 = new DaemonThread();
		thread2.run();
		// -------------------------------------------------------------------------------------------------------------
		
		
		
		// ==================== Thread Joining =========================================================================
		
		List <Long> nums = Arrays.asList(0L, 3435L, 35435L, 2324L, 4656L, 23L, 5556L);
		List<FactorialThread> threads = new ArrayList<>();
		
		for(long inputNum : nums) {
			threads.add(new FactorialThread(inputNum));
		}
		
		for(Thread thread : threads) {
			thread.setDaemon(true);
			thread.start();
		}
		
		for(Thread thread : threads) {
			
			// Argument is the amount of milliseconds willing to wait
			thread.join(2000);
			
		}
		
		for(int i = 0; i < nums.size(); ++i) {
			
			FactorialThread factorialThread = threads.get(i);
			
			if(factorialThread.isFinished()) {
				System.out.println("Factorial of " + nums.get(i) + " is " + factorialThread.getResult());
			}
			else {
				System.out.println("Still calculating: " + nums.get(i) +"!");
			}
			
			
		}
		// -------------------------------------------------------------------------------------------------------------
		
	}

}
