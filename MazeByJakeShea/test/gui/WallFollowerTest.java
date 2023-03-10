package gui;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

/**
 * Class: WallFollowerTest
 * Responsibilities: Tests whether the WallFollower algorithm works correctly
 * Collaborators: tests.gui.DriverTest.java
 * 
 * If you are reading this, I apologize for the
 * long running time, but in order to accurately
 * test my wall-following algorithm, I need to do
 * a bunch of tests with size 1 mazes which it cannot
 * solve efficiently. These tests take a while
 * and I could not shorten them well.
 * 
 * @Author Jake Shea
 */

public class WallFollowerTest extends DriverTest
{
	/**
	 * Test Case: Tests if WallFollower can measure efficiency of robot in terms of odometer
     * Routine being tested: getPathLength()
     * 
     * Tests if a robot can make it to the end of a maze while counting steps
	 * @throws Exception 
	 */
	@Test
	public void testFollowerCountsSteps() throws Exception
	{
		// Set up the maze and controller
		setUp(519, 0);
		
		// Needs this to be set for GUI purposes. Does not matter for the test otherwise
		controller.reliability = "0000";
		
		// Create a new WallFollower driver and robot
		WallFollower testFollower = new WallFollower();
		UnreliableRobot robot = new UnreliableRobot(0, 0, 0, 0);
				
		// Set them up to each other and to the controller
		setForTesting(testFollower, robot);
		
		//Makes controller start the unreliable sensor failures.
		((UnreliableRobot) controller.getRobot()).start();
		
		// Assert that the WallFollower finishes the maze with
		// the expected number of steps
		testFollower.drive2Exit();
		assertEquals(15, testFollower.getPathLength());
	}
	
	/**
	 * Test Case: Tests if WallFollower can measure efficiency of robot in terms of battery level
     * Routine being tested: getPathLength()
     * 
     * Tests if a robot can make it to the end of a maze without losing energy
	 * @throws Exception 
	 */
	@Test
	public void testFollowerTrackEnergyConsumption() throws Exception
	{
		// Set up the maze and controller
		setUp(519, 0);
		
		// Needs this to be set for GUI purposes. Does not matter for the test otherwise
		controller.reliability = "1100";
		
		// Create a new WallFollower driver and robot
		WallFollower testFollower = new WallFollower();
		
		//I make the left and forward sensors reliable to for ease of counting turns
		UnreliableRobot robot = new UnreliableRobot(1, 1, 0, 0);
				
		// Set them up to each other and to the controller
		setForTesting(testFollower, robot);
		
		//Makes controller start the unreliable sensor failures.
		((UnreliableRobot) controller.getRobot()).start();
		
		// Assert that the WallFollower finishes the maze with
		// the expected amount of energy used
		testFollower.drive2Exit();
		
		assertEquals(168, testFollower.getEnergyConsumption());
	}
	
	/**
	 * Test Case: Tests if WallFollower can solve a maze with an exit on the left
     * Routine being tested: drive2Exit()
     * 
     * Tests if the robot is able to leave via the left exit of a maze properly
	 * @throws Exception 
	 */
	@Test
	public void testFollowerCanSolveLeftExit() throws Exception
	{
		// Sets up with a maze that specifically has an eastern exit
		setUp(685, 0);

		// Needs this to be set for GUI purposes. Does not matter for the test otherwise
		controller.reliability = "0000";
		
		// Create a new WallFollower driver and robot
		WallFollower testFollower = new WallFollower();
		UnreliableRobot robot = new UnreliableRobot(0, 0, 0, 0);
				
		// Set them up to each other and to the controller
		setForTesting(testFollower, robot);
		
		//Makes controller start the unreliable sensor failures.
		((UnreliableRobot) controller.getRobot()).start();
		
		// Assert that the WallFollower can use drive2Exit() to finish the maze
		assertTrue(testFollower.drive2Exit());
	}
	
	/**
	 * Test Case: Tests if WallFollower can solve a maze with an exit on the right
     * Routine being tested: drive2Exit()
     * 
     * Tests if the robot is able to leave via the right exit of a maze properly
	 * @throws Exception 
	 */
	@Test
	public void testFollowerCanSolveRightExit() throws Exception
	{
		// Sets up with a maze that specifically has an western exit
		setUp(241, 0);
		
		// Needs this to be set for GUI purposes. Does not matter for the test otherwise
		controller.reliability = "0000";
		
		// Create a new WallFollower driver and robot
		WallFollower testFollower = new WallFollower();
		UnreliableRobot robot = new UnreliableRobot(0, 0, 0, 0);
				
		// Set them up to each other and to the controller
		setForTesting(testFollower, robot);
		
		//Makes controller start the unreliable sensor failures.
		((UnreliableRobot) controller.getRobot()).start();
		
		// Assert that the WallFollower can use drive2Exit() to finish the maze
		assert(testFollower.drive2Exit());
	}
	
	/**
	 * Test Case: Tests if WallFollower can solve a maze with an exit on the bottom
     * Routine being tested: drive2Exit()
     * 
     * Tests if the robot is able to leave via the bottom exit of a maze properly
	 * @throws Exception 
	 */
	@Test
	public void testFollowerCanSolveBottomExit() throws Exception
	{
		// Sets up with a maze that specifically has an northern exit
		setUp(657, 0);
		
		// Needs this to be set for GUI purposes. Does not matter for the test otherwise
		controller.reliability = "0000";
		
		// Create a new WallFollower driver and robot
		WallFollower testFollower = new WallFollower();
		UnreliableRobot robot = new UnreliableRobot(0, 0, 0, 0);
				
		// Set them up to each other and to the controller
		setForTesting(testFollower, robot);
		
		//Makes controller start the unreliable sensor failures.
		((UnreliableRobot) controller.getRobot()).start();
		
		// Assert that the WallFollower can use drive2Exit() to finish the maze
		assertTrue(testFollower.drive2Exit());
	}
	
	/**
	 * Test Case: Tests if WallFollower can solve a maze with an exit on the top
     * Routine being tested: drive2Exit()
     * 
     * Tests if the robot is able to leave via the top exit of a maze properly
	 * @throws Exception 
	 */
	@Test
	public void testFollowerCanSolveTopExit() throws Exception
	{
		// Set up the maze and controller
		// Sets up with a maze that specifically has an southern exit
		setUp(157, 0);
		
		// Needs this to be set for GUI purposes. Does not matter for the test otherwise
		controller.reliability = "0000";
		
		// Create a new WallFollower driver and robot
		UnreliableRobot robot = new UnreliableRobot(0, 0, 0, 0);
		WallFollower testFollower = new WallFollower();
		
		// Set them up to each other and to the controller
		setForTesting(testFollower, robot);
		
		//Makes controller start the unreliable sensor failures.
		((UnreliableRobot) controller.getRobot()).start();
		
		// Assert that the WallFollower can use drive2Exit() to finish the maze
		assertTrue(testFollower.drive2Exit());
	}
	
	/**
	 * Test Case: Tests if WallFollower can fail as robot runs out of energy
     * Routine being tested: drive2Exit()
     * 
     * Tests what the WallFollower does if robot runs out of energy
	 * @throws Exception 
	 */
	@Test
	public void testFollowerCanFail() throws Exception
	{
		// Set up the maze and controller
		setUp(0, 0);
		
		// Create a new WallFollower driver and robot
		UnreliableRobot robot = new UnreliableRobot(0, 0, 0, 0);
		WallFollower testFollower = new WallFollower();
		
		// Lower the energy of the robot right away so it will die before winning
		robot.setBatteryLevel(0);
				
		// Set them up to each other and to the controller
		setForTesting(testFollower, robot);
		
		//Makes controller start the unreliable sensor failures.
		((UnreliableRobot) controller.getRobot()).start();
		
		// Assert that the WallFollower knows that it will fail here trying to finish the maze.
		try
		{
			testFollower.drive2Exit();
			
			//This assert should not be able to run
			assert (false);
		}
		catch(Exception e)
		{
			//This assert should always run
			assert (true);
		}
	}
	
	/**
	 * Test Case: Tests if WallFollower can solve a maze where sensors will start failing
     * Routine being tested: drive2Exit()
     * 
     * Tests if the robot is able to leave a maze that it cannot solve in 4 or less seconds
	 * @throws Exception 
	 */
	@Test
	public void testFollowerCanSolveSufficientlyLargeMaze() throws Exception
	{
		// Set up the maze and controller
		// Sets up with a maze that specifically has an southern exit
		setUp(613, 1);
		
		// Needs this to be set for GUI purposes. Does not matter for the test otherwise
		controller.reliability = "0000";
		
		// Create a new WallFollower driver and robot
		UnreliableRobot robot = new UnreliableRobot(0, 0, 0, 0);
		WallFollower testFollower = new WallFollower();
		
		// Set them up to each other and to the controller
		setForTesting(testFollower, robot);
		
		//Makes controller start the unreliable sensor failures.
		((UnreliableRobot) controller.getRobot()).start();
		
		// Assert that the WallFollower can use drive2Exit() to finish the maze
		assertTrue(testFollower.drive2Exit());
	}
	
	/**
	 * Test Case: Tests if WallFollower can solve a maze where sensors will start failing
     * Routine being tested: drive2Exit()
     * 
     * Tests if the robot is able to leave a maze that it cannot solve in 4 or less seconds
	 * @throws Exception 
	 */
	@Test
	public void testFollowerCanSolveSufficientlyLargeMazeForwardAndBackReliable() throws Exception
	{
		// Set up the maze and controller
		// Sets up with a maze that specifically has an southern exit
		setUp(613, 1);
		
		// Needs this to be set for GUI purposes. Does not matter for the test otherwise
		controller.reliability = "1001";
		
		// Create a new WallFollower driver and robot
		UnreliableRobot robot = new UnreliableRobot(1, 0, 0, 1);
		WallFollower testFollower = new WallFollower();
		
		// Set them up to each other and to the controller
		setForTesting(testFollower, robot);
		
		//Makes controller start the unreliable sensor failures.
		((UnreliableRobot) controller.getRobot()).start();
		
		// Assert that the WallFollower can use drive2Exit() to finish the maze
		assertTrue(testFollower.drive2Exit());
	}
	
	/**
	 * Test Case: Tests if WallFollower will see a maze exit and immediately go for it
     * Routine being tested: drive2Exit()
     * 
     * Tests if the robot is able to leave a maze and sense its exit before it is right next to it
	 * @throws Exception 
	 */
	@Test
	public void testFollowerSeesExitAndRuns() throws Exception
	{
		// Set up the maze and controller
		// Sets up with a maze that specifically has an southern exit
		setUp(893, 1);
		
		// Needs this to be set for GUI purposes. Does not matter for the test otherwise
		controller.reliability = "1100";
		
		// Create a new WallFollower driver and robot which will take most efficient path
		UnreliableRobot robot = new UnreliableRobot(1, 1, 0, 0);
		WallFollower testFollower = new WallFollower();
		
		// Set them up to each other and to the controller
		setForTesting(testFollower, robot);
		
		//Makes controller start the unreliable sensor failures.
		((UnreliableRobot) controller.getRobot()).start();
		
		// Assert that the WallFollower can use drive2Exit() to finish the maze
		assertTrue(testFollower.drive2Exit());
	}
	
	/**
	 * Test Case: Tests if WallFollower doesn't break when weirdly spawning in a room
     * Routine being tested: dealWithRoom
     * 
     * Tests if the robot is able to leave a maze where the robot
     * spawned in a room with no wall to its left
	 * @throws Exception 
	 */
	@Test
	public void testFollowerStartsInRoom() throws Exception
	{
		// Set up the maze and controller
		// Sets up with a maze that specifically has an southern exit
		setUp(110, 1);
		
		// Needs this to be set for GUI purposes. Does not matter for the test otherwise
		controller.reliability = "1100";
		
		// Create a new WallFollower driver and robot which will take most efficient path
		UnreliableRobot robot = new UnreliableRobot(1, 1, 0, 0);
		WallFollower testFollower = new WallFollower();
		
		// Set them up to each other and to the controller
		setForTesting(testFollower, robot);
		
		//Makes controller start the unreliable sensor failures.
		((UnreliableRobot) controller.getRobot()).start();
		
		// Assert that the WallFollower can use drive2Exit() to finish the maze
		assertTrue(testFollower.drive2Exit());
	}
	
	/**
	 * Test Case: Tests if WallFollower can break out of a loop
     * Routine being tested: drive2Exit
     * 
     * Tests if the robot is able to finish a maze where the left wall
     * is an inner wall as well
	 * @throws Exception 
	 */
	@Test
	public void testFollowerSolvesAnInfiniteLoop() throws Exception
	{
		// Set up the maze and controller
		// Sets up with a maze that specifically has an southern exit
		setUp(933, 1);
		
		// Needs this to be set for GUI purposes. Does not matter for the test otherwise
		controller.reliability = "1100";
		
		// Create a new WallFollower driver and robot which will take most efficient path
		UnreliableRobot robot = new UnreliableRobot(1, 1, 0, 0);
		WallFollower testFollower = new WallFollower();
		
		// Set them up to each other and to the controller
		setForTesting(testFollower, robot);
		
		//Makes controller start the unreliable sensor failures.
		((UnreliableRobot) controller.getRobot()).start();
		
		// Assert that the WallFollower can use drive2Exit() to finish the maze
		assertTrue(testFollower.drive2Exit());
	}
}
