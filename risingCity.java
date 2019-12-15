import java.io.*;
import java.util.*;
/*
Class Project holds the parsed values of instructions from the file
such as "Print" and "Insert". It stores the following
  type of instruction- "Print" or "Insert"
  bno: building number
  globaltime: the time at which instruction comes
  total_time: total time to finish construction of the building
  b1: one of the parameter of "Print(b1)"
  b2: second parameter of Print type print range of values "Print(b1.b2)"

*/
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
    }
}


/*
  Node class is nothing but the building instance
    buildingno: the unique number of the building
    parent_ptr: a pointer to the current buildings parent in RedBlack Tree
    left_ptr: pointer to left child
    right_ptr: pointer to right child
    colour: Colour of the node of the Redblack tree 1--> Red 0-->Black
    exec_time: execution time
    total_time: totaltime.


*/

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
    Node(){}
}
/*
  About 
  1. The input file is read and parsed into Project class and put into a queue of instructions waiting to be
  executed when its time matches the running global time.

  2. Construction begins with globaltime pulling out first instruction from the queue which is assumed to be started at 
  0 so that the heap and red blacktree can be initialised.

  3. Now we work on the building which is at the top of the heap( which signifies the one which has the least executuion time
  and in case of same execution time ties are broken with building number which means the one with small building number is at
  top of the Min Heap)

  4. While executing certain building is worked on for 5 days. However in case some other instructions comes in between its execution
  then it is handled in two ways:
      Case 1: when certain building is being worked upon and a Print command comes in between. So the execution is paused and
      the command print gets executed and then again the execution of the previous building resumes after print is done.

      Case 2: when certain building is being worked upon and a Insert command comes in between. So, here the execution of building
      continues and the insert command is put in the Wait queue which is emptied at the end of 5 iterations of the current building.
      All the Building in the wait queue gets added to the minHeap along with the building which was worked upon recently together after
      5 iterations.

*/
class risingCity{
   
   static int globalTime=0;// globalTime
   static  BufferedWriter writer=null;
	public static void main(String [] args) throws FileNotFoundException{
        String filename= args[0];
         Queue<Project> construct=new LinkedList<>();// Queue of instructions
        RBT bst = new RBT();
        Heap h=new Heap();
      //Line 105 to Line 190: Reads the instruction from the file and adds it to the Construct Queue
      // and once all the instruction is added to the construct queue we start with the construction.
        BufferedReader br = null;
        BufferedWriter bw = null;
        FileWriter fw = null;
        try{    
          writer=new BufferedWriter(new FileWriter("Output.txt"));
            File file = new File(filename);
             if (!file.canRead()) {
            System.err.println(file.getAbsolutePath() + ": cannot be read");
             return;   
}   
           br = new BufferedReader(new FileReader(file));   
          
           
            String contentLine = br.readLine();
           ;
            String gt = " ", command = " ", totalT = " ";
            String builnum =" ", builnum1 = " ", builnum2 = " ";
            while (contentLine != null) {
                int gti = contentLine.indexOf(':');
                int i = contentLine.indexOf('(');
                int commaindex = contentLine.indexOf(',');
                int closeindex = contentLine.indexOf(')');
                if(i!=-1)
                {
                    gt = contentLine.substring(0,gti);
                  
                    command = contentLine.substring(gti+2,i);
          
                    if(command.equalsIgnoreCase("Insert"))
                    {

                        builnum = contentLine.substring(i+1,commaindex);
                        
                        totalT = contentLine.substring(commaindex+1,closeindex);
                        
                         Project new_proj=new Project(Integer.valueOf(gt),"Insert",Integer.valueOf(builnum),Integer.valueOf(totalT));
                         construct.add(new_proj);
                        
                    }
                    else{//Print
                        if(commaindex!=-1) // 2 parameters (range)
                        {
                            builnum1 = contentLine.substring(i+1,commaindex);
                         
                            builnum2 = contentLine.substring(commaindex+1,closeindex);
                            Project new_proj=new Project(Integer.valueOf(gt),"Print",Integer.valueOf(builnum1),Integer.valueOf(builnum2));
                            construct.add(new_proj);
                            //bw.write("TotalTime " + totalT + "\n");
                           // System.out.println("BuildingID2 " + builnum2 + "\n");
                        }
                        else //print one triplet
                        {
                            builnum = contentLine.substring(i+1,closeindex);
                            //bw.write(" BuildingID " + builnum);
                             Project new_proj=new Project(Integer.valueOf(gt),"Print",Integer.valueOf(builnum),-1);
                            construct.add(new_proj);
                           // System.out.println("BuildingID " + builnum);
                        }
                    }
                  
                }
                contentLine = br.readLine();
            }
       } 
       catch (IOException ioe) 
       {
        System.out.println("ASD");
       ioe.printStackTrace();
       } 
       finally 
       {
           try 
           {
              if (br != null)
                        br.close();
            if (bw != null)
                        bw.close();
            if (fw != null)
                        fw.close();     
           }
           catch (IOException ioe) 
           {
                System.out.println("Error in closing the BufferedReader");
           }
        }

        //line 192 to line199: Initialises and MinHeap and RBT with the instruction at the Front of the queue in order
        //to begin the constrctuion.
       Project in=construct.poll();
        //System.out.println(in.bno+" "+ in.total_time);
        Node x=new Node(in.bno,in.total_time);
        globalTime=0;
        x.exec_time=0;
        h.insert(x);
        bst.insert(x);
        
      startConstruction(construct,bst,h);// Construction begins
      try{
      writer.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

}

    static void startConstruction(Queue<Project> construct,RBT bst,Heap h){
      try{
        Queue<Node> wait=new LinkedList<>();// wait queue : adds all the "INSERT" instruction that comes in between execution
        //of the current building worked upon.
        int upcomingProject=-1;// this variable matches the globaLTime with the time of the next Instruction's time
        //if it matches it is extracted from the Construct Queue and depending on the type of instrcution it is dealt
        //with accordingly.
        
   
         while(h.h[1]!=null){
          
            Node current=h.h[1];// current holds the building which is at the top of the MinHeap and which will be worked
            //upon for 5 days.
            if(!construct.isEmpty())
                upcomingProject=construct.peek().globaltime;

            boolean Over=false;
            for(int i=0;i<5;i++){// the for loop denotes execution of 5 days work in current building


                if(current.exec_time!=current.total_time)//current building executiontime does not matches total_time
                {                                         //execution time and global time is incremented by 1.
                  current.exec_time++;
                  globalTime++;
                }

               if(current.exec_time==current.total_time)// exection time equals total time then the building entity is
                {                                       // is removed from the heap and the RBT after its printed.
                    if(globalTime==upcomingProject){
                 // Line 242 to line 271
                  // 1. this handles the situation when during the execution of current building 
                         // an Insert of Print command comes then 
                      // in case of insert command its building number isadded the wait queue and at the same time building in the
                      //insert command is added to the RBT as well

                      //else part handles the Print command which makes a call to the appropriate Print type and
                      //prints the building number at that moment by pausing the exection of current building.
                 if(construct.peek().type.equalsIgnoreCase("Insert")){
                  Project tp=construct.poll();// 
                      Node bb=new Node(tp.bno,tp.total_time);
                      bb.exec_time=0;
                     bst.insert(bb);
                     wait.add(bb);

                }
                  else{
                    writer.write("\n");
                    ArrayList<String> ans=new ArrayList<>();
                       Project p = construct.poll();
                       System.out.print(globalTime+" ");
                        if(p.b2==-1){
                          bst.print(p.b1,ans);
                          for(String a: ans)
                            writer.write(a);
                        }
                        else{
                         bst.printRange(p.b1,p.b2,ans);
                         for(String a: ans)
                            writer.write(a);
                        }

                        }
                
                    
                upcomingProject=construct.peek()!=null?construct.peek().globaltime: -1;
               
            }
                  
                  writer.write("\n"+current.buildingno+" "+ globalTime);
                    System.out.println(current.buildingno+" "+ globalTime);

                 
                  bst.deleteNode(current.buildingno);
                  h.remove();
                 

                    if(globalTime==upcomingProject){

                 if(construct.peek().type.equalsIgnoreCase("Insert")){
                  Project tp=construct.poll();
                      Node bb=new Node(tp.bno,tp.total_time);
                      bb.exec_time=1;
                     bst.insert(bb);
                     wait.add(bb);

                }
                  else{
                    writer.write("\n");
                      ArrayList<String> ans=new ArrayList<>();
                       Project p = construct.poll();
                       System.out.print(globalTime+" ");
                        if(p.b2==-1){
                          bst.print(p.b1,ans);
                          for(String a: ans)
                            writer.write(a);
                        }
                        else{
                         bst.printRange(p.b1,p.b2,ans);
                          for(String a:ans)
                            writer.write(a);
                        }

                        }
                
                        
                upcomingProject=construct.peek()!=null?construct.peek().globaltime: -1;
               
            }





               

                
                 
                   Over=true;
                    
                   break;

                }

               if(globalTime==upcomingProject){

                 if(construct.peek().type.equalsIgnoreCase("Insert")){
                  Project tp=construct.poll();
                      Node bb=new Node(tp.bno,tp.total_time);
                     bb.exec_time=1;
                     bst.insert(bb);
                     wait.add(bb);

                }
                  else{
                    writer.write("\n");
                    ArrayList<String> ans=new ArrayList<>();
                       Project p = construct.poll();
                       System.out.print(globalTime+" ");
                        if(p.b2==-1){
                          bst.print(p.b1,ans);
                        
                        for(String a: ans)
                            writer.write(a);
                        }
                        else{
                         bst.printRange(p.b1,p.b2,ans);
                         for(String a:ans)
                            writer.write(a);
                        }

                        }
                
                      
                upcomingProject=construct.peek()!=null?construct.peek().globaltime: -1;
               
            }


                   

    }
    if(!Over){
       h.remove();// removes building at the top of the minHeap
       h.insert(current);// inserts into the heap again so that it Minheapifies the heap
    }
    

    while(!wait.isEmpty())// empties the queue of instruction that came in middle of execution of current building
    {                     // adds all those building into the Heap and Rbt.
        Node assigned = wait.poll();
      
        h.insert(assigned);
       
    }
    // Line 387 to Line 417
        //Handles the case where the Heap and RBT is empty but there are still some instructions
        //pending to be executed so this while loop increments the globalTime by 1 until it reaches a stage where
        //it matches the time of the instruction such as Insert or Print and once the globalTime matches the 
        //instrcution's time in case of Insert it adds it to the Heap and RBT and begins the previous execution and in case
        //of print it does nothing just skips the execution( in other words it is just skipping that day as there is nothing to
    //to be printed as both heap and RBT are empty) 
   while(h.h[1]==null &&!construct.isEmpty())
   {
    //System.out.println();
      while(h.h[1]==null &&!construct.isEmpty()){
          if(construct.peek()!=null && construct.peek().globaltime==globalTime){
             if(construct.peek().type.equalsIgnoreCase("Insert")){
                  Project tp=construct.poll();
                      Node bb=new Node(tp.bno,tp.total_time);
                     bb.exec_time=1;
                     bst.insert(bb);
                     h.insert(bb);


                }
                 else{
                  
                    //System.out.println();
                       Project p = construct.poll();
                       System.out.println("0,0,0");
                        continue;

                    

                        } 
          }
          globalTime++;
      }
   }
  
}


}//try

catch(Exception e){
      e.printStackTrace();}
  }


}
