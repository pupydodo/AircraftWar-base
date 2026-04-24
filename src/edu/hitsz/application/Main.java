package edu.hitsz.application;

import javax.swing.*;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DifficultySelectionFrame selectionFrame = new DifficultySelectionFrame();
            selectionFrame.setVisible(true);
        });
    }
}
