package src.rmi.Room;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import src.rmi.User.IUserChat;
import src.rmi.main.Constants;

@SuppressWarnings("serial")
public class RoomChat extends UnicastRemoteObject implements IRoomChat {
    private Map<String, IUserChat> userList = new HashMap<>();
    private String roomName;
    //private String msgReceived;
    
    
    public RoomChat() throws RemoteException {
        super();
        //this.msgReceived = "Sem mensagem";
    }
    
    public RoomChat (Map<String, IUserChat> userList) throws RemoteException {
		super();
		this.userList = userList;
	}
    
    public RoomChat (String roomName) throws MalformedURLException, RemoteException, AlreadyBoundException {
		super();
		this.roomName = roomName;
		roomName = roomName.replaceAll(" ", "-");
		Naming.bind(Constants.URI + roomName, this);
	}

	public void sendMsg (String usrName, String msg) throws RemoteException {
		for (var entry : this.userList.entrySet()) {
    		//String name = entry.getKey();
	        IUserChat userKey = entry.getValue();
	        userKey.deliverMsg(usrName, ": " + msg);
    	}
	}

    public void joinRoom (String usrName, IUserChat user) throws RemoteException {
    	this.userList.putIfAbsent(usrName, user);
    	System.out.println("nr de usuarios na sala " + this.userList.size());
    	for (var entry : this.userList.entrySet()) {
    		if (usrName != entry.getKey()) {
		        IUserChat userKey = entry.getValue();
		        userKey.deliverMsg(usrName, "entrou na sala");
    		}
    	}
    }

    public void leaveRoom (String usrName) throws RemoteException {
    	try {
    		this.userList.remove(usrName);
    		for (var entry : this.userList.entrySet()) {
    	        IUserChat userKey = entry.getValue();
    	        userKey.deliverMsg(usrName, "saiu da sala");
        	}
    	} catch (Exception ex) {
    		System.out.println("Erro ao sair da sala. " + ex);
    	}
    }

    public void closeRoom () throws RemoteException {}

    public String getRoomName () throws RemoteException {
    	return roomName;
    }
}
