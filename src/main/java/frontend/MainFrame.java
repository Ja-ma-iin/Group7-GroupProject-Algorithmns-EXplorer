
package frontend;

import backend.RecursionController;
import backend.DataStructuresController;
import backend.SortingController;
import backend.ComplexityAnalysisController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel contentPanel;
    private JButton activeButton;
    private JButton dashboardButton;

    // Controller references
    private RecursionController recursionController;
    private DataStructuresController dataStructuresController;
    private SortingController sortingController;
    private ComplexityAnalysisController complexityController;

    private final Color ACTIVE_COLOR = new Color(66, 107, 154);
    private final Color NORMAL_COLOR = UITheme.SIDEBAR;
    private final Color HOVER_COLOR = UITheme.SIDEBAR_HOVER;

    public MainFrame() {

        setTitle("Algorithm Explorer");
        setSize(1200, 750);
        setMinimumSize(new Dimension(950, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createMainArea(), BorderLayout.CENTER);

        showDashboard();
    }

    // =====================================================
    // SIDEBAR
    // =====================================================

    private JPanel createSidebar() {

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(UITheme.SIDEBAR);
        sidebar.setPreferredSize(new Dimension(255, 750));
        sidebar.setBorder(new EmptyBorder(25, 15, 20, 15));

        JButton logoButton =
                createSidebarButton("ALGORITHM EXPLORER", true);

        logoButton.setHorizontalAlignment(SwingConstants.CENTER);
        logoButton.addActionListener(e -> showDashboard());

        sidebar.add(logoButton);
        sidebar.add(Box.createVerticalStrut(40));

        addNavigationButton(sidebar, "Dashboard",
                this::showDashboard);

        addNavigationButton(sidebar, "Recursion Lab",
                this::showRecursionLab);

        addNavigationButton(sidebar, "Data Structures",
                this::showDataStructuresLab);

        addNavigationButton(sidebar, "Sorting Lab",
                this::showSortingLab);

        addNavigationButton(sidebar, "Complexity Analysis",
                this::showComplexityAnalysis);

        sidebar.add(Box.createVerticalGlue());

        addNavigationButton(sidebar, "Group 7 Members",
                this::showGroupMembers);

        return sidebar;
    }

    // =====================================================
    // SIDEBAR BUTTON DESIGN
    // =====================================================

    private JButton createSidebarButton(String text, boolean isLogo) {

        JButton button = new JButton(text) {

            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(getBackground());

                g2.fillRoundRect(
                        3, 3,
                        getWidth() - 6,
                        getHeight() - 6,
                        18, 18
                );

                g2.dispose();

                super.paintComponent(g);
            }
        };

        button.setFont(isLogo
                ? new Font("Segoe UI", Font.BOLD, 16)
                : new Font("Segoe UI", Font.PLAIN, 14));

        button.setForeground(isLogo
                ? new Color(190, 215, 245)
                : Color.WHITE);

        button.setBackground(NORMAL_COLOR);

        button.setMaximumSize(new Dimension(225, 48));
        button.setPreferredSize(new Dimension(225, 48));

        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(new EmptyBorder(12, 18, 12, 10));

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    // =====================================================
    // NAVIGATION BUTTONS
    // =====================================================

    private void addNavigationButton(
            JPanel sidebar,
            String text,
            Runnable action) {

        JButton button = createSidebarButton(text, false);

        if (text.equals("Dashboard")) {
            dashboardButton = button;
        }

        button.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {

                if (button != activeButton) {
                    button.setBackground(HOVER_COLOR);
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {

                if (button != activeButton) {
                    button.setBackground(NORMAL_COLOR);
                }
            }
        });

        button.addActionListener(e -> {

            setActiveButton(button);

            try {
                action.run();

            } catch (Exception ex) {

                ex.printStackTrace();

                JOptionPane.showMessageDialog(
                        MainFrame.this,
                        "Unable to open " + text + ":\n"
                                + ex.getClass().getSimpleName()
                                + ": " + ex.getMessage(),
                        "Navigation Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        sidebar.add(button);
        sidebar.add(Box.createVerticalStrut(8));
    }

    // =====================================================
    // ACTIVE BUTTON
    // =====================================================

    private void setActiveButton(JButton button) {

        if (activeButton != null) {
            activeButton.setBackground(NORMAL_COLOR);
        }

        activeButton = button;

        if (activeButton != null) {
            activeButton.setBackground(ACTIVE_COLOR);
        }
    }

    // =====================================================
    // MAIN CONTENT AREA
    // =====================================================

    private JPanel createMainArea() {

        JPanel mainArea = new JPanel(new BorderLayout());
        mainArea.setBackground(UITheme.BACKGROUND);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UITheme.WHITE);
        header.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel heading = new JLabel("Algorithm Explorer");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 21));
        heading.setForeground(UITheme.TEXT);

        header.add(heading, BorderLayout.WEST);

        mainArea.add(header, BorderLayout.NORTH);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(UITheme.BACKGROUND);

        mainArea.add(contentPanel, BorderLayout.CENTER);

        return mainArea;
    }

    // =====================================================
    // DISPLAY PANEL
    // =====================================================

    private void displayPanel(JPanel panel) {

        contentPanel.removeAll();

        contentPanel.add(panel, BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // =====================================================
    // DASHBOARD
    // =====================================================

    private void showDashboard() {

        if (dashboardButton != null) {
            setActiveButton(dashboardButton);
        }

        displayPanel(new DashboardPanel());
    }

    // =====================================================
    // RECURSION LAB
    // =====================================================

    private void showRecursionLab() {

        RecursionPanel recursionPanel = new RecursionPanel();

        recursionController =
                new RecursionController(recursionPanel);

        recursionPanel.setListener(recursionController);

        displayPanel(recursionPanel);
    }

    // =====================================================
    // DATA STRUCTURES LAB
    // =====================================================

    private void showDataStructuresLab() {

        DataStructuresPanel dataStructuresPanel =
                new DataStructuresPanel();

        dataStructuresController =
                new DataStructuresController(dataStructuresPanel);

        dataStructuresPanel.setListener(dataStructuresController);

        displayPanel(dataStructuresPanel);
    }

    // =====================================================
    // SORTING LAB
    // =====================================================

    private void showSortingLab() {

        SortingPanel sortingPanel = new SortingPanel();

        sortingController =
                new SortingController(sortingPanel);

        sortingPanel.setListener(sortingController);

        displayPanel(sortingPanel);
    }

    // =====================================================
    // COMPLEXITY ANALYSIS
    // =====================================================

    private void showComplexityAnalysis() {

        ComplexityAnalysisPanel complexityPanel =
                new ComplexityAnalysisPanel();

        complexityController =
                new ComplexityAnalysisController(complexityPanel);

        complexityPanel.setListener(complexityController);

        displayPanel(complexityPanel);
    }

    // =====================================================
    // GROUP MEMBERS
    // =====================================================

    private void showGroupMembers() {

        String[] members = {
                "1. Kasozi Reymond Samuel MB",
                "2. Kugonza Lawrence",
                "3. Oketcho Caleb Jamen",
                "4. Lulemesha Isaac",
                "5. Menha Samuel Victor"
        };

        JOptionPane.showMessageDialog(
                this,
                String.join("\n\n", members),
                "GROUP 7 MEMBERS",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
