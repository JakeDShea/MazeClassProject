package gui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import gui.Robot.Direction;

/**
 * Class: WallFollowerTest
 * Responsibilities: Tests whether the WallFollower algorithm works correctly
 * Collaborators: tests.gui.DriverTest.java
 * 
 * @Author Jake Shea
 */

public class WallFollowerTest extends DriverTest
{
	/**
	 * Test Case: Tests if the WallFollower can solve a maze
     * Routine being tested: drive2Exit()
     * 
     * Tests if a robot can make it to the end of a maze.
	 * @throws Exception 
	 */
	@Test
	public void testFollowerSolvesMaze() throws Exception
	{
		// Set up the maze and controller
		
		// Create a new WallFollower driver and robot
		
		// Set them up to each other and to the controller
		
		// Assert that the WallFollower can use drive2Exit() to finish the maze
	}
	
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
		
		// Create a new WallFollower driver and robot
		WallFollower testFollower = new WallFollower();
		UnreliableRobot robot = new UnreliableRobot(0, 0, 0, 0);
				
		// Set them up to each other and to the controller
		setForTesting(testFollower, robot);
		
		// Assert that the WallFollower finishes the maze with
		// the expected number of steps
		testFollower.drive2Exit();
		assertEquals(16, robot.getOdometerReading());
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
		
		// Create a new WallFollower driver and robot
		WallFollower testFollower = new WallFollower();
		
		//I make the left sensor reliable to for ease of counting turns
		UnreliableRobot robot = new UnreliableRobot(1, 0, 0, 0);
				
		// Set them up to each other and to the controller
		setForTesting(testFollower, robot);
		
		// Assert that the WallFollower finishes the maze with
		// the expected amount of energy used
		testFollower.drive2Exit();
		
		assertEquals(3263, robot.getBatteryLevel());
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
		
		// Create a new WallFollower driver and robot
				
		// Set them up to each other and to the controller
		
		// Assert that the WallFollower can use drive2Exit() to finish the maze
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
}
