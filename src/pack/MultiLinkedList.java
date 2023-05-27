package pack;

public class MultiLinkedList 
{
	HeadNode head;

	public MultiLinkedList()
	{
		head = null;
	}
	
	void deleteFile(String filename)
	{
		HeadNode temp = head;
		HeadNode pre =null;
		
		while(temp!= null)
		{
			if(temp.getFilename().replace("@", "").equals(filename))
				break;
			
			pre= temp;
			temp= temp.getDown();
		}
		
		if(temp.getFilename().equals(head.getFilename()) && head.getDown()== null)
			head= null;
		else if(temp.getFilename().equals(head.getFilename()) && head.getDown()!= null)
			head= head.getDown();
		else
			pre.setDown(temp.getDown());
	}
	
	int sizeOfFile(String filename)
	{
		HeadNode temp = head;
		Node temp2;
		int count =0;
		
		while(!temp.getFilename().replace("@", "").equals(filename))
			temp = temp.getDown();
		
		temp2= temp.getRight();
		
		while(temp2 !=null)
		{
			temp2 =temp2.getRight();
			count++;
		}
		
		return count;
	}

	void insert(String filename, String number, String data)
	{
		HeadNode temp1 = head;
		Node temp2 =null;
		int adrs =Integer.parseInt(number);

		while(true)
		{
			String listdata = temp1.getFilename().replace("@", "");
			if(listdata.equals(filename))
				break;
			temp1 = temp1.getDown();
		}
		temp2 = temp1.getRight();

		while(true)
		{
			if(data.length()< 11)
			{
				Node newnode= new Node(data);
				
				if(adrs == 1 && temp2== null)
				{
					temp1.setRight(newnode);
				}
				else if(adrs == 1 && temp2!= null)
				{
					temp1.setRight(newnode);
					newnode.setRight(temp2);
				}
				else
				{
					Node prev= null;
					temp2= temp1.getRight();

					for(int i =0; i< adrs-1; i++)
					{
						prev= temp2;
						temp2= temp2.getRight();
					}

					if(temp2== null)
						prev.setRight(newnode);
					else
					{
						prev.setRight(newnode);
						newnode.setRight(temp2);
					}
				}
				break;
			}
			else if(data.length()> 10)
			{
				Node newnode= new Node(data.substring(0, 10));
				data= data.substring(10);

				if(adrs == 1 && temp2== null)
				{
 					temp1.setRight(newnode);
					adrs++;
					temp2 =temp1.getRight();
				}
				else if(adrs == 1 && temp2!= null)
				{
					temp1.setRight(newnode);
					newnode.setRight(temp2);
					adrs++;
				}
				else
				{
					Node prev= null;
					temp2= temp1.getRight();
					
					for(int i =0; i< adrs-1; i++)
					{
						prev= temp2;
						temp2= temp2.getRight();
					}

					if(temp2== null)
					{
						prev.setRight(newnode);
						temp2= newnode;
					}
					else
					{
						prev.setRight(newnode);
						newnode.setRight(temp2);
					}
					adrs++;
				}
			}
		}
	}

	void append(String data, String filename)
	{
		HeadNode temp1 = head;
		Node temp2 =null;

		while(true)
		{
			String listdata = temp1.getFilename().replace("@", "");
			if(listdata.equals(filename))
				break;
			temp1 = temp1.getDown();
		}
		temp2= temp1.getRight();

		while(temp2 != null && temp2.getRight() != null)
			temp2= temp2.getRight();

		while(true)
		{
			if(data.length()< 11)
			{
				if(temp1.getRight()== null)
				{
					Node newnode= new Node(data);
					temp1.setRight(newnode);
				}
				else
				{
					Node newnode= new Node(data);
					temp2.setRight(newnode);
				}
				break;
			}
			else if(data.length()> 10)
			{
				Node newnode= new Node(data.substring(0, 10));
				data= data.substring(10);

				if(temp1.getRight()== null)
				{
					temp1.setRight(newnode);
					temp2 = temp1.getRight();
				}
				else
				{
					temp2.setRight(newnode);
					temp2 = temp2.getRight();
				}
			}
		}
	}

	public void create(String filename)
	{
		filename = "@" + filename;
		HeadNode newNode = new HeadNode(filename, Vdisk.searchspace()+1);

		if(head == null)
		{
			head = newNode;
		}
		else
		{
			HeadNode temp = head;

			while(temp.getDown() != null)
			{
				temp = temp.getDown();
			}

			temp.setDown(newNode);
		}
	}

	void print(String filename)
	{
		filename = "@" + filename;
		HeadNode temp = head;
		Node temp2;

		while(!temp.getFilename().equals(filename))
		{
			temp = temp.getDown();
		}

		temp2= temp.getRight();
		System.out.print(filename + " : ");

		while(temp2!= null)
		{
			System.out.print(temp2.getData());

			temp2 = temp2.getRight();
		}
		System.out.println();
	}

	boolean isFileEmpty(String filename)
	{
		filename = "@" + filename;
		HeadNode temp = head;
		Node temp2;

		while(!temp.getFilename().equals(filename))
		{
			temp = temp.getDown();
		}

		if(temp.getRight() == null)
			return true;
		else
			return false;
	}


}
