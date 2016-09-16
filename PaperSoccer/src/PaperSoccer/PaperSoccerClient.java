package PaperSoccer;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;













import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class PaperSoccerClient extends PaperSoccerServer implements Runnable, MouseListener, ActionListener
											{

	private Socket socket;
	private String nomclient;
//	private static BufferedReader in = null;
//	private static PrintWriter out= null;
	private Player myplayer ;
	private JPanel container = new JPanel();
	private JTextField jtf = new JTextField("Player's name");
	private JButton ClientButton = new JButton("Start");
	private JLabel label = new JLabel("Player's Name");
	private static Boolean IsGameOver = false;
	private Boolean stop = false;
	private static String WinnerName;
	
	
	public PaperSoccerClient(String a,Socket S){

	    setNomclient(a);
	    this.setTitle(nomclient);
	    this.setSize(200, 300);
	    this.setLocationRelativeTo(null);               
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setResizable(false);
	    
	    socket = S;
	    
	    container.setLayout(new BorderLayout());
	    
	    JPanel top = new JPanel();
	    Font police = new Font("Arial", Font.BOLD, 14);
	    jtf.setFont(police);
	    jtf.setPreferredSize(new Dimension(150, 30));
	    jtf.setForeground(Color.BLUE);
	    top.add(label);
	    top.add(jtf);
	    ClientButton.addActionListener(this);
	    top.add(ClientButton);
	    
	    container.add(top, BorderLayout.CENTER);
	    this.setContentPane(container);
	    
	    this.setVisible(true);
	}
	
	public static void main(String[] zero) {
		try {
			Socket S;
	   		S = new Socket(InetAddress.getLocalHost(),1110);
	   }
	   catch ( IOException e ) {
	      e.printStackTrace();         
	   }
}

	//This a second window which appears after the player put his name on the first window
	public void SecondWindow(){
	    this.setTitle(nomclient);
	    this.setSize(500, 600);
	    this.setLocationRelativeTo(null);               
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setContentPane(Board);
	    this.setResizable(false);
	    this.setVisible(true);
	    addMouseListener(this);
	    

		final JTextArea texte = new JTextArea();
	    getContentPane().add( texte, BorderLayout.CENTER);
	    
	    
		TimerTask task = new TimerTask()// Timer which actualize the display each second
		{
			
			public void run() 
			{
				Board.repaint();
				texte.setText(ClientTurn);
				if(IsGameOver && !stop){
					GameOver(WinnerName);
					stop = true;
				}
			}	
		};

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 1000);
	}
	
	public synchronized void mouseReleased(MouseEvent e) {
//		InetAddress addr;
//		try {
//			addr = InetAddress.getByName("localhost");
//			Socket socket = new Socket(addr, 1110);
//			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
//			out.println("boule");
//			
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		if(!AI){
		if(Myturn && nomclient.equals("Client 1")){
		for (int j =0; j<=14;j++){
			for (int i =0; i<=10;i++){
				if(e.getX()<=105+i*30&&e.getX()>=85+i*30 && e.getY()<=135+j*30&&e.getY()>=115+j*30){
					if(MovePossible(Board.GetNode(j, i))){
						if(Board.GetField()[Board.GetNode(j, i).getX()][Board.GetNode(j, i).getY()]==3){
							IsGameOver = true;
							WinnerName = "Client 1";
						}
						if(Board.GetField()[Board.GetNode(j, i).getX()][Board.GetNode(j, i).getY()]==10){
							IsGameOver = true;
							WinnerName = "Client 2";
						}
						if(Board.GetField()[Board.GetNode(j, i).getX()][Board.GetNode(j, i).getY()]==1)
						{
							Board.SetField(Board.GetNode(j, i),4);
							Board.GetNode(j,i).getNodeListConnexion().add(CurrentNode);
							CurrentNode.getNodeListConnexion().add(Board.GetNode(j,i));
							Board.SetField(Board.GetNode(CurrentNode.getX(),CurrentNode.getY()),11);
							CurrentNode = Board.GetNode(j, i);
							setClientTurn(" Player2's turn ");
							Myturn=false;
						}
						else{

							Board.SetField(Board.GetNode(j, i),4);
							Board.GetNode(j,i).getNodeListConnexion().add(CurrentNode);
							CurrentNode.getNodeListConnexion().add(Board.GetNode(j,i));
							Board.SetField(Board.GetNode(CurrentNode.getX(),CurrentNode.getY()),11);
							CurrentNode = Board.GetNode(j, i);
						}
					}
				}
			}
			}
		
		}
		
		if(Myturn==false && nomclient.equals("Client 2")){
			for (int j =0; j<=14;j++){
				for (int i =0; i<=10;i++){
					if(e.getX()<=105+i*30&&e.getX()>=85+i*30 && e.getY()<=135+j*30&&e.getY()>=115+j*30){
						if(MovePossible(Board.GetNode(j, i))){
							if(Board.GetField()[Board.GetNode(j, i).getX()][Board.GetNode(j, i).getY()]==3){
								IsGameOver = true;
								WinnerName = "Client 1";
							}
							if(Board.GetField()[Board.GetNode(j, i).getX()][Board.GetNode(j, i).getY()]==10){
								IsGameOver = true;
								WinnerName = "Client 2";
							}
							if(Board.GetField()[Board.GetNode(j, i).getX()][Board.GetNode(j, i).getY()]==1)
							{
								Board.SetField(Board.GetNode(j, i),4);
								Board.GetNode(j,i).getNodeListConnexion().add(CurrentNode);
								CurrentNode.getNodeListConnexion().add(Board.GetNode(j,i));
								Board.SetField(Board.GetNode(CurrentNode.getX(),CurrentNode.getY()),11);
								CurrentNode = Board.GetNode(j, i);
								setClientTurn(" Player1's turn ");
								Myturn=true;
							}
							else{

								Board.SetField(Board.GetNode(j, i),4);
								Board.GetNode(j,i).getNodeListConnexion().add(CurrentNode);
								CurrentNode.getNodeListConnexion().add(Board.GetNode(j,i));
								Board.SetField(Board.GetNode(CurrentNode.getX(),CurrentNode.getY()),11);
								CurrentNode = Board.GetNode(j, i);
							}
						}
					}
				}
				}
			}
		}
		//AI********************************************
		else {
			if(Myturn){
				for (int j =0; j<=14;j++){
					for (int i =0; i<=10;i++){
						if(e.getX()<=105+i*30&&e.getX()>=85+i*30 && e.getY()<=135+j*30&&e.getY()>=115+j*30){
							if(MovePossible(Board.GetNode(j, i))){
								if(Board.GetField()[Board.GetNode(j, i).getX()][Board.GetNode(j, i).getY()]==3){
									IsGameOver = true;
									WinnerName = "Client 1";
								}
								if(Board.GetField()[Board.GetNode(j, i).getX()][Board.GetNode(j, i).getY()]==10){
									IsGameOver = true;
									WinnerName = "Client 2";
								}
								if(Board.GetField()[Board.GetNode(j, i).getX()][Board.GetNode(j, i).getY()]==1)
								{
									Board.SetField(Board.GetNode(j, i),4);
									Board.GetNode(j,i).getNodeListConnexion().add(CurrentNode);
									CurrentNode.getNodeListConnexion().add(Board.GetNode(j,i));
									Board.SetField(Board.GetNode(CurrentNode.getX(),CurrentNode.getY()),11);
									CurrentNode = Board.GetNode(j, i);
									setClientTurn(" Player2's turn ");
									Myturn=false;
								}
								else{

									Board.SetField(Board.GetNode(j, i),4);
									Board.GetNode(j,i).getNodeListConnexion().add(CurrentNode);
									CurrentNode.getNodeListConnexion().add(Board.GetNode(j,i));
									Board.SetField(Board.GetNode(CurrentNode.getX(),CurrentNode.getY()),11);
									CurrentNode = Board.GetNode(j, i);
								}
							}
						}
					}
					}
				
				}
			
			if(!Myturn){Aimoves();}
			
			//AI PART******************************************************
			
			
		}
		}
		
	

	public boolean MovePossible(Node a){
		boolean B = false;
		if(Board.GetField()[a.getX()][a.getY()] == 0 || a.getNodeListConnexion().contains(CurrentNode)){
			B = false;
		}
		else if(CurrentNode.IsNeigboor(a))
		{
			B = true;
		}

		return B;
	}
	
	public void Aimoves(){
		//Really basic for the moment
		ArrayList<Node> PossibleMove = new ArrayList();
		for (int j =0; j<=14;j++){
			for (int i =0; i<=10;i++){
					if(MovePossible(Board.GetNode(j, i))){
						if(Board.GetField()[Board.GetNode(j, i).getX()][Board.GetNode(j, i).getY()]==3){
							IsGameOver = true;
							WinnerName = "Client 1";
						}
						if(Board.GetField()[Board.GetNode(j, i).getX()][Board.GetNode(j, i).getY()]==10){
							IsGameOver = true;
							WinnerName = "Client 2";
						}
			
						PossibleMove.add(Board.GetNode(j, i));
			}
		}
		}
		BestMove(PossibleMove);
	}
	
	
	public void BestMove(ArrayList<Node> List){
		
		Collections.sort(List);

		Board.SetField(Board.GetNode(List.get(0).getX(), List.get(0).getY()),4);
		Board.GetNode(List.get(0).getX(),List.get(0).getY()).getNodeListConnexion().add(CurrentNode);
		CurrentNode.getNodeListConnexion().add(Board.GetNode(List.get(0).getX(),List.get(0).getY()));
		Board.SetField(Board.GetNode(CurrentNode.getX(),CurrentNode.getY()),11);
		CurrentNode = Board.GetNode(List.get(0).getX(),List.get(0).getY());
		setClientTurn(" AI turn ");
		Myturn=true;
	}
	
	public void GameOver(String s){
		this.dispose();
		System.out.println("The winner is" +s);
		JFrame frame = new JFrame();
		frame.setTitle("GAME OVER");
	    frame.setSize(200, 200);
	    frame.setLocationRelativeTo(null);               
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setResizable(false);
	    frame.setVisible(true);
	    if(s.equals("nomclient")){
	    	myplayer.Score(true);
	    }
	    
	    else{
	    	myplayer.Score(false);
	    }
	    

	}
	
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}


	public String getNomclient() {
		return nomclient;
	}


	public void setNomclient(String nomclient) {
		this.nomclient = nomclient;
	}

	public void actionPerformed(ActionEvent e) {
		
		//myplayer = new Player(jtf.getText());
		this.dispose();
		SecondWindow();
		

		
	}
	
	public void run() {
		
//		try {
//			InetAddress addr = InetAddress.getByName("localhost");
//			Socket socket = new Socket(addr, 1110);
//			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			String line = in.readLine();
//			System.out.println(line);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		}

		}







