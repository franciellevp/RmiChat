package src.rmi.Room;

import java.rmi.RemoteException;

import src.rmi.User.IUserChat;

/**
 * Interface com os metodos remotos do controlador
 */
public interface IRoomChat extends java.rmi.Remote {
    
	/**
	 * Envia uma mensagem a todos os usuarios da sala
	 * @param usrName nome do usuario que esta enviando a mensagem
	 * @param msg mensagem a ser enviada
	 * @throws RemoteException lida com as excecoes de metodos remotos
	 */
    public void sendMsg (String usrName, String msg) throws RemoteException;
    
    /**
     * Faz com que um usuario entre na sala
     * @param usrName nome do usuario que esta entrando na sala
     * @param user objeto que contem informacoes do usuario
     * @throws RemoteException lida com as excecoes de metodos remotos
     */
    public void joinRoom (String usrName, IUserChat user) throws RemoteException;
    
    /**
     * Retira as referencias daquele usuario da sala
     * @param usrName nome do usuario que esta saindo da sala
     * @throws RemoteException lida com as excecoes de metodos remotos
     */
    public void leaveRoom (String usrName) throws RemoteException;
    
    /**
     * Fecha a sala, removendo-a da listagem de salas disponiveis e desconectando os usuarios
     * @throws RemoteException lida com as excecoes de metodos remotos
     */
    public void closeRoom () throws RemoteException;
    
    /**
     * 
     * @return o nome da sala
     * @throws RemoteException lida com as excecoes de metodos remotos
     */
    public String getRoomName () throws RemoteException;
}