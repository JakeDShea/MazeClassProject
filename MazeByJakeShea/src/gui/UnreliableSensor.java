package gui;

import generation.CardinalDirection;
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
	private boolean isFunctioning, inStartProcess;
	// A thread object that uses the runnable UnreliableSensor.
	private Thread sensorThread;
	private int uptime, downtime;
	
	// The robot that this sensor is on
	private UnreliableRobot robot;
	// The direction it is facing as well
	private Direction sensorDirection;
	
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
	 * Tells the distance to an obstacle (a wallboard) that the sensor
	 * measures. The sensor is assumed to be mounted in a particular
	 * direction relative to the forward direction of the robot.
	 * Distance is measured in the number of cells towards that obstacle, 
	 * e.g. 0 if the current cell has a wallboard in this direction, 
	 * 1 if it is one step in this direction before directly facing a wallboard,
	 * Integer.MaxValue if one looks through the exit into eternity.
	 * 
	 * This method requires that the sensor has been given a reference
	 * to the current maze and a mountedDirection by calling 
	 * the corresponding set methods with a parameterized constructor.
	 * 
	 * @param currentPosition is the current location as (x,y) coordinates
	 * @param currentDirection specifies the direction of the robot
	 * @param powersupply is an array of length 1, whose content is modified 
	 * to account for the power consumption for sensing
	 * @return number of steps towards obstacle if obstacle is visible 
	 * in a straight line of sight, Integer.MAX_VALUE otherwise.
	 * @throws Exception with message 
	 * SensorFailure if the sensor is currently not operational
	 * PowerFailure if the power supply is insufficient for the operation
	 * @throws IllegalArgumentException if any parameter is null
	 * or if currentPosition is outside of legal range
	 * ({@code currentPosition[0] < 0 || currentPosition[0] >= width})
	 * ({@code currentPosition[1] < 0 || currentPosition[1] >= height}) 
	 * @throws IndexOutOfBoundsException if the powersupply is out of range
	 * ({@code powersupply < 0}) 
	 */
	@Override
	public int distanceToObstacle(int[] currentPosition, CardinalDirection currentDirection, float[] powersupply) throws Exception
	{
		if(!isFunctioning)
			throw new Exception("SensorFailure");
		
		return super.distanceToObstacle(currentPosition, currentDirection, powersupply);
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
			// Makes the thread wait the necessary uptime
			try {
				Thread.sleep(uptime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				isFunctioning = true;
			}
			
			// Breaks the unreliable sensor
			isFunctioning = false;
			
			// Makes thread wait necessary downtime
			try {
				Thread.sleep(downtime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				isFunctioning = true;
			}
			
			isFunctioning = true;
			// Synchronizes uptime for all threads after the first run is done to deal with possible offset
			uptime = 4000;
		}
	}
	
	/**
	 * States whether the sensor is currently functioning or not
	 * 
	 * @return true if the sensor is functioning currently, false otherwise.
	 */
	public boolean getFunctional()
	{
		return isFunctioning;
	}
	
	/**
	 * Provides access to the sensors direction
	 * 
	 * @return the direction of the sensor
	 */
	public Direction getDirection()
	{
		return sensorDirection;
	}
}