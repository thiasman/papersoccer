package PaperSoccer;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//This class can write in a .txt but not change specific line yet
public class Player extends PaperSoccerServer{
	private String Name;
	private int numbwin =0;
	private int numblos =0;
	//private DataOutputStream Output;
	private Boolean AlreadyPlayed = false;
	private int PlayerLine;
	
	   public Player(String name){ 
//		  Name = name;
//		    try{
//		        BufferedReader in  = new BufferedReader(new FileReader("Score.txt"));
//		        String line;
//		        int nbline = -1;  
//		          while ((line = in.readLine()) != null) {
//		        	nbline++;
//		            if(in.readLine().equals(Name)){
//		            	AlreadyPlayed = true;
//		            	PlayerLine = nbline;
//		            	System.out.println(PlayerLine);
//		            	if(nbline == PlayerLine+1){
//		            		numbwin = Integer.parseInt(in.readLine());
//		            	}
//		            	if(nbline == PlayerLine+2){
//		            		numblos = Integer.parseInt(in.readLine());
//		            	}
//		            }
//
//		          }
//		        in.close();
//		      }
//		    catch (NullPointerException a)
//			{
//		    	System.out.println("Erreur : pointeur null");
//			}
//		      catch(Exception e){
//		    	  e.printStackTrace();
//		      }
	   }
	   
	public void Score(Boolean win){
		  try {
			if(AlreadyPlayed){
				BufferedReader in  = new BufferedReader(new FileReader("Score.txt"));
		        String line;
		        String[] lines = null;
		        int i =1;
		          while ((line = in.readLine()) != null) {
		        	  lines[1] = line;
		        	  i++;
		          }
				
				if(win){
					lines[PlayerLine+1]=Integer.toString(numbwin+1);
					lines[PlayerLine+2]=Integer.toString(numblos);
				}
				else{
					lines[PlayerLine+1]=Integer.toString(numbwin);
					lines[PlayerLine+2]=Integer.toString(numblos+1);
				}
				FileWriter f = new FileWriter("Score.txt",true);
				BufferedWriter bw = new BufferedWriter ( f );
				bw.newLine();
				PrintWriter pw = new PrintWriter ( bw );
				int j =0;
				while(lines[i] != null){
					pw.print(lines[j]);
					j++;
				}
				
				pw. close ( );
			}
			else{
				if(win){
				FileWriter f = new FileWriter("Score.txt",true);
				BufferedWriter bw = new BufferedWriter ( f ) ;
				PrintWriter pw = new PrintWriter ( bw ) ;
				pw. print ( Name );
				bw.newLine();
				pw.print(1);
				bw.newLine();
				pw.print(0);
				bw.newLine();
				pw. close ( );
				}
				else{
					System.out.println("caca");
					FileWriter f = new FileWriter("Score.txt",true);
					BufferedWriter bw = new BufferedWriter ( f ) ;
					PrintWriter pw = new PrintWriter ( bw ) ;
					pw. print ( Name );
					bw.newLine();
					pw.print(0);
					bw.newLine();
					pw.print(1);
					bw.newLine();
					pw. close ( );
				}
			}
		} catch (IOException e1) {
		
			e1.printStackTrace();
		}
	}
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
	}
	public int getNumbwin() {
		return numbwin;
	}
	public void setNumbwin(int numbwin) {
		this.numbwin = numbwin;
	}
	public int getNumblos() {
		return numblos;
	}
	public void setNumblos(int numblos) {
		this.numblos = numblos;
	}
	
}
