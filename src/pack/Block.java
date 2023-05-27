package pack;

public class Block 
{
	char [] ary=new char[10];
	int number;
	
	public Block()
	{
		for (int i = 0; i < ary.length; i++) 
		{
			ary[i]='.';
		}
		number=0;
	}
}
