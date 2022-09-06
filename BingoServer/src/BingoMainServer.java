//Developed by: nickriz

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.Registry;

public class BingoMainServer{

    public static void main(String[] args){
        try {
            Registry r = java.rmi.registry.LocateRegistry.createRegistry(1099);
            BingoOperationServer server = new BingoOperationServer();
            System.setProperty("java.rmi.server.hostname","localhost");
            Naming.rebind("//localhost/BingoMainServer", server);
            System.out.println("Bingo Server is up and it is running...");
        } catch (MalformedURLException | RemoteException ex) {
            System.out.println(ex);
            System.out.println("Could not connect to the server!");
            System.exit(0);
        }
    }
}