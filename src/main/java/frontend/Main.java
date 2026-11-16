
package frontend;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception e) {
                System.out.println(
                        "Could not load system appearance."
                );
            }

            MainFrame application = new MainFrame();
            application.setVisible(true);
        });
    }
}