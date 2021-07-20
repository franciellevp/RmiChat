package src.rmi.Server;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Interface com os metodos remotos do Servidor
 */
public interface IServerChat extends java.rmi.Remote {
    
	/**
	 * Retorna todas as salas existentes
	 * @return uma lista contendo o Id das salas
	 * @throws RemoteException lida com as excecoes de metodos remotos
	 */
    public ArrayList<String> getRooms() throws RemoteException;
    
    /**
     * Cria uma nova sala
     * @param roomName o nome da sala a ser criada
     * @throws RemoteException lida com as excecoes de metodos remotos
     */
    public void createRoom(String roomName) throws RemoteException;
}