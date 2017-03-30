package fengyu.aoshen.topeakdemo.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

import fengyu.aoshen.topeakdemo.R;
import fengyu.aoshen.topeakdemo.Tools;
import fengyu.aoshen.topeakdemo.model.BulletInfo;

/**
 * Created by David on 2017/3/30.
 */
public class BulletInfoController {

    private static BulletInfoController sharedInstance;
    private Context context;


    /**
     * Fake Data
     **/
    private Bitmap bitmapDavincci;
    private Bitmap bitmapBacon;
    private Bitmap bitmapNewton;


    private BulletInfoController(Context context) {
        this.context = context;
    }


    public static BulletInfoController getInstance(Context context) {
        if (sharedInstance == null)
            sharedInstance = new BulletInfoController(context);

        return sharedInstance;
    }

    public void getBullList(IAPPCallback<ArrayList<BulletInfo>> callback) {
        ArrayList<BulletInfo> fakeData = getFakeData();
        callback.onFinish(fakeData);
        initFakeBulletBitmap(fakeData);
    }

    // TODO 應該要下載圖片 改為下載圖片
    private void initFakeBulletBitmap(ArrayList<BulletInfo> fakeData) {
        for (final BulletInfo bulletInfo : fakeData) {
            Tools.getResourceBitmapWithSize(
                    context,
                    bulletInfo.getTypeIconResId(),
                    new int[]{context.getResources().getDimensionPixelOffset(R.dimen.bullet_width), context.getResources().getDimensionPixelOffset(R.dimen.bullet_height)},
                    new Tools.OnWorkFinishedListener<Bitmap>() {
                        @Override
                        public void onFinish(Bitmap bitmap) {
                            switch (bulletInfo.getType()) {
                                case BulletInfo.BULLET_TYPE_MISSILE_DAVINCCI:
                                    bitmapDavincci = bitmap;
                                    break;

                                case BulletInfo.BULLET_TYPE_MISSILE_BACON:
                                    bitmapBacon = bitmap;
                                    break;

                                case BulletInfo.BULLET_TYPE_MISSILE_NEWTON:
                                    bitmapNewton = bitmap;
                                    break;

                                default:
                            }
                        }
                    });
        }

    }

    private ArrayList<BulletInfo> getFakeData() {
        ArrayList<BulletInfo> arrayList = new ArrayList<>();

        BulletInfo davincci = new BulletInfo(
                "Davincii",
                BulletInfo.BULLET_TYPE_MISSILE_DAVINCCI,
                1,
                BulletInfo.BULLET_SPEED_DEFAULT,
                R.drawable.icon_bullet_type_missile_davincci,
                R.drawable.icon_bullet_list_item_missile_davincci);

        BulletInfo bacon = new BulletInfo(
                "Bacon",
                BulletInfo.BULLET_TYPE_MISSILE_BACON,
                2,
                BulletInfo.BULLET_SPEED_DEFAULT,
                R.drawable.icon_bullet_type_missile_bacon,
                R.drawable.icon_bullet_list_item_missile_bacon);

        BulletInfo newton = new BulletInfo(
                "Newton",
                BulletInfo.BULLET_TYPE_MISSILE_NEWTON,
                3,
                BulletInfo.BULLET_SPEED_DEFAULT,
                R.drawable.icon_bullet_type_missile_newton,
                R.drawable.icon_bullet_list_item_missile_newton);

        arrayList.add(davincci);
        arrayList.add(bacon);
        arrayList.add(newton);

        return arrayList;
    }

    public Bitmap getBulletBitmapByType(int type) {
        switch (type) {
            case BulletInfo.BULLET_TYPE_MISSILE_DAVINCCI:
                return bitmapDavincci;

            case BulletInfo.BULLET_TYPE_MISSILE_BACON:
                return bitmapBacon;

            case BulletInfo.BULLET_TYPE_MISSILE_NEWTON:
                return bitmapNewton;

            default:
                return null;
        }
    }

}
