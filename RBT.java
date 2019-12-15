import java.util.*;
public class RBT extends risingCity {
	 Node root;
	 Node Ext;

	
	
void Delete_fixup(Node n) {
		Node temp;
		while (n != root && n.colour == 0) {
			if (n == n.parent_ptr.left_ptr) {
				temp = n.parent_ptr.right_ptr;
				if (temp.colour == 1) {
					
					temp.colour = 0;
					n.parent_ptr.colour = 1;
					leftRotate(n.parent_ptr);
					temp = n.parent_ptr.right_ptr;
				}

				if (temp.left_ptr.colour == 0 && temp.right_ptr.colour == 0) {
					
					temp.colour = 1;
					n = n.parent_ptr;
				} else {
					if (temp.right_ptr.colour == 0) {
						
						temp.left_ptr.colour = 0;
						temp.colour = 1;
						rightRotate(temp);
						temp = n.parent_ptr.right_ptr;
					} 

					
					temp.colour = n.parent_ptr.colour;
					n.parent_ptr.colour = 0;
					temp.right_ptr.colour = 0;
					leftRotate(n.parent_ptr);
					n = root;
				}
			} else {
				temp = n.parent_ptr.left_ptr;
				if (temp.colour == 1) {
					
					temp.colour = 0;
					n.parent_ptr.colour = 1;
					rightRotate(n.parent_ptr);
					temp = n.parent_ptr.left_ptr;
				}

				if (temp.right_ptr.colour == 0 && temp.right_ptr.colour == 0) {
					
					temp.colour = 1;
					n = n.parent_ptr;
				} else {
					if (temp.left_ptr.colour == 0) {
						
						temp.right_ptr.colour = 0;
						temp.colour = 1;
						leftRotate(temp);
						temp = n.parent_ptr.left_ptr;
					} 

					
					temp.colour = n.parent_ptr.colour;
					n.parent_ptr.colour = 0;
					temp.left_ptr.colour = 0;
					rightRotate(n.parent_ptr);
					n = root;
				}
			} 
		}
		n.colour = 0;
	}


	 void Trans(Node a, Node b){
		if (a.parent_ptr == null) 
			root = b;
		 else if (a == a.parent_ptr.left_ptr)
			a.parent_ptr.left_ptr = b;
		 else 
			a.parent_ptr.right_ptr = b;
		
		b.parent_ptr = a.parent_ptr;
	}

	 void deleteNodeUtil(Node node, int key) {
		// find the node containing key
		Node z = Ext;
		Node x, w;
		node =searchTreeHelper(root,key);
		

		if (z == Ext) {
			//System.out.println("Key Not found");
			return;
		} 

		w = z;
		int y_col = w.colour;
		if (z.left_ptr == Ext) {
			x = z.right_ptr;
			Trans(z, z.right_ptr);
		} else if (z.right_ptr == Ext) {
			x = z.left_ptr;
			Trans(z, z.left_ptr);
		} 
		else {
			w = get_min(z.right_ptr);
			y_col = w.colour;
			x = w.right_ptr;
			if (w.parent_ptr == z) {
				x.parent_ptr = w;
			} else {
				Trans(w, w.right_ptr);
				w.right_ptr = z.right_ptr;
				w.right_ptr.parent_ptr = w;
			}

			Trans(z, w);
			w.left_ptr = z.left_ptr;
			w.left_ptr.parent_ptr = w;
			w.colour = z.colour;
		}
		if (y_col == 0){
			Delete_fixup(x);
		}
	}


	
	// fix the red-black tree
	 void insert_fixup(Node new_node){
		Node aunt;
		while (new_node.parent_ptr.colour == 1) {
			if (new_node.parent_ptr == new_node.parent_ptr.parent_ptr.right_ptr) {
				aunt = new_node.parent_ptr.parent_ptr.left_ptr; // uncle
				if (aunt.colour == 1) {
					// case 3.1
					aunt.colour = 0;
					new_node.parent_ptr.colour = 0;
					new_node.parent_ptr.parent_ptr.colour = 1;
					new_node = new_node.parent_ptr.parent_ptr;
				} else {
					if (new_node == new_node.parent_ptr.left_ptr) {
						// case 3.2.2
						new_node = new_node.parent_ptr;
						rightRotate(new_node);
					}
					// case 3.2.1
					new_node.parent_ptr.colour = 0;
					new_node.parent_ptr.parent_ptr.colour = 1;
					leftRotate(new_node.parent_ptr.parent_ptr);
				}
			} else {
				aunt = new_node.parent_ptr.parent_ptr.right_ptr; // uncle

				if (aunt.colour == 1) {
					// mirror case 3.1
					aunt.colour = 0;
					new_node.parent_ptr.colour = 0;
					new_node.parent_ptr.parent_ptr.colour = 1;
					new_node = new_node.parent_ptr.parent_ptr;	
				} else {
					if (new_node == new_node.parent_ptr.right_ptr) {
						
						new_node = new_node.parent_ptr;
						leftRotate(new_node);
					}
					
					new_node.parent_ptr.colour = 0;
					new_node.parent_ptr.parent_ptr.colour = 1;
					rightRotate(new_node.parent_ptr.parent_ptr);
				}
			}
			if (new_node == root) {
				break;
			}
		}
		root.colour = 0;
	}

	

	 Node get_min(Node node) {
		while (node.left_ptr != Ext) {
			node = node.left_ptr;
		}
		return node;
	}

	


	

	
	 void leftRotate(Node x) {
		Node w = x.right_ptr;
		x.right_ptr = w.left_ptr;
		if (w.left_ptr != Ext) {
			w.left_ptr.parent_ptr = x;
		}
		w.parent_ptr = x.parent_ptr;
		if (x.parent_ptr == null) {
			this.root = w;
		} else if (x == x.parent_ptr.left_ptr) {
			x.parent_ptr.left_ptr = w;
		} else {
			x.parent_ptr.right_ptr = w;
		}
		w.left_ptr = x;
		x.parent_ptr = w;
	}

	// rotate right_ptr at node x
	 void rightRotate(Node x) {
		Node w = x.left_ptr;
		x.left_ptr = w.right_ptr;
		if (w.right_ptr != Ext) {
			w.right_ptr.parent_ptr = x;
		}
		w.parent_ptr = x.parent_ptr;
		if (x.parent_ptr == null) {
			this.root = w;
		} else if (x == x.parent_ptr.right_ptr) {
			x.parent_ptr.right_ptr = w;
		} else {
			x.parent_ptr.left_ptr = w;
		}
		w.right_ptr = x;
		x.parent_ptr = w;
	}

	
	 void insert(Node node) {
		
		
		
		node.exec_time=0;
		node.left_ptr=Ext;
		node.right_ptr=Ext;
		node.parent_ptr=null;
		node.colour=1;// a new node inserted is always red
		
		

		Node w = null;
		Node x = this.root;

		while (x != Ext) {
			w = x;
			if (node.buildingno < x.buildingno) {
				x = x.left_ptr;
			} else {
				x = x.right_ptr;
			}
		}

		// w is parent_ptr of x
		node.parent_ptr = w;
		if (w == null) {
			root = node;
		} else if (node.buildingno < w.buildingno) {
			w.left_ptr = node;
		} else {
			w.right_ptr = node;
		}

		// if new node is a root node, simply return
		if (node.parent_ptr == null){
			node.colour = 0;
			return;
		}

		// if the grandparent is null, simply return
		if (node.parent_ptr.parent_ptr == null) {
			return;
		}

		// Fix the tree
		insert_fixup(node);
	}

	

	
	 void deleteNode(int buildingno) {
		deleteNodeUtil(this.root, buildingno);
	}

	
	
	void print(int bno,ArrayList<String> ans){
		Node cur=root;
		while(cur!=null){
			if(cur.buildingno == bno){
				ans.add("("+cur.buildingno+" "+ cur.exec_time+" "+ cur.total_time+")\n");
			 System.out.print("("+cur.buildingno+" "+ cur.exec_time+" "+ cur.total_time+") ");
			  System.out.println();
			  return;
			}
			else if(bno>cur.buildingno)
				cur=cur.right_ptr;
			else
				cur=cur.left_ptr;
		}
	}

	void printRange(int b1,int b2,ArrayList<String> ans)
	{

		Node cur=root;
		while(cur!=null){
			if(cur.buildingno>=b1 && cur.buildingno<=b2){

				recurPrint(cur,b1,b2,ans);
				
				return;
			}
			else if(cur.buildingno<b1){
				cur=cur.right_ptr;
			}
			else{
				cur=cur.left_ptr;
			}
		}
	}

	void recurPrint(Node cur,int b1,int b2,ArrayList<String> ans){
		if(cur==Ext ) return;

		if(cur.buildingno==b1){
			recurPrint(cur.right_ptr,b1,b2,ans);
			if(cur.total_time!=cur.exec_time)
			ans.add("("+cur.buildingno+" "+ cur.exec_time+" "+ cur.total_time+") ");
			System.out.print("("+cur.buildingno+" "+ cur.exec_time+" "+ cur.total_time+") ");
		}
		else if(cur.buildingno==b2){
			recurPrint(cur.left_ptr,b1,b2,ans);
			if(cur.total_time!=cur.exec_time)
			ans.add("("+cur.buildingno+" "+ cur.exec_time+" "+ cur.total_time+") ");
			System.out.print("("+cur.buildingno+" "+ cur.exec_time+" "+ cur.total_time+") ");
		
		}
		else
		{
			if(cur.left_ptr!=Ext && inRange( cur.left_ptr.buildingno, b1,b2)){
			recurPrint(cur.left_ptr,b1,b2,ans);
			}	
			if(cur.total_time!=cur.exec_time)
			ans.add("("+cur.buildingno+" "+ cur.exec_time+" "+ cur.total_time+") ");
			System.out.print("("+cur.buildingno+" "+ cur.exec_time+" "+ cur.total_time+") ");
			if(cur.right_ptr!=Ext && inRange(cur.right_ptr.buildingno,b1,b2))
				recurPrint(cur.right_ptr,b1,b2,ans);
		}
	}

	boolean inRange(int target,int l,int h){
		if(target>=l && target<=h)
			return true;
		return false;
	}

	public RBT() {
		Ext = new Node();
		Ext.buildingno=-1;
		Ext.colour = 0;
		Ext.left_ptr = null;
		Ext.right_ptr = null;
		root = Ext;
	}

	 Node searchTreeHelper(Node node, int key) {
		if (node == Ext || key == node.buildingno) {
			return node;
		}

		if (key < node.buildingno) {
			return searchTreeHelper(node.left_ptr, node.buildingno);
		} 
		return searchTreeHelper(node.right_ptr, node.buildingno);
	}





	
}