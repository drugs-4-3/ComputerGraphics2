import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolsPanel extends JPanel implements ActionListener {

    Controller controller;

    public ToolsPanel(Controller controller) {
        super();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        this.controller = controller;
        setButtons();
    }

    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
    }

    private void setButtons() {
        JButton rectButton = new JButton("Rectangle");
        rectButton.addActionListener(this);
        JButton ovalButton = new JButton("Oval");
        ovalButton.addActionListener(this);
        add(rectButton);
        add(ovalButton);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton source = (JButton) actionEvent.getSource();
        controller.setCurrentTool(source.getText());
    }
}
