package pack;

public class Node 
{
	private String data;
	private Node right; 

	public Node(String dataToAdd) 
	{
		data = dataToAdd;
		right = null;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	
}
