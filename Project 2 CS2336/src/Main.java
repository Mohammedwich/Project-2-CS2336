//Mohammed Ahmed, msa190000

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;



//TODO: remove "debug code"
//TODO: do input validation
//TODO: add input validation for data extraction and confirm the regex patterns work as intended
public class Main
{
	public static void main(String[] args) throws FileNotFoundException
	{
		/*
		//debug test code //debug code
		String testString = "g-'g 1.2,21 2,2.3 3,4";
		Pattern testPattern = Pattern.compile("[a-zA-Z]+[-'a-zA-z0-9]+([\\s][\\d]+[.]?[\\d]*[,][\\d]+[.]?[\\d]*)+"); 
		Matcher testMatcher = testPattern.matcher(testString);
		
		System.out.println(testMatcher.matches());
		*/
		
		// Create and check files 
		//*************************************************************************************************************
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
		
		//create arrays to hold extracted data, extract data from file, process it, and write to output file pilot_areas.txt
//**************************************************************************************************************************
		//For the 3D list assume the first dimension holds the specific pilot's list of coordinates, second dimension holds a list of all
		//coordinates for said pilot third dimension holds a list of two values(x and y coordinate at positions 0 and 1)
		//So list[0][0][1] is first pilot's first coordinate's y value
		ArrayList<ArrayList<ArrayList<Double>>> coordinatesList = new ArrayList<ArrayList<ArrayList<Double>>>(); // 3D array list
		
		ArrayList<String> namesList = new ArrayList<String>();
		ArrayList<Double> areasList = new ArrayList<Double>();
		
		//TODO: remove this line if not needed
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
		//System.out.println(namesList); //debug code
		//System.out.println(coordinatesList); //debug code
				
		//Passing each pilot's 2D coordinates array to the calculateArea function
		for(int i = 0; i < namesList.size(); ++i)
		{
			areasList.add(calculateArea(coordinatesList.get(i)) );
		}
		
		
		//System.out.println(areasList); //debug code
		
		
		//Write data from arrays to output file
		writeData(namesList, areasList, pilotAreasOutputFile);	
		
		//Create an object from the LinkedList class I made and fill it up with the nodes made of the extracted data
//*************************************************************************************************************************
		LinkedList<Payload> payLoadsList = new LinkedList<Payload>();
		
		for(int i = 0; i < namesList.size(); ++i)
		{
			String currentName = namesList.get(i);
			double currentArea = areasList.get(i);
			
			Payload tempPayload = new Payload(currentName);
			tempPayload.setArea(currentArea);
			
			Node<Payload> tempNode = new Node<Payload>(tempPayload);
			
			payLoadsList.addNode(tempNode);
		}
		
		
		
		//read commands and write results to output file results.txt
//*************************************************************************************************************************
		
		Scanner commandReader = new Scanner(commandsInputFile); // will read the commands file
		PrintWriter commandWriter = new PrintWriter(commandResultsFile);
		
		//valid line patterns 
		Pattern sortPattern = Pattern.compile("[sS][oO][rR][tT][\\s][aApP][rRiI][eElL][aAoO][tT]*[\\s][aAdD][sScC][cC]"); // pattern for exactly(case insensitive): sort <area/pilot> <asc/dec>
		Pattern nameSearchPattern = Pattern.compile("[a-zA-Z]+[-'a-zA-z0-9]+"); // pattern for a name that starts with a letter and contains alphanumeric characters, hyphens and apostrophe's
		Pattern areaSearchPattern = Pattern.compile("[\\d]+[.]?[\\d]*"); //pattern for area. one or more numbers followed by a dot/no dot followed by zero or more numbers
		//TODO: if we need to adjust this pattern for cases with a decimal followed by no numbers, try matcher.matches("\\b[.][\\d]+\\b")) word boundary of .xxxx if this pattern is syntaxicaally correct, test it first
		
		while(commandReader.hasNextLine())
		{
			String currentLine = commandReader.nextLine();
			
			Scanner lineReader = new Scanner(currentLine);
			
			Matcher sortMatcher = sortPattern.matcher(currentLine);
			Matcher nameSearchMatcher = nameSearchPattern.matcher(currentLine);
			Matcher areaSearchMatcher = areaSearchPattern.matcher(currentLine);
			
			//check if valid line. If not, skip line
			if(sortMatcher.matches() == false && nameSearchMatcher.matches() == false && areaSearchMatcher.matches() == false )
			{
				continue;
			}
			
			//execute command depending on which pattern it is
			if(sortMatcher.matches())
			{
				String outputString;
				int nameOrAreaMarker = 1; //will be set to 1 if payload compareByName is true and 0 if false(comparing by area)
				
				String firstWord = lineReader.next(); //holds the word "sort". Only holding it for checking purposes and to move the scanner to next. No plan to use it for processing
				String nameOrArea = lineReader.next(); //holds "name" or "area"; what we are trying to sort by
				String ascendingOrDescending = lineReader.next(); //holds "asc" or "dec" for ascending or decending sort
				
								
				//set the flag for the compare criteria for Payload objects
				if(nameOrArea.compareToIgnoreCase("area") == 0)
				{
					Payload.setCompareFlag(false); //false if compare by area, true if compare by name
					nameOrAreaMarker = 0;
				}
				else if(nameOrArea.compareToIgnoreCase("pilot") == 0)
				{
					Payload.setCompareFlag(true); //false if compare by area, true if compare by name
					nameOrAreaMarker = 1;
				}
				else
				{
					System.out.println("Invalid Payload compare criteria. The word is neither area nor pilot. Default criteria set to pilot name");
					Payload.setCompareFlag(true); //false if compare by area, true if compare by name
					nameOrAreaMarker = 1; //default it to 1 to compare by name since that is the default in payload
				}
				
				
				//sort the list ascending or descending. sort() parameter = true means sort by ascending, false = descending
				if(ascendingOrDescending.compareToIgnoreCase("asc") == 0)
				{
					payLoadsList.sort(true);
				}
				else if(ascendingOrDescending.compareToIgnoreCase("dec") == 0)
				{
					payLoadsList.sort(false);
				}
				else
				{
					System.out.println("Invalid sort criteria. The word is not asc or dec. Default criteria set to ascending");
					payLoadsList.sort(true);
				}
				
				//Create the string we will write to the output file
				if(nameOrAreaMarker == 1)
				{
					outputString = "Head: " + payLoadsList.getHead().getObject().getName() + ", Tail: " + payLoadsList.getTail().getObject().getName();  
				}
				else
				{
					outputString = "Head: " + payLoadsList.getHead().getObject().getArea() + ", Tail: " + payLoadsList.getTail().getObject().getArea();  
				}
				
				//Write string to command output file
				commandWriter.append(outputString + "\n");
				
			}
			
			//search for the string and write the result of the search to an output file
			if(nameSearchMatcher.matches())
			{
				String nameToSearch = lineReader.next();
				
				String searchResult = payLoadsList.search(nameToSearch);
				
				commandWriter.append(searchResult + "\n");
			}
			
			//search for the string and write the result of the search to an output file
			if(areaSearchMatcher.matches())
			{
				String searchResult;
				String areaToSearch = lineReader.next();
				
				int secondDecimalPlaceIndex = areaToSearch.indexOf('.'); //we want to match search to the second decimal place
				
				//if there is a decimal we search to two decimal points, otherwise we search the number as is
				if(secondDecimalPlaceIndex != -1)
				{
					//TODO: confirm that we don't get out of bounds exception
					String twoDecimalSearch = (String) areaToSearch.subSequence(0, secondDecimalPlaceIndex + 3); //add 3 so we get two digits after the decimal point
					searchResult = payLoadsList.search(twoDecimalSearch);
				}
				else
				{
					searchResult = payLoadsList.search(areaToSearch);
				}
				
				commandWriter.append(searchResult + "\n");
			}
			
			lineReader.close();
		} //command reading loop end
		
		
		
		
		inputScanner.close();
		lineCounter.close();
		commandReader.close();
		commandWriter.close();
		
	}//main end

	
	//Functions
//*********************************************************************************
	//The area formula: 0.5 * absoluteValue( Summation( x(i) + x(i-1) ) * (y(i) - y(i-1) ) )
		// Takes the 2D list inside one of the elements of the 3D array as a parameter. The array inside a list's first dimension basically
		static double calculateArea(ArrayList<ArrayList<Double>> theList)
		{
			int listSize = theList.size(); // will be used to end the loop
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
				
				//Prevents out of bounds 
				if(currentCoordinate == (listSize - 2))
				{
					break;
				}
			}
			
			//Do it once more for the last coordinate
			sum += (theList.get(currentCoordinate).get(0) + theList.get(currentCoordinate-1).get(0)) * 
					(theList.get(currentCoordinate).get(1) - theList.get(currentCoordinate-1).get(1));
			
			
			area = 0.5 * Math.abs(sum);
			//TODO: see if we need to do any rounding here
			return area;
		}
		
		/*********************************************************************************************/
		
		static void extractData(ArrayList<String> theNamesList, ArrayList<ArrayList<ArrayList<Double>>> theCoordinatesList, File inputFile) throws FileNotFoundException
		{
			//TODO: confirm this pattern works as intended
			//should be a pattern for a name then space then (a coordinate point followed by one or no white space) repeating
			Pattern validPilotCoordinatesPattern = Pattern.compile("[a-zA-Z]+[-'a-zA-z0-9]+([\\s][-]?[\\d]+[.]?[\\d]*[,][-]?[\\d]+[.]?[\\d]*)+");   
			
			//For the list assume the first dimension holds the specific pilot's list of coordinates, second dimension holds a list of all
			//coordinates for said pilot third dimension holds a list of two values(x and y coordinate at positions 0 and 1)
			//So list[0][0][1] is first pilot's first coordinate's y value
			Scanner fileReader = new Scanner(inputFile);

			String currentLine; // Will hold one line at a time for processing
			int currentLineIndex = 0; // Will be incremented after each line is stored
			
			//Used to make scanner pick a mutiword name before starting on coordinates by changing its delimiter temporarily
			Pattern scannerDelimiterPattern = Pattern.compile("[\\s][-\\d]"); 
			
			while(fileReader.hasNextLine() /*&& currentLineIndex < theCoordinatesList.size()*/) // unnecessary code in comment?
			{
				//TODO: remove this line if not needed anymore
				//int currentCoordinateindex = 0; // Allows us to take both parts of a coordinate since space separates them
				
				//Put line in a string, check it then if valid, replace commas with spaces before passing the string to another scanner
				currentLine = fileReader.nextLine();
				
				//Check for valid pattern in line, if not valid, skip line
				Matcher pilotCoordinatesMatcher = validPilotCoordinatesPattern.matcher(currentLine);
				if(pilotCoordinatesMatcher.matches() == false)
				{
					continue;
				}
				//now replace commas and proceed
				currentLine = currentLine.replace(',', ' ');
				
				
				
				Scanner stringScanner = new Scanner(currentLine); // Created so we can pick the string word by word
				
				stringScanner.useDelimiter(scannerDelimiterPattern); //change delimiter to pick the name
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



