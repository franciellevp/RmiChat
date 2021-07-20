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

import src.rmi.Room.RoomChat;
import src.rmi.gui.ServerWindow;
import src.rmi.utils.Constants;

/**
 * Classe com todas as operacoes que o servidor consegue realizar
 */
@SuppressWarnings("serial")
public class ServerChat extends UnicastRemoteObject implements IServerChat {
	
	/**
	 * lista contendo o nome das salas existentes
	 */
	private ArrayList<String> roomList;
    
	/**
	 * Construtor que exporta um objeto e instancia as salas iniciais
	 * @throws RemoteException lida com as excecoes de metodos remotos
	 * @throws MalformedURLException lida com as excecoes de metodos remotos relacionados a URI
	 * @throws AlreadyBoundException lida com as excecoes relacionadas a criacao de uma referencia
	 * @throws NotBoundException 
	 */
    public ServerChat() throws RemoteException, MalformedURLException, AlreadyBoundException, NotBoundException {
    	super();
    	roomList = new ArrayList<>();
    	new RoomChat("Os Manolitos", "Os-Manolitos");
    	roomList.add("Os-Manolitos");
    	new RoomChat("Sala de Estudos", "Sala-de-Estudos");
    	roomList.add("Sala-de-Estudos");
    }
    
    // Remove o nome de uma sala da lista de salas
    public void removeRoom(String roomName)
    {
    	roomList.remove(roomName);
    }
    
    /**
	 * Retorna todas as salas existentes
	 * @return uma lista contendo o Id das salas
	 * @throws RemoteException lida com as excecoes de metodos remotos
	 */
    public ArrayList<String> getRooms() throws RemoteException {
		return roomList;
	}

    /**
     * Cria uma nova sala
     * @param roomName o nome da sala a ser criada
     * @throws RemoteException lida com as excecoes de metodos remotos
     */
    public void createRoom (String roomName) throws RemoteException {
    	try {
    		String roomId = roomName.replaceAll(" ", "-");
    		synchronized (roomList) {
    			new RoomChat(roomName, roomId);
    			roomList.add(roomId);
    		}
    	} catch (Exception ex) {
    		System.out.println("Erro ao criar sala. " + ex);
    	}
	}

    
    /**
     * Cria o registro RMI e inicia o servidor
     * @param args possiveis argumentos passados como parametro
     * @throws RemoteException lida com as excecoes de metodos remotos
     * @throws AlreadyBoundException lida com as excecoes relacionadas a criacao de uma referencia
     */
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		try {
			System.out.println("Criando RMI...");
			Registry registry = LocateRegistry.createRegistry(Constants.PORT);
			ServerChat server = new ServerChat();
			try {
				registry.bind(Constants.SERVER, server);
				System.out.println("Servidor executando...");
				IServerChat serverApi = (IServerChat) Naming.lookup(Constants.URI + Constants.SERVER);
		    	new ServerWindow("Controle do servidor", serverApi, 300, 500, server);
			} catch (AlreadyBoundException | AccessException ex) {
                System.out.println("Erro ao criar o servidor: " + ex.getMessage());
                return;
            }
		} catch(Exception ex) {
			System.out.println("ERRO: Servidor RMI nao conseguiu iniciar..." + ex.getMessage());
		}
	}
}
