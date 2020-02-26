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
	public static void main(String[] args)
	{
		Pattern thePattern = Pattern.compile("[\\s][\\d]");
		String testString = "Jack Jones 3,4 5,6 7,8 3,4";
		Scanner testScanner = new Scanner(testString);
		
		
		testScanner.useDelimiter(thePattern);
		System.out.println(testScanner.next());
		testScanner.useDelimiter(" ");
		
		while(testScanner.hasNext())
		{
			System.out.println(testScanner.next());
		}
		
		/*
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
		
		

		inputScanner.close();
		*/
	}//main end

	
	//Functions
	//*********************************************************************************
	//The formula: 0.5 * absoluteValue( Summation( x(i) + x(i-1) ) * (y(i) - y(i-1) ) )
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
			Pattern thePattern = Pattern.compile("[\\s][\\d]"); //Used to pick a mutiword name before starting on coordinates
			
			while(fileReader.hasNextLine() && currentLineIndex < theCoordinatesList.size())
			{
				int currentCoordinateindex = 0; // Allows us to take both parts of a coordinate since space separates them
				
				//Put line in a string and commas with spaces before passing the string to another scanner
				currentLine = fileReader.nextLine();
				currentLine = currentLine.replace(',', ' ');
				
				Scanner stringScanner = new Scanner(currentLine); // Created so we can pick the string word by word
				
				stringScanner.useDelimiter(thePattern); //change delimiter to pick the name
				theNamesList.add(stringScanner.next()); // Store the pilot's name before storing coordinates
				stringScanner.useDelimiter(" "); //change delimiter back to space
				
				while(stringScanner.hasNext())
				{
					//Store x and y coordinate
					theCoordinatesList.get(currentLineIndex).get(currentCoordinateindex).add(Double.parseDouble(stringScanner.next() ));
					theCoordinatesList.get(currentLineIndex).get(currentCoordinateindex).add(Double.parseDouble(stringScanner.next() ));
									
					++currentCoordinateindex;
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



