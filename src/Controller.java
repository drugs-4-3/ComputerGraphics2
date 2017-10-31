import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Controller {

    private JFileChooser chooser;
    private BoardPanel boardPanel;
    private ToolsPanel toolsPanel;
    JPanel mainPanel;
    private Window window;
    private static double MIN_MARK_SIZE = 30;
    private LinkedList<Mark> markList;
    private String currentToolName;

    public Controller() {
        chooser = new JFileChooser();
        boardPanel = new BoardPanel(this);
        toolsPanel = new ToolsPanel(this);
        markList = new LinkedList<>();
        currentToolName = "RECTANGLE";
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

    public void addMark(int x1, int y1, int x2, int y2) {
        if (dist(x1, y1, x2, y2) > MIN_MARK_SIZE) {
            switch(this.getCurrentTool()) {
                case "RECTANGLE":
                    markList.add(new RectangleMark(x1, y1, x2, y2));
                    break;
                case "OVAL":
                    int width = (int)dist(x1, 0, x2, 0);
                    int height = (int)dist(0, y1, 0, y2);
                    markList.add(new OvalMark(x1, y1, width, height));
                    break;
            }

            drawMarks();
            boardPanel.repaint();
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
        boardPanel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        drawMarks();
        boardPanel.repaint();
        window.pack();
    }

    public void drawMarks() {
        BufferedImage image = boardPanel.getImage();
        Graphics g = image.getGraphics();
        Graphics2D g2d = (Graphics2D) g;

        for (Mark m: markList) {
            System.out.println("painting mark");
            m.draw(g);
        }
        boardPanel.revalidate();
    }

    public void setCurrentTool(String name) {
        this.currentToolName = name.toUpperCase();
    }

    public String getCurrentTool() {
        return this.currentToolName;
    }
}
