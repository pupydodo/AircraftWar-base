package edu.hitsz.Props;

public class SimplePropFactory {

    private static final int PROP_SPEED_Y = 5;
    private static final int BLOOD_PROP_HEAL_HP = 20;

    public static AbstractProp createProp(String propType, int locationX, int locationY) {
        if ("Blood".equals(propType)) {
            return new BloodProp(locationX, locationY, 0, PROP_SPEED_Y, BLOOD_PROP_HEAL_HP);
        } else if ("Bullet".equals(propType)) {
            return new BulletProp(locationX, locationY, 0, PROP_SPEED_Y);
        } else if ("BulletPlus".equals(propType)) {
            return new BulletPlusProp(locationX, locationY, 0, PROP_SPEED_Y);
        } else if ("Bomb".equals(propType)) {
            return new BombProp(locationX, locationY, 0, PROP_SPEED_Y);
        } else if ("Freeze".equals(propType)) {
            return new FreezeProp(locationX, locationY, 0, PROP_SPEED_Y);
        }
        return null;
    }
}

