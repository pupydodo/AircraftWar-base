package edu.hitsz.application;

public class NormalGame extends Game {

    private int bossHp = 500;
    private int timeThreshold = 0;

    public NormalGame() {
        super(Difficulty.NORMAL);
        this.enemySpawnCycle = 20;
        this.shootCycle = 5;
        this.enemy_shoot_Cycle = 30;
    }

    @Override
    protected boolean allowBoss() {
        return true;
    }

    @Override
    protected int getBossHp() {
        return bossHp; // 普通难度boss血量不提升
    }

    @Override
    protected void updateDifficultyOverTime() {
        // 普通难度：随时间逐步提升敌机产生周期、敌机速度、敌机血量
        if (gameTime >= timeThreshold + 5000) {
            timeThreshold += 5000;

            if (this.enemySpawnCycle > 10) {
                this.enemySpawnCycle -= 0.5;
            }
            this.enemyHpBoost += 10;
            this.enemySpeedYBoost += 1;

            System.out.println("难度提升！产生周期缩短，敌机血量、速度增加！");
        }
    }
}
