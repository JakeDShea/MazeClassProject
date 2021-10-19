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
	ReliableRobot robot;
	Maze maze;
	boolean[][] hasBeenVisited;
	
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
		while(!solved)
		{
			//Throws an exception if Wizard's Robot stops by means like losing energy or crashing
			try
			{
				solved = !drive1Step2Exit();
			}
			catch (Exception e)
			{
				throw new Exception();
			}
			// TODO: handle exception
			
			//Returns false is Wizard's Robot ever walks on a cell it has already walked upon.
			if(!hasBeenVisited[robot.getCurrentPosition()[0]][robot.getCurrentPosition()[1]])
				hasBeenVisited[robot.getCurrentPosition()[0]][robot.getCurrentPosition()[1]] = true;
			else
				return false;
			//Returns true otherwise, meaning it has completed the maze.
		}
		
		return false;
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
				robot.rotate(Turn.AROUND);
			
			return false;
		}
		
		//Else, moves the Robot 1 step in whatever the best direction is.
		int[] destination = new int[2];
		CardinalDirection direction = CardinalDirection.East;
		
		destination = maze.getNeighborCloserToExit(robot.getCurrentPosition()[0], robot.getCurrentPosition()[1]);
		
		//Figures out which direction we want to go to
		direction = CardinalDirection.getDirection(destination[0] - robot.getCurrentPosition()[0], destination[1] - robot.getCurrentPosition()[1]);
		
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
		
		//Robot finally moves
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

}
