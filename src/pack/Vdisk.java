package pack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Vdisk 
{
	static Block[] blocks=new Block[30];

	public Vdisk()
	{
		for (int i = 0; i < blocks.length; i++) 
		{
			blocks[i]=new Block();
		}
	}
	
	void deleteFile(String filename)
	{
		int a= searchfile(filename)+1;
		int b=0;
		
		while(a!= 0)
		{
			for (int i = 0; i < 10; i++) 
			{
				blocks[a-1].ary[i]= '.';
			}
			
			b= a-1;
			a= blocks[a-1].number;
			blocks[b].number= 0;
		}
		
	}

	void store()
	{
		try
		{
			FileWriter fWriter=new FileWriter("store.txt");
			BufferedWriter writer=new BufferedWriter(fWriter);

			for (int i = 0; i < blocks.length; i++) 
			{
				for (int j = 0; j <10; j++) 
				{
					writer.write(blocks[i].ary[j]);
				}
				if(i != 29)
					writer.write(Integer.toString(blocks[i].number)+ "&");
				else
					writer.write(Integer.toString(blocks[i].number));
			}
			writer.close();
		}

		catch(IOException e)
		{
			System.out.println("Error");
		}
	}

	void restore(String [] rowsplit)
	{
		for (int i = 0; i < rowsplit.length;i++) 
		{
			for (int j = 0; j < 10; j++) 
			{
				blocks[i].ary[j]= rowsplit[i].charAt(j);
			}
			if(rowsplit[i].length() >11)
				blocks[i].number= (10*((int)rowsplit[i].charAt(10)-48)+ (int)rowsplit[i].charAt(11)-48);
			else
				blocks[i].number= (int)rowsplit[i].charAt(10)-48;
		}
	}

	void insert(String filename, String position, String data)
	{
		int empty= searchspace();
		int temp_empty=empty;
		int length =0;
		int i=0;
		int j=0;
		int count=0;
		int count2=0;
		int whichblock = Integer.parseInt(position);

		do
		{
			if(data.length()-length < 10)
				length= data.length()-length;
			else
				length=10;

			if(blocks[searchfile(filename)].number ==0)
			{
				blocks[searchfile(filename)].number = empty+1;
				whichblock++;
			}

			else if(whichblock == 1)
			{
				int a= blocks[searchfile(filename)].number;
				blocks[searchfile(filename)].number= empty+1;
				blocks[empty].number= a;
				whichblock++;
			}
			else
			{
				int a= blocks[searchfile(filename)].number;

				for (int k = 0; k < whichblock+ count2-2; k++) 
				{
					a= blocks[a-1].number;
				}

				int b= blocks[a-1].number;
				blocks[a-1].number= empty+1;
				blocks[empty].number= b;

				if(blocks[empty].number <0)
					blocks[empty].number= 0;
				count2++;
			}

			while(length!= 0 && empty!= -1)
			{
				blocks[empty].ary[i]= data.charAt(j);
				i++;
				j++;
				length--;
			}

			if(empty== -1)
				System.out.println("Error - Disk is Full");

			i=0;
			count++;
			length= 10*count;
			empty= searchspace();

		}while(data.length()-length> 0);
	}

	boolean isVdiskFull()
	{
		return blocks[29].ary[0] != '.';
	}

	int searchfile(String willsearch)
	{
		willsearch="@"+willsearch;

		if(willsearch.length()<11)
		{
			for (int i = 0; i < 30; i++) 
			{
				for (int j = 0; j < willsearch.length(); j++) 
				{
					if(blocks[i].ary[j] != willsearch.charAt(j))
						break;

					if(j == willsearch.length()-1 && ((j<10 && blocks[i].ary[j+1]== '.') || j== 10))
						return i;
				}
			}

			return -1;
		}
		else
			return -1;
	}

	static int searchspace()
	{
		for (int i = 0; i < blocks.length; i++) 
		{
			if(blocks[i].ary[0]=='.')
				return i;
		}

		return -1;
	}

	public void create(String filename)
	{
		int empty= searchspace();
		blocks[empty].ary[0]='@';

		for (int i = 1; i < filename.length()+1; i++) 
			blocks[empty].ary[i]= filename.charAt(i-1);
	}

	public void append(String appendword, String filename)
	{
		int empty= searchspace();
		int temp_empty=empty;
		int length =0;
		int i=0;
		int j=0;
		int count=0;

		do
		{
			if(appendword.length()-length < 10)
				length= appendword.length()-length;

			else
				length=10;

			blocks[lastblock(filename)].number= empty+1;

			while(length!= 0 && empty!= -1)
			{
				blocks[empty].ary[i]= appendword.charAt(j);
				i++;
				j++;
				length--;
			}

			if(empty== -1)
				System.out.println("Error - Disk is Full");

			i=0;
			count++;
			length= 10*count;
			empty= searchspace();

		}while(appendword.length()-length> 0);
	}

	public void printdisk()
	{
		if(Cpu.isLoad ==true)
			System.out.println();
		for(int i=0; i<6; i++)
		{
			if(i==0 || i==1)
			{
				System.out.print("0");
			}
			System.out.print((i*5)+1 + ":  ");

			for (int j = 0; j < 5; j++) 
			{
				for (int k = 0; k < blocks[(i*5)+j].ary.length; k++) 
				{
					System.out.print(blocks[(i*5)+j].ary[k]);
				}

				System.out.print(" " + blocks[(i*5)+j].number);
				if(blocks[(i*5)+j].number <10)
					System.out.print("       ");
				else
					System.out.print("      ");
			}

			System.out.println();
		}
	}

	public int lastblock(String filename)
	{
		int x= searchfile(filename)+1;
		int a;

		while(true)
		{
			a=blocks[x-1].number;

			if(a==0)
				return x-1;
			else
				x=a;
		}
	}

	int sizeOfFile(String filename)
	{
		int x= searchfile(filename)+1;
		int a;
		int count=0;

		while(true)
		{
			a=blocks[x-1].number;

			if(a==0)
				return count;
			else
				x=a;
			count++;
		}
	}
}
