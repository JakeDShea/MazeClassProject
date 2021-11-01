package gui;

import generation.CardinalDirection;
import generation.Maze;
import gui.Robot.Direction;
import gui.Robot.Turn;

/**
 * Class: Wizard
 * Responsibilities: Figures out where to go for Robot
 * Collaborators: gui.RobotDriver.java
 * 				  generation.Maze.java
 * 				  gui.ReliableRobot.java
 * 
 * @Author Jake Shea
 */


public class Wizard implements RobotDriver {
	//Sets up instance variables for the Wizard, such as the Robot and maze.
	private ReliableRobot robot;
	private Maze maze;
	private boolean[][] hasBeenVisited;
	
	/**
	 * Assigns a robot platform to the driver. 
	 * The driver uses a robot to perform, this method provides it with this necessary information.
	 * @param r robot to operate
	 */
	@Override
	public void setRobot(Robot r) {
		//Sets up the Wizard's instance variable using a Robot passed as a parameter.
		robot = (ReliableRobot) r;
	}

	/**
	 * Provides the robot driver with the maze information.
	 * Only some drivers such as the wizard rely on this information to find the exit.
	 * @param maze represents the maze, must be non-null and a fully functional maze object.
	 */
	@Override
	public void setMaze(Maze mazeParam) {
		//Sets up the Wizard's maze instance variable using a Maze passed as a parameter.
		maze = mazeParam;
		
		//Sets counterpart array to allow for checking if algorithm loops.
		hasBeenVisited = new boolean[maze.getWidth()][maze.getHeight()];
		
		for(int x = 0; x < maze.getWidth(); x++)
			for(int y = 0; y < maze.getHeight(); y++)
				hasBeenVisited[x][y]= false; 
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
		//Tries to solve maze
		while(!solved){
			//Throws an exception if Wizard's Robot stops by means like losing energy or crashing
			try {
				solved = !drive1Step2Exit();
			}
			catch (Exception e) {
				throw new Exception();
			}
			//Checks if the wizard solved the maze
			if(solved)
				break;
			
			//Assert that wizard will never go on a cell twice
			assert (!hasBeenVisited[robot.getCurrentPosition()[0]][robot.getCurrentPosition()[1]]) : "Robot has never been here before";
			
			//Returns false is Wizard's Robot ever walks on a cell it has already walked upon.
			if(!hasBeenVisited[robot.getCurrentPosition()[0]][robot.getCurrentPosition()[1]])
				hasBeenVisited[robot.getCurrentPosition()[0]][robot.getCurrentPosition()[1]] = true;
			else
				return false;
			//Returns true otherwise, meaning it has completed the maze.
		}
		//Makes robot finish maze
		robot.move(1);
		return true;
	}

	/**
	 * Drives the robot one step towards the exit following
	 * its solution strategy and given the exit exists and 
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
		//If Robot is at the exit, changes the Robot's direction to face the exit and returns false.
		if(maze.getDistanceToExit(robot.getCurrentPosition()[0], robot.getCurrentPosition()[1]) == 1)
		{
			if(robot.canSeeThroughTheExitIntoEternity(Direction.LEFT))
				robot.rotate(Turn.LEFT);
			else if (robot.canSeeThroughTheExitIntoEternity(Direction.RIGHT))
				robot.rotate(Turn.RIGHT);
			else if(robot.canSeeThroughTheExitIntoEternity(Direction.BACKWARD))
			{
				//Should never be reached by wizard's algorithm
				assert (false);
				
				robot.rotate(Turn.AROUND);
			}
			else
			{
				//Assert that robot is already facing direction needed
				assert (robot.canSeeThroughTheExitIntoEternity(Direction.FORWARD)) : "Robot is facing correct direction";
			}
			
			return false;
		}
		
		//Else, moves the Robot 1 step in whatever the best direction is.
		boolean jumping = getNeighborClosestToExit(robot.getCurrentPosition()[0], robot.getCurrentPosition()[1]);
		
		// if the closest neighbor has a wall, we will jump
		if(jumping)
			robot.jump();
		// Otherwise, we simply move
		else
			robot.move(1);
		
		//Will throw an error if robot stops moving
		if(robot.hasStopped())
			throw new Exception();
		
		return true;
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
		//Returns Robot's starting energy minus it's final energy
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
		//Returns Robot's odometer reading.
		return robot.odometer;
	}

	////////////////////////////////////////////////////////////////////////////////
	//////////////////////THIS METHOD IS FOR SMART JUMPING//////////////////////////
	////////////////////////////////////////////////////////////////////////////////
	/**
	 * A private method that will turn the robot towards the closest neighbor,
	 * then returns a boolean value for whether the robot has to jump or simply
	 * move.
	 * True for jump, False for move
	 * 
	 * @param xCoor the current x-coordinate of the robot
	 * @param yCoor the curreent y-coordinate of the robot
	 * @return returns a boolean for whether the robot has to jump or not
	 */
	private boolean getNeighborClosestToExit(int xCoor, int yCoor)
	{
		// Set up the value to hold the smallest distance
		int minDistance = maze.getDistanceToExit(maze.getNeighborCloserToExit(xCoor, yCoor)[0], maze.getNeighborCloserToExit(xCoor, yCoor)[1]);
		int minX = maze.getNeighborCloserToExit(xCoor, yCoor)[0];
		int minY = maze.getNeighborCloserToExit(xCoor, yCoor)[1];
		
		// Sets up flag for if the robot will have to jump to get to this position
		boolean willJump = false;
		
		// Creates a private sensor to quickly tell if there is a wall
		ReliableSensor sensor = new ReliableSensor();
		sensor.setMaze(maze);
		
		int[] pos = {xCoor, yCoor};
		float[] power = {3500};
		
		// Checks if one spot to the right is the smallest value
		if(maze.isValidPosition(xCoor+1, yCoor))
		{
			// Checks if closer than current min
			if(minDistance - 6 > maze.getDistanceToExit(xCoor+1, yCoor))
			{
				minDistance = maze.getDistanceToExit(xCoor+1, yCoor);
				minX = xCoor + 1;
				minY = yCoor;
				
				// Checks if there is a wall there
				try {
					if(sensor.distanceToObstacle(pos, CardinalDirection.East, power) == 0)
						willJump = true;
					else
						willJump = false;
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		// Checks if one spot to the above is the smallest value
		if(maze.isValidPosition(xCoor, yCoor+1))
		{
			// Checks if closer than current min
			if(minDistance - 6> maze.getDistanceToExit(xCoor, yCoor+1))
			{
				minDistance = maze.getDistanceToExit(xCoor, yCoor+1);
				minX = xCoor;
				minY = yCoor + 1;
				
				// Checks if there is a wall there
				try {
					if(sensor.distanceToObstacle(pos, CardinalDirection.South, power) == 0)
						willJump = true;
					else
						willJump = false;
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		// Checks if one spot to the left is the smallest value
		if(maze.isValidPosition(xCoor-1, yCoor))
		{
			// Checks if closer than current min
			if(minDistance - 6> maze.getDistanceToExit(xCoor-1, yCoor))
			{
				minDistance = maze.getDistanceToExit(xCoor-1, yCoor);
				minX = xCoor-1;
				minY = yCoor;
				
				// Checks if there is a wall there
				try {
					if(sensor.distanceToObstacle(pos, CardinalDirection.West, power) == 0)
						willJump = true;
					else
						willJump = false;
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		// Checks if one spot to the above is the smallest value
		if(maze.isValidPosition(xCoor, yCoor-1))
		{
			// Checks if closer than current min
			if(minDistance - 6> maze.getDistanceToExit(xCoor, yCoor-1))
			{
				minDistance = maze.getDistanceToExit(xCoor, yCoor-1);
				minX = xCoor;
				minY = yCoor - 1;
				
				// Checks if there is a wall there
				try {
					if(sensor.distanceToObstacle(pos, CardinalDirection.North, power) == 0)
						willJump = true;
					else
						willJump = false;
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		// Now figures out which direction we should be going
		CardinalDirection direction = CardinalDirection.East;
		
		//Figures out which direction we want to go to
		direction = CardinalDirection.getDirection(minX - xCoor, minY - yCoor);
		
		//Figures out how to turn the Robot
		switch(robot.getCurrentDirection())
		{
			//Checks if Robot is facing north and turns accordingly
			case North:
			{
				switch(direction)
				{
					case East:
					{
						robot.rotate(Turn.LEFT);
						break;
					}
					case West:
					{
						robot.rotate(Turn.RIGHT);
						break;
					}
					case South:
					{
						//Should never have to turn around for wizard
						assert (false);
						
						robot.rotate(Turn.AROUND);
						break;
					}
					default:
						break;
				}
				break;
			}
			//Checks if Robot is facing south and turns accordingly
			case South:
			{
				switch(direction)
				{
					case East:
					{
						robot.rotate(Turn.RIGHT);
						break;
					}
					case West:
					{
						robot.rotate(Turn.LEFT);
						break;
					}
					case North:
					{
						//Should never have to turn around for wizard
						assert (false);
						
						robot.rotate(Turn.AROUND);
						break;
					}
					default:
						break;
				}
				break;
			}
			//Checks if Robot is facing west and turns accordingly
			case West:
			{
				switch(direction)
				{
					case East:
					{
						//Should never have to turn around for wizard
						assert (false);
						
						robot.rotate(Turn.AROUND);
						break;
					}
					case South:
					{
						robot.rotate(Turn.RIGHT);
						break;
					}
					case North:
					{
						robot.rotate(Turn.LEFT);
						break;
					}
					default:
						break;
				}
				break;
			}
			//Checks if Robot is facing east and turns accordingly
			case East:
			{
				switch(direction)
				{
					case West:
					{
						//Can actually hit this in case robot starts in the maze facing East and must go west instantly
						robot.rotate(Turn.AROUND);
						break;
					}
					case South:
					{
						robot.rotate(Turn.LEFT);
						break;
					}
					case North:
					{
						robot.rotate(Turn.RIGHT);
						break;
					}
					default:
						break;
				}
				break;
			}
		}
		
		// Has figured out and turned the robot towards where it wants to go, and returns whether it must jump or move
		return willJump;
	}
}
