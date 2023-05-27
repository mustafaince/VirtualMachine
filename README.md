In virtual machine there are three parts: CPU, Disk and RAM. CPU processes the command set. The
structures to process commands are in RAM. The function of the virtual Disk (vdisk) is to provide
permanency. Mainly, vdisk is simulated in the project.

###CPU is the part which processes the following command set.###
load file.txt // load and run an executable batch file from harddisk
print filename // print the content of the file on the screen
create filename // create a file
append filename &quot;Hello World&quot; // append data to the end of the file (as a block/blocks)
insert filename 3 &quot;Hello World&quot; // insert data into the file from the 3rd block (as a block/blocks)
delete filename // delete file
delete filename 5 7 // delete blocks from 5 to 7
printdisk // print all vdisk on the screen
defrag // defragment vdisk
store // save vdisk to harddisk as a file (vdisk.txt)
restore // restore vdisk from harddisk, clear RAM structures,
// and create appropriate new memory structures in the RAM
