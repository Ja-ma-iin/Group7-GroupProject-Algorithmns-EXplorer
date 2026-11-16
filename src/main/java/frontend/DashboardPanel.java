
package frontend;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {

        setLayout(new BorderLayout(20, 20));
        setBackground(UITheme.BACKGROUND);
        setBorder(new EmptyBorder(30, 35, 30, 35));

        // Welcome section
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(
                welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBackground(UITheme.BACKGROUND);

        JLabel title = new JLabel("Welcome to Algorithm Explorer");
        title.setFont(UITheme.TITLE_FONT);
        title.setForeground(UITheme.TEXT);

        JLabel subtitle = new JLabel(
                "Explore algorithms, data structures and computational efficiency."
        );
        subtitle.setFont(UITheme.NORMAL_FONT);
        subtitle.setForeground(UITheme.MUTED);

        welcomePanel.add(title);
        welcomePanel.add(Box.createVerticalStrut(10));
        welcomePanel.add(subtitle);

        add(welcomePanel, BorderLayout.NORTH);

        // Module cards
        JPanel cardsPanel = new JPanel(new GridLayout(2, 2, 22, 22));
        cardsPanel.setBackground(UITheme.BACKGROUND);

        cardsPanel.add(createCard(
                "01", "Recursion Lab",
                "Explore Fibonacci, factorial and Tower of Hanoi."
        ));

        cardsPanel.add(createCard(
                "02", "Data Structures Lab",
                "Experiment with stacks, queues and linked lists."
        ));

        cardsPanel.add(createCard(
                "03", "Sorting Lab",
                "Visualize and compare five sorting algorithms."
        ));

        cardsPanel.add(createCard(
                "04", "Complexity Analysis",
                "Study algorithm performance and Big-O notation."
        ));

        add(cardsPanel, BorderLayout.CENTER);


    }

    private JPanel createCard(
            String number,
            String heading,
            String description) {

        JPanel card = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                // White rounded card background
                g2.setColor(UITheme.WHITE);
                g2.fillRoundRect(
                        0, 0, getWidth() - 1,
                        getHeight() - 1, 22, 22
                );

                // Subtle border
                g2.setColor(UITheme.BORDER);
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(
                        0, 0, getWidth() - 1,
                        getHeight() - 1, 22, 22
                );

                // Blue accent line
                g2.setColor(UITheme.ACCENT);
                g2.fillRoundRect(
                        22, 0, 55, 5, 5, 5
                );

                g2.dispose();
            }
        };

        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setBackground(UITheme.WHITE);

        card.setBorder(new EmptyBorder(28, 28, 28, 28));

        JLabel numberLabel = new JLabel(number);
        numberLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        numberLabel.setForeground(UITheme.ACCENT);

        JLabel titleLabel = new JLabel(heading);
        titleLabel.setFont(UITheme.HEADING_FONT);
        titleLabel.setForeground(UITheme.TEXT);

        JLabel descriptionLabel = new JLabel(
                "<html><div style='width:230px;'>"
                        + description
                        + "</div></html>"
        );

        descriptionLabel.setFont(UITheme.NORMAL_FONT);
        descriptionLabel.setForeground(UITheme.MUTED);

        card.add(numberLabel);
        card.add(Box.createVerticalStrut(18));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(12));
        card.add(descriptionLabel);

        return card;
    }
}
