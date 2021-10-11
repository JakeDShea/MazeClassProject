package generation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import generation.Order.Builder;

public class MazeBuilderPrimTest
{
	/**
     * Test Case: Check if a maze is perfect when it should be
     * Routine being tested: generate
     * 
     * Can test routine by simply seeing if exit attribute has been
     * saved.
     */
    @Test
    public void testBrokenSeed()
    {
    	//Use this int as a seed because it leads to a broken maze
    	int seed = -1413251252;
    	
    	//Set up a maze and an order to go with it
    	Maze primMaze;
    	StubOrder order = new StubOrder();
    	
    	order.setBuilder(Builder.Prim);
    	order.setSkill(0);
    	order.setSeed(seed);
    	order.setPerfect(true);
    	
    	//Set up a factory to order and create the maze we want
    	MazeFactory factory = new MazeFactory();
    	factory.order(order);
    	
    	//Wait for order to finish
		factory.waitTillDelivered();
		
		primMaze = order.getMaze();
		
		//Count how many walls are present
		int wallCount = 0;
		
		//Count all internal walls within maze
    	for(int x = 0; x < primMaze.getWidth(); x++)
    		for(int y = 0; y < primMaze.getHeight(); y++)
    		{
    			//Checks any edge cases for counting wall, specifically the bottom row and the right column
    			if((x == primMaze.getWidth() - 1) && (y == primMaze.getHeight() - 1))
    				wallCount += 0;
    			else if (x == primMaze.getWidth() - 1)
    			{
    				if(primMaze.getFloorplan().hasWall(x, y, CardinalDirection.South))
    					wallCount++;
    			}
    			else if(y == primMaze.getHeight() - 1)
    			{
    				if(primMaze.getFloorplan().hasWall(x, y, CardinalDirection.East))
    					wallCount++;
    			}
    			else
    			{
    				if(primMaze.getFloorplan().hasWall(x, y, CardinalDirection.East))
    					wallCount++;
    				
    				if(primMaze.getFloorplan().hasWall(x, y, CardinalDirection.South))
    					wallCount++;
				}
    		}
    	
    	//This assert will fail because the number of walls is one less than it actually should be.
    	//This happens during the occasion where Prim's algorithm creates a loop of removed walls, which
    	//can only happen when the first pair of indices gets looped back to each other.
    	//A simple fix would be to simply mark the randomly chosen x and y coordinates at the beginning as seen
    	// by using "floorplan.setCellAsVisited(x, y);" right after choosing the x and y.
    	//Then this test will pass, and the algorithm will correctly work again.
		assertEquals(wallCount, (((primMaze.getWidth() + 1) * primMaze.getHeight()) + (primMaze.getWidth() * (primMaze.getHeight() + 1)) -
    			(2 * (primMaze.getWidth() + primMaze.getHeight())) - (primMaze.getWidth() * primMaze.getHeight() - 1)));
		
		//I have also put the line above in the MazeBuilderPrim.java class in a comment to easily test this assertion
		//if you so choose to.
    }
}
