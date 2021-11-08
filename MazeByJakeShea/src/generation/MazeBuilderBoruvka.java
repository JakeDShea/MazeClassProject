package generation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class MazeBuilderBoruvka extends MazeBuilder implements Runnable
{
	//Create list of 1000000 random numbers for seed and randomization purposes
	int[] seedVals = new int[1000000];
	
	public MazeBuilderBoruvka() {
		super();
		System.out.println("MazeBuilderBoruvka uses Boruvka's algorithm to generate maze.");
	}
	
	/**
	 * Precondition: A wallboard with an x-coordinate, y-coordinate,
	 * and direction.
	 * Postcondition: An integer variable specifically created for
	 * the wallboard.
	 * @param w is the wallboard that we want to find a weight for
	 * @return the unique weight of the wallboard
	 */
	public int getEdgeWeight(Wallboard w)
	{
		int wallX = w.getX();
		int wallY = w.getY();
		int weight = 0;
		
		//If wallboard is an external wall, simply return maximum integer.
		if(floorplan.isPartOfBorder(w))
			return Integer.MAX_VALUE;
		
		//First, check which direction wallboard w contains.
		//This is important as wallboard with attributes 12, 35, East
		//should return the same integer value as a wallboard with attributes 13, 35, West
		//Save values of x and y in wallboard w in two integer variables X and Y
		//To standardize similar wallboards, if direction is west, subtract 1 from X
		//If direction is north, subtract one from Y
		if(Arrays.equals(w.getDirection().getDirection(), CardinalDirection.North.getDirection()))
			wallY--;
		else if(Arrays.equals(w.getDirection().getDirection(), CardinalDirection.West.getDirection()))
			wallX--;
		
		//Adds a slight distinction between close values
		if(Arrays.equals(w.getDirection().getDirection(), CardinalDirection.North.getDirection()) ||
				Arrays.equals(w.getDirection().getDirection(), CardinalDirection.South.getDirection()))
			weight = seedVals[(order.getSeed() + 1)] % 4 + 1;
		
		//weight = seedVals2[(653 * wallX * wallY + (order.getSeed() + 1)) % seedVals2.length] + seedVals2[(113 * (wallX)) % seedVals2.length] +
		//		seedVals2[(173 * wallY) % seedVals2.length] + seedVals2[(717 * (wallX * wallY) + wallY) % seedVals2.length];
		
		weight += seedVals[(6053 * wallX * wallY + (order.getSeed() + 1)) % seedVals.length] + seedVals[(1013 * (wallX)) % seedVals.length] +
				seedVals[(1773 * wallY) % seedVals.length] + seedVals[(7177 * (wallX * wallY) + wallY) % seedVals.length];
		
		//return said integer.
		return weight;
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
		createList();
		//Create an ArrayList that should be used to hold ArrayLists that hold integer arrays for
		//coordinates.
		ArrayList<ArrayList<int[]>> tree = new ArrayList<ArrayList<int[]>>();
		ArrayList<int[]> coor = new ArrayList<int[]>();
		int[] coordinates = new int[2];
		
		//Set up compartmentalized array, which will be useful when creating all the components at the beginning.
		boolean[][] compartmentalized = new boolean[width][height];
		for(int i = 0; i < compartmentalized.length; i++)
			for(int j = 0; j < compartmentalized[i].length; j++)
				compartmentalized[i][j] = false; 
		
		//Sets up mergable array, which will be useful for keep track of where components merge
		// This works by saving two pairs of coordinates per row, and finds the wall between them
		int[][] mergable = new int[width*height][4];
		for(int i = 0; i < mergable.length; i++)
			for(int j = 0; j < mergable[i].length; j++)
				mergable[i][j] = -1; 
		
		// Gets ready to find a minimum value 
		int minWeight = Integer.MAX_VALUE;
		Wallboard minWallboard = new Wallboard(0, 0, CardinalDirection.North);
		
		//Loop through whole maze and add each pair of coordinates to an ArrayList.
		//Add these ArrayLists to the large ArrayList.
		//The smaller ArrayLists are the "components" that one needs in Boruvka's method.
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				//Does a subprocedure to create an entire component out of a room if in a room
				coordinates[0] = x;
				coordinates[1] = y;
				
				//Checks if in room and not yet created into a component
				if(floorplan.isInRoom(x, y) && !compartmentalized[x][y])
					tree.add(deepCopy(roomComponent(tree, compartmentalized, x, y)));
				else if(!floorplan.isInRoom(x, y) && !compartmentalized[x][y])
				{
					// Uses deepcopying to get rid of any pesky memory issues
					coor.add(deepCopy(coordinates));
					
					// Sets that the current cell we are on has been put into some component
					compartmentalized[x][y] = true; 
					
					//Adds coordinate into main ArrayList
					tree.add(deepCopy(coor));
					
					//Removes value from coor to keep each pair of coordinates separate.
					coor.clear();
				}
			}
		}
		
		int tempX = 0;
		int tempY = 0;
		Wallboard tempWall = new Wallboard(0, 0, CardinalDirection.North);
		
		//Run through all the component ArrayLists, and check the weights of all the wallboards
		//near them.
		while(tree.size() > 1)
		{
			// This will run through each component
			for(int i = 0; i < tree.size(); i++)
			{
				// This will run through each value in the current component
				for(int j = 0; j < tree.get(i).size(); j++)
				{
					//Keep a running total of the minimum wallboard weight and the wallboard associated with it.
					//Once you finish, save the wallboard and the coordinates associated with it for merging purposes.
					
					//Gets the cell being tested
					tempX = tree.get(i).get(j)[0];
					tempY = tree.get(i).get(j)[1];
					
					//Goes through all possible directions to find smallest wall weight
					if(floorplan.hasWall(tempX, tempY, CardinalDirection.North))
					{
						//Checks that cell is not already in same component
						if(!inSameComponent(tree, tempX, tempY, tempX, tempY - 1))
							{
							tempWall.setLocationDirection(tempX, tempY, CardinalDirection.North);
							if(getEdgeWeight(tempWall) < minWeight)
							{
								minWeight = getEdgeWeight(tempWall);
								minWallboard.setLocationDirection(tempWall.getX(), tempWall.getY(), tempWall.getDirection());
							}
						}
					}
					
					if(floorplan.hasWall(tempX, tempY, CardinalDirection.East))
					{
						//Checks that cell is not already in same component
						if(!inSameComponent(tree, tempX, tempY, tempX + 1, tempY))
						{
							tempWall.setLocationDirection(tempX, tempY, CardinalDirection.East);
							if(getEdgeWeight(tempWall) < minWeight)
							{
								minWeight = getEdgeWeight(tempWall);
								minWallboard.setLocationDirection(tempWall.getX(), tempWall.getY(), tempWall.getDirection());
							}
						}
					}
					
					if(floorplan.hasWall(tempX, tempY, CardinalDirection.South))
					{
						//Checks that cell is not already in same component
						if(!inSameComponent(tree, tempX, tempY, tempX, tempY + 1))
						{
							tempWall.setLocationDirection(tempX, tempY, CardinalDirection.South);
							if(getEdgeWeight(tempWall) < minWeight)
							{
								minWeight = getEdgeWeight(tempWall);
								minWallboard.setLocationDirection(tempWall.getX(), tempWall.getY(), tempWall.getDirection());
							}
						}
					}
					
					if(floorplan.hasWall(tempX, tempY, CardinalDirection.West))
					{
						//Checks that cell is not already in same component
						if(!inSameComponent(tree, tempX, tempY, tempX - 1, tempY))
						{
							tempWall.setLocationDirection(tempX, tempY, CardinalDirection.West);
							if(getEdgeWeight(tempWall) < minWeight)
							{
								minWeight = getEdgeWeight(tempWall);
								minWallboard.setLocationDirection(tempWall.getX(), tempWall.getY(), tempWall.getDirection());
							}
						}
					}
				}
				
				//Gets the first two coordinates
				mergable[i][0] = minWallboard.getX();
				mergable[i][1] = minWallboard.getY();
			
				//Gets the next two coordinates based off the wallboards direction
				//Change these to use the arrays in CardinalDirection
				if(Arrays.equals(minWallboard.getDirection().getDirection(), CardinalDirection.North.getDirection()))
				{
					mergable[i][2] = minWallboard.getX();
					mergable[i][3] = minWallboard.getY() - 1;
				}
				else if(Arrays.equals(minWallboard.getDirection().getDirection(), CardinalDirection.East.getDirection()))
				{
					mergable[i][2] = minWallboard.getX() + 1;
					mergable[i][3] = minWallboard.getY();
				}
				else if(Arrays.equals(minWallboard.getDirection().getDirection(), CardinalDirection.South.getDirection()))
				{
					mergable[i][2] = minWallboard.getX();
					mergable[i][3] = minWallboard.getY() + 1;
				}
				else
				{
					mergable[i][2] = minWallboard.getX() - 1;
					mergable[i][3] = minWallboard.getY();
				}
			
				//Resets minWeight and minWallboard
				minWeight = Integer.MAX_VALUE;
				minWallboard.setLocationDirection(0, 0, CardinalDirection.North);
			
			}
		
			//Once all components have been taken care of, delete the saved wallboards and merge the components
			//based on the coordinates you saved, finding which components contain which coordinates.
			int[] tempCoordinate1 = new int[2];
			int[] tempCoordinate2 = new int[2];
			int index1 = 0;
			int index2 = 0;
			for(int i = 0; i < width * height; i++)
			{
				if(mergable[i][0] == -1)
					break;
			
				//Sets up the two merging coordinates
				tempCoordinate1[0] = mergable[i][0];
				tempCoordinate1[1] = mergable[i][1];
				tempCoordinate2[0] = mergable[i][2];
				tempCoordinate2[1] = mergable[i][3];
			
				//Removes freshly used indices
				mergable[i][0] = -1;
				mergable[i][1] = -1;
				mergable[i][2] = -1;
				mergable[i][3] = -1;
				
				//Figures out where the wallboard being removed is
				if(tempCoordinate1[0] < tempCoordinate2[0])
					minWallboard.setLocationDirection(tempCoordinate1[0], tempCoordinate1[1], CardinalDirection.East);
				else if(tempCoordinate1[0] > tempCoordinate2[0])
					minWallboard.setLocationDirection(tempCoordinate1[0], tempCoordinate1[1], CardinalDirection.West);
				else if(tempCoordinate1[1] < tempCoordinate2[1])
					minWallboard.setLocationDirection(tempCoordinate1[0], tempCoordinate1[1], CardinalDirection.South);
				else 
					minWallboard.setLocationDirection(tempCoordinate1[0], tempCoordinate1[1], CardinalDirection.North);
			
				//Checks if merging into same component after some merges took place
				if(inSameComponent(tree, tempCoordinate1[0], tempCoordinate1[1], tempCoordinate2[0], tempCoordinate2[1]))
				{
					continue;
				}
				
				if (floorplan.canTearDown(minWallboard))
					floorplan.deleteWallboard(minWallboard);
				
				//Finds the two components that just merged.
				for(int j = 0; j < tree.size(); j++)
				{
					for(int k = 0; k < tree.get(j).size(); k++)
					{
						if(Arrays.equals(tree.get(j).get(k), tempCoordinate1))
							index1 = j;
						if(Arrays.equals(tree.get(j).get(k), tempCoordinate2))
							index2 = j;
					}
				}
				
				//Sets up tree to be correct number of components
				//Also checks if indices aren't the same, meaning a component that has been combined already
				if(index1 != index2)
				{
					if(index1 < index2)
					{
						if(index1 != tree.size())
						{
							tree.add(index1, mergeComponents(tree.get(index1), tree.get(index2)));
							tree.remove(index1 + 1);
							tree.remove(index2);
						}
					}
					else
					{
						tree.add(index2, mergeComponents(tree.get(index1), tree.get(index2)));
						tree.remove(index2 + 1);
						tree.remove(index1);
					}
					
				}
				
				index1 = 0;
				index2 = 0;
			}
			
		}
		//Repeat this until there is only one ArrayList left in the large ArrayList.
	}
	
	/**
	 * Merges two components into a sorted new component
	 * 
	 * @param first the first component we are dealing with
	 * @param second the second component that we want to merge into with the first
	 * @return Returns a sorted ArrayList that contains all objects from the two parameters
	 */
	private ArrayList<int[]> mergeComponents(ArrayList<int[]> first, ArrayList<int[]> second)
	{
		ArrayList<int[]> newComponent = new ArrayList<int[]>();
		int early = 0;
		
		//Uses an ordering system to keep components ordered well.
		while(!first.isEmpty() && !second.isEmpty())
		{
			early = sortArrayList(first, second);
			
			if(early == 1)
			{
				newComponent.add(first.get(0));
				first.remove(0);
			}
			else
			{
				newComponent.add(second.get(0));
				second.remove(0);
			}
		}
		
		//Loop finished, meaning one component is empty, but must check other still.
		while(!first.isEmpty())
		{
			newComponent.add(first.get(0));
			first.remove(0);
		}
		
		while(!second.isEmpty())
		{
			newComponent.add(second.get(0));
			second.remove(0);
		}
		
		//Return the new, larger component.
		return newComponent;
	}
	
	/**
	 * Creates a deep copy of an ArrayList
	 * 
	 * @param aList the ArrayList we plan on deep copying
	 * @return A new copy of the original ArrayList that should have a different memory address
	 */
	private ArrayList<int[]> deepCopy(ArrayList<int[]> aList)
	{
		//Creates copy to return
		ArrayList<int[]> copiedArrayList = new ArrayList<int[]>();
		
		//Uses iterator to move through ArrayList and deep copy values to new returned value.
		Iterator<int[]> iter = aList.iterator();
		while (iter.hasNext())
		{
			int[] arr = iter.next();
			int[] newArr = {arr[0], arr[1]};
			copiedArrayList.add(newArr);
		}
		
		return copiedArrayList;
	}
	
	/**
	 * Creates a deep copy of an array
	 * 
	 * @param arr the array we plan on deep copying
	 * @return A new copy of the original array that should have a different memory address
	 */
	private int[] deepCopy(int[] arr)
	{
		//Creates copy to return
		int[] copiedArray = new int[arr.length];
		
		copiedArray[0] = arr[0];
		copiedArray[1] = arr[1];
		
		return copiedArray;
	}
	
	/**
	 * A private helper method to see whether the
	 * two cells are in the same component or not.
	 * 
	 * @param compGroup The main ArrayList that holds all components
	 * @param xCord1 the x-coordinate of the first cell we check
	 * @param yCord1 the y-coordinate of the first cell we check
	 * @param xCord2 the x-coordinate of the second cell we check
	 * @param yCord2 the y-coordinate of the second cell we check
	 * @return a boolean value, true if the two cells are in the same component, false otherwise
	 */
	private boolean inSameComponent(ArrayList<ArrayList<int[]>> compGroup, int xCord1, int yCord1, int xCord2, int yCord2)
	{
		boolean foundInComponent = false;
		
		for(int i = 0; i < compGroup.size() && !foundInComponent; i++)
		{
			for(int j = 0; j < compGroup.get(i).size() && !foundInComponent; j++)
			{
				//Finds if the x and y coordinates are in a component
				if(xCord1 == compGroup.get(i).get(j)[0] && yCord1 == compGroup.get(i).get(j)[1])
				{
					//Sees if other pair is in the same component
					for(int k = 0; k < compGroup.get(i).size(); k++)
					{
						if(xCord2 == compGroup.get(i).get(k)[0] && yCord2 == compGroup.get(i).get(k)[1])
						{
							foundInComponent = true;
							break;
						}
					}
				}
			}
		}
		
		return foundInComponent;
	}
	
	/**
	 * Method to create components out of rooms and arbitrarily
	 * combine it with cells near it not in rooms using the seed
	 * to allow for Boruvka's algorithm to work
	 * 
	 * @param compGroup The main ArrayList that holds all components
	 * @param compMap a boolean map to say whether a cell is in a component or not already
	 * @param xCord the x-coordinate of the first cell we are checking
	 * @param yCord the y-coordinate of the first cell we are checking
	 * @return a component that makes up the entire room of the maze we currently are in
	 */
	private ArrayList<int[]> roomComponent(ArrayList<ArrayList<int[]>> compGroup, boolean[][]compMap, int xCord, int yCord)
	{
		//Sets up coordinates
		int x = xCord;
		int y = yCord;
		
		//Sets up return value
		ArrayList<int[]> component = new ArrayList<int[]>();
		ArrayList<int[]> compForSorting = new ArrayList<int[]>();
		int[] coordinates = new int[2];
		
		while(floorplan.isInRoom(x, y))
		{
			coordinates[0] = x;
			coordinates[1] = y;
			
			//Creates an ordered component
			compForSorting.add(deepCopy(coordinates));
			component = mergeComponents(deepCopy(component), deepCopy(compForSorting));
			compForSorting.remove(0);
			
			compMap[x][y]= true; 
			
			x++;
			
			//Checks if iteration has left room through right wall
			if(!floorplan.isInRoom(x, y))
			{
				x = xCord;
				y++;
			}
		}
		return component;
	}
	
	/**
	 * Method meant for sorting the components quickly
	 * returns a 1 for first array, 2 for second
	 * 
	 * @param first the first ArrayList we are checking
	 * @param second the second ArrayList we are checking
	 * @return a 1 for if the first ArrayList comes first in a sort, 2 otherwise.
	 */
	private int sortArrayList(ArrayList<int[]> first, ArrayList<int[]> second)
	{
		// Checks if first ArrayList will be first in the sort
		if(first.get(0)[0] <= second.get(0)[0])
		{
			// Checks a corner case where both values are equal and we don't know which is first yet
			if(first.get(0)[0] == second.get(0)[0])
			{
				// If in this case, we arbitrarily choose this ArrayList for the sort
				if(first.get(0)[1] <= second.get(0)[1])
				{
					return 1;
				}
				// The second ArrayList must be first in the sort
				else
				{
					return 2;
				}
			}
			// First ArrayList must be first in the sort
			else
			{
				return 1;
			}
		}
		// Second ArrayList must be first in the sort
		else
		{
			return 2;
		}
	}
	
	/**
	 * Method useds to randomize maze wall weights. Creates
	 * a sufficiently large array of random integers.
	 */
	protected void createList()
	{
		for(int i = 0; i < 1000000; i++)
		{
			// Creates a list for edge weights for the walls via a seed
			seedVals[i] = random.nextIntWithinInterval(0, 1000000);
		}
	}
}