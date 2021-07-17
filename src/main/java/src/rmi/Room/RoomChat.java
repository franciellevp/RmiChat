package src.rmi.Room;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import src.rmi.Server.IServerChat;
import src.rmi.User.IUserChat;
import src.rmi.User.UserChat;
import src.rmi.main.Constants;

@SuppressWarnings("serial")
public class RoomChat extends UnicastRemoteObject implements IRoomChat {
    private Map<String, IUserChat> userList;
    private String roomName;
    //private String msgReceived;
    
    
    public RoomChat() throws RemoteException {
        super();
        this.userList = new HashMap<>();
        //this.msgReceived = "Sem mensagem";
    }
    
    public RoomChat (Map<String, IUserChat> userList) throws RemoteException {
		super();
		this.userList = userList;
	}
    
    public RoomChat (String roomName) throws MalformedURLException, RemoteException, AlreadyBoundException {
		super();
		this.roomName = roomName;
		this.userList = new HashMap<>();
		Naming.rebind(Constants.URI + Constants.ROOM, this);
	}

	public void sendMsg (String usrName, String msg) throws RemoteException {
		System.out.println(usrName + ": " + msg);
		//this.msgReceived = msg;
		IUserChat user = userList.get(usrName);
	}

    public void joinRoom (String usrName, IUserChat user) throws RemoteException {
    	this.userList.putIfAbsent(usrName, user);
    	user.deliverMsg(usrName, " entrou na sala");
    }

    public void leaveRoom (String usrName) throws RemoteException {
    	try {
    		IUserChat user = userList.get(usrName);
    		user.deliverMsg(usrName, " saiu da sala");
    	} catch (Exception ex) {
    		System.out.println("Erro ao sair da sala. " + ex);
    	}
    }

    public void closeRoom () throws RemoteException {}

    public String getRoomName () throws RemoteException {
    	return roomName;
    }
}
