package pack;

public class Ram 
{
	static MultiLinkedList list= new MultiLinkedList();

	void create(String filename)
	{
		list.create(filename);
	}
	
	void append(String data, String filename)
	{
		list.append(data, filename);
	}

	void print(String filename)
	{
		list.print(filename);
	}
	
	void insert(String filename, String number, String data)
	{
		list.insert(filename, number, data);
	}
	
	void deleteFile(String filename)
	{
		list.deleteFile(filename);
	}
	
}
