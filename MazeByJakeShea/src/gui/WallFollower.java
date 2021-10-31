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
		Direction works = getWorkingDirection();
		Boolean moved = false;
		
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
			if(robot.distanceToObstacle(works) == 0)
			{
				robot.rotate(Turn.RIGHT);
				if(robot.distanceToObstacle(works) != 0)
				{	// If there is not, move forward.
					robot.rotate(Turn.LEFT);
					faceBackForward(works);
					robot.move(1);
					moved = true;
				}
				// If there is, turn the robot once more to the right.
				else
				{
					faceBackForward(works);
					robot.rotate(Turn.RIGHT);
					
					moved = drive1Step2Exit();
					
					/*
					// Recheck the sensors and check the wall on the left again.
					//works = getWorkingDirection();
					//makeThisFaceLeft(works);
			
					// If there is a wall, just move forward.
					if(robot.distanceToObstacle(works) == 0)
					{
						robot.move(1);
						moved = true;
					}
					else
					{
						// If not, turn back to the left and move forward.
						faceBackForward(works);
						robot.rotate(Turn.LEFT);
						robot.move(1);
						moved = true;
					}*/
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
	private Direction getWorkingDirection()
	{
		if(robot instanceof UnreliableRobot)
		{
			// Checks if the driver's robot's left sensor is working. If so, return left
			if(((UnreliableRobot) robot).getSensor(Direction.LEFT) instanceof UnreliableSensor)
			{
				if(((UnreliableSensor) ((UnreliableRobot) robot).getSensor(Direction.LEFT)).isFunctioning)
					return Direction.LEFT;
				else
				{
					// Checks if forward sensor is working. If so, return forward
					if(((UnreliableRobot) robot).getSensor(Direction.FORWARD) instanceof UnreliableSensor)
					{
						if(((UnreliableSensor) ((UnreliableRobot) robot).getSensor(Direction.FORWARD)).isFunctioning)
							return Direction.FORWARD;
						else
						{
							// Checks if backward sensor is working. If so, return backward
							if(((UnreliableRobot) robot).getSensor(Direction.BACKWARD) instanceof UnreliableSensor)
							{
								if(((UnreliableSensor) ((UnreliableRobot) robot).getSensor(Direction.BACKWARD)).isFunctioning)
									return Direction.BACKWARD;
								else
								{
									// Checks if right sensor is working. If so, return right
									if(((UnreliableRobot) robot).getSensor(Direction.RIGHT) instanceof UnreliableSensor)
									{
										if(((UnreliableSensor) ((UnreliableRobot) robot).getSensor(Direction.RIGHT)).isFunctioning)
											return Direction.RIGHT;
										else
										{
											// Recalls the method until it finds a functioning direction
											return getWorkingDirection();
										}
									}
									// Right has to work because it is reliable
									else
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
}
