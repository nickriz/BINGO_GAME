
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WinnersServerOperationServer extends UnicastRemoteObject implements WinnersServerOperationsInterface {
    private File file_object = null;
    private ObjectOutputStream object_out;
    private ObjectInputStream object_in;

    //We will use this flag for the synchronization
    boolean isWinnerAddingProcess =false;

    public WinnersServerOperationServer() throws RemoteException, IOException{
        file_object = new File("Winners.txt");


    }

    @Override
    public ArrayList<Player> getWinnersFromFile() throws RemoteException {
        ArrayList<Player> winners = new ArrayList<>();
        synchronized(this){
            while(true){
                //no more race condition problems
                if (!isWinnerAddingProcess){
                    break;
                }
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(WinnersServerOperationServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                if (!file_object.createNewFile()) {
                    try{
                        object_in = new ObjectInputStream(new FileInputStream(file_object));
                        //for each Player until the file ends
                        for (;;){
                            Player winner = (Player) object_in.readObject();
                            winners.add(winner);
                        }
                    }
                    catch(EOFException eof){

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(WinnersServerOperationServer.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            } catch (IOException ex) {
                Logger.getLogger(WinnersServerOperationServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            notifyAll();
        }
        return winners;
    }

    @Override
    public void addWinnerToFile(Player winner) throws RemoteException {
        ArrayList<Player> winners = getWinnersFromFile();

        synchronized (this){
            isWinnerAddingProcess = true;
            winners.add(winner);

            try {
                object_out = new ObjectOutputStream(new FileOutputStream(file_object));
                for(Player player_winner : winners){
                    object_out.writeObject(player_winner);
                }
            } catch (IOException ex) {
                Logger.getLogger(WinnersServerOperationServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            isWinnerAddingProcess = false;
            notifyAll();
        }

    }

}
