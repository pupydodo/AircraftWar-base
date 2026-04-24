package edu.hitsz.application;


import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.Props.BloodProp;
import edu.hitsz.Props.BulletPlusProp;
import edu.hitsz.Props.BulletProp;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 综合管理图片的加载，访问
 * 提供图片的静态访问方法
 * @author hitsz
 */
public class ImageManager {

    /**
     * 类名-图片 映射，存储各基类的图片 <br>
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, BufferedImage> CLASSNAME_IMAGE_MAP = new HashMap<>();

    public static BufferedImage BACKGROUND_IMAGE;
    public static BufferedImage BACKGROUND_EASY_IMAGE;
    public static BufferedImage BACKGROUND_NORMAL_IMAGE;
    public static BufferedImage BACKGROUND_HARD_IMAGE;
    public static BufferedImage HERO_IMAGE;
    public static BufferedImage HERO_BULLET_IMAGE;
    public static BufferedImage ENEMY_BULLET_IMAGE;
    public static BufferedImage MOB_ENEMY_IMAGE;
    public static BufferedImage ELITE_ENEMY_IMAGE;
    public static BufferedImage ELITE_PLUS_ENEMY_IMAGE;
    public static BufferedImage ELITE_PRO_ENEMY_IMAGE;
    public static BufferedImage ELITE_BULLET_IMAGE;
    public static BufferedImage BLOOD_PROP_IMAGE;
    public static BufferedImage BULLET_PROP_IMAGE;
    public static BufferedImage BULLET_PLUS_PROP_IMAGE;
    public static BufferedImage BOMB_PROP_IMAGE;
    public static BufferedImage FREEZE_PROP_IMAGE;
    public static BufferedImage BOSS_ENEMY_IMAGE;

    static {
        try {

            BACKGROUND_EASY_IMAGE = ImageIO.read(new FileInputStream("src/images/bg.jpg"));
            BACKGROUND_NORMAL_IMAGE = ImageIO.read(new FileInputStream("src/images/bg2.jpg"));
            BACKGROUND_HARD_IMAGE = ImageIO.read(new FileInputStream("src/images/bg3.jpg"));
            BACKGROUND_IMAGE = BACKGROUND_NORMAL_IMAGE;

            HERO_IMAGE = ImageIO.read(new FileInputStream("src/images/hero.png"));
            MOB_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/mob.png"));
            HERO_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_hero.png"));
            ENEMY_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_enemy.png"));
            ELITE_ENEMY_IMAGE =  ImageIO.read(new FileInputStream("src/images/elite.png"));
            ELITE_PLUS_ENEMY_IMAGE =  ImageIO.read(new FileInputStream("src/images/elitePlus.png"));
            ELITE_PRO_ENEMY_IMAGE =  ImageIO.read(new FileInputStream("src/images/elitePro.png"));
            BLOOD_PROP_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_blood.png"));
            BULLET_PROP_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bullet.png"));
            BULLET_PLUS_PROP_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bulletPlus.png"));
            BOMB_PROP_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bomb.png"));
            FREEZE_PROP_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_freeze.png"));
            BOSS_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/boss.png"));
//            ELITE_BULLET_IMAGE =  ImageIO.read(new FileInputStream("src/images/elite_enemy.png"));

            CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
            CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ELITE_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(edu.hitsz.aircraft.ElitePlusEnemy.class.getName(), ELITE_PLUS_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(edu.hitsz.aircraft.EliteProEnemy.class.getName(), ELITE_PRO_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BloodProp.class.getName(), BLOOD_PROP_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BulletProp.class.getName(), BULLET_PROP_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BulletPlusProp.class.getName(), BULLET_PLUS_PROP_IMAGE);
            CLASSNAME_IMAGE_MAP.put(edu.hitsz.Props.BombProp.class.getName(), BOMB_PROP_IMAGE);
            CLASSNAME_IMAGE_MAP.put(edu.hitsz.Props.FreezeProp.class.getName(), FREEZE_PROP_IMAGE);
            CLASSNAME_IMAGE_MAP.put(edu.hitsz.aircraft.BossEnemy.class.getName(), BOSS_ENEMY_IMAGE);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static BufferedImage get(String className){
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static BufferedImage get(Object obj){
        if (obj == null){
            return null;
        }
        return get(obj.getClass().getName());
    }

    public static BufferedImage getBackgroundImage(Difficulty difficulty) {
        if (difficulty == null) {
            return BACKGROUND_IMAGE;
        }
        switch (difficulty) {
            case EASY:
                return BACKGROUND_EASY_IMAGE;
            case HARD:
                return BACKGROUND_HARD_IMAGE;
            case NORMAL:
            default:
                return BACKGROUND_NORMAL_IMAGE;
        }
    }

}
