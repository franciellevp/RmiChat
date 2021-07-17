package src.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI_SquaredInterface extends Remote {
	public double getSquare(double input) throws RemoteException;
	public double getSquareRoot(double input) throws RemoteException;
}

