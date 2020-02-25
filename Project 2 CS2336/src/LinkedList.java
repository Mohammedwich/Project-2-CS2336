//Mohammed Ahmed, msa190000


public class LinkedList<T extends Comparable<T>>
{
	private Node<T> listHead;
	private Node<T> listTail;
	private int size;

	public LinkedList()
	{
		listHead = null;
		listTail = null;
		size = 0;
	}
	
	public LinkedList(Node<T> theNode)
	{
		setHead(theNode);
		listTail = theNode;
		++size;
	}

	public Node<T> getHead()
	{
		return listHead;
	}

	public void setHead(Node<T> aNode)
	{
		listHead = aNode;
	}
	
	public Node<T> getTail()
	{
		return listTail;
	}

	public void setTail(Node<T> aNode)
	{
		listTail = aNode;
	}
	
	public void addNode(Node<T> aNode)
	{
	   listTail.setNext(aNode);
	   listTail = aNode;   
	   ++size;
	}
	
	public int getSize()
	{
		return size;
	}
	
	//Used to be: 	public void sort(boolean byName, boolean byAscending) but compare should happen based on payload's flag
	public void sort(boolean byAscending)
	{
		Node<T> firstNode = listHead;
		Node<T> currentNode = firstNode.getNext();
		Node<T> smallestOrLargestNode = firstNode; // Holds the smallest/largest node when sorting by ascending/descending
		
		if(byAscending == true)
		{
			while(firstNode != listTail)
			{
			    while(currentNode != null)
			    {
			    	if(smallestOrLargestNode.compareTo(currentNode) >= 1)
			    	{
			    		smallestOrLargestNode = currentNode;
			    	}
	
			    	if(smallestOrLargestNode == listTail)
			    	{
			    		listTail = firstNode;
			    	} //since firstNode and smallestNode will be switched, set the new tail
	
				   currentNode = currentNode.getNext();
			    }
			    
			    //switch nodes pointers
			    Node<T> tempNext = firstNode.getNext();
			    Node<T> tempPrevious = firstNode.getPrevious();
			    
			    firstNode.setNext(smallestOrLargestNode.getNext());
			    firstNode.setPrevious(smallestOrLargestNode.getPrevious());
	
			    smallestOrLargestNode.setNext(tempNext);
			    smallestOrLargestNode.setPrevious(tempPrevious);
	
			    firstNode = smallestOrLargestNode.getNext();
			    
	
			    if(firstNode != listTail)
			    {
			       currentNode = firstNode.getNext();
			    }
			}
			
		} //if byAscending true end
		
		if(byAscending == false)
		{
			while(firstNode != listTail)
			{
			    while(currentNode != null)
			    {
			    	if(smallestOrLargestNode.compareTo(currentNode) <= -1)
			    	{
			    		smallestOrLargestNode = currentNode;
			    	}
	
			    	if(smallestOrLargestNode == listTail)
			    	{
			    		listTail = firstNode;
			    	} //since firstNode and smallestNode will be switched, set the new tail
	
				   currentNode = currentNode.getNext();
			    }
			    
			    //switch nodes pointers
			    Node<T> tempNext = firstNode.getNext();
			    Node<T> tempPrevious = firstNode.getPrevious();
			    
			    firstNode.setNext(smallestOrLargestNode.getNext());
			    firstNode.setPrevious(smallestOrLargestNode.getPrevious());
	
			    smallestOrLargestNode.setNext(tempNext);
			    smallestOrLargestNode.setPrevious(tempPrevious);
	
			    firstNode = smallestOrLargestNode.getNext();
			    
	
			    if(firstNode != listTail)
			    {
			       currentNode = firstNode.getNext();
			    }
			}
			
		} //if byAscending false end
	} // sort function end
	
	@Override
	public String toString()
	{
		Node<T> currentNode = listHead;
		StringBuilder builder = new StringBuilder();
		String result;
	
		while(currentNode.getNext() != null)
		{
		  builder.append(currentNode.toString());
		  currentNode = (Node<T>) currentNode.getNext();
		}
		
		result = builder.toString();
		return result;
	}

}
