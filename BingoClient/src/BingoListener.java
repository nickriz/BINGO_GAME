
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface BingoListener extends Remote{
    void generateNumber(int number) throws RemoteException;
    void newDraw() throws RemoteException;
}
