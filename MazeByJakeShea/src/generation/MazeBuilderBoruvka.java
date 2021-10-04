package generation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class MazeBuilderBoruvka extends MazeBuilder implements Runnable
{
	//Create list of 1000 random numbers for seed and randomization purposes
	int[] seedVals = {1033, 4723, 4661, 2960, 9237, 5810, 8117, 7937, 6134, 9268, 9861, 3116, 7864, 2553, 977, 2029, 3298, 1976, 1837, 3232, 3113, 5724, 9238, 1356, 594, 8480, 8218, 420, 3941, 9586, 3811, 8437, 5885, 4518, 2830, 9852, 2539, 506, 9834, 7144, 2410, 9281, 636, 1367, 6302, 4687, 8784, 2384, 1174, 7056, 889, 6716, 4768, 229, 7225, 8417, 3323, 9164, 3437, 1084, 1080, 5130, 9168, 6027, 4426, 7672, 2624, 8005, 8556, 7272, 6381, 1543, 8642, 3314, 413, 7883, 3847, 5167, 3868, 7702, 2197, 9697, 7569, 1727, 3508, 797, 5388, 7287, 9721, 6534, 8540, 8634, 4464, 1415, 7165, 9264, 9701, 5757, 7790, 4591, 7419, 5695, 2688, 2369, 5135, 5035, 6347, 8654, 1143, 398, 2907, 6822, 7156, 2687, 9735, 5661, 6503, 3320, 6402, 8483, 4016, 5826, 4634, 5151, 2177, 1501, 7614, 8839, 8161, 7465, 5261, 9935, 4844, 1875, 9881, 8283, 1128, 6559, 6251, 7123, 1042, 5215, 7056, 4962, 5591, 3613, 1152, 7083, 6271, 59, 3759, 6281, 9107, 6308, 781, 8128, 9487, 2142, 5288, 2453, 7314, 1119, 5057, 1159, 3987, 7433, 9756, 5961, 6542, 6564, 8337, 1118, 8972, 6086, 2543, 6239, 8801, 4785, 8992, 3995, 9454, 7769, 8088, 5612, 4310, 9216, 9063, 6258, 7710, 2992, 1022, 8558, 8734, 2117, 9086, 5069, 8500, 4165, 3931, 575, 9288, 3564, 4368, 1955, 5625, 6424, 5853, 2401, 8310, 190, 4795, 7923, 2441, 631, 5535, 705, 7534, 5418, 6685, 887, 2530, 8695, 2916, 4662, 8375, 4669, 7831, 7876, 7923, 1449, 1388, 8928, 1543, 7821, 9805, 5654, 4605, 5448, 3692, 4203, 6203, 7415, 9636, 5494, 1045, 7656, 6266, 9867, 4783, 4374, 4211, 3333, 1839, 5574, 9582, 3520, 5905, 5901, 6754, 9696, 756, 124, 6210, 5057, 5106, 2057, 7357, 3500, 2269, 8091, 3219, 770, 9844, 4721, 2752, 424, 7829, 3008, 557, 2015, 5036, 3768, 8940, 9743, 4922, 2033, 5505, 6592, 6753, 7639, 8945, 1173, 683, 3695, 7362, 4837, 8555, 8101, 225, 5783, 8059, 7764, 7859, 6057, 3060, 6849, 8019, 6653, 9980, 9044, 410, 7585, 4218, 2323, 9021, 1690, 7509, 5437, 6432, 9980, 1402, 1632, 1833, 8647, 476, 616, 431, 7778, 6182, 453, 7008, 8413, 7036, 983, 7858, 4374, 9008, 8553, 9297, 3642, 2000, 5911, 5127, 3765, 9132, 5053, 810, 792, 9125, 5405, 1472, 3030, 6465, 6108, 5677, 6859, 2699, 4714, 589, 393, 6040, 1309, 8045, 2705, 8308, 6617, 195, 1114, 4907, 3358, 110, 2001, 9875, 221, 6983, 9774, 4951, 8040, 2503, 8126, 9822, 6294, 3459, 3074, 3946, 4687, 9891, 196, 9037, 4922, 1040, 417, 1508, 2513, 7067, 7223, 3896, 8877, 7690, 668, 9402, 8148, 5709, 3048, 75, 5691, 8975, 2689, 2798, 9305, 8312, 9425, 277, 8923, 9679, 6486, 6743, 9597, 3597, 4181, 6420, 6811, 3629, 828, 8335, 7077, 1599, 2675, 8906, 2232, 4779, 7774, 2736, 271, 419, 4098, 7323, 4741, 8210, 9665, 5350, 149, 544, 1540, 8444, 4064, 4375, 9508, 8831, 8874, 6976, 2804, 2597, 594, 8711, 5535, 5325, 7628, 5005, 2634, 3087, 7229, 6123, 7941, 4018, 4130, 7364, 9571, 3543, 8414, 8532, 835, 1586, 1630, 6133, 8219, 2280, 6122, 3504, 1760, 9780, 8810, 1312, 3381, 7684, 5650, 7874, 8653, 4017, 4556, 7288, 7182, 5452, 8369, 6349, 8484, 4947, 6643, 1716, 8783, 7346, 2666, 6217, 3883, 9687, 8825, 6345, 8090, 1854, 8700, 4109, 4928, 6667, 6864, 5232, 9962, 5158, 8687, 5106, 7195, 8367, 3655, 6598, 6981, 5811, 7350, 7350, 1329, 7112, 5829, 9846, 363, 6578, 2973, 7478, 6716, 4360, 9274, 2704, 197, 9280, 7531, 9330, 2358, 4919, 5106, 9782, 4280, 589, 3838, 6348, 3581, 2605, 5363, 5017, 8498, 8442, 3779, 8344, 3853, 84, 2731, 3887, 649, 2346, 4492, 7277, 6283, 3386, 149, 5408, 801, 3460, 4346, 4643, 1142, 188, 4242, 4964, 4292, 8062, 8274, 1955, 3574, 3548, 786, 9526, 4121, 4419, 6905, 5385, 5023, 6044, 7547, 5027, 6015, 5982, 411, 1845, 2780, 6704, 1373, 6875, 3136, 876, 2701, 2308, 5925, 8224, 4534, 6191, 4849, 3390, 7056, 2955, 6747, 2994, 137, 8193, 734, 5268, 7278, 1869, 2265, 1427, 2599, 8310, 3760, 9378, 5981, 8719, 947, 3775, 3944, 8742, 8440, 2142, 2985, 8938, 4375, 1172, 1760, 3018, 3267, 4063, 8907, 6697, 4000, 5379, 8092, 2477, 7765, 7983, 4150, 6598, 2816, 6862, 5876, 1836, 4096, 7434, 4262, 625, 275, 1723, 1369, 3045, 60, 9068, 6545, 3976, 8848, 2959, 8309, 1435, 8782, 8839, 7575, 7104, 4217, 3746, 2367, 8287, 5459, 3130, 6436, 2858, 7575, 2497, 2358, 8408, 8003, 5514, 8490, 398, 9728, 807, 6307, 2380, 2352, 4780, 4538, 7651, 5251, 9074, 265, 9380, 6902, 5816, 2361, 8916, 553, 6532, 1926, 571, 1674, 1607, 2956, 9628, 3075, 8783, 6849, 6863, 2653, 2790, 2639, 7162, 8544, 1623, 5867, 6958, 5368, 1649, 1303, 4430, 9861, 9220, 7728, 8522, 9535, 1912, 5045, 185, 2212, 636, 5829, 4773, 2688, 7324, 2707, 2864, 5960, 4413, 7305, 7733, 2579, 8706, 2934, 9785, 8837, 2336, 2918, 8753, 3513, 8622, 1516, 3117, 7930, 9766, 8430, 8321, 830, 6361, 8813, 9572, 576, 2735, 3344, 3585, 9688, 7468, 4284, 5275, 2873, 5246, 1426, 7774, 4759, 3570, 7701, 2528, 6179, 3046, 2534, 775, 8101, 6377, 9294, 7172, 1061, 3535, 1879, 8236, 677, 4673, 2647, 2024, 6911, 4177, 1178, 2450, 1974, 571, 3708, 752, 4619, 7686, 5632, 2349, 2287, 7674, 2751, 2562, 4560, 6668, 3392, 7092, 9426, 6453, 2704, 9655, 2859, 8705, 3151, 8577, 282, 2146, 2167, 8313, 1045, 3045, 4681, 7661, 5038, 4060, 326, 2707, 3026, 4810, 2710, 1272, 8302, 1838, 8617, 5092, 382, 4119, 4424, 7982, 5299, 790, 5767, 9467, 8751, 4784, 2617, 6658, 4448, 5826, 3360, 123, 1496, 9935, 5215, 9558, 9813, 9477, 1118, 1657, 4913, 9646, 3738, 8849, 8376, 9186, 4278, 2232, 5313, 9658, 6418, 3927, 3820, 3615, 1398, 9379, 3142, 5137, 4507, 6324, 9580, 1684, 2646, 1827, 2905, 9140, 9746, 897, 4093, 3657, 6857, 8468, 3101, 5797, 4078, 4684, 6001, 4699, 4618, 2068, 3583, 3370, 6050, 293, 7089, 4550, 4417, 6308, 8980, 9272, 9511, 5355, 6640, 1051, 9822, 6052, 7775, 1527, 4708, 3282, 9724, 4748, 3834, 1450, 9242, 7108, 4472, 4095, 1118, 8827, 1546, 6188, 8250, 9082, 7691, 9897, 3606, 414, 3741, 7968, 2765, 4392, 8930, 8122, 4289, 1341, 1115, 1219, 9020, 6606, 1274, 9580, 1013, 9730, 7445, 8159, 43, 9950, 5514, 7516, 7969, 5111, 1673, 4562, 2742, 495, 4003, 5322, 928, 2843, 173, 8941, 1156, 3061, 9345, 9782, 628, 6141, 8641, 4754, 2357};
	
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
		int wallX = w.getX();
		int wallY = w.getY();
		int weight = 0;
		
		//If wallboard is an external wall, simply return maximum integer.
		if((w.getX() == 0 && Arrays.equals(w.getDirection().getDirection(), CardinalDirection.West.getDirection()) ||
			(w.getX() == width - 1 && Arrays.equals(w.getDirection().getDirection(), CardinalDirection.East.getDirection())) ||
			(w.getY() == 0 && Arrays.equals(w.getDirection().getDirection(), CardinalDirection.North.getDirection())) ||
			(w.getY() == height - 1 && Arrays.equals(w.getDirection().getDirection(), CardinalDirection.South.getDirection()))))
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
			
		
		//Use seed to get value from 1000 size int array, and use it in a function to create
		//a "random" integer.
		weight = seedVals[(((wallX * wallY) + wallY) * order.getSeed()) % 1000] + seedVals[(wallX * order.getSeed()) % 1000] +
				seedVals[(wallY + order.getSeed()) % 1000] + seedVals[order.getSeed() % 1000] * (order.getSeed() % 11);
		
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
		//Create an ArrayList that should be used to hold ArrayLists that hold integer arrays for
		//coordinates.
		ArrayList<ArrayList<int[]>> tree = new ArrayList<ArrayList<int[]>>();
		ArrayList<int[]> coor = new ArrayList<int[]>();
		int[] coordinates = new int[2];
		
		//Sets up mergable array, which will be useful for keep track of where components merge
		int[][] mergable = new int[width*height][4];
		for(int i = 0; i < mergable.length; i++)
			for(int j = 0; j < mergable[i].length; j++)
				mergable[i][j] = -1; 
		
		int minWeight = Integer.MAX_VALUE;
		Wallboard minWallboard = new Wallboard(0, 0, CardinalDirection.North);
		
		//Loop through whole maze and add each pair of coordinates to an ArrayList.
		//Add these ArrayLists to the large ArrayList.
		//The smaller ArrayLists are the "components" that one needs in Boruvka's method.
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
			{
				coordinates[0] = x;
				coordinates[1] = y;
				
				coor.add(coordinates);
				
				//Adds coordinate into main ArrayList
				tree.add(deepCopy(coor));
				
				//Removes value from coor to keep each pair of coordinates separate.
				coor.clear();
			}
		
		int tempX = 0;
		int tempY = 0;
		Wallboard tempWall = new Wallboard(0, 0, CardinalDirection.North);
		
		//Run through all the component ArrayLists, and check the weights of all the wallboards
		//near them.
		while(tree.size() > 1)
		{
			for(int i = 0; i < tree.size(); i++)
			{
				for(int j = 0; j < tree.get(i).size(); j++)
				{
					//Keep a running total of the minimum wallboard weight and the wallboard associated with it.
					//Once you finish, save the wallboard and the coordinates associated with it for merging purposes.
					
					//Gets the cell being tested
					tempX = tree.get(i).get(j)[0];
					tempY = tree.get(i).get(j)[1];
					
					//Goes through all possible directions
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
					tree.add(index1, mergeComponents(tree.get(index1), tree.get(index2)));
					tree.remove(index1 + 1);
					tree.remove(index2);
				}
			}
		}
		//Repeat this until there is only one ArrayList left in the large ArrayList.
	}
	
	/**
	 * Merges two components into a sorted new component
	 */
	private ArrayList<int[]> mergeComponents(ArrayList<int[]> first, ArrayList<int[]> second)
	{
		ArrayList<int[]> newComponent = new ArrayList<int[]>();
		
		//Uses an ordering system to keep components ordered well.
		while(!first.isEmpty() && !second.isEmpty())
		{
			if(first.get(0)[0] <= second.get(0)[0])
			{
				if(first.get(0)[0] == second.get(0)[0])
				{
					if(first.get(0)[1] <= second.get(0)[1])
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
				else
				{
					newComponent.add(first.get(0));
					first.remove(0);
				}
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
	 * A private helper method to see whether the
	 * two cells are in the same component or not.
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
}