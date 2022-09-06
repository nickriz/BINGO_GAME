
import java.rmi.*;
import java.util.ArrayList;
import java.util.HashSet;

// interface gia apomakrysmenes methodous tou bingo server
public interface BingoOperations extends Remote{
    public HashSet<Integer> loginFunction(String username, String password, BingoListener client) throws RemoteException;
    public Player getLoggedInPlayer(String username) throws RemoteException;
    public void logoutFunction(BingoListener client, String username) throws RemoteException;
    public HashSet<Integer> registrationFunction(String username, String password, BingoListener client) throws RemoteException;
    public int[][] createCoupon() throws RemoteException;
    public boolean isBingo(int bingo_coupon[][],String username) throws RemoteException;

}
