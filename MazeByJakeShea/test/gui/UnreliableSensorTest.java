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

public class UnreliableSensorTest
{
	//Sets up maze and stuff that robot needs for a sensor to be working.
	private static Maze maze;
	private static StubOrder order;
	private static MazeFactory factory;
	
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
	}
	
	// TESTS THAT SENSORS WORK CORRECTLY WITHOUT FAILING YET
	
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
		// Create a sensor
		UnreliableSensor sensor = new UnreliableSensor();
		sensor.setMaze(maze);
		
		// Set sensor attributes
		int[] pos = {18, 9};
		float[] power = {3500};
		
		//Check that the sensor works when it has a wall a bit in front of it.
		assertEquals(0, sensor.distanceToObstacle(pos, CardinalDirection.North, power));
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
		// Create a sensor
		UnreliableSensor sensor = new UnreliableSensor();
		sensor.setMaze(maze);
		
		// Set sensor attributes
		int[] pos = {18, 9};
		float[] power = {3500};
		
		//Check that the sensor works when it has a wall a bit in front of it.
		assertEquals(1, sensor.distanceToObstacle(pos, CardinalDirection.South, power));
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
		// Create a sensor
		UnreliableSensor sensor = new UnreliableSensor();
		sensor.setMaze(maze);
		
		// Set sensor attributes
		int[] pos = {18, 8};
		float[] power = {3500};
		
		//Check that the sensor works when it has a wall a bit in front of it.
		assertEquals(6, sensor.distanceToObstacle(pos, CardinalDirection.West, power));
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
		// Create a sensor
		UnreliableSensor sensor = new UnreliableSensor();
		sensor.setMaze(maze);
		
		// Set sensor attributes
		int[] pos = {18, 9};
		float[] power = {3500};
		
		// Call the distanceToObstacle method
		sensor.distanceToObstacle(pos, CardinalDirection.South, power);
		
		// Assert power gets lowered
		assertEquals(3499, power[0]);
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
		Maze newMaze;
		
		newOrder.setBuilder(Builder.DFS);
		newOrder.setSeed(241);
		newOrder.setSkill(0);
		
		//Orders maze
		newFactory.order(newOrder);
		newFactory.waitTillDelivered();
		
		newMaze = newOrder.getMaze();
		
		// Create a sensor
		UnreliableSensor sensor = new UnreliableSensor();
		sensor.setMaze(newMaze);
		
		// Set sensor attributes
		int[] pos = {0, 3};
		float[] power = {3500};
		
		//Check that the sensor works when it has a wall a bit in front of it.
		assertEquals(Integer.MAX_VALUE, sensor.distanceToObstacle(pos, CardinalDirection.East, power));
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
		Maze newMaze;
		
		newOrder.setBuilder(Builder.DFS);
		newOrder.setSeed(241);
		newOrder.setSkill(0);
		
		//Orders maze
		newFactory.order(newOrder);
		newFactory.waitTillDelivered();
		
		newMaze = newOrder.getMaze();
		
		// Create a sensor
		UnreliableSensor sensor = new UnreliableSensor();
		sensor.setMaze(newMaze);
		
		// Set sensor attributes
		int[] pos = {3, 3};
		float[] power = {3500};
		
		//Check that the sensor works when it has a wall a bit in front of it.
		assertEquals(Integer.MAX_VALUE, sensor.distanceToObstacle(pos, CardinalDirection.East, power));
	}
	
	// TESTS THAT TEST SENSOR CAN BE WORKING OR CAN FAIL
	
	/**
	 * Test Case: Checks if a sensor sees when not failed
     * Routine being tested: distanceToObstacle()
     * 
     * Robot checks a sensor when functional and again when not
	 * @throws Exception 
	 */
	@Test
	public void testSensorSeesThenDoesNotSeeThenSeesAgain() throws Exception
	{
		//New setup to test the exit
		StubOrder newOrder = new StubOrder();
		MazeFactory newFactory = new MazeFactory();
		Maze newMaze;
		
		newOrder.setBuilder(Builder.DFS);
		newOrder.setSeed(241);
		newOrder.setSkill(0);
		
		//Orders maze
		newFactory.order(newOrder);
		newFactory.waitTillDelivered();
		
		newMaze = newOrder.getMaze();
		
		// Create a sensor
		UnreliableSensor sensor = new UnreliableSensor();
		sensor.setMaze(newMaze);
		
		// Set sensor attributes
		int[] pos = {0, 3};
		float[] power = {3500};
		
		sensor.startFailureAndRepairProcess(4000, 2000);
		
		//Check that the sensor works when it has a wall a bit in front of it.
		assertEquals(Integer.MAX_VALUE, sensor.distanceToObstacle(pos, CardinalDirection.East, power));
		
		// Sets up timing values
		long startTime = System.nanoTime();
		long endTime = System.nanoTime();
		
		// Waits a sufficiently long time for the sensor to fail
		do {
			endTime = System.nanoTime();
		// has the background threads run for near 4 seconds, which should be enough time for a failure of the sensor to occur
		} while (endTime - startTime < 4100000000.0);
		
		// Fails
		try
		{
			sensor.distanceToObstacle(pos, CardinalDirection.East, power);
		}
		catch(Exception e)
		{
			assertTrue(e.getMessage().equals("SensorFailure"));
		}
		
		// Resets them to see if a sensor can turn back on
		startTime = System.nanoTime();
		endTime = System.nanoTime();
		
		// Waits a sufficiently long time for the sensor to fail
		do {
			endTime = System.nanoTime();
		// has the background threads run for near 2 seconds, which should be enough time for a repair of the sensor to occur
		} while (endTime - startTime < 2100000000.0);
		
		// Assert sensor is back up and running
		assertEquals(Integer.MAX_VALUE, sensor.distanceToObstacle(pos, CardinalDirection.East, power));
		
		// Cleans up threads
		sensor.stopFailureAndRepairProcess();
	}
	
	/**
	 * Test Case: Checks if a sensor works with a robot
     * Routine being tested: setRobot()
     * 
     * Checks if a sensor can be used by a robot
	 * @throws Exception 
	 */
	@Test
	public void testSensorCanGoOnRobot() throws Exception
	{
		// Create a robot for sensors to go on
		UnreliableRobot robot = new UnreliableRobot(0, 0, 0, 0);
		Controller controller = new Controller();
		
		// Sets up a controller to use the sensors
		controller.switchFromGeneratingToPlaying(maze);
		robot.setController(controller);
		controller.setRobotAndDriver(robot, null);
		((UnreliableRobot) controller.robot).start();
		
		// Asserts the robot works in tandem with the sensors correctly
		assertTrue(robot.getSensor(Direction.LEFT) != null  && ((UnreliableSensor)robot.getSensor(Direction.LEFT)).sensorDirection == Direction.LEFT);
		assertTrue(robot.getSensor(Direction.FORWARD) != null && ((UnreliableSensor)robot.getSensor(Direction.FORWARD)).sensorDirection == Direction.FORWARD);
		assertTrue(robot.getSensor(Direction.BACKWARD) != null && ((UnreliableSensor)robot.getSensor(Direction.BACKWARD)).sensorDirection == Direction.BACKWARD);
		assertTrue(robot.getSensor(Direction.RIGHT) != null && ((UnreliableSensor)robot.getSensor(Direction.RIGHT)).sensorDirection == Direction.RIGHT);
		
		// Cleans up threads
		controller.getRobot().stopFailureAndRepairProcess(Direction.LEFT);
		controller.getRobot().stopFailureAndRepairProcess(Direction.FORWARD);
		controller.getRobot().stopFailureAndRepairProcess(Direction.BACKWARD);
		controller.getRobot().stopFailureAndRepairProcess(Direction.RIGHT);
	}
}
