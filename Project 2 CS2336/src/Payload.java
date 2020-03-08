//Mohammed Ahmed, msa190000

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Payload implements Comparable<Payload>
{
	private String name;
	private double area;
	private static boolean compareByName;  //false if compare by area, true if compare by name

	public Payload()
	{
		name = "";
		area = 0.0;
		compareByName = false;
	}
	
	public Payload(String theName)
	{
		this();
		name = theName;
	}
	
	public String getName()
	{
		return name;
	}
	
	public double getArea()
	{
		return area;
	}
	
	public boolean getCompareFlag()
	{
		return compareByName;
	}
	
	public void setName(String theName)
	{
		name = theName;
	}
	
	public void setArea(double theArea)
	{
		BigDecimal theNum = new BigDecimal(theArea).setScale(2, RoundingMode.HALF_UP);
		area = theNum.doubleValue();
	}
	
	public static void setCompareFlag(boolean theFlag)
	{
		compareByName = theFlag;
	}


	//Return -666 is an error. Compares based on name or area based on the calling object's compareByName flag
	@Override
	public int compareTo(Payload o)
	{
		if((o instanceof Payload))
		{
			if(compareByName == true)
			{
				int result;
				result = name.compareTo(o.getName() );
				return result;
			}
			else
			{
				if(area < o.getArea())
				{
					return -1;
				}
				else if(area == o.getArea())
				{
					return 0;
				}
				else if(area > o.getArea())
				{
					return 1;
				}
				else
				{
					System.out.println("Unexpected compare in class Payload.");
					return -666;
				}
			}
		}
		else
		{
			System.out.println("Invalid compare in Payload class.");
			return -666;
		}
	}

	@Override
	public String toString()
	{
		String result = String.format("%s\t%.2f", name, area); 
		return result;
	}
}
