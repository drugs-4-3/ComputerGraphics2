import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.LinkedList;

public class Controller {

    private JFileChooser chooser;
    private BoardPanel boardPanel;
    private ToolsPanel toolsPanel;
    JPanel mainPanel;
    private Window window;
    private static double MIN_MARK_SIZE = 30;
    public LinkedList<Mark> markList;
    private String currentToolName;
    private Mark temporaryMark;
    private Mark selectedMark;
    private DefaultListModel<Mark> model;

    public Controller() {
        model = new DefaultListModel<>();
        chooser = new JFileChooser();
        boardPanel = new BoardPanel(this);
        toolsPanel = new ToolsPanel(this);
        markList = new LinkedList<>();
        currentToolName = "RECTANGLE";
        temporaryMark = null;
    }

    public void run() {
        window = new Window(this);
        mainPanel = new JPanel();
        mainPanel.add(boardPanel);
        mainPanel.add(toolsPanel);
        window.add(mainPanel);
        window.start();
    }

    public AbstractAction getSaveFileMenuHandler() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                chooser.setSelectedFile(new File("filename.png"));
                int result = chooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    saveImage(chooser.getSelectedFile());
                }
            }
        };
    }

    public AbstractAction getOpenFileMenuHandler() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter("IMAGES", "jpg", "jpeg", "png", "gif");
                chooser.setFileFilter(filter);
                int result = chooser.showOpenDialog(boardPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        loadImage(chooser.getSelectedFile());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Cannot load file",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                chooser.setFileFilter(null); // don't forget to disable only image filter for chooser
            }
        };
    }

    public AbstractAction getImportActionListener() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int result = chooser.showOpenDialog(boardPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        importMarks(chooser.getSelectedFile());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Cannot load file",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                chooser.setFileFilter(null); // don't forget to disable only image filter for chooser
                boardPanel.repaint();
            }
        };
    }

    public AbstractAction getExportActionListener() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                chooser.setSelectedFile(new File("marks"));
                int result = chooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        exportMarks(chooser.getSelectedFile());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(
                                null,
                                "Cannot save file",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };
    }

    private void exportMarks(File file) throws IOException {
        FileWriter fw = new FileWriter(file);
        for(Mark m: markList) {
            fw.write(String.format("%d %d %d %d %d \n", m.CATEGORY, m.x, m.y, m.width, m.height));

        }
        fw.close();
    }

    private void importMarks(File file) throws Exception {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null) {
            String[] split = line.split(" ");
            int category = Integer.valueOf(split[0]);
            int x = Integer.valueOf(split[1]);
            int y = Integer.valueOf(split[2]);
            int width = Integer.valueOf(split[3]);
            int height = Integer.valueOf(split[4]);
            switch (category) {
                case Mark.CATEGORY_RECT:
                    Mark m = new RectangleMark(x, y, width, height);
                    markList.add(m);
                    model.addElement(m);
                    break;
                case Mark.CATEGORY_OVAL:
                    markList.add(new OvalMark(x, y, width, height));
                    break;
                default:
                    throw new Exception("Provided file has illegal content");
            }
        }
    }

    public void addMark(int x1, int y1, int x2, int y2) {
        if (dist(x1, y1, x2, y2) > MIN_MARK_SIZE) {
            int x, y, width, height;
            x = x1 < x2 ? x1 : x2;
            y = y1 < y2 ? y1 : y2;
            width = (int)dist(x1, 0, x2, 0);
            height = (int)dist(0, y1, 0, y2);
            Mark m = null;
            switch(this.getCurrentTool()) {
                case "RECTANGLE":
                    m = new RectangleMark(x, y, width, height);
                    break;
                case "OVAL":
                    m = new OvalMark(x, y, width, height);
                    break;
                default:
                    break;
            }
            markList.add(m);
            model.addElement(m);
        }
    }

    private double dist(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
    }

    private void saveImage(File file) {
        BufferedImage imageToSave = boardPanel.getImage();
        try {
            ImageIO.write(imageToSave, "PNG", file);
        } catch (IOException e) {
            // handle the exception
        }
    }

    private void loadImage(File file) throws IOException{
        BufferedImage image = ImageIO.read(file);
        boardPanel.setImage(image);
        drawMarks();
        boardPanel.repaint();
        window.pack();
    }

    public void drawMarks() {
        BufferedImage image = boardPanel.getImage();
        Graphics g = image.getGraphics();

        for (Mark m: markList) {
            System.out.println("painting mark");
            m.draw(g);
        }
        if (temporaryMark != null) {
            temporaryMark.draw(g);
        }
        boardPanel.revalidate();
    }

    public void setCurrentTool(String name) {
        this.currentToolName = name.toUpperCase();
    }

    public String getCurrentTool() {
        return this.currentToolName;
    }

    public void setTemporaryMark(int x1, int y1, int x2, int y2) {
        int x = x1 < x2 ? x1 : x2;
        int y = y1 < y2 ? y1 : y2;
        int width = (int)dist(x1, 0, x2, 0);
        int height = (int)dist(0, y1, 0, y2);

        switch(this.getCurrentTool()) {
            case "RECTANGLE":
                temporaryMark = new RectangleMark(x, y, width, height, true);
                break;
            case "OVAL":
                temporaryMark = new OvalMark(x, y, width, height, true);
                break;
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    public void setTemporaryMark(Mark m) {
        this.temporaryMark = m;
    }

    public Mark getTemporaryMark() {
        return this.temporaryMark;
    }

    public static BufferedImage deepCopyBufferedImage(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    /**
     * First mark which border contains point (x, y) is set as selected.
     * Only one mark at time can be selected
     *
     * @param x cord
     * @param y cord
     * @return true if point (x,y) is inside borders of one of the marks.
     */
    public boolean trySelectingMark(int x, int y) {
        boolean result = false;
        for(Mark m: markList) {
            if (!result) {
                if (m.isPointInsideBorder(x, y)) {
                    int index = markList.indexOf(m);
                    toolsPanel.selectMarkAt(index);
                    m.setSelected(true);
                    result = true;
                }
                else {
                    m.setSelected(false);
                }
            }
            else {
                m.setSelected(false);
                toolsPanel.selectMarkAt(-1);
            }
        }
        return result;
    }

    public void modifySelectedMark(int x, int y) {
        Mark m = getSelectedMark();
        if (m != null) {
            // we need to know to which direction we want
            boolean horizontalSide = x >= (m.getX() + m.getWidth())/2;
            boolean verticallSide = y >= (m.getY() + m.getHeight())/2;
        }
    }

    private Mark getSelectedMark() {
        return this.selectedMark;
    }

    public DefaultListModel<Mark> getMarkListModel() {
        return this.model;
    }

    public ActionListener getDeleteActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int selected_index = toolsPanel.getSelectedIndex();
                //toolsPanel.list.remove(selected_index);
                if (selected_index >= 0) {
                    model.remove(selected_index);
                    markList.remove(selected_index);
                    boardPanel.repaint();
                }
            }
        };
    }

}

/*
    - newly added mark should be selected by default
    - method for selecting given mark and deselecting others
    - modify selected mark - allow to move every side of rect
 */
