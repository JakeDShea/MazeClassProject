package gui;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class WizardTest extends DriverTest
{	
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
		setUp(0, 0);
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		Wizard testWizard = new Wizard();
		
		//Set up for communication between robot, wizard, and controller
		setForTesting(testWizard, robot);
		
		// Set up for the GUI, should not affect the logic of the tests
		controller.reliability = "1111";
		
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
		setUp(519,0);
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		Wizard testWizard = new Wizard();
		
		//Set up for communication between robot, wizard, and controller
		setForTesting(testWizard, robot);
		
		//Asserts if wizard solves maze
		assertTrue(testWizard.drive2Exit());
		
		//Asserts that wizard took shortest path
		assertEquals(2, testWizard.getPathLength());
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
		setUp(519,0);
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		Wizard testWizard = new Wizard();
		
		//Set up for communication between robot, wizard, and controller
		setForTesting(testWizard, robot);
		
		//Asserts if wizard solves maze
		assertTrue(testWizard.drive2Exit());
		
		//Asserts that wizard took shortest path via energy
		//97 moves, 50 90-degree turns, and senses for the exit twice based off my algorithm gets me 734 energy used.
		assertEquals(51, testWizard.getEnergyConsumption());
	}
	
	/**
	 * Test Case: Tests if wizard can solve a maze with an exit on the left
     * Routine being tested: drive2Exit()
     * 
     * Tests if the robot is able to leave via the left exit of a maze properly
	 * @throws Exception 
	 */
	@Test
	public void testWizardCanSolveLeftExit() throws Exception
	{
		//Sets up a maze with a seed that creates an exit directly on the left
		setUp(685, 0);
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		Wizard testWizard = new Wizard();
		
		//Set up for communication between robot, wizard, and controller
		setForTesting(testWizard, robot);
		
		//Asserts if wizard solves maze
		assertTrue(testWizard.drive2Exit());
	}
	
	/**
	 * Test Case: Tests if wizard can solve a maze with an exit on the right
     * Routine being tested: drive2Exit()
     * 
     * Tests if the robot is able to leave via the right exit of a maze properly
	 * @throws Exception 
	 */
	@Test
	public void testWizardCanSolveRightExit() throws Exception
	{
		//Sets up a maze with a seed that creates an exit directly on the right
		setUp(291, 0);
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		Wizard testWizard = new Wizard();
		
		//Set up for communication between robot, wizard, and controller
		setForTesting(testWizard, robot);
		
		//Asserts if wizard solves maze
		assertTrue(testWizard.drive2Exit());
	}
	
	/**
	 * Test Case: Tests if wizard can solve a maze with an exit on the bottom
     * Routine being tested: drive2Exit()
     * 
     * Tests if the robot is able to leave via the bottom exit of a maze properly
	 * @throws Exception 
	 */
	@Test
	public void testWizardCanSolveBottomExit() throws Exception
	{
		//Sets up a maze with a seed that creates an exit directly on the right
		setUp(657, 0);
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		Wizard testWizard = new Wizard();
		
		//Set up for communication between robot, wizard, and controller
		setForTesting(testWizard, robot);
		
		//Asserts if wizard solves maze
		assertTrue(testWizard.drive2Exit());
	}
	
	/**
	 * Test Case: Tests if wizard can solve a maze with an exit on the top
     * Routine being tested: drive2Exit()
     * 
     * Tests if the robot is able to leave via the top exit of a maze properly
	 * @throws Exception 
	 */
	@Test
	public void testWizardCanSolveTopExit() throws Exception
	{
		//Sets up a maze with a seed that creates an exit directly on the right
		setUp(157, 0);
		//Basic setup for tests
		ReliableRobot robot = new ReliableRobot();
		Wizard testWizard = new Wizard();
		
		//Set up for communication between robot, wizard, and controller
		setForTesting(testWizard, robot);
		
		//Asserts if wizard solves maze
		assertTrue(testWizard.drive2Exit());
	}
	
	/**
	 * Test Case: Tests if wizard can fail as robot runs out of energy
     * Routine being tested: drive2Exit()
     * 
     * Tests what the wizard does if robot runs out of energy
	 * @throws Exception 
	 */
	@Test
	public void testWizardCanFail() throws Exception
	{
		//Basic set up for wizard
		setUp(157, 0);
		ReliableRobot robot = new ReliableRobot();
		Wizard testWizard = new Wizard();
		
		//Drains robots battery right away.
		robot.setBatteryLevel(0);
		
		//Set up for communication between robot, wizard, and controller
		setForTesting(testWizard, robot);
		
		//Asserts the wizard fails
		try {
			testWizard.drive2Exit();
			
			//This assert should not be able to run
			assert (false);
		}
		catch(Exception e)
		{
			//This assert should always run
			assert (true);
		}
	}
}
