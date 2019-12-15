## Problem Description
Wayne Enterprises is developing a new city. They are constructing many buildings and plan to use software to keep track of all buildings under construction in this new city. A building record has the following fields:

**buildingNum**: unique integer identifier for each building.

**executed_time**: total number of days spent so far on this building.

**total_time**: the total number of days needed to complete the construction of the building.

The needed operations are:

1. Print (buildingNum) prints the triplet buildingNume,executed_time,total_time.

2. Print (buildingNum1, buildingNum2) prints all triplets bn, executed_tims, total_time for which buildingNum1 <= bn <= buildingNum2.

3. Insert (buildingNum,total_time) where buildingNum is different from existing building numbers and executed_time = 0.

Build a software that allows proper construction of building without any chaos. 
Used Minimum Heap to give unbiased attention to all the buildings and integrated Red Black Tree to allow printing of building in accordance to specified format above.

## Steps to execute
1. Extract all files to your local machine
2. Go to the above directory via command prompt
3.  Type javac *.java
4. Follow it by java risingCity Sample_Input2.txt
5. An Output.txt  file will be generated in the current directory which will have output as specified in the input file. 

# Overview of the Code

The entire code is divided into three files namely:

* risingCity.java

* Heap.java

* RBT.java

The files Heap.java and RBT.java inherits the properties of risingCity.java.

The reason I have used inheritance is because the risingCity class has Data structure Node which is as follows:  

	class Node {
		int buildingno;
		Node parent_ptr;
		Node left_ptr;
		Node right_ptr;
		int colour; // 1 . Red, 0 . Black
		int exec_time;
		int total_time;
		Node(int key,int tots){
		buildingno=key;
		total_time=tots;
		exec_time=0;
		colour=1;
	}


The above Node data structure is used by RBT.java to create a Tree Node and the same Node reference is used by Heap.java to create Minheap structure.

Heap.java makes use of following properties of Node

	Buildingno
	Exec_time
	Total_time

RBT.java makes Tree Node structure which uses following properties of Node

	Buildingno// unique key of Binary search tree
	Left_ptr
	Right_ptr
	Colour
	Parent_ptr
To build the connection between Heap and RBT I have used same node reference in both the structure so any change in the node of the heap will be reflected in the corresponding node in the RBT and vice-versa.

Implementation begins with list of commands being added to the Contruct 

## risingCity.java
* This class defines two data structures namely Node and the Project
* Project data structure breaks the instruction and store its  
time
  
		type= “Insert”, building number, total_time OR  
		type=”print”, b1,b2.

* B1 is the Building number to be printed and in case of Print(b1) b2 value is null by default.

* Print(b1,b2) take two positive number and prints all the building within that range by traversing the red black tree.

* The code reads all the instruction from the file and adds it to “Construct queue” by turning each instruction into the project data type.
* 
			class Project{
		    String type;
		    int bno;
		    int globaltime;
		    int total_time;
		    int b1,b2;
		    
		  Project(int gt,String command,int b,int t){//insert
		       globaltime=gt;
		        if(command.equalsIgnoreCase("Insert")){
		        type=command;
		        bno=b;
		        total_time=t;
	    }
		    else{
		        if(t==-1){
		            type=command;
		            b1=b;
		            b2=-1;
	        }
		        else{
		            type=command;
		            b1=b;
		            b2=t;
		        }
		    }
		}	}


* First instruction from this queue is used to initialize Heap and the RBT.

* Now the startContruction() is called to begin the construction.

* Instructions from the “Construct queue: are fetched as and when the globalTimer matches the time of the front of the Construct queue project.
* Every building instance is fetched from the Heap and is worked upon for 5 days. However, in case when some instruction comes in between the execution of current building then its is dealt in two ways

* If incoming instruction is Print type then it is immediately executed by calling the print() or printRange() in the RBT.java and after it is printed the execution of current building continues from where it left off.

* If incoming command is insert then the Node entity is created and inserted into RBT immediately and the same node is added to the wait queue which gets emptied after complete 5 days execution of the current building.

* Once 5 days execution is over the wait queue is emptied and added to the heap and if the current buildings execution time is not equal to its total_time then it gets added to the heap as well.

* Once the current building’s execution time equals total_time it gets is printed to the file along with the globalTime at that instant and then this building gets deleted from the redblack tree and heap and is never inserted again.

* The code also handles a case where heap is empty but there are still some instructions left in the “construct Queue” to be executed but it comes at some point later. So I have used a logic where it brings the globalTimer to that day where it matches the time of the instruction waiting the the front of the queue so that it can be fetched and executed. In case of Insert it gets removed from the “Construct Queue” and is added to the Heap and RBT and thus allowing the same normal execution as explained before this point. But in case of Print it simply skips the day as there is nothing to be printed because both Heap and RBT are empty. This goes on until there comes some Insert command which will reinitialize the Heap and RBT so that execution begins again the same way it started with.
    


