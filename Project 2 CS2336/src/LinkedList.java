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

	public void sort(boolean byAscending)
	{
		int initialSize = size;
		ArrayList<Node<T>> nodesArray = new ArrayList<Node<T>>(); //will hold elements while they get bubble sorted
		Node<T> currentNode = listHead;
		boolean switchHappened = true;
		
		//load all our nodes for processing
		while(currentNode != null)
		{
			nodesArray.add(currentNode);
			currentNode = currentNode.getNext();
		}
		
		//If we want to sort by ascending
		if(byAscending == true)
		{
			while(switchHappened == true)
			{
				switchHappened = false; //set to false, code that switches will make it true again if it executes
				
				for(int currentItem = 0; currentItem < initialSize; ++currentItem)
				{
					int nextItem = currentItem + 1;
					
					if(nextItem != initialSize)
					{
						if(nodesArray.get(currentItem).compareTo(nodesArray.get(nextItem)) >= 1)
						{
							Node<T> tempNode = nodesArray.get(currentItem);
							nodesArray.set(currentItem, nodesArray.get(nextItem)); //put the smaller item in currentItem position
							nodesArray.set(nextItem, tempNode); //put the larger item in nextItem position
							switchHappened = true;
						}
					}					
				} //when this for loop ends, one largest item will have been pushed to where it belongs.
			} // This while loop ends when no switch happens which means elements are in order
			
			//Now we link the nodes
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
			
		} // ascending sort end
		
		// If we want to sort by descending
		if(byAscending == false)
		{
			while(switchHappened == true)
			{
				switchHappened = false; //set to false, code that switches will make it true again if it executes
				
				for(int currentItem = 0; currentItem < initialSize; ++currentItem)
				{
					int nextItem = currentItem + 1;
					
					if(nextItem != initialSize)
					{
						if(nodesArray.get(currentItem).compareTo(nodesArray.get(nextItem)) <= -1)
						{
							Node<T> tempNode = nodesArray.get(currentItem);
							nodesArray.set(currentItem, nodesArray.get(nextItem)); //put the smaller item in currentItem position
							nodesArray.set(nextItem, tempNode); //put the larger item in nextItem position
							switchHappened = true;
						}
					}					
				} //when this for loop ends, one smallest item will have been pushed to where it belongs.
			} // This while loop ends when no switch happens which means elements are in order
			
			//Now we link the nodes
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
			
		} // descending sort end
		
	} // sort end
	
	
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
