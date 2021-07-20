package src.rmi.User;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import src.rmi.Room.IRoomChat;
import src.rmi.Server.IServerChat;
import src.rmi.gui.ButtonMessage;
import src.rmi.gui.ChatWindow;
import src.rmi.gui.ListWindow;
import src.rmi.gui.LoginWindow;
import src.rmi.utils.Constants;

import java.io.IOException;

/**
 * Classe com todas as operacoes que o usuario consegue realizar no chat
 */

@SuppressWarnings("serial")
public class UserChat extends UnicastRemoteObject implements IUserChat {
	/**
	 * nome do usuario no chat
	 */
	private String username;
	
	/**
	 * janela JFrame para visualizacao do chat
	 */
	private ChatWindow chat;
	
	/**
	 * Construtor que inicializa o username e exporta o objeto UnicastRemoteObject
	 * @throws RemoteException lida com as excecoes de metodos remotos
	 */
	protected UserChat() throws RemoteException {
		super();
		this.username = null;
	}
	
	
	/**
	 * Seta o nick que o usuario digitou
	 * @param username nick do usuario no chat
	 */
	public void setUsername (String username) {
		this.username = username;
	}
	
	/**
	 * Retorna o nick do usuario
	 * @return nome do usuario
	 */
	public String getUsername () {
		return this.username;
	}
	
    
	/**
	 * Metodo para o usuario receber as mensagens dos demais ou do servidor
	 * @param senderName nome de quem esta enviando a mensagem
	 * @param msg a mensagem a ser enviada
	 * @throws RemoteException lida com as excecoes de metodos remotos
	 */
    public void deliverMsg (String senderName, String msg) throws RemoteException {
    	chat.receiveMessage(senderName + " " + msg);
    }
    
    
    /**
     * Cria uma janela para que o usuario digite o seu nome
     * @throws IOException lida com as excecoes de tentativas de IO
     */
    private void askName () throws IOException {
    	do {
	        LoginWindow loginWindow = new LoginWindow();
	        username = loginWindow.Login();
    	} while (username == null || username.isEmpty());
        this.setUsername(username);
        new ButtonMessage("Bem-vindo " + username + "!", 250, 100);
    }
    
    
    /**
     * Metodo auxiliar para que um usuario entre em uma sala
     * @param idx id da sala que o usuario deseja entrar
     * @throws IOException lida com as excecoes de tentativas de IO
     * @throws NotBoundException lida com as excecoes de acesso a metodos remotos
     */
    public void userJoin (int idx) throws IOException, NotBoundException {
    	IServerChat serverApi = (IServerChat) Naming.lookup(Constants.URI + Constants.SERVER);
    	String roomname = serverApi.getRooms().get(idx);
        IRoomChat roomApi = (IRoomChat) Naming.lookup(Constants.URI + roomname);
        roomApi.joinRoom(this.getUsername(), this);
        chat = new ChatWindow(serverApi, roomApi, this);
    }
    
    
    /**
     * Instancia um novo usuario
     * @param args possiveis argumentos passados como parametro
     * @throws Exception lida com as excecoes genericas
     */
    public static void main(String[] args) throws Exception {
    	try {
    		// pega referencia do servidor
	    	IServerChat serverApi = (IServerChat) Naming.lookup(Constants.URI + Constants.SERVER);

	        UserChat user = new UserChat();
	        user.askName();
	        new ListWindow("Lista de salas", serverApi, 300, 500, user);

    	} catch (Exception ex) {
    		System.out.println("ERRO: O RMI do Cliente nao esta funcionando..." + ex.getMessage());
    	}
    }
}
