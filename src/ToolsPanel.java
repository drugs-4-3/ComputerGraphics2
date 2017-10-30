import javax.swing.*;
import java.awt.*;

public class ToolsPanel extends JPanel {

    Controller controller;

    public ToolsPanel(Controller controller) {
        super();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        this.controller = controller;
        add(new JButton("Rectangle"));
        add(new JButton("Oval"));
        add(new JButton("Circle"));
    }

    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
    }
}
