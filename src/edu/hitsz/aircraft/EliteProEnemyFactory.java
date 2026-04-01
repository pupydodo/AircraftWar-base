package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.application.ImageManager;

public class EliteProEnemyFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy() {
        int speedX = Math.random() > 0.5 ? 5 : -5;
        return new EliteProEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_PRO_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                speedX,
                10,
                90
        );
    }
}

