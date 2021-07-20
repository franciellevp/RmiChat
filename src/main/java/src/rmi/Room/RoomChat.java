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
import src.rmi.utils.Constants;

/**
 * Classe com todas as operacoes que o controlador (sala) consegue realizar no chat
 */
@SuppressWarnings("serial")
public class RoomChat extends UnicastRemoteObject implements IRoomChat {
	/**
	 * Mapeamento de todos os usuarios da sala
	 */
    private Map<String, IUserChat> userList = new HashMap<>();
    
    /**
     * nome da sala
     */
    private String name;
    
    /**
     * identificador da sala usada na URI do RMI
     */
    private String id;
    
    /**
     * 
     * @param roomName nome da sala sendo criada
     * @param roomId identificador da sala para acesso no RMI
     * @throws MalformedURLException lida com as excecoes de metodos remotos relacionados a URI
     * @throws RemoteException lida com as excecoes de metodos remotos
     * @throws AlreadyBoundException lida com as excecoes relacionadas a criacao de uma referencia
     */
    public RoomChat (String roomName, String roomId) throws MalformedURLException, RemoteException, AlreadyBoundException {
		super();
		this.name = roomName;
		this.id = roomId;
		Naming.bind(Constants.URI + this.id, this);
	}
    
    
    /**
	 * Envia uma mensagem a todos os usuarios da sala
	 * @param usrName nome do usuario que esta enviando a mensagem
	 * @param msg mensagem a ser enviada
	 * @throws RemoteException lida com as excecoes de metodos remotos
	 */
	public synchronized void sendMsg (String usrName, String msg) throws RemoteException {
		for (var entry : this.userList.entrySet()) {
			if (msg != null && !msg.isEmpty() && !usrName.equals(entry.getKey())) {
		        IUserChat user = entry.getValue();
		        user.deliverMsg(usrName, ": " + msg);
			}
    	}
	}

	
	/**
     * Faz com que um usuario entre na sala
     * @param usrName nome do usuario que esta entrando na sala
     * @param user objeto que contem informacoes do usuario
     * @throws RemoteException lida com as excecoes de metodos remotos
     */
    public synchronized void joinRoom (String usrName, IUserChat user) throws RemoteException {
    	this.userList.putIfAbsent(usrName, user);
    	for (var entry : this.userList.entrySet()) {
    		if (usrName != entry.getKey()) {
		        IUserChat userKey = entry.getValue();
		        userKey.deliverMsg(usrName, " entrou na sala");
    		}
    	}
    }

    
    /**
     * Retira as referencias daquele usuario da sala
     * @param usrName nome do usuario que esta saindo da sala
     * @throws RemoteException lida com as excecoes de metodos remotos
     */
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

    
    /**
     * Fecha a sala, removendo-a da listagem de salas disponiveis e desconectando os usuarios
     * @throws RemoteException lida com as excecoes de metodos remotos
     */
    public void closeRoom () throws RemoteException {
    	for (var entry : this.userList.entrySet()) {
	        IUserChat userKey = entry.getValue();
	        userKey.deliverMsg("", "Sala fechada pelo servidor. Voce sera desconectado");
    	}
    	boolean r = UnicastRemoteObject.unexportObject(this, true);
    	try {
			Naming.unbind(Constants.URI + this.id);
		} catch (RemoteException | MalformedURLException | NotBoundException ex) {
			System.out.println("Erro ao tentar fechar sala na sala. " + ex.getMessage());
		}
    	if (r)
    		System.out.println("Sala fechada");
    	this.userList.clear();
    }

    
    /**
     * Retorna o nome da sala
     * @return name nome da sala
     * @throws RemoteException lida com as excecoes de metodos remotos
     */
    public String getRoomName () throws RemoteException {
    	return name;
    }
    
    
    /**
     * Retorna o identificador da sala
     * @return id identificador da sala na URI
     * @throws RemoteException lida com as excecoes de metodos remotos
     */
    public String getRoomId () throws RemoteException {
    	return id;
    }
}
