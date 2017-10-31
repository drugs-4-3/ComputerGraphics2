import java.awt.*;

public class RectangleMark extends Mark {

    public final int CATEGORY = Mark.CATEGORY_RECT;
    private static final int DEFAULT_STROKE_THICKNESS = 8;
    private static final int TEMPORARY_STROKE_THICKNESS = 4;
    private static final Color DEFAULT_COLOR = Color.RED;
    private static final Color TEMPORARY_COLOR= Color.BLUE;
    private int x, y, width, height;

    public RectangleMark(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = DEFAULT_COLOR;
        this.stroke = new BasicStroke(DEFAULT_STROKE_THICKNESS);
    }

    public RectangleMark(int x, int y, int width, int height, boolean isTemporary) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        if (isTemporary) {
            this.color = TEMPORARY_COLOR;
            this.stroke = new BasicStroke(TEMPORARY_STROKE_THICKNESS);
        } else {
            this.color = DEFAULT_COLOR;
            this.stroke = new BasicStroke(DEFAULT_STROKE_THICKNESS);
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(this.color);
        g2d.setStroke(this.stroke);
        g.drawRect(x, y, width, height);
    }
}
