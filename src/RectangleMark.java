import java.awt.*;
import java.awt.geom.Rectangle2D;

public class RectangleMark extends Mark {


    private static final Color DEFAULT_COLOR = Color.RED;
    private static final Color TEMPORARY_COLOR= Color.BLUE;

    public RectangleMark(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.color = DEFAULT_COLOR;
        this.stroke = new BasicStroke(DEFAULT_STROKE_THICKNESS);
        this.CATEGORY = Mark.CATEGORY_RECT;
    }

    public RectangleMark(int x, int y, int width, int height, boolean isTemporary) {
        super(x, y, width, height);
        if (isTemporary) {
            this.color = TEMPORARY_COLOR;
            this.stroke = new BasicStroke(TEMPORARY_STROKE_THICKNESS);
        }

        else {
            this.color = DEFAULT_COLOR;
            this.stroke = new BasicStroke(DEFAULT_STROKE_THICKNESS);
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.drawRect(x, y, width, height);
    }
}
