import java.math.BigInteger;

public class FactorialThread extends Thread {
// private:
	private long input;
	private BigInteger result;
	public boolean isFinished;
	
	
// public:
	@Override public void run() {
		result = factorial(input);
		isFinished = true;
	}
	
	public FactorialThread(long n){
		input = n;
		isFinished = false;
		result = BigInteger.ONE;
	}
	
	
	public boolean isFinished() {
		return isFinished;
	}
	
	
	public BigInteger getResult() {
		return result;
	}
	
	
	public BigInteger factorial(long n) {
		BigInteger tempHolder = BigInteger.ONE;
		
		for(long i = n; i > 0; --i) {
			tempHolder = tempHolder.multiply(new BigInteger(Long.toString(i)));
		}
		
		return tempHolder;
	}
}