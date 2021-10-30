package gui;

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
}
