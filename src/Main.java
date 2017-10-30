import java.awt.*;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Controller controller = new Controller();
            controller.run();
        });
    }
}
