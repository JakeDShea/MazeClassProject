package generation;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import generation.MazeBuilder;
import gui.Controller;
import generation.Order;
import generation.Order.Builder;
import generation.StubOrder;

public class MazeFactoryTest
{   
	//Private variables
	private static Maze maze0;
	private static Maze maze1;
	private static Maze maze2;
	private static Maze maze3;
	private static Maze maze4;
	private static Maze maze5;
	private static Maze maze6;
	private static Maze maze7;
	private static Maze maze8;
	private static Maze maze9;
	private static StubOrder setupOrder;
	private static MazeFactory factorySetup;
	
	/**
	 * Create 10 mazes of varying difficulty for ease of testing.
	 * This makes it so tests don't have to remake mazes multiple times.
	 */
	@BeforeClass
	public static void setUp()
	{
		//Set up order variable and factory object
		setupOrder = new StubOrder();
		factorySetup = new MazeFactory();
		
		//Give Order variable default builder.
		//This can get overwritten when making test cases for Boruvka
		setupOrder.setBuilder(Builder.DFS);
		
		//Loop through all ten skill levels
		for(int skill = 0; skill < 10; skill++)
		{
			//Recreate the order with a new skill and build a maze with it
			setupOrder.setSkill(skill);
			factorySetup.order(setupOrder);
			
			//Wait for order to finish
			factorySetup.waitTillDelivered();
			
			//Choose which maze variable to update based on which skill level
			//is currently being used.
			switch(setupOrder.getSkillLevel())
			{
				case 0:
				{
					maze0 = setupOrder.getMaze();
					break;
				}
				case 1:
				{
					maze1 = setupOrder.getMaze();
					break;
				}
				case 2:
				{
					maze2 = setupOrder.getMaze();
					break;
				}
				case 3:
				{
					maze3 = setupOrder.getMaze();
					break;
				}
				case 4:
				{
					maze4 = setupOrder.getMaze();
					break;
				}
				case 5:
				{
					maze5 = setupOrder.getMaze();
					break;
				}
				case 6:
				{
					maze6 = setupOrder.getMaze();
					break;
				}
				case 7:
				{
					maze7 = setupOrder.getMaze();
					break;
				}
				case 8:
				{
					maze8 = setupOrder.getMaze();
					break;
				}
				case 9:
				{
					maze9 = setupOrder.getMaze();
					break;
				}
				default:
				{
					System.out.println("No maze was made.");
					break;
				}
			}
			
		}
	}
	
	/**
	 * Nothing needed to clean up variables after each test
	 * @throws Exception
	 */
	/*
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Figures out which maze to test, as these tests can work with
	 * multiple different skill levels of mazes.
	 * @param choice: The choice will map to the skill level of
	 * whichever maze is wanted by the tests.
	 * @return: Returns the correct maze of specified skill level.
	 */
	private Maze chooseMazeVariable(int choice)
	{
		//Figures which maze is wanted by the test.
		switch(choice)
		{
			case 0: return maze0;
			case 1: return maze1;
			case 2: return maze2;
			case 3: return maze3;
			case 4: return maze4;
			case 5: return maze5;
			case 6: return maze6;
			case 7: return maze7;
			case 8: return maze8;
			case 9: return maze9;
			default: return null;
		}
	}
	
    /**
     * Test Case: Check if something gets built by the factory.
     * Routine being tested: order
     * 
     * Can test routine by creating an order for a maze, and seeing
     * if something is outputted. Not checking if it is a true maze
     * yet, only if the factory builds something.
     * This method checks if something gets built using default parameters.
     */
    @Test
    public final void testOrderDFS()
    {
    	//Create a MazeFactory object to interact with
    	MazeFactory factory = new MazeFactory();
    	//Create a StubOrder variable with default builder for a maze
    	StubOrder order = new StubOrder();
    	order.setBuilder(Builder.DFS);
    	
    	//Create a boolean flag to see if order worked or not.
    	boolean flag = false;
    	
    	//Use order as parameter for factory's order function.
    	//Set boolean flag equal to the function call.
    	flag = factory.order(order);
    	
    	//Wait until building thread terminates
    	factory.waitTillDelivered();
    	
    	//Assert that boolean flag is true.
    	assertTrue(flag);
    }
       
    /**
     * Test Case: Check if something gets built by the factory.
     * Routine being tested: order
     * 
     * Can test routine by creating an order for a maze, and seeing
     * if something is outputted. Not checking if it is a true maze
     * yet, only if the factory builds something.
     * This method checks if something gets built using Prim parameters.
     */
    @Test
    public final void testOrderPrim()
    {
    	//Create a MazeFactory object to interact with
    	MazeFactory factory = new MazeFactory();
    	//Create a controller variable with Prim builder for a maze
    	StubOrder order = new StubOrder();
    	order.setBuilder(Builder.Prim);
    	//Create a boolean flag to see if order worked or not.
    	boolean flag = false;
    	
    	//Use order as parameter for factory's order function.
    	//Set boolean flag equal to the function call.
    	flag = factory.order(order);
    	
    	//Wait until building thread terminates
    	factory.waitTillDelivered();
    	
    	//Assert that boolean flag is true.
    	assertTrue(flag);
    }
    
    /**
     * Test Case: Check if something gets built by the factory.
     * Routine being tested: order
     * 
     * Can test routine by creating an order for a maze, and seeing
     * if something is outputted. Not checking if it is a true maze
     * yet, only if the factory builds something.
     * This method checks if something gets built using Boruvka
     * parameters.
     */
    @Test
    public final void testOrderBoruvka()
    {
    	//Create a MazeFactory object to interact with
    	MazeFactory factory = new MazeFactory();
    	//Create a StubOrder variable with Boruvka builder for a maze
    	StubOrder order = new StubOrder();
    	order.setBuilder(Builder.Boruvka);
    	//Create a boolean flag to see if order worked or not.
    	boolean flag = false;
    	
    	//Use order as parameter for factory's order function.
    	//Set boolean flag equal to the function call.
    	flag = factory.order(order);
    	
    	//Wait until building thread terminates
    	factory.waitTillDelivered();
    	
    	//Assert that boolean flag is true.
    	assertTrue(flag);
    }
    
    /**
     * Test Case: Check if something does not get built by the factory.
     * Routine being tested: order
     * 
     * Can test routine by creating an order for a maze, and seeing
     * if something is outputted. Not checking if it is a true maze
     * yet, only making sure the factory does not build something.
     * This method checks to make sure that Eller's generation
     * algorithm wasn't accidentally implemented.
     */
    @Test
    public final void testOrderEller()
    {
    	//Create a MazeFactory object to interact with
    	MazeFactory factory = new MazeFactory();
    	//Create a StubOrder variable with Eller builder for a maze
    	StubOrder order = new StubOrder();
    	order.setBuilder(Builder.Eller);
    	//Create a boolean flag to see if order worked or not.
    	boolean flag = false;
    	
    	//Use order as parameter for factory's order function.
    	//Set boolean flag equal to the function call.
    	flag = factory.order(order);
    	
    	//Wait until building thread terminates
    	factory.waitTillDelivered();
    	
    	//Assert that boolean flag is false.
    	assertFalse(flag);
    }
    
    /**
     * Test Case: Check if something does not get built by the factory.
     * Routine being tested: order
     * 
     * Can test routine by creating an order for a maze, and seeing
     * if something is outputted. Not checking if it is a true maze
     * yet, only making sure the factory does not build something.
     * This method checks to make sure that Kruskal's generation
     * algorithm wasn't accidentally implemented.
     */
    @Test
    public final void testOrderKruskal()
    {
    	//Create a MazeFactory object to interact with
    	MazeFactory factory = new MazeFactory();
    	//Create a controller variable with Kruskal builder for a maze
    	StubOrder order = new StubOrder();
    	order.setBuilder(Builder.Kruskal);
    	//Create a boolean flag to see if order worked or not.
    	boolean flag = false;
    	
    	//Use order as parameter for factory's order function.
    	//Set boolean flag equal to the function call.
    	flag = factory.order(order);
    	
    	//Wait until building thread terminates
    	factory.waitTillDelivered();
    	
    	//Assert that boolean flag is false.
    	assertFalse(flag);
    }
    
    /**
     * Test Case: Check if exit is saved in maze
     * Routine being tested: order
     * 
     * Can test routine by simply seeing if exit attribute has been
     * saved.
     */
    @Test
    public final void testHasExitAttribute()
    {
    	//Create a Distance object to check exit position
    	//Create a Maze object to hold the currently tested maze
    	//Loop through all created mazes
    		
    	//Set Distance object from each maze
    		
    	//Assert that getExitPosition in dist attribute in MazeBuilder is not none
        //for each maze.
    	
    }
    
    /**
     * Test Case: Check if start is saved in maze
     * Routine being tested: order
     * 
     * Can test routine by simply seeing if starting attribute
     * has been saved.
     */
    @Test
    public final void testHasStartAttribute()
    {
    	//Create a Distance object to check exit position
    	Distance mazeDist;
    	//Create a Maze object to hold the currently tested maze
    	Maze testMaze;
    	//Loop through all created mazes
    		
    	//Set Distance object from each maze
    		
    	//Assert that getStartPosition in dist attribute in MazeBuilder is not none
        //for each maze.
    }
    
    /**
     * Test Case: Check if maze has an exit cell
     * Routine being tested: generate
     * 
     * After creating a maze, simply go to check if the floor plan
     * that should have been made has an exit somewhere within
     * the maze. 
     * Goes around border of the maze until it finds an opening
     * to the exterior of the maze, meaning there is no wallboard
     * present.
     */
    @Test
    public final void testHasExit()
    {
    	//Create a Maze object to hold the currently tested maze
    	//Create a boolean flag set to false to see if an exit has been found

    	//Loop through all created mazes
    	
    	//Iterate through all external cells like so and stop looping when
    	//the flag has been made true.
    	
    	//Iterate through cells on top and check if they have no wallboard
    	//facing north
    	//If there is one cell like that, set the flag to true
    	
    	//Iterate through cells on left and check if they have no wallboard
    	//facing west
    	//If there is one cell like that, set the flag to true
    	
    	//Iterate through cells on right and check if they have no wallboard
    	//facing east
    	//If there is one cell like that, set the flag to true
    	
    	//Iterate through cells on bottom and check if they have no wallboard
    	//facing south
    	//If there is one cell like that, set the flag to true
    	
    	//Assert that the flag is true.
    }
    
    /**
     * Test Case: Check if maze has only one exit cell
     * Routine being tested: generate
     * 
     * After creating a maze, make sure the floorplan only has
     * one opening to the exterior of the maze, and no more.
     * Goes around border of the maze trying to find an opening
     * to the exterior of the maze, meaning there is no wallboard
     * present. Counts how many times it succeeds, and passes if
     * only once, fails if more.
     */
    @Test
    public final void testHasOnlyOneExit()
    {
    	//Create a Maze object to hold the currently tested maze
    	//Create an integer counter set to 0 to see how many exits are found

    	//Loop through all created mazes
    	
    	//Iterate through all external cells like so
    	
    	//Iterate through cells on top and check if they have no wallboard
    	//facing north
    	//If there is a cell like that, increment the counter
    	
    	//Iterate through cells on left and check if they have no wallboard
    	//facing west
    	//If there is a cell like that, increment the counter
    	
    	//Iterate through cells on right and check if they have no wallboard
    	//facing east
    	//If there is a cell like that, increment the counter
    	
    	//Iterate through cells on bottom and check if they have no wallboard
    	//facing south
    	//If there is a cell like that, increment the counter
    	
    	//Assert that the counter is equal to 1.
    }
    
    /**
     * Test Case: Check if maze has only one exit cell
     * Routine being tested: generate
     * 
     * After creating a maze, makes sure that all cells have a distance
     * from the exit, meaning that they are all reachable in some way.
     * Can use this by checking the dists attribute in MazeFactory's
     * builder attribute.
     */
    @Test
    public final void testAllHaveDistance()
    {
    	//Create a Maze object to hold the currently tested maze

    	//Loop through all created mazes
    	
    	//Run through every value in dist attribute in builder attribute in MazeFactory.
    	//Assert that each value is not none.
    }
    
    /**
     * Test Case: Check that exit's distance is minimal
     * Routine being tested: generate
     * 
     * After a maze has been created, check that the distance at
     * the end is the smallest value, since it should be closest
     * to the end of the maze.
     */
    @Test
    public final void testExitIsMinimal()
    {
    	//Create a Maze object to hold the currently tested maze
    	//Create a boolean flag set to false to see if an exit has been found
    	
    	//Create int array with two values to hold exit position.
    	
    	//Loop through all created mazes
    	
    	//Use dist's getExitPosition() function and put it into int array
    	
    	//Iterate through every value in dist attribute in builder attribute in MazeFactory object.
    	//Assert that value at exit position is smaller than every other value besides itself.
    }
    
    /**
     * Test Case: Check that starting distance is maximal
     * Routine being tested: generate
     * 
     * After a maze has been created, check that the distance at
     * the beginning was chosen because it was the farthest from
     * the end of the maze.
     */
    @Test
    public final void testStartIsMaximal()
    {
    	//Create a Maze object to hold the currently tested maze
    	//Create a boolean flag set to false to see if an exit has been found
    	
    	//Create int array with two values to hold starting position.
    	
    	//Loop through all created mazes
    	
    	//Use dist's getStartPosition() function and put it into int array
    	
    	//Iterate through every value in dist attribute in builder attribute in MazeFactory object.
    	//Assert that value at exit position is greater than or equal to every other value.
    	//Is allowed to be equal because there may be another place that is equally far,
    	//only exit can there be one
    }
    
    /**
     * Test Case: Check if perfect maze has illegal cells
     * Routine being tested: order
     * 
     * Tests if there is a large enough block of cells that do
     * not have wallboards, making a room when the maze should
     * not have any.
     */
    @Test
    public final void testPerfectMazeNoRooms()
    {
    	//Create a MazeFactory object to interact with
    	//Create an order variable with default builder and perfect set for a maze\
    	//Create a maze-sized matrix for boolean values.
    	//Create boolean flag for existence of rooms set to false.
    	
    	//Loop for all skill levels
    	
    	//Use order as parameter for factory's order function.
    	//Wait until building thread terminates
    	
    	//Check if order is perfect, if not then test doesn't matter.
    	//Assert that order is perfect is true.
    	
    	//Iterate through every cell.
    	//If a cell has zero wallboards, put true in the corresponding cell
    	//in the boolean 2d array.
    	//Otherwise you put false.
    	
    	//Go through the boolean array.
    	//Whenever you hit a true, check if the neighboring cells are also true.
    	//If this makes a square of at least the constant MIN_ROOM_DIMENSION in
    	//MazeBuilder.java, set the boolean flag to true and break the loop.
    	
    	//Assert the boolean flag equals false.
    }
    
    /**
     * Test Case: Check if a perfect maze has correct number of walls
     * Routine being tested: generate
     * 
     * Tests if the maze has the current number of internal walls.
     */
    @Test
    public final void testInternalWallCount()
    {
    	//Create a MazeFactory object.
    	//Set up an order variable with default builder and perfect as true
    	//Create an integer counter to keep track of all internal walls
    	
    	//Use order for MazeFactory's order method
    	//Wait until building thread terminates
    	
    	//Count all internal walls within maze
    	
    	//For maze of dimensions x and y
    	//Assert that counter is equal to (x+1)y [all possible vertical walls]
    	//+ x(y+1) [all horizontal walls] - 2(x+y) [All external walls]
    	//- (x + y - 1) [Walls destroyed by making a minimal spanning tree]
    }
}
