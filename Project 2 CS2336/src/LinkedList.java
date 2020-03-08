//Mohammed Ahmed, msa190000

import java.util.ArrayList;

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
		if(listHead == null)
		{
			listHead = aNode;
			listTail = aNode;
			
			++size;
		}
		else
		{
			listTail.setNext(aNode);
			Node<T> nodeBefore = listTail;
			listTail = listTail.getNext();  
			listTail.setPrevious(nodeBefore);
			
			++size;
		}		
	}
	
	public void removeNode(Node<T> aNode)
	{
		Node<T> previousNode = aNode.getPrevious();
		Node<T> nextNode = aNode.getNext();
		
		if(previousNode != null)
		{
			previousNode.setNext(nextNode);
		}
		if(nextNode != null)
		{
			nextNode.setNext(nextNode);
		}
		
		aNode.setNext(null);
		aNode.setPrevious(null);
		
		--size;
	}
	
	
	public int getSize()
	{
		return size;
	}
	
	//Used to be: 	public void sort(boolean byName, boolean byAscending) but compare should happen based on payload's flag
	public void sort(boolean byAscending)
	{
		Node<T> currentNode = listHead;
		int initialSize = size;
		ArrayList<Node<T>> nodesArray = new ArrayList<Node<T>>(); //fix pointers as they are added.
		
		
		if(byAscending == true)
		{
			while(nodesArray.size() != initialSize) //keep going till we collected all elements.
			{
				currentNode = listHead;
				Node<T> smallestOrLargestNode = currentNode; // Holds the smallest/largest node when sorting by ascending/descending
				
				//Make put a value we don't already have inside
				while(nodesArray.contains(smallestOrLargestNode))
				{
					smallestOrLargestNode = smallestOrLargestNode.getNext();
				}
				
				while(currentNode != null) //Iterates over the list and gets the smallest value we don't already have.
				{					
					if(currentNode.compareTo(smallestOrLargestNode) <= -1)
			    	{
						if(!nodesArray.contains(currentNode))
						{
							smallestOrLargestNode = currentNode;
						}
			    		
			    		currentNode = currentNode.getNext();
			    	}
					else
					{
						currentNode = currentNode.getNext();
					}
				}
				
				nodesArray.add(smallestOrLargestNode);
			}
			
			//Adjust all previous and next pointers
			for(int i = 0; i < initialSize; ++i)
			{
				Node<T> theNode = nodesArray.get(i);
				
				if(i == 0)
				{
					theNode.setPrevious(null);
					theNode.setNext(nodesArray.get(i+1));
				}
				else if(i == (initialSize-1))
				{
					theNode.setPrevious(nodesArray.get(i-1));
					theNode.setNext(null);
				}
				else
				{
					theNode.setPrevious(nodesArray.get(i-1));
					theNode.setNext(nodesArray.get(i+1));
				}
			}
			
			listHead = nodesArray.get(0);
			listTail = nodesArray.get(initialSize-1);			
			
		} //if byAscending==true end
		
		if(byAscending == false)
		{
			while(nodesArray.size() != initialSize) //keep going till we collected all elements.
			{
				currentNode = listHead;
				Node<T> smallestOrLargestNode = currentNode; // Holds the smallest/largest node when sorting by ascending/descending
				
				//Make put a value we don't already have inside
				if(nodesArray.contains(smallestOrLargestNode))
				{
					smallestOrLargestNode = smallestOrLargestNode.getNext();
				}
				
				while(currentNode != null) //Iterates over the list and gets the smallest value we don't already have.
				{					
					//Check: Don't Erase this comment!! Used to be >= 1, changed to zero to fix an issue where equal items were not being added. See if reverting or adjusting ascending sort fixes any potential issues with this in the future  
					if(currentNode.compareTo(smallestOrLargestNode) >= 1) 
			    	{
						if(!nodesArray.contains(currentNode))
						{
							smallestOrLargestNode = currentNode;
						}
			    		
			    		currentNode = currentNode.getNext();
			    	}
					else
					{
						currentNode = currentNode.getNext();
					}
				}
				
				nodesArray.add(smallestOrLargestNode);
			}
			
			//Adjust all previous and next pointers
			for(int i = 0; i < initialSize; ++i)
			{
				Node<T> theNode = nodesArray.get(i);
				
				if(i == 0)
				{
					theNode.setPrevious(null);
					theNode.setNext(nodesArray.get(i+1));
				}
				else if(i == (initialSize-1))
				{
					theNode.setPrevious(nodesArray.get(i-1));
					theNode.setNext(null);
				}
				else
				{
					theNode.setPrevious(nodesArray.get(i-1));
					theNode.setNext(nodesArray.get(i+1));
				}
			}
			
			listHead = nodesArray.get(0);
			listTail = nodesArray.get(initialSize-1);
			
		} //if byAscending==false end
		
		//TODO: remove this code block if not needed
		/*
		String sortedHeadValue = listHead.toString();
		String sortedTailValue = listTail.toString();
		
		System.out.println("Head: " + sortedHeadValue + ", " + "Tail: " + sortedTailValue);
		*/
		System.out.println(this);
	} // sort function end

	public String search(String keyword)
	{
		String result = keyword + " not found";
		
		String currentStringToCheck;
		Node<T> currentNode = listHead;
		
		while(currentNode != null)
		{
			currentStringToCheck = currentNode.toString();
			
			if(currentStringToCheck.contains(keyword))
			{
				String temp = currentStringToCheck;
				temp.replace('\t', ' '); //replace the tab between pilotname and area with a space
				result = temp;
				return result;
			}
			else
			{
				if(currentNode != listTail)
				{
					currentNode = currentNode.getNext();
				}
				else
				{
					currentNode = null;
				}
			}
		}
		
		return result; 
	}
	
	@Override
	public String toString()
	{
		Node<T> currentNode = listHead;
		StringBuilder builder = new StringBuilder();
		String result;
	
		while(currentNode != null)
		{
		  builder.append(currentNode.toString() + "\n");
		  currentNode = currentNode.getNext();
		}
		
		result = builder.toString();
		return result;
	}

}
