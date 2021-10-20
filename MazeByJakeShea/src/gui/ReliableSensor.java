package gui;

import generation.CardinalDirection;
import generation.Maze;
import gui.Robot.Direction;

/**
 * Class: ReliableSensor
 * Responsibilities: Interacting with maze to find obstacles and layout
 * Collaborators: gui.DistanceSensor.java
 * 				  generation.CardinalDirection.java
 * 
 * @Author Jake Shea
 */


public class ReliableSensor implements DistanceSensor {
	//Sets up instance variables for the ReliableSensor, like the maze and direction
	Direction sensorDirection;
	Maze maze;
	
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
	public int distanceToObstacle(int[] currentPosition, CardinalDirection currentDirection, float[] powersupply)
			throws Exception {
		//Sets up an integer value to count how far away the next wallboard is
		int count = 0;
		int[] newPos = currentPosition;
		
		while(maze.getFloorplan().hasNoWall(newPos[0], newPos[1], currentDirection))
		{
			//From current position, checks if there is a wallboard in the current direction.
			//If not, add one to counter variable.
			count++;
			
			//Check if next position is legal (i.e. in the maze)
			//If not, then logically we had to have just left the maze through its exit. Returns Integer.MAX_VALUE.
			//Else, we loop through the code again until we hit a wallboard or leave the maze.
			if(maze.getFloorplan().hasNoWall(newPos[0], newPos[1], currentDirection) && !maze.isValidPosition(currentDirection.getDirection()[0] + newPos[0], currentDirection.getDirection()[1] + newPos[1]))
			{
				count = Integer.MAX_VALUE;
				break;
			}
			
			//Moves to next position
			newPos[0] += currentDirection.getDirection()[0];
			newPos[1] += currentDirection.getDirection()[1];
		}
		
		//Decrements energy
		powersupply[0] -= getEnergyConsumptionForSensing();
		
		//Return counter variable if wallboard has been found.
		return count;
	}

	/**
	 * Provides the maze information that is necessary to make
	 * a DistanceSensor able to calculate distances.
	 * @param maze the maze for this game
	 * @throws IllegalArgumentException if parameter is null
	 * or if it does not contain a floor plan
	 */
	@Override
	public void setMaze(Maze mazeParam) {
		//Sets ReliableSensor's maze instance variable to the Maze object parameter
		maze = mazeParam;
	}

	/**
	 * Provides the angle, the relative direction at which this 
	 * sensor is mounted on the robot.
	 * If the direction is left, then the sensor is pointing
	 * towards the left hand side of the robot at a 90 degree
	 * angle from the forward direction. 
	 * @param mountedDirection is the sensor's relative direction
	 * @throws IllegalArgumentException if parameter is null
	 */
	@Override
	public void setSensorDirection(Direction mountedDirection) {
		//Sets the direction of the ReliableSensor in its instance variable
		sensorDirection = mountedDirection;
	}

	/**
	 * Returns the amount of energy this sensor uses for 
	 * calculating the distance to an obstacle exactly once.
	 * This amount is a fixed constant for a sensor.
	 * @return the amount of energy used for using the sensor once
	 */
	@Override
	public float getEnergyConsumptionForSensing() {
		//Return set number for much energy is used by sensing
		return 1;
	}

	////////////////////////////////////////////////////////////////////////////////
	/////////////////////////DO NOT HAVE TO DO THESE YET////////////////////////////
	////////////////////////////////////////////////////////////////////////////////
	
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
	 * @param meanTimeBetweenFailures is the mean time in seconds, must be greater than zero
	 * @param meanTimeToRepair is the mean time in seconds, must be greater than zero
	 * @throws UnsupportedOperationException if method not supported
	 */
	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

}
