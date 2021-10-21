package gui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.BeforeClass;

import generation.CardinalDirection;
import generation.Maze;
import generation.MazeFactory;
import generation.StubOrder;
import gui.Robot.Direction;
import gui.Robot.Turn;
import generation.Order.Builder;

import org.junit.Test;

public class ReliableRobotTest
{
	//Sets up maze and stuff that robot needs.
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
		robotParam.setController(controller);
		controller.setRobotAndDriver(robotParam, null);
	}
	
	/**
	 * Test Case: Check if robot is able to move a single step
     * Routine being tested: ReliableRobot()
     * 
     * Tests if a robot moves after having it move one step.
	 * @throws Exception 
	 */
	@Test
	public void testRobotStarts() throws Exception
	{
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		setRobot(robot);
		
		//Assert robot starts in correct area.
		assertEquals(18, robot.getCurrentPosition()[0]);
		assertEquals(9, robot.getCurrentPosition()[1]);
		
		//Assert direction is correct
		assertEquals(CardinalDirection.East, robot.getCurrentDirection());
		
		//Assert it starts with correct amount of energy
		assertEquals(100, robot.getBatteryLevel());
	}
	
	/**
	 * Test Case: Check if robot is able to rotate to the left
     * Routine being tested: rotate()
     * 
     * Tests if direction of the robot changes after it rotates
	 * @throws Exception 
	 */
	@Test
	public void testRobotTurns() throws Exception
	{
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		setRobot(robot);
		
		//Robot turns only once
		robot.rotate(Turn.LEFT);
		
		//Assert robot stays in correct area.
		assertEquals(18, robot.getCurrentPosition()[0]);
		assertEquals(9, robot.getCurrentPosition()[1]);
		
		//Assert robot changed to correct direction
		assertEquals(CardinalDirection.South, robot.getCurrentDirection());
		
		//Assert robot lost correct amount of energy
		assertEquals(3497, robot.getBatteryLevel());
	}
	
	/**
	 * Test Case: Check if robot is able to jump a single step
     * Routine being tested: jump()
     * 
     * Tests if a robot moves after having it jump one step.
     * Also checks if it crashes by going into an impossible room.
	 * @throws Exception 
	 */
	@Test
	public void testRobotJumps() throws Exception
	{
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		setRobot(robot);
		
		//Robot jumps only once
		robot.jump();
		
		//Assert robot moves to correct area.
		assertEquals(19, robot.getCurrentPosition()[0]);
		assertEquals(9, robot.getCurrentPosition()[1]);
		
		//Assert robot has not changed to correct direction
		assertEquals(CardinalDirection.East, robot.getCurrentDirection());
		
		//Assert robot lost correct amount of energy
		assertEquals(3460, robot.getBatteryLevel());
		
		//Have robot try to jump again, which it should not be able to.
		robot.jump();
		
		//Assert robot has not moved.
		assertEquals(19, robot.getCurrentPosition()[0]);
		assertEquals(9, robot.getCurrentPosition()[1]);
		
		//Check that robot crashed
		assertTrue(robot.hasStopped());
	}
	
	/**
	 * Test Case: Check if robot is able to rotate in all directions
     * Routine being tested: rotate()
     * 
     * Tests if a robot is able to rotate multiple times
	 * @throws Exception 
	 */
	@Test
	public void testRobotRotatesCorrectly() throws Exception
	{
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		setRobot(robot);
		
		//Robot rotates to the left
		robot.rotate(Turn.LEFT);
		
		//Assert robot has changed to correct direction
		assertEquals(CardinalDirection.South, robot.getCurrentDirection());
		
		//Robot rotates around entirely
		robot.rotate(Turn.AROUND);
		
		//Assert robot has changed to correct direction
		assertEquals(CardinalDirection.North, robot.getCurrentDirection());
		
		//Robot rotates to the right
		robot.rotate(Turn.RIGHT);
		
		//Assert robot has changed to correct direction
		assertEquals(CardinalDirection.West, robot.getCurrentDirection());
		
		//Check that robot has not moved from rotating
		assertEquals(18, robot.getCurrentPosition()[0]);
		assertEquals(9, robot.getCurrentPosition()[1]);
		
		//Asserts correct amount of energy has been decreased
		assertEquals(3488, robot.getBatteryLevel());
	}
	
	/**
	 * Test Case: Check if robot is able to move
     * Routine being tested: move()
     * 
     * Tests if a robot is able to move one cell
	 * @throws Exception 
	 */
	@Test
	public void testRobotMoves() throws Exception
	{
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		setRobot(robot);
		
		//Robot rotates to the left to be able to move in a straight line
		robot.rotate(Turn.LEFT);
		
		//Move robot forward once
		robot.move(1);
		
		//Assert robot has changed position
		assertEquals(18, robot.getCurrentPosition()[0]);
		assertEquals(10, robot.getCurrentPosition()[1]);
		
		//Asserts correct amount of energy has been decreased
		assertEquals(3491, robot.getBatteryLevel());
	}
	
	/**
	 * Test Case: Check if robot is able to move
     * Routine being tested: move()
     * 
     * Tests if a robot is able to move multiple times
	 * @throws Exception 
	 */
	@Test
	public void testRobotMovesMultipleTimes() throws Exception
	{
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		setRobot(robot);
		
		//Robot rotates to the left to be able to move in a straight line
		robot.rotate(Turn.AROUND);
		
		//Makes robot jump to get to a straigh path in this specific maze
		robot.jump();
		
		//Robot moves twice times
		robot.move(2);
		
		//Assert robot moves to correct location
		assertEquals(15, robot.getCurrentPosition()[0]);
		assertEquals(9, robot.getCurrentPosition()[1]);
		
		//Asserts correct amount of energy has been decreased
		assertEquals(3442, robot.getBatteryLevel());
	}
	
	/**
	 * Test Case: Check if robot stops when supposed to
     * Routine being tested: move()
     * 
     * Tests if a robot is unable to move after hitting a wall
	 * @throws Exception 
	 */
	@Test
	public void testRobotCrashed() throws Exception
	{
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		setRobot(robot);
		
		//Robot moves into wall
		robot.move(1);
		
		//Assert robot does not move
		assertEquals(18, robot.getCurrentPosition()[0]);
		assertEquals(9, robot.getCurrentPosition()[1]);
		
		//Assert robot has stopped
		assertTrue(robot.hasStopped());
	}
	
	/**
	 * Test Case: Check if robot is able to move
     * Routine being tested: hasStopped()
     * 
     * Tests if a robot is able to move due to not crashing or running out of energy
	 * @throws Exception 
	 */
	@Test
	public void testRobotHasNotStopped() throws Exception
	{
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		setRobot(robot);
		
		//Asserts that the robot has not hit any criteria to stop yet
		assertFalse(robot.hasStopped());
	}
	
	/**
	 * Test Case: Check if robot knows it is at exit
     * Routine being tested: isAtExit()
     * 
     * Tests if a robot knows it is by the exit
	 * @throws Exception 
	 */
	@Test
	public void testRobotAtExit() throws Exception
	{
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		setRobot(robot);
		
		//Makes robot jump to get to a straigh path to exit
		robot.jump();
		
		//Make robot rotate to face exit
		robot.rotate(Turn.RIGHT);
		
		//Robot moves twice to be one cell away from cell with exit
		robot.move(2);
		
		//Assert that the robot knows it is not near the exit
		assertFalse(robot.isAtExit());
		
		//Moves robot once more to be near exit
		robot.move(1);
		
		//Assert robot knows it is next to the exit regardless of its direction
		assertTrue(robot.isAtExit());
		robot.rotate(Turn.LEFT);
		assertTrue(robot.isAtExit());
		robot.rotate(Turn.LEFT);
		assertTrue(robot.isAtExit());
		robot.rotate(Turn.LEFT);
		assertTrue(robot.isAtExit());
		
		//Asserts correct amount of energy has been decreased
		assertEquals(3430, robot.getBatteryLevel());
	}
	
	/**
	 * Test Case: Check if robot knows it is in a room
     * Routine being tested: isInsideRoom()
     * 
     * Tests if a robot knows it is in a room
	 * @throws Exception 
	 */
	@Test
	public void testRobotinRoom() throws Exception
	{
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		setRobot(robot);
		
		//Assert that robot knows it is currently not in a room
		assertFalse(robot.isInsideRoom());
		
		//Make robot rotate to face exit
		robot.rotate(Turn.RIGHT);
		
		//Makes robot jump to get to a straigh path to exit
		robot.jump();
		
		//Assert that robot knows it now is in a room
		assertTrue(robot.isInsideRoom());
	}
	
	/**
	 * Test Case: Check if robot ran out of energy
     * Routine being tested: hasStopped()
     * 
     * Tests if a robot will stop if it has ran out of energy
	 * @throws Exception 
	 */
	@Test
	public void testRobotRunsOutOfEnergy() throws Exception
	{
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		setRobot(robot);
		
		/*   //Can do this if you want actual code to drain the battery, but code is expensive in time due to GUI attempting to paint this
		//loop until robot runs out of energy
		do
		{
			//Check that the robot is not out of energy yet
			assertFalse(robot.hasStopped());
			
			//Expensive operations for the robot to lose energy fast
			robot.jump();
			robot.rotate(Turn.AROUND);
		} while (robot.getBatteryLevel() > 0);*/
		
		//For now, we just drain the robot ourselves.
		
		//Check that the robot is not out of energy yet
		assertFalse(robot.hasStopped());
		
		//Deplete battery of robot
		robot.setBatteryLevel(0);
		
		//Check that robot has stopped
		assertTrue(robot.hasStopped());
	}
	
	/**
	 * Test Case: Check if robot counts steps
     * Routine being tested: getOdometerReading()
     * 
     * Tests if a robot counts its steps correctly
	 * @throws Exception 
	 */
	@Test
	public void testRobotOdometerAccuracy() throws Exception
	{
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		setRobot(robot);
		
		//We use the path to the exit we did in a previous test because the path is known to us
		
		//Makes robot jump to get to a straigh path to exit
		robot.jump();
				
		//Make robot rotate to face exit
		robot.rotate(Turn.RIGHT);
				
		//Robot moves twice to be one cell away from cell with exit
		robot.move(3);
		
		//Asserts that the robot counted its steps correctly
		assertEquals(4, robot.getOdometerReading());
	}
	
	/**
	 * Test Case: Check if robot resets its odometer
     * Routine being tested: resetOdometer()
     * 
     * Tests if a robot counts its steps correctly and then forgets its steps
	 * @throws Exception 
	 */
	@Test
	public void testRobotOdometerReset() throws Exception
	{
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		setRobot(robot);
		
		//We use the path to the exit we did in a previous test because the path is known to us
		
		//Makes robot jump to get to a straigh path to exit
		robot.jump();
				
		//Make robot rotate to face exit
		robot.rotate(Turn.RIGHT);
				
		//Robot moves twice to be one cell away from cell with exit
		robot.move(3);
		
		//Asserts that the robot counted its steps correctly
		assertEquals(4, robot.getOdometerReading());
		
		//Resets Odometer after checking its steps were recorded
		robot.resetOdometer();
		
		//Asserts odometer reads 0 now
		assertEquals(0, robot.getOdometerReading());
	}
	
	/**
	 * Test Case: Check if robot can make sensors
     * Routine being tested: ReliableRobot()
     * 
     * Tests if the constructor will not throw any exceptions when a robot is expected to have sensors with it
	 * @throws Exception 
	 */
	@Test(expected = Test.None.class)
	public void testRobotWitHSensors() throws Exception
	{
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		setRobot(robot);
	}
	
	/**
	 * Test Case: Check if robot can use sensors appropriately
     * Routine being tested: distanceToObstacle()
     * 
     * Tests if a robot's sensors work in all directions
	 * @throws Exception 
	 */
	@Test
	public void testRobotSensorsWork() throws Exception
	{
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		setRobot(robot);
		
		//Assert that robot's sensors work in all directions, regardless of the robot's orientation
		
		//Assert these distances when robot is facing East from starting point
		assertEquals(0, robot.distanceToObstacle(Direction.FORWARD));
		assertEquals(0, robot.distanceToObstacle(Direction.RIGHT));
		assertEquals(1, robot.distanceToObstacle(Direction.LEFT));
		assertEquals(0, robot.distanceToObstacle(Direction.BACKWARD));
		
		//Turn robot to left
		robot.rotate(Turn.LEFT);
		
		//Assert these distances when robot is facing South from starting point
		assertEquals(1, robot.distanceToObstacle(Direction.FORWARD));
		assertEquals(0, robot.distanceToObstacle(Direction.RIGHT));
		assertEquals(0, robot.distanceToObstacle(Direction.LEFT));
		assertEquals(0, robot.distanceToObstacle(Direction.BACKWARD));
		
		//Turn robot to left again
		robot.rotate(Turn.LEFT);
				
		//Assert these distances when robot is facing West from starting point
		assertEquals(0, robot.distanceToObstacle(Direction.FORWARD));
		assertEquals(1, robot.distanceToObstacle(Direction.RIGHT));
		assertEquals(0, robot.distanceToObstacle(Direction.LEFT));
		assertEquals(0, robot.distanceToObstacle(Direction.BACKWARD));
		
		//Turn robot to left one more time
		robot.rotate(Turn.LEFT);
				
		//Assert these distances when robot is facing South from starting point
		assertEquals(0, robot.distanceToObstacle(Direction.FORWARD));
		assertEquals(0, robot.distanceToObstacle(Direction.RIGHT));
		assertEquals(0, robot.distanceToObstacle(Direction.LEFT));
		assertEquals(1, robot.distanceToObstacle(Direction.BACKWARD));
	}
	
	/**
	 * Test Case: Check if robot can see out of the exit of a maze
     * Routine being tested: canSeeThroughTheExitIntoEternity()
     * 
     * Tests if a robot sees no obstacles after looking through the exit of a maze
	 * @throws Exception 
	 */
	@Test
	public void testRobotSeesInfinity() throws Exception
	{
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		setRobot(robot);
		
		//Makes robot jump to get to a straigh path to exit
		robot.jump();
		
		//Make robot rotate to face exit
		robot.rotate(Turn.RIGHT);
		
		//Robot moves twice to be one cell away from cell with exit
		robot.move(3);
		
		//Asserts Robot can peer out of the exit with the correct sensor
		assertTrue(robot.canSeeThroughTheExitIntoEternity(Direction.LEFT));
		assertFalse(robot.canSeeThroughTheExitIntoEternity(Direction.RIGHT));
		assertFalse(robot.canSeeThroughTheExitIntoEternity(Direction.FORWARD));
		assertFalse(robot.canSeeThroughTheExitIntoEternity(Direction.BACKWARD));
	}
}
