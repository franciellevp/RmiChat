package src.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMI_SquaredServer extends UnicastRemoteObject implements RMI_SquaredInterface {

	public RMI_SquaredServer() throws RemoteException {
		super();
	}

	/*public RMI_SquaredServer(int port) throws RemoteException {
		super(port);
		// TODO Auto-generated constructor stub
	}

	public RMI_SquaredServer(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
		super(port, csf, ssf);
		// TODO Auto-generated constructor stub
	}*/

	public double getSquare(double input) throws RemoteException {
		return input * input;
	}

	public double getSquareRoot(double input) throws RemoteException {
		return Math.sqrt(input);
	}

	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		try
		{
			Registry registry = LocateRegistry.createRegistry(7777);
			registry.bind("squared", new RMI_SquaredServer());
			System.out.println("The RMI_SquaredApp is running and ready...");
		}
		catch(Exception e)
		{
			System.out.println("ERROR: The RMI_SquaredApp is not running..." + e);
		}
	}

}
