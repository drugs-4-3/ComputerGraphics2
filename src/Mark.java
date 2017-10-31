import java.awt.*;

public abstract class Mark {

    public static final int CATEGORY_RECT = 1;
    public static final int CATEGORY_OVAL = 2;
    public static final int CATEGORY_CIRCLE = 3;

    protected int category;
    protected Stroke stroke;
    protected Color color;

    public abstract void draw(Graphics g);

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    public Stroke getStroke() {
        return this.stroke;
    }

}
