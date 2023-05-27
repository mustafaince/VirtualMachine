package pack;

public class HeadNode 
{
	private String filename;
	private HeadNode down; 
	private Node right; 
	private int diskadrs;

	public HeadNode(String dataToAdd, int address) 
	{
		filename = dataToAdd;
		right = null;
		diskadrs =address;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public HeadNode getDown() {
		return down;
	}

	public void setDown(HeadNode down) {
		this.down = down;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public int getDiskadrs() {
		return diskadrs;
	}

	public void setDiskadrs(int diskadrs) {
		this.diskadrs = diskadrs;
	}

	
}
