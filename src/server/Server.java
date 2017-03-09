package server;

import java.net.*;
import java.util.ArrayList;

import application.MessengerMethods;

import java.io.*;

public class Server{
	
	private ServerSocket serverSocket; //do the basic configurations
	private Socket conn; //a connection between the server and a client
	private static ObjectInputStream input; //receive your message
	private static ObjectOutputStream output;//sends your message
	MessengerMethods m = new MessengerMethods();	

	public Server(){
		
	}
	
	//the main function for the server to become alive	
	public void start(){
		
		try {
			serverSocket = new ServerSocket(1324,100);
			while(true){
				try{					
				//waiting for requests rom clients
					waitRequests();
					//setup streams
					setStreams();
					//communication
					providingService();
				}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					System.out.println("server closed the connection");
					close();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void waitRequests(){
		m.clearHistory();
		System.out.println("waiting for clients");
		try {
			conn = serverSocket.accept();
			System.out.println("A new client is coming, his IP is: "+conn.getInetAddress().getHostAddress());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setStreams() throws IOException{
		System.out.println("Ready for stream setup");
		output = new ObjectOutputStream(conn.getOutputStream());
		input = new ObjectInputStream(conn.getInputStream());		
		System.out.println("Streams setup");
	}
	
	@SuppressWarnings("unchecked")
	public void providingService(){
		
		ArrayList<String> message = new ArrayList<String>();
		String s="";
		//Back & Forth communication
		do{
			try {				
					message = (ArrayList<String>)input.readObject();
					System.out.println("Message Received: "+message);
					if(message.size() == 1)
						s = message.get(0);
					else
						m.decodeContent(message);								
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}while(!s.equalsIgnoreCase("requestToCloseConn"));
		message.clear();
		message.add("requestAcceptedByServer");
		sendMessage(message);
	}
	
	public void close(){		
		try {
			input.close();
			output.close();
			conn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void sendMessage(ArrayList<String> message){
		try {
			output.writeObject(message);
			System.out.println("sendMessage: "+message);
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}	
}
