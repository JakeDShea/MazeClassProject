package gui;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import generation.CardinalDirection;
import generation.Maze;
import generation.MazeFactory;
import generation.StubOrder;
import generation.Order.Builder;
import gui.Robot.Direction;
import gui.Robot.Turn;

public class ReliableSensorTest
{
	//Sets up maze and stuff that robot needs for a sensor to be working.
	private static Maze maze;
	private static StubOrder order;
	private static MazeFactory factory;
	private static Controller controller;
	
	/**
	 * Creates a maze for robot testing
	 * This makes it so tests don't have to remake mazes multiple times.
	 */
	@BeforeClass
	public static void setUp()
	{
		//Set up factory object
		factory = new MazeFactory();
		
		//Sets up order object
		order = new StubOrder();
		order.setBuilder(Builder.DFS);
		order.setSeed(927);
		order.setSkill(3);
		
		//Starting area should be [18, 9]
		
		//Orders maze
		factory.order(order);
		factory.waitTillDelivered();
		
		maze = order.getMaze();
		
		controller = new Controller();
	}
	
	private void setRobot(ReliableRobot robotParam)
	{
		//Sets up needed objects for robot
		controller.switchFromGeneratingToPlaying(maze);
		controller.reliability = "1111";
		robotParam.setController(controller);
		controller.setRobotAndDriver(robotParam, null);
	}
	
	/**
	 * Test Case: Checks if a sensor works
     * Routine being tested: distanceToObstacle()
     * 
     * Uses robot to check that combination of robot and sensor works and ease of use of sensor methods
	 * @throws Exception 
	 */
	@Test
	public void testSensorWorksWithWallInFront() throws Exception
	{
		//Basic setup for tests
		//We only need the one sensor right now, which we have the forward sensor up
		ReliableRobot robot = new ReliableRobot();
		setRobot(robot);
		
		//Check that the sensor works when it has a wall immediately in front of it.
		assertEquals(0, robot.distanceToObstacle(Direction.FORWARD));
	}
	
	/**
	 * Test Case: Checks if a sensor works
     * Routine being tested: distanceToObstacle()
     * 
     * Robot uses a sensor to see if there is a wall a little away from it
	 * @throws Exception 
	 */
	@Test
	public void testSensorWorksWithWallOneAway() throws Exception
	{
		//Basic setup for tests
		//We only need the one sensor right now, which we have the forward sensor up
		ReliableRobot robot = new ReliableRobot();
		setRobot(robot);
		
		//Have robot turn
		robot.rotate(Turn.LEFT);
		
		//Check that the sensor works when it has a wall a bit in front of it.
		assertEquals(1, robot.distanceToObstacle(Direction.FORWARD));
	}
	
	/**
	 * Test Case: Checks if a sensor works
     * Routine being tested: distanceToObstacle()
     * 
     * Robot checks a wall that is a little bit further away than just 1 cell
	 * @throws Exception 
	 */
	@Test
	public void testSensorWorksWithWallFar() throws Exception
	{
		//Basic setup for tests
		//We only need the one sensor right now, which we have the forward sensor up
		ReliableRobot robot = new ReliableRobot();
		setRobot(robot);
		
		//Move robot to an area with a long stretch until a wall
		robot.rotate(Turn.RIGHT);
		robot.jump();
		robot.rotate(Turn.RIGHT);
		
		//Check that the sensor works when it has a wall far from the front of it.
		assertEquals(6, robot.distanceToObstacle(Direction.FORWARD));
	}
	
	/**
	 * Test Case: Checks if a sensor depletes energy
     * Routine being tested: distanceToObstacle()
     * 
     * Robot checks a wall and checks if battery gets correctly depleted
	 * @throws Exception 
	 */
	@Test
	public void testSensorDrainsBattery() throws Exception
	{
		//Basic setup for tests
		//We only need the one sensor right now, which we have the forward sensor up
		ReliableRobot robot = new ReliableRobot();
		setRobot(robot);
		
		//Have robot sense with sensor
		robot.distanceToObstacle(Direction.FORWARD);
		
		//Check that the sensor drained a bit of the robot's battery
		assertEquals(3499, robot.getBatteryLevel());
	}
	
	/**
	 * Test Case: Checks if a sensor knows when it is staring out the exit of the maze
     * Routine being tested: distanceToObstacle()
     * 
     * Robot checks a sensor that is facing an exit and never hits a wall
	 * @throws Exception 
	 */
	@Test
	public void testSensorSeesInfinity() throws Exception
	{
		//New setup to test the exit
		StubOrder newOrder = new StubOrder();
		MazeFactory newFactory = new MazeFactory();
		Controller newController = new Controller();
		Maze newMaze;
		
		newOrder.setBuilder(Builder.DFS);
		newOrder.setSeed(241);
		newOrder.setSkill(0);
		
		//Orders maze
		newFactory.order(newOrder);
		newFactory.waitTillDelivered();
		
		newMaze = newOrder.getMaze();
		
		newController = new Controller();
		
		//Basic setup for tests
		//We only need the one sensor right now, which we have the forward sensor up
		ReliableRobot robot = new ReliableRobot();
		
		newController.switchFromGeneratingToPlaying(newMaze);
		robot.setController(newController);
		newController.setRobotAndDriver(robot, null);
		
		//Move robot to exit and face the sensor out of it
		robot.rotate(Turn.AROUND);
		robot.jump();
		robot.move(1);
		robot.rotate(Turn.RIGHT);
		robot.move(3);
		robot.rotate(Turn.RIGHT);
		
		//Check that the sensor drained a bit of the robot's battery
		assertEquals(Integer.MAX_VALUE, robot.distanceToObstacle(Direction.FORWARD));
	}
	
	/**
	 * Test Case: Checks if a sensor knows when it is staring out the exit of the maze
     * Routine being tested: distanceToObstacle()
     * 
     * Robot checks a sensor that is facing an exit and is also directly in front of said exit
	 * @throws Exception 
	 */
	@Test
	public void testSensorSeesInfinityRightNextToExit() throws Exception
	{
		//New setup to test the exit
		StubOrder newOrder = new StubOrder();
		MazeFactory newFactory = new MazeFactory();
		Controller newController = new Controller();
		Maze newMaze;
		
		newOrder.setBuilder(Builder.DFS);
		newOrder.setSeed(241);
		newOrder.setSkill(0);
		
		//Orders maze
		newFactory.order(newOrder);
		newFactory.waitTillDelivered();
		
		newMaze = newOrder.getMaze();
		
		newController = new Controller();
		
		//Basic setup for tests
		//We only need the one sensor right now, which we have the forward sensor up
		ReliableRobot robot = new ReliableRobot();
		
		newController.switchFromGeneratingToPlaying(newMaze);
		robot.setController(newController);
		newController.setRobotAndDriver(robot, null);
		
		//Move robot right in front of exit and face the sensor out of it
		robot.rotate(Turn.AROUND);
		robot.jump();
		robot.move(1);
		robot.rotate(Turn.RIGHT);
		robot.move(3);
		robot.rotate(Turn.RIGHT);
		robot.move(3);
		
		//Check that the sensor drained a bit of the robot's battery
		assertEquals(Integer.MAX_VALUE, robot.distanceToObstacle(Direction.FORWARD));
	}
}
