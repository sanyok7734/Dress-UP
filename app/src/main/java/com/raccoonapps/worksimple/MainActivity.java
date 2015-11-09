package com.raccoonapps.worksimple;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.raccoonapps.worksimple.eventbus.BusProvider;
import com.raccoonapps.worksimple.model.Squeezing;
import com.raccoonapps.worksimple.view.FragmentStart;
import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity {

    public static final int LAYOUT = R.layout.activity_main;

    public static final String FONT_PATH_LOGO = "fonts/PFArmonia-Reg.ttf";

    public static final Uri ADDRESS_BANNER = Uri.parse("https://quickappninja.com/");

    public static boolean onClickStart = true;
    public static boolean onClickWellDone = true;

    public  double screenWidth1;
    public  double screenHeight1;
    public static double screenWidth;
    public static double screenHeight;

    public static FragmentManager fragmentManager;
    private static FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenWidth = displaymetrics.widthPixels;
        screenHeight = displaymetrics.heightPixels;

        screenWidth1 = displaymetrics.widthPixels;
        screenHeight1 = displaymetrics.heightPixels;
        calculateOccupy();


        BusProvider.getInstanceMain().register(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new FragmentStart());
        fragmentTransaction.commit();

    }


    @Subscribe
    public void startFragment(Fragment fragmentGame) {
        fragmentTransaction = fragmentManager.beginTransaction();
        //  fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.add(R.id.container, fragmentGame);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstanceMain().unregister(this);

    }

    //TODO image girl jsone
    private void calculateOccupy() {
        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.girls);
        Squeezing.squeezingPercentage(drawable, screenWidth1, screenHeight1);
    }

    public static void preferenceBanner(View view, final Context context, boolean visible) {

        Typeface typefaceLogo = Typeface.createFromAsset(context.getAssets(), MainActivity.FONT_PATH_LOGO);

        FrameLayout banner = (FrameLayout) view.findViewById(R.id.banner_layout);
        final TextView textLogo = (TextView) view.findViewById(R.id.text_logo);
        final ImageView bannerLogo = (ImageView) view.findViewById(R.id.banner_logo);

        textLogo.setTypeface(typefaceLogo);
        textLogo.setText("This game is created in QuickAppNinja Free Game Builder");

        final ViewGroup.LayoutParams layoutParamsBanner = banner.getLayoutParams();
        layoutParamsBanner.height = (int) ((8 * MainActivity.screenHeight) / 100);
        ViewGroup.LayoutParams layoutParamsLogoBanner = bannerLogo.getLayoutParams();
        layoutParamsLogoBanner.height = layoutParamsBanner.height - ((20 * layoutParamsBanner.height) / 100);
        layoutParamsLogoBanner.width = ((174 * layoutParamsLogoBanner.height) / 100);

        ViewTreeObserver vto = textLogo.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                textLogo.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int heightLine = textLogo.getLineHeight();
                int countLine = textLogo.getLineCount();

                int freeH = layoutParamsBanner.height - (heightLine * countLine);
                int x = freeH / (countLine + 1);
                textLogo.setHeight(layoutParamsBanner.height);
                //textLogo.setLineSpacing(x, 1);
                textLogo.setPadding(0, x, x * 2, x);
            }
        });


        if (visible)
            banner.setVisibility(View.VISIBLE);

        final Intent openLink = new Intent(Intent.ACTION_VIEW, ADDRESS_BANNER);
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(openLink);
            }
        });

    }

}
