package com.raccoonapps.worksimple.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
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
import com.raccoonapps.worksimple.music.MainPlayer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

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
                        MainPlayer.getInstance(getActivity()).resume();
                        MainActivity.fragmentManager.popBackStack();

                        break;
                    case R.id.button_restart:
                        MainPlayer.getInstance(getActivity()).resetPlayer();
                        for(int i = 0; i < MainActivity.fragmentManager.getBackStackEntryCount(); ++i) {
                            MainActivity.fragmentManager.popBackStack();
                        }
                        BusProvider.getInstanceMain().post(new FragmentGame());
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


    private void shareInSocialNetwork(String applicationName, String imagePath, String message) {
        ArrayList<Intent> intents = new ArrayList<>();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpeg");
        List<ResolveInfo> resolveInfos = getActivity().getPackageManager().queryIntentActivities(shareIntent, 0);
        if (!resolveInfos.isEmpty()) {
            for (ResolveInfo info : resolveInfos) {
                Intent targetShare = new Intent(Intent.ACTION_SEND);
                targetShare.setType("image/jpeg");
                boolean isPackageNameContains = info.activityInfo.packageName.toLowerCase().contains(applicationName);
                boolean isNameContains = info.activityInfo.name.toLowerCase().contains(applicationName);
                if (isPackageNameContains || isNameContains) {
                    targetShare.putExtra(Intent.EXTRA_SUBJECT, "Pretty-girl photo");
                    targetShare.putExtra(Intent.EXTRA_TEXT, message);
                    targetShare.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imagePath)));
                    targetShare.setPackage(info.activityInfo.packageName);
                    intents.add(targetShare);
                }
            }
            Intent chooserIntent = Intent.createChooser(
                    intents.remove(0), "Select application to share by this beautiful girl");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                    intents.toArray(new Parcelable[intents.size()]));
            startActivity(chooserIntent);
        }
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
                    File sdPath;
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        sdPath = new File("Dress_UP");
                    } else {
                        sdPath = Environment.getExternalStorageDirectory();
                        sdPath = new File(sdPath.getAbsolutePath() + "/" + "Dress_UP");
                        // create directory
                        sdPath.mkdirs();
                    }
                    fos = new FileOutputStream(sdPath + "/" + "Dress_UP_"
                            + System.currentTimeMillis() + ".jpg");
                    if (fos != null) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                        fos.flush();
                        fos.close();
                    }
                } catch (Exception e) {
                }
                splash();
                break;
        }
        return true;
    }

    private void splash() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(splash, "Alpha", 0, 1);
        animator.setDuration(100).addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                splash.setAlpha(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.start();
    }

}
