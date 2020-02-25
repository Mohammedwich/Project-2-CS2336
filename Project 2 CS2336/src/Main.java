//Mohammed Ahmed, msa190000


public class Main
{
	public static void main(String[] args)
	{
		String printedList;
		LinkedList<Integer> testList = new LinkedList<Integer>();
		
		Node<Integer> node1 = new Node<Integer>(5);
		Node<Integer> node2 = new Node<Integer>(2);
		Node<Integer> node3 = new Node<Integer>(9);
		Node<Integer> node4 = new Node<Integer>(3);
		
		testList.addNode(node1);
		testList.addNode(node2);
		testList.addNode(node3);
		testList.addNode(node4);
		
		printedList = testList.toString();
		System.out.println(printedList);
		
		testList.sort(true);
		
		printedList = testList.toString();
		System.out.println(printedList);
		

	}

}
