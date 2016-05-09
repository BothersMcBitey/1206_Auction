package communication;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

import communication.messages.Message;

public class Comms {

	private Socket socket;
	private PrintStream log;
	
	public Comms(String host, int port) throws IOException{
		socket = new Socket(host, port);
		socket.setKeepAlive(true);
//		socket.setSoTimeout(500);
	}
	
	public Comms(Socket socket){
		this.socket = socket;
	}
	
	public String getIP(){
		return socket.getInetAddress().getHostAddress();
	}
	
	public void sendMessage(Message msg) throws IOException{
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		log.println("sending " + msg.toString());
		try{
			out.writeObject(msg);
		} catch(IOException e){
			log.print("sending message failed.");
			throw e;
		}
		out.close();
	}
	
	public Message receiveMessage() throws IOException, ClassNotFoundException{
		ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		Message msg = null; 
		if(in.available() > 0){
			try {
				msg = (Message) in.readObject();
			} catch (ClassNotFoundException e) {
				log.println("failed to read in message, ClassNotFound");
				log.println(e.getStackTrace());
				throw e;
			} catch(IOException e1){
				log.println("failed to read in message, IOException");
				log.println(e1.getStackTrace());
				throw e1;
			}
		}
		in.close();
		log.println("recieved message " + msg.toString());
		return msg;
	}

	public PrintStream getLog() {
		return log;
	}

	public void setLog(PrintStream log) {
		this.log = log;
	}
}