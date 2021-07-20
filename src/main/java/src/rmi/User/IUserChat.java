package src.rmi.User;

import java.rmi.RemoteException;

/**
 * Interface com método remoto do User
 */
public interface IUserChat extends java.rmi.Remote {
	
	/**
	 * Metodo para o usuario receber as mensagens dos demais ou do servidor
	 * @param senderName nome de quem esta enviando a mensagem
	 * @param msg a mensagem a ser enviada
	 * @throws RemoteException lida com as excecoes de metodos remotos
	 */
    public void deliverMsg (String senderName, String msg) throws RemoteException;
}