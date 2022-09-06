import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

class BingoActionListener implements ActionListener {
    private BingoOperations bingo_look_up;
    private WinnersServerOperationsInterface winner_look_up;
    private int bingo_coupon[][];
    private Player player;
    public BingoActionListener(BingoOperations bingo_look_up, WinnersServerOperationsInterface winner_look_up, Player player, int bingo_coupon[][]){
        this.player = player;
        this.bingo_look_up = bingo_look_up;
        this.winner_look_up = winner_look_up;
        this.bingo_coupon = bingo_coupon;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (this.bingo_look_up.isBingo(this.bingo_coupon, this.player.getUsername())){
                JOptionPane.showMessageDialog(null,"Congratulations BINGO!!!");
                this.winner_look_up.addWinnerToFile(player);
            }
            else{
                JOptionPane.showMessageDialog(null,"It is not BINGO! Keep trying!");
            }
        } catch (RemoteException ex) {
            Logger.getLogger(BingoActionListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}