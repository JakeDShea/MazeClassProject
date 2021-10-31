package gui;

import java.util.Arrays;

import generation.CardinalDirection;

/**
 * Class: ReliableRobot
 * Responsibilities: Interacting with the GUI
 * Collaborators: gui.Robot.java
 * 				  generation.CardinalDirection.java
 * 				  gui.ReliableSensor.java
 * 
 * @Author Jake Shea
 */

public class ReliableRobot implements Robot {
	//Sets up instance variables for the ReliableRobot, namely the 4 distance sensors and others
	ReliableSensor leftSensor = new ReliableSensor(), rightSensor = new ReliableSensor(), forwardSensor = new ReliableSensor(), backwardSensor = new ReliableSensor();
	Controller robotController;
	float[] energy;
	int odometer = 0;
	boolean crashed = false;
	
	/**
	 * Creates the ReliableRobot object that will interact with the GUI along with
	 * the 4 mounted sensors for the robot to have
	 * 
	 * The parameters are needed so that a robot can be created without all four sensors.
	 * If a parameter is false, it will not create the sensor, if true then it will.
	 */
	public ReliableRobot() {
		//Creates 4 sensors and sets up robot instance variables
		addDistanceSensor(leftSensor, Direction.LEFT);
		addDistanceSensor(rightSensor, Direction.RIGHT);
		addDistanceSensor(forwardSensor, Direction.FORWARD);
		addDistanceSensor(backwardSensor, Direction.BACKWARD);
		
		energy = new float[1];
		setBatteryLevel(3500);
	}
	
	/**
	 * Provides the robot with a reference to the controller to cooperate with.
	 * The robot memorizes the controller such that this method is most likely called only once
	 * and for initialization purposes. The controller serves as the main source of information
	 * for the robot about the current position, the presence of walls, the reaching of an exit.
	 * The controller is assumed to be in the playing state.
	 * @param controller is the communication partner for robot
	 * @throws IllegalArgumentException if controller is null, 
	 * or if controller is not in playing state, 
	 * or if controller does not have a maze
	 */
	@Override
	public void setController(Controller controller) {
		robotController = controller;
		
		//Private method to give the sensors a maze from this new controller.
		setMazesForSensors();
	}

	/**
	 * Adds a distance sensor to the robot such that it measures in the given direction.
	 * This method is used when a robot is initially configured to get ready for operation.
	 * The point of view is that one mounts a sensor on the robot such that the robot
	 * can measure distances to obstacles or walls in that particular direction.
	 * For example, if one mounts a sensor in the forward direction, the robot can tell
	 * with the distance to a wall for its current forward direction, more technically,
	 * a method call distanceToObstacle(FORWARD) will return a corresponding distance.
	 * So a robot with a left and forward sensor will internally have 2 DistanceSensor
	 * objects at its disposal to calculate distances, one for the forward, one for the
	 * left direction.
	 * A robot can have at most four sensors in total, and at most one for any direction.
	 * If a robot already has a sensor for the given mounted direction, adding another
	 * sensor will replace/overwrite the current one for that direction with the new one.
	 * @param sensor is the distance sensor to be added
	 * @param mountedDirection is the direction that it points to relative to the robot's forward direction
	 */
	@Override
	public void addDistanceSensor(DistanceSensor sensor, Direction mountedDirection) {
		//Sets up a DistanceSensor instance variable for Robot
		sensor.setSensorDirection(mountedDirection);
	}

	/**
	 * Provides the current position as (x,y) coordinates for 
	 * the maze as an array of length 2 with [x,y].
	 * @return array of length 2, x = array[0], y = array[1]
	 * and ({@code 0 <= x < width, 0 <= y < height}) of the maze
	 * @throws Exception if position is outside of the maze
	 */
	@Override
	public int[] getCurrentPosition() throws Exception {
		//Returns a tuple of the Robot's current position
		return robotController.getCurrentPosition();
	}

	/**
	 * Provides the robot's current direction.
	 * @return cardinal direction is the robot's current direction in absolute terms
	 */	
	@Override
	public CardinalDirection getCurrentDirection() {
		//Returns an enumerated CardinalDirection type which will likely be an instance variable for the Robot.
		return robotController.getCurrentDirection();
	}

	/**
	 * Returns the current battery level.
	 * The robot has a given battery level (energy level) 
	 * that it draws energy from during operations. 
	 * The particular energy consumption is device dependent such that a call 
	 * for sensor distance2Obstacle may use less energy than a move forward operation.
	 * If battery {@code level <= 0} then robot stops to function and hasStopped() is true.
	 * @return current battery level, {@code level > 0} if operational. 
	 */
	@Override
	public float getBatteryLevel() {
		//Returns the battery level of the Robot, which is an instance variable.
		return energy[0];
	}

	/**
	 * Sets the current battery level.
	 * The robot has a given battery level (energy level) 
	 * that it draws energy from during operations. 
	 * The particular energy consumption is device dependent such that a call 
	 * for distance2Obstacle may use less energy than a move forward operation.
	 * If battery {@code level <= 0} then robot stops to function and hasStopped() is true.
	 * @param level is the current battery level
	 * @throws IllegalArgumentException if level is negative 
	 */
	@Override
	public void setBatteryLevel(float level) {
		//Sets Robot's battery level using the parameter value as the new level.
		if(level >= 0)
			energy[0] = level;
		else {
			//Makes robot stop and ends run
			energy[0] = 0;
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Gives the energy consumption for a full 360 degree rotation.
	 * Scaling by other degrees approximates the corresponding consumption. 
	 * @return energy for a full rotation
	 */
	@Override
	public float getEnergyForFullRotation() {
		//Returns a set number for how much energy a full rotation uses.
		return 12;
	}

	/**
	 * Gives the energy consumption for moving forward for a distance of 1 step.
	 * For simplicity, we assume that this equals the energy necessary 
	 * to move 1 step and that for moving a distance of n steps 
	 * takes n times the energy for a single step.
	 * @return energy for a single step forward
	 */
	@Override
	public float getEnergyForStepForward() {
		//Returns a set number of energy for a single step forward of the Robot.
		return 6;
	}

	/** 
	 * Gets the distance traveled by the robot.
	 * The robot has an odometer that calculates the distance the robot has moved.
	 * Whenever the robot moves forward, the distance 
	 * that it moves is added to the odometer counter.
	 * The odometer reading gives the path length if its setting is 0 at the start of the game.
	 * The counter can be reset to 0 with resetOdomoter().
	 * @return the distance traveled measured in single-cell steps forward
	 */
	@Override
	public int getOdometerReading() {
		//Returns how many steps the Robot has moved so far.
		return odometer;
	}

	/** 
     * Resets the odometer counter to zero.
     * The robot has an odometer that calculates the distance the robot has moved.
     * Whenever the robot moves forward, the distance 
     * that it moves is added to the odometer counter.
     * The odometer reading gives the path length if its setting is 0 at the start of the game.
     */
	@Override
	public void resetOdometer() {
		//Sets the odometer back to 0.
		odometer = 0;
	}

	/**
	 * Turn robot on the spot for amount of degrees. 
	 * If robot runs out of energy, it stops, 
	 * which can be checked by hasStopped() == true and by checking the battery level. 
	 * @param turn is the direction to turn and relative to current forward direction. 
	 */
	@Override
	public void rotate(Turn turn) {
		int sizeOfTurn = 0;
		
		//Changes Robot's direction
		switch(turn)
		{
			case LEFT:
			{
				robotController.keyDown(Constants.UserInput.LEFT, 0);
				
				//Finds denominator of fraction of energy used
				sizeOfTurn = 360 / 90;
				break;
			}
			case AROUND:
			{
				//Rotates fully around
				robotController.keyDown(Constants.UserInput.LEFT, 0);
				robotController.keyDown(Constants.UserInput.LEFT, 0);
				
				//Finds denominator of fraction of energy used
				sizeOfTurn = 360 / 180;
				break;
			}
			case RIGHT:
			{
				robotController.keyDown(Constants.UserInput.RIGHT, 0);
				
				//Finds denominator of fraction of energy used
				sizeOfTurn = 360 / 90;
				break;
			}
		}
		
		//Uses up an amount of energy based on the size of the turn
		setBatteryLevel(getBatteryLevel() - getEnergyForFullRotation()/sizeOfTurn);
	}

	/**
	 * Moves robot forward a given number of steps. A step matches a single cell.
	 * If the robot runs out of energy somewhere on its way, it stops, 
	 * which can be checked by hasStopped() == true and by checking the battery level. 
	 * If the robot hits an obstacle like a wall, it remains at the position in front 
	 * of the obstacle and also hasStopped() == true as this is not supposed to happen.
	 * This is also helpful to recognize if the robot implementation and the actual maze
	 * do not share a consistent view on where walls are and where not.
	 * @param distance is the number of cells to move in the robot's current forward direction 
	 * @throws IllegalArgumentException if distance not positive
	 */
	@Override
	public void move(int distance) {
		int moves = distance;
		int[] originalPos = robotController.getCurrentPosition();
		
		//Loops these lines as many times as Robot is set to move to know if he runs out of energy in the middle of the movement
		while(moves > 0 && getBatteryLevel() > 0)
		{
			//Move Robot one step
			robotController.keyDown(Constants.UserInput.UP, 0);
		
			//Checks if robot hit a wall and controller could not move robot
			if(Arrays.equals(originalPos, robotController.getCurrentPosition()))
			{
				crashed = true;
				break;
			}
			else
			{
				//Decrease energy by using the getEnergyForStepForward() method
				setBatteryLevel(getBatteryLevel() - getEnergyForStepForward());
				
				odometer++;
				moves--;
			}
		}
	}

	/**
	 * Makes robot move in a forward direction even if there is a wall
	 * in front of it. In this sense, the robot jumps over the wall
	 * if necessary. The distance is always 1 step and the direction
	 * is always forward.
	 * If the robot runs out of energy somewhere on its way, it stops, 
	 * which can be checked by hasStopped() == true and by checking the battery level.
	 * If the robot tries to jump over an exterior wall and
	 * would land outside of the maze that way,  
	 * it remains at its current location and direction,
	 * hasStopped() == true as this is not supposed to happen.
	 */
	@Override
	public void jump() {
		//Make sure Robot is not on the edge of the maze facing the border by comparing original position to new one
		int[] originalPos = robotController.getCurrentPosition();
		
		//Make Robot move forward, regardless if there is a wall or not
		robotController.keyDown(Constants.UserInput.JUMP, 0);
		
		if(Arrays.equals(originalPos, robotController.getCurrentPosition()))
		{
			crashed = true;
		}
		else
		{
			//Decrease the energy of the Robot for the cost of a jump
			setBatteryLevel(getBatteryLevel() - 40);
			odometer++;
		}
	}

	/**
	 * Tells if the current position is right at the exit but still inside the maze. 
	 * The exit can be in any direction. It is not guaranteed that 
	 * the robot is facing the exit in a forward direction.
	 * @return true if robot is at the exit, false otherwise
	 */
	@Override
	public boolean isAtExit() {
		//Sets up variables for ease of understanding code
		int posX = robotController.getCurrentPosition()[0];
		int posY = robotController.getCurrentPosition()[1];
		
		//Checks if cell Robot is in contains the exit to the maze
		if(robotController.getMazeConfiguration().getDistanceToExit(posX, posY) == 1)
			return true;
		
		//Returns true if it is, false if not.
		return false;
	}

	/**
	 * Tells if current position is inside a room. 
	 * @return true if robot is inside a room, false otherwise
	 */	
	@Override
	public boolean isInsideRoom() {
		//Sets up variables for ease of understanding code
		int posX = robotController.getCurrentPosition()[0];
		int posY = robotController.getCurrentPosition()[1];
		
		//Checks if cell Robot is in is in a room.
		//Returns true if it is, false if not.
		return robotController.getMazeConfiguration().isInRoom(posX, posY);
	}

	/**
	 * Tells if the robot has stopped for reasons like lack of energy, 
	 * hitting an obstacle, etc.
	 * Once a robot is has stopped, it does not rotate or 
	 * move anymore.
	 * @return true if the robot has stopped, false otherwise
	 */
	@Override
	public boolean hasStopped() {
		//Checks if Robot's energy has stopped, or if Robot has just ran into a wall
		if(getBatteryLevel() <= 0 || crashed)
			return true;
		
		//Returns true if Robot has stopped, false otherwise.
		return false;
	}

	/**
	 * Tells the distance to an obstacle (a wall) 
	 * in the given direction.
	 * The direction is relative to the robot's current forward direction.
	 * Distance is measured in the number of cells towards that obstacle, 
	 * e.g. 0 if the current cell has a wallboard in this direction, 
	 * 1 if it is one step forward before directly facing a wallboard,
	 * Integer.MaxValue if one looks through the exit into eternity.
	 * The robot uses its internal DistanceSensor objects for this and
	 * delegates the computation to the DistanceSensor which need
	 * to be installed by calling the addDistanceSensor() when configuring
	 * the robot.
	 * @param direction specifies the direction of interest
	 * @return number of steps towards obstacle if obstacle is visible 
	 * in a straight line of sight, Integer.MAX_VALUE otherwise
	 */
	@Override
	public int distanceToObstacle(Direction direction) {
		int distance = 0;
		//Uses sensor to find how many tiles the robot can walk in a given direction.
		
		switch(direction)
		{
			//Using the left sensor
			case LEFT:
			{
				try
				{
					//Will use left sensor and rotate "clockwise" once as a west and east are backwards so this will be the correct path to go.
					distance = leftSensor.distanceToObstacle(getCurrentPosition(), getCurrentDirection().rotateClockwise(), energy);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				break;
			}
			//Using the right sensor
			case RIGHT:
			{
				try
				{
					//Will use right sensor to calculate distance and rotates direction from robot three times to get correct direction
					distance = rightSensor.distanceToObstacle(getCurrentPosition(), getCurrentDirection().rotateClockwise().rotateClockwise().rotateClockwise(), energy);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				break;
			}
			//Using the forward sensor
			case FORWARD:
			{
				try
				{
					//Will use forward sensor to calculate distance and does not need to change direction from robot.
					distance = forwardSensor.distanceToObstacle(getCurrentPosition(), getCurrentDirection(), energy);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				break;
			}
			//Using the backwards sensor
			case BACKWARD:
			{
				try
				{
					//Will use backwards sensor to calculate distance and goes 180 degrees around robots direction to get correct direction
					distance = backwardSensor.distanceToObstacle(getCurrentPosition(), getCurrentDirection().oppositeDirection(), energy);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				break;
			}
		}
		
		return distance;
	}

	/**
	 * Tells if a sensor can identify the exit in the given direction relative to 
	 * the robot's current forward direction from the current position.
	 * It is a convenience method is based on the distanceToObstacle() method and transforms
	 * its result into a boolean indicator.
	 * @param direction is the direction of the sensor
	 * @return true if the exit of the maze is visible in a straight line of sight
	 * @throws UnsupportedOperationException if robot has no sensor in this direction
	 * or the sensor exists but is currently not operational
	 */
	@Override
	public boolean canSeeThroughTheExitIntoEternity(Direction direction) throws UnsupportedOperationException {
		int distance = 0;
		
		//Checks to see if Sensor never hits an obstacle because it is staring out the exit
		distance = distanceToObstacle(direction);
		
		//Returns true if sensor goes to Integer.MAX_VALUE, false otherwise
		if(distance == Integer.MAX_VALUE)
			return true;
		else
			return false;
	}

	/**
	 * A private method to set up sensors with mazes the second the Robot gets a controller.
	 */
	private void setMazesForSensors()
	{
		//Gives sensors mazes if they exist
		leftSensor.setMaze(robotController.getMazeConfiguration());
		rightSensor.setMaze(robotController.getMazeConfiguration());
		forwardSensor.setMaze(robotController.getMazeConfiguration());
		backwardSensor.setMaze(robotController.getMazeConfiguration());
	}
	
	/**
	 * A method to return the sensor of the robot
	 * @param dir is the direction of which sensor we want
	 * @return the sensor of the specified direction
	 */
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
	 * @param direction the direction the sensor is mounted on the robot
	 * @param meanTimeBetweenFailures is the mean time in seconds, must be greater than zero
	 * @param meanTimeToRepair is the mean time in seconds, must be greater than zero
	 * @throws UnsupportedOperationException if method not supported
	 */
	@Override
	public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		//Throw an exception to make sure that it is unimplemented

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
		//Throw an exception to make sure that it is unimplemented
	}

}
