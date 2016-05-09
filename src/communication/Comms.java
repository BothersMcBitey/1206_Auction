package communication;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import communication.messages.Message;

public class Comms {

	private Socket socket;
	
	public Comms(String host, int port) throws IOException{
		socket = new Socket(host, port);
		socket.setKeepAlive(true);
		socket.setSoTimeout(500);
	}
	
	public Comms(Socket socket){
		this.socket = socket;
	}
	
	public void sendMessage(Message msg) throws IOException{
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(msg);
		out.close();
	}
	
	public Message receiveMessage() throws IOException{
		ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		Message msg = null; 
		if(in.available() > 0){
			try {
				msg = (Message) in.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		in.close();
		return msg;
	}
}