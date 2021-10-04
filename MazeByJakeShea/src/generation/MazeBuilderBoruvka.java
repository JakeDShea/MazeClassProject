package generation;

import java.util.ArrayList;

public class MazeBuilderBoruvka extends MazeBuilder
{
	//Create list of 1000 random numbers for seed and randomization purposes
	
	public MazeBuilderBoruvka() {
		super();
		System.out.println("MazeBuilderPrim uses Boruvka's algorithm to generate maze.");
	}
	
	/**
	 * Precondition: A wallboard with an x-coordinate, y-coordinate,
	 * and direction.
	 * Postcondition: An integer variable specifically created for
	 * the wallboard.
	 * @param w
	 * @return
	 */
	public int getEdgeWeight(Wallboard w)
	{
		//If wallboard is an external wall, simply return maximum integer.
		
		//First, check which direction wallboard w contains.
		//This is important as wallboard with attributes 12, 35, East
		//should return the same integer value as a wallboard with attributes 13, 35, West
		
		//Save values of x and y in wallboard w in two integer variables X and Y
		//To standardize similar wallboards, if direction is west, subtract 1 from X
		//If direction is north, subtract one from Y
		
		//Use seed to get value from 1000 size int array, and use it in a function to create
		//a "random" integer.
		
		//return said integer.
		return 0;
	}
	
	/**
	 * Uses Boruvka's method to create a maze. Uses an ArrayList
	 * to hold all the other components, which are held in their
	 * own specific ArrayLists.
	 * 
	 * First puts every value into their own component ArrayList
	 * then slowly starts breaking walls created larger and larger
	 * components, until eventually the entire maze is accounted for.
	 */
	@Override
	protected void generatePathways()
	{
		//Create an ArrayList that should be used to hold ArrayLists that hold integer arrays for
		//coordinates.
		
		//Loop through whole maze and add each pair of coordinates to an ArrayList.
		//Add these ArrayLists to the large ArrayList.
		//The smaller ArrayLists are the "components" that one needs in Boruvka's method.
		
		//Run through all the component ArrayLists, and check the weights of all the wallboards
		//near them.
		//Keep a running total of the minimum wallboard weight and the wallboard associated with it.
		//Once you finish, save the wallboard and the coordinates associated with it for merging purposes.
		
		//Once all components have been taken care of, delete the saved wallboards and merge the components
		//based on the coordinates you saved, finding which components contain which coordinates.
		
		//Repeat this until there is only one ArrayList left in the large ArrayList.
	}
}
