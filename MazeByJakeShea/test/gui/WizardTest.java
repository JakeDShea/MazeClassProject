package gui;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import generation.CardinalDirection;
import generation.Maze;
import generation.MazeFactory;
import generation.StubOrder;
import generation.Order.Builder;

public class WizardTest
{
	//Sets up maze and stuff that robot needs.
	private Maze maze;
	private StubOrder order;
	private MazeFactory factory;
	private Controller controller;
	
	/**
	 * Creates a maze for robot testing
	 * This makes it so tests don't have to remake mazes multiple times.
	 */
	@Before
	public void setUp()
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
	
	/**
	 * Test Case: Tests if the Wizard can solve a maze
     * Routine being tested: drive2Exit()
     * 
     * Tests if a robot can make it to the end of a maze.
	 * @throws Exception 
	 */
	@Test
	public void testWizardSolvesMaze() throws Exception
	{
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot(false, false, false, false);
		Wizard testWizard = new Wizard();
		
		testWizard.setRobot(robot);
		testWizard.setMaze(maze);
		controller.switchFromGeneratingToPlaying(maze);
		robot.setController(controller);
		controller.setRobotAndDriver(robot, testWizard);
		
		//True if wizard solves maze
		assertTrue(testWizard.drive2Exit());
	}
	
	/**
	 * Test Case: Tests if wizard can measure efficiency of robot in terms of odometer
     * Routine being tested: getPathLength()
     * 
     * Tests if a robot can make it to the end of a maze while counting steps
	 * @throws Exception 
	 */
	@Test
	public void testWizardCountsSteps() throws Exception
	{
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot(false, false, false, false);
		Wizard testWizard = new Wizard();
		
		testWizard.setRobot(robot);
		testWizard.setMaze(maze);
		controller.switchFromGeneratingToPlaying(maze);
		robot.setController(controller);
		controller.setRobotAndDriver(robot, testWizard);
		
		//Asserts if wizard solves maze
		assertTrue(testWizard.drive2Exit());
		
		//Asserts that wizard took shortest path
		assertEquals(97, testWizard.getPathLength());
	}
	
	/**
	 * Test Case: Tests if wizard can measure efficiency of robot in terms of battery level
     * Routine being tested: getPathLength()
     * 
     * Tests if a robot can make it to the end of a maze without losing energy
	 * @throws Exception 
	 */
	@Test
	public void testWizardTrackEnergyConsumption() throws Exception
	{
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot(false, false, false, false);
		Wizard testWizard = new Wizard();
		
		testWizard.setRobot(robot);
		testWizard.setMaze(maze);
		controller.switchFromGeneratingToPlaying(maze);
		robot.setController(controller);
		controller.setRobotAndDriver(robot, testWizard);
		
		//Asserts if wizard solves maze
		assertTrue(testWizard.drive2Exit());
		
		//Asserts that wizard took shortest path via energy
		//97 moves, 50 90-degree turns, and senses for the exit twice based off my algorithm gets me 734 energy used.
		assertEquals(734, testWizard.getEnergyConsumption());
	}
}
