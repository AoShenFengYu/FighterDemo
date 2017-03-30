package fengyu.aoshen.topeakdemo.fighter;

import android.content.Context;

import fengyu.aoshen.topeakdemo.controller.BulletBarrageController;
import fengyu.aoshen.topeakdemo.model.BulletInfo;
import fengyu.aoshen.topeakdemo.view.Fighter;

/**
 * Created by David on 2017/3/30.
 * <p/>
 * 射擊器
 */
public class Shooter {

    public final static String TAG = "Shooter";
    public final int DEFAULT_COUNT = 100;
    private Context context;
    private Fighter fighter;
    private BulletInfo currentBulletInfo;

    private int bulletCount; //子彈數量

    private OnShootListener onShootListener;

    public Shooter(Context context, Fighter fighter) {
        this.context = context;
        this.fighter = fighter;

        bulletCount = DEFAULT_COUNT;
    }

    public void shoot() {
        if (currentBulletInfo != null && bulletCount > 0) {
            BulletBarrageController.getInstance(context).createBullet(fighter, currentBulletInfo);
            bulletCount--;

            if (onShootListener != null)
                onShootListener.onShoot(fighter,bulletCount);
        }
    }

    public void setCurrentBulletInfo(BulletInfo currentBulletInfo) {
        this.currentBulletInfo = currentBulletInfo;
    }

    public void setOnShootListener(OnShootListener onShootListener) {
        this.onShootListener = onShootListener;
    }

    public int getBulletCount() {
        return bulletCount;
    }

    public interface OnShootListener {
        void onShoot(Fighter fighter, int bulletCount);
    }
}
