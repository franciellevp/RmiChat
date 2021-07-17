package src.rmi.User;

import java.rmi.Naming;
import java.util.ArrayList;
import java.util.Scanner;

import src.rmi.Room.IRoomChat;
import src.rmi.Server.IServerChat;
import src.rmi.main.Constants;

import java.io.IOException;


public class UserChat implements IUserChat {
	
	private String username;
	private static Scanner sc = new Scanner(System.in);
	
	public void setUsername (String username) {
		this.username = username;
	}
	
	public String getUsername () {
		return this.username;
	}
    

    public void deliverMsg (String senderName, String msg) {
    	// TODO Auto-generated method stub
    }
    
    private void run() throws IOException {
    	System.out.println("Username: ");
    	String username = null;
    	do {
    		username = sc.nextLine();
    	} while (username == null || username.isEmpty());
    	this.setUsername(username);
    	System.out.println("Bem vindo(a) " + this.getUsername());
    }
    
    public static void main(String[] args) throws Exception {
    	try {
	    	IServerChat serverApi = (IServerChat) Naming.lookup(Constants.URI);
	    	//IRoomChat roomApi = (IRoomChat) Naming.lookup(Constants.URI);
	    	
	        var user = new UserChat();
	        user.run();
	
	        String msg;
	        System.out.println("Salas disponíveis");
            System.out.println(serverApi.getRooms().toString());
            serverApi.createRoom("Sala Jujuba");
            System.out.println("Sala: Sala Jujuba criada");
            System.out.println(serverApi.getRooms().toString());
	        //serverApi.joinRoom("Os Manolos", user);
            while (true) {  
	            //msg = sc.nextLine();
	        }
    	} catch (Exception ex) {
    		System.out.println("ERRO: O RMI do Cliente não está funcionando..." + ex.getMessage());
    	}
    }
}
