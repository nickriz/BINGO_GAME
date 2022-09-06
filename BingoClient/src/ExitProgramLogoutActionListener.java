import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

class ExitProgramLogoutActionListener implements ActionListener {
    private BingoOperations bingo_look_up;
    private BingoClient bingo_client;
    private String username;

    public ExitProgramLogoutActionListener(BingoOperations bingo_look_up, BingoClient bingo_client, String username){
        this.username = username;
        this.bingo_client = bingo_client;
        this.bingo_look_up = bingo_look_up;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {

            //Logout - Exit Game
            this.bingo_look_up.logoutFunction(this.bingo_client, this.username);

        } catch (RemoteException ex) {

        }
        System.out.println("Player " + this.username +  " left the game!");
        System.exit(0);

    }

}