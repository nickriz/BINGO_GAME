//Developed by: nickriz

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class WinnersMainServer {

    public static void main(String[] args) throws IOException{
        try {
            Registry registry = LocateRegistry.createRegistry(1110);
            WinnersServerOperationServer server=new WinnersServerOperationServer();
            Naming.rebind("//localhost/WinnersMainServer", server);
            System.out.println("WinnerMainServer is up and it's running....");
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("Fail to connect to the server!\nExiting...");
            System.exit(0);
        }

    }

}
