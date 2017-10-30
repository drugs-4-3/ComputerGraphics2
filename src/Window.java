import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Window {

    public static final String WINDOW_NAME = "Computer Graphics 2 - marking app";
    public BoardPanel boardPanel;
    private JFrame frame;
    private Controller controller;

    public Window(Controller controller) {
        this.controller = controller; // registering controller
        frame = new JFrame(WINDOW_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setJMenuBar(new Menu(controller));
    }

    public void start() {
        frame.pack();
        frame.setVisible(true);
    }

    public void pack() {
        frame.pack();
    }

    public void add(JComponent component) {
        frame.add(component, BorderLayout.CENTER);
    }

    public void setSize(int x, int y) {
        frame.setSize(x, y);
    }
}
