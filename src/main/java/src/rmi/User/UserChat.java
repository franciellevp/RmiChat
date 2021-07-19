package src.rmi.User;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

import src.rmi.Room.IRoomChat;
import src.rmi.Server.IServerChat;
import src.rmi.gui.ButtonMessage;
import src.rmi.gui.ChatWindow;
import src.rmi.gui.ListWindow;
import src.rmi.gui.LoginWindow;
import src.rmi.main.Constants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

@SuppressWarnings("serial")
public class UserChat extends UnicastRemoteObject implements IUserChat {
	private String username;
	
	protected UserChat() throws RemoteException {
		super();
		this.username = null;
	}
	
	public void setUsername (String username) {
		this.username = username;
	}
	
	public String getUsername () {
		return this.username;
	}
    

    public void deliverMsg (String senderName, String msg) {
    	System.out.println(senderName + " " + msg);
    }
    
    private void run (Scanner sc) throws IOException {
    	//new ChatWindow();
    	do {
	        LoginWindow loginWindow = new LoginWindow();
	        username = loginWindow.Login();
    	} while (username == null || username.isEmpty());
        this.setUsername(username);
        new ButtonMessage("Bem-vindo " + username + "!", 250, 100);
    }
    
    private void listRooms (IServerChat server) throws IOException {
    	ArrayList<String> rooms = server.getRooms();
        for (int i = 0; i < rooms.size(); i++)
        	System.out.println((i + 1) + ". " + rooms.get(i));
    }
    
    public void userJoin (int idx) throws IOException, NotBoundException {
    	IServerChat serverApi = (IServerChat) Naming.lookup(Constants.URI + Constants.SERVER);
    	String roomname = serverApi.getRooms().get(idx);
        IRoomChat roomApi = (IRoomChat) Naming.lookup(Constants.URI + roomname);
        roomApi.joinRoom(this.getUsername(), this);
        new ChatWindow(serverApi, roomApi, this);
    }
    
    public static void main(String[] args) throws Exception {
    	try {
    		Scanner sc = new Scanner(System.in);
    		// pega referencia do servidor
	    	IServerChat serverApi = (IServerChat) Naming.lookup(Constants.URI + Constants.SERVER);

	        UserChat user = new UserChat();
	        user.run(sc);
	        ListWindow window = new ListWindow("Lista de salas", serverApi, 300, 500, user);

    	} catch (Exception ex) {
    		System.out.println("ERRO: O RMI do Cliente não está funcionando..." + ex.getMessage());
    	}
    }
}
