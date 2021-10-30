package gui;

import org.junit.Test;

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
		
		// Create a new WallFollower driver and robot
				
		// Set them up to each other and to the controller
		
		// Assert that the WallFollower finishes the maze with
		// the expected number of steps
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
		
		// Create a new WallFollower driver and robot
				
		// Set them up to each other and to the controller
		
		// Assert that the WallFollower finishes the maze with
		// the expected amount of energy used
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
		// Set up the maze and controller
		// Sets up with a maze that specifically has an eastern exit
		
		// Create a new WallFollower driver and robot
				
		// Set them up to each other and to the controller
		
		// Assert that the WallFollower can use drive2Exit() to finish the maze
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
		// Set up the maze and controller
		// Sets up with a maze that specifically has an western exit
		
		// Create a new WallFollower driver and robot
				
		// Set them up to each other and to the controller
		
		// Assert that the WallFollower can use drive2Exit() to finish the maze
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
		// Set up the maze and controller
		// Sets up with a maze that specifically has an northern exit
		
		// Create a new WallFollower driver and robot
				
		// Set them up to each other and to the controller
		// Assert that the WallFollower can use drive2Exit() to finish the maze
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
		
		// Create a new WallFollower driver and robot
		// Lower the energy of the robot right away so it will die before winning
				
		// Set them up to each other and to the controller
		
		// Assert that the WallFollower knows that it will fail here trying to finish the maze.
	}
}
