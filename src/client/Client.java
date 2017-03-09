package client;

import java.net.*;
import java.util.ArrayList;

import application.MessengerMethods;

import java.io.*;	
	
public class Client {
	
	private Socket conn; //a connection between the server and a client
	private static ObjectInputStream input; //receive your message
	private static ObjectOutputStream output;//sends your message
	private String server_ip = "127.0.0.1";
	MessengerMethods m = new MessengerMethods();	
		
	public Client(){
		
	}
		
	public void start(){
		System.out.println("client");	
		try{
			//send requests to the server
			sendRequest();
			
			//setup streams
			setStreams();
			
			//communication
			havingService();			
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				System.out.println("client closed connection");
				close();
			}
	}
	
	public void sendRequest() throws UnknownHostException,IOException{
		System.out.println("Sending requests to the server");
		//send out request to the server
		conn = new Socket(InetAddress.getByName(server_ip),1324);	
		System.out.println("Succesfully established the connection");
	}
	
	public void setStreams() throws IOException{
		System.out.println("Ready for set up");		
		input = new ObjectInputStream(conn.getInputStream());
		output = new ObjectOutputStream(conn.getOutputStream());		
		System.out.println("Streams set up successfully");
	}
	
	@SuppressWarnings("unchecked")
	public void havingService(){
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
		}while(!s.equalsIgnoreCase("requestAcceptedByServer"));		
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
}
