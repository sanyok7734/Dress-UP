package com.raccoonapps.worksimple;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.raccoonapps.worksimple.eventbus.BusProvider;
import com.raccoonapps.worksimple.view.FragmentGame;
import com.raccoonapps.worksimple.view.FragmentStart;
import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity {

    public static final int LAYOUT = R.layout.activity_main;

    public static double screenWidth;
    public static double screenHeight;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenWidth = displaymetrics.widthPixels;
        screenHeight = displaymetrics.heightPixels;

        Log.d("COODRRNATORX", "screenWidth " + screenWidth);
        Log.d("COODRRNATORX", "screenHeight " + screenHeight);

        BusProvider.getInstance().register(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new FragmentStart());
        fragmentTransaction.commit();

    }

    @Subscribe
    public void startGame(FragmentGame fragmentGame) {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragmentGame);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    /*        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenWidth = displaymetrics.widthPixels;
        screenHeight = displaymetrics.heightPixels;


        final ImageView frameLayout = (ImageView) findViewById(R.id.girl);

        FrameLayout butHair = (FrameLayout) findViewById(R.id.but_hair);*/



  /*      fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ImageView heirs = (ImageView) findViewById(R.id.volosy);
                //TODO VISIBLE DELETE
                heirs.setVisibility(View.VISIBLE);
                CoordinatorElements.imageCoordinator(screenWidth, screenHeight, 36.19632, -0.37, heirs, frameLayout);

            }
        });*/


   /* public void clickButtonPanel(View view) {
        switch (view.getId()) {
            case R.id.but_hair:

                break;
        }


    }*/

/*    public void showPanel(View panel, View morePanel) {
        TranslateAnimation anim = new TranslateAnimation(0, -morePanel.getWidth(), 0, 0);
        anim.setDuration(1000);
        anim.setFillAfter(true);


        TranslateAnimation anim2 = new TranslateAnimation(0, -morePanel.getWidth(), 0, 0);
        anim2.setDuration(1000);
        anim2.setFillAfter(true);
        morePanel.startAnimation(anim);
        panel.startAnimation(anim2);
    }*/

}
