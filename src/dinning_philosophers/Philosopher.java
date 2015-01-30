package dinning_philosophers;

import java.util.concurrent.Semaphore;

public class Philosopher extends Thread {
	public boolean execute = true;
	public static int[] counter = new int [] {0,0,0,0,0};
	private static int philosophers = 0;	
	private int id;
	private Semaphore waiter;
	private Semaphore left_fork;
	private Semaphore right_fork;
	
	
	public Philosopher(Semaphore waiter, Semaphore[] forks){
		this.id = Philosopher.philosophers++;
		this.waiter = waiter;
		this.left_fork = forks[id];
		//return zero if philosopher has id 5
		this.right_fork = forks[(id+1) % 5];
	}
	
	public void activity(String activity_name){
		System.out.println("Philosopher " + id + " start " + activity_name);
		
		try {
			Thread.sleep((int)Math.random() * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Philosopher " + id + " stop " + activity_name);	
		
	}
	
	public void iAmHungry(){
		try {
			this.waiter.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void iAmFull(){
		this.waiter.release();
	}
	
	public void tryToGetForks(){
		try {
			this.left_fork.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.right_fork.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void releaseForks(){
		this.left_fork.release();
		this.right_fork.release();
	}
	
	public void run(){
		/*
		 * wait for waiter
		 * get left fork
		 * get second, right fork
		 * eat
		 * leave forks
		 */
		while(this.execute){
			this.activity("thinking");
			this.iAmHungry();
			this.tryToGetForks();
			this.activity("eating");
			Philosopher.counter[this.id]++;
			this.releaseForks();
			this.iAmFull();
		}
		
	}
}
