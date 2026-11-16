
package frontend;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class RecursionPanel extends JPanel {

    // =========================================================
    // BACKEND CONNECTION
    // =========================================================

    public interface Listener {
        void calculateFibonacci(int n, FibonacciMethod method);
        void calculateFactorial(int n, FactorialMethod method);

        void startHanoi(int disks, String source, String destination,
                        String auxiliary, int delayMillis);

        void pauseHanoi();
        void stepHanoi();
        void resetHanoi(int disks, String source);
    }

    public enum FibonacciMethod {
        NAIVE_RECURSION,
        MEMOIZED_RECURSION,
        BOTTOM_UP_DP
    }

    public enum FactorialMethod {
        RECURSIVE,
        ITERATIVE
    }

    private Listener listener;

    // =========================================================
    // THEME
    // =========================================================

    private final Color BACKGROUND = new Color(244, 247, 251);
    private final Color WHITE = Color.WHITE;
    private final Color NAVY = new Color(27, 43, 67);
    private final Color TEXT = new Color(47, 61, 80);
    private final Color MUTED = new Color(119, 132, 150);

    private final Color PRIMARY = new Color(55, 101, 174);
    private final Color PRIMARY_HOVER = new Color(39, 82, 151);

    private final Color GREEN = new Color(35, 145, 104);
    private final Color GREEN_HOVER = new Color(27, 119, 84);

    private final Color AMBER = new Color(218, 147, 48);
    private final Color AMBER_HOVER = new Color(188, 119, 27);

    private final Color RED = new Color(190, 76, 83);
    private final Color RED_HOVER = new Color(157, 54, 62);

    private final Color BORDER = new Color(225, 232, 241);

    // =========================================================
    // COMPONENTS
    // =========================================================

    private JTextField fibonacciInput;
    private JComboBox<String> fibonacciMethod;

    private JTextField factorialInput;
    private JComboBox<String> factorialMethod;

    // Editable disk input
    private JTextField diskInput;

    private JComboBox<String> sourceInput;
    private JComboBox<String> destinationInput;
    private JComboBox<String> auxiliaryInput;

    private JSlider speedSlider;
    private JLabel speedLabel;

    private JLabel moveLabel;
    private JLabel totalMovesLabel;

    private DiskBoard hanoiBoard;

    private ResultCard fibonacciResult;
    private ResultCard factorialResult;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public RecursionPanel() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(22, 26, 22, 26));

        buildInterface();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private void buildInterface() {
        JPanel main = new JPanel(new BorderLayout(0, 20));
        main.setOpaque(false);

        main.add(createHeader(), BorderLayout.NORTH);
        main.add(createTabs(), BorderLayout.CENTER);

        add(main, BorderLayout.CENTER);
    }

    // =========================================================
    // HEADER
    // =========================================================

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

        JLabel eyebrow = new JLabel("ALGORITHM LAB  /  MODULE 01");
        eyebrow.setFont(new Font("Segoe UI", Font.BOLD, 11));
        eyebrow.setForeground(PRIMARY);

        JLabel title = new JLabel("Recursion Lab");
        title.setFont(new Font("Segoe UI", Font.BOLD, 29));
        title.setForeground(NAVY);

        JLabel subtitle = new JLabel(
                "Explore recursive problem-solving through interactive examples."
        );
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);

        heading.add(eyebrow);
        heading.add(Box.createVerticalStrut(5));
        heading.add(title);
        heading.add(Box.createVerticalStrut(4));
        heading.add(subtitle);

        JPanel status = new JPanel(new FlowLayout(
                FlowLayout.CENTER, 8, 7
        ));
        status.setBackground(new Color(233, 247, 239));
        status.setBorder(new LineBorder(
                new Color(207, 231, 216), 1, true
        ));

        JLabel dot = new JLabel("●");
        dot.setForeground(GREEN);

        JLabel statusText = new JLabel("Interactive module");
        statusText.setFont(new Font("Segoe UI", Font.BOLD, 11));
        statusText.setForeground(new Color(54, 119, 83));

        status.add(dot);
        status.add(statusText);

        JPanel statusWrapper = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 0, 5)
        );
        statusWrapper.setOpaque(false);
        statusWrapper.add(status);

        header.add(heading, BorderLayout.WEST);
        header.add(statusWrapper, BorderLayout.EAST);

        return header;
    }

    // =========================================================
    // TABS
    // =========================================================

    private JTabbedPane createTabs() {
        JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);

        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabs.setBackground(BACKGROUND);
        tabs.setForeground(TEXT);
        tabs.setOpaque(false);
        tabs.setFocusable(false);
        tabs.setBorder(BorderFactory.createEmptyBorder());

        tabs.setUI(new BasicTabbedPaneUI() {

            @Override
            protected void installDefaults() {
                super.installDefaults();
                tabAreaInsets = new Insets(8, 3, 12, 3);
                contentBorderInsets = new Insets(0, 0, 0, 0);
                tabInsets = new Insets(12, 24, 12, 24);
            }

            @Override
            protected void paintTabBackground(
                    Graphics g, int placement, int index,
                    int x, int y, int width, int height,
                    boolean selected
            ) {
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(selected
                        ? PRIMARY
                        : new Color(235, 240, 247));

                g2.fillRoundRect(
                        x + 3, y + 3,
                        width - 6, height - 6,
                        13, 13
                );

                g2.dispose();
            }

            @Override
            protected void paintTabBorder(
                    Graphics g, int placement, int index,
                    int x, int y, int width, int height,
                    boolean selected
            ) {
            }

            @Override
            protected void paintContentBorder(
                    Graphics g, int placement, int selectedIndex
            ) {
            }

            @Override
            protected Insets getTabInsets(int placement, int index) {
                return new Insets(12, 24, 12, 24);
            }

            @Override
            protected void paintText(
                    Graphics g, int placement, Font font,
                    FontMetrics metrics, int index, String title,
                    Rectangle textRect, boolean selected
            ) {
                g.setFont(font);
                g.setColor(selected ? WHITE : TEXT);

                super.paintText(
                        g, placement, font, metrics,
                        index, title, textRect, selected
                );
            }
        });

        tabs.addTab("01   Fibonacci", createFibonacciTab());
        tabs.addTab("02   Factorial", createFactorialTab());
        tabs.addTab("03   Tower of Hanoi", createHanoiTab());

        return tabs;
    }

    // =========================================================
    // FIBONACCI
    // =========================================================

    private JPanel createFibonacciTab() {
        JPanel page = createPage();

        JPanel content = new JPanel(new GridBagLayout());
        content.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 15, 15);

        JPanel inputCard = createCard();
        inputCard.setLayout(new BoxLayout(inputCard, BoxLayout.Y_AXIS));

        addSectionTitle(
                inputCard,
                "Fibonacci calculator",
                "Calculate a sequence value using different algorithms."
        );

        inputCard.add(Box.createVerticalStrut(23));
        inputCard.add(fieldLabel("Input value (n)"));

        fibonacciInput = createTextField("Enter a number, e.g. 10");
        inputCard.add(fibonacciInput);

        inputCard.add(Box.createVerticalStrut(17));
        inputCard.add(fieldLabel("Algorithm"));

        fibonacciMethod = new JComboBox<>(new String[]{
                "Naive Recursion",
                "Memoized Recursion",
                "Bottom-up Dynamic Programming"
        });

        styleComboBox(fibonacciMethod);
        inputCard.add(fibonacciMethod);

        inputCard.add(Box.createVerticalStrut(24));

        JButton calculate = primaryButton(
                "Calculate Fibonacci", PRIMARY, PRIMARY_HOVER
        );
        calculate.addActionListener(e -> calculateFibonacci());
        inputCard.add(calculate);

        inputCard.add(Box.createVerticalStrut(12));
        inputCard.add(smallText(
                "Naive recursion may become slow for larger inputs."
        ));

        gbc.weighty = 1;
        content.add(inputCard, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 15, 0);

        JPanel infoCard = createCard();
        infoCard.setLayout(new BoxLayout(infoCard, BoxLayout.Y_AXIS));

        addSectionTitle(
                infoCard,
                "How Fibonacci works",
                "Every number is the sum of the two preceding numbers."
        );

        infoCard.add(Box.createVerticalStrut(24));

        JLabel formula = new JLabel("F(n) = F(n-1) + F(n-2)");
        formula.setFont(new Font("Consolas", Font.BOLD, 18));
        formula.setForeground(PRIMARY);
        formula.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoCard.add(formula);
        infoCard.add(Box.createVerticalStrut(20));

        addInfoLine(infoCard, "Base cases", "F(0) = 0, F(1) = 1");
        addInfoLine(infoCard, "Naive recursion", "Repeated function calls");
        addInfoLine(infoCard, "Memoization", "Stores previous results");
        addInfoLine(infoCard, "Bottom-up DP", "Builds answers iteratively");

        content.add(infoCard, gbc);

        fibonacciResult = new ResultCard("Calculation results");

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        gbc.insets = new Insets(0, 0, 0, 0);

        content.add(fibonacciResult, gbc);

        // Keep the complete calculator content reachable on shorter screens.
        // The result card sits below the input and information cards, so the
        // tab needs vertical scrolling instead of clipping its lower section.
        page.add(createScrollPane(content), BorderLayout.CENTER);
        return page;
    }

    private void calculateFibonacci() {
        Integer n = parseInteger(fibonacciInput.getText());

        if (n == null || n < 0) {
            showError("Enter a valid non-negative whole number.");
            return;
        }

        if (n > 30 && fibonacciMethod.getSelectedIndex() == 0) {
            showError("For Naive Recursion, use an input of 30 or less.");
            return;
        }

        // Fibonacci uses long values in the backend, so the largest
        // supported input is 92. Naive recursion has the stricter limit above.
        if (n > 92) {
            showError("Please enter a value of 92 or less.");
            return;
        }

        if (listener == null) {
            showBackendMessage();
            return;
        }

        FibonacciMethod method;

        switch (fibonacciMethod.getSelectedIndex()) {
            case 1:
                method = FibonacciMethod.MEMOIZED_RECURSION;
                break;
            case 2:
                method = FibonacciMethod.BOTTOM_UP_DP;
                break;
            default:
                method = FibonacciMethod.NAIVE_RECURSION;
        }

        fibonacciResult.setPending();
        listener.calculateFibonacci(n, method);
    }

    // =========================================================
    // FACTORIAL
    // =========================================================

    private JPanel createFactorialTab() {
        JPanel page = createPage();

        JPanel content = new JPanel(new GridBagLayout());
        content.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 15, 15);

        JPanel inputCard = createCard();
        inputCard.setLayout(new BoxLayout(inputCard, BoxLayout.Y_AXIS));

        addSectionTitle(
                inputCard,
                "Factorial calculator",
                "Compare recursive and iterative calculations."
        );

        inputCard.add(Box.createVerticalStrut(23));
        inputCard.add(fieldLabel("Input value (n)"));

        factorialInput = createTextField("Enter a number, e.g. 5");
        inputCard.add(factorialInput);

        inputCard.add(Box.createVerticalStrut(17));
        inputCard.add(fieldLabel("Calculation method"));

        factorialMethod = new JComboBox<>(new String[]{
                "Recursive",
                "Iterative"
        });

        styleComboBox(factorialMethod);
        inputCard.add(factorialMethod);

        inputCard.add(Box.createVerticalStrut(24));

        JButton calculate = primaryButton(
                "Calculate Factorial", PRIMARY, PRIMARY_HOVER
        );
        calculate.addActionListener(e -> calculateFactorial());
        inputCard.add(calculate);

        inputCard.add(Box.createVerticalStrut(12));
        inputCard.add(smallText(
                "Factorial is defined for non-negative integers."
        ));

        content.add(inputCard, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 15, 0);

        JPanel infoCard = createCard();
        infoCard.setLayout(new BoxLayout(infoCard, BoxLayout.Y_AXIS));

        addSectionTitle(
                infoCard,
                "Understanding factorial",
                "The product of all positive integers up to a given number."
        );

        infoCard.add(Box.createVerticalStrut(24));

        JLabel formula = new JLabel("n! = n × (n-1) × ... × 1");
        formula.setFont(new Font("Consolas", Font.BOLD, 18));
        formula.setForeground(PRIMARY);
        formula.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoCard.add(formula);
        infoCard.add(Box.createVerticalStrut(20));

        addInfoLine(infoCard, "Example", "5! = 5 × 4 × 3 × 2 × 1");
        addInfoLine(infoCard, "Result", "5! = 120");
        addInfoLine(infoCard, "Recursive", "Calls itself with a smaller value");
        addInfoLine(infoCard, "Iterative", "Uses a loop");

        content.add(infoCard, gbc);

        factorialResult = new ResultCard("Calculation results");

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        gbc.insets = new Insets(0, 0, 0, 0);

        content.add(factorialResult, gbc);

        // Keep the complete calculator content reachable on shorter screens.
        // The result card sits below the input and information cards, so the
        // tab needs vertical scrolling instead of clipping its lower section.
        page.add(createScrollPane(content), BorderLayout.CENTER);
        return page;
    }

    private void calculateFactorial() {
        Integer n = parseInteger(factorialInput.getText());

        if (n == null || n < 0) {
            showError("Enter a valid non-negative whole number.");
            return;
        }

        // Factorial uses long values in the backend, which supports n <= 20.
        if (n > 20) {
            showError("Please enter a value of 20 or less.");
            return;
        }

        if (listener == null) {
            showBackendMessage();
            return;
        }

        FactorialMethod method =
                factorialMethod.getSelectedIndex() == 0
                        ? FactorialMethod.RECURSIVE
                        : FactorialMethod.ITERATIVE;

        factorialResult.setPending();
        listener.calculateFactorial(n, method);
    }

    // =========================================================
    // TOWER OF HANOI - TWO COLUMN LAYOUT
    // =========================================================

    private JPanel createHanoiTab() {

        JPanel page = createPage();
        page.setLayout(new BorderLayout(0, 15));

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Tower of Hanoi Simulation");
        title.setFont(new Font("Segoe UI", Font.BOLD, 21));
        title.setForeground(NAVY);

        JLabel subtitle = new JLabel(
                "Explore recursive disk movements through an interactive simulation."
        );
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(MUTED);

        heading.add(title);
        heading.add(Box.createVerticalStrut(5));
        heading.add(subtitle);

        page.add(heading, BorderLayout.NORTH);

        // Simulation card
        JPanel simulationCard = createCard();
        simulationCard.setLayout(new BorderLayout(0, 12));
        simulationCard.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        new EmptyBorder(16, 16, 16, 16)
                )
        );

        JPanel boardHeader = new JPanel(new BorderLayout());
        boardHeader.setOpaque(false);

        JLabel liveLabel = new JLabel("●  LIVE VISUALISATION");
        liveLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        liveLabel.setForeground(GREEN);

        JLabel diskHint = new JLabel("Supports 1–8 disks");
        diskHint.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        diskHint.setForeground(MUTED);

        boardHeader.add(liveLabel, BorderLayout.WEST);
        boardHeader.add(diskHint, BorderLayout.EAST);

        simulationCard.add(boardHeader, BorderLayout.NORTH);

        hanoiBoard = new DiskBoard();
        simulationCard.add(hanoiBoard, BorderLayout.CENTER);

        JPanel statistics = new JPanel(new GridLayout(1, 2, 12, 0));
        statistics.setOpaque(false);

        moveLabel = new JLabel("0");
        totalMovesLabel = new JLabel("7");

        statistics.add(createStatCard("CURRENT MOVE", moveLabel));
        statistics.add(createStatCard(
                "TOTAL MOVES REQUIRED", totalMovesLabel
        ));

        simulationCard.add(statistics, BorderLayout.SOUTH);

        JPanel controls = createHanoiControls();

        JScrollPane controlsScroll = new JScrollPane(controls);
        controlsScroll.setBorder(null);
        controlsScroll.setOpaque(false);
        controlsScroll.getViewport().setOpaque(false);
        controlsScroll.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        controlsScroll.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        );
        controlsScroll.getVerticalScrollBar().setUnitIncrement(16);

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                simulationCard,
                controlsScroll
        );

        splitPane.setOpaque(false);
        splitPane.setBorder(null);
        splitPane.setDividerSize(8);
        splitPane.setResizeWeight(0.69);
        splitPane.setContinuousLayout(true);
        splitPane.setOneTouchExpandable(false);

        simulationCard.setMinimumSize(new Dimension(350, 300));
        controlsScroll.setMinimumSize(new Dimension(250, 300));

        page.add(splitPane, BorderLayout.CENTER);

        resetHanoiBoardOnly();
        updateSpeedLabel();

        return page;
    }

    // =========================================================
    // HANOI CONTROLS
    // =========================================================

    private JPanel createHanoiControls() {

        JPanel controls = createCard();
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));

        controls.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        new EmptyBorder(17, 15, 17, 15)
                )
        );

        JLabel heading = new JLabel("Simulation Controls");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 17));
        heading.setForeground(NAVY);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel description = smallText(
                "Configure and control the disk movements."
        );
        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        controls.add(heading);
        controls.add(Box.createVerticalStrut(5));
        controls.add(description);
        controls.add(Box.createVerticalStrut(22));

        // Editable number of disks
        diskInput = createTextField("Enter number of disks (1–8)");
        diskInput.setText("3");
        diskInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        controls.add(createHanoiField(
                "NUMBER OF DISKS (1–8)", diskInput
        ));

        controls.add(Box.createVerticalStrut(15));

        // Peg selectors
        sourceInput = createPegCombo("A", "B", "C");
        destinationInput = createPegCombo("C", "A", "B");
        auxiliaryInput = createPegCombo("B", "C", "A");

        controls.add(createHanoiField(
                "SOURCE PEG", sourceInput
        ));
        controls.add(Box.createVerticalStrut(13));

        controls.add(createHanoiField(
                "DESTINATION PEG", destinationInput
        ));
        controls.add(Box.createVerticalStrut(13));

        controls.add(createHanoiField(
                "AUXILIARY PEG", auxiliaryInput
        ));
        controls.add(Box.createVerticalStrut(20));

        // Speed
        JLabel speedHeading = fieldLabel("ANIMATION SPEED");
        speedHeading.setAlignmentX(Component.LEFT_ALIGNMENT);

        controls.add(speedHeading);
        controls.add(Box.createVerticalStrut(5));

        speedSlider = new JSlider(1, 10, 5);
        speedSlider.setOpaque(false);
        speedSlider.setForeground(PRIMARY);
        speedSlider.setMajorTickSpacing(3);
        speedSlider.setPaintTicks(true);
        speedSlider.setSnapToTicks(true);
        speedSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
        speedSlider.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 48)
        );

        controls.add(speedSlider);

        speedLabel = smallText("Normal speed");
        speedLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        controls.add(speedLabel);
        controls.add(Box.createVerticalStrut(20));

        // Movement rule
        JPanel ruleCard = new JPanel();
        ruleCard.setLayout(new BoxLayout(ruleCard, BoxLayout.Y_AXIS));
        ruleCard.setBackground(new Color(243, 247, 252));
        ruleCard.setBorder(new EmptyBorder(12, 12, 12, 12));
        ruleCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        ruleCard.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 90)
        );

        JLabel ruleTitle = new JLabel("MOVEMENT RULE");
        ruleTitle.setFont(new Font("Segoe UI", Font.BOLD, 10));
        ruleTitle.setForeground(PRIMARY);

        JLabel ruleDescription = new JLabel(
                "<html>Move one disk at a time.<br>"
                        + "Never place a larger disk on a smaller one.</html>"
        );
        ruleDescription.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        ruleDescription.setForeground(TEXT);

        ruleCard.add(ruleTitle);
        ruleCard.add(Box.createVerticalStrut(5));
        ruleCard.add(ruleDescription);

        controls.add(ruleCard);
        controls.add(Box.createVerticalStrut(22));

        // Playback
        JLabel playbackHeading = new JLabel("Playback");
        playbackHeading.setFont(new Font("Segoe UI", Font.BOLD, 14));
        playbackHeading.setForeground(NAVY);
        playbackHeading.setAlignmentX(Component.LEFT_ALIGNMENT);

        controls.add(playbackHeading);
        controls.add(Box.createVerticalStrut(10));

        JPanel buttons = new JPanel(new GridLayout(2, 2, 8, 9));
        buttons.setOpaque(false);
        buttons.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 100)
        );

        JButton start = primaryButton(
                "▶  Start", GREEN, GREEN_HOVER
        );

        JButton pause = primaryButton(
                "Ⅱ  Pause", AMBER, AMBER_HOVER
        );

        JButton step = primaryButton(
                "→  Next Move", PRIMARY, PRIMARY_HOVER
        );

        JButton reset = primaryButton(
                "↻  Reset", RED, RED_HOVER
        );

        start.setPreferredSize(new Dimension(110, 42));
        pause.setPreferredSize(new Dimension(110, 42));
        step.setPreferredSize(new Dimension(110, 42));
        reset.setPreferredSize(new Dimension(110, 42));

        start.addActionListener(e -> startHanoi());

        pause.addActionListener(e -> {
            if (listener != null) {
                listener.pauseHanoi();
            } else {
                showBackendMessage();
            }
        });

        step.addActionListener(e -> {
            if (listener != null) {
                listener.stepHanoi();
            } else {
                showBackendMessage();
            }
        });

        reset.addActionListener(e -> resetHanoi());

        buttons.add(start);
        buttons.add(pause);
        buttons.add(step);
        buttons.add(reset);

        controls.add(buttons);

        // Update move count when the user types.
        diskInput.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        updateTotalMoves();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        updateTotalMoves();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        updateTotalMoves();
                    }
                }
        );

        diskInput.addActionListener(e -> resetHanoiBoardOnly());

        sourceInput.addActionListener(e -> resetHanoiBoardOnly());

        speedSlider.addChangeListener(e -> updateSpeedLabel());

        return controls;
    }

    private JPanel createHanoiField(
            String title,
            JComponent component
    ) {

        JPanel field = new JPanel();
        field.setOpaque(false);
        field.setLayout(new BoxLayout(field, BoxLayout.Y_AXIS));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 75)
        );

        JLabel label = fieldLabel(title);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        component.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Preserve the component's preferred height.
        component.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 40)
        );

        field.add(label);
        field.add(Box.createVerticalStrut(6));
        field.add(component);

        return field;
    }

    private JComboBox<String> createPegCombo(String... values) {
        JComboBox<String> combo = new JComboBox<>(values);
        styleComboBox(combo);
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        combo.setPreferredSize(new Dimension(200, 38));
        combo.setAlignmentX(Component.LEFT_ALIGNMENT);
        return combo;
    }

    // =========================================================
    // HANOI ACTIONS
    // =========================================================

    private void startHanoi() {

        Integer disks = parseInteger(diskInput.getText());

        if (disks == null || disks < 1 || disks > 8) {
            showError("Enter a valid number of disks between 1 and 8.");
            diskInput.requestFocusInWindow();
            return;
        }

        String source = (String) sourceInput.getSelectedItem();
        String destination = (String) destinationInput.getSelectedItem();
        String auxiliary = (String) auxiliaryInput.getSelectedItem();

        if (source.equals(destination)
                || source.equals(auxiliary)
                || destination.equals(auxiliary)) {
            showError("Choose three different pegs.");
            return;
        }

        if (listener == null) {
            showBackendMessage();
            return;
        }

        int delay = 1100 - (speedSlider.getValue() * 100);

        resetHanoiBoardOnly();

        listener.startHanoi(
                disks, source, destination, auxiliary, delay
        );
    }

    private void resetHanoi() {

        Integer disks = parseInteger(diskInput.getText());

        if (disks == null || disks < 1 || disks > 8) {
            showError("Enter a valid number of disks between 1 and 8.");
            diskInput.requestFocusInWindow();
            return;
        }

        String source = (String) sourceInput.getSelectedItem();

        resetHanoiBoardOnly();

        if (listener != null) {
            listener.resetHanoi(disks, source);
        } else {
            showBackendMessage();
        }
    }

    private void resetHanoiBoardOnly() {

        if (hanoiBoard == null || diskInput == null) {
            return;
        }

        Integer disks = parseInteger(diskInput.getText());

        if (disks == null || disks < 1 || disks > 8) {
            return;
        }

        String source = sourceInput == null
                ? "A"
                : (String) sourceInput.getSelectedItem();

        List<List<Integer>> pegs = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            pegs.add(new ArrayList<>());
        }

        int sourceIndex = source.charAt(0) - 'A';

        if (sourceIndex < 0 || sourceIndex > 2) {
            sourceIndex = 0;
        }

        for (int i = disks; i >= 1; i--) {
            pegs.get(sourceIndex).add(i);
        }

        hanoiBoard.setState(pegs);

        if (moveLabel != null) {
            moveLabel.setText("0");
        }

        if (totalMovesLabel != null) {
            totalMovesLabel.setText(
                    String.valueOf((1 << disks) - 1)
            );
        }
    }

    private void updateTotalMoves() {

        if (diskInput == null || totalMovesLabel == null) {
            return;
        }

        Integer disks = parseInteger(diskInput.getText());

        if (disks != null && disks >= 1 && disks <= 8) {
            totalMovesLabel.setText(
                    String.valueOf((1 << disks) - 1)
            );
        } else {
            totalMovesLabel.setText("--");
        }
    }

    private void updateSpeedLabel() {

        if (speedLabel == null || speedSlider == null) {
            return;
        }

        int speed = speedSlider.getValue();

        if (speed <= 3) {
            speedLabel.setText("Slow speed");
        } else if (speed <= 7) {
            speedLabel.setText("Normal speed");
        } else {
            speedLabel.setText("Fast speed");
        }
    }

    // =========================================================
    // BACKEND UPDATE METHODS
    // =========================================================

    public void showFibonacciResult(
            String result, String operations, String time
    ) {
        SwingUtilities.invokeLater(() ->
                fibonacciResult.setResult(result, operations, time)
        );
    }

    public void showFactorialResult(
            String result, String operations, String time
    ) {
        SwingUtilities.invokeLater(() ->
                factorialResult.setResult(result, operations, time)
        );
    }

    public void updateHanoiState(
            List<List<Integer>> pegs,
            int currentMove,
            int totalMoves
    ) {
        SwingUtilities.invokeLater(() -> {
            if (hanoiBoard != null) {
                hanoiBoard.setState(pegs);
            }

            if (moveLabel != null) {
                moveLabel.setText(String.valueOf(currentMove));
            }

            if (totalMovesLabel != null) {
                totalMovesLabel.setText(String.valueOf(totalMoves));
            }
        });
    }

    // =========================================================
    // GENERAL DESIGN COMPONENTS
    // =========================================================

    private JPanel createPage() {
        JPanel page = new JPanel(new BorderLayout());
        page.setOpaque(false);
        page.setBorder(new EmptyBorder(18, 3, 3, 3));
        return page;
    }

    // Wrap calculator tabs in a scroll pane so their result cards remain
    // accessible even when the application window is not very tall.
    private JScrollPane createScrollPane(JComponent component) {
        JScrollPane scrollPane = new JScrollPane(component);

        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        scrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        );
        scrollPane.getVerticalScrollBar().setUnitIncrement(18);

        return scrollPane;
    }

    private JPanel createCard() {
        JPanel card = new JPanel();
        card.setBackground(WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(20, 21, 20, 21)
        ));

        return card;
    }

    private void addSectionTitle(
            JPanel panel, String title, String subtitle
    ) {
        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 17));
        heading.setForeground(NAVY);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel description = new JLabel(subtitle);
        description.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        description.setForeground(MUTED);
        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(heading);
        panel.add(Box.createVerticalStrut(5));
        panel.add(description);
    }

    private JLabel fieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setForeground(TEXT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JLabel smallText(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        label.setForeground(MUTED);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createTextField(String placeholder) {

        JTextField field = new JTextField();

        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setForeground(TEXT);
        field.setCaretColor(PRIMARY);
        field.setBackground(new Color(249, 251, 254));

        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(10, 12, 10, 12)
        ));

        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        field.setPreferredSize(new Dimension(200, 42));
        field.setToolTipText(placeholder);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        return field;
    }

    private void styleComboBox(JComboBox<String> combo) {

        combo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        combo.setForeground(TEXT);
        combo.setBackground(WHITE);
        combo.setBorder(new LineBorder(BORDER, 1, true));

        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        combo.setPreferredSize(new Dimension(200, 40));
        combo.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JButton primaryButton(
            String text, Color normalColor, Color hoverColor
    ) {

        JButton button = new JButton(text);

        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(WHITE);
        button.setBackground(normalColor);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 15, 10, 15));
        button.setPreferredSize(new Dimension(100, 38));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalColor);
            }
        });

        return button;
    }

    private void addInfoLine(
            JPanel panel, String heading, String description
    ) {

        JPanel row = new JPanel(new BorderLayout(8, 3));
        row.setOpaque(false);
        row.setBorder(new EmptyBorder(8, 0, 8, 0));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel left = new JLabel(heading);
        left.setFont(new Font("Segoe UI", Font.BOLD, 12));
        left.setForeground(TEXT);

        JLabel right = new JLabel(description);
        right.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        right.setForeground(MUTED);
        right.setHorizontalAlignment(SwingConstants.RIGHT);

        row.add(left, BorderLayout.WEST);
        row.add(right, BorderLayout.EAST);

        panel.add(row);
        panel.add(new JSeparator());
    }

    private JPanel createStatCard(String title, JLabel value) {

        JPanel card = new JPanel(new BorderLayout(0, 5));
        card.setBackground(WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(12, 16, 12, 16)
        ));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 10));
        heading.setForeground(MUTED);

        value.setFont(new Font("Segoe UI", Font.BOLD, 22));
        value.setForeground(PRIMARY);

        card.add(heading, BorderLayout.NORTH);
        card.add(value, BorderLayout.CENTER);

        return card;
    }

    // =========================================================
    // RESULT CARD
    // =========================================================

    private class ResultCard extends JPanel {

        private final JLabel resultValue =
                new JLabel("Waiting for calculation");

        private final JLabel operationsValue = new JLabel("--");
        private final JLabel timeValue = new JLabel("--");

        ResultCard(String heading) {

            setLayout(new BorderLayout(0, 14));
            setBackground(WHITE);

            setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(BORDER, 1, true),
                    new EmptyBorder(17, 21, 17, 21)
            ));

            JLabel title = new JLabel(heading);
            title.setFont(new Font("Segoe UI", Font.BOLD, 15));
            title.setForeground(NAVY);

            JPanel values = new JPanel(new GridLayout(1, 3, 18, 0));
            values.setOpaque(false);

            values.add(createMetric("Result", resultValue));
            values.add(createMetric("Operations", operationsValue));
            values.add(createMetric("Execution time", timeValue));

            add(title, BorderLayout.NORTH);
            add(values, BorderLayout.CENTER);
        }

        private JPanel createMetric(String heading, JLabel value) {

            JPanel metric = new JPanel();
            metric.setOpaque(false);
            metric.setLayout(new BoxLayout(metric, BoxLayout.Y_AXIS));

            JLabel label = new JLabel(heading);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            label.setForeground(MUTED);

            value.setFont(new Font("Segoe UI", Font.BOLD, 16));
            value.setForeground(PRIMARY);

            metric.add(label);
            metric.add(Box.createVerticalStrut(5));
            metric.add(value);

            return metric;
        }

        void setPending() {
            resultValue.setText("Calculating...");
            operationsValue.setText("--");
            timeValue.setText("--");
        }

        void setResult(String result, String operations, String time) {
            resultValue.setText(result);
            operationsValue.setText(operations);
            timeValue.setText(time);
        }
    }

    // =========================================================
    // TOWER OF HANOI VISUALISATION
    // =========================================================

    private class DiskBoard extends JPanel {

        private List<List<Integer>> pegs = new ArrayList<>();

        private final Color[] diskColors = {
                new Color(62, 125, 199),
                new Color(57, 151, 176),
                new Color(51, 157, 129),
                new Color(126, 163, 93),
                new Color(197, 153, 66),
                new Color(211, 119, 78),
                new Color(185, 94, 124),
                new Color(125, 104, 180)
        };

        DiskBoard() {

            setOpaque(true);
            setBackground(new Color(248, 250, 254));

            setBorder(new LineBorder(
                    new Color(229, 235, 244), 1, true
            ));

            setPreferredSize(new Dimension(650, 400));
            setMinimumSize(new Dimension(320, 260));
        }

        void setState(List<List<Integer>> newState) {

            if (newState == null) {
                return;
            }

            pegs = new ArrayList<>();

            for (List<Integer> peg : newState) {
                pegs.add(
                        peg == null
                                ? new ArrayList<>()
                                : new ArrayList<>(peg)
                );
            }

            repaint();
        }

        @Override
        protected void paintComponent(Graphics graphics) {

            super.paintComponent(graphics);

            Graphics2D g = (Graphics2D) graphics.create();

            g.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g.setRenderingHint(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY
            );

            int width = getWidth();
            int height = getHeight();

            if (width <= 0 || height <= 0) {
                g.dispose();
                return;
            }

            int topY = 30;
            int baseY = height - 58;

            int[] centers = {
                    width / 6,
                    width / 2,
                    width * 5 / 6
            };

            int maxDisks = 1;

            for (List<Integer> peg : pegs) {
                maxDisks = Math.max(maxDisks, peg.size());
            }

            maxDisks = Math.min(maxDisks, 8);

            int availableHeight = Math.max(
                    70, baseY - topY - 8
            );

            int gap = 2;

            int diskHeight = Math.max(
                    10,
                    Math.min(
                            22,
                            (availableHeight - (maxDisks - 1) * gap)
                                    / maxDisks
                    )
            );

            // Floor shadow
            g.setColor(new Color(224, 232, 242));

            g.fillRoundRect(
                    18, baseY + 5,
                    width - 36, 10, 10, 10
            );

            // Floor
            GradientPaint floorGradient = new GradientPaint(
                    0, baseY,
                    new Color(174, 192, 215),
                    0, baseY + 12,
                    new Color(119, 145, 176)
            );

            g.setPaint(floorGradient);

            g.fillRoundRect(
                    18, baseY,
                    width - 36, 10, 10, 10
            );

            // Three rods
            for (int i = 0; i < 3; i++) {

                int center = centers[i];

                g.setColor(new Color(190, 205, 222, 90));

                g.fillRoundRect(
                        center - 3,
                        topY + 4,
                        14,
                        baseY - topY,
                        12, 12
                );

                GradientPaint pegGradient = new GradientPaint(
                        center - 5, 0,
                        new Color(205, 218, 234),
                        center + 5, 0,
                        new Color(105, 137, 174)
                );

                g.setPaint(pegGradient);

                g.fillRoundRect(
                        center - 5,
                        topY,
                        10,
                        baseY - topY,
                        10, 10
                );

                g.setColor(new Color(255, 255, 255, 150));

                g.drawLine(
                        center - 2,
                        topY + 5,
                        center - 2,
                        baseY - 5
                );
            }

            // Disks
            for (int i = 0; i < 3; i++) {

                if (i < pegs.size()) {
                    drawDisks(
                            g,
                            pegs.get(i),
                            centers[i],
                            baseY,
                            diskHeight,
                            gap,
                            width
                    );
                }
            }

            // Peg labels
            String[] labels = {
                    "PEG A",
                    "PEG B",
                    "PEG C"
            };

            g.setFont(new Font(
                    "Segoe UI", Font.BOLD, 12
            ));

            g.setColor(new Color(88, 105, 128));

            for (int i = 0; i < 3; i++) {
                drawCentered(
                        g,
                        labels[i],
                        centers[i],
                        height - 17
                );
            }

            g.dispose();
        }

        private void drawDisks(
                Graphics2D g,
                List<Integer> disks,
                int center,
                int baseY,
                int diskHeight,
                int gap,
                int boardWidth
        ) {

            int maxWidth = Math.min(
                    165,
                    Math.max(75, boardWidth / 3 - 35)
            );

            for (int i = 0; i < disks.size(); i++) {

                int disk = disks.get(i);

                if (disk < 1 || disk > 8) {
                    continue;
                }

                // Smaller disks have smaller widths.
                int diskWidth = maxWidth - ((8 - disk) * 12);
                diskWidth = Math.max(42, diskWidth);

                int x = center - diskWidth / 2;

                // Bottom-up stack arrangement
                int y = baseY - diskHeight
                        - i * (diskHeight + gap);

                Color diskColor =
                        diskColors[(disk - 1) % diskColors.length];

                // Shadow
                g.setColor(new Color(39, 56, 80, 35));

                g.fillRoundRect(
                        x + 2,
                        y + 3,
                        diskWidth,
                        diskHeight,
                        10, 10
                );

                // Gradient
                GradientPaint diskGradient = new GradientPaint(
                        x, y,
                        diskColor.brighter(),
                        x, y + diskHeight,
                        diskColor.darker()
                );

                g.setPaint(diskGradient);

                g.fillRoundRect(
                        x,
                        y,
                        diskWidth,
                        diskHeight,
                        10, 10
                );

                // Border
                g.setColor(diskColor.darker());

                g.drawRoundRect(
                        x,
                        y,
                        diskWidth,
                        diskHeight,
                        10, 10
                );

                // Highlight
                g.setColor(new Color(255, 255, 255, 120));

                g.drawLine(
                        x + 8,
                        y + 3,
                        x + diskWidth - 8,
                        y + 3
                );

                // Disk number
                if (diskHeight >= 15) {

                    g.setColor(Color.WHITE);

                    g.setFont(new Font(
                            "Segoe UI",
                            Font.BOLD,
                            Math.max(9, diskHeight - 9)
                    ));

                    drawCentered(
                            g,
                            String.valueOf(disk),
                            center,
                            y + diskHeight - 4
                    );
                }
            }
        }

        private void drawCentered(
                Graphics2D g,
                String text,
                int centerX,
                int y
        ) {

            FontMetrics metrics = g.getFontMetrics();

            g.drawString(
                    text,
                    centerX - metrics.stringWidth(text) / 2,
                    y
            );
        }
    }

    // =========================================================
    // GENERAL HELPERS
    // =========================================================

    private Integer parseInteger(String value) {

        try {
            return Integer.parseInt(value.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private void showError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Invalid input",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void showBackendMessage() {

        JOptionPane.showMessageDialog(
                this,
                "The interface is ready, but the backend has not been connected yet.",
                "Backend connection",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
