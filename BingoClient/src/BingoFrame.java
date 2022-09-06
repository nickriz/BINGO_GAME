
import java.awt.Color;
import java.awt.GridLayout;
import java.rmi.RemoteException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class BingoFrame {
    private BingoClient bingoClient;
    private BingoOperations bingo_look_up;
    private WinnersServerOperationsInterface winner_look_up;
    private int bingo_coupon[][];
    private Player player;
    BingoFrame(BingoClient bingoClient, BingoOperations bingo_look_up, WinnersServerOperationsInterface winner_look_up, Player player) throws RemoteException{
        this.bingoClient = bingoClient;
        this.bingo_look_up = bingo_look_up;
        this.winner_look_up = winner_look_up;
        this.player = player;

        this.bingo_coupon = bingo_look_up.createCoupon();


        JFrame frame = new JFrame(player.getUsername());

        JMenu menu = new JMenu("Menu");

        JMenuBar menu_bar = new JMenuBar();
        menu_bar.add(menu);

        JMenuItem bingo_menu_item = new JMenuItem("Bingo");
        bingo_menu_item.addActionListener(new BingoActionListener(bingo_look_up, winner_look_up, player, this.bingo_coupon));
        JMenuItem winners_menu_item = new JMenuItem("Winners");
        winners_menu_item.addActionListener(new GetWinnersActionListener(winner_look_up));

        JMenuItem exit_menu_item = new JMenuItem("Exit");
        exit_menu_item.addActionListener(new ExitProgramLogoutActionListener(bingo_look_up, bingoClient, player.getUsername()));

        frame.setJMenuBar(menu_bar);
        menu_bar.add(menu);
        menu.add(bingo_menu_item);
        menu.add(winners_menu_item);
        menu.add(exit_menu_item);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6,5));

        String bingo = "BINGO";
        for(int i=0;i<bingo.length();i++){
            JLabel label = new JLabel(""+bingo.charAt(i),SwingConstants.CENTER);
            label.setForeground(Color.red);
            label.setBackground(Color.white);
            panel.add(label);
        }
        // Length 24 cause the Free-Space cell is not a button
        JButton buttons[] = new JButton[24];
        int number = 1;
        for(int i=0;i<bingo_coupon.length; i++){
            for(int j=0; j<bingo_coupon.length; j++) {


                // If it is the center we don't need a Button, so we create a Jlabel
                if (i == 2 && j == 2) {
                    JLabel label = new JLabel("Free Space", SwingConstants.CENTER);
                    label.setBackground(Color.white);
                    panel.add(label);


                } else {
                    buttons[number] = new JButton("'" + number + "'");
                    buttons[number].setForeground(Color.black);
                    buttons[number].addActionListener(new ButtonSelectionActionListener(buttons[i]));
                    panel.add(buttons[number]);
                }
                number = number + 1;
            }
        }
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocation(400,300);
        frame.setVisible(true);
    }
}



