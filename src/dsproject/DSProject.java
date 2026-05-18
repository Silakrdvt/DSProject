package dsproject;

import javax.swing.SwingUtilities;

public class DSProject {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }

}
