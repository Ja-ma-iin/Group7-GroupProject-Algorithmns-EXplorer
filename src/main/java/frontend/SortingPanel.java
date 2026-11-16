
package frontend;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Random;

public class SortingPanel extends JPanel {

    // BACKEND CONNECTION
    public interface Listener {
        void startSorting(int[] values, String algorithm, String pivotStrategy);
        void requestNextStep();
        void setAutoPlay(boolean enabled, int delayMillis);
        void compareAll(int[] values);
    }

    private Listener listener;

    // COLORS
    private final Color TEXT = UITheme.TEXT;
    private final Color MUTED = UITheme.MUTED;
    private final Color PRIMARY = UITheme.PRIMARY;
    private final Color BORDER = UITheme.BORDER;
    private final Color WHITE = UITheme.WHITE;
    private final Color LIGHT = new Color(248, 250, 253);

    // INPUTS
    private JTextField arrayInput;
    private JTextField randomSizeInput;
    private JComboBox<String> algorithmInput;
    private JComboBox<String> pivotInput;

    // VISUALISATION
    private ArrayVisualization visualisation;
    private JLabel stepLabel;
    private JLabel statusLabel;

    // PLAYBACK
    private JButton autoButton;
    private JSlider speedSlider;
    private boolean autoPlaying = false;

    // PERFORMANCE
    private JLabel comparisonsLabel;
    private JLabel movesLabel;
    private JLabel timeLabel;

    // COMPARISON TABLE
    private DefaultTableModel comparisonModel;

    public SortingPanel() {

        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);

        JPanel main = new JPanel(new BorderLayout(0, 15));
        main.setBackground(UITheme.BACKGROUND);
        main.setBorder(new EmptyBorder(22, 25, 20, 25));

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));

        tabs.addTab("Sort Visualiser", createVisualizerTab());
        tabs.addTab("Compare All", createComparisonTab());
        tabs.addTab("Algorithm Guide", createGuideTab());

        main.add(createHeader(), BorderLayout.NORTH);
        main.add(tabs, BorderLayout.CENTER);

        add(main, BorderLayout.CENTER);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    // =====================================================
    // HEADER
    // =====================================================

    private JPanel createHeader() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JPanel titles = new JPanel();
        titles.setOpaque(false);
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Sorting Lab");
        title.setFont(UITheme.TITLE_FONT);
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel(
                "Explore sorting algorithms through visualisation and performance analysis."
        );

        subtitle.setFont(UITheme.NORMAL_FONT);
        subtitle.setForeground(MUTED);

        titles.add(title);
        titles.add(Box.createVerticalStrut(5));
        titles.add(subtitle);

        JLabel module = new JLabel("MODULE C");
        module.setFont(new Font("Segoe UI", Font.BOLD, 11));
        module.setForeground(PRIMARY);
        module.setOpaque(true);
        module.setBackground(new Color(231, 239, 249));
        module.setBorder(new EmptyBorder(8, 12, 8, 12));

        panel.add(titles, BorderLayout.WEST);
        panel.add(module, BorderLayout.EAST);

        return panel;
    }

    // =====================================================
    // VISUALISER TAB
    // =====================================================

    private JPanel createVisualizerTab() {

        JPanel panel = createTabContainer();

        JPanel body = new JPanel(new BorderLayout(0, 12));
        body.setOpaque(false);

        body.add(createInputCard(), BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 12));
        center.setOpaque(false);

        center.add(createVisualizationCard(), BorderLayout.CENTER);
        center.add(createPlaybackCard(), BorderLayout.SOUTH);

        body.add(center, BorderLayout.CENTER);

        panel.add(body, BorderLayout.CENTER);

        return panel;
    }

    // =====================================================
    // INPUT CARD
    // =====================================================

    private JPanel createInputCard() {

        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 12));
        card.setBorder(new EmptyBorder(14, 17, 14, 17));

        JLabel title = new JLabel("Sorting Configuration");
        title.setFont(UITheme.HEADING_FONT);
        title.setForeground(TEXT);

        JPanel fields = new JPanel(new GridBagLayout());
        fields.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        arrayInput = createField("e.g. 45, 12, 78, 23, 9");

        algorithmInput = createCombo(new String[]{
                "Bubble Sort",
                "Selection Sort",
                "Insertion Sort",
                "Merge Sort",
                "Quick Sort"
        });

        pivotInput = createCombo(new String[]{
                "First Element Pivot",
                "Randomised Pivot"
        });

        addField(fields, gbc, 0, "Input Numbers", arrayInput);
        addField(fields, gbc, 1, "Algorithm", algorithmInput);
        addField(fields, gbc, 2, "Quick Sort Pivot", pivotInput);

        pivotInput.setEnabled(false);

        algorithmInput.addActionListener(e -> {
            boolean quickSort =
                    "Quick Sort".equals(algorithmInput.getSelectedItem());

            pivotInput.setEnabled(quickSort);
        });

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);

        randomSizeInput = createField("Array size");
        randomSizeInput.setText("10");
        randomSizeInput.setPreferredSize(new Dimension(100, 37));

        JButton randomButton = createButton(
                "Generate Random",
                new Color(65, 130, 95)
        );

        JButton loadButton = createButton(
                "Load Array",
                PRIMARY
        );

        randomButton.addActionListener(e -> generateRandomArray());
        loadButton.addActionListener(e -> loadArray());

        actions.add(randomSizeInput);
        actions.add(randomButton);
        actions.add(loadButton);

        card.add(title, BorderLayout.NORTH);
        card.add(fields, BorderLayout.CENTER);
        card.add(actions, BorderLayout.SOUTH);

        return card;
    }

    // =====================================================
    // VISUALISATION CARD
    // =====================================================

    private JPanel createVisualizationCard() {

        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 10));
        card.setBorder(new EmptyBorder(14, 17, 14, 17));

        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);

        JLabel title = new JLabel("Array Visualisation");
        title.setFont(UITheme.HEADING_FONT);
        title.setForeground(TEXT);

        JLabel legend = new JLabel(
                "Blue: Normal    Orange: Comparing    Green: Sorted"
        );

        legend.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        legend.setForeground(MUTED);

        heading.add(title, BorderLayout.WEST);
        heading.add(legend, BorderLayout.EAST);

        visualisation = new ArrayVisualization();

        JScrollPane scroll = new JScrollPane(visualisation);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER));
        scroll.getViewport().setBackground(LIGHT);

        // PERFORMANCE METRICS
        comparisonsLabel = new JLabel("0");
        movesLabel = new JLabel("0");
        timeLabel = new JLabel("Not measured");

        JPanel metrics = new JPanel(new GridLayout(1, 3, 10, 0));
        metrics.setOpaque(false);

        metrics.add(createMetricCard(
                "Comparisons", comparisonsLabel));

        metrics.add(createMetricCard(
                "Swaps / Moves", movesLabel));

        metrics.add(createMetricCard(
                "Execution Time", timeLabel));

        stepLabel = new JLabel("Step 0");
        stepLabel.setFont(UITheme.BUTTON_FONT);
        stepLabel.setForeground(PRIMARY);

        statusLabel = new JLabel("Load an array to begin.");
        statusLabel.setFont(UITheme.NORMAL_FONT);
        statusLabel.setForeground(MUTED);

        JPanel footer = new JPanel(new BorderLayout(0, 8));
        footer.setOpaque(false);

        JPanel stepStatus = new JPanel(new BorderLayout());
        stepStatus.setOpaque(false);

        stepStatus.add(stepLabel, BorderLayout.WEST);
        stepStatus.add(statusLabel, BorderLayout.CENTER);

        footer.add(metrics, BorderLayout.NORTH);
        footer.add(stepStatus, BorderLayout.SOUTH);

        card.add(heading, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        card.add(footer, BorderLayout.SOUTH);

        return card;
    }

    // =====================================================
    // PLAYBACK CONTROLS
    // =====================================================

    private JPanel createPlaybackCard() {

        JPanel card = createCard();
        card.setLayout(new BorderLayout(10, 8));
        card.setBorder(new EmptyBorder(11, 15, 11, 15));

        JPanel buttons = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 8, 0)
        );

        buttons.setOpaque(false);

        JButton startButton = createButton(
                "Start Sorting", PRIMARY);

        JButton stepButton = createButton(
                "Next Step", new Color(66, 107, 154));

        autoButton = createButton(
                "Auto Play", new Color(65, 130, 95));

        JButton resetButton = createButton(
                "Reset", new Color(180, 75, 75));

        startButton.addActionListener(e -> startSorting());
        stepButton.addActionListener(e -> nextStep());
        autoButton.addActionListener(e -> toggleAutoPlay());
        resetButton.addActionListener(e -> resetVisualisation());

        buttons.add(startButton);
        buttons.add(stepButton);
        buttons.add(autoButton);
        buttons.add(resetButton);

        JPanel speed = new JPanel(new BorderLayout(8, 0));
        speed.setOpaque(false);

        JLabel speedLabel = new JLabel("Animation Speed");
        speedLabel.setFont(UITheme.NORMAL_FONT);
        speedLabel.setForeground(TEXT);

        speedSlider = new JSlider(1, 10, 5);
        speedSlider.setOpaque(false);
        speedSlider.setMajorTickSpacing(3);
        speedSlider.setPaintTicks(true);

        speed.add(speedLabel, BorderLayout.WEST);
        speed.add(speedSlider, BorderLayout.CENTER);

        card.add(buttons, BorderLayout.NORTH);
        card.add(speed, BorderLayout.SOUTH);

        return card;
    }

    // =====================================================
    // COMPARE ALL TAB
    // =====================================================

    private JPanel createComparisonTab() {

        JPanel panel = createTabContainer();

        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 13));
        card.setBorder(new EmptyBorder(17, 17, 17, 17));

        JLabel title = new JLabel("Compare Sorting Algorithms");
        title.setFont(UITheme.HEADING_FONT);
        title.setForeground(TEXT);

        JLabel description = new JLabel(
                "Run all five algorithms using the same input and compare performance."
        );

        description.setFont(UITheme.NORMAL_FONT);
        description.setForeground(MUTED);

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

        heading.add(title);
        heading.add(Box.createVerticalStrut(5));
        heading.add(description);

        comparisonModel = new DefaultTableModel(
                new Object[]{
                        "Algorithm",
                        "Comparisons",
                        "Swaps / Moves",
                        "Execution Time"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(comparisonModel);
        styleTable(table);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER));

        JButton compareButton = createButton(
                "Compare All Algorithms",
                PRIMARY
        );

        compareButton.addActionListener(e -> compareAll());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setOpaque(false);
        bottom.add(compareButton);

        card.add(heading, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);

        panel.add(card, BorderLayout.CENTER);

        return panel;
    }

    // =====================================================
    // ALGORITHM GUIDE
    // =====================================================

    private JPanel createGuideTab() {

        JPanel panel = createTabContainer();

        String[] columns = {
                "Algorithm",
                "Best Case",
                "Average Case",
                "Worst Case",
                "Extra Space",
                "Stable?"
        };

        Object[][] data = {
                {"Bubble Sort", "O(n)", "O(n²)", "O(n²)", "O(1)", "Yes"},
                {"Selection Sort", "O(n²)", "O(n²)", "O(n²)", "O(1)", "No"},
                {"Insertion Sort", "O(n)", "O(n²)", "O(n²)", "O(1)", "Yes"},
                {"Merge Sort", "O(n log n)", "O(n log n)", "O(n log n)", "O(n)", "Yes"},
                {"Quick Sort", "O(n log n)", "O(n log n)", "O(n²)", "O(log n)*", "No"}
        };

        JTable table = new JTable(data, columns);
        styleTable(table);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER));

        JTextArea notes = new JTextArea(
                "Quick Sort:\n"
                + "The first-element pivot method has O(n²) worst-case time. "
                + "Randomised pivot selection has expected O(n log n) time, "
                + "but O(n²) worst-case time remains possible.\n\n"
                + "* Quick Sort uses O(log n) expected auxiliary stack space "
                + "and O(n) in the worst case.\n\n"
                + "Stable algorithms preserve the relative order of equal elements."
        );

        notes.setEditable(false);
        notes.setLineWrap(true);
        notes.setWrapStyleWord(true);
        notes.setFont(UITheme.NORMAL_FONT);
        notes.setForeground(MUTED);
        notes.setBackground(LIGHT);
        notes.setBorder(new EmptyBorder(12, 12, 12, 12));

        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 12));
        card.setBorder(new EmptyBorder(17, 17, 17, 17));

        JLabel title = new JLabel("Sorting Algorithm Reference");
        title.setFont(UITheme.HEADING_FONT);
        title.setForeground(TEXT);

        card.add(title, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        card.add(notes, BorderLayout.SOUTH);

        panel.add(card, BorderLayout.CENTER);

        return panel;
    }

    // =====================================================
    // INPUT HANDLING
    // =====================================================

    private int[] parseInput() {

        String input = arrayInput.getText().trim();

        if (input.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please enter some numbers."
            );
        }

        String[] parts = input.split("[,\\s]+");

        if (parts.length > 100) {
            throw new IllegalArgumentException(
                    "Maximum array size is 100."
            );
        }

        int[] values = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            try {
                values[i] = Integer.parseInt(parts[i]);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException(
                        "Invalid number: " + parts[i]
                );
            }
        }

        return values;
    }

    private void generateRandomArray() {

        try {
            int size = Integer.parseInt(
                    randomSizeInput.getText().trim()
            );

            if (size < 1 || size > 100) {
                showError("Array size must be between 1 and 100.");
                return;
            }

            Random random = new Random();
            StringBuilder values = new StringBuilder();

            for (int i = 0; i < size; i++) {
                if (i > 0) values.append(", ");
                values.append(random.nextInt(99) + 1);
            }

            arrayInput.setText(values.toString());
            loadArray();

        } catch (NumberFormatException ex) {
            showError("Enter a valid array size.");
        }
    }

    private void loadArray() {

        try {
            int[] values = parseInput();

            visualisation.setArray(values);
            resetMetrics();

            stepLabel.setText("Step 0");
            statusLabel.setText("Array loaded successfully.");

        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    // =====================================================
    // SORTING ACTIONS
    // =====================================================

    private void startSorting() {

        try {
            int[] values = parseInput();

            if (listener == null) {
                showBackendMessage();
                return;
            }

            stopAutoPlay();

            String algorithm =
                    (String) algorithmInput.getSelectedItem();

            String pivot =
                    (String) pivotInput.getSelectedItem();

            visualisation.setArray(values);
            resetMetrics();

            stepLabel.setText("Step 0");
            statusLabel.setText("Sorting started...");

            listener.startSorting(values, algorithm, pivot);

        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private void nextStep() {

        if (listener == null) {
            showBackendMessage();
            return;
        }

        listener.requestNextStep();
    }

    private void toggleAutoPlay() {

        if (listener == null) {
            showBackendMessage();
            return;
        }

        autoPlaying = !autoPlaying;

        int delay = 1100 - (speedSlider.getValue() * 100);

        listener.setAutoPlay(autoPlaying, delay);

        autoButton.setText(
                autoPlaying ? "Pause Auto Play" : "Auto Play"
        );

        statusLabel.setText(
                autoPlaying
                        ? "Automatic playback running..."
                        : "Playback paused."
        );
    }

    private void stopAutoPlay() {

        if (listener != null && autoPlaying) {
            listener.setAutoPlay(false, 0);
        }

        autoPlaying = false;

        if (autoButton != null) {
            autoButton.setText("Auto Play");
        }
    }

    private void resetVisualisation() {

        stopAutoPlay();

        try {
            int[] values = parseInput();

            visualisation.setArray(values);
            resetMetrics();

            stepLabel.setText("Step 0");
            statusLabel.setText("Visualisation reset.");

        } catch (IllegalArgumentException ex) {
            visualisation.setArray(new int[0]);
            resetMetrics();
            statusLabel.setText("Enter an array to begin.");
        }
    }

    private void compareAll() {

        try {
            int[] values = parseInput();

            if (listener == null) {
                showBackendMessage();
                return;
            }

            comparisonModel.setRowCount(0);
            listener.compareAll(values);

        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    // =====================================================
    // BACKEND CALLBACKS
    // =====================================================

    public void updateArray(
            int[] values,
            int highlightedA,
            int highlightedB,
            String description,
            int currentStep,
            int totalSteps
    ) {

        visualisation.setArray(values);
        visualisation.setHighlights(
                highlightedA, highlightedB
        );

        stepLabel.setText(
                "Step " + currentStep + " / " + totalSteps
        );

        statusLabel.setText(description);
    }

    public void updateMetrics(
            String comparisons,
            String swapsMoves,
            String executionTime
    ) {

        comparisonsLabel.setText(comparisons);
        movesLabel.setText(swapsMoves);
        timeLabel.setText(executionTime);
    }

    public void updateComparisonResults(String[][] results) {

        comparisonModel.setRowCount(0);

        for (String[] row : results) {
            comparisonModel.addRow(row);
        }
    }

    public void showSortingComplete(
            String message,
            String comparisons,
            String moves,
            String executionTime
    ) {

        statusLabel.setText(message);

        updateMetrics(
                comparisons,
                moves,
                executionTime
        );

        stopAutoPlay();
    }

    private void resetMetrics() {

        comparisonsLabel.setText("0");
        movesLabel.setText("0");
        timeLabel.setText("Not measured");
    }

    // =====================================================
    // ARRAY VISUALISATION
    // =====================================================

    private class ArrayVisualization extends JPanel {

        private int[] values = new int[0];

        private int highlightA = -1;
        private int highlightB = -1;

        private boolean sorted = false;

        ArrayVisualization() {
            setBackground(LIGHT);
            setPreferredSize(new Dimension(650, 260));
        }

        void setArray(int[] values) {

            this.values = values == null
                    ? new int[0]
                    : values.clone();

            highlightA = -1;
            highlightB = -1;
            sorted = false;

            int width = Math.max(
                    650,
                    this.values.length * 65
            );

            setPreferredSize(new Dimension(width, 260));

            revalidate();
            repaint();
        }

        void setHighlights(int a, int b) {

            highlightA = a;
            highlightB = b;

            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            if (values.length == 0) {

                g2.setColor(MUTED);
                g2.setFont(UITheme.NORMAL_FONT);

                String message = "Load an array to begin sorting.";

                FontMetrics fm = g2.getFontMetrics();

                g2.drawString(
                        message,
                        (getWidth() - fm.stringWidth(message)) / 2,
                        getHeight() / 2
                );

                g2.dispose();
                return;
            }

            int max = 1;

            for (int value : values) {
                max = Math.max(max, Math.abs(value));
            }

            int cellWidth = Math.min(
                    55,
                    Math.max(32, (getWidth() - 30) / values.length - 8)
            );

            int gap = 8;

            int totalWidth =
                    values.length * (cellWidth + gap) - gap;

            int x = Math.max(
                    15,
                    (getWidth() - totalWidth) / 2
            );

            int centerY = getHeight() / 2;

            for (int i = 0; i < values.length; i++) {

                int barHeight = Math.max(
                        15,
                        (int) (
                                Math.abs((double) values[i])
                                / max * 100
                        )
                );

                int y = centerY - barHeight / 2;

                Color cellColor = PRIMARY;

                if (sorted) {
                    cellColor = new Color(65, 150, 100);
                }

                if (i == highlightA || i == highlightB) {
                    cellColor = new Color(230, 155, 65);
                }

                g2.setColor(cellColor);

                g2.fillRoundRect(
                        x,
                        y,
                        cellWidth,
                        barHeight,
                        9,
                        9
                );

                g2.setColor(Color.WHITE);
                g2.setFont(new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                ));

                String value = String.valueOf(values[i]);
                FontMetrics fm = g2.getFontMetrics();

                g2.drawString(
                        value,
                        x + (cellWidth - fm.stringWidth(value)) / 2,
                        y + barHeight / 2 + 4
                );

                g2.setColor(MUTED);
                g2.setFont(new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        10
                ));

                String index = String.valueOf(i);
                fm = g2.getFontMetrics();

                g2.drawString(
                        index,
                        x + (cellWidth - fm.stringWidth(index)) / 2,
                        centerY + 75
                );

                x += cellWidth + gap;
            }

            g2.dispose();
        }
    }

    // =====================================================
    // UI HELPERS
    // =====================================================

    private JPanel createTabContainer() {

        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(UITheme.BACKGROUND);
        panel.setBorder(new EmptyBorder(16, 12, 10, 12));

        return panel;
    }

    private JPanel createCard() {

        JPanel card = new JPanel();
        card.setBackground(WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(10, 10, 10, 10)
        ));

        return card;
    }

    private JPanel createMetricCard(
            String title,
            JLabel value
    ) {

        JPanel card = createCard();

        card.setLayout(new BoxLayout(
                card,
                BoxLayout.Y_AXIS
        ));

        card.setBorder(new EmptyBorder(8, 10, 8, 10));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font(
                "Segoe UI",
                Font.PLAIN,
                11
        ));

        heading.setForeground(MUTED);

        value.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                15
        ));

        value.setForeground(PRIMARY);

        card.add(heading);
        card.add(Box.createVerticalStrut(4));
        card.add(value);

        return card;
    }

    private JTextField createField(String placeholder) {

        JTextField field = new JTextField();

        field.setFont(UITheme.NORMAL_FONT);
        field.setForeground(TEXT);
        field.setBackground(WHITE);
        field.setToolTipText(placeholder);

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(8, 10, 8, 10)
        ));

        field.setPreferredSize(new Dimension(160, 37));

        return field;
    }

    private JComboBox<String> createCombo(String[] options) {

        JComboBox<String> combo = new JComboBox<>(options);

        combo.setFont(UITheme.NORMAL_FONT);
        combo.setForeground(TEXT);
        combo.setBackground(WHITE);
        combo.setPreferredSize(new Dimension(175, 37));

        return combo;
    }

    private void addField(
            JPanel panel,
            GridBagConstraints gbc,
            int column,
            String labelText,
            JComponent field
    ) {

        gbc.gridx = column;
        gbc.gridy = 0;
        gbc.weightx = 1;

        JPanel group = new JPanel();

        group.setOpaque(false);
        group.setLayout(new BoxLayout(
                group,
                BoxLayout.Y_AXIS
        ));

        JLabel label = new JLabel(labelText);

        label.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                11
        ));

        label.setForeground(MUTED);

        group.add(label);
        group.add(Box.createVerticalStrut(6));
        group.add(field);

        panel.add(group, gbc);
    }

    private JButton createButton(
            String text,
            Color color
    ) {

        JButton button = new JButton(text);

        button.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                12
        ));

        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.setBorder(new EmptyBorder(
                10, 15, 10, 15
        ));

        return button;
    }

    private void styleTable(JTable table) {

        table.setFont(UITheme.NORMAL_FONT);
        table.setRowHeight(38);
        table.setForeground(TEXT);
        table.setGridColor(BORDER);

        table.getTableHeader().setFont(UITheme.BUTTON_FONT);

        table.getTableHeader().setBackground(
                new Color(235, 241, 248)
        );

        table.setSelectionBackground(
                new Color(225, 235, 247)
        );
    }

    private void showError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Invalid Input",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void showBackendMessage() {

        JOptionPane.showMessageDialog(
                this,
                "The sorting backend has not been connected yet.",
                "Backend Not Connected",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
