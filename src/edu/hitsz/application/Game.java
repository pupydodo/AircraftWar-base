package edu.hitsz.application;

import edu.hitsz.Props.AbstractProp;
import edu.hitsz.aircraft.*;
import edu.hitsz.application.HeroController;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.*;

import edu.hitsz.aircraft.EnemyFactory;
import edu.hitsz.aircraft.MobEnemyFactory;
import edu.hitsz.aircraft.EliteEnemyFactory;
import edu.hitsz.aircraft.ElitePlusEnemyFactory;
import edu.hitsz.aircraft.EliteProEnemyFactory;
import edu.hitsz.Props.SimplePropFactory;
import edu.hitsz.dao.Record;
import edu.hitsz.dao.RecordDao;
import edu.hitsz.dao.RecordDaoImpl;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 游戏主面板，游戏启动
 * @author hitsz
 */
public class Game extends JPanel {

    private int backGroundTop = 0;

    //调度器, 用于定时任务调度
    private final Timer timer;
    //时间间隔(ms)，控制刷新频率
    private final int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractProp> props;

    //屏幕中出现的敌机最大数量
    private final int enemyMaxNumber = 5;

    private final EnemyFactory mobFactory = new MobEnemyFactory();
    private final EnemyFactory eliteFactory = new EliteEnemyFactory();
    private final EnemyFactory elitePlusFactory = new ElitePlusEnemyFactory();
    private final EnemyFactory eliteProFactory = new EliteProEnemyFactory();
    private final EnemyFactory bossFactory = new BossEnemyFactory();

    //敌机生成周期
    protected double enemySpawnCycle = 20;
    private int enemySpawnCounter = 0;

    // Boss机控制
    private boolean bossExists = false;
    private int scoreThreshold = 0;

    //英雄机和敌机射击周期
    protected double shootCycle = 5;
    protected double enemy_shoot_Cycle = 30;
    private int shootCounter = 0;
    private int enemyShootCounter =0;

    //当前玩家分数
    private int score = 0;

    //游戏结束标志
    private boolean gameOverFlag = false;

    private static final double ELITE_PROP_DROP_RATE = 0.3;
    private static final int HERO_COLLISION_DAMAGE = 40;

    public Game() {
//        heroAircraft = new HeroAircraft(
//                Main.WINDOW_WIDTH / 2,
//                Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
//                0, 0, 100);
        heroAircraft = HeroAircraft.getInstance();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

        this.timer = new Timer("game-action-timer", true);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、及结束判定
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                enemySpawnCounter++;
                if (enemySpawnCounter >=enemySpawnCycle) {
                    enemySpawnCounter = 0;
                    // 产生敌机
                    if (enemyAircrafts.size() < enemyMaxNumber) {
                        if (score >= scoreThreshold + 500 && !bossExists) {
                            bossExists = true;
                            scoreThreshold += 500;
                            enemyAircrafts.add(bossFactory.createEnemy());
                        } else {
                            double rand_enemy = Math.random();
                            if (rand_enemy < 0.4) {
                                enemyAircrafts.add(mobFactory.createEnemy());
                            } else if (rand_enemy < 0.7) {
                                enemyAircrafts.add(eliteFactory.createEnemy());
                            } else if (rand_enemy < 0.9) {
                                enemyAircrafts.add(elitePlusFactory.createEnemy());
                            } else {
                                enemyAircrafts.add(eliteProFactory.createEnemy());
                            }
                        }
                    }
                }

                // 飞机发射子弹
                shootAction();
                // 子弹移动
                bulletsMoveAction();
                // 飞机移动
                aircraftsMoveAction();
                // 道具移动
                propsMoveAction();
                // 撞击检测
                crashCheckAction();
                // 后处理
                postProcessAction();
                // 重绘界面
                repaint();
                // 游戏结束检查
                checkResultAction();
            }
        };
        // 以固定延迟时间进行执行：本次任务执行完成后，延迟 timeInterval 再执行下一次
        timer.schedule(task,0,timeInterval);

    }

    //***********************
    //      Action 各部分
    //***********************

    private void shootAction() {
        shootCounter++;
        enemyShootCounter++;
        if (shootCounter >= shootCycle) {
            shootCounter = 0;
            //英雄机射击
            heroBullets.addAll(heroAircraft.shoot());
            // TODO 敌机射击
        }
        if(enemyShootCounter >= enemy_shoot_Cycle){
            enemyShootCounter = 0;
            for(AbstractAircraft enemyAircraft: enemyAircrafts){
                if (isEliteEnemy(enemyAircraft)) {
                    enemyBullets.addAll(enemyAircraft.shoot());
                }
            }
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction() {
        for (AbstractProp prop : props) {
            prop.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄机
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                // 英雄机损失一定生命值
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        score += 10;
                        maybeGenerateProp(enemyAircraft);
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(HERO_COLLISION_DAMAGE);
                }
            }
        }

        // 我方获得道具，道具生效
        for (AbstractProp prop : props) {
            if (prop.notValid()) {
                continue;
            }
            if (prop.crash(heroAircraft) || heroAircraft.crash(prop)) {
                prop.activate(heroAircraft);
                prop.vanish();
            }
        }

    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 删除无效的道具
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }

    /**
     * 检查游戏是否结束，若结束：关闭线程池
     */
    private void checkResultAction(){
        // 游戏结束检查英雄机是否存活
        if (heroAircraft.getHp() <= 0) {
            timer.cancel(); // 取消定时器并终止所有调度任务
            gameOverFlag = true;
            System.out.println("Game Over.");
            // 记录得分并展示排行榜
            RecordDao recordDao = new RecordDaoImpl("NORMAL");
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
            String currentTime = formatter.format(new Date());
            Record record = new Record("testUserName", score, currentTime);
            recordDao.addRecord(record);
            recordDao.printLeaderboard();
        }
    }

    //***********************
    //      Paint 各部分
    //***********************
    /**
     * 重写 paint方法
     * 通过重复调用paint方法，实现游戏动画
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, props);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.isEmpty()) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(Color.RED);
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE: " + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE: " + this.heroAircraft.getHp(), x, y);
    }

    private boolean isEliteEnemy(AbstractAircraft enemyAircraft) {
        return enemyAircraft instanceof EliteEnemy
                || enemyAircraft instanceof ElitePlusEnemy
                || enemyAircraft instanceof EliteProEnemy
                || enemyAircraft instanceof BossEnemy;
    }

    private void maybeGenerateProp(AbstractAircraft enemyAircraft) {
        if (!isEliteEnemy(enemyAircraft)) {
            return;
        }

        int locationX = enemyAircraft.getLocationX();
        int locationY = enemyAircraft.getLocationY();

        if (enemyAircraft instanceof BossEnemy) {
            bossExists = false;
            for (int i = 0; i < 3; i++) {
                double randPropType = Math.random();
                AbstractProp prop;
                if (randPropType < 0.33) {
                    prop = SimplePropFactory.createProp("Blood", locationX + (i-1)*30, locationY);
                } else if (randPropType < 0.66) {
                    prop = SimplePropFactory.createProp("Bullet", locationX + (i-1)*30, locationY);
                } else {
                    prop = SimplePropFactory.createProp("BulletPlus", locationX + (i-1)*30, locationY);
                }
                if (prop != null) {
                    props.add(prop);
                }
            }
            return;
        }

        if (Math.random() >= ELITE_PROP_DROP_RATE) {
            return;
        }

        double randPropType = Math.random();
        AbstractProp prop;

        if (enemyAircraft instanceof EliteEnemy) {
            if (randPropType < 0.25) {
                prop = SimplePropFactory.createProp("Blood", locationX, locationY);
            } else if (randPropType < 0.5) {
                prop = SimplePropFactory.createProp("Bullet", locationX, locationY);
            } else if (randPropType < 0.75) {
                prop = SimplePropFactory.createProp("BulletPlus", locationX, locationY);
            } else {
                prop = SimplePropFactory.createProp("Bomb", locationX, locationY);
            }
        } else {
            if (randPropType < 0.2) {
                prop = SimplePropFactory.createProp("Blood", locationX, locationY);
            } else if (randPropType < 0.4) {
                prop = SimplePropFactory.createProp("Bullet", locationX, locationY);
            } else if (randPropType < 0.6) {
                prop = SimplePropFactory.createProp("BulletPlus", locationX, locationY);
            } else if (randPropType < 0.8) {
                prop = SimplePropFactory.createProp("Bomb", locationX, locationY);
            } else {
                prop = SimplePropFactory.createProp("Freeze", locationX, locationY);
            }
        }

        if (prop != null) {
            props.add(prop);
        }
    }
}

