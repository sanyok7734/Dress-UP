package com.raccoonapps.worksimple.view;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.raccoonapps.worksimple.MainActivity;
import com.raccoonapps.worksimple.R;
import com.raccoonapps.worksimple.eventbus.BusProvider;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTouch;

/**
 * Created by sanyok on 21.10.15.
 */
public class FragmentWellDone extends Fragment {


    @Bind(R.id.well_done_girl)
    ImageView wellDoneGirl;

    @Bind(R.id.splash)
    View splash;
    @Bind(R.id.root)
    FrameLayout root;
    @Bind(R.id.buttons)
    RelativeLayout buttons;
    @Bind(R.id.banner_well_done)
    FrameLayout banner;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_well_done, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = this.getArguments();
        Bitmap bitmap = bundle.getParcelable("well_done");
        BitmapDrawable wellDoneGirlImage = new BitmapDrawable(getResources(), bitmap);

        wellDoneGirl.setImageDrawable(wellDoneGirlImage);
        return view;
    }

    @OnTouch ({R.id.button_restart, R.id.button_back,
               R.id.button_fb, R.id.button_twi, R.id.button_wa, R.id.button_email})
    public boolean onTouch(View button, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                button.setAlpha(0.8f);
                break;

            case MotionEvent.ACTION_UP:
                button.setAlpha(1);
                switch (button.getId()) {
                    case R.id.button_back:
                        MainActivity.fragmentManager.popBackStack();

                        break;
                    case R.id.button_restart:
                        for(int i = 0; i < MainActivity.fragmentManager.getBackStackEntryCount(); ++i) {
                            MainActivity.fragmentManager.popBackStack();
                        }
                        BusProvider.getInstance().post(new FragmentGame());
                        break;
                    //TODO sharing
                    case R.id.button_fb:
                        wellDoneGirl.setVisibility(View.GONE);
                        break;
                    case R.id.button_twi:
                        wellDoneGirl.setVisibility(View.VISIBLE);
                        break;
                    case R.id.button_wa:
                        break;
                    case R.id.button_email:
                        break;
                }
                break;
        }
        return true;
    }



    //TODO PHOTO BU0TTON
    @OnTouch(R.id.button_photo)
    public boolean onTouchPhoto(View button, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                button.setAlpha(0.8f);
                break;

            case MotionEvent.ACTION_UP:
                button.setAlpha(1);

                buttons.setVisibility(View.INVISIBLE);
                banner.setVisibility(View.VISIBLE);
                wellDoneGirl.setVisibility(View.VISIBLE);

                View rootView = (View) root;
                rootView.setDrawingCacheEnabled(true);
                Bitmap bitmap = rootView.getDrawingCache();
                buttons.setVisibility(View.VISIBLE);
                banner.setVisibility(View.INVISIBLE);

                FileOutputStream fos = null;
                try {
                    File sdPath = Environment.getExternalStorageDirectory();
                    sdPath = new File(sdPath.getAbsolutePath() + "/" + "Dress_UP");
                    // create directory
                    sdPath.mkdirs();

                    fos = new FileOutputStream(sdPath + "/" + "Dress_UP_"
                            + System.currentTimeMillis() + ".jpg");

                    if (fos != null) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                        fos.flush();
                        fos.close();
                    }
                } catch (Exception e) {
                }

                break;
        }
        return true;
    }
}
