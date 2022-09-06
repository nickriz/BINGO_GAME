
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface WinnersServerOperationsInterface extends Remote{
    public ArrayList<Player> getWinnersFromFile() throws RemoteException;
    public void addWinnerToFile(Player player_winner) throws RemoteException;
}
