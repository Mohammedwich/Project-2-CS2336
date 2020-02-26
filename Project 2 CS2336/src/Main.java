//Mohammed Ahmed, msa190000

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;




public class Main
{
	public static void main(String[] args) throws FileNotFoundException
	{
		/*
		//debug test code
		ArrayList<ArrayList<Double>> testList = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> doubleList = new ArrayList<Double>();
		doubleList.add(1.0);
		doubleList.add(2.0);
		testList.add(doubleList);
		testList.add(new ArrayList<Double>(doubleList));
		
		testList.get(0).add(3.0);
		
		System.out.println(testList);
		*/
		
		
		String pilotNamesInputName;
		String commandsInputName;
		Scanner inputScanner = new Scanner(System.in);
		
		//Take names and create input file objects
		System.out.println("Enter the input file name: ");
		pilotNamesInputName = inputScanner.next();
		File pilotAreasInputFile = new File(pilotNamesInputName);
		
		System.out.println("Enter the commands file name: ");
		commandsInputName = inputScanner.next();
		File commandsInputFile = new File(commandsInputName);
		
		
		//create output file objects
		File pilotAreasOutputFile = new File("pilot_areas.txt");
		File commandResultsFile = new File("results.txt");
		
		//create output files
		try 
		{
			pilotAreasOutputFile.createNewFile();
		} 
		catch (IOException e) 
		{
			System.out.print("failed to create pilotAreasOutputFile.\n");
			System.out.flush();
		}
		
		try 
		{
			commandResultsFile.createNewFile();
		} 
		catch (IOException e) 
		{
			System.out.print("failed to create commandResultsFile.\n");
			System.out.flush();
		}

		//Check if input files opened properly
		if(!pilotAreasInputFile.exists())
		{
			System.out.print("pilotAreasInputFile file does not exist \n");
		}
		if(!commandsInputFile.exists())
		{
			System.out.print("commandsInputFile file does not exist \n");
		}
		
		//Check if output files were created properly
		if(!pilotAreasOutputFile.exists())
		{
			System.out.print("pilotAreasOutputFile file does not exist \n");
		}
		if(!commandResultsFile.exists())
		{
			System.out.print("commandResultsFile file does not exist \n");
		}
//***********************************************************************************
		
		//For the 3D list assume the first dimension holds the specific pilot's list of coordinates, second dimension holds a list of all
		//coordinates for said pilot third dimension holds a list of two values(x and y coordinate at positions 0 and 1)
		//So list[0][0][1] is first pilot's first coordinate's y value
		ArrayList<ArrayList<ArrayList<Double>>> coordinatesList = new ArrayList<ArrayList<ArrayList<Double>>>(); // 3D array list
		
		ArrayList<String> namesList = new ArrayList<String>();
		ArrayList<Double> areasList = new ArrayList<Double>();
		
		int numberOfLinesInInputFile = 0; //will use this to expand the list so elements can be referenced in extractData()
		
		
		Scanner lineCounter = new Scanner(pilotAreasInputFile);
		while(lineCounter.hasNextLine())
		{
			++numberOfLinesInInputFile;
			lineCounter.nextLine();
		}
		
		
		
		//*********************************
		
		//Take data from file and put it in arrays
		extractData(namesList, coordinatesList, pilotAreasInputFile);
		System.out.println(namesList); //debug code
		System.out.println(coordinatesList); //debug code
		
		/*
		//Passing each pilot's 2D coordinates array to the calculateArea function
		for(int i = 0; i < namesList.size(); ++i)
		{
			areasList.add(calculateArea(coordinatesList.get(i)) );
		}
		
		//Write data from arrays to output file
		writeData(namesList, areasList, pilotAreasOutputFile);
		*/
		//*********************************

		inputScanner.close();
		lineCounter.close();
		
	}//main end

	
	//Functions
	//*********************************************************************************
	//The area formula: 0.5 * absoluteValue( Summation( x(i) + x(i-1) ) * (y(i) - y(i-1) ) )
		// Takes the 2D list inside one of the elements of the 3D array as a parameter. The array inside a list's first dimension basically
		static double calculateArea(ArrayList<ArrayList<Double>> theList)
		{
			int currentCoordinate = 1; // Starts at 1 since the formula needs to sum/sub an x or y with the next x or y
			double sum = 0;
			double area = 0;
			
			//checks if we reached the last coordinate which is equal to the first coordinate
			while ( (theList.get(currentCoordinate).get(0) != theList.get(0).get(0)) || (theList.get(currentCoordinate).get(1) != theList.get(0).get(1)) )
			{
				// sum of ( x(i) + x(i-1) ) * (y(i) - y(i-1) )
				sum += (theList.get(currentCoordinate).get(0) + theList.get(currentCoordinate-1).get(0)) * 
						(theList.get(currentCoordinate).get(1) - theList.get(currentCoordinate-1).get(1));
				
				++currentCoordinate;	//sum until last coordinate is the same as first coordinate
				
				//Prevents out of bounds since coordinates array is 16 elements max 
				if(currentCoordinate == 15)
				{
					break;
				}
			}
			
			//Do it once more for the last coordinate
			sum += (theList.get(currentCoordinate).get(0) + theList.get(currentCoordinate-1).get(0)) * 
					(theList.get(currentCoordinate).get(1) - theList.get(currentCoordinate-1).get(1));
			
			
			area = 0.5 * Math.abs(sum);
			
			return area;
		}
		
		/*********************************************************************************************/
		
		static void extractData(ArrayList<String> theNamesList, ArrayList<ArrayList<ArrayList<Double>>> theCoordinatesList, File inputFile) throws FileNotFoundException
		{
			//For the list assume the first dimension holds the specific pilot's list of coordinates, second dimension holds a list of all
			//coordinates for said pilot third dimension holds a list of two values(x and y coordinate at positions 0 and 1)
			//So list[0][0][1] is first pilot's first coordinate's y value
			Scanner fileReader = new Scanner(inputFile);

			String currentLine; // Will hold one line at a time for processing
			int currentLineIndex = 0; // Will be incremented after each line is stored
			
			//Used to make scanner pick a mutiword name before starting on coordinates by changing its delimiter temporarily
			Pattern thePattern = Pattern.compile("[\\s][-\\d]"); 
			
			while(fileReader.hasNextLine() /*&& currentLineIndex < theCoordinatesList.size()*/) // unnecessary code in comment?
			{
				//TODO: remove this line if not needed anymore
				//int currentCoordinateindex = 0; // Allows us to take both parts of a coordinate since space separates them
				
				//Put line in a string and commas with spaces before passing the string to another scanner
				currentLine = fileReader.nextLine();
				currentLine = currentLine.replace(',', ' ');
				
				Scanner stringScanner = new Scanner(currentLine); // Created so we can pick the string word by word
				
				stringScanner.useDelimiter(thePattern); //change delimiter to pick the name
				theNamesList.add(stringScanner.next()); // Store the pilot's name before storing coordinates
				stringScanner.useDelimiter(" "); //change delimiter back to space
				
				//create and add the list of coordinates so we can add to it.
				theCoordinatesList.add(new ArrayList<ArrayList<Double>>(1) );
				
				while(stringScanner.hasNext())
				{
					//Store x and y coordinate
					double x = Double.parseDouble(stringScanner.next());
					double y = Double.parseDouble(stringScanner.next());
					
					//create a list of doubles(a coordinates pair) so we can add it to the list of coordinates we created just before this loop
					ArrayList<Double> doubleList = new ArrayList<Double>(2);
					doubleList.add(x);
					doubleList.add(y);
					
					theCoordinatesList.get(currentLineIndex).add(doubleList);
									
					//++currentCoordinateindex; //Remove this line if not needed anymore
				}
				
				++currentLineIndex;
				stringScanner.close();
			}
			
			fileReader.close();
		}
		
		
		/***********************************************************************/
		
		// Takes array of names, array of areas and an output file. Writes every name followed by a tab and its area
		static void writeData(ArrayList<String> theNamesList, ArrayList<Double> theAreasList, File theFile) throws FileNotFoundException
		{
			//System.out.println(theNamesList); //debug code
			PrintWriter writer = new PrintWriter(theFile);
			
			for(int i = 0; i < theNamesList.size(); ++i)
			{
				if(theNamesList.get(i) == null)
				{
					break;
				}
				writer.printf("%s\t%.2f\n", theNamesList.get(i), theAreasList.get(i));
			}
			
			writer.close();
		}

}//main class end



