import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolsPanel extends JPanel implements ActionListener {

    Controller controller;
    JLabel textLabel;

    public ToolsPanel(Controller controller) {
        super();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        this.controller = controller;
        setButtons();
        setTextLabel();
    }

    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
    }

    private void setButtons() {
        JButton rectButton = new JButton("Rectangle");
        rectButton.addActionListener(this);
        rectButton.setPreferredSize(new Dimension(150, 50));
        JButton ovalButton = new JButton("Oval");
        ovalButton.addActionListener(this);
        ovalButton.setPreferredSize(new Dimension(150, 50));
        add(rectButton);
        add(ovalButton);
    }

    private void setTextLabel() {
        textLabel = new JLabel();
        textLabel.setText("Lorem ipsum signum dorum loremi ti nomi aerysuym nohole");
        textLabel.setPreferredSize(new Dimension(150, 150));
        add(textLabel);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton source = (JButton) actionEvent.getSource();
        controller.setCurrentTool(source.getText());
    }
}
