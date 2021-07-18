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

@SuppressWarnings("serial")
public class UserChat extends UnicastRemoteObject implements IUserChat {
	
	protected UserChat() throws RemoteException {
		super();
	}

	private String username;
	
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
    	System.out.println("Username: ");
    	String username = null;
    	do {
    		username = sc.nextLine();
    	} while (username == null || username.isEmpty());
    	this.setUsername(username);
    	System.out.println("Bem vindo(a) " + this.getUsername());
    }
    
    private void listRooms (IServerChat server) throws IOException {
    	ArrayList<String> rooms = server.getRooms();
        for (int i = 0; i < rooms.size(); i++)
        	System.out.println((i + 1) + ". " + rooms.get(i));
    }
    
    public static void main(String[] args) throws Exception {
    	try {
    		Scanner sc = new Scanner(System.in);
    		// pega referencia do servidor
	    	IServerChat serverApi = (IServerChat) Naming.lookup(Constants.URI + Constants.SERVER);

	        UserChat user = new UserChat();
	        user.run(sc);
           
            System.out.println("0. Criar sala");
            System.out.println("Escolha uma sala:");
            user.listRooms(serverApi);
            int choice = Integer.parseInt(sc.nextLine());
            
            if (choice == 0) {
            	String roomname = sc.nextLine();
                serverApi.createRoom(roomname);
                user.listRooms(serverApi);
            	choice = sc.nextInt();
            }
            
            /*var list = Naming.list(Constants.URI);
            for (var a : list)
            	System.out.println(a);*/
            // choice - 1 é o indice da sala que a pessoa escolheu/clicou
            String roomname = serverApi.getRooms().get(choice - 1);
            IRoomChat roomApi = (IRoomChat) Naming.lookup(Constants.URI + roomname);
            roomApi.joinRoom(user.getUsername(), user);
            while (true) {  
	            String msg = sc.nextLine();
	            if (msg.equals("sair")) break;
	            roomApi.sendMsg(user.getUsername(), msg);
	        }
            // quando clica em algum botao de sair chama isso aqui:
            roomApi.leaveRoom(user.getUsername());
    	} catch (Exception ex) {
    		System.out.println("ERRO: O RMI do Cliente não está funcionando..." + ex.getMessage());
    	}
    }
}
