package gui;

import gui.Robot.Direction;

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
	public boolean isFunctioning, inStartProcess;
	// A thread object that uses the runnable UnreliableSensor.
	Thread sensorThread;
	int uptime, downtime;
	
	// The robot that this sensor is on
	UnreliableRobot robot;
	// The direction it is facing as well
	Direction sensorDirection;
	
	/**
	 * Constructor for an UnreliableSensor object
	 */
	public UnreliableSensor()
	{
		isFunctioning = true;
		inStartProcess = false;
		sensorThread = new Thread(this);
		uptime = 0;
		downtime = 0;
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
		// Sets a boolean value that says whether this sensor is currently in the failing process
		inStartProcess = true;
		
		uptime = meanTimeBetweenFailures;
		downtime = meanTimeToRepair;
		
		// Has the sensor thread wait for meanTimeBetweenFailures seconds.
		sensorThread.start();
		
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
		if(inStartProcess)
		{
			isFunctioning = true;
			inStartProcess = false;
		}
		// Otherwise this method will throw an exception.
		else
		{
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * A method that is used to set the robot field for this param for thread
	 * purposes.
	 * 
	 * @param robotParam the robot that this sensor object is used by
	 */
	public void setRobot(UnreliableRobot robotParam)
	{
		robot = robotParam;
	}
	
	/**
	 * A method that is used for setting up the directional field of the
	 * sensor.
	 * 
	 * @param dir the direction of the robot this sensor is on
	 */
	public void setDirection(Direction dir)
	{
		sensorDirection = dir;
	}
	
	/**
	 * This method will be used to kick off the UnreliableSensor's
	 * thread activity, allowing for the background thread to work
	 * during the programs run-time.
	 */
	@Override
	public void run() {
		// Will run while the maze is still playing
		while(inStartProcess)
		{
			try {
				Thread.sleep(uptime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				isFunctioning = true;
			}
			
			isFunctioning = false;
			
			try {
				Thread.sleep(downtime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				isFunctioning = true;
			}
			
			isFunctioning = true;
			// Synchronizes uptime for all threads after the first run is done
			uptime = 4000;
		}
	}
}