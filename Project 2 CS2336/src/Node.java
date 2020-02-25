//Mohammed Ahmed, msa190000



public class Node<T extends Comparable<T>> implements Comparable<Node<T>>
{
	private T heldObject;
	private Node<T> nextNode;
	private Node<T> previousNode;

	public Node()
	{
		heldObject = null;
		nextNode = null;
		previousNode = null;
	}
	
	public Node(T someObject)
	{
		this();
		heldObject = someObject;
	}
	
	public T getObject()
	{
		return heldObject;
	}
	
	public Node<T> getNext()
	{
		return nextNode;
	}
	
	public Node<T> getPrevious()
	{
		return previousNode;
	}

	public void setObject(T someObject)
	{
		heldObject = someObject;
	}
	
	public void setNext(Node<T> aNode)
	{
		nextNode = aNode;
	}
	
	public void setPrevious(Node<T> aNode)
	{
		previousNode = aNode;
	}

	@Override
	public int compareTo(Node<T> o)
	{
		int result;
		result = heldObject.compareTo( (T) o.getObject() );
		
		return result;
	}
	
	@Override
	public String toString()
	{
		String result = heldObject.toString();
		return result;
	}

}
