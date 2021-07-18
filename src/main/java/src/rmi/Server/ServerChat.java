package src.rmi.Server;

import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import src.rmi.Room.IRoomChat;
import src.rmi.Room.RoomChat;
import src.rmi.main.Constants;

@SuppressWarnings("serial")
public class ServerChat extends UnicastRemoteObject implements IServerChat {
	private ArrayList<String> roomList;
    
    public ServerChat() throws RemoteException, MalformedURLException, AlreadyBoundException {
    	super();
    	roomList = new ArrayList<>();
    	new RoomChat("Os Manolitos", "Os-Manolitos");
    	roomList.add("Os-Manolitos");
    	new RoomChat("Sala de Estudos", "Sala-de-Estudos");
    	roomList.add("Sala-de-Estudos");
    }
    
    public ArrayList<String> getRooms() throws RemoteException {
		return roomList;
	}

    public void createRoom (String roomName) throws RemoteException {
    	try {
    		String roomId = roomName.replaceAll(" ", "-");
    		System.out.println("Criando " + roomId);
    		synchronized (roomList) {
    			new RoomChat(roomName, roomId);
    			roomList.add(roomId);
    		}
    		System.out.println(roomName + " criada");
    	} catch (Exception ex) {
    		System.out.println("Erro ao criar sala. " + ex);
    	}
	}
    
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		try {
			System.out.println("Criando RMI...");
			Registry registry = LocateRegistry.createRegistry(Constants.PORT);
			ServerChat server = new ServerChat();
			try {
				registry.bind(Constants.SERVER, server);
				System.out.println("Servidor executando...");
			} catch (AlreadyBoundException | AccessException ex) {
                System.out.println("Erro ao criar o servidor: " + ex.getMessage());
                return;
            }
			Runnable closeRoom = new Runnable() {
			    public void run() {
					try {
						String roomId = null;
						roomId = server.roomList.get(0);
						IRoomChat roomApi = (IRoomChat) Naming.lookup(Constants.URI + roomId);
						roomApi.closeRoom();
						server.roomList.remove(roomId);
					} catch (MalformedURLException | RemoteException | NotBoundException ex) {
						System.out.println("Erro ao acessar sala para fecha-la. " + ex.getMessage());
					}
			    }
			};
			
			ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
			exec.scheduleAtFixedRate(closeRoom , 20, 20, TimeUnit.SECONDS);
		} catch(Exception ex) {
			System.out.println("ERRO: Servidor RMI não conseguiu iniciar..." + ex.getMessage());
		}
	}
}
