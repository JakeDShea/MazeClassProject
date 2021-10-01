package generation;

import static org.junit.Assert.*;

import org.junit.Test;

import generation.MazeBuilder;

public class MazeFactoryTest
{   
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
    	//Create an order variable with default builder for a maze
    	//Create a boolean flag to see if order worked or not.
    	
    	//Use order as parameter for factory's order function.
    	//Set boolean flag equal to the function call.
    	
    	//Wait until building thread terminates
    	
    	//Assert that boolean flag is true.
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
    	//Create an order variable with Prim builder for a maze
    	//Create a boolean flag to see if order worked or not.
    	
    	//Use order as parameter for factory's order function.
    	//Set boolean flag equal to the function call.
    	
    	//Wait until building thread terminates
    	
    	//Assert that boolean flag is true.
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
    	//Create an order variable with Boruvka builder for a maze
    	//Create a boolean flag to see if order worked or not.
    	
    	//Use order as parameter for factory's order function.
    	//Set boolean flag equal to the function call.
    	
    	//Wait until building thread terminates
    	
    	//Assert that boolean flag is true.
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
    	//Create an order variable with Eller builder for a maze
    	//Create a boolean flag to see if order worked or not.
    	
    	//Use order as parameter for factory's order function.
    	//Set boolean flag equal to the function call.
    	
    	//Wait until building thread terminates
    	
    	//Assert that boolean flag is false.
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
    	//Create an order variable with Kruskal builder for a maze
    	//Create a boolean flag to see if order worked or not.
    	
    	//Use order as parameter for factory's order function.
    	//Set boolean flag equal to the function call.
    	
    	//Wait until building thread terminates
    	
    	//Assert that boolean flag is false.
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
    	//Create a MazeFactory object to interact with
    	//Create an order variable with Kruskal builder for a maze
    	//Create a boolean flag to see if order worked or not.
    	
    	//Use order as parameter for factory's order function.
    	//Wait until building thread terminates
    	
    	//Assert that getExitPosition in dist attribute in MazeBuilder is not none.
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
    	//Create a MazeFactory object to interact with
    	//Create an order variable with Kruskal builder for a maze
    	//Create a boolean flag to see if order worked or not.
    	
    	//Use order as parameter for factory's order function.
    	//Wait until building thread terminates
    	
    	//Assert that getStartPosition in dist attribute in MazeBuilder is not none.
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
    	//Create a MazeFactory object to interact with
    	//Create an order variable with default builder for a maze
    	//Create a boolean flag set to false to see if an exit has been found
    	
    	//Use order as parameter for factory's order function.
    	//Wait until building thread terminates
    	
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
    	//Create a MazeFactory object to interact with
    	//Create an order variable with default builder for a maze
    	//Create a counter variable to check how many external openings there are
    	
    	//Use order as parameter for factory's order function.
    	//Wait until building thread terminates
    	
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
    	//Create a MazeFactory object to interact with
    	//Create an order variable with default builder for a maze
    	
    	//Use order as parameter for factory's order function.
    	//Wait until building thread terminates
    	
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
    	//Create a MazeFactory object to interact with
    	//Create an order variable with default builder for a maze
    	
    	//Use order as parameter for factory's order function.
    	//Wait until building thread terminates
    	
    	//Create int array with two values to hold exit position.
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
    	//Create a MazeFactory object to interact with
    	//Create an order variable with default builder for a maze
    	
    	//Use order as parameter for factory's order function.
    	//Wait until building thread terminates
    	
    	//Create int array with two values to hold starting position.
    	//Use dist's getStartPosition() function and put it into int array
    	
    	//Iterate through every value in dist attribute in builder attribute in MazeFactory object.
    	//Assert that value at exit position is greater than or equal to every other value.
    	//Is allowed to be equal because there may be another place that is equally far,
    	//only exit can there be one
    }
    
    /**
     * Test Case: Check that maze is categorized as perfect when it should be
     * Routine being tested: generate
     * 
     * After a maze has been created, check if it has any rooms.
     * Then, check whether or not it is perfect. Either it has
     * rooms and is not perfect, or it has no rooms and therefore
     * is perfect. Anything else is a bug.
     */
    @Test
    public final void testPerfection()
    {
    	//Create a MazeFactory object to interact with
    	//Create an order variable with default builder for a maze
    	
    	//Use order as parameter for factory's order function.
    	//Wait until building thread terminates
    	
    	//Check if the order is perfect
    	//If so, assert that the rooms attribute in the MazeBuilder equals 0
    	
    	//Else, check if the order is not perfect.
    	//If so, check that the rooms attribute does not equal 0.
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
    	//Create an order variable with Kruskal builder for a maze
    	//Create a maze-sized matrix for boolean values.
    	//Create boolean flag for existence of rooms set to false.
    	
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
}
