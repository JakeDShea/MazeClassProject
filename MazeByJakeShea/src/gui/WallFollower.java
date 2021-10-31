package gui;

import generation.Maze;
import gui.Robot.Direction;
import gui.Robot.Turn;

/**
 * Class: WallFollower
 * Responsibilities: Figuring out where to go for Robot using a specific algorithm
 * Collaborators: gui.RobotDriver.java
 * 				  generation.Maze.java
 * 				  gui.Robot.java
 * 
 * @Author Jake Shea
 */


public class WallFollower implements RobotDriver
{
	// A robot field to interact with the maze
	Robot robot;
	// A maze field to know what the game looks like
	Maze maze;
	
	/**
	 * Assigns a robot platform to the driver. 
	 * The driver uses a robot to perform, this method provides it with this necessary information.
	 * @param r robot to operate
	 */
	@Override
	public void setRobot(Robot r) {
		// Sets the robot field to the paramter r
		robot = r;
	}

	/**
	 * Provides the robot driver with the maze information.
	 * Only some drivers such as the wizard rely on this information to find the exit.
	 * @param maze represents the maze, must be non-null and a fully functional maze object.
	 */
	@Override
	public void setMaze(Maze mazeParam) {
		// Sets the maze field to the paramter maze
		maze = mazeParam;
	}

	/**
	 * Drives the robot towards the exit following
	 * its solution strategy and given the exit exists and  
	 * given the robot's energy supply lasts long enough. 
	 * When the robot reached the exit position and its forward
	 * direction points to the exit the search terminates and 
	 * the method returns true.
	 * If the robot failed due to lack of energy or crashed, the method
	 * throws an exception.
	 * If the method determines that it is not capable of finding the
	 * exit it returns false, for instance, if it determines it runs
	 * in a cycle and can't resolve this.
	 * @return true if driver successfully reaches the exit, false otherwise
	 * @throws Exception thrown if robot stopped due to some problem, e.g. lack of energy
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		boolean solved = false;
		// Keeps looping as long as the method drive1Step2Exit continues working
		while(!solved)
		{
			// Calls the method drive1Step2Exit()
			try {
				solved = !drive1Step2Exit();
			} catch (Exception e) {
				// If robot has stopped, throw an exception
				throw new Exception();
			}
			
			// If robot has found the exit, return true
			if(solved)
				break;
		
			// Otherwise, return false
		}
		
		robot.move(1);
		
		return solved;
	}

	/**
	 * Drives the robot one step towards the exit following
	 * its solution strategy and given the exists and 
	 * given the robot's energy supply lasts long enough.
	 * It returns true if the driver successfully moved
	 * the robot from its current location to an adjacent
	 * location.
	 * At the exit position, it rotates the robot 
	 * such that if faces the exit in its forward direction
	 * and returns false. 
	 * If the robot failed due to lack of energy or crashed, the method
	 * throws an exception. 
	 * @return true if it moved the robot to an adjacent cell, false otherwise
	 * @throws Exception thrown if robot stopped due to some problem, e.g. lack of energy
	 */
	@Override
	public boolean drive1Step2Exit() throws Exception {
		// Check all of the robots sensors to see which one works using the
		// getWorkingDirection() method
		Direction works = getWorkingDirectionForLeft();
		// Finds which direction will be best to move forward with
		Direction support = getWorkingDirectionForForward(works);
		Boolean moved = false;
		
		int leftDistance = 0, forwardDistance = 0;
		
		// First checks if robot has found the exit
		if(robot.isAtExit())
		{
			// Checks where the exit is and positions robot accordingly
			checkForExit(works);
		}
		else
		{
			// Turn robot using the direction we get from above using the
			// makeThisFaceLeft(...) method
			makeThisFaceLeft(works);
			
			// Check if a wall exists using the sensor that works
			// Turn robot to the right once and check if there is a wall in front
			leftDistance = robot.distanceToObstacle(works);
			if(leftDistance == 0)
			{
				// Sets up robot to check in front of it
				makeThisFaceForward(works, support);
				
				forwardDistance = robot.distanceToObstacle(support);
				
				if(forwardDistance != 0)
				{	// If there is not, move forward.
					turnRobotBackAgain(works, support);
					faceBackForward(works);
					robot.move(1);
					moved = true;
				}
				// If there is, turn the robot once more to the right.
				else
				{
					// Sets up robot to recur this method
					turnRobotBackAgain(works, support);
					faceBackForward(works);
					robot.rotate(Turn.RIGHT);
					
					// Recurring is the easiest way to change the wall on your left
					moved = drive1Step2Exit();
				}
			}
			else
			{
				// Robot can turn left
				faceBackForward(works);
				robot.rotate(Turn.LEFT);
				robot.move(1);
				moved = true;
			}
		}
	
		// If robot crashed or ran out of energy, crash the program
		if(robot.hasStopped())
		{
			throw new Exception();
		}
		
		return moved;
	}

	/**
	 * A helper method for the robots movement to see which direction
	 * can be checked to see what lays ahead forward
	 * 
	 * @return Returns the easiest direction to check forward for the robot
	 */
	private Direction getWorkingDirectionForForward(Direction dir)
	{
		switch(dir)
		{
		// Checks best direction that can check in front of robot if left sensor is working
		case LEFT:
		{
			return forLeft();
		}
		// Checks best direction that can check in front of robot if forward sensor is working
		case FORWARD:
		{
			return forForward();
		}
		// Checks best direction that can check in front of robot if backward sensor is working
		case BACKWARD:
		{
			return forBackward();
		}
		// Checks best direction that can check in front of robot if right sensor is working
		case RIGHT:
		{
			return forRight();
		}
		}
		
		// Should never hit this
		return null;
	}

	/**
	 * Returns the total energy consumption of the journey, i.e.,
	 * the difference between the robot's initial energy level at
	 * the starting position and its energy level at the exit position. 
	 * This is used as a measure of efficiency for a robot driver.
	 * @return the total energy consumption of the journey
	 */
	@Override
	public float getEnergyConsumption() {
		// Return how much energy the robot has used
		return 3500 - robot.getBatteryLevel();
	}

	/**
	 * Returns the total length of the journey in number of cells traversed. 
	 * Being at the initial position counts as 0. 
	 * This is used as a measure of efficiency for a robot driver.
	 * @return the total length of the journey in number of cells traversed
	 */
	@Override
	public int getPathLength() {
		// Return how far the robot has moved
		return robot.getOdometerReading();
	}
	
	/**
	 * A private method that is used to find the first working sensor in the
	 * preferred order of left, front, back, and then right lastly.
	 * 
	 * This is made into its own method for ease of writing and not copying code
	 * multiple times.
	 * 
	 * @return the first possible direction of the driver's robot's working sensor
	 */
	private Direction getWorkingDirectionForLeft()
	{
		if(robot instanceof UnreliableRobot)
		{
			return forBackward();
		}
		
		return Direction.LEFT;
	}
	
	/**
	 * A private method that is used to turn the robot as efficiently as possible
	 * to standardize when one of its working sensors shall face the left wall.
	 * 
	 * @param dir : the direction of the nearest sensor that works
	 */
	private void makeThisFaceLeft(Direction dir)
	{
		// Checks if dir is left, then does nothing
		
		// Checks if dir is forward, then turns robot left once.
		if(dir == Direction.FORWARD)
			robot.rotate(Turn.LEFT);
		
		// Checks if dir is backward, then turns the robot right once.
		if(dir == Direction.BACKWARD)
			robot.rotate(Turn.RIGHT);
		
		//Checks if dir is right, then turns the robot around.
		if(dir == Direction.RIGHT)
			robot.rotate(Turn.AROUND);
	}
	
	/**
	 * A private method that is used to easily make the robot turn
	 * around to correctly face the correct path again.
	 * 
	 * Effectively reverses the turn made in makeThisFaceLeft()
	 * 
	 * @param dir the direction of the working sensor that was used
	 */
	private void faceBackForward(Direction dir)
	{
		// Checks if dir is left, then does nothing
		
		// Checks if dir is forward, then turns robot right once.
		if(dir == Direction.FORWARD)
			robot.rotate(Turn.RIGHT);
		
		// Checks if dir is backward, then turns the robot left once.
		if(dir == Direction.BACKWARD)
			robot.rotate(Turn.LEFT);
		
		//Checks if dir is right, then turns the robot around.
		if(dir == Direction.RIGHT)
			robot.rotate(Turn.AROUND);
	}
	
	private void checkForExit(Direction working)
	{
		// Standardizes the robots direction
		makeThisFaceLeft(working);
		
		// Checks if robot can see through the exit
		if(robot.canSeeThroughTheExitIntoEternity(working))
		{
			faceBackForward(working);
			robot.rotate(Turn.LEFT);
		}
		else
		{
			// Turns the robot a little
			robot.rotate(Turn.RIGHT);
			
			if(robot.canSeeThroughTheExitIntoEternity(working))
			{
				// Rotates Robot back to original position
				robot.rotate(Turn.LEFT);
				faceBackForward(working);
			}
			else
			{
				// Turns robot even more
				robot.rotate(Turn.RIGHT);
				
				if(robot.canSeeThroughTheExitIntoEternity(working))
				{
					// Rotates Robot back to original position
					robot.rotate(Turn.AROUND);
					faceBackForward(working);
					robot.rotate(Turn.RIGHT);
				}
				else
				{
					// Exit must be behind the robot here
					robot.rotate(Turn.AROUND);
				}
			}
		}
	}
	
	/**
	 * This helper method is used by the robot to be able to easily
	 * and efficiently check the spots directly in front of it by turning
	 * it to make the robot's efficient sensor to check in front of it be
	 * in front
	 * 
	 * @param leftward the sensor direction that is checking the left
	 * @param forwardward the sensor direction that is checking in front
	 */
	private void makeThisFaceForward(Direction leftward, Direction forwardward)
	{
		// The case where the robot is already in the correct orientation
		if((leftward == Direction.LEFT && forwardward == Direction.FORWARD) || (leftward == Direction.FORWARD && forwardward == Direction.RIGHT) ||
				(leftward == Direction.RIGHT && forwardward == Direction.BACKWARD) || (leftward == Direction.BACKWARD && forwardward == Direction.LEFT))
			// Placeholder
			System.out.print("");
		// The case where robot must turn to the left once
		else if ((leftward == Direction.LEFT && forwardward == Direction.RIGHT) || (leftward == Direction.FORWARD && forwardward == Direction.BACKWARD) ||
				(leftward == Direction.RIGHT && forwardward == Direction.LEFT) || (leftward == Direction.BACKWARD && forwardward == Direction.FORWARD))
			robot.rotate(Turn.LEFT);
		// The case where robot must turn to the right once
		else if ((leftward == Direction.LEFT && forwardward == Direction.LEFT) || (leftward == Direction.FORWARD && forwardward == Direction.FORWARD) ||
				(leftward == Direction.RIGHT && forwardward == Direction.RIGHT) || (leftward == Direction.BACKWARD && forwardward == Direction.BACKWARD))
			robot.rotate(Turn.RIGHT);
		// Must be the final case where robot has to turn around
		else
			robot.rotate(Turn.AROUND);
	}
	
	/**
	 * A complementary method to makeThisFaceForward(), it simply
	 * undoes the turn to return the robot to its proper orientation.
	 * 
	 * @param leftward the sensor direction that is checking the left
	 * @param forwardward the sensor direction that is checking in front
	 */
	private void turnRobotBackAgain(Direction leftward, Direction forwardward)
	{
		// The case where the robot is already in the correct orientation
		if((leftward == Direction.LEFT && forwardward == Direction.FORWARD) || (leftward == Direction.FORWARD && forwardward == Direction.RIGHT) ||
				(leftward == Direction.RIGHT && forwardward == Direction.BACKWARD) || (leftward == Direction.BACKWARD && forwardward == Direction.LEFT))
			// Placeholder
			System.out.print("");
		// The case where robot must turn to the right once
		else if ((leftward == Direction.LEFT && forwardward == Direction.RIGHT) || (leftward == Direction.FORWARD && forwardward == Direction.BACKWARD) ||
				(leftward == Direction.RIGHT && forwardward == Direction.LEFT) || (leftward == Direction.BACKWARD && forwardward == Direction.FORWARD))
			robot.rotate(Turn.RIGHT);
		// The case where robot must turn to the left once
		else if ((leftward == Direction.LEFT && forwardward == Direction.LEFT) || (leftward == Direction.FORWARD && forwardward == Direction.FORWARD) ||
				(leftward == Direction.RIGHT && forwardward == Direction.RIGHT) || (leftward == Direction.BACKWARD && forwardward == Direction.BACKWARD))
			robot.rotate(Turn.LEFT);
		// Must be the final case where robot has to turn around
		else
			robot.rotate(Turn.AROUND);
	}
	
	////////////////////////////////////////////////////////////////////
	////////SOME HELPER METHODS FOR CHOOSING DIRECTIONS EASILY//////////
	////////////////////////////////////////////////////////////////////
	
	private Direction forLeft()
	{
		if(((UnreliableRobot) robot).getSensor(Direction.FORWARD) instanceof UnreliableSensor)
		{
			if(((UnreliableSensor) ((UnreliableRobot) robot).getSensor(Direction.FORWARD)).isFunctioning)
				return Direction.FORWARD;
			else
			{
				// Checks if right sensor is working. If so, return right
				if(((UnreliableRobot) robot).getSensor(Direction.RIGHT) instanceof UnreliableSensor)
				{
					if(((UnreliableSensor) ((UnreliableRobot) robot).getSensor(Direction.RIGHT)).isFunctioning)
						return Direction.RIGHT;
					else
					{
						// Checks if left sensor is working. If so, return left
						if(((UnreliableRobot) robot).getSensor(Direction.LEFT) instanceof UnreliableSensor)
						{
							if(((UnreliableSensor) ((UnreliableRobot) robot).getSensor(Direction.LEFT)).isFunctioning)
								return Direction.LEFT;
							else
							{
								// Otherwise backwards is our best bet
								return Direction.BACKWARD;
							}
						}
						// Left has to work because it is reliable
						else
							return Direction.LEFT;
					}
				}
				// Right has to work because it is reliable
				else
					return Direction.RIGHT;
			}
		}
		// Forward has to work because it is reliable
		else
			return Direction.FORWARD;
	}
	
	private Direction forForward()
	{
		// Will check if the right sensor is working
		if(((UnreliableRobot) robot).getSensor(Direction.RIGHT) instanceof UnreliableSensor)
		{
			if(((UnreliableSensor) ((UnreliableRobot) robot).getSensor(Direction.RIGHT)).isFunctioning)
				return Direction.RIGHT;
			else
			{
				// Checks if backward sensor is working. If so, return backward
				if(((UnreliableRobot) robot).getSensor(Direction.BACKWARD) instanceof UnreliableSensor)
				{
					if(((UnreliableSensor) ((UnreliableRobot) robot).getSensor(Direction.BACKWARD)).isFunctioning)
						return Direction.BACKWARD;
					else
					{
						// Checks if forward sensor is working. If so, return forward
						if(((UnreliableRobot) robot).getSensor(Direction.FORWARD) instanceof UnreliableSensor)
						{
							if(((UnreliableSensor) ((UnreliableRobot) robot).getSensor(Direction.FORWARD)).isFunctioning)
								return Direction.FORWARD;
							else
							{
								// Otherwise Left is the best bet
								return Direction.LEFT;
							}
						}
						// Forward has to work because it is reliable
						else
							return Direction.FORWARD;
					}
				}
				// Backward has to work because it is reliable
				else
					return Direction.BACKWARD;
			}
		}
		// Right has to work because it is reliable
		else
			return Direction.RIGHT;
	}
	
	private Direction forRight()
	{
		// Will check if the backward sensor is working
		if(((UnreliableRobot) robot).getSensor(Direction.BACKWARD) instanceof UnreliableSensor)
		{
			if(((UnreliableSensor) ((UnreliableRobot) robot).getSensor(Direction.BACKWARD)).isFunctioning)
				return Direction.BACKWARD;
			else
			{
				// Checks if left sensor is working. If so, return left
				if(((UnreliableRobot) robot).getSensor(Direction.LEFT) instanceof UnreliableSensor)
				{
					if(((UnreliableSensor) ((UnreliableRobot) robot).getSensor(Direction.LEFT)).isFunctioning)
						return Direction.LEFT;
					else
					{
						// Checks if right sensor is working. If so, return right
						if(((UnreliableRobot) robot).getSensor(Direction.RIGHT) instanceof UnreliableSensor)
						{
							if(((UnreliableSensor) ((UnreliableRobot) robot).getSensor(Direction.RIGHT)).isFunctioning)
								return Direction.RIGHT;
							else
							{
								// Otherwise Forward is the best bet
								return Direction.FORWARD;
							}
						}
						// Forward has to work because it is reliable
						else
							return Direction.RIGHT;
					}
				}
				// Backward has to work because it is reliable
				else
					return Direction.LEFT;
			}
		}
		// Right has to work because it is reliable
		else
			return Direction.BACKWARD;
	}
	
	private Direction forBackward()
	{
		// Will check if the left sensor is working
		if(((UnreliableRobot) robot).getSensor(Direction.LEFT) instanceof UnreliableSensor)
		{
			if(((UnreliableSensor) ((UnreliableRobot) robot).getSensor(Direction.LEFT)).isFunctioning)
				return Direction.LEFT;
			else
			{
				// Checks if forward sensor is working. If so, return backward
				if(((UnreliableRobot) robot).getSensor(Direction.FORWARD) instanceof UnreliableSensor)
				{
					if(((UnreliableSensor) ((UnreliableRobot) robot).getSensor(Direction.FORWARD)).isFunctioning)
						return Direction.FORWARD;
					else
					{
						// Checks if backward sensor is working. If so, return forward
						if(((UnreliableRobot) robot).getSensor(Direction.BACKWARD) instanceof UnreliableSensor)
						{
							if(((UnreliableSensor) ((UnreliableRobot) robot).getSensor(Direction.BACKWARD)).isFunctioning)
								return Direction.BACKWARD;
							else
							{
								// Otherwise Right is the best bet
								return Direction.RIGHT;
							}
						}
						// Backward has to work because it is reliable
						else
							return Direction.BACKWARD;
					}
				}
				// Forward has to work because it is reliable
				else
					return Direction.FORWARD;
			}
		}
		// Left has to work because it is reliable
		else
			return Direction.LEFT;
	}
}
