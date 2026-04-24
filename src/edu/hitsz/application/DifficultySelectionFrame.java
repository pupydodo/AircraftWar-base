package edu.hitsz.application;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

/**
 * 难度选择界面。
 */
public class DifficultySelectionFrame extends JFrame {

    public DifficultySelectionFrame() {
        super("Aircraft War - 选择难度");
        setSize(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel rootPanel = new JPanel(new BorderLayout(0, 20));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(80, 60, 80, 60));

        JLabel titleLabel = new JLabel("请选择游戏难度", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        rootPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.add(createDifficultyButton(Difficulty.EASY));
        buttonPanel.add(createDifficultyButton(Difficulty.NORMAL));
        buttonPanel.add(createDifficultyButton(Difficulty.HARD));
        rootPanel.add(buttonPanel, BorderLayout.CENTER);

        JPanel backgroundPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImageManager.getBackgroundImage(Difficulty.NORMAL), 0, 0, getWidth(), getHeight(), null);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(rootPanel, BorderLayout.CENTER);
        setContentPane(backgroundPanel);
    }

    private JButton createDifficultyButton(Difficulty difficulty) {
        JButton button = new JButton(difficulty.getDisplayName());
        button.setFont(new Font("SansSerif", Font.BOLD, 22));
        button.addActionListener(e -> {
            dispose();
            new GameFrame(difficulty);
        });
        return button;
    }
}

