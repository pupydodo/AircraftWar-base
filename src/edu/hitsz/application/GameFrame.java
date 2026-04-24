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

        Game game = new Game(difficulty);
        add(game);
        setVisible(true);
        game.action();
    }
}

