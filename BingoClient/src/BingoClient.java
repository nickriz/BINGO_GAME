//Developed by: nickriz

import java.awt.GridLayout;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class BingoClient extends UnicastRemoteObject implements BingoListener {


    protected BingoClient() throws RemoteException {
    }



    public static void main(String[] args) throws IOException {
        try{


            WinnersServerOperationsInterface winner_look_op =(WinnersServerOperationsInterface) Naming.lookup("//localhost/WinnersMainServer");
            BingoOperations bingo_look_up =(BingoOperations) Naming.lookup("//localhost/BingoMainServer");

            BingoClient bingo_client = new BingoClient();
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2,2));
            panel.add(new JLabel("Username:"));
            JTextField username_text_field = new JTextField();
            panel.add(username_text_field);
            panel.add(new JLabel("Password:"));
            JTextField password_text_field = new JTextField();
            panel.add(password_text_field);
            String buttons[] = {"Login", "Register"};

            int option = JOptionPane.showOptionDialog(null, panel,"",JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);
            String username = username_text_field.getText();
            String password = password_text_field.getText();

            //if it is log in
            if (option==0){
                HashSet<Integer> numbers_have_been_drawn = bingo_look_up.loginFunction(username, password, bingo_client);
                if (numbers_have_been_drawn!=null){
                    Player player_logged_in = bingo_look_up.getLoggedInPlayer(username);
                    JOptionPane.showMessageDialog(null,"A player has logged in the system!\nThe numbers have been drawn until now are:" +numbers_have_been_drawn);
                    BingoFrame bingoframe = new BingoFrame(bingo_client,bingo_look_up,winner_look_op,player_logged_in);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Wrong username or password! Try again!");
                    System.exit(0);
                }

            }
            // if it is registration
            else{
                HashSet<Integer> numbers_have_been_drawn = bingo_look_up.registrationFunction(username, password, bingo_client);
                if (numbers_have_been_drawn!=null){
                    Player player_logged_in = bingo_look_up.getLoggedInPlayer(username);
                    JOptionPane.showMessageDialog(null,"A player has just been registered! The player has been automatically logged in the system!\nThe numbers have been drawn until now are:" + numbers_have_been_drawn);
                    new BingoFrame(bingo_client,bingo_look_up,winner_look_op,player_logged_in);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Username exists! Try another!");
                    System.exit(0);
                }
            }

        }
        catch(Exception e){
            System.out.println("BingoClient error: " + e);
            System.exit(1);
        }
    }

    @Override
    public void generateNumber(int number) throws RemoteException {
        JOptionPane.showMessageDialog(null,"New number has been drawn:"+number);
    }

    @Override
    public void newDraw() throws RemoteException {
        JOptionPane.showMessageDialog(null,"New number draw!");
    }

}

