package src.rmi.Room;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import src.rmi.User.IUserChat;
import src.rmi.main.Constants;

public class RoomChat implements IRoomChat {
    private Map<String, IUserChat> userList;
    private String roomName;
    private String msgReceived;
    
    
    public RoomChat() throws RemoteException {
        super();
        this.userList = new HashMap<>();
        this.msgReceived = "Sem mensagem";
    }
    
    public RoomChat (Map<String, IUserChat> userList) {
		super();
		this.userList = userList;
	}

	public void sendMsg (String usrName, String msg) {
		System.out.println(usrName + ": " + msg);
		this.msgReceived = msg;
		IUserChat user = userList.get(usrName);
	}

    public void joinRoom (String usrName, IUserChat user) {
    	this.userList.putIfAbsent(usrName, user);
    }

    public void leaveRoom (String usrName) {}

    public void closeRoom () {}

    public String getRoomName () {
    	return roomName;
    }
}
