package src.rmi.utils;

/**
 * Classe de constantes utilizadas para conexão de métodos remotos
 */
public class Constants {
	/**
	 * host utilizada para criacao do registro RMI
	 */
	public final static String HOST = "localhost";
	
	/** 
	 * porta utilizada para as conexao
	 */
    public final static Integer PORT = 2020;
    
    /**
     * nome da referencia do servidor no RMI
     */
    public final static String SERVER = "Servidor";
    
    /**
     * URI base do registro RMI onde ficarao todas as referencias necessarias
     */
    public final static String URI = "rmi://" + HOST + ":" + PORT + "/";
}
