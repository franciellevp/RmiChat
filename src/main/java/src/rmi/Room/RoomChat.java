package src.rmi.Room;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import src.rmi.User.IUserChat;
import src.rmi.main.Constants;

@SuppressWarnings("serial")
public class RoomChat extends UnicastRemoteObject implements IRoomChat {
    private Map<String, IUserChat> userList = new HashMap<>();
    private String name;
    private String id;
    //private String msgReceived;
    
    
    public RoomChat() throws RemoteException {
        super();
        //this.msgReceived = "Sem mensagem";
    }
    
    public RoomChat (Map<String, IUserChat> userList) throws RemoteException {
		super();
		this.userList = userList;
	}
    
    public RoomChat (String roomName, String roomId) throws MalformedURLException, RemoteException, AlreadyBoundException {
		super();
		this.name = roomName;
		this.id = roomId;
		Naming.bind(Constants.URI + this.id, this);
	}
    
	public synchronized void sendMsg (String usrName, String msg) throws RemoteException {
		for (var entry : this.userList.entrySet()) {
			if (msg != null && !msg.isEmpty() && !usrName.equals(entry.getKey())) {
		        IUserChat user = entry.getValue();
		        user.deliverMsg(usrName, ": " + msg);
			}
    	}
	}

    public synchronized void joinRoom (String usrName, IUserChat user) throws RemoteException {
    	this.userList.putIfAbsent(usrName, user);
    	for (var entry : this.userList.entrySet()) {
    		if (usrName != entry.getKey()) {
		        IUserChat userKey = entry.getValue();
		        userKey.deliverMsg(usrName, " entrou na sala");
    		}
    	}
    }

    public synchronized void leaveRoom (String usrName) throws RemoteException {
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

    public void closeRoom () throws RemoteException {
    	for (var entry : this.userList.entrySet()) {
	        IUserChat userKey = entry.getValue();
	        userKey.deliverMsg("", "Sala fechada pelo servidor. Voce sera desconectado");
    	}
    	boolean r = UnicastRemoteObject.unexportObject(this, true);
    	try {
			Naming.unbind(Constants.URI + this.id);
		} catch (RemoteException | MalformedURLException | NotBoundException ex) {
			System.out.println("Erro ao dar unbind na sala. " + ex.getMessage());
		}
    	if (r)
    		System.out.println("Sala fechada");
    	this.userList.clear();
    }

    public String getRoomName () throws RemoteException {
    	return name;
    }
    
    public String getRoomId () throws RemoteException {
    	return id;
    }
}
