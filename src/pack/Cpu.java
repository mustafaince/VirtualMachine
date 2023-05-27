package pack;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;
import enigma.core.Enigma;//çýkart

public class Cpu 
{
	public static enigma.console.Console cn = Enigma.getConsole("Virtual Machine", 150, 40);
	String command;
	Ram ram= new Ram();
	Vdisk vdisk= new Vdisk();
	static boolean isLoad;

	public Cpu()
	{
		while(true)
		{
			System.out.print("\ncommand> ");
			Scanner scn= new Scanner(System.in);
			command= scn.nextLine();
			Commands(command);
		}
	}
	
	void restoreRam()
	{
		Ram.list.head= null;
		
		for(int i=0; i<30; i++)
		{
			if(Vdisk.blocks[i].ary[0]== '@')
			{
				String filename="";
				
				for (int j = 1; j < 10; j++) 
				{
					filename+= Vdisk.blocks[i].ary[j];
					if(Vdisk.blocks[i].ary[j+1]== '.')
						break;
				}
				
				ram.create(filename);
				int a = Vdisk.blocks[i].number;
				
				while(a!= 0)
				{
					String data="";
					for (int j = 0; j < 10; j++) 
					{
						data += Vdisk.blocks[a-1].ary[j];
						if(j==9 || Vdisk.blocks[a-1].ary[j+1]== '.')
							break;
					}
					ram.append(data, filename);
					a = Vdisk.blocks[a-1].number;
				}
			}
		}
	}

	public boolean restore()
	{
		String str;
		File f=new File("store.txt");

		try
		{
			FileReader fr=new FileReader(f);
			BufferedReader br =new BufferedReader(fr);
			str= br.readLine();

			String[] rowsplit= str.split("&");
			vdisk.restore(rowsplit);
			restoreRam();
			br.close();
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Error - There is no stored data");
			return false;
		}
	}

	public void filereader(String file)
	{
		String str;
		File f= new File(file);

		try
		{
			FileReader fr= new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			str= br.readLine();

			while(str!=null)
			{
				isLoad= true;
				System.out.print(str+ " : ");
				Commands(str);
				isLoad = false;
				str= br.readLine();
			}

			br.close();
		}
		catch(Exception e)
		{
			System.out.println("Error - No such file");
		}
	}

	public void Commands(String command)
	{
		String temp ="";
		String[] commandsplit= command.trim().split(" ");


		if(commandsplit[0].equals("create"))
		{
			if(vdisk.isVdiskFull())
				System.out.println("Error - Disk is full");
			else if(commandsplit.length < 2)
				System.out.println("Error - Enter a filename");
			else if(commandsplit.length > 2)
				System.out.println("Error - Don't use space character");
			else if(commandsplit[1].length() > 9)
				System.out.println("Error - Filename is so long");
			else if(vdisk.searchfile(commandsplit[1]) == -1)
			{
				command= command.replace("create", "").trim();
				ram.create(command);
				vdisk.create(command);
				System.out.println("OK");
			}
			else 
				System.out.println("Error - File exists");
		}

		else if(commandsplit[0].equals("printdisk") && commandsplit.length == 1)
		{
			vdisk.printdisk();
		}

		else if(commandsplit[0].equals("append"))
		{
			if(commandsplit.length >1)
				temp = command.trim().replace("append " + commandsplit[1], "").trim();

			if(vdisk.isVdiskFull())
				System.out.println("Error - Disk is full");
			else if(commandsplit.length <2)
				System.out.println("Error - Enter a filename");
			else if(vdisk.searchfile(commandsplit[1]) == -1)
				System.out.println("Error - No such file");
			else if(commandsplit.length < 3 || temp.length()<3 || temp.charAt(1) ==' ')
				System.out.println("Error - Enter a data");
			else if(temp.charAt(0)!= '"' || temp.charAt(temp.length()-1) != '"')
				System.out.println("Error - Use \"data\" form while entering data");
			else
			{
				command= command.replace("append " + commandsplit[1], "").replace("\"", "").trim();
				ram.append(command, commandsplit[1]);
				vdisk.append(command, commandsplit[1]);
				System.out.println("OK");
			}
		}

		else if(commandsplit[0].equals("print"))
		{
			if(commandsplit.length <2)
				System.out.println("Error - Enter a filename");
			else if(vdisk.searchfile(commandsplit[1]) == -1 || commandsplit.length >2)
				System.out.println("Error - No such file");
			else if(ram.list.isFileEmpty(commandsplit[1]))
				System.out.println("Error - File is empty");
			else
				ram.print(commandsplit[1]);
		}

		else if(commandsplit[0].equals("load"))
		{
			if(commandsplit.length <2)
				System.out.println("Error - Enter a filename");
			else if(commandsplit.length >2)
				System.out.println("Error - No such file");
			else
				filereader(commandsplit[1]);
		}

		else if(commandsplit[0].equals("insert"))
		{
			if(commandsplit.length >3)
				temp = command.trim().replace("insert " + commandsplit[1]+" "+commandsplit[2], "").trim();

			if(vdisk.isVdiskFull())
				System.out.println("Error - Disk is full");
			else if(commandsplit.length <2)
				System.out.println("Error - Enter a filename");
			else if(vdisk.searchfile(commandsplit[1]) == -1)
				System.out.println("Error - No such file");
			else if(commandsplit.length <3)
				System.out.println("Error - Enter a number");
			else if(commandsplit.length <4 || temp.length()<3 || temp.charAt(1) ==' ')
				System.out.println("Error - Enter a data");
			else if(temp.charAt(0)!= '"' || temp.charAt(temp.length()-1) != '"')
				System.out.println("Error - Use \"data\" form while entering data");
			else if(Integer.parseInt(commandsplit[2]) > ram.list.sizeOfFile(commandsplit[1])+1)
				System.out.println("Error - Enter smaller number");
			else if(Integer.parseInt(commandsplit[2]) <1)
				System.out.println("Error - Enter higher number");
			else
			{
				temp= temp.replace("\"", "").trim();
				ram.insert(commandsplit[1], commandsplit[2], temp);
				vdisk.insert(commandsplit[1], commandsplit[2], temp);
				System.out.println("OK");
			}
		}

		else if(commandsplit[0].equals("store") && commandsplit.length==1)
		{
			vdisk.store();
			System.out.println("OK");
		}

		else if(commandsplit[0].equals("restore") && commandsplit.length==1)
		{
			if(restore())
				System.out.println("OK");
		}
		
		else if(commandsplit[0].equals("delete") && commandsplit.length == 2)
		{
			if(vdisk.searchfile(commandsplit[1]) == -1)
				System.out.println("Error - No such file");
			else
			{
				ram.deleteFile(commandsplit[1]);
				vdisk.deleteFile(commandsplit[1]);
				System.out.println("OK");
			}
		}

		else
		{
			System.out.println("Error - Invalid command");
		}
	}

}
