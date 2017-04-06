package fengyu.aoshen.topeakdemo;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import fengyu.aoshen.topeakdemo.tools.Tools;
import fengyu.aoshen.topeakdemo.view.Fighter;
import fengyu.aoshen.topeakdemo.fighter.Shooter;
import fengyu.aoshen.topeakdemo.view.BulletBarrage;
import fengyu.aoshen.topeakdemo.bullet.BulletListAdapter;
import fengyu.aoshen.topeakdemo.controller.BulletBarrageController;
import fengyu.aoshen.topeakdemo.controller.BulletInfoController;
import fengyu.aoshen.topeakdemo.controller.IAPPCallback;
import fengyu.aoshen.topeakdemo.model.BulletInfo;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = "MainActivity";

    public final static int INDEX_INIT_BULLET_INFO = 0;

    /**
     * Bullet List
     **/
    private BulletListAdapter bulletListAdapter;
    private ImageView ivBulletTypeIcon;

    /**
     * A Fighter
     **/
    private Fighter fighter;
    private ImageButton ibFireButton;

    /**
     * Bullet Count
     **/
    private TextView tvBulletCount;

    // ArrayList<Fighter> fighters ?
    // ArrayList<ImageButton> fireButtons ?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {

            initLayout();

            startGetData();


            testThread();


        } else {
            reLoadApp();
        }
    }

    private void testThread() {

        HandlerThread handlerThread = new HandlerThread("我的背景執行續");
        handlerThread.start();

        Log.e(TAG, "In Ui Thread, getMainLooper is "+ Looper.getMainLooper());
        Log.e(TAG, "In Ui Thread, myLooper is "+ Looper.myLooper());

        Handler handler = new Handler(handlerThread.getLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "start work");

                Log.e(TAG, "In Handler Thread, getMainLooper is "+ Looper.getMainLooper());
                Log.e(TAG, "In Handler Thread, myLooper is "+ Looper.myLooper());

            }
        });

    }

    private void startGetData() {

        BulletInfoController.getInstance(this).getBullList(new IAPPCallback<ArrayList<BulletInfo>>() {
            @Override
            public void onFinish(ArrayList<BulletInfo> bulletInfos) {
                bulletListAdapter.update(bulletInfos);

                BulletBarrageController.getInstance(MainActivity.this).setBulletInfos(bulletInfos);

                if (bulletListAdapter.getBulletTypeCount() > 0 &&
                        fighter != null && fighter.hasShooter())
                    showFireButton(bulletListAdapter.getBulletInfo(INDEX_INIT_BULLET_INFO));


            }
        });


        // ......get Fighter Data ?

        createFighter();

    }

    private void createFighter() {

        RelativeLayout rlFighterContainer = (RelativeLayout) findViewById(R.id.activity_main_fighter_container);
        fighter = new Fighter(this, Tools.getScreenSize(this));
        rlFighterContainer.addView(fighter);

        // if Fighter has been finished
        if (bulletListAdapter.getBulletTypeCount() > 0 &&
                fighter != null && fighter.hasShooter())
            showFireButton(bulletListAdapter.getBulletInfo(INDEX_INIT_BULLET_INFO));

        initBulletCount();
    }

    private void initBulletCount() {
        tvBulletCount = (TextView) findViewById(R.id.activity_main_bullet_count);

        fighter.setOnShootListener(new Shooter.OnShootListener() {
            @Override
            public void onShoot(Fighter fighter, int bulletCount) {
                tvBulletCount.setText(String.valueOf(bulletCount));
            }
        });

        tvBulletCount.setText(String.valueOf(fighter.getBulletCount()));
    }

    private void showFireButton(BulletInfo bulletInfo) {
        ibFireButton.setVisibility(View.VISIBLE);
        ivBulletTypeIcon.setVisibility(View.VISIBLE);

        setFighterBulletInfo(bulletInfo);
    }

    private void setFighterBulletInfo(BulletInfo bulletInfo) {

        if (fighter != null && fighter.hasShooter()) {
            Shooter shooter = fighter.getShooter();
            shooter.setCurrentBulletInfo(bulletInfo);
            ivBulletTypeIcon.setImageResource(bulletInfo.getTypeIconResId());
        }
    }

    private void initLayout() {

        setStatusBarColor();
        initBackground();
        initBulletList();
        initFireButton();
        initBulletContainer();
    }

    private void initBulletContainer() {

        // 新增彈幕畫面
        BulletBarrage bulletBarrage = new BulletBarrage(this, Tools.getScreenSize(this));

        // init BulletBarrageController ,bind BulletContainer
        BulletBarrageController.getInstance(this).setBulletBarrage(bulletBarrage);
        BulletBarrageController.getInstance(this).clearAllBullts();

        // TODO Add BulletContainer to layout
        RelativeLayout rlBulletBarrageContainer = (RelativeLayout) findViewById(R.id.activity_main_bullet_barrage_container);
        rlBulletBarrageContainer.addView(bulletBarrage);
    }

    private void initFireButton() {

        // ... fighters shot together?

        ibFireButton = (ImageButton) findViewById(R.id.activity_main_fire_button);

        ibFireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fighter != null && fighter.hasShooter()) {
                    Shooter shooter = fighter.getShooter();
                    shooter.shoot();
                }
            }
        });

    }

    private void initBulletList() {

        final RecyclerView rvBulletList = (RecyclerView) findViewById(R.id.activity_main_bullet_list);
        ivBulletTypeIcon = (ImageView) findViewById(R.id.activity_main_current_bullet_type_icon);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        bulletListAdapter = new BulletListAdapter(this, layoutManager);

        rvBulletList.setItemAnimator(new DefaultItemAnimator());
        rvBulletList.setLayoutManager(layoutManager);
        rvBulletList.setAdapter(bulletListAdapter);

        bulletListAdapter.setOnItemClickListener(new BulletListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, BulletInfo bulletInfo) {
                setFighterBulletInfo(bulletInfo);
            }
        });

        ivBulletTypeIcon.setVisibility(View.GONE);
    }

    /**
     * 設定手機最上方的狀態列背景顏色，5.0以上適用
     **/
    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color232122));
    }

    private void initBackground() {
        final ImageView ivBackground = (ImageView) findViewById(R.id.activity_main_background);

        Tools.getResourceBitmapWithSize(
                this,
                R.drawable.pciture_activity_main_background,
                Tools.getScreenSize(this),
                new Tools.OnWorkFinishedListener<Bitmap>() {
                    @Override
                    public void onFinish(Bitmap object) {
                        ivBackground.setImageBitmap(object);
                    }
                }
        );
    }

    private void reLoadApp() {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
