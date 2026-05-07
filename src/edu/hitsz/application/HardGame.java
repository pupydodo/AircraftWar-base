package edu.hitsz.application;

public class HardGame extends Game {

    private int bossHp = 500;
    private int timeThreshold = 0;

    public HardGame() {
        super(Difficulty.HARD);
        this.enemySpawnCycle = 15;
        this.shootCycle = 5;
        this.enemy_shoot_Cycle = 25;
    }

    @Override
    protected boolean allowBoss() {
        return true;
    }

    @Override
    protected int getBossHp() {
        int currentHp = bossHp;
        bossHp += 200; // 困难难度：每次召唤 Boss 机时，会提升其血量。
        return currentHp;
    }

    @Override
    protected void updateDifficultyOverTime() {
        // 困难难度：随时间逐步提升敌机产生周期、敌机速度、敌机血量，同时提升英雄机射击周期与敌机射击周期
        if (gameTime >= timeThreshold + 5000) {
            timeThreshold += 5000;
            
            if (this.enemySpawnCycle > 8) {
                this.enemySpawnCycle -= 1;
            }
            if (this.shootCycle > 2) {
                this.shootCycle -= 0.2;
            }
            if (this.enemy_shoot_Cycle > 15) {
                this.enemy_shoot_Cycle -= 1;
            }

            this.enemyHpBoost += 20;
            this.enemySpeedYBoost += 2;
            
            System.out.println("难度飙升！产生周期、射击周期缩短，血量速度增加！");
        }
    }
}
