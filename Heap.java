import java.util.*;

public class Heap extends risingCity {
     Node h[]=new Node[2000];//
     Node dummy;
    
    int cursize;
    Heap(){
    	dummy=new Node(Integer.MIN_VALUE,Integer.MIN_VALUE);
    	h[0] = dummy;
     	h[0].exec_time= -1;
     	cursize=1;

    }
     void insert(Node key)
    
    {
        
        h[cursize++]=key;
        int last=cursize-1;
     while( h[last].exec_time < h[parent(last)].exec_time)
        {
            swap(last,parent(last));
            last=parent(last);
           
        }

        while(h[last].exec_time==h[parent(last)].exec_time){
        	if(h[last].buildingno<h[parent(last)].buildingno)
        	{
              
        		swap(last,parent(last));
           	    last=parent(last);
               
        	}
        	else break;
        }
        minheapify(1); 
        
    }
  
     void minheapify(int index)
    
    {
        Node ele=h[index];
        Node min;
        int min_i=0;
        if(isLeaf(index))
            return;
        if(isRcThere(index)){
            if(ele.exec_time>h[leftChild(index)].exec_time||ele.exec_time>h[rightChild(index)].exec_time){
            if(h[leftChild(index)].exec_time<h[rightChild(index)].exec_time){
            	min=h[leftChild(index)];
                min_i=leftChild(index);
                swap(min_i,index);
                minheapify(min_i);
            }
            else if(h[leftChild(index)].exec_time>h[rightChild(index)].exec_time){
                min=h[rightChild(index)];
                min_i=rightChild(index);
                swap(min_i,index);
                minheapify(min_i); 
            }


            else if(h[leftChild(index)].exec_time==h[(rightChild(index))].exec_time){

            	if(h[leftChild(index)].buildingno<h[rightChild(index)].buildingno){
                    //System.out.println("Dsffd");
            		min=h[leftChild(index)];
                     min_i=leftChild(index);
                     swap(min_i,index);
            minheapify(min_i);
                }
            	else{
                    //System.out.println("Dsfdsfdsffd");
            		min=h[rightChild(index)];
                    min_i=rightChild(index);
                    swap(min_i,index);
            minheapify(min_i);
                }
            }
        }
        else if(ele.exec_time==h[leftChild(index)].exec_time&&ele.exec_time==h[rightChild(index)].exec_time)
        {
           
                if(h[leftChild(index)].buildingno<h[rightChild(index)].buildingno && ele.buildingno>h[leftChild(index)].buildingno){
                    min=h[leftChild(index)];
                     min_i=leftChild(index);
                     swap(min_i,index);
            minheapify(min_i);
                }
                else if(h[leftChild(index)].buildingno>h[rightChild(index)].buildingno && ele.buildingno>h[rightChild(index)].buildingno){
                    min=h[rightChild(index)];
                    min_i=rightChild(index);
                    swap(min_i,index);
            minheapify(min_i);
                }
        }
        else if(ele.exec_time==h[leftChild(index)].exec_time)
        {
                if(ele.buildingno>h[leftChild(index)].buildingno){
                    min=h[leftChild(index)];
                     min_i=leftChild(index);
                     swap(min_i,index);
                     minheapify(min_i);
                }
        }
        else if(ele.exec_time==h[rightChild(index)].exec_time)
        {
                if(ele.buildingno>h[rightChild(index)].buildingno){
                    min=h[rightChild(index)];
                     min_i=rightChild(index);
                     swap(min_i,index);
            minheapify(min_i);
                }
        }
    }

        else if(ele.exec_time>h[leftChild(index)].exec_time){
            min=h[leftChild(index)];
             min_i=leftChild(index);
             swap(min_i,index);
            minheapify(min_i);
        }
        else if(ele.exec_time==h[leftChild(index)].exec_time){
            if(ele.buildingno>h[leftChild(index)].buildingno){
                    min=h[leftChild(index)];
                     min_i=leftChild(index);
                     swap(min_i,index);
                     minheapify(min_i);
                }   
        }
       

    }
     boolean isRcThere(int p)
    {
        if((2*p+1)<cursize)
            return true;
        return false;
    }
     boolean isLeaf(int index)
    {
        if((2*index+1)>cursize-1)
            return true;
        return false;
    }
     int leftChild(int p)
    {
        
        return 2*p;
    }
     int rightChild(int p)
    {
        
            return 2*p+1;
        
        
    }        
     int parent(int pos)
    {

        int p=(pos)/2;
        if(p<0)
            return 0;
        return p;
    }
     void swap(int child,int parent)
    {
        Node temp=h[child];
        h[child]=h[parent];
        h[parent]=temp;
        
    }
     void print()
    {
       
        for(int i=1;i<cursize;i++){
            Node x=h[i];
            System.out.println(x.buildingno+" "+ x.exec_time+" "+ x.total_time);
        }
        
    }

    Node remove()
    {
        Node r=h[1];

        if(cursize>2){
        h[1]=h[cursize-1];
        cursize--;
        minheapify(1);
    }
    else{
    	h[1]=null;
    	cursize=1;
    }
    	

        return r;
    }

        
    }

