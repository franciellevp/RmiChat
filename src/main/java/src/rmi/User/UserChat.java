package src.rmi.User;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

import src.rmi.Room.IRoomChat;
import src.rmi.Server.IServerChat;
import src.rmi.main.Constants;

import java.io.IOException;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class UserChat extends UnicastRemoteObject implements IUserChat {
	
	protected UserChat() throws RemoteException {
		super();
	}

	private String username;
	private static Scanner sc = new Scanner(System.in);
	
	public void setUsername (String username) {
		this.username = username;
	}
	
	public String getUsername () {
		return this.username;
	}
    

    public void deliverMsg (String senderName, String msg) {
    	System.out.println(senderName + ": " + msg);
    }
    
    private void run () throws IOException {
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
    		// pega referencia do servidor
	    	IServerChat serverApi = (IServerChat) Naming.lookup(Constants.URI + Constants.SERVER);

	        UserChat user = new UserChat();
	        user.run();
	        // cria ref do cliente
	        //Naming.rebind(Constants.CLIENT, user);
	        
	        System.out.println("Salas disponíveis");
            System.out.println(serverApi.getRooms().toString());
            
            String roomname = "Sala Jujuba";
            serverApi.createRoom(roomname);
            
            System.out.println(serverApi.getRooms().toString());
            var list = Naming.list(Constants.URI + Constants.SERVER);
            for (var a : list)
            	System.out.println(a);
            IRoomChat roomApi = (IRoomChat) Naming.lookup(Constants.URI + Constants.ROOM);
            roomApi.joinRoom(user.getUsername(), user);
            
            while (true) {  
	            //msg = sc.nextLine();
	        }
    	} catch (Exception ex) {
    		System.out.println("ERRO: O RMI do Cliente não está funcionando..." + ex.getMessage());
    	}
    }
}
