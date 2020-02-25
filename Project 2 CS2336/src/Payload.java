//Mohammed Ahmed, msa190000


public class Payload implements Comparable<Payload>
{
	private String name;
	private double area;
	private boolean compareByName;  //false if compare by area, true if compare by name

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
		area = theArea;
	}
	
	public void setCompareFlag(boolean theFlag)
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
				result = name.compareTo(((Payload)o).getName() );
				return result;
			}
			else
			{
				if(area < ((Payload)o).getArea())
				{
					return -1;
				}
				else if(area == ((Payload)o).getArea())
				{
					return 0;
				}
				else if(area > ((Payload)o).getArea())
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
		String result = (name + "\t" + area + "\n");
		return result;
	}
}
