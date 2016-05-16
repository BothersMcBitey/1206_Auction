package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import main.Item;
import main.User;

public class DataPersistence {
	
	private File itemFile, logFile, userFile;
	private Path dataPath, logPath, userPath;
	private PrintStream log;
	
	public DataPersistence(PrintStream log) {
		this.log = log;
		dataPath = Paths.get("auctionData.auc");
		logPath = Paths.get("auctionLog.txt");
		userPath = Paths.get("userData.auc");
		
		itemFile = new File(dataPath.toString());
		userFile = new File(userPath.toString());
		logFile= new File(logPath.toString());
		
		try{
			if(!Files.exists(logPath)){
				Files.createFile(logPath);
			}
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void WriteOutData(Map<Integer, Item> items, List<User> users){
		try{
			if(!Files.exists(dataPath)){
				Files.createFile(dataPath);
			}
		} catch(IOException e){
			e.printStackTrace();
		}			
		
		
		
		try{
			if(!Files.exists(userPath)){
				Files.createFile(userPath);
			}
		} catch(IOException e){
			e.printStackTrace();
		}
		
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(userFile))){
			out.writeObject(users);
			out.flush();
			out.close();
		}catch (IOException e){
			e.printStackTrace();
		}
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(itemFile))){
			out.writeObject(items);
			out.flush();
			out.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public Map<Integer, Item> ReadInItemData(){		
		if(!Files.exists(dataPath)) return null;
			
		Map<Integer, Item> data = null;
		
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(itemFile))){
			data = (Map<Integer, Item>) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	public List<User> ReadInUserData(){
		if(!Files.exists(userPath)) return null; 
		 List<User> data = null;
		
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(userFile))){
			data = (List<User>) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return data;
	}
}
