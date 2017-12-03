package Game_2048;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame game = new JFrame();
        Model model = new Model();
        Controller controller = new Controller(model);

        game.setTitle("Game_2048");
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setSize(450, 500);
        game.setResizable(false);
        game.add(controller.getView());
        game.setLocationRelativeTo(null);
        game.setVisible(true);
    }
}
