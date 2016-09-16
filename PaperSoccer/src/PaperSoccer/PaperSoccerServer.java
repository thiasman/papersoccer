package PaperSoccer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class PaperSoccerServer extends JFrame implements ActionListener{
	
	protected static Node CurrentNode = new Node(7,5);
	protected Field Board = new Field();
	protected static boolean Myturn = true;
	protected static String ClientTurn = " Player 1's turn ";
	private JTextArea text;
	private static Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private int GridSize =1;
	private Boolean GameInitialize = false;
	protected static Boolean AI = false;

	  private JPanel container = new JPanel();
	  private JComboBox combo = new JComboBox(), combo2 = new JComboBox();
	  private JLabel label = new JLabel("GRID SIZE"), label2 = new JLabel("PvP or PvAI");
	  private JButton button = new JButton("Initialize the game");
	  
	public PaperSoccerServer(){
	    this.setTitle("PaperSoccer Server");
	    this.setSize(300, 300);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
	    
	    this.setResizable(false);

	    container.setLayout(new BorderLayout());
	    combo.setPreferredSize(new Dimension(200, 20));
	    combo.addItem("10 - 14");
	    combo.addItem("14 - 18");
	    combo.addItem("18 - 22");
	    combo.addItem("22 - 26");
	    
	    combo2.setPreferredSize(new Dimension(200, 20));
	    combo2.addItem("Player Vs Player");
	    combo2.addItem("Player Vs AI");
	
	    JPanel top = new JPanel();
	    top.add(label);
	    top.add(combo);
	    top.add(label2);
	    top.add(combo2);
	    button.addActionListener(this);
	    top.add(button);
	    
	    container.add(top, BorderLayout.CENTER);
	   
	    this.setContentPane(container);
	    
	    this.setVisible(true);
	    
		for (int i =0; i<=14;i++){
			for (int j =0; j<=10;j++){
				Board.GetList().add(new Node(i,j));		
			}}
		
		AddConnection();
	  }

	public static void main(String[] args){
		
		ServerSocket socketserver  ;
		
		try {
		socketserver = new ServerSocket(1110);
        System.out.println("Server is on the PORT "+socketserver.getLocalPort());
		Thread t = new Thread(new Accept_clients(socketserver));
		t.start();
		
		System.out.println("Server ready!");
     
			}catch (IOException e) {
		
		e.printStackTrace();
			
	}

	}

	public String getClientTurn() {
		return ClientTurn;
	}

	public void setClientTurn(String clientTurn) {
		ClientTurn = clientTurn;
	}
	
	//add the connection to each node in order to show the borders of the field
	public void AddConnection(){
		for (int x =0; x<=14;x++){
			for (int y =0; y<=10;y++){
				if(Board.GetField()[x][y]==2){
					Board.GetNode(x,y).getNodeListConnexion().add(Board.GetNode(x+1,y));
					Board.GetNode(x,y).getNodeListConnexion().add(Board.GetNode(x-1,y));}
				if(Board.GetField()[x][y]==3 || Board.GetField()[x][y]==9 || Board.GetField()[x][y]==9){
					Board.GetNode(x,y).getNodeListConnexion().add(Board.GetNode(x,y+1));
					Board.GetNode(x,y).getNodeListConnexion().add(Board.GetNode(x,y-1));}
				if(Board.GetField()[x][y]==5){
					Board.GetNode(x,y).getNodeListConnexion().add(Board.GetNode(x,y+1));
					Board.GetNode(x,y).getNodeListConnexion().add(Board.GetNode(x-1,y));}
				if(Board.GetField()[x][y]==6){
					Board.GetNode(x,y).getNodeListConnexion().add(Board.GetNode(x+1,y));
					Board.GetNode(x,y).getNodeListConnexion().add(Board.GetNode(x,y+1));}
				if(Board.GetField()[x][y]==7){
					Board.GetNode(x,y).getNodeListConnexion().add(Board.GetNode(x,y-1));
					Board.GetNode(x,y).getNodeListConnexion().add(Board.GetNode(x+1,y));}
				if(Board.GetField()[x][y]==8){
					Board.GetNode(x,y).getNodeListConnexion().add(Board.GetNode(x,y-1));
					Board.GetNode(x,y).getNodeListConnexion().add(Board.GetNode(x-1,y));}
		
	}

}
}


	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == button && !GameInitialize){
			if(combo.getSelectedItem().equals("10 - 14")){
				System.out.println("10 - 14");
				GridSize =1;
			}
			else if(combo.getSelectedItem().equals("14 - 18")){
				System.out.println("14 - 18");
				GridSize =2;
			}
			else if(combo.getSelectedItem().equals("18 - 22")){
				System.out.println("18 - 22");
				GridSize =3;
			}
			else if(combo.getSelectedItem().equals("22 - 26")){
				System.out.println("22 - 26");
				GridSize =4;
			}
			if(combo2.getSelectedItem().equals("Player Vs Player")){
				System.out.println("Player Vs Player");
				AI = false;
			}
			if(combo2.getSelectedItem().equals("Player Vs AI")){
				System.out.println("Player Vs AI");
				AI =true;
			}
			GameInitialize=true;
		}
	}


}
class Accept_clients extends PaperSoccerServer implements Runnable {

		  
		private ServerSocket socketserver;
		   private Socket socket;
		   private int nbrclient = 0;
			
			public Accept_clients(ServerSocket S1){
				socketserver = S1;
			}
			
			public void run() {

		        try {
		        	
		        		System.out.println(AI);
		        	while(nbrclient<=2){
		  			  socket = socketserver.accept();
		  			  nbrclient++;// Un client se connecte on l'accepte
	                  System.out.println("Client number "+nbrclient+ " is connected !");
	                  if(nbrclient ==1){
	                	  	Thread t1;
	                	  	t1 = new Thread(new PaperSoccerClient("Client 1",socket));
	                	  	t1.start();
	                	  	
	                	 
	                  }
	                  if(nbrclient ==2){
	                	  	Thread t3;
	                	  	t3 = new Thread(new PaperSoccerClient("Client 2",socket));
	                	  	t3.start();
	                	  }

		        	} 
		        	
	       	
		        
		        } catch (IOException e) {
					e.printStackTrace();
				}
	
}
}

