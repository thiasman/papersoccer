package PaperSoccer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
//this class will be usefull when I will solve the problem of communication
public class ClientThread extends Thread{
	private final Socket socket;
	private final BufferedReader in;
	private final PrintWriter out;
	
	
	public ClientThread(Socket s) throws IOException {
		socket = s;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
		start();
}

	public void run() {
		System.out.println("start");
		try {
			if(in.readLine().equals("boule")){
				System.out.println("caca");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}
