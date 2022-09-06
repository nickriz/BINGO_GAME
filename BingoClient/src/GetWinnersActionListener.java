import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

class GetWinnersActionListener implements ActionListener {

    private WinnersServerOperationsInterface winner_look_up;

    public GetWinnersActionListener(WinnersServerOperationsInterface winner_look_up) {
        this.winner_look_up = winner_look_up;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            ArrayList<Player> winners = winner_look_up.getWinnersFromFile();
            JOptionPane.showMessageDialog(null,winners.toString());
        } catch (RemoteException exception) {
            Logger.getLogger(GetWinnersActionListener.class.getName()).log(Level.SEVERE, null, exception);
        }
    }


}
