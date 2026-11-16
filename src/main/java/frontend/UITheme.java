
package frontend;

import java.awt.Color;
import java.awt.Font;

public class UITheme {

    // Main colours
    public static final Color SIDEBAR = new Color(36, 59, 90);
    public static final Color SIDEBAR_HOVER = new Color(55, 80, 113);

    public static final Color BACKGROUND = new Color(244, 246, 249);
    public static final Color WHITE = Color.WHITE;

    public static final Color PRIMARY = new Color(66, 107, 154);
    public static final Color PRIMARY_DARK = new Color(49, 83, 122);

    public static final Color TEXT = new Color(38, 52, 69);
    public static final Color MUTED = new Color(105, 117, 132);

    public static final Color BORDER = new Color(220, 229, 239);
    public static final Color ACCENT = new Color(88, 125, 166);

    public static final Color SUCCESS = new Color(65, 130, 95);
    public static final Color DANGER = new Color(180, 75, 75);

    // Fonts
    public static final Font TITLE_FONT =
            new Font("Segoe UI", Font.BOLD, 25);

    public static final Font HEADING_FONT =
            new Font("Segoe UI", Font.BOLD, 18);

    public static final Font NORMAL_FONT =
            new Font("Segoe UI", Font.PLAIN, 14);

    public static final Font BUTTON_FONT =
            new Font("Segoe UI", Font.BOLD, 13);

    private UITheme() {
    }
}
