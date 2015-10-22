package com.raccoonapps.worksimple.view;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.raccoonapps.worksimple.MainActivity;
import com.raccoonapps.worksimple.R;
import com.raccoonapps.worksimple.eventbus.BusProvider;
import com.raccoonapps.worksimple.music.MainPlayer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTouch;

/**
 * Created by sanyok on 21.10.15.
 */
public class FragmentWellDone extends Fragment {


    @Bind(R.id.well_done_girl)
    ImageView wellDoneGirl;


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
                        MainPlayer.getInstance(getActivity()).resume();
                        MainActivity.fragmentManager.popBackStack();
                        break;
                    case R.id.button_restart:
                        MainPlayer.getInstance(getActivity()).resetPlayer();
                        for(int i = 0; i < MainActivity.fragmentManager.getBackStackEntryCount(); ++i) {
                            MainActivity.fragmentManager.popBackStack();
                        }
                        BusProvider.getInstance().post(new FragmentGame());
                        break;
                    //TODO sharing
                    case R.id.button_fb:
                        break;
                    case R.id.button_twi:
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

    //TODO PHOTO BUTTON
    @OnTouch(R.id.button_photo)
    public boolean onTouchPhoto(View button, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                button.setAlpha(0.8f);
                break;

            case MotionEvent.ACTION_UP:
                button.setAlpha(1);
                break;
        }
        return true;
    }

}
