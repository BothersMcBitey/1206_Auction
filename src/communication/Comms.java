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
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public Comms(String host, int port, PrintStream log) throws IOException{
		socket = new Socket(host, port);
		init(log);
	}
	
	public Comms(Socket socket, PrintStream log){
		this.socket = socket;
		init(log);
	}
	
	private void init(PrintStream log){		
		 try {
			 this.log = log;
			socket.setKeepAlive(true);						
		} catch (IOException e) {		
			e.printStackTrace();
		}
	}
	
	public void close(){
		log.println("closing connection " + socket.getInetAddress().getHostAddress());
		try {
			socket.close();
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getIP(){
		return socket.getInetAddress().getHostAddress();
	}
	
	public synchronized void sendMessage(Message msg) throws IOException{ 
		if(out == null) out = new ObjectOutputStream(socket.getOutputStream());		
		log.println("sending " + msg.toString());
		try{
			out.writeObject(msg);
		} catch(IOException e){
			log.print("sending message failed.");
			e.printStackTrace();
			throw e;
		}
		out.flush();
	}
	
	public synchronized Message receiveMessage() throws IOException, ClassNotFoundException{
		if(in == null)in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		Message msg = null; 
		try {
			while(msg == null){
				msg = (Message) in.readObject();
			}
		} catch (ClassNotFoundException e) {
			log.println("failed to read in message, ClassNotFound");
			log.println(e.getStackTrace());
			throw e;
		} catch(IOException e1){
			log.println("failed to read in message, IOException");
			log.println(e1.getStackTrace());
			throw e1;
		}
		log.println("recieved message " + msg.toString());
		return msg;
	}
	
	private static String MessageToString(Message msg){
		
		return null;
	}
	
	private static Message StringToMessage(String s){
		
		return null;
	}

	public PrintStream getLog() {
		return log;
	}

	public void setLog(PrintStream log) {
		this.log = log;
	}
}