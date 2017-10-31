import java.awt.*;

public class RectangleMark extends Mark {

    public final int CATEGORY = Mark.CATEGORY_RECT;
    private static final int DEFAULT_STROKE_THICKNESS = 8;
    private static final Color DEFAULT_COLOR = Color.RED;
    private int x, y, width, height;

    public RectangleMark(int x1, int y1, int x2, int y2) {
        this.x = x1 < x2 ? x1 : x2;
        this.y = y1 < y2 ? y1 : y2;
        this.width = Math.abs(x1 - x2);
        this.height = Math.abs(y1 - y2);
        this.color = DEFAULT_COLOR;
        this.stroke = new BasicStroke(DEFAULT_STROKE_THICKNESS);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(this.color);
        g2d.setStroke(this.stroke);
        g.drawRect(x, y, width, height);
    }

}
