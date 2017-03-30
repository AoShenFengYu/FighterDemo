package fengyu.aoshen.topeakdemo.model;


/**
 * Created by David on 2017/3/30.
 */
public class BulletInfo {

    public final static String TAG = "BulletInfo";

    public final static int BULLET_TYPE_UNKNOWN = -1;
    public final static int BULLET_TYPE_MISSILE_DAVINCCI = 0;
    public final static int BULLET_TYPE_MISSILE_BACON = 1;
    public final static int BULLET_TYPE_MISSILE_NEWTON = 2;

    public final static int BULLET_SPEED_DEFAULT = 5;

    private int type;
    private String name;
    private int damage;
    private int speed;

    private int typeIconResId;
    private int listItemIconResId;


    public BulletInfo(String name,
                      int type,
                      int damage,
                      int speed,
                      int typeIconResId,
                      int listItemIconResId) {
        this.name = name;
        this.type = type;
        this.damage = damage;
        this.speed = speed;
        this.typeIconResId = typeIconResId;
        this.listItemIconResId = listItemIconResId;
    }

    public int getType() {
        return type;
    }


    public String getName() {
        return name;
    }


    public int getDamage() {
        return damage;
    }


    public int getSpeed() {
        return speed;
    }

    public int getTypeIconResId() {
        return typeIconResId;
    }

    public int getListItemIconResId() {
        return listItemIconResId;
    }


}
