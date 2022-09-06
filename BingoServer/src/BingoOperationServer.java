
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;


public class BingoOperationServer extends UnicastRemoteObject implements BingoOperations{

    private BingoGameManager bingo_game_manager;
    private ArrayList<Player> players = new ArrayList();
    private ArrayList<BingoListener> players_listeners = new ArrayList();
    private ArrayList<Player> players_connected = new ArrayList();

    public BingoOperationServer() throws RemoteException {
        super();

        this.bingo_game_manager = new BingoGameManager(this.players_listeners);
        new Thread(this.bingo_game_manager).start();
    }


    //If the player connect to the server, then the server returns all the numbers that have been drawn until this moment
    @Override
    synchronized public HashSet<Integer> loginFunction(String username, String password, BingoListener client) throws RemoteException {

        if (ifCredentialsAreCorrect(username,password)){
            this.players_listeners.add(client);
            this.players_connected.add(new Player(username, password));
            return this.bingo_game_manager.getDrawnNumbers();
        }
        return null;
    }

    @Override
    synchronized public Player getLoggedInPlayer(String username) throws RemoteException{
        for(Player player : players_connected)
            if(player.getUsername().equals(username))
                return player;
        return null;
    }

    //If the player register to the server, then the server is logging them in and it returns all the numbers that have been drawn until this moment
    @Override
    synchronized public HashSet<Integer> registrationFunction(String username, String password, BingoListener client) throws RemoteException {
        if (ifUsernameExists(username)){
            return null;
        }
        Player player = new Player(username,password);
        this.players.add(player);
        this.players_listeners.add(client);
        this.players_connected.add(new Player(username, password));
        return bingo_game_manager.getDrawnNumbers();
    }

    @Override
    public int[][] createCoupon() throws RemoteException {
        return this.bingo_game_manager.getBingoCoupon();
    }

    @Override
    public void logoutFunction(BingoListener client, String username) throws RemoteException {
        this.players_listeners.remove(client);
        this.players_connected.remove(username);
    }

    //We are checking if the credentials of the user trying to connect are correct
    private boolean ifCredentialsAreCorrect(String username, String password){
        for(Player player : players){
            if (player.getUsername().equals(username)){
                if (player.getPassword().equals(password))
                    return true;
                else
                    return false;
            }
        }
        return false;
    }
    //We shouldn't allow a player to register if the username already exists
    private boolean ifUsernameExists(String username){
        for(Player player : players){
            if (player.getUsername().equals(username))
                return true;
        }
        return false;
    }
    @Override
    public boolean isBingo(int bingo_coupon[][], ArrayList<Integer> number_found, String username) throws RemoteException {
        if (this.bingo_game_manager.isBingo(bingo_coupon, number_found)){
            //TODO If i want I can write here the winner automatically in the file
            return true;
        }
        return false;
    }

}