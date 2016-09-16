package PaperSoccer;

import java.util.ArrayList;

public class Node extends Field implements java.lang.Comparable{
	private int x;
	private int y;
	private ArrayList<Node> NodeListConnexion = new ArrayList();

	public Node(){
	
	}
	
	//used for the AI part
	  public int compareTo(Object other) { 
		      int a1 = ((Node) other).getX(); 
		      int a2 = this.getX(); 
		      if (a1 > a2)  return -1; 
		      else if(a1 == a2) return 0; 
		      else return 1; 
		   }

	public Node(int i, int j) {
		setX(i);
		setY(j);
	}

	public Boolean IsNeigboor (Node n){
		if( n.x==x+1&&n.y==y-1 || n.x==x&&n.y==y-1 || n.x==x-1 && n.y==y+1 || n.x==x-1&&n.y==y || n.x==x-1&&n.y==y-1 || n.x==x&&n.y==y+1 || n.x==x+1&&n.y==y+1 || n.x==x+1&&n.y==y)
		{
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public ArrayList<Node> getNodeListConnexion() {
		return NodeListConnexion;
	}


	public void setNodeListConnexion(ArrayList<Node> nodeListConnexion) {
		NodeListConnexion = nodeListConnexion;
	}
}
