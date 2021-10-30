package gui;

/**
 * Class: UnreliableSensor
 * Responsibilities: Interacting with obstacles in Maze
 * 					 To break and be repaired
 * Collaborators: gui.ReliableSensor.java
 * 				  Runnable.java
 * 				  Thread.java
 * 
 * @Author Jake Shea
 */

public class UnreliableSensor extends ReliableSensor implements Runnable
{
	// A boolean value to say whether the sensor is currently working or not
	boolean isFunctioning;
	// A thread object that uses the runnable UnreliableSensor.
	Thread sensorThread;
	
	/**
	 * Constructor for an UnreliableSensor object
	 */
	public UnreliableSensor()
	{
		isFunctioning = true;
		sensorThread = new Thread(this);
	}
	
	/**
	 * A method that will be used to first break, then repair said sensor.
	 * 
	 * @param meanTimeBetweenFailures is the mean time in seconds, must be greater than zero
	 * @param meanTimeToRepair is the mean time in seconds, must be greater than zero
	 * @throws UnsupportedOperationException if method not supported
	 */
	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException{
		// Has the sensor thread wait for meanTimeBetweenFailures seconds.
		try
		{
			Thread.sleep(meanTimeToRepair * 1000);
		}
		catch (InterruptedException e) {
			System.out.println("Thread is asleep");
		}
		
		// Sets the sensor to failing state.
		isFunctioning = false;
		
		// Has the sensor thread wait for meanTimeToRepair more seconds.
		try
		{
			Thread.sleep(meanTimeToRepair * 1000);
		}
		catch (InterruptedException e) {
			System.out.println("Thread is asleep");
		}
	}

	/**
	 * This method stops a failure and repair process and
	 * leaves the sensor in an operational state.
	 * 
	 * It is complementary to starting a 
	 * failure and repair process. 
	 * 
	 * Intended use: If called after starting a process, this method
	 * will stop the process as soon as the sensor is operational.
	 * 
	 * If called with no running failure and repair process, 
	 * the method will return an UnsupportedOperationException.
	 * 
	 * This an optional operation. If not implemented, the method
	 * throws an UnsupportedOperationException.
	 * 
	 * @throws UnsupportedOperationException if method not supported
	 */
	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		// Checks if the sensor is currently failing.
		// If so, repair the sensor.
		if(!isFunctioning)
			isFunctioning = !isFunctioning;
		// Otherwise this method will throw an exception.
		else
		{
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * This method will be used to kick off the UnreliableSensor's
	 * thread activity, allowing for the background thread to work
	 * during the programs run-time.
	 */
	@Override
	public void run() {
		// Will run while the maze is still playing
		while(maze != null)
		{
			//Keeps thread active until maze ends
		}
		
	}
	
	/**
	 * This method will be called by the constructor to start
	 * the thread this object has.
	 */
	public void start()
	{
		sensorThread.start();
	}
}
