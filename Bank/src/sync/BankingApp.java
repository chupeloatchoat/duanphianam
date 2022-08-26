package sync;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BankingApp {

	private static Account account = new Account("101", "A");

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		double money = 0;

		Callable<Double> withdrawTask = () -> {
			return account.withdraw(2.0);
		};

		ExecutorService service = Executors.newCachedThreadPool();

		List<Future<Double>> fs = new Vector<Future<Double>>();

//		List<Future<Double>> fs = new ArrayList<Future<Double>>();

		for (int i = 0; i < 49; i++) {
			fs.add(service.submit(withdrawTask));
		}
		
		service.shutdown();
		
		while(!service.isTerminated()) {
			
		}
		
		for (Future<Double> future : fs) {
			money += future.get();
		}
		
		System.out.println("Money = " + money);
		System.out.println("Balance = " + account.getBalance());
	}
}