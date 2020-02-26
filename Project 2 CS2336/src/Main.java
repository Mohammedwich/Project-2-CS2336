import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

//Mohammed Ahmed, msa190000


public class Main
{
	public static void main(String[] args)
	{
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
		
		
		
		

		inputScanner.close();
	}//main end

	
	//Functions
	//*********************************************************************************
	//The formula: 0.5 * absoluteValue( Summation( x(i) + x(i-1) ) * (y(i) - y(i-1) ) )
		// Takes the 2D array inside one of the elements of the 3D array as a parameter
		static double calculateArea(double[][] theArray)
		{
			int currentCoordinate = 1; // Starts at 1 since the formula needs to sum/sub an x or y with the next x or y
			double sum = 0;
			double area = 0;
			
			//checks if we reached the last coordinate which is equal to the first coordinate
			while ( (theArray[currentCoordinate][0] != theArray[0][0]) || (theArray[currentCoordinate][1] != theArray[0][1]) )
			{
				// sum of ( x(i) + x(i-1) ) * (y(i) - y(i-1) )
				sum += (theArray[currentCoordinate][0] + theArray[currentCoordinate - 1][0]) * 
						(theArray[currentCoordinate][1] - theArray[currentCoordinate - 1][1]);
				
				++currentCoordinate;	//sum until last coordinate is the same as first coordinate
				
				//Prevents out of bounds since coordinates array is 16 elements max
				if(currentCoordinate == 15)
				{
					break;
				}
			}
			
			//Do it once more for the last coordinate
			sum += (theArray[currentCoordinate][0] + theArray[currentCoordinate - 1][0]) * 
					(theArray[currentCoordinate][1] - theArray[currentCoordinate - 1][1]);
			
			
			area = 0.5 * Math.abs(sum);
			
			return area;
		}
		
		/*********************************************************************************************/
		
		static void extractData(String[] namesArray, double[][][] coordinateArray, File inputFile) throws FileNotFoundException
		{
			Scanner fileReader = new Scanner(inputFile);

			String currentLine; // Will hold one line at a time for processing
			int currentLineIndex = 0; // Will be incremented after each line is stored
			
			while(fileReader.hasNextLine() && currentLineIndex < 20)
			{
				int currentCoordinateindex = 0; // Allows us to take both parts of a coordinate since space separates them
				
				//Put line in a string and commas with spaces before passing the string to another scanner
				currentLine = fileReader.nextLine();
				currentLine = currentLine.replace(',', ' ');
				
				Scanner stringScanner = new Scanner(currentLine); // Created so we can pick the string word by word
				
				namesArray[currentLineIndex] = stringScanner.next(); // Store the pilot's name before storing coordinates
				
				while(stringScanner.hasNext() && currentCoordinateindex < 16)
				{
					//Store x and y coordinate
					coordinateArray[currentLineIndex][currentCoordinateindex][0] = Double.parseDouble(stringScanner.next() );
					coordinateArray[currentLineIndex][currentCoordinateindex][1] = Double.parseDouble(stringScanner.next() );
									
					++currentCoordinateindex;
				}
				
				++currentLineIndex;
				stringScanner.close();
			}
			
			fileReader.close();
		}
		
		
		/***********************************************************************/
		
		// Takes array of names, array of areas and an output file. Writes every name followed by a tab and its area
		static void writeData(String[] namesArray, double[] areasArray, File theFile) throws FileNotFoundException
		{
			PrintWriter writer = new PrintWriter(theFile);
			
			for(int i = 0; i < namesArray.length; ++i)
			{
				if(namesArray[i] == null)
				{
					break;
				}
				writer.printf("%s\t%.2f\n", namesArray[i], areasArray[i]);
			}
			
			writer.close();
		}

}//main class end



