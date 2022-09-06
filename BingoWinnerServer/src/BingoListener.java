
import java.rmi.Remote;
import java.rmi.RemoteException;

// Interface for the Callbacks
public interface BingoListener extends Remote{
    void generateNumber(int number) throws RemoteException;
    void newDraw() throws RemoteException;
}
