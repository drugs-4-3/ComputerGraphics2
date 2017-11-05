import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class Menu extends JMenuBar {

    private JMenu fileMenu = new JMenu("File");
    private JMenu markMenu = new JMenu("Mark");
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

        markMenu.setMnemonic(KeyEvent.VK_M);
        markMenu.add(makeImportMarksMenu());
        markMenu.add(makeExportMarksMenu());
        add(markMenu);
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

    private JMenuItem makeImportMarksMenu() {
        JMenuItem importItem = markMenu.add("Import");
        importItem.setMnemonic(KeyEvent.VK_I);
        importItem.addActionListener(controller.getImportActionListener());
        return importItem;
    }

    private JMenuItem makeExportMarksMenu() {
        JMenuItem exportItem = markMenu.add("Export");
        exportItem.setMnemonic(KeyEvent.VK_I);
        exportItem.addActionListener(controller.getExportActionListener());
        return exportItem;
    }
}
