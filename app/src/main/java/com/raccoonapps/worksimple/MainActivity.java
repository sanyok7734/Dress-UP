package com.raccoonapps.worksimple;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.raccoonapps.worksimple.eventbus.BusProvider;
import com.raccoonapps.worksimple.model.Squeezing;
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
        calculateOccupy();


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

    //TODO image girl
    private void calculateOccupy() {
        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.girls);
        Squeezing.squeezingPercentage(drawable, screenWidth, screenHeight);
    }

}
