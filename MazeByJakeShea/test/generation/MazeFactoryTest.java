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
	private static Maze perfectMaze ;
	private static StubOrder setupOrder;
	private static MazeFactory factorySetup;
	
	/**
	 * Create 10 mazes of varying difficulty for ease of testing.
	 * This makes it so tests don't have to remake mazes multiple times.
	 * Do not do skill levels a-f because it would be too expensive to create them.
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
		
		//Creates perfect maze for use for tests that need one
		setupOrder.setPerfect(true);
		factorySetup.order(setupOrder);
		
		factorySetup.waitTillDelivered();
		
		perfectMaze = setupOrder.getMaze();
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
	private Maze getMazeVariable(int choice)
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
    	Distance mazeDist;
    	//Create a Maze object to hold the currently tested maze
    	Maze testMaze;
    	//Loop through all created mazes
    	for(int i = 0; i < 10; i++)
    	{
    		testMaze = getMazeVariable(i);
    		
    		//Set Distance object from each maze
    		mazeDist = testMaze.getMazedists();
    		
    		//Assert that getExitPosition in dist attribute in MazeBuilder is not none
        	//for each maze.
    		assertNotNull(mazeDist.getExitPosition());
    	}
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
    	for(int i = 0; i < 10; i++)
    	{
    		testMaze = getMazeVariable(i);
    		
    		//Set Distance object from each maze
    		mazeDist = testMaze.getMazedists();
    		
    		//Assert that getStartPosition in dist attribute in MazeBuilder is not none
        	//for each maze.
    		assertNotNull(mazeDist.getStartPosition());
    	}
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
    	//Create a floorplan object to hold layout of each maze
    	Floorplan testFloor;
    	//Create a Maze object to hold the currently tested maze
    	Maze testMaze;
    	//Create a boolean flag set to false to see if an exit has been found
    	boolean isFound = false;

    	//Loop through all created mazes
    	for(int i = 0; i < 10 && !isFound; i++)
    	{
    		testMaze = getMazeVariable(i);
    		
    		//Iterate through all external cells like so and stop looping when
    		//the flag has been made true.
    		testFloor = testMaze.getFloorplan();
    	
    		//Iterate through cells on top and check if they have no wallboard
    		//facing north
    		//If there is one cell like that, set the flag to true
    		for(int x = 0; x < testMaze.getWidth(); x++)
    		{
    			if(!testFloor.hasWall(x, 0, CardinalDirection.North))
    			{
    				isFound = true;
    				break;
    			}
    		}
    		
    		//Check an exit hasn't been found yet or end already
    		if(isFound)
    		{
    			break;
    		}
    	
    		//Iterate through cells on left and check if they have no wallboard
    		//facing west
    		//If there is one cell like that, set the flag to true
    		for(int y = 0; y < testMaze.getHeight(); y++)
    		{
    			if(!testFloor.hasWall(0, y, CardinalDirection.West))
    			{
    				isFound = true;
    				break;
    			}
    		}
    		
    		//Check an exit hasn't been found yet or end already
    		if(isFound)
    		{
    			break;
    		}
    		
    		//Iterate through cells on right and check if they have no wallboard
    		//facing east
    		//If there is one cell like that, set the flag to true
    		for(int y = 0; y < testMaze.getHeight(); y++)
    		{
    			if(!testFloor.hasWall(testMaze.getWidth(), y, CardinalDirection.East))
    			{
    				isFound = true;
    				break;
    			}
    		}
    	
    		//Check an exit hasn't been found yet or end already
    		if(isFound)
    		{
    			break;
    		}
    		
    		//Iterate through cells on bottom and check if they have no wallboard
    		//facing south
    		//If there is one cell like that, set the flag to true
    	
    		for(int x = 0; x < testMaze.getWidth(); x++)
    		{
    			if(!testFloor.hasWall(x, testMaze.getHeight(), CardinalDirection.South))
    			{
    				isFound = true;
    				break;
    			}
    		}
    		
    		//Assert that the flag is true.
    		assertTrue(isFound);
    	}
    	
    	
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
    	//Create a floorplan object to hold layout of each maze
    	Floorplan testFloor;
    	//Create a Maze object to hold the currently tested maze
    	Maze testMaze;
    	//Create an integer counter set to 0 to see how many exits are found
    	int exits = 0;

    	//Loop through all created mazes
    	for(int i = 0; i < 10; i++)
    	{
    		testMaze = getMazeVariable(i);
    		
    		//Iterate through all external cells like so and stop looping when
    		//the flag has been made true.
    		testFloor = testMaze.getFloorplan();
    	
    		//Iterate through all external cells like so
        	
        	//Iterate through cells on top and check if they have no wallboard
        	//facing north
        	//If there is a cell like that, increment the counter
    		for(int x = 0; x < testMaze.getWidth(); x++)
    		{
    			if(!testFloor.hasWall(x, 0, CardinalDirection.North))
    				exits++;
    		}
    		
    		//Iterate through cells on left and check if they have no wallboard
        	//facing west
        	//If there is a cell like that, increment the counter
    		for(int y = 0; y < testMaze.getHeight(); y++)
    		{
    			if(!testFloor.hasWall(0, y, CardinalDirection.West))
    				exits++;
    		}
    	
    		//Iterate through cells on right and check if they have no wallboard
    		//facing east
    		//If there is a cell like that, increment the counter
    		for(int y = 0; y < testMaze.getHeight(); y++)
    		{
    			if(!testFloor.hasWall(testMaze.getWidth() - 1, y, CardinalDirection.East))
    				exits++;
    		}
    	
    		//Iterate through cells on bottom and check if they have no wallboard
    		//facing south
    		//If there is a cell like that, increment the counter
    		for(int x = 0; x < testMaze.getWidth(); x++)
    		{
    			if(!testFloor.hasWall(x, testMaze.getHeight() - 1, CardinalDirection.South))
    				exits++;
    		}
    		
    		//Assert that the counter is equal to 1.
    		assertEquals(exits, 1);
    		
    		//Reset exits counter for next maze
    		exits = 0;
    	}
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
    	Maze testMaze;
    	//Create Distance object to hold mazes distance matrix
    	Distance testDist;

    	//Loop through all created mazes
    	for(int i = 0; i < 10; i++)
    	{
    		testMaze = getMazeVariable(i);
    		
    		//Run through every value in dist attribute in testMaze.
    		testDist = testMaze.getMazedists();
    		
    		for(int x = 0; x < testMaze.getWidth(); x++)
    			for(int y = 0; y < testMaze.getHeight(); y++)
    			{
    				//Assert that each value is not none.
    				assertNotNull(testDist.getDistanceValue(x, y));
    			}
    	}
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
    	Maze testMaze;
    	//Create int array with two values to hold exit position.
    	int[] exitPos = new int[2];
    	//Create a distance object to use distance's methods
    	Distance testDist;
    	//Create an integer to check what the minimum distance is.
    	int min = Integer.MAX_VALUE;
    	
    	//Loop through all created mazes
    	for(int i = 0; i < 10; i++)
    	{
    		testMaze = getMazeVariable(i);
    		testDist = testMaze.getMazedists();
    		
    		//Use dist's getExitPosition() function and put it into int array
    		exitPos = testDist.getExitPosition();
    	
    		//Iterate through every value in dist attribute in builder attribute in MazeFactory object.
    		for(int x = 0; x < testMaze.getWidth(); x++)
    			for(int y = 0; y < testMaze.getHeight(); y++)
    			{
    				//Find smallest value in maze besides the exit position itself
    				if(x != exitPos[0] || y != exitPos[1])
    				{
    					if(min > testDist.getDistanceValue(x, y))
    						min = testDist.getDistanceValue(x, y);
    				}
    			}
    		
    		//Assert that value at exit position is smaller than every other value besides itself.
    		assertTrue(testDist.getDistanceValue(exitPos[0], exitPos[1]) < min);
    		
    		//Fixes min back to large value so tests can stay up to date.
    		min = Integer.MAX_VALUE;
    	}
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
    	Maze testMaze;
    	//Create int array with two values to hold exit position.
    	int[] startPos = new int[2];
    	//Create a distance object to use distance's methods
    	Distance testDist;
    	//Create an integer to check what the maximum distance is.
    	int max = Integer.MIN_VALUE;
    	
    	//Loop through all created mazes
    	for(int i = 0; i < 10; i++)
    	{
    		testMaze = getMazeVariable(i);
    		testDist = testMaze.getMazedists();
    		
    		//Use dist's getStartPosition() function and put it into int array
    		startPos = testDist.getStartPosition();
    		
    		//Iterate through every value in dist attribute in builder attribute in MazeFactory object.
    		for(int x = 0; x < testMaze.getWidth(); x++)
    			for(int y = 0; y < testMaze.getHeight(); y++)
    			{
    				//Find smallest value in maze besides the exit position itself
    				if(x != startPos[0] || y != startPos[1])
    				{
    					if(max < testDist.getDistanceValue(x, y))
    						max = testDist.getDistanceValue(x, y);
    				}
    			}
    		
    		//Assert that value at start position is greater than or equal to every other value.
    		//Is allowed to be equal because there may be another place that is equally far,
    		//only for minimal values near the exit can there be one.
    		assertTrue(testDist.getDistanceValue(startPos[0], startPos[1]) >= max);
    		
    		//Fixes max back to smallest value to keep test up and running correctly
    		max = Integer.MIN_VALUE;
    	}
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
    	//Create boolean flag for existence of rooms set to false.
    	boolean hasRooms = false;
    	
    	//Create a maze-sized matrix for boolean values.
    	boolean[][] roomMatrix = new boolean[perfectMaze.getWidth()][perfectMaze.getHeight()];
    	
    	//Iterate through every cell.
    	//If a cell has zero wallboards, put true in the corresponding cell
    	//in the boolean 2d array.
    	//Otherwise you put false.
    	for(int x = 0; x < perfectMaze.getWidth(); x++)
    		for(int y = 0; y < perfectMaze.getHeight(); y++)
    		{
    			if(perfectMaze.getFloorplan().hasNoWall(x, y, CardinalDirection.North) && 
    				perfectMaze.getFloorplan().hasNoWall(x, y, CardinalDirection.South) &&
    				perfectMaze.getFloorplan().hasNoWall(x, y, CardinalDirection.West) &&
    				perfectMaze.getFloorplan().hasNoWall(x, y, CardinalDirection.East))
    				roomMatrix[x][y] = true; 
    			else
					roomMatrix[x][y]= false; 
    		}
    	
    	boolean foundRoom = false;
    	boolean didntFindRoom = false;
    	
    	//Go through the boolean array.
    	for(int x = 0; x < perfectMaze.getWidth(); x++)
    		for(int y = 0; y < perfectMaze.getHeight(); y++)
    		{
    			//Whenever you hit a true, check if the neighboring cells are also true.
    			if(roomMatrix[x][y])
    			{
    				//If this makes a square of at least the constant MIN_ROOM_DIMENSION in
    				//MazeBuilder.java, set the boolean flag to true and break the loop.
    				for(int roomX = x; roomX < x + MazeBuilder.MIN_ROOM_DIMENSION; roomX++)
    					for(int roomY = y; roomY < y + MazeBuilder.MIN_ROOM_DIMENSION; roomY++)
    					{
    						//Ends termination
    						if(!roomMatrix[roomX][roomY])
    						{
    							roomX = x + MazeBuilder.MIN_ROOM_DIMENSION;
    							didntFindRoom = true;
    							break;
    						}
    					}
    				
    				//Checks if a room was actually found
    				if(!didntFindRoom)
    					foundRoom = true;
    				else
						didntFindRoom = false;
    			}
    		}
    	
    	//Assert the boolean flag equals false.
    	assertFalse(foundRoom);
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
    	//Create an integer counter to keep track of all internal walls
    	int walls = 0;
    	
    	//Count all internal walls within maze
    	for(int x = 0; x < perfectMaze.getWidth(); x++)
    		for(int y = 0; y < perfectMaze.getHeight(); y++)
    		{
    			//Checks any edge cases for counting wall, specifically the bottom row and the right column
    			if((x == perfectMaze.getWidth() - 1) && (y == perfectMaze.getHeight() - 1))
    				walls += 0;
    			else if (x == perfectMaze.getWidth() - 1)
    			{
    				if(perfectMaze.getFloorplan().hasWall(x, y, CardinalDirection.South))
    					walls++;
    			}
    			else if(y == perfectMaze.getHeight() - 1)
    			{
    				if(perfectMaze.getFloorplan().hasWall(x, y, CardinalDirection.East))
    					walls++;
    			}
    			else
    			{
    				if(perfectMaze.getFloorplan().hasWall(x, y, CardinalDirection.East))
    					walls++;
    				
    				if(perfectMaze.getFloorplan().hasWall(x, y, CardinalDirection.South))
    					walls++;
				}
    		}
    	
    	//For maze of dimensions x and y
    	//Assert that counter is equal to (x+1)y [all possible vertical walls]
    	//+ x(y+1) [all horizontal walls] - 2(x+y) [All external walls]
    	//- (x + y - 1) [Walls destroyed by making a minimal spanning tree]
    	assertEquals(walls, (((perfectMaze.getWidth() + 1) * perfectMaze.getHeight()) + (perfectMaze.getWidth() * (perfectMaze.getHeight() + 1)) -
    			(2 * (perfectMaze.getWidth() + perfectMaze.getHeight())) - (perfectMaze.getWidth() * perfectMaze.getHeight() - 1)));
    }
}
