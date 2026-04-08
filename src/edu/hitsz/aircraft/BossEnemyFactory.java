package edu.hitsz.aircraft;
import edu.hitsz.application.Main;
public class BossEnemyFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy() {
        return new BossEnemy(
                Main.WINDOW_WIDTH / 2,
                (int) (Main.WINDOW_HEIGHT * 0.1),
                5,
                0,
                500
        );
    }
}