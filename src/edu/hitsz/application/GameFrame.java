package edu.hitsz.application;

import javax.swing.JFrame;

/**
 * 游戏窗口。
 */
public class GameFrame extends JFrame {

    public GameFrame(Difficulty difficulty) {
        super("Aircraft War - " + difficulty.getDisplayName());
        setSize(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game game;
        switch (difficulty) {
            case EASY:
                game = new EasyGame();
                break;
            case HARD:
                game = new HardGame();
                break;
            case NORMAL:
            default:
                game = new NormalGame();
                break;
        }
        
        add(game);
        setVisible(true);
        game.action();
    }
}
