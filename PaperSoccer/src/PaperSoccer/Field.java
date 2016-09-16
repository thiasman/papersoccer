
package PaperSoccer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Field extends JPanel{
//all the differents values represents differents with differents connections
private static  int field[][] =       {{0,0,0,0,0,0,0,0,0,0,0},
			            			   {0,0,0,0,6,3,7,0,0,0,0},
			            			   {0,6,9,9,8,1,5,9,9,7,0},
			            			   {0,2,1,1,1,1,1,1,1,2,0},
			            			   {0,2,1,1,1,1,1,1,1,2,0},
			            			   {0,2,1,1,1,1,1,1,1,2,0},
			            			   {0,2,1,1,1,1,1,1,1,2,0},
			            			   {0,2,1,1,1,4,1,1,1,2,0},
			            			   {0,2,1,1,1,1,1,1,1,2,0},
			            			   {0,2,1,1,1,1,1,1,1,2,0},
			            			   {0,2,1,1,1,1,1,1,1,2,0},
			            			   {0,2,1,1,1,1,1,1,1,2,0},
			            			   {0,5,9,9,7,1,6,9,9,8,0},
			            			   {0,0,0,0,5,10,8,0,0,0,0},
			            			   {0,0,0,0,0,0,0,0,0,0,0}};

protected static ArrayList<Node> NodeList = new ArrayList();//contains all the node different for 0

public void paintComponent (Graphics g){

	for (int i =0; i<=14;i++){
		for (int j =0; j<=10;j++){
			if (field[i][j]!=1 && field[i][j]!=0 && field[i][j]!=4){
			g.setColor(Color.black);
			g.fillOval(95+j*30,95+i*30,10,10);
			}
			if(field[i][j]!=0 && field[i][j]!=4){g.setColor(Color.black);
				g.drawOval(95+j*30,95+i*30,10,10);}
			if(field[i][j]==4){
				g.setColor(Color.GREEN);
				g.fillOval(95+j*30,95+i*30,10,10);
			}
		}
		
	for (int a =0; a<=NodeList.size()-1;a++){
			if(NodeList.get(a).getNodeListConnexion().isEmpty()){
			}
			else{
				for(int b=0; b<=NodeList.get(a).getNodeListConnexion().size()-1;b++)
				{
					g.setColor(Color.red);
					Graphics2D g2 = (Graphics2D) g;
				    g2.setStroke(new BasicStroke(3));
					g2.drawLine(100 + NodeList.get(a).getY()*30 ,100+NodeList.get(a).getX()*30,100 + NodeList.get(a).getNodeListConnexion().get(b).getY()*30,100+NodeList.get(a).getNodeListConnexion().get(b).getX()*30);
				}
			}
	}

}
}
//This function is called in the papersoccerclient when a move is done
public void SetField (Node n, int a){
	field[n.getX()][n.getY()]=a;
	repaint();
}


public int[][] GetField(){
	return field;
}

public Node GetNode(int x, int y){
	int a = 0;
	for(int i = 0; i < NodeList.size(); i++)
	{
		if(NodeList.get(i).getX()==x && NodeList.get(i).getY()==y){
			a= i;
		}
	}
	return NodeList.get(a);
}

public ArrayList<Node> GetList(){
	return NodeList;
	
}
}