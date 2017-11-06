import sun.font.TextLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolsPanel extends JPanel implements ActionListener {

    private Controller controller;
    public JList list;
    GridBagConstraints c;

    public ToolsPanel(Controller controller) {
        super();
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        c = new GridBagConstraints();
        this.controller = controller;
        setButtons();
        setMarkScrollList();
    }

    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
    }

    private void setButtons() {

        JButton rectButton = new JButton("Rectangle");
        rectButton.setPreferredSize(new Dimension(130, 30));
        rectButton.setBackground(new Color(59, 89, 182));
        rectButton.setFocusPainted(false);
        rectButton.setForeground(Color.WHITE);
        rectButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        rectButton.addActionListener(this);
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = c.HORIZONTAL;
        c.anchor = c.FIRST_LINE_START;
        c.insets = new Insets(5, 5, 5, 0);
        c.weighty = 0.5;
        c.weightx = 0.5;
        add(rectButton, c);

        JButton ovalButton = new JButton("Oval");
        ovalButton.addActionListener(this);
        ovalButton.setPreferredSize(new Dimension(130, 30));
        ovalButton.setBackground(new Color(59, 89, 182));
        ovalButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        ovalButton.setFocusPainted(false);
        ovalButton.setForeground(Color.WHITE);
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.fill = c.HORIZONTAL;
        c.anchor = c.FIRST_LINE_START;
        c.insets = new Insets(5, 5, 5, 0);
        c.weighty = 0.5;
        c.weightx = 0.5;
        add(ovalButton, c);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(controller.getDeleteActionListener());
        deleteButton.setPreferredSize(new Dimension(130, 30));
        deleteButton.setBackground(new Color(59, 89, 182));
        deleteButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        deleteButton.setFocusPainted(false);
        deleteButton.setForeground(Color.WHITE);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.fill = c.HORIZONTAL;
        c.anchor = c.FIRST_LINE_START;
        c.insets = new Insets(5, 5, 5, 0);
        c.weighty = 0.5;
        c.weightx = 0.5;
        add(deleteButton, c);
    }

    private void setMarkScrollList() {
        list = new JList(controller.getMarkListModel());
        JScrollPane scrollPane = new JScrollPane(list);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        scrollPane.setPreferredSize(new Dimension(300, 300));
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 3;
        c.fill = c.VERTICAL;
        c.anchor = c.CENTER;
        c.insets = new Insets(20, 20, 20, 20);
        add(scrollPane, c);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton source = (JButton) actionEvent.getSource();
        controller.setCurrentTool(source.getText());
    }

    public int getSelectedIndex() {
        return list.getSelectedIndex();
    }

    public void selectMarkAt(int index) {
        list.setSelectedIndex(index);
    }

}
