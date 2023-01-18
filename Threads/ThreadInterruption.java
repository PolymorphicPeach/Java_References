import java.math.BigInteger;

public class ThreadInterruption implements Runnable {
// private:
	private BigInteger base;
	private BigInteger power;
	
	// This method could potentially take a very very long time.
	private BigInteger pow(BigInteger base, BigInteger power) {
		BigInteger result = BigInteger.ONE;
		
		// This is identifiable as the spot that could take a long time to complete
		for(BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE) ) {
			
			// ----- This is the code that responds to the interruption -----
			if(Thread.currentThread().isInterrupted()) {
				System.out.println("Thread interrupted");
				
				
				// Exit this loop early
				return BigInteger.ZERO;
			}
			
			
			result = result.multiply(base);
		}
		
		return result;
	}
	
// public:
	ThreadInterruption(BigInteger base, BigInteger power){
		this.base = base;
		this.power = power;
	}
	
	
	@Override
	public void run() {
		System.out.println(base + "^" + power + " = " + pow(base, power));
	}
	
}
