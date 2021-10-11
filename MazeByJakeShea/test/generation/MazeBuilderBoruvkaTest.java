package generation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import generation.Order.Builder;

public class MazeBuilderBoruvkaTest extends MazeFactoryTest
{
	//Setup for testing
	
	//Makes all variables of mazes again for ease and optimization of testing
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
	private static Maze perfectMaze;
	
	//Makes multiple order variables as well to allow test builders to use them
	private static StubOrder setupOrder0;
	private static StubOrder setupOrder1;
	private static StubOrder setupOrder2;
	private static StubOrder setupOrder3;
	private static StubOrder setupOrder4;
	private static StubOrder setupOrder5;
	private static StubOrder setupOrder6;
	private static StubOrder setupOrder7;
	private static StubOrder setupOrder8;
	private static StubOrder setupOrder9;
	private static StubOrder setupOrderPerf;
	private static MazeFactory factorySetup;
	
	/**
	 * Create 10 mazes of varying difficulty for ease of testing.
	 * This makes it so tests don't have to remake mazes multiple times.
	 * Do not do skill levels a-f because it would be too expensive to create them.
	 */
	@BeforeClass
	public static void setUp()
	{
		//Set up factory object
		factorySetup = new MazeFactory();
		
		//Give Order variable default builder.
		//This can get overwritten when making test cases for Boruvka
		
		
		//Loop through all ten skill levels
		for(int skill = 0; skill < 10; skill++)
		{
			//Choose which maze variable to update based on which skill level
			//is currently being used.
			switch(skill)
			{
				case 0:
				{
					//Create the order with a new skill and builder and build a maze with it
					setupOrder0 = new StubOrder();
					setupOrder0.setBuilder(Builder.Boruvka);
					setupOrder0.setSkill(skill);
					
					factorySetup.order(setupOrder0);
					
					//Wait for order to finish
					factorySetup.waitTillDelivered();
					
					maze0 = setupOrder0.getMaze();
					break;
				}
				case 1:
				{
					//Create the order with a new skill and builder and build a maze with it
					setupOrder1 = new StubOrder();
					setupOrder1.setBuilder(Builder.Boruvka);
					setupOrder1.setSkill(skill);
					
					factorySetup.order(setupOrder1);
					
					//Wait for order to finish
					factorySetup.waitTillDelivered();
					
					maze1 = setupOrder1.getMaze();
					break;
				}
				case 2:
				{
					//Create the order with a new skill and builder and build a maze with it
					setupOrder2 = new StubOrder();
					setupOrder2.setBuilder(Builder.Boruvka);
					setupOrder2.setSkill(skill);
					
					factorySetup.order(setupOrder2);
					
					//Wait for order to finish
					factorySetup.waitTillDelivered();
					
					maze2 = setupOrder2.getMaze();
					break;
				}
				case 3:
				{
					//Create the order with a new skill and builder and build a maze with it
					setupOrder3 = new StubOrder();
					setupOrder3.setBuilder(Builder.Boruvka);
					setupOrder3.setSkill(skill);
					
					factorySetup.order(setupOrder3);
					
					//Wait for order to finish
					factorySetup.waitTillDelivered();
					
					maze3 = setupOrder3.getMaze();
					break;
				}
				case 4:
				{
					//Create the order with a new skill and builder and build a maze with it
					setupOrder4 = new StubOrder();
					setupOrder4.setBuilder(Builder.Boruvka);
					setupOrder4.setSkill(skill);
					
					factorySetup.order(setupOrder4);
					
					//Wait for order to finish
					factorySetup.waitTillDelivered();
					
					maze4 = setupOrder4.getMaze();
					break;
				}
				case 5:
				{
					//Create the order with a new skill and builder and build a maze with it
					setupOrder5 = new StubOrder();
					setupOrder5.setBuilder(Builder.Boruvka);
					setupOrder5.setSkill(skill);
					
					factorySetup.order(setupOrder5);
					
					//Wait for order to finish
					factorySetup.waitTillDelivered();
					
					maze5 = setupOrder5.getMaze();
					break;
				}
				case 6:
				{
					//Create the order with a new skill and builder and build a maze with it
					setupOrder6 = new StubOrder();
					setupOrder6.setBuilder(Builder.Boruvka);
					setupOrder6.setSkill(skill);
					
					factorySetup.order(setupOrder6);
					
					//Wait for order to finish
					factorySetup.waitTillDelivered();
					
					maze6 = setupOrder6.getMaze();
					break;
				}
				case 7:
				{
					//Create the order with a new skill and builder and build a maze with it
					setupOrder7 = new StubOrder();
					setupOrder7.setBuilder(Builder.Boruvka);
					setupOrder7.setSkill(skill);
					
					factorySetup.order(setupOrder7);
					
					//Wait for order to finish
					factorySetup.waitTillDelivered();
					
					maze7 = setupOrder7.getMaze();
					break;
				}
				case 8:
				{
					//Create the order with a new skill and builder and build a maze with it
					setupOrder8 = new StubOrder();
					setupOrder8.setBuilder(Builder.Boruvka);
					setupOrder8.setSkill(skill);
					
					factorySetup.order(setupOrder8);
					
					//Wait for order to finish
					factorySetup.waitTillDelivered();
					
					maze8 = setupOrder8.getMaze();
					break;
				}
				case 9:
				{
					//Create the order with a new skill and builder and build a maze with it
					setupOrder9 = new StubOrder();
					setupOrder9.setBuilder(Builder.Boruvka);
					setupOrder9.setSkill(skill);
					
					factorySetup.order(setupOrder9);
					
					//Wait for order to finish
					factorySetup.waitTillDelivered();
					
					maze9 = setupOrder9.getMaze();
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
		setupOrderPerf = new StubOrder();
		setupOrderPerf.setBuilder(Builder.Boruvka);
		setupOrderPerf.setSkill(9);
		setupOrderPerf.setPerfect(true);
		factorySetup.order(setupOrderPerf);
		
		factorySetup.waitTillDelivered();
		
		perfectMaze = setupOrderPerf.getMaze();
	}
	
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
	
	private StubOrder getOrderVariable(int choice)
	{
		//Figures which maze is wanted by the test.
		switch(choice)
		{
			case 0: return setupOrder0;
			case 1: return setupOrder1;
			case 2: return setupOrder2;
			case 3: return setupOrder3;
			case 4: return setupOrder4;
			case 5: return setupOrder5;
			case 6: return setupOrder6;
			case 7: return setupOrder7;
			case 8: return setupOrder8;
			case 9: return setupOrder9;
			default: return null;
		}
	}
	
	//Overridden methods
	
	/**
     * Test Case: Check if exit is saved in maze
     * Routine being tested: order
     * 
     * Can test routine by simply seeing if exit attribute has been
     * saved.
     */
    @Test
    public void testHasExitAttribute()
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
    public void testHasStartAttribute()
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
    public void testHasExit()
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
    			if(!testFloor.hasWall(testMaze.getWidth() - 1, y, CardinalDirection.East))
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
    			if(!testFloor.hasWall(x, testMaze.getHeight() - 1, CardinalDirection.South))
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
    public void testHasOnlyOneExit()
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
    public void testAllHaveDistance()
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
    public void testExitIsMinimal()
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
    public void testStartIsMaximal()
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
    public void testPerfectMazeNoRooms()
    {
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
    public void testInternalWallCount()
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
    
    //New Methods for White Box Testing
    
    /**
     * Test Case: Check if wall weights are unique
     * Routine being tested: getEdgeWeight
     * 
     * Tests if the maze wall weighting algorithm produces
     * unique weights.
     */
    @Test
    public void testInternalWallWeight()
    {
    	//Create a Maze object to hold the currently tested maze
    	Maze testMaze;
    	StubOrder testOrder;
    	
    	//Creates a MazeBuilderBoruvka object to use the getEdgeWeight function.
    	MazeBuilderBoruvka builder = new MazeBuilderBoruvka();
    	builder.random = SingleRandom.getRandom();
    	builder.createList();
    	//Loops through each maze
    	for(int mazeNum = 0; mazeNum < 10; mazeNum++)
    	{
    		testMaze = getMazeVariable(mazeNum);
    		testOrder = getOrderVariable(mazeNum);
    		builder.floorplan = testMaze.getFloorplan();
    		builder.order = testOrder;
    		
    		//Instantiates an array that holds wall weights of every single wall.
    		int[] weights = new int[testMaze.getWidth() * testMaze.getHeight() * 4];
    		
    		//Instantiates variables that will be used through loop
    		int counter = 0;
    		Wallboard wall = new Wallboard(0, 0, CardinalDirection.North);
    		
    		//Initializes all values to 0
    		for(int weightIndex = 0; weightIndex < weights.length; weightIndex++)
    			weights[weightIndex] = 0;
    		
    		//Checks the walls of each maze
    		for(int xIndex = 0; xIndex < testMaze.getWidth(); xIndex++)
    		{
    			for(int yIndex = 0; yIndex < testMaze.getHeight(); yIndex++)
    			{
    				//Checks if southern wall exists first
    				if(testMaze.getFloorplan().hasWall(xIndex, yIndex, CardinalDirection.South))
    				{
    					//We can add every southern wall of a room with no fear of duplicating a wall.
        				wall.setLocationDirection(xIndex, yIndex, CardinalDirection.South);
    					weights[counter] = builder.getEdgeWeight(wall);
    					counter++;
    				}
    				
    				//Checks if eastern wall exists first
    				if(testMaze.getFloorplan().hasWall(xIndex, yIndex, CardinalDirection.East))
    				{
    					//We can add every eastern wall of a room with no fear of duplicating a wall.
        				wall.setLocationDirection(xIndex, yIndex, CardinalDirection.East);
        				weights[counter] = builder.getEdgeWeight(wall);
        				counter++;
    				}
    				
    				//Corner Cases
    				//Checks if northern wall exists first and we are at top row, meaning nobody shares this wall
    				if(testMaze.getFloorplan().hasWall(xIndex, yIndex, CardinalDirection.North) && yIndex == 0)
    				{
    					//We can add every eastern wall of a room with no fear of duplicating a wall.
        				wall.setLocationDirection(xIndex, yIndex, CardinalDirection.North);
        				weights[counter] = builder.getEdgeWeight(wall);
        				counter++;
    				}
    				
    				//Checks if western wall exists first and we are at left-most column, meaning nobody shares this wall
    				if(testMaze.getFloorplan().hasWall(xIndex, yIndex, CardinalDirection.West) && xIndex == 0)
    				{
    					//We can add every eastern wall of a room with no fear of duplicating a wall.
        				wall.setLocationDirection(xIndex, yIndex, CardinalDirection.West);
        				weights[counter] = builder.getEdgeWeight(wall);
        				counter++;
    				}
    			}
    		}
    		//Now that we found all weights, we check whether there are any duplicates.
    		//Sort array to make finding duplicates easier.
    		Arrays.sort(weights);
    		
    		for(int i = 0; i < weights.length; i++)
    		{
    			//Not counted, because there will be many for the destroyed walls
    			if(weights[i] == 0)
    				continue;
    			
    			if(weights[i] == weights[i + 1])
    			{
    				assertNotEquals(weights[i], weights[i+1]);
    			}
    			
    			//We break here, because this is the case of borders, which means there is no longer any weights to check.
    			if(weights[i+1] == Integer.MAX_VALUE)
    				i = weights.length;
    		}
    		
    		//Initializes all values to 0 again
    		for(int weightIndex = 0; weightIndex < weights.length; weightIndex++)
    			weights[weightIndex] = 0;
    	}
    }
    
    /**
     * Test Case: Check if wall weights are equal on both sides
     * Routine being tested: getEdgeWeight
     * 
     * Tests if the maze wall weighting algorithm produces
     * same weights for walls on other sides
     */
    @Test
    public void testWallWeights()
    {
    	//Create a Maze object to hold the currently tested maze
    	Maze testMaze;
    	StubOrder testOrder;
    	
    	//Creates a MazeBuilderBoruvka object to use the getEdgeWeight function.
    	MazeBuilderBoruvka builder = new MazeBuilderBoruvka();
    	builder.random = SingleRandom.getRandom();
    	builder.createList();
    	
    	//Loops through each maze
    	for(int mazeNum = 0; mazeNum < 10; mazeNum++)
    	{
    		//Set up all variables needed for edge weights
    		testMaze = getMazeVariable(mazeNum);
    		testOrder = getOrderVariable(mazeNum);
    		builder.floorplan = testMaze.getFloorplan();
    		builder.order = testOrder;
    		
    		//Sets up walls to test
    		Wallboard wall = new Wallboard(0, 0, CardinalDirection.North);
    		Wallboard wallNeighbor = new Wallboard(0, 0, CardinalDirection.North);
    		
    		//Loop through each wall
    		for(int xIndex = 0; xIndex < testMaze.getWidth(); xIndex++)
        	{
        		for(int yIndex = 0; yIndex < testMaze.getHeight(); yIndex++)
        		{
        			//Checks if southern wall exists and is not a border
        			wall.setLocationDirection(xIndex, yIndex, CardinalDirection.South);
    				if(testMaze.getFloorplan().hasWall(xIndex, yIndex, CardinalDirection.South) && !testMaze.getFloorplan().isPartOfBorder(wall))
    				{
    					wallNeighbor.setLocationDirection(xIndex, yIndex + 1, CardinalDirection.North);
    					assertEquals(builder.getEdgeWeight(wall), builder.getEdgeWeight(wallNeighbor));
    				}
    				
    				//Checks if eastern wall exists and is not a border
    				wall.setLocationDirection(xIndex, yIndex, CardinalDirection.East);
    				if(testMaze.getFloorplan().hasWall(xIndex, yIndex, CardinalDirection.East) && !testMaze.getFloorplan().isPartOfBorder(wall))
    				{
    					wallNeighbor.setLocationDirection(xIndex + 1, yIndex, CardinalDirection.West);
    					assertEquals(builder.getEdgeWeight(wall), builder.getEdgeWeight(wallNeighbor));
    				}
    				
    				//Checks if northern wall exists and is not a border
    				wall.setLocationDirection(xIndex, yIndex, CardinalDirection.North);
    				if(testMaze.getFloorplan().hasWall(xIndex, yIndex, CardinalDirection.North) && !testMaze.getFloorplan().isPartOfBorder(wall))
    				{
    					wallNeighbor.setLocationDirection(xIndex, yIndex - 1, CardinalDirection.South);
    					assertEquals(builder.getEdgeWeight(wall), builder.getEdgeWeight(wallNeighbor));
    				}
    				
    				//Checks if western wall exists and is not a border
    				wall.setLocationDirection(xIndex, yIndex, CardinalDirection.West);
    				if(testMaze.getFloorplan().hasWall(xIndex, yIndex, CardinalDirection.West) && !testMaze.getFloorplan().isPartOfBorder(wall))
    				{
    					wallNeighbor.setLocationDirection(xIndex - 1, yIndex, CardinalDirection.East);
    					assertEquals(builder.getEdgeWeight(wall), builder.getEdgeWeight(wallNeighbor));
    				}
        		}
        	}
    	}
    }
    
    /**
     * Test Case: Check if wall weights are equal when called multiple times
     * Routine being tested: getEdgeWeight
     * 
     * Tests if the maze wall weighting algorithm produces
     * same weights one wall over and over again
     */
    @Test
    public void testWallWeightTwice()
    {
    	//Create a Maze object to hold the currently tested maze
    	Maze testMaze;
    	StubOrder testOrder;
    	
    	//Creates a MazeBuilderBoruvka object to use the getEdgeWeight function.
    	MazeBuilderBoruvka builder = new MazeBuilderBoruvka();
    	builder.random = SingleRandom.getRandom();
    	builder.createList();
    	
    	//Loops through each maze
    	for(int mazeNum = 0; mazeNum < 10; mazeNum++)
    	{
    		//Set up all variables needed for edge weights
    		testMaze = getMazeVariable(mazeNum);
    		testOrder = getOrderVariable(mazeNum);
    		builder.floorplan = testMaze.getFloorplan();
    		builder.order = testOrder;
    		
    		//Sets up walls to test
    		Wallboard wall = new Wallboard(0, 0, CardinalDirection.North);
    		
    		//Loop through each wall
    		for(int xIndex = 0; xIndex < testMaze.getWidth(); xIndex++)
        	{
        		for(int yIndex = 0; yIndex < testMaze.getHeight(); yIndex++)
        		{
        			//Checks if southern wall exists and is not a border
        			wall.setLocationDirection(xIndex, yIndex, CardinalDirection.South);
    				if(testMaze.getFloorplan().hasWall(xIndex, yIndex, CardinalDirection.South) && !testMaze.getFloorplan().isPartOfBorder(wall))
    				{
    					assertEquals(builder.getEdgeWeight(wall), builder.getEdgeWeight(wall));
    				}
    				
    				//Checks if eastern wall exists and is not a border
    				wall.setLocationDirection(xIndex, yIndex, CardinalDirection.East);
    				if(testMaze.getFloorplan().hasWall(xIndex, yIndex, CardinalDirection.East) && !testMaze.getFloorplan().isPartOfBorder(wall))
    				{
    					assertEquals(builder.getEdgeWeight(wall), builder.getEdgeWeight(wall));
    				}
    				
    				//Checks if northern wall exists and is not a border
    				wall.setLocationDirection(xIndex, yIndex, CardinalDirection.North);
    				if(testMaze.getFloorplan().hasWall(xIndex, yIndex, CardinalDirection.North) && !testMaze.getFloorplan().isPartOfBorder(wall))
    				{
    					assertEquals(builder.getEdgeWeight(wall), builder.getEdgeWeight(wall));
    				}
    				
    				//Checks if western wall exists and is not a border
    				wall.setLocationDirection(xIndex, yIndex, CardinalDirection.West);
    				if(testMaze.getFloorplan().hasWall(xIndex, yIndex, CardinalDirection.West) && !testMaze.getFloorplan().isPartOfBorder(wall))
    				{
    					assertEquals(builder.getEdgeWeight(wall), builder.getEdgeWeight(wall));
    				}
        		}
        	}
    	}
    }
    
    /**
     * Test Case: Check how builder handles cancelling an order
     * Routine being tested: order, dbg
     * 
     * Tests if maze building handles errors that we expect when we halt
     * the building process.
     * Tests no exceptions are thrown, meaning it is handled correctly 
     */
    @Test(expected = Test.None.class)
    public void testInterruption() throws IOException
    {
    	//Creates an order for a maze
    	StubOrder testOrder = new StubOrder();
    	testOrder.setSeed(9);
    	testOrder.setBuilder(Builder.Boruvka);
    	
    	factorySetup.order(setupOrder0);
    	
    	//Cancels order and asserts correct assertion gets thrown.
    	factorySetup.cancel();
    }
}
