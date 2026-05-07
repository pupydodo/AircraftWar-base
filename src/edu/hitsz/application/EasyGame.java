package edu.hitsz.application;

public class EasyGame extends Game {

    public EasyGame() {
        super(Difficulty.EASY);
        this.enemySpawnCycle = 25;
        this.shootCycle = 5;
        this.enemy_shoot_Cycle = 35;
    }

    @Override
    protected boolean allowBoss() {
        return false;
    }

    @Override
    protected int getBossHp() {
        return 0; // Not applicable
    }

    @Override
    protected void updateDifficultyOverTime() {
        // 简单难度各项参数不随时间变化
    }
}
