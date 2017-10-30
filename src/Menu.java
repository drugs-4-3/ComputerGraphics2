import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class Menu extends JMenuBar {

    private JMenu fileMenu = new JMenu("File");
    private Controller controller;

    public Menu(Controller controller) {
        this.controller = controller;
        setFileMenu();
    }

    private void setFileMenu() {
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.add(makeSaveFileMenu());
        fileMenu.add(makeOpenFileMenu());
        add(fileMenu);
    }

    private JMenuItem makeSaveFileMenu() {
        JMenuItem saveItem = fileMenu.add("Save");
        saveItem.setMnemonic(KeyEvent.VK_S);
        saveItem.addActionListener(controller.getSaveFileMenuHandler());
        return saveItem;
    }

    private JMenuItem makeOpenFileMenu() {
        JMenuItem openItem = fileMenu.add("Open");
        openItem.setMnemonic(KeyEvent.VK_O);
        openItem.addActionListener(controller.getOpenFileMenuHandler());
        return openItem;
    }
}
