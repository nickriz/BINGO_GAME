
import java.rmi.Remote;
import java.rmi.RemoteException;

// Interface for the Callbacks
public interface BingoListener extends Remote{
    public void generateNumber(int number) throws RemoteException;
    public void newDraw() throws RemoteException;
}
