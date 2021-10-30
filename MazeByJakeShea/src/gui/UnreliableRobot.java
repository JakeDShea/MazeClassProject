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
	DistanceSensor leftSensor, forwardSensor, backwardSensor, rightSensor;
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
			getSensor(direction).startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
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
}
