package gui;

import gui.Robot.Direction;

/**
 * Class: UnreliableRobot
 * Responsibilities: Interacting with obstacles in Maze
 * 					 To have tools that may not function at all times
 * Collaborators: gui.ReliableSensor.java
 * 				  gui.UnreliableSensor.java
 * 				  gui.DistanceSensor.java
 * 
 * @Author Jake Shea
 */

public class UnreliableRobot extends ReliableRobot
{
	// Four DistanceSensors that can be either reliable or unreliable
	private DistanceSensor leftSensor, forwardSensor, backwardSensor, rightSensor;
	
	// Four int fields to set up sensors again after games are made
	public int leftSense, forwardSense, backwardSense, rightSense; 
	// All other necessary fields are to be inherited
	
	/**
	 * A constructor that can be used by the controller to easily
	 * set up an UnreliableRobot with the command line arguments
	 * 
	 * @param forward: The value to determine the reliability of the forwards sensor
	 * @param left: The value to determine the reliability of the left-most sensor
	 * @param right: The value to determine the reliability of the right-most sensor
	 * @param back: The value to determine the reliability of the backwards sensor
	 */
	public UnreliableRobot(int forward, int left, int right, int back)
	{
		// Sets up the int fields
		leftSense = left;
		forwardSense = forward;
		rightSense = right;
		backwardSense = back;
		
		//Check each integer parameter.
		//If it is a 1, make it a reliable sensor, if a 0 then make it an unreliable one
		
		//Creates the sensor for the forward direction
		if(forward == 1)
			forwardSensor = new ReliableSensor();
		else
			forwardSensor = new UnreliableSensor();
		
		//Creates the sensor for the left direction
		if(left == 1)
			leftSensor = new ReliableSensor();
		else
			leftSensor = new UnreliableSensor();
		
		//Creates the sensor for the right direction
		if(right == 1)
			rightSensor = new ReliableSensor();
		else
			rightSensor = new UnreliableSensor();
		
		//Creates the sensor for the backward direction
		if(back == 1)
			backwardSensor = new ReliableSensor();
		else
			backwardSensor = new UnreliableSensor();
	}
	
	/**
	 * Method starts a concurrent, independent failure and repair
	 * process that makes the sensor fail and repair itself.
	 * This creates alternating time periods of up time and down time.
	 * Up time: The duration of a time period when the sensor is in 
	 * operational is characterized by a distribution
	 * whose mean value is given by parameter meanTimeBetweenFailures.
	 * Down time: The duration of a time period when the sensor is in repair
	 * and not operational is characterized by a distribution
	 * whose mean value is given by parameter meanTimeToRepair.
	 * 
	 * This an optional operation. If not implemented, the method
	 * throws an UnsupportedOperationException.
	 * 
	 * @param direction the direction the sensor is mounted on the robot
	 * @param meanTimeBetweenFailures is the mean time in seconds, must be greater than zero
	 * @param meanTimeToRepair is the mean time in seconds, must be greater than zero
	 * @throws UnsupportedOperationException if method not supported
	 */
	@Override
	public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		// First checks if the sensor is unreliable, otherwise it cannot fail
		if (getSensor(direction) instanceof UnreliableSensor)
		{
			// Starts the failing process
			getSensor(direction).startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
			
			//Makes all other threads in the system wait 1.3 seconds to prevent concurrent and synchronized failures
			pauseOtherThreads(direction);
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
	 * @param direction the direction the sensor is mounted on the robot
	 * @throws UnsupportedOperationException if method not supported
	 */
	@Override
	public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {
		// First checks if the sensor is unreliable, otherwise it cannot fail
		if (getSensor(direction) instanceof UnreliableSensor)
			getSensor(direction).stopFailureAndRepairProcess();
	}
	
	/**
	 * A method to return the sensor of the robot
	 * @param dir is the direction of which sensor we want
	 * @return the sensor of the specified direction
	 */
	@Override
	public DistanceSensor getSensor(Direction dir)
	{
		// Checks if we want the left-facing sensor
		if(dir == Direction.LEFT)
			return leftSensor;
		// Checks if we want the forward-facing sensor
		else if (dir == Direction.FORWARD)
			return forwardSensor;
		// Checks if we want the backward-facing sensor
		else if (dir == Direction.BACKWARD)
			return backwardSensor;
		// Seems we want the right-facing sensor otherwise
		else
			return rightSensor;
	}
	
	/**
	 * This method will be called by the controller to start
	 * the thread this object has.
	 */
	public void start()
	{
    	// Goes through all the robot's distance sensors
    	// Checks left sensor and starts as needed
    	if(getSensor(Direction.LEFT) instanceof UnreliableSensor)
    	{
    		((UnreliableSensor) getSensor(Direction.LEFT)).setRobot(this);
    		((UnreliableSensor) getSensor(Direction.LEFT)).setDirection(Direction.LEFT);
    		startFailureAndRepairProcess(Direction.LEFT, 4000, 2000);
    	}
    	
    	// Checks forward sensor and starts as needed
    	if(getSensor(Direction.FORWARD) instanceof UnreliableSensor)
    	{
    		((UnreliableSensor) getSensor(Direction.FORWARD)).setRobot(this);
    		((UnreliableSensor) getSensor(Direction.FORWARD)).setDirection(Direction.FORWARD);
    		startFailureAndRepairProcess(Direction.FORWARD, 4000, 2000);
    	}
    	
    	// Checks backward sensor and starts as needed
    	if(getSensor(Direction.BACKWARD) instanceof UnreliableSensor)
    	{
    		((UnreliableSensor) getSensor(Direction.BACKWARD)).setRobot(this);
    		((UnreliableSensor) getSensor(Direction.BACKWARD)).setDirection(Direction.BACKWARD);
    		startFailureAndRepairProcess(Direction.BACKWARD, 4000, 2000);
    	}
    	
    	// Checks right sensor and starts as needed
    	if(getSensor(Direction.RIGHT) instanceof UnreliableSensor)
    	{
    		((UnreliableSensor) getSensor(Direction.RIGHT)).setRobot(this);
    		((UnreliableSensor) getSensor(Direction.RIGHT)).setDirection(Direction.RIGHT);
    		startFailureAndRepairProcess(Direction.RIGHT, 4000, 2000);
    	}
	}
	
	/**
	 * A helper method that is used to check if there are threads that
	 * need to be paused so that we do not have synchronous failures
	 * by multiple unreliable sensors.
	 * 
	 * @param direction is the direction we want to ignore, we check all other directions
	 */
	private void pauseOtherThreads(Direction direction)
	{
		// Loops through all possible directions denoted by gui.Robot.java
		for(Direction dir: Robot.Direction.values())
		{
			// Checks for all the directions besides the one we came from
			if(dir != direction)
			{
				// Checks first if the sensor is an UnreliableSensor
				if(getSensor(dir) instanceof UnreliableSensor)
				{
					// Checks if the UnreliableSensor is not yet starting the failure process
					if(!((UnreliableSensor)getSensor(dir)).inStartProcess)
					{
						// Makes the sensorThread sleep for a bit of time before continuing
						try {
							((UnreliableSensor)getSensor(dir)).sensorThread.sleep(1300);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		
	}
}
