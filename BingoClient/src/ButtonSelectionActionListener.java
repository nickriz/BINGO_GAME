import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ButtonSelectionActionListener implements ActionListener {
    JButton button;

    public ButtonSelectionActionListener(JButton button) {
        this.button = button;
    }

    // We are changing the color of the number-button when the player is selecting it
    @Override
    public void actionPerformed(ActionEvent e) {
        if (button.getForeground().equals(Color.yellow)) {
            button.setForeground(Color.black);
        } else {
            button.setForeground(Color.yellow);
        }
    }
}
