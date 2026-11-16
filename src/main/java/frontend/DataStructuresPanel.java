
package frontend;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataStructuresPanel extends JPanel {

    public interface Listener {
        void performOperation(
                String structure,
                String operation,
                String value,
                String target,
                int capacity
        );

        void checkBrackets(String expression);
    }

    private Listener listener;

    private final Map<String, StructureVisual> visualPanels = new HashMap<>();
    private final Map<String, JLabel> statusLabels = new HashMap<>();
    private final Map<String, JLabel> detailLabels = new HashMap<>();
    private final Map<String, String> savedVisualisations = new HashMap<>();

    private JTextArea bracketSteps;
    private JLabel bracketStatus;

    private final Color WHITE = UITheme.WHITE;
    private final Color TEXT = UITheme.TEXT;
    private final Color MUTED = UITheme.MUTED;
    private final Color PRIMARY = UITheme.PRIMARY;
    private final Color BORDER = UITheme.BORDER;
    private final Color LIGHT = new Color(248, 250, 253);
    private final Color SUCCESS = UITheme.SUCCESS;
    private final Color DANGER = UITheme.DANGER;

    private final Color NODE_COLOR = new Color(231, 239, 249);
    private final Color NODE_BORDER = new Color(113, 145, 185);
    private final Color EMPTY_COLOR = new Color(244, 246, 249);

    public DataStructuresPanel() {
        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabs.setBackground(WHITE);
        tabs.setForeground(TEXT);

        tabs.addTab("  Stack  ", createStackTab());
        tabs.addTab("  Queues  ", createQueuesTab());
        tabs.addTab("  Linked Lists  ", createLinkedListsTab());
        tabs.addTab("  Bracket Checker  ", createBracketTab());

        tabs.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        JPanel main = new JPanel(new BorderLayout(0, 15));
        main.setBackground(UITheme.BACKGROUND);
        main.setBorder(new EmptyBorder(22, 25, 20, 25));

        main.add(createHeader(), BorderLayout.NORTH);
        main.add(tabs, BorderLayout.CENTER);

        add(main, BorderLayout.CENTER);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    // =========================================================
    // HEADER
    // =========================================================

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel titles = new JPanel();
        titles.setOpaque(false);
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Data Structures Lab");
        title.setFont(UITheme.TITLE_FONT);
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel(
                "Explore and interact with fundamental data structures."
        );
        subtitle.setFont(UITheme.NORMAL_FONT);
        subtitle.setForeground(MUTED);

        titles.add(title);
        titles.add(Box.createVerticalStrut(5));
        titles.add(subtitle);

        JLabel moduleTag = new JLabel("MODULE B");
        moduleTag.setFont(new Font("Segoe UI", Font.BOLD, 11));
        moduleTag.setForeground(PRIMARY);
        moduleTag.setOpaque(true);
        moduleTag.setBackground(new Color(231, 239, 249));
        moduleTag.setBorder(new EmptyBorder(8, 12, 8, 12));

        header.add(titles, BorderLayout.WEST);
        header.add(moduleTag, BorderLayout.EAST);

        return header;
    }

    // =========================================================
    // STACK
    // =========================================================

    private JPanel createStackTab() {
        return createStructureTab(
                "STACK",
                "Array-Based Stack",
                "Last In, First Out (LIFO). The last item added is the first removed.",
                new String[]{
                        "Push", "Pop", "Peek", "Size",
                        "Is Empty", "Is Full", "Display"
                },
                true
        );
    }

    // =========================================================
    // QUEUES
    // =========================================================

    private JPanel createQueuesTab() {
        JPanel panel = createTabContainer();

        JComboBox<String> selector = createCombo(
                new String[]{"Linear Queue", "Circular Queue"}
        );

        JPanel selectorCard = createSelectorCard(
                "Queue Implementation",
                "Choose the queue type you want to explore.",
                selector
        );

        JPanel dynamicPanel = new JPanel(new BorderLayout());
        dynamicPanel.setOpaque(false);

        Runnable refresh = () -> {
            dynamicPanel.removeAll();

            if ("Circular Queue".equals(selector.getSelectedItem())) {
                dynamicPanel.add(
                        createStructureTab(
                                "CIRCULAR_QUEUE",
                                "Circular Queue",
                                "FIFO queue that reuses empty spaces by wrapping around the array.",
                                new String[]{
                                        "Enqueue", "Dequeue", "Front", "Size",
                                        "Is Empty", "Is Full", "Display"
                                },
                                true
                        ),
                        BorderLayout.CENTER
                );
            } else {
                dynamicPanel.add(
                        createStructureTab(
                                "LINEAR_QUEUE",
                                "Linear Queue",
                                "First In, First Out (FIFO). Elements leave from the front.",
                                new String[]{
                                        "Enqueue", "Dequeue", "Front", "Size",
                                        "Is Empty", "Is Full", "Display"
                                },
                                true
                        ),
                        BorderLayout.CENTER
                );
            }

            dynamicPanel.revalidate();
            dynamicPanel.repaint();
        };

        selector.addActionListener(e -> refresh.run());
        refresh.run();

        panel.add(selectorCard, BorderLayout.NORTH);
        panel.add(dynamicPanel, BorderLayout.CENTER);

        return panel;
    }

    // =========================================================
    // LINKED LISTS
    // =========================================================

    private JPanel createLinkedListsTab() {
        JPanel panel = createTabContainer();

        JComboBox<String> selector = createCombo(
                new String[]{"Singly Linked List", "Doubly Linked List"}
        );

        JPanel selectorCard = createSelectorCard(
                "Linked List Implementation",
                "Choose how the nodes are connected.",
                selector
        );

        JPanel dynamicPanel = new JPanel(new BorderLayout());
        dynamicPanel.setOpaque(false);

        Runnable refresh = () -> {
            dynamicPanel.removeAll();

            if ("Doubly Linked List".equals(selector.getSelectedItem())) {
                dynamicPanel.add(
                        createStructureTab(
                                "DOUBLY_LINKED_LIST",
                                "Doubly Linked List",
                                "Each node connects to both the previous and next node.",
                                new String[]{
                                        "Insert at Beginning",
                                        "Insert at End",
                                        "Insert Before",
                                        "Insert After",
                                        "Delete at Beginning",
                                        "Delete at End",
                                        "Delete Before",
                                        "Delete After",
                                        "Delete Element",
                                        "Search",
                                        "Size",
                                        "Display Forward",
                                        "Display Backward"
                                },
                                false
                        ),
                        BorderLayout.CENTER
                );
            } else {
                dynamicPanel.add(
                        createStructureTab(
                                "SINGLY_LINKED_LIST",
                                "Singly Linked List",
                                "Each node stores a value and a reference to the next node.",
                                new String[]{
                                        "Insert at Beginning",
                                        "Insert at End",
                                        "Insert Before",
                                        "Insert After",
                                        "Delete at Beginning",
                                        "Delete at End",
                                        "Delete Before",
                                        "Delete After",
                                        "Delete Element",
                                        "Search",
                                        "Size",
                                        "Display Forward"
                                },
                                false
                        ),
                        BorderLayout.CENTER
                );
            }

            dynamicPanel.revalidate();
            dynamicPanel.repaint();
        };

        selector.addActionListener(e -> refresh.run());
        refresh.run();

        panel.add(selectorCard, BorderLayout.NORTH);
        panel.add(dynamicPanel, BorderLayout.CENTER);

        return panel;
    }

    // =========================================================
    // REUSABLE STRUCTURE LAYOUT
    // =========================================================

    private JPanel createStructureTab(
            String structure,
            String heading,
            String description,
            String[] operations,
            boolean showCapacity
    ) {
        JPanel panel = createTabContainer();

        JPanel body = new JPanel(new BorderLayout(0, 14));
        body.setOpaque(false);

        JPanel headingPanel = new JPanel();
        headingPanel.setOpaque(false);
        headingPanel.setLayout(new BoxLayout(headingPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(heading);
        title.setFont(UITheme.HEADING_FONT);
        title.setForeground(TEXT);

        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(UITheme.NORMAL_FONT);
        descriptionLabel.setForeground(MUTED);

        headingPanel.add(title);
        headingPanel.add(Box.createVerticalStrut(5));
        headingPanel.add(descriptionLabel);

        JComboBox<String> operationInput = createCombo(operations);
        JTextField valueInput = createField("Enter value");
        JTextField targetInput = createField("Reference element");
        JTextField capacityInput = createField("10");
        capacityInput.setText("10");

        JPanel controlsCard = createCard();
        controlsCard.setLayout(new BorderLayout(0, 12));
        controlsCard.setBorder(new EmptyBorder(15, 17, 15, 17));

        JLabel controlsTitle = new JLabel("Operation Controls");
        controlsTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        controlsTitle.setForeground(TEXT);

        JPanel fields = new JPanel(new GridBagLayout());
        fields.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        addControl(fields, gbc, 0, "Operation", operationInput);
        addControl(fields, gbc, 1, "Value / Element", valueInput);
        addControl(fields, gbc, 2, "Reference Element", targetInput);

        if (showCapacity) {
            addControl(fields, gbc, 3, "Capacity", capacityInput);
        }

        JButton executeButton = createButton("Execute Operation", PRIMARY);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(executeButton);

        controlsCard.add(controlsTitle, BorderLayout.NORTH);
        controlsCard.add(fields, BorderLayout.CENTER);
        controlsCard.add(buttonPanel, BorderLayout.SOUTH);

        // Visualisation card
        JPanel visualCard = createCard();
        visualCard.setLayout(new BorderLayout(0, 10));
        visualCard.setBorder(new EmptyBorder(16, 17, 14, 17));

        JPanel visualHeader = new JPanel(new BorderLayout());
        visualHeader.setOpaque(false);

        JLabel visualTitle = new JLabel("Structure Visualisation");
        visualTitle.setFont(UITheme.HEADING_FONT);
        visualTitle.setForeground(TEXT);

        JLabel visualTag = new JLabel("LIVE DIAGRAM");
        visualTag.setFont(new Font("Segoe UI", Font.BOLD, 10));
        visualTag.setForeground(SUCCESS);

        visualHeader.add(visualTitle, BorderLayout.WEST);
        visualHeader.add(visualTag, BorderLayout.EAST);

        StructureVisual visual = new StructureVisual(structure);
        visual.setVisualisation(savedVisualisations.getOrDefault(
                structure, ""
        ));

        JScrollPane visualScroll = new JScrollPane(visual);
        visualScroll.setBorder(BorderFactory.createLineBorder(BORDER));
        visualScroll.getViewport().setBackground(LIGHT);
        visualScroll.getVerticalScrollBar().setUnitIncrement(18);
        visualScroll.getHorizontalScrollBar().setUnitIncrement(18);

        JLabel status = new JLabel("  Ready for your first operation");
        status.setFont(UITheme.NORMAL_FONT);
        status.setForeground(MUTED);

        JLabel details = new JLabel(" ");
        details.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        details.setForeground(MUTED);

        JPanel feedback = new JPanel();
        feedback.setOpaque(false);
        feedback.setLayout(new BoxLayout(feedback, BoxLayout.Y_AXIS));
        feedback.add(status);
        feedback.add(Box.createVerticalStrut(3));
        feedback.add(details);

        visualCard.add(visualHeader, BorderLayout.NORTH);
        visualCard.add(visualScroll, BorderLayout.CENTER);
        visualCard.add(feedback, BorderLayout.SOUTH);

        visualPanels.put(structure, visual);
        statusLabels.put(structure, status);
        detailLabels.put(structure, details);

        executeButton.addActionListener(e -> {
            String operation = (String) operationInput.getSelectedItem();
            String value = valueInput.getText().trim();
            String target = targetInput.getText().trim();

            int capacity = 10;

            if (showCapacity) {
                try {
                    capacity = Integer.parseInt(capacityInput.getText().trim());

                    if (capacity < 1) {
                        showError("Capacity must be at least 1.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    showError("Enter a valid whole number for capacity.");
                    return;
                }
            }

            if (needsValue(operation) && value.isEmpty()) {
                showError("Please enter a value.");
                return;
            }

            if (needsTarget(operation) && target.isEmpty()) {
                showError("Please enter the reference element.");
                return;
            }

            if (listener == null) {
                status.setText("Backend has not been connected yet.");
                return;
            }

            listener.performOperation(
                    structure,
                    operation,
                    value,
                    target,
                    capacity
            );
        });

        JPanel top = new JPanel(new BorderLayout(0, 13));
        top.setOpaque(false);
        top.add(headingPanel, BorderLayout.NORTH);
        top.add(controlsCard, BorderLayout.CENTER);

        body.add(top, BorderLayout.NORTH);
        body.add(visualCard, BorderLayout.CENTER);

        panel.add(body, BorderLayout.CENTER);

        return panel;
    }

    // =========================================================
    // BRACKET CHECKER
    // =========================================================

    private JPanel createBracketTab() {
        JPanel panel = createTabContainer();

        JPanel body = new JPanel(new BorderLayout(0, 15));
        body.setOpaque(false);

        JPanel intro = new JPanel();
        intro.setOpaque(false);
        intro.setLayout(new BoxLayout(intro, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Balanced Bracket Checker");
        title.setFont(UITheme.HEADING_FONT);
        title.setForeground(TEXT);

        JLabel description = new JLabel(
                "Use a stack to determine whether an expression has correctly matched brackets."
        );
        description.setFont(UITheme.NORMAL_FONT);
        description.setForeground(MUTED);

        intro.add(title);
        intro.add(Box.createVerticalStrut(5));
        intro.add(description);

        JPanel inputCard = createCard();
        inputCard.setLayout(new BorderLayout(12, 10));
        inputCard.setBorder(new EmptyBorder(16, 17, 16, 17));

        JLabel inputTitle = new JLabel("Enter Expression");
        inputTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        inputTitle.setForeground(TEXT);

        JTextField expressionInput = createField("{ ( [ ] ) }");
        expressionInput.setText("{ ( [ ] ) }");

        JButton checkButton = createButton("Check Expression", PRIMARY);

        JPanel inputRow = new JPanel(new BorderLayout(10, 0));
        inputRow.setOpaque(false);
        inputRow.add(expressionInput, BorderLayout.CENTER);
        inputRow.add(checkButton, BorderLayout.EAST);

        inputCard.add(inputTitle, BorderLayout.NORTH);
        inputCard.add(inputRow, BorderLayout.CENTER);

        JPanel stepsCard = createCard();
        stepsCard.setLayout(new BorderLayout(0, 10));
        stepsCard.setBorder(new EmptyBorder(16, 17, 15, 17));

        JLabel stepsTitle = new JLabel("Stack Execution Steps");
        stepsTitle.setFont(UITheme.HEADING_FONT);
        stepsTitle.setForeground(TEXT);

        bracketSteps = new JTextArea(
                "The step-by-step stack operations will appear here."
        );
        bracketSteps.setEditable(false);
        bracketSteps.setFont(new Font("Consolas", Font.PLAIN, 14));
        bracketSteps.setForeground(TEXT);
        bracketSteps.setBackground(LIGHT);
        bracketSteps.setMargin(new Insets(15, 15, 15, 15));

        JScrollPane stepsScroll = new JScrollPane(bracketSteps);
        stepsScroll.setBorder(BorderFactory.createLineBorder(BORDER));

        bracketStatus = new JLabel("  Waiting for an expression...");
        bracketStatus.setFont(UITheme.NORMAL_FONT);
        bracketStatus.setForeground(MUTED);

        stepsCard.add(stepsTitle, BorderLayout.NORTH);
        stepsCard.add(stepsScroll, BorderLayout.CENTER);
        stepsCard.add(bracketStatus, BorderLayout.SOUTH);

        checkButton.addActionListener(e -> {
            String expression = expressionInput.getText().trim();

            if (expression.isEmpty()) {
                showError("Please enter an expression.");
                return;
            }

            if (listener == null) {
                bracketStatus.setText("Backend has not been connected yet.");
                return;
            }

            listener.checkBrackets(expression);
        });

        body.add(intro, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 13));
        center.setOpaque(false);
        center.add(inputCard, BorderLayout.NORTH);
        center.add(stepsCard, BorderLayout.CENTER);

        body.add(center, BorderLayout.CENTER);
        panel.add(body, BorderLayout.CENTER);

        return panel;
    }

    // =========================================================
    // BACKEND CALLBACKS
    // =========================================================

    public void updateStructureView(
            String structure,
            String visualisation,
            String message,
            String details
    ) {
        Runnable update = () -> {
            String raw = visualisation == null ? "" : visualisation;
            savedVisualisations.put(structure, raw);

            StructureVisual visual = visualPanels.get(structure);
            JLabel status = statusLabels.get(structure);
            JLabel detail = detailLabels.get(structure);

            if (visual != null) {
                visual.setVisualisation(raw);
            }

            if (status != null) {
                status.setText("  " + (message == null ? "" : message));
            }

            if (detail != null) {
                detail.setText(details == null ? " " : details);
            }
        };

        if (SwingUtilities.isEventDispatchThread()) {
            update.run();
        } else {
            SwingUtilities.invokeLater(update);
        }
    }

    public void updateBracketResult(
            List<String> steps,
            boolean balanced,
            String message
    ) {
        Runnable update = () -> {
            if (bracketSteps != null) {
                StringBuilder result = new StringBuilder();

                if (steps != null) {
                    for (String step : steps) {
                        result.append(step).append("\n");
                    }
                }

                bracketSteps.setText(result.toString());
                bracketSteps.setCaretPosition(0);
            }

            if (bracketStatus != null) {
                bracketStatus.setText("  " + message);
                bracketStatus.setForeground(
                        balanced ? SUCCESS : DANGER
                );
            }
        };

        if (SwingUtilities.isEventDispatchThread()) {
            update.run();
        } else {
            SwingUtilities.invokeLater(update);
        }
    }

    // =========================================================
    // CUSTOM STRUCTURE DRAWING COMPONENT
    // =========================================================

    private class StructureVisual extends JPanel {

        private final String structure;

        private final List<String> values = new ArrayList<>();
        private final List<Boolean> occupied = new ArrayList<>();

        private int capacity;
        private int front = -1;
        private int rear = -1;
        private int size;

        private boolean hasFront;
        private boolean hasRear;
        private boolean isEmpty = true;
        private boolean isFull;
        private boolean backwards;
        private boolean hasData;

        StructureVisual(String structure) {
            this.structure = structure;
            setBackground(LIGHT);
            setOpaque(true);
        }

        void setVisualisation(String raw) {
            values.clear();
            occupied.clear();

            capacity = 0;
            front = -1;
            rear = -1;
            size = 0;
            hasFront = false;
            hasRear = false;
            isEmpty = true;
            isFull = false;
            backwards = raw != null && raw.contains("NULL <-");
            hasData = raw != null && !raw.trim().isEmpty();

            if (raw != null && !raw.isEmpty()) {
                parseMetadata(raw);

                if (structure.equals("STACK")) {
                    parseStack(raw);
                } else if (structure.contains("QUEUE")) {
                    parseQueue(raw);
                } else {
                    parseLinkedList(raw);
                }
            }

            if (capacity == 0) {
                capacity = Math.max(values.size(), 1);
            }

            if (structure.equals("STACK") || structure.contains("QUEUE")) {
                while (values.size() < capacity) {
                    values.add("");
                    occupied.add(false);
                }
            }

            updatePreferredSize();
            revalidate();
            repaint();
        }

        private void parseMetadata(String raw) {
            capacity = readNumber(raw, "Capacity");
            size = readNumber(raw, "Size");

            front = readNumber(raw, "Front Index");
            rear = readNumber(raw, "Rear Index");

            hasFront = raw.contains("FRONT") || front >= 0;
            hasRear = raw.contains("REAR") || rear >= 0;

            isFull = raw.matches("(?is).*Full:\\s*true.*");
            isEmpty = raw.matches("(?is).*Empty:\\s*true.*");
        }

        private int readNumber(String raw, String label) {
            Matcher matcher = Pattern.compile(
                    "(?i)" + Pattern.quote(label) + ":\\s*(-?\\d+)"
            ).matcher(raw);

            if (matcher.find()) {
                try {
                    return Integer.parseInt(matcher.group(1));
                } catch (NumberFormatException ignored) {
                    return -1;
                }
            }

            return -1;
        }

        private void parseStack(String raw) {
            Matcher matcher = Pattern.compile(
                    "\\|\\s*(-?\\d+)\\s*\\|"
            ).matcher(raw);

            while (matcher.find()) {
                values.add(matcher.group(1));
                occupied.add(true);
            }
        }

        private void parseQueue(String raw) {
            String[] lines = raw.split("\\R");

            for (String line : lines) {
                String trimmed = line.trim();

                if (!trimmed.startsWith("[")) {
                    continue;
                }

                Matcher matcher = Pattern.compile(
                        "^\\[\\s*(-?\\d*)\\s*\\]"
                ).matcher(trimmed);

                if (matcher.find()) {
                    String value = matcher.group(1).trim();
                    values.add(value);
                    occupied.add(!value.isEmpty());
                }
            }

            if (front < 0 && hasFront) {
                front = findMarkerIndex(raw, "FRONT");
            }

            if (rear < 0 && hasRear) {
                rear = findMarkerIndex(raw, "REAR");
            }
        }

        private int findMarkerIndex(String raw, String marker) {
            String[] lines = raw.split("\\R");
            int index = 0;

            for (String line : lines) {
                if (line.trim().startsWith("[")) {
                    if (line.contains(marker)) {
                        return index;
                    }
                    index++;
                }
            }

            return -1;
        }

        private void parseLinkedList(String raw) {
            Matcher matcher = Pattern.compile(
                    "\\[\\s*(-?\\d+)\\s*\\]"
            ).matcher(raw);

            while (matcher.find()) {
                values.add(matcher.group(1));
                occupied.add(true);
            }
        }

        private void updatePreferredSize() {
            if (structure.equals("STACK")) {
                int height = Math.max(300, (capacity + 1) * 62 + 100);
                setPreferredSize(new Dimension(650, height));
            } else if (structure.equals("CIRCULAR_QUEUE")) {
                if (capacity <= 12) {
                    setPreferredSize(new Dimension(650, 510));
                } else {
                    setPreferredSize(new Dimension(
                            Math.max(700, capacity * 76 + 100), 280
                    ));
                }
            } else if (structure.contains("QUEUE")) {
                setPreferredSize(new Dimension(
                        Math.max(650, capacity * 82 + 100), 300
                ));
            } else {
                setPreferredSize(new Dimension(
                        Math.max(650, values.size() * 145 + 120), 300
                ));
            }
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);

            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            if (!hasData) {
                drawEmptyMessage(g);
            } else if (structure.equals("STACK")) {
                drawStack(g);
            } else if (structure.equals("LINEAR_QUEUE")) {
                drawLinearQueue(g);
            } else if (structure.equals("CIRCULAR_QUEUE")) {
                drawCircularQueue(g);
            } else if (structure.equals("SINGLY_LINKED_LIST")) {
                drawLinkedList(g, false);
            } else if (structure.equals("DOUBLY_LINKED_LIST")) {
                drawLinkedList(g, true);
            }

            g.dispose();
        }

        private void drawEmptyMessage(Graphics2D g) {
            g.setColor(MUTED);
            g.setFont(new Font("Segoe UI", Font.PLAIN, 15));

            String message = "Your structure will appear here.";
            FontMetrics fm = g.getFontMetrics();

            g.drawString(
                    message,
                    Math.max(20, (getWidth() - fm.stringWidth(message)) / 2),
                    getHeight() / 2
            );

            g.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            String hint = "Execute an operation to see the visual representation.";
            fm = g.getFontMetrics();

            g.drawString(
                    hint,
                    Math.max(20, (getWidth() - fm.stringWidth(hint)) / 2),
                    getHeight() / 2 + 28
            );
        }

        // -----------------------------------------------------
        // STACK DRAWING
        // -----------------------------------------------------

        private void drawStack(Graphics2D g) {
            int cellWidth = 190;
            int cellHeight = 43;
            int x = Math.max(80, (getWidth() - cellWidth) / 2);
            int y = 65;

            drawHeading(g, "STACK ARRAY");

            g.setFont(new Font("Segoe UI", Font.BOLD, 12));

            int total = Math.max(capacity, values.size());

            for (int i = 0; i < total; i++) {
                int cellY = y + i * 53;
                boolean filled = i < values.size()
                        && i < occupied.size()
                        && occupied.get(i);

                drawCell(
                        g, x, cellY, cellWidth, cellHeight,
                        filled ? values.get(i) : "",
                        filled
                );

                g.setColor(MUTED);
                g.drawString("Index " + i, x + cellWidth + 15, cellY + 26);

                if (i == 0 && filled) {
                    drawArrow(g, x - 45, cellY + 21, x - 5, cellY + 21, PRIMARY);
                    g.setColor(PRIMARY);
                    g.drawString("TOP", x - 48, cellY - 8);
                }
            }

            if (size == 0 || isEmpty) {
                g.setColor(MUTED);
                g.drawString("TOP: -1 (empty)", x - 100, y + total * 53 + 10);
            }

            drawLegend(g, "Filled slot", "Empty slot");
        }

        // -----------------------------------------------------
        // LINEAR QUEUE DRAWING
        // -----------------------------------------------------

        private void drawLinearQueue(Graphics2D g) {
            drawHeading(g, "LINEAR QUEUE ARRAY");

            int count = Math.max(capacity, values.size());
            int cellWidth = 65;
            int cellHeight = 58;
            int gap = 10;
            int totalWidth = count * (cellWidth + gap) - gap;
            int x = Math.max(35, (getWidth() - totalWidth) / 2);
            int y = 115;

            for (int i = 0; i < count; i++) {
                int cellX = x + i * (cellWidth + gap);
                boolean filled = i < values.size()
                        && i < occupied.size()
                        && occupied.get(i);

                drawCell(
                        g, cellX, y, cellWidth, cellHeight,
                        filled ? values.get(i) : "",
                        filled
                );

                g.setColor(MUTED);
                g.drawString(String.valueOf(i), cellX + 26, y + 82);

                if (i == front && hasFront) {
                    drawArrow(g, cellX + cellWidth / 2, y - 35,
                            cellX + cellWidth / 2, y - 5, SUCCESS);
                    g.setColor(SUCCESS);
                    g.drawString("FRONT", cellX + 8, y - 43);
                }

                if (i == rear && hasRear) {
                    drawArrow(g, cellX + cellWidth / 2, y + cellHeight + 5,
                            cellX + cellWidth / 2, y + cellHeight + 32, PRIMARY);
                    g.setColor(PRIMARY);
                    g.drawString("REAR", cellX + 13, y + cellHeight + 48);
                }
            }

            drawLegend(g, "Occupied", "Available");
        }

        // -----------------------------------------------------
        // CIRCULAR QUEUE DRAWING
        // -----------------------------------------------------

        private void drawCircularQueue(Graphics2D g) {
            if (capacity > 12) {
                drawLinearQueue(g);
                g.setColor(MUTED);
                g.drawString(
                        "Large circular queue shown as indexed slots for readability.",
                        25, getHeight() - 22
                );
                return;
            }

            drawHeading(g, "CIRCULAR QUEUE");

            int count = Math.max(capacity, 1);
            int centerX = getWidth() / 2;
            int centerY = 265;
            int radius = 145;
            int cellWidth = 62;
            int cellHeight = 48;

            for (int i = 0; i < count; i++) {
                double angle = -Math.PI / 2 + (2 * Math.PI * i / count);

                int cellX = (int) (centerX + radius * Math.cos(angle)
                        - cellWidth / 2);
                int cellY = (int) (centerY + radius * Math.sin(angle)
                        - cellHeight / 2);

                boolean filled = i < values.size()
                        && i < occupied.size()
                        && occupied.get(i);

                drawCell(
                        g, cellX, cellY, cellWidth, cellHeight,
                        filled ? values.get(i) : "",
                        filled
                );

                g.setColor(MUTED);
                g.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                g.drawString(
                        String.valueOf(i),
                        cellX + cellWidth / 2 - 3,
                        cellY + cellHeight + 15
                );

                if (i == front && hasFront) {
                    drawArrow(g,
                            cellX + cellWidth / 2,
                            cellY - 28,
                            cellX + cellWidth / 2,
                            cellY - 5,
                            SUCCESS);
                    g.setColor(SUCCESS);
                    g.setFont(new Font("Segoe UI", Font.BOLD, 11));
                    g.drawString("FRONT", cellX - 2, cellY - 32);
                }

                if (i == rear && hasRear) {
                    drawArrow(g,
                            cellX + cellWidth / 2,
                            cellY + cellHeight + 5,
                            cellX + cellWidth / 2,
                            cellY + cellHeight + 27,
                            PRIMARY);
                    g.setColor(PRIMARY);
                    g.setFont(new Font("Segoe UI", Font.BOLD, 11));
                    g.drawString("REAR", cellX + 5, cellY + cellHeight + 42);
                }
            }

            g.setColor(MUTED);
            g.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            String info = "Front index: " + front + "     Rear index: " + rear;
            FontMetrics fm = g.getFontMetrics();
            g.drawString(
                    info,
                    centerX - fm.stringWidth(info) / 2,
                    centerY + 8
            );

            drawLegend(g, "Occupied", "Available");
        }

        // -----------------------------------------------------
        // LINKED LIST DRAWING
        // -----------------------------------------------------

        private void drawLinkedList(Graphics2D g, boolean doubly) {
            drawHeading(
                    g,
                    doubly ? "DOUBLY LINKED LIST" : "SINGLY LINKED LIST"
            );

            int count = values.size();
            int nodeWidth = doubly ? 105 : 85;
            int nodeHeight = 58;
            int gap = 48;
            int totalWidth = Math.max(
                    1, count * nodeWidth + Math.max(0, count - 1) * gap
            );

            int x = Math.max(65, (getWidth() - totalWidth) / 2);
            int y = 125;

            if (count == 0) {
                g.setColor(MUTED);
                g.setFont(new Font("Segoe UI", Font.BOLD, 14));
                g.drawString(
                        doubly ? "NULL  <->  NULL" : "HEAD  ->  NULL",
                        Math.max(20, getWidth() / 2 - 70),
                        y + 25
                );
                return;
            }

            g.setColor(PRIMARY);
            g.setFont(new Font("Segoe UI", Font.BOLD, 12));
            g.drawString("HEAD", x - 5, y - 22);
            drawArrow(g, x + 25, y - 17, x + 25, y - 5, PRIMARY);

            for (int i = 0; i < count; i++) {
                int nodeX = x + i * (nodeWidth + gap);

                if (i > 0) {
                    int previousX = nodeX - gap;

                    if (doubly) {
                        drawArrow(
                                g,
                                previousX + nodeWidth + 2,
                                y + 21,
                                nodeX - 3,
                                y + 21,
                                PRIMARY
                        );
                        drawArrow(
                                g,
                                nodeX - 3,
                                y + 39,
                                previousX + nodeWidth + 2,
                                y + 39,
                                SUCCESS
                        );
                    } else {
                        drawArrow(
                                g,
                                previousX + nodeWidth + 2,
                                y + nodeHeight / 2,
                                nodeX - 3,
                                y + nodeHeight / 2,
                                PRIMARY
                        );
                    }
                }

                g.setColor(NODE_BORDER);
                g.setStroke(new BasicStroke(1.5f));
                g.drawRoundRect(
                        nodeX, y, nodeWidth, nodeHeight, 10, 10
                );
                g.setColor(NODE_COLOR);
                g.fillRoundRect(
                        nodeX + 1, y + 1,
                        nodeWidth - 2, nodeHeight - 2, 9, 9
                );

                g.setColor(TEXT);
                g.setFont(new Font("Segoe UI", Font.BOLD, 15));
                FontMetrics fm = g.getFontMetrics();
                String value = values.get(i);
                g.drawString(
                        value,
                        nodeX + (nodeWidth - fm.stringWidth(value)) / 2,
                        y + 35
                );

                g.setColor(MUTED);
                g.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                g.drawString("Node " + (i + 1), nodeX + 5, y + 75);
            }

            int lastX = x + (count - 1) * (nodeWidth + gap);

            if (doubly) {
                drawArrow(
                        g,
                        lastX + nodeWidth + 3,
                        y + 21,
                        lastX + nodeWidth + 32,
                        y + 21,
                        PRIMARY
                );
                g.setColor(MUTED);
                g.drawString("NULL", lastX + nodeWidth + 35, y + 25);
            } else {
                drawArrow(
                        g,
                        lastX + nodeWidth + 3,
                        y + nodeHeight / 2,
                        lastX + nodeWidth + 32,
                        y + nodeHeight / 2,
                        PRIMARY
                );
                g.setColor(MUTED);
                g.drawString("NULL", lastX + nodeWidth + 35, y + 35);
            }

            if (doubly) {
                g.setColor(SUCCESS);
                g.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                g.drawString("Forward and backward links", x, y + 112);
            }
        }

        // -----------------------------------------------------
        // DRAWING HELPERS
        // -----------------------------------------------------

        private void drawHeading(Graphics2D g, String heading) {
            g.setColor(TEXT);
            g.setFont(new Font("Segoe UI", Font.BOLD, 15));
            g.drawString(heading, 22, 30);

            g.setColor(BORDER);
            g.drawLine(22, 42, Math.max(200, getWidth() - 22), 42);
        }

        private void drawCell(
                Graphics2D g,
                int x,
                int y,
                int width,
                int height,
                String value,
                boolean filled
        ) {
            g.setColor(filled ? NODE_BORDER : BORDER);
            g.setStroke(new BasicStroke(1.4f));
            g.drawRoundRect(x, y, width, height, 9, 9);

            g.setColor(filled ? NODE_COLOR : EMPTY_COLOR);
            g.fillRoundRect(
                    x + 1, y + 1,
                    width - 1, height - 1, 8, 8
            );

            g.setColor(filled ? TEXT : MUTED);
            g.setFont(new Font("Segoe UI", Font.BOLD, 14));

            String text = filled ? value : "Empty";
            FontMetrics fm = g.getFontMetrics();

            g.drawString(
                    text,
                    x + (width - fm.stringWidth(text)) / 2,
                    y + height / 2 + 5
            );
        }

        private void drawArrow(
                Graphics2D g,
                int x1,
                int y1,
                int x2,
                int y2,
                Color color
        ) {
            g.setColor(color);
            g.setStroke(new BasicStroke(1.8f));
            g.drawLine(x1, y1, x2, y2);

            double angle = Math.atan2(y2 - y1, x2 - x1);
            int arrowSize = 7;

            int ax1 = (int) (x2 - arrowSize * Math.cos(angle - Math.PI / 6));
            int ay1 = (int) (y2 - arrowSize * Math.sin(angle - Math.PI / 6));

            int ax2 = (int) (x2 - arrowSize * Math.cos(angle + Math.PI / 6));
            int ay2 = (int) (y2 - arrowSize * Math.sin(angle + Math.PI / 6));

            g.drawLine(x2, y2, ax1, ay1);
            g.drawLine(x2, y2, ax2, ay2);
        }

        private void drawLegend(
                Graphics2D g,
                String first,
                String second
        ) {
            int y = getHeight() - 28;

            g.setColor(NODE_BORDER);
            g.fillRoundRect(25, y - 11, 15, 15, 4, 4);

            g.setColor(TEXT);
            g.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            g.drawString(first, 47, y + 1);

            g.setColor(BORDER);
            g.fillRoundRect(140, y - 11, 15, 15, 4, 4);

            g.setColor(TEXT);
            g.drawString(second, 162, y + 1);
        }
    }

    // =========================================================
    // LAYOUT HELPERS
    // =========================================================

    private JPanel createTabContainer() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(UITheme.BACKGROUND);
        panel.setBorder(new EmptyBorder(17, 12, 10, 12));
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

    private JPanel createSelectorCard(
            String title,
            String description,
            JComboBox<String> selector
    ) {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(15, 0));
        card.setBorder(new EmptyBorder(12, 15, 12, 15));

        JPanel labels = new JPanel();
        labels.setOpaque(false);
        labels.setLayout(new BoxLayout(labels, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 13));
        heading.setForeground(TEXT);

        JLabel info = new JLabel(description);
        info.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        info.setForeground(MUTED);

        labels.add(heading);
        labels.add(Box.createVerticalStrut(3));
        labels.add(info);

        card.add(labels, BorderLayout.CENTER);
        card.add(selector, BorderLayout.EAST);

        return card;
    }

    private void addControl(
            JPanel panel,
            GridBagConstraints gbc,
            int column,
            String label,
            JComponent component
    ) {
        gbc.gridx = column;
        gbc.gridy = 0;
        gbc.weightx = 1;

        JPanel wrapper = new JPanel(new BorderLayout(0, 6));
        wrapper.setOpaque(false);

        JLabel title = new JLabel(label);
        title.setFont(new Font("Segoe UI", Font.BOLD, 11));
        title.setForeground(MUTED);

        wrapper.add(title, BorderLayout.NORTH);
        wrapper.add(component, BorderLayout.CENTER);

        panel.add(wrapper, gbc);
    }

    private JComboBox<String> createCombo(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        combo.setBackground(WHITE);
        combo.setForeground(TEXT);
        combo.setPreferredSize(new Dimension(150, 35));
        return combo;
    }

    private JTextField createField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setForeground(TEXT);
        field.setBackground(WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(7, 9, 7, 9)
        ));
        field.setPreferredSize(new Dimension(130, 35));
        field.setToolTipText(placeholder);
        return field;
    }

    private JButton createButton(String text, Color background) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(WHITE);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 17, 10, 17));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private boolean needsValue(String operation) {
        return operation.equals("Push")
                || operation.equals("Enqueue")
                || operation.equals("Insert at Beginning")
                || operation.equals("Insert at End")
                || operation.equals("Insert Before")
                || operation.equals("Insert After")
                || operation.equals("Delete Element")
                || operation.equals("Search");
    }

    private boolean needsTarget(String operation) {
        return operation.equals("Insert Before")
                || operation.equals("Insert After")
                || operation.equals("Delete Before")
                || operation.equals("Delete After");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Input Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
