package ServerPackage;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool{
	private final int number_of_threads;
	private final PoolWorker[] threads;
	private final LinkedBlockingQueue queue;

	public ThreadPool(int n_threads){
		this.number_of_threads = n_threads;
		queue = new LinkedBlockingQueue();
		threads = new PoolWorker[number_of_threads];

		for(int i=0; i<number_of_threads; ++i){
			threads[i] = new PoolWorker();
			threads[i].start();
			threads[i].setThreadPool(this);
		}
	}

	public void execute(Runnable task){
		synchronized(queue){
			queue.add(task);
			queue.notify();
		}
	}

	public Boolean usernameInPool(String username){
		for(PoolWorker i : threads){
			if(!i.taskRunning())
				continue;
			if(i.getUsername().equals(username))
				return true;
		}
		return false;
	}

	public void broadcastList(){
		ArrayList<User> users = new ArrayList<User>();

		for(PoolWorker i : threads){
			if(!i.taskRunning())
				continue;
			users.add(i.getUser());
		}

		for(PoolWorker i : threads){
			if(!i.taskRunning())
				continue;
			i.getUser().getOutputStream().println(users);
		}
	}

	public void broadcastToAll(String message){
		for(PoolWorker i : threads){
			if(i == null)
				continue;
			i.getUser().getOutputStream().println(message);
		}
	}

	//************************************************************

	private class PoolWorker extends Thread{
		private UserHandler task = null;
		private ThreadPool super_pool = null;

		public void run(){
			while(true){
				synchronized(queue){
					while(queue.isEmpty()){
						try{
							queue.wait();
						}catch(InterruptedException exc){
							// Notify?
						}
					}
					task = (UserHandler)queue.poll();
					super_pool.broadcastList();
				}

				try{
					task.run();
				}catch(RuntimeException exc){
					// Notify?
				}
			}
		}

		public void setThreadPool(ThreadPool pool){
			super_pool = pool;
		}

		public Boolean taskRunning(){
			return task != null;
		}

		public String getUsername(){
			if(task == null)
				return new String();
			return task.getUsername();
		}

		public User getUser(){
			if(task == null)
				return null;
			return task.getUser();
		}
	}
}