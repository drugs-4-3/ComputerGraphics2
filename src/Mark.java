import java.awt.*;

public abstract class Mark {

    public static final int CATEGORY_RECT = 1;
    public static final int CATEGORY_OVAL = 2;
    public static final int CATEGORY_CIRCLE = 3;

    private int category;
    private Stroke stroke;

    public abstract void draw(Graphics g);

}
