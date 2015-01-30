package dinning_philosophers;

import java.util.concurrent.Semaphore;

public class Main {
	
	public static void main(String... args){
		//waiter can handle only 4 clients
		int simulation_time = 10000;
		Semaphore waiter = new Semaphore(4, true);
		Philosopher[] philosophers = new Philosopher[5];
		//forks
		Semaphore[] forks = new Semaphore[5];
		for (int i=0; i<=4; i++){
			Semaphore fork = new Semaphore(1, true);
			forks[i] = fork;
		}
		for (int i=0; i<=4; i++){
			Philosopher ph = new Philosopher(waiter, forks);
			philosophers[i] = ph;
			ph.start();
		}
		
		try {
			Thread.sleep(simulation_time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Philosopher philosopher : philosophers) {
	        philosopher.execute = false;
	    }
		
		try {
			Thread.sleep(1000);
			//Threads are finishing
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Results: ");
		for(int i = 0; i <=4; i++){
			System.out.println("Philosopher " + i + " " + Philosopher.counter[i]);
		}
		
	}
}
