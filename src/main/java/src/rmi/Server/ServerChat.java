package src.rmi.Server;

import java.rmi.AlreadyBoundException;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import src.rmi.main.Constants;

public class ServerChat extends UnicastRemoteObject implements IServerChat { 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> roomList;
    
    public ServerChat() throws RemoteException {
    	super();
    	roomList = new ArrayList<>();
    	roomList.add("Os Manolitos");
    	roomList.add("Os que odeiam SD");
    }
    
    public ArrayList<String> getRooms() throws RemoteException {
		return roomList;
	}

    public void createRoom (String roomName) throws RemoteException {
    	try {
    		roomList.add(roomName);
    	} catch (Exception ex) {
    		System.out.println("Erro ao criar sala. " + ex);
    	}
	}
    
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		try {
			System.out.println("Criando RMI...");
			Registry registry = LocateRegistry.createRegistry(Constants.PORT);
			try {
				registry.bind(Constants.SERVICE, new ServerChat());
				System.out.println("Servidor executando...");
			} catch (AlreadyBoundException | AccessException ex) {
                System.out.println("Erro ao criar o servidor: " + ex.getMessage());
                return;
            }
		} catch(Exception ex) {
			System.out.println("ERRO: Servidor RMI não conseguiu iniciar..." + ex.getMessage());
		}
	}
}
